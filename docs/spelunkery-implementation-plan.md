# Spelunkery Update Implementation Plan

## Goal

Implement the Spelunkery update in playable phases, starting with core ore and alloy systems, then moving into gems, expedition tools, and finally large cave biome content.

## Working Rules

- Keep common gameplay logic in `common`.
- Use loader-specific code only where platform hooks require it.
- Prefer simple, readable feature slices over giant system drops.
- Keep commits small and feature-based.
- Use these commit styles:
  - `feat: x`
  - `fix: x`
  - `docs: x`
  - `style: x`

## Phase 1: Stabilize Tin

- Verify tin worldgen and item flow on both Fabric and NeoForge.
- Add any missing creative tab placement and polish.
- Keep tin positioned as a utility metal rather than a pure combat progression metal.

## Phase 2: Add Nickel

- Add nickel ore and deepslate nickel ore.
- Add raw nickel, nickel ingot, and storage block.
- Set nickel deeper and rarer than tin.
- Add nickel plating through smithing as the first nickel use.
- Nickel plating should focus on durability or reinforcement.

## Phase 3: Add Silver

- Add silver ore and deepslate silver ore.
- Add raw silver, silver ingot, and storage block.
- Add silver weapons with double damage to undead.
- Add silver arrows.
- Add silver lining or plating with full wither immunity.

## Phase 4: Build the Alloying System

### Structure

- Add the `Crucible` block.
- Add the `Foundry` block.
- The foundry must sit directly above a valid crucible.
- Lava is consumed during alloy processing.
- Alloy recipes use ingots, not raw ores.

### Machine Behavior

- Start with a compact two-block multiblock only.
- Avoid pipes, tanks, or large tech-mod style mechanics.
- Make the foundry GUI readable and simple:
  - two or more alloy inputs as needed
  - fuel or heat state
  - output slot
- Surface the valid multiblock condition clearly to the player.

## Phase 5: Add Bronze

- Add bronze ingot and bronze block.
- Add the first bronze foundry recipe using copper and tin.
- Add bronze utility items before full equipment sets.

### Bronze Feature Targets

- bronze shield
- bronze compass or cave info item
- expedition utility parts

### Bronze Shield

- Implement the bash effect while blocking and moving into an incoming attacker.
- Add a cooldown to prevent spam or stunlock behavior.

### Bronze Info Tool

- Pick one first information item:
  - depth meter
  - bronze compass variant
  - survey tool

## Phase 6: Add Invar

- Add invar ingot and block.
- Add foundry recipe: iron plus nickel.
- Add invar armor or utility pieces with a stability or heat-resistant identity.
- Add the invar anvil.

### Invar Anvil Targets

- Degrades much more slowly than vanilla anvil.
- Serves as a premium workstation because of alloy cost.
- Later option:
  - add a modest enchanting or repair advantage if needed

## Phase 7: Add Rose Gold

- Add rose gold ingot and block.
- Add foundry recipe for rose gold.
- Add rose gold gear with improved enchantability and better durability than plain gold.
- Add rose gold plating through smithing.
- Add at least one rose gold information item.

### Rose Gold Feature Targets

- enchantability-focused tools or armor
- depth meter or cave-reading item
- creature detector or ore/prospecting device

## Phase 8: Add Electrum

- Add electrum ingot and block.
- Add foundry recipe after silver is available.
- Give electrum a premium enchanting identity.
- Bias electrum strongly toward rarer or more premium enchanting outcomes.
- Add electrum bow with 25% faster draw time.

## Phase 9: Add Gem Features

### Topaz

- Add topaz gem item and related assets.
- Implement topaz as a geode or crystal pocket feature.
- Topaz is the first recommended gem to build.

### Ruby

- Add ruby gem item and related assets.
- Implement ruby crystal pocket generation.

### Sapphire

- Add sapphire gem item and related assets.
- Implement sapphire crystal pocket generation.

### Amethyst

- Leave worldgen vanilla.
- Add new uses later if needed.

## Phase 10: Add Expedition Gear

### Miner's Helmet

- Add base helmet item.
- Add single-gem socket system.
- Add activation behavior with durability drain over time.
- Start with a small set of gem effects.

### Torch Launcher

- Add ranged torch placement item.
- Decide torch consumption and ammo behavior during implementation.

### Spelunker's Rope

- Add rope block or rope column system.
- Add deployable rope bundle item.
- Tune recoverability after the basic system works.

### Potions

- Add Spelunker's Brew with subtle nearby ore reveal.
- Add Miner's Potion as a mining-focused potion effect.

## Phase 11: Add Cave Biome Block Sets

- Add decorative and environmental blocks needed for later cave biomes.
- Candidate blocks:
  - prismatic stone
  - scorched dripstone
  - cinder rock
  - bioluminescent moss
  - spore bloom
  - mycelium mud

## Phase 12: Add New Cave Biomes

### Crystal Caverns

- Add biome palette and terrain identity first.
- Add crystal and gem feature overlap second.

### Magma Vaults

- Add overworld heat biome identity distinct from the Nether.
- Add hazards and supporting decorative blocks.

### Fungal Grottos

- Add damp overgrown cave identity with glowing plant life.
- Add biome-specific decorative blocks and atmosphere.

## Cross-Cutting Balancing Work

- Tune ore counts, vein sizes, and y-level ranges together.
- Keep each metal and alloy focused on a distinct niche.
- Make utility gear worthwhile without making it mandatory over late-game armor.
- Keep new cave rewards meaningful without turning exploration into constant wallhack-style sensing.

## Recommended Near-Term Order

1. nickel
2. silver
3. foundry and crucible
4. bronze
5. invar
6. rose gold
7. electrum
8. topaz
9. ruby
10. sapphire
11. miner's helmet
12. torch launcher
13. rope
14. potions
15. cave biome block sets
16. cave biomes

## Suggested Commit Sequence

- `feat: add nickel ore`
- `feat: add silver ore`
- `feat: add foundry and crucible`
- `feat: add bronze alloy`
- `feat: add invar alloy`
- `feat: add rose gold alloy`
- `feat: add electrum alloy`
- `feat: add topaz pockets`
- `feat: add ruby pockets`
- `feat: add sapphire pockets`
- `feat: add miner helmet`
- `feat: add torch launcher`
- `feat: add spelunker rope`
- `feat: add spelunker potions`

## Immediate Next Step

Build the next metal slice before moving into gems or cave biomes:

- nickel first if you want to keep strengthening the ore foundation
- silver first if you want to unlock the full alloy roadmap sooner
