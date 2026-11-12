# Spelunker Update — 16-target port handoff

Updated 2026-11-09. The compatibility port and its automated acceptance are complete.
The mod version remains **1.0.2**. Changes remain uncommitted on `work`; no push,
tag, merge, platform publication or release-record replacement was performed.

## Target acceptance

| Added target | Java | Compile / package / strict JAR verification | Runtime evidence |
| --- | --- | --- | --- |
| 1.20.1 Fabric | 17 | PASS | Dedicated development server startup and clean stop; old Fabric brewing and shield mixins load. |
| 1.20.1 Forge | 17 | PASS | Final packaged client with required dependencies, then with optional JEI; native Forge world-test server checks below. |
| 26.3 Fabric | 25 | PASS | Windows resource processing matches all 350 packaged data JSON files. No separate client/server run claimed. |
| 26.3 NeoForge | 25 | PASS | Final packaged client; dedicated development server, resource reload, worldgen, loot and machine checks below. Windows resource processing also matches all 350 packaged data JSON files. |

The original twelve targets (1.21, 1.21.1, 26.1, 26.1.1, 26.1.2 and 26.2,
each Fabric/NeoForge) passed the complete compile/package regression gate.
Their clients were not all relaunched. The aggregate compile and package work
finished on 2026-10-19; the final strict verifier passed **16/16** on 2026-11-09.
Successful aggregate gates were reused rather than rebuilt after the interruption.

## Implementation boundaries

- Target registration and package-task selection discover `gradle/matrix/*.properties`.
  The existing Stonecutter build and legacy compatibility architecture remain in use.
- Native Forge owns its entrypoint, event bus, brewing and client hooks under `forge/`.
  1.20.1 extends the existing legacy view through `gradle/compat/1.20.1.gradle` and
  narrow Java implementations for genuinely different NBT, recipe, armor and render APIs.
  Fabric retains its native callbacks and mixins.
- 26.3 uses `gradle/compat/26.3.gradle`, native loader initialization and Architectury
  standalone `compileOnly()`. Registry codecs, item properties and biome resolvers
  adapt the shared behavior to the newer APIs. Existing targets keep their pipeline.
- Canonical gameplay source under `common/`, `fabric/` and `neoforge/` was preserved.
  Registry IDs and existing machine/item save keys remain the compatibility baseline.
  World downgrades and cross-version save transfers were not validated.
- Forge server fixtures are opt-in (`-Pport_tests`); their classes, metadata and
  datapack are excluded from release JARs. JEI remains optional and unbundled.

## Resource compatibility

- 1.20.1 receives plural data directories, pre-component recipe outputs, older
  enchantment predicates and wrapped number providers. Forge biome modifiers use
  native Forge directories/type IDs. Two namespaced diamond resources preserve the
  existing medium-diamond distribution absent from that vanilla version.
- 26.3 receives flattened feature resources, updated block-state/provider/offset
  formats, advancement recipe holder sets and three data-driven brewing recipes.
  Loot conditions/functions convert to the new condition/modifier representation;
  slab predicates use `match_block`. The old parser's crafting-category fallback
  remains `misc` where needed.
- The new targets omit one unused invalidly named backup PNG from their JARs.
  Original assets and the twelve existing target resource views remain intact.
- The strict JAR gate checks metadata, Java levels, remapping, classes, mixins,
  expected resources, JSON and target-specific data shapes. Native world checks
  independently verify representative processing, generation and drops.
- A Windows path-separator bug in the 26.3 feature destination was corrected on
  2026-11-09. Targeted native processing of both 26.3 loaders produced data JSON
  identical to the verified JARs, so no Java rebuild or repeat aggregate gate was needed.

## Gameplay and production evidence

Four focused native Forge 1.20.1 world-test groups passed:

- `machine_and_menu`: Foundry inventory/progress survives NBT save/load midway
  through processing; 3 copper + 1 tin yields 4 bronze, Crucible lava falls from
  3 to 2, and Foundry/Invar Anvil menu construction succeeds.
- `equipment_and_projectile`: all six helmet sockets survive serialization and
  apply effects; nickel's 20% durability upgrade persists; rose-gold glint,
  silver wither immunity, tonic effects and a spawned custom silver arrow work.
- `ore_and_cave_features`: tin ore and the custom giant cave mushroom place;
  the four custom biome registry entries exist.
- `loot_semantics`: normal tin ore drops raw tin, Silk Touch returns the ore,
  and a double marble slab drops two slabs.

The 26.3 NeoForge server reached normal-world startup, generated natural tin ore
in an untouched chunk, placed the custom cave mushroom and completed resource reload.
Command-driven checks confirmed normal/Silk Touch drops, double-slab drops and
actual Foundry bronze output with Crucible lava consumption. The server saved and
stopped cleanly. Foundry recipes remain intentionally unavailable to the vanilla
crafting recipe book; their own machine processing was demonstrated.

Final packaged client checks on 2026-11-09 verified SHA-256, the loaded class's
origin in the exact staged release JAR, initial resource reload, a deterministic
startup marker, and termination of only the owned game JVM:

| Packaged target | Runtime dependencies checked | SHA-256 |
| --- | --- | --- |
| 1.20.1 Forge | Architectury; repeated with JEI 15.74.0.225 present | `8323d0ab1c691e2a3f3a89bdc441d43808ee1e55f0dfcf1cb5a4294838336739` |
| 26.3 NeoForge | Architectury | `5203de4fa6219d0ef59d3ca14d4e8f96ac9ca47290bf693e33dce46df94362f7` |

The native Forge world tests and dedicated development server checks are separate
from packaged-client origin proof. Client startup with JEI does not claim an
in-world review of its recipe categories.

The publishing safety suite passed **23 tests**, including matrix-derived planning,
native Forge dependencies, Forge/NeoForge duplicate distinctions and resume guards.
All sixteen targets are recognized; required public dependencies remain loader
appropriate. Credentials and historical release records were untouched.

Ignored local evidence lives under `build/validation/spelunkery-port/`:
`artifacts.log`, `native-resources.json`, `native-resources.log`, and the
`*-required.json`, `*-jei.json` and `*-passed.txt` production-client receipts.
The receipts identify the isolated runtime directories containing detailed logs.
Earlier server logs remain under `runs/<target>/`; those are not release artifacts.

On 2026-11-09, all sixteen verified JARs were also made available in both existing
shared layouts: `../.builds/spelunkery/<target>/` and `../.builds/<target>/`.
All 32 destination copies match their source SHA-256. The four new targets were
added, and the differing 26.2 NeoForge copies were backed up under
`build/validation/spelunkery-port/shared-builds-backup/` before replacement.
The ignored staging receipt is `build/validation/spelunkery-port/shared-builds.json`.

## Remaining manual checks and deferred work

No deterministic port blocker remains. Manual play should still review terrain
distribution and visuals; machine/anvil GUI interaction; helmet/lens/compass
rendering; rope/bundle and torch-launcher use; bow/shield interactions; and JEI
categories inside a world. Automated coverage is representative rather than an
exhaustive playthrough of every feature or a launch of every target.

Release preparation, a new version decision, commits and publication are separate
work. The current outputs retain version 1.0.2 and are not a newly published release.

For future changes, use the existing matrix gates only after affected sentinels
are stable. Forge fixtures can be selected with
`-Pport_tests -Pport_test_cases=<case>` on `:1.20.1-forge:runPortTest`.
`-Pdev_integrations=false` omits development JEI; `-Prelease_smoke_jei=true`
explicitly stages JEI for an isolated packaged client. These flags do not change
publication dependencies.

## Release preparation — 1.0.3

Publishing to Modrinth, CurseForge and GitHub was authorized on 2026-11-11.
The user confirmed successful Prism startup on 1.20.1 Forge and 26.3 Fabric and
accepted representative coverage for this initial publication; detailed feature
playtesting remains deferred. The release uses only the supplied changelog text.

All sixteen 1.0.3 packages pass strict artifact verification. Comparing them with
the tested 1.0.2 builds shows identical classes and resources, with changes confined
to release metadata. A damaged local Forge entrypoint was recovered from this
task's saved source transformation; its compiled class matches the tested JAR.
The 26.3 source transform now normalizes Windows line endings before matching.
Completed Windows packages were retained when Windows command launching stopped;
the two remaining packages were built with the existing Linux Java tools.

Current preflight and publication evidence is kept under the ignored
`build/publishing/1.0.3/` directory. Platform upload receipts remain in
`build/publishing/1.0.3-uploads.jsonl`; previous 1.0.2 artifacts and receipts are preserved.
