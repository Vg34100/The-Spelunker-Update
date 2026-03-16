# Spelunkery Update Ideas

## Design Pillars

- Keep the mod grounded in cave exploration first, not generic fantasy loot.
- Prefer real geological inspiration, then bend reality only when it clearly improves game feel.
- Add progression in layers: ores and gems first, traversal and utility second, large biome systems third.
- Make each addition answer one of these needs: better reasons to explore caves, better tools for cave play, or more distinct cave identity.

## Reality Check and World Logic

### Amethyst

- Amethyst geodes already make strong real-world sense because amethyst is a quartz variety commonly associated with cavity growth.
- A normal amethyst ore is not the best fit. Keeping amethyst as a geode-focused material is cleaner and more believable.
- If vanilla amethyst needs expansion, prefer richer geodes, amethyst shards, or new amethyst cave interactions over adding plain stone ore.

### Emerald

- Emerald is a gemstone, but emerald geodes are not a standard real-world geological occurrence.
- Recommendation: do not add emerald geodes.
- If emerald gets attention, make it a rare vein or crystal-pocket style feature tied to specific rock contexts instead of a geode.

### Ruby and Sapphire

- Ruby and sapphire are both corundum in real life, just with different trace elements.
- Recommendation: treat them as related materials, not unrelated gem systems.
- Best realism-friendly version: a shared corundum-bearing ore or crystal deposit that can yield ruby or sapphire depending on biome, depth, or feature variant.
- If game clarity matters more than realism, separate ruby and sapphire items are still fine, but their worldgen should feel related.

### Topaz

- Topaz is more plausible in crystal pockets, granite-associated deposits, and cavity-adjacent formation than as a generic everywhere ore.
- Recommendation: topaz works well as a rare geode-like or crystal-pocket feature, especially in a more crystalline cave biome.

## Feature A: Gems and Geodes

### Recommended Direction

- Keep vanilla amethyst geodes as the baseline gem feature.
- Add topaz geodes or crystal pockets.
- Do not add emerald geodes.
- Add ruby and sapphire through a shared corundum system rather than four independent gem geologies.

### Proposed Gem Set

- `amethyst`
  - Keep geode-only.
  - Expand uses later if needed.
- `topaz`
  - Add crystal pocket or geode variant.
  - Optional ore only if tied to granite or pegmatite-like host rock.
- `ruby`
  - Add as a rare corundum-derived gem.
  - Could bias warmer/deeper cave regions.
- `sapphire`
  - Add as a rare corundum-derived gem.
  - Could bias cooler/wetter cave regions.
- `emerald`
  - Leave vanilla ore structure intact unless we intentionally redesign emerald generation.

### Suggested Worldgen Framing

- `geode` means an enclosed cavity feature with inner crystal growth, close to vanilla amethyst.
- `crystal pocket` means a smaller, irregular stone cavity with clusters and loose gem-bearing blocks.
- `ore` means a standard host-rock replacement vein.

### Suggested Use Cases

- Ruby: heat resistance and offensive utility.
- Sapphire: vision, clarity, or navigation utility.
- Topaz: mining luck, treasure-finding, or mobility-adjacent utility.
- Amethyst: resonance, sensing, or potion/alchemy support.

## Feature B: More Ores

### Tin

- Tin is already in and should become the early expedition metal.
- Best use recommendation: pair tin with copper to make bronze.
- Bronze gives tin a strong identity and creates a believable early-mid progression path.
- Bronze can later support tools, armor, lantern hardware, rope anchors, and helmet upgrades.

### Nickel

- Nickel should be deeper and rarer than tin.
- Good niche: reinforcement, plating, durability upgrades, and corrosion-resistant metalwork.
- Recommendation: use nickel for armor plating through smithing or a custom workstation, not as a full parallel armor set unless needed later.

### Suggested Ore Identity

- Tin: accessible, practical, broad crafting value.
- Nickel: deeper, rarer, utility-heavy, upgrade-oriented.

## Feature C: Quality of Life

### Torch Launcher

- Strong fit for the mod.
- Should prioritize convenience, not combat.
- Best implementation is likely a utility item that places torches on valid surfaces at range.
- Desirable constraints:
  - Requires torches as ammo or consumes torches from inventory.
  - Short cooldown.
  - Fails cleanly if no valid face is found.

### Miner's Helmet

- Strong thematic fit.
- Should be intentionally weak as armor but valuable for utility.
- Recommendation: one base helmet item plus gem socket upgrades rather than separate full helmet items for every gem.
- Example buffs:
  - Sapphire: strong night vision or improved darkness handling.
  - Ruby: fire resistance or lava-adjacent protection.
  - Topaz: mining speed, ore awareness, or navigation support.
  - Amethyst: mob or ore sensing, or a subtle echo-location style effect.

### Spelunker's Rope

- Excellent fit if it behaves like a cave-specific traversal tool rather than just reskinned scaffolding.
- Good direction: throwable rope anchor that creates a vertical climbable line downward until blocked or max length.
- It should be easier to deploy in shafts than ladders, but less flexible for building.

### Potions

- `Spelunker's Brew`
  - Highlights nearby ores.
  - Best implemented as a custom effect that temporarily applies a visual outline or glow-like reveal to ore blocks in range.
  - Should be limited radius and duration to avoid replacing exploration entirely.
- `Miner's Potion`
  - Essentially a cave-focused haste effect.
  - Recommendation: apply increased mining speed only to stone-like and ore-like blocks if feasible, otherwise use a weaker global mining buff.

## Feature D: New Cave Biomes

### Crystal Caverns

- Strongest candidate from the list.
- Recommended palette:
  - calcite
  - smooth basalt
  - water pools
  - crystalline decorative block such as `prismatic_stone`
- Gem rates can be slightly elevated here for topaz and corundum-derived gems.
- This is the most natural place for crystal pockets.

### Magma Vaults

- Good contrast biome, but easy to drift too close to Nether visuals.
- Recommendation: keep it clearly overworld by introducing at least one or two unique blocks.
- Candidate blocks:
  - `scorched_dripstone`
  - `cinder_rock`
  - `ashen_tuff` or similar
- Ruby and heat-oriented features fit here better than sapphire or topaz.

### Fungal Grottos

- Strong exploration biome if it stays damp, overgrown, and dim rather than turning into a generic mushroom biome.
- Recommended palette:
  - mycelium mud
  - moss
  - rooted dirt
  - glow lichen
  - hanging vines
  - bioluminescent moss or `spore_bloom`
- Nickel can appear here, but only if the rationale is "mineral-rich damp environment" rather than random placement.

## Suggested Additions and Adjustments

- Add `bronze` as the main reason tin matters.
- Make ruby and sapphire a shared `corundum` family under the hood even if the player-facing items stay separate.
- Treat geodes and crystal pockets as different feature types instead of making every gem use the same structure.
- Keep emerald vanilla-like unless there is a very strong design reason to rework it.
- Ship traversal and utility items before giant biome overhauls if momentum matters.

## Open Questions

- Do you want bronze to be the main tin sink, or do you want tin to stay mostly utility-focused?
- Should ruby and sapphire be fully separate materials to the player, or visibly linked through a corundum deposit system?
- Do you want the miner's helmet buffs to be passive while worn, or activated by socketed upgrades with durability/cooldown costs?
- Should the rope be recoverable after use, partially recoverable, or a consumable expedition tool?
- For ore highlighting, do you want a subtle nearby reveal, or a stronger x-ray-like effect with real balance costs?
