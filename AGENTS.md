# AGENTS.md

This file is a reusable workflow guide for agentic work in a typical Architectury/Fabric/NeoForge Minecraft mod repo.

Use it as the first file to load before exploring the tree.

## Goals

- Minimize context waste.
- Prefer fast search over broad file reading.
- Keep user work safe in dirty trees.
- Make changes loader-aware.
- End with a clean, inspectable commit history.

## Context Discipline

- Search first. Read second. Edit last.
- Prefer `rg --files` and `rg -n` over opening directories or large files blindly.
- Open only the exact files on the active path.
- Reuse known build/test commands instead of re-deriving them each turn.
- Summarize findings instead of repeating raw command output back to the user.

## Fast Search Workflow

### Find files

```bash
rg --files
```

### Find text or symbols

```bash
rg -n "pattern" common/src/main/java fabric/src/main/java neoforge/src/main/java
```

### Find assets or data

```bash
rg --files common/src/main/resources/assets common/src/main/resources/data
```

## Typical Minecraft Mod Layout

These are the first places to check in most multiplatform mod repos.

### Common code

- `common/src/main/java/...`
- shared registries
- shared blocks, items, menus, block entities, worldgen, recipes, screens

### Fabric code

- `fabric/src/main/java/...`
- Fabric-only bootstrap
- Fabric-only client hooks
- Fabric-only events and data hooks

### NeoForge code

- `neoforge/src/main/java/...`
- NeoForge-only bootstrap
- NeoForge-only client hooks
- NeoForge event bus registration

### Resources

- `common/src/main/resources/assets/<modid>/textures/block/`
- `common/src/main/resources/assets/<modid>/textures/item/`
- `common/src/main/resources/assets/<modid>/textures/gui/`
- `common/src/main/resources/assets/<modid>/models/block/`
- `common/src/main/resources/assets/<modid>/models/item/`
- `common/src/main/resources/assets/<modid>/blockstates/`
- `common/src/main/resources/data/<modid>/recipe/`
- `common/src/main/resources/data/<modid>/loot_table/`
- `common/src/main/resources/data/<modid>/worldgen/`
- `common/src/main/resources/data/<modid>/tags/`

## Loader Split Rule

If something works on one loader but not the other:

1. Check the shared implementation.
2. Check loader-specific bootstrap and client registration.
3. Do not assume Architectury abstraction is enough for every case.
4. If a shared helper is unstable on one loader, move that behavior into loader-native code.

Good examples:

- screen registration
- creative tab insertion
- render layer registration
- client predicates
- event wiring

## Common Debug Paths

### Screen/menu issue

Check:

- menu type registration
- menu open call
- client screen registration
- loader-specific screen event hooks

### Texture/model issue

Check:

- block/item model JSON
- blockstate JSON
- referenced texture path
- render layer if cutout/translucent behavior matters

Use `F3 + T` for texture/model reloads.

### Worldgen issue

Check:

- configured feature JSON
- placed feature JSON
- biome JSON generation-step wiring
- custom feature registration and custom feature code

### Recipe/book/UI issue

Check:

- menu class
- screen class
- recipe type registration
- recipe serializer/type wiring
- client-side category/filter hooks

## Dirty Worktree Rule

- Assume the tree is dirty unless proven otherwise.
- Never revert unrelated user changes.
- Treat modified PNGs, docs, and generated references as user-owned unless explicitly told otherwise.
- Stage only the files for the current fix.

Before committing, always inspect:

```bash
git status --short
```

## Compile/Test Workflow

For WSL-on-Windows or mixed-filesystem setups, prefer a mirror build to avoid path, lock, and Gradle cache issues.

### Reusable mirror compile loop

```bash
mirror=/tmp/mod-wsl
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
env GRADLE_USER_HOME=/tmp/mod-gradle-home \
    MOD_BUILD_ROOT=/tmp/mod-build \
    ./gradlew --project-cache-dir /tmp/mod-project-cache \
    --rerun-tasks \
    :common:processResources \
    :common:compileJava \
    :fabric:compileJava \
    :neoforge:compileJava
```

If a specific repo already has a known-good variant, prefer that exact command.

## Editing Rules

- Use the smallest patch that fixes the actual issue.
- Prefer loader-native fixes over forcing a shared abstraction when runtime behavior diverges.
- Avoid drive-by refactors unless they directly reduce future breakage on the active path.
- If you discover a reusable list or workflow, centralize it once instead of duplicating it in multiple loader files.

## Commit Workflow

Use the commit style the user has preferred in this repo:

1. Make one coherent change.
2. Run the relevant compile/test loop.
3. Stage only the files for that change.
4. Commit with a short conventional-style message.

Preferred commit shape:

- `fix: ...`
- `feat: ...`
- `refactor: ...`
- `style: ...`
- `docs: ...`
- `chore: ...`

Good examples:

- `fix: register foundry screen on neoforge`
- `fix: split vanilla creative tabs by loader`
- `docs: refresh wiki for current gameplay`

Avoid:

- giant mixed-purpose commits
- vague messages like `updates` or `misc fixes`
- committing user texture work unless explicitly requested

## Final Response Pattern

Keep closeout concise:

- say what changed
- point to the important file or two
- say what was verified
- call out anything not tested

Do not dump long terminal logs into the response.

## Repo-Local Appendix

These notes are specific to this repo and can be replaced in a new project.

### Current known-good mirror compile loop for this repo

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

### Current repo-specific caution

- This repo frequently has user-owned texture edits in `common/src/main/resources/assets/spelunkery/textures/`
- Do not stage or revert those files unless the user explicitly asks for that
