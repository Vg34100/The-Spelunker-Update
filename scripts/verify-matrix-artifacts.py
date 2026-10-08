#!/usr/bin/env python3
"""Verify The Spelunker Update's exact installable matrix JARs."""
import json
from pathlib import Path
import re
import struct
from zipfile import BadZipFile, ZipFile

try:
    import tomllib
except ImportError:  # Windows' established Python 3.10 runtime.
    tomllib = None

ROOT = Path(__file__).resolve().parents[1]


def properties(path):
    return {key.strip(): value.strip() for line in path.read_text().splitlines()
            if '=' in line and not line.lstrip().startswith('#')
            for key, value in [line.split('=', 1)]}


MOD_ID = properties(ROOT / 'gradle/publishing.properties')['mod_id']
PACKAGE = properties(ROOT / 'gradle.properties')['maven_group'].replace('.', '/') + '/' + MOD_ID + '/'


def quoted(text, key):
    match = re.search(rf'^\s*{re.escape(key)}\s*=\s*"([^"\n]*)"', text, re.MULTILINE)
    assert match, f'Missing metadata field: {key}'
    return match.group(1)


def release_jar(matrix):
    """Select one current release; reject intermediaries and stale versions."""
    jars = [p for p in (ROOT / 'build/libs' / matrix.stem).glob('*.jar')
            if not p.name.endswith(('-dev.jar', '-sources.jar', '-javadoc.jar',
                                    '-dev-shadow.jar', '-raw.jar'))]
    assert len(jars) == 1, f'Expected one release JAR: {jars}'
    pins = properties(ROOT / 'gradle.properties')
    minecraft, loader = matrix.stem.rsplit('-', 1)
    expected = f"{pins['archives_name']}-{loader}-{minecraft}-{pins['mod_version']}.jar"
    assert jars[0].name == expected, f'Wrong/stale release artifact: {jars[0]} (expected {expected})'
    return jars[0]


def inspect(matrix, artifact=None):
    pins = properties(matrix)
    assert matrix.stem == f"{pins['minecraft_version']}-{pins['loader']}", 'Target/property mismatch'
    artifact = Path(artifact) if artifact else release_jar(matrix)
    legacy = pins['loom_generation'] == 'legacy'
    root_pins = properties(ROOT / 'gradle.properties')
    config = properties(ROOT / 'gradle/publishing.properties')
    resource_roots = [ROOT / source / 'src/main/resources' for source in ('common', pins['loader'])]
    if legacy:
        resource_roots += [ROOT / 'gradle/compat/legacy' / source / 'src/main/resources'
                           for source in ('common', pins['loader'])]
    owned_resources = {p.relative_to(base).as_posix() for base in resource_roots
                       for p in base.rglob('*') if p.is_file()}
    with ZipFile(artifact) as jar:
        entries = jar.namelist()
        files = set(entries)
        assert len(entries) == len(files), 'Duplicate archive entries'
        read_json = lambda name: json.loads(jar.read(name))
        for name in ('Spelunkery', 'registry/SpelunkeryItems', 'registry/SpelunkeryBlocks',
                     'registry/SpelunkeryBiomeSources', 'registry/SpelunkeryRecipeTypes',
                     'worldgen/SpelunkeryWorldgen', 'gameplay/SpelunkeryGameplayHelper',
                     'compat/jei/SpelunkeryJeiPlugin'):
            assert PACKAGE + name + '.class' in files, f'Missing mod class: {name}'
        classes = [name for name in files if name.endswith('.class')]
        assert classes and all(name.startswith(PACKAGE) for name in classes), 'Bundled dependency classes'
        for name in files:
            if name.startswith(('assets/', 'data/')) and not name.endswith('/'):
                assert name in owned_resources, f'Bundled external resource/datapack: {name}'
            if name.endswith('.json'):
                read_json(name)
        for name in classes:
            bytecode = jar.read(name)
            assert bytecode[:4] == b'\xca\xfe\xba\xbe', f'Invalid class: {name}'
            assert struct.unpack('>H', bytecode[6:8])[0] == int(pins['java_version']) + 44, f'Wrong class level: {name}'
        expected_mixins = ['spelunkery.mixins.json']
        if pins['loader'] == 'fabric':
            expected_mixins.append('spelunkery.fabric.mixins.json')
        assert {n for n in files if n.endswith('.mixins.json')} == set(expected_mixins), 'Unexpected mixin configuration'
        for name in expected_mixins:
            mixins = read_json(name)
            expected = next(json.loads((base / name).read_text()) for base in reversed(resource_roots)
                            if (base / name).is_file())
            assert mixins['required'] is True
            assert mixins['compatibilityLevel'] == 'JAVA_' + pins['java_version']
            assert mixins['package'] == expected['package']
            for side in ('mixins', 'client', 'server'):
                assert mixins.get(side, []) == expected.get(side, []), f'Changed active mixins: {name}/{side}'
                for mixin in mixins.get(side, []):
                    cls = (mixins['package'] + '.' + mixin).replace('.', '/') + '.class'
                    assert cls in files, f'Missing mixin class: {cls}'
        if pins['loader'] == 'fabric':
            assert 'META-INF/neoforge.mods.toml' not in files, 'Wrong loader metadata'
            meta = read_json('fabric.mod.json')
            assert meta['id'] == MOD_ID and meta['version'] == root_pins['mod_version']
            assert meta['name'] == config['mod_name']
            assert meta['depends']['minecraft'] == pins['minecraft_version']
            assert set(meta['depends']) == {'java', 'minecraft', 'fabricloader', 'architectury', 'fabric-api'}
            for dep, pin in (('java', 'java_version'), ('fabricloader', 'fabric_loader_version'),
                             ('architectury', 'architectury_api_version')):
                assert meta['depends'][dep] == '>=' + pins[pin], f'Wrong dependency: {dep}'
            assert meta.get('suggests', {}) == {} and meta.get('recommends', {}) == {}
            assert meta['mixins'] == expected_mixins and meta['environment'] == '*'
            assert meta['entrypoints'] == {
                'main': ['net.vg.spelunkery.fabric.SpelunkeryFabric'],
                'client': ['net.vg.spelunkery.fabric.client.SpelunkeryFabricClient'],
                'jei_mod_plugin': ['net.vg.spelunkery.compat.jei.SpelunkeryJeiPlugin'],
            }
            for names in meta['entrypoints'].values():
                for name in names:
                    assert name.replace('.', '/') + '.class' in files, f'Missing entrypoint: {name}'
            assert meta['icon'] in files
            if legacy:
                assert b'net/minecraft/class_' in jar.read(PACKAGE + 'Spelunkery.class'), 'Unremapped Fabric release'
        else:
            assert 'fabric.mod.json' not in files, 'Wrong loader metadata'
            meta = jar.read('META-INF/neoforge.mods.toml').decode()
            assert quoted(meta, 'modId') == MOD_ID and quoted(meta, 'version') == root_pins['mod_version']
            assert quoted(meta, 'displayName') == config['mod_name']
            assert PACKAGE + 'neoforge/SpelunkeryNeoForge.class' in files
            blocks = re.findall(r'\[\[dependencies\.' + re.escape(MOD_ID) + r'\]\](.*?)(?=\n\[|\Z)', meta, re.DOTALL)
            deps = {quoted(block, 'modId'): block for block in blocks}
            assert set(deps) == {'minecraft', 'neoforge', 'architectury'}
            assert quoted(deps['minecraft'], 'versionRange') == '[' + pins['minecraft_version'] + ']'
            for dep, pin in (('neoforge', 'neoforge_version'), ('architectury', 'architectury_api_version')):
                assert quoted(deps[dep], 'versionRange') == '[' + pins[pin] + ',)'
            assert all(quoted(block, 'type') == 'required' and quoted(block, 'side') == 'BOTH'
                       for block in deps.values())
            if tomllib is not None:
                tomllib.loads(meta)
            icon = 'iconFile' if pins['minecraft_version'] == '26.2' else 'logoFile'
            assert quoted(meta, icon) in files
            assert quoted(meta, 'config') == 'spelunkery.mixins.json'
        for name in ('assets/spelunkery/lang/en_us.json', 'data/spelunkery/recipe/foundry.json',
                     'data/spelunkery/worldgen/biome/marble_caves.json',
                     'data/spelunkery/worldgen/biome/crystal_caverns.json',
                     'data/spelunkery/worldgen/biome/fungal_grottos.json',
                     'data/spelunkery/worldgen/biome/magma_vaults.json'):
            assert name in files, f'Missing mod resource: {name}'
        assert jar.read('META-INF/services/mezz.jei.api.IModPlugin').decode().strip() == 'net.vg.spelunkery.compat.jei.SpelunkeryJeiPlugin'
        assert not any(name.endswith(('.jar', '.java')) for name in files), 'Embedded JAR or source archive'
        for name in ['fabric.mod.json', 'META-INF/neoforge.mods.toml', *expected_mixins]:
            if name in files:
                assert b'${' not in jar.read(name), f'Unexpanded metadata: {name}'
    print(f'{matrix.stem}: release metadata, classes, mixins and resources OK')
    return artifact


def inspect_existing(matrix, artifact):
    """Accept valid historical packaging without weakening the current matrix gate."""
    pins = properties(matrix)
    version = properties(ROOT / 'gradle.properties')['mod_version']
    with ZipFile(artifact) as jar:
        files = set(jar.namelist())
        assert len(files) == len(jar.namelist()), 'Duplicate public archive entries'
        assert PACKAGE + 'Spelunkery.class' in files, 'Existing file is not this mod'
        for name in files:
            if name.endswith('.class'):
                generated = bool(re.fullmatch(r'architectury_inject_' + re.escape(MOD_ID) +
                                             r'_common_[^/]+/PlatformMethods.class', name))
                assert name.startswith(PACKAGE) or generated, 'Bundled external dependency class: ' + name
                bytecode = jar.read(name)
                assert bytecode[:4] == b'\xca\xfe\xba\xbe'
                level = struct.unpack('>H', bytecode[6:8])[0]
                assert level <= int(pins['java_version']) + 44 if generated else level == int(pins['java_version']) + 44
        assert not any(name.endswith(('.jar', '.java')) for name in files), 'Embedded dependency/source archive'
        if pins['loader'] == 'fabric':
            assert 'META-INF/neoforge.mods.toml' not in files
            meta = json.loads(jar.read('fabric.mod.json'))
            assert meta['id'] == MOD_ID and meta['version'] == version and meta['environment'] == '*'
            assert meta['depends']['minecraft'] in (pins['minecraft_version'], '~' + pins['minecraft_version'])
            assert {'architectury', 'fabric-api'} <= set(meta['depends'])
            assert PACKAGE + 'fabric/SpelunkeryFabric.class' in files
        else:
            assert 'fabric.mod.json' not in files
            meta = jar.read('META-INF/neoforge.mods.toml').decode()
            assert quoted(meta, 'modId') == MOD_ID and quoted(meta, 'version') == version
            blocks = re.findall(r'\[\[dependencies\.' + re.escape(MOD_ID) + r'\]\](.*?)(?=\n\[|\Z)', meta, re.DOTALL)
            deps = {quoted(block, 'modId'): block for block in blocks}
            assert {'minecraft', 'neoforge', 'architectury'} <= set(deps)
            constraint = quoted(deps['minecraft'], 'versionRange')
            target = tuple(int(part) for part in pins['minecraft_version'].split('.'))
            if constraint != '[' + pins['minecraft_version'] + ']':
                bounds = re.fullmatch(r'([\[(])([0-9.]+),([0-9.]*)([\])])', constraint)
                assert bounds, 'Unrecognized historical Minecraft constraint: ' + constraint
                lower = tuple(int(part) for part in bounds[2].split('.'))
                assert target >= lower if bounds[1] == '[' else target > lower
                if bounds[3]:
                    upper = tuple(int(part) for part in bounds[3].split('.'))
                    assert target <= upper if bounds[4] == ']' else target < upper
            assert all(quoted(deps[name], 'type') == 'required' and quoted(deps[name], 'side') == 'BOTH'
                       for name in ('minecraft', 'neoforge', 'architectury'))
            assert PACKAGE + 'neoforge/SpelunkeryNeoForge.class' in files
        for config in [name for name in files if name.endswith('.mixins.json')]:
            mixins = json.loads(jar.read(config))
            assert mixins['package'].startswith(PACKAGE.replace('/', '.').rstrip('.'))
            assert mixins['compatibilityLevel'] == 'JAVA_' + pins['java_version']
            for side in ('mixins', 'client', 'server'):
                for name in mixins.get(side, []):
                    assert (mixins['package'] + '.' + name).replace('.', '/') + '.class' in files
        assert 'spelunkery.mixins.json' in files and 'assets/spelunkery/lang/en_us.json' in files
    print(f'{matrix.stem}: verified existing public mod/loader/Minecraft/Java identity')
    return Path(artifact)


if __name__ == '__main__':
    matrices = sorted((ROOT / 'gradle/matrix').glob('*.properties'))
    assert matrices, 'No matrix properties found'
    for matrix in matrices:
        try:
            inspect(matrix)
        except (AssertionError, KeyError, ValueError, OSError, BadZipFile) as error:
            raise SystemExit(f'{matrix.stem}: {error}') from error
    print(f'ARTIFACT VERIFICATION PASS ({len(matrices)}/{len(matrices)})')
