# AGENTS.md

Use this file as the fast-path for working in this repo. Load this before wandering the tree.

## Priorities

- Do not revert or overwrite unrelated user edits.
- The worktree is often dirty with active texture work. Treat PNG changes as user-owned unless the user explicitly asks you to edit them.
- Prefer the smallest amount of context needed: search first, then open only the exact files you need.

## Fast Search

- Find files: `rg --files`
- Find symbols/text: `rg -n "pattern" common/src/main/java fabric/src/main/java neoforge/src/main/java`
- Find textures/models/data: `rg --files common/src/main/resources/assets/spelunkery common/src/main/resources/data/spelunkery`

## Where Things Live

### Common Java

- Main mod init: `common/src/main/java/net/vg/spelunkery/Spelunkery.java`
- Registries: `common/src/main/java/net/vg/spelunkery/registry/`
- Blocks/items/worldgen/features/menus/screens live under:
  - `common/src/main/java/net/vg/spelunkery/block/`
  - `common/src/main/java/net/vg/spelunkery/item/`
  - `common/src/main/java/net/vg/spelunkery/worldgen/`
  - `common/src/main/java/net/vg/spelunkery/menu/`
  - `common/src/main/java/net/vg/spelunkery/client/screen/`

### Loader-Specific Java

- Fabric init: `fabric/src/main/java/net/vg/spelunkery/fabric/`
- NeoForge init: `neoforge/src/main/java/net/vg/spelunkery/neoforge/`
- NeoForge client hooks: `neoforge/src/main/java/net/vg/spelunkery/neoforge/client/`
- If something works on Fabric but not NeoForge, inspect the loader-specific init/client files first.

### Assets and Data

- Block textures: `common/src/main/resources/assets/spelunkery/textures/block/`
- Item textures: `common/src/main/resources/assets/spelunkery/textures/item/`
- GUI textures: `common/src/main/resources/assets/spelunkery/textures/gui/`
- Block models: `common/src/main/resources/assets/spelunkery/models/block/`
- Item models: `common/src/main/resources/assets/spelunkery/models/item/`
- Blockstates: `common/src/main/resources/assets/spelunkery/blockstates/`
- Loot tables: `common/src/main/resources/data/spelunkery/loot_table/`
- Recipes: `common/src/main/resources/data/spelunkery/recipe/`
- Worldgen JSON: `common/src/main/resources/data/spelunkery/worldgen/`
- Tags: `common/src/main/resources/data/spelunkery/tags/`

### Docs

- Wiki docs source: `docs/wiki/`
- Modrinth front-page draft: `docs/wiki/modrinth-front-page.md`

## Dirty Worktree Warning

Expect many modified PNGs and some untracked reference textures. Do not clean them up unless asked.

Known recurring user-owned texture/reference files include:

- `common/src/main/resources/assets/spelunkery/textures/block/stone.png`
- `common/src/main/resources/assets/spelunkery/textures/block/deepslate.png`
- `common/src/main/resources/assets/spelunkery/textures/block/iron_block.png`
- `common/src/main/resources/assets/spelunkery/textures/block/emerald_block.png`
- `common/src/main/resources/assets/spelunkery/textures/item/iron_ingot.png`
- `common/src/main/resources/assets/spelunkery/textures/item/raw_iron.png`
- `common/src/main/resources/assets/spelunkery/textures/item/raw_gold.png`
- `common/src/main/resources/assets/spelunkery/textures/item/raw_copper.png`

## WSL Compile Loop

Use a WSL mirror build to avoid Windows path and file-lock issues. This is the standard compile check.

```bash
mirror=/tmp/spelunkery-wsl
rm -rf "$mirror"
mkdir -p "$mirror"
rsync -a --delete \
  --exclude '.git' \
  --exclude '.gradle' \
  --exclude 'build' \
  --exclude 'fabric/run' \
  --exclude 'neoforge/run' \
  ./ "$mirror"/
cd "$mirror"
env GRADLE_USER_HOME=/tmp/spelunkery-gradle-home \
    SPELUNKERY_BUILD_ROOT=/tmp/spelunkery-build \
    ./gradlew --project-cache-dir /tmp/spelunkery-project-cache \
    --rerun-tasks \
    :common:processResources \
    :common:compileJava \
    :fabric:compileJava \
    :neoforge:compileJava
```

## Typical Debug Path

### Fabric-only or NeoForge-only bug

1. Check the common implementation.
2. Check the loader-specific init/client registration.
3. Search for loader-specific screen, menu, creative tab, render layer, or event registration.

### Texture or model issue

1. Check block/item model JSON.
2. Check blockstate JSON.
3. Check the PNG path actually referenced by the model.
4. For in-game refresh, use `F3 + T`.

### Worldgen issue

1. Check configured feature JSON.
2. Check placed feature JSON.
3. Check biome JSON generation step wiring.
4. If custom feature code is involved, inspect the registered feature in `registry/SpelunkeryFeatures.java`.

## Context Efficiency Rules

- Do not open whole directories blindly.
- Do not re-read giant files if `rg` can narrow the target first.
- Prefer inspecting only the 1-3 files on the relevant path.
- When reporting back, summarize rather than dumping command output.
