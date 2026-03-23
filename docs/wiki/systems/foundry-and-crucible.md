# Foundry and Crucible

## Overview

The Spelunkery alloying system starts with a two-block setup:

- top block: `Foundry`
- bottom block: `Crucible`

The foundry will only process alloys when it is placed directly above a crucible that is filled with lava.

The current build uses a proper foundry menu with a recipe book, rather than the older right-click-only prototype.

## Crafting the Blocks

### Crucible

- pattern:
  - `###`
  - `#C#`
  - `###`
- `# = Deepslate Bricks`
- `C = Cauldron`

### Foundry

- pattern:
  - `#C#`
  - `CBC`
  - `#C#`
- `# = Deepslate Bricks`
- `C = Copper Ingot`
- `B = Blast Furnace`

## Filling the Crucible

- Right-click the crucible with a lava bucket to fill it
- Filling the crucible consumes the lava bucket and leaves an empty bucket
- The crucible stores `3` lava uses
- The filled state is shown visually on the top of the crucible

## Using the Foundry

- Place the foundry directly above the crucible
- Open the foundry UI
- Insert alloy ingredients into the three foundry input slots
- Use the foundry recipe book if you want ghost recipes and recipe placement help
- The foundry consumes crucible lava when the recipe completes
- Output is collected from the result slot in the foundry menu

## Current Alloy Recipes

- `Bronze`
  - `3 Copper Ingots`
  - `1 Tin Ingot`
  - output: `4 Bronze Ingots`
  - `process time: 200`
  - `lava cost: 1`

- `Electrum`
  - `1 Gold Ingot`
  - `1 Silver Ingot`
  - output: `2 Electrum Ingots`
  - `process time: 220`
  - `lava cost: 1`

- `Invar`
  - `1 Iron Ingot`
  - `1 Nickel Ingot`
  - output: `2 Invar Ingots`
  - `process time: 240`
  - `lava cost: 1`

- `Rose Gold`
  - `2 Gold Ingots`
  - `1 Copper Ingot`
  - output: `3 Rose Gold Ingots`
  - `process time: 220`
  - `lava cost: 1`

## Current Notes

- The foundry and crucible are intended as the core alloying line for the mod
- The system is menu-driven and has a working recipe book
- More alloys can be added onto the same structure later
