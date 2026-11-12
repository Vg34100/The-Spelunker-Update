# Compatibility Policy

## Decision Order

Use the first mechanism that fits.

### 1. Shared source

If the same code/resource works, leave it shared.

### 2. Local Stonecutter condition

Use for small API drift:

- import/package move
- method rename
- one changed argument
- short alternate call
- annotation/interface difference

### 3. Narrow deterministic replacement

Use only for truly mechanical drift.

Requirements:

- narrow scope
- deterministic
- behavior-preserving
- expected matches can be asserted

Avoid broad regex/package rewrites.

### 4. Parsed resource/data transform

Use when behavior is shared but serialized schema changed.

Examples:

- recipe representation
- loader metadata
- model/item-definition schema
- advancement/data-component formats

Parse structured data where practical and verify processed output.

### 5. Separate compatibility implementation

Use a real alternate file when the implementation or lifecycle materially differs.

Examples:

- renderer architecture
- menu/screen lifecycle
- entity render-state model
- fundamentally different mixin injection strategy

Keep the compatibility area small.

## Loader Differences

Minecraft-version differences and loader differences are separate axes.

Prefer Fabric-specific source for Fabric-only behavior and NeoForge-specific source for NeoForge-only behavior.

## Mixins

Compilation does not prove mixin correctness.

Verify:

- target class
- target method/descriptor
- injection point
- actual dispatch path
- whether the mixin should exist on the target

## Optional Integrations

Optional means optional.

- no accidental required metadata
- no accidental bundling
- dev runtimes may attach compatible optional mods
- test present/absent states when integration behavior changes

## Runtime ABI

When runtime libraries/loaders differ:

1. identify actual runtime versions
2. compare good/bad combinations
3. pin the narrow dependency when proven
4. do not contort application source around an unproven loader/library issue

## Durable Boundary Log

Record only proven reusable boundaries:

```text
Boundary:
Affected targets:
Affected files/system:
Mechanism:
Reason:
Validation:
```

Raw migration diaries do not belong here.

## Proven 1.20.1 / 26.3 boundaries

- 1.20.1 starts from the existing legacy source view. Its narrow delta is
  `gradle/compat/1.20.1.gradle`; different recipe serializers, item NBT storage,
  armor material and render-state access live in real compatibility Java files.
  Forge owns its entrypoint, event bus, client hooks and brewing recipes under
  `forge/`. Fabric's old brewing methods are static; shield bash uses its actual
  successful-block callback. Registry namespaces and saved machine/item keys stay fixed.
- 1.20.1 resources use plural data directories, pre-component recipe outputs,
  wrapped number providers and enchantment predicates. Its two added diamond
  feature resources preserve the 1.21 medium-diamond distribution under this
  mod's namespace. Forge biome modifiers use Forge's directory and type IDs.
- 26.3 retains the shared placement algorithms through explicit API substitutions
  in `gradle/compat/26.3.gradle`. Normalize Windows line endings before matching
  source anchors. Feature types register map codecs, biome sources
  expose a resolver, and tools use native item property factories. Blocks no
  longer provide dispatch codecs. Brewing uses three ordinary data recipes.
- Parsed 26.3 conversion flattens configured features, rewrites block states and
  placement offsets, and carries every loot condition/function into the new
  condition/modifier representation. Slab state predicates become `match_block`.
  Previously unknown crafting categories retain the older parser's `misc` fallback.
  JAR verification checks these shapes; native world checks prove drops and processing.
