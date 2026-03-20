# Topaz

## Overview

Topaz is a gem material found through a geode or crystal-pocket style cave feature.

It is intended to feel more special than a normal ore vein.

## Generation

- Generates as a geode-style underground feature in the overworld.
- Uses a rarity-based geode placement rather than a normal ore vein.
- Placement chance:
  - roughly `1` geode attempt per `30` chunks
- Vertical range:
  - minimum: `above bottom + 6`
  - maximum: `y=24`
- Uses a calcite and smooth basalt shell with a topaz interior in the current implementation.

## Collection

- Topaz geodes contain `Topaz Block` in the interior.
- Breaking topaz blocks normally drops `2-4 Topaz Shards`, with Fortune increasing the yield.
- Silk Touch preserves the block itself.
- Topaz is gathered from the geode interior rather than from a standard ore vein.

## Planned Uses

- Gem socketing for the miner's helmet
- Cave sensing and exploration utility
- Possible potion or navigation support
