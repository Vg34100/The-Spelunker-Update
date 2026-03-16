# Spelunkery Update Implementation Plan

## Goal

Implement the Spelunkery update in phases so each phase is playable, testable, and commit-friendly without forcing the large cave biome work to block the rest of the mod.

## Working Principles

- Prefer data-driven worldgen and loot where possible.
- Keep shared logic in `common` and only use loader-specific code where worldgen hooks or platform APIs force it.
- Land one feature slice at a time with small commits and a test note for each slice.
- Use intermediate balancing passes instead of trying to perfect generation values upfront.

## Phase 1: Foundation Cleanup

- Standardize commit style going forward:
  - `feat: x`
  - `fix: x`
  - `docs: x`
  - `style: x`
- Add a lightweight docs workflow:
  - Keep feature intent in `docs/spelunkery-ideas.md`
  - Keep executable roadmap in `docs/spelunkery-implementation-plan.md`
- Decide whether to keep manual JSON authoring or introduce data generation later.
- Add a shared balancing note for ore count, vein size, and y-range targets.

## Phase 2: Finish the Base Ore Layer

### Tin Follow-Up

- Verify Fabric and NeoForge both generate tin in fresh chunks.
- Add creative tab placement if desired.
- Decide whether tin should expose XP values or stay mostly utility-focused.
- If bronze is approved, add:
  - bronze ingot
  - bronze block
  - bronze tools and/or armor
  - copper + tin alloy recipes

### Nickel

- Add nickel ore and deepslate nickel ore.
- Add raw nickel, nickel ingot, and storage block.
- Set nickel generation deeper and rarer than tin.
- Implement its first real use:
  - armor plating system, or
  - smithing-based durability upgrades

### Deliverable

- A stable overworld metal layer with clear roles:
  - tin = accessible alloy/utility metal
  - nickel = deeper upgrade metal

## Phase 3: Gem Framework

### Shared Systems

- Create a reusable gem registration pattern:
  - gem item
  - block/item assets
  - loot
  - recipes
  - tags
- Decide feature taxonomy:
  - geode
  - crystal pocket
  - ore vein

### Specific Content

- Keep amethyst geode-focused.
- Add topaz as a crystal pocket or geode feature.
- Add ruby and sapphire via a shared corundum-inspired system.
- Skip emerald geodes unless the design direction changes.

### Deliverable

- At least one new gem feature landed with a reusable structure for the others.

## Phase 4: Utility and Expedition Gear

### Torch Launcher

- Implement ranged torch placement.
- Decide ammo behavior:
  - consume torches from inventory, or
  - custom loaded torch cartridges
- Add placement validation and a short cooldown.

### Miner's Helmet

- Implement base helmet item.
- Add gem socket or infusion mechanic.
- Start with 2-3 buffs before expanding:
  - sapphire vision
  - ruby heat protection
  - topaz mining/navigation bonus

### Rope System

- Prototype a climbable rope block.
- Add rope deployment item that places a downward rope column until max length or obstruction.
- Decide whether rope uses anchor logic, gravity logic, or scaffold-like support rules.

### Potions

- Add `Spelunker's Brew`.
- Add `Miner's Potion`.
- Prototype effects with simple versions first, then refine:
  - ore reveal radius and update interval
  - mining speed scope and balance

### Deliverable

- A complete expedition utility layer that changes how cave exploration feels.

## Phase 5: Cave Biome Foundations

- Decide whether new cave biomes should rely on vanilla biome injection, custom regions, or a loader-bridged biome system.
- Build a shared decorative block library first:
  - `prismatic_stone`
  - `cinder_rock`
  - `scorched_dripstone`
  - `bioluminescent_moss`
  - `spore_bloom`
- Prototype biome-specific surface rules and block palettes before adding mob/ecosystem complexity.

## Phase 6: New Cave Biomes

### Crystal Caverns

- Add biome palette and ambient feel first.
- Then add crystal features and elevated gem generation.
- Keep water, calcite, and crystalline blocks central to the identity.

### Magma Vaults

- Establish unique overworld heat-biome visuals that do not read as copy-pasted Nether.
- Add heat blocks and hazard interactions.
- Integrate ruby or fire-aligned materials if approved.

### Fungal Grottos

- Focus on dense atmosphere, vegetation, and subtle light.
- Add fungal decorative content and moisture-heavy terrain.
- Consider a mild nickel bias only if it reinforces exploration routes.

## Phase 7: Balancing and Cohesion

- Review all ore generation values together rather than per-feature in isolation.
- Review overlap between helmet buffs, potions, and gems so each has a distinct role.
- Review cave biomes for visual separation and resource identity.
- Add advancement hooks, loot-table integration, and chest loot for expedition gear.

## Recommended Build Order

1. Nickel
2. Bronze or confirmed tin sink
3. One gem feature end-to-end
4. Torch launcher
5. Miner's helmet
6. Rope system
7. Potions
8. Decorative blocks for cave biomes
9. Crystal Caverns
10. Magma Vaults
11. Fungal Grottos

## Suggested Commit Strategy

- `docs: add spelunkery roadmap`
- `feat: add nickel ore`
- `feat: add bronze crafting`
- `feat: add topaz crystal pockets`
- `feat: add torch launcher`
- `feat: add miner helmet`
- `feat: add spelunker rope`
- `feat: add spelunker potions`
- `feat: add crystal caverns`

## Immediate Next Step

Pick one of these before more coding:

- Confirm tin's long-term role, especially whether bronze is in.
- Confirm whether ruby and sapphire should be separate player-facing gems or one corundum-derived family.
- Confirm the first utility feature after ores: torch launcher, helmet, rope, or potion.
