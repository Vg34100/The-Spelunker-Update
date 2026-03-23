package net.vg.spelunkery.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.vg.spelunkery.Spelunkery;

import java.util.function.Consumer;

public final class SpelunkeryCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Spelunkery.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> SPELUNKERY_TAB = TABS.register(
            "spelunkery",
            () -> CreativeTabRegistry.create(builder -> builder
                    .title(Component.translatable("itemGroup.spelunkery"))
                    .icon(() -> new ItemStack(SpelunkeryBlocks.TOPAZ_BLOCK.get()))
                    .displayItems((parameters, output) -> {
                        addBlockEntries(output::accept);
                        addAllItemEntries(output::accept);
                    }))
    );

    private static boolean initialized;

    private SpelunkeryCreativeTabs() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        TABS.register();
    }

    private static void acceptEntries(Consumer<ItemLike> output, ItemLike... entries) {
        for (ItemLike entry : entries) {
            output.accept(entry);
        }
    }

    public static void addNaturalBlockEntries(Consumer<ItemLike> output) {
        acceptEntries(output,
                SpelunkeryBlocks.TIN_ORE.get(),
                SpelunkeryBlocks.DEEPSLATE_TIN_ORE.get(),
                SpelunkeryBlocks.SILVER_ORE.get(),
                SpelunkeryBlocks.DEEPSLATE_SILVER_ORE.get(),
                SpelunkeryBlocks.NICKEL_ORE.get(),
                SpelunkeryBlocks.DEEPSLATE_NICKEL_ORE.get(),
                SpelunkeryBlocks.TOPAZ_BLOCK.get(),
                SpelunkeryBlocks.RUBY_BLOCK.get(),
                SpelunkeryBlocks.SAPPHIRE_BLOCK.get(),
                SpelunkeryBlocks.MARBLE.get(),
                SpelunkeryBlocks.GEM_POCKET.get(),
                SpelunkeryBlocks.PRISMATIC_STONE.get(),
                SpelunkeryBlocks.CINDER_ROCK.get(),
                SpelunkeryBlocks.ASHEN_DIRT.get(),
                SpelunkeryBlocks.MYCELIUM_MUD.get(),
                SpelunkeryBlocks.BIOLUMINESCENT_MOSS.get(),
                SpelunkeryBlocks.BLUE_CRYSTAL.get(),
                SpelunkeryBlocks.CRUCIBLE.get(),
                SpelunkeryBlocks.FOUNDRY.get()
        );
    }

    public static void addBuildingBlockEntries(Consumer<ItemLike> output) {
        acceptEntries(output,
                SpelunkeryBlocks.TIN_BLOCK.get(),
                SpelunkeryBlocks.RAW_TIN_BLOCK.get(),
                SpelunkeryBlocks.SILVER_BLOCK.get(),
                SpelunkeryBlocks.RAW_SILVER_BLOCK.get(),
                SpelunkeryBlocks.NICKEL_BLOCK.get(),
                SpelunkeryBlocks.RAW_NICKEL_BLOCK.get(),
                SpelunkeryBlocks.MARBLE.get(),
                SpelunkeryBlocks.MARBLE_STAIRS.get(),
                SpelunkeryBlocks.MARBLE_SLAB.get(),
                SpelunkeryBlocks.MARBLE_WALL.get(),
                SpelunkeryBlocks.POLISHED_MARBLE.get(),
                SpelunkeryBlocks.POLISHED_MARBLE_STAIRS.get(),
                SpelunkeryBlocks.POLISHED_MARBLE_SLAB.get(),
                SpelunkeryBlocks.POLISHED_MARBLE_WALL.get(),
                SpelunkeryBlocks.MARBLE_BRICKS.get(),
                SpelunkeryBlocks.MARBLE_BRICK_STAIRS.get(),
                SpelunkeryBlocks.MARBLE_BRICK_SLAB.get(),
                SpelunkeryBlocks.MARBLE_BRICK_WALL.get(),
                SpelunkeryBlocks.MARBLE_TILES.get(),
                SpelunkeryBlocks.MARBLE_TILE_STAIRS.get(),
                SpelunkeryBlocks.MARBLE_TILE_SLAB.get(),
                SpelunkeryBlocks.MARBLE_TILE_WALL.get(),
                SpelunkeryBlocks.PRISMATIC_STONE.get(),
                SpelunkeryBlocks.PRISMATIC_STONE_STAIRS.get(),
                SpelunkeryBlocks.PRISMATIC_STONE_SLAB.get(),
                SpelunkeryBlocks.PRISMATIC_STONE_WALL.get(),
                SpelunkeryBlocks.POLISHED_PRISMATIC_STONE.get(),
                SpelunkeryBlocks.POLISHED_PRISMATIC_STONE_STAIRS.get(),
                SpelunkeryBlocks.POLISHED_PRISMATIC_STONE_SLAB.get(),
                SpelunkeryBlocks.POLISHED_PRISMATIC_STONE_WALL.get(),
                SpelunkeryBlocks.PRISMATIC_BRICKS.get(),
                SpelunkeryBlocks.PRISMATIC_BRICK_STAIRS.get(),
                SpelunkeryBlocks.PRISMATIC_BRICK_SLAB.get(),
                SpelunkeryBlocks.PRISMATIC_BRICK_WALL.get(),
                SpelunkeryBlocks.PRISMATIC_TILES.get(),
                SpelunkeryBlocks.PRISMATIC_TILE_STAIRS.get(),
                SpelunkeryBlocks.PRISMATIC_TILE_SLAB.get(),
                SpelunkeryBlocks.PRISMATIC_TILE_WALL.get(),
                SpelunkeryBlocks.BRONZE_BLOCK.get(),
                SpelunkeryBlocks.BRONZE_TILES.get(),
                SpelunkeryBlocks.BRONZE_BARS.get(),
                SpelunkeryBlocks.BRONZE_CHAIN.get(),
                SpelunkeryBlocks.BRONZE_LANTERN.get(),
                SpelunkeryBlocks.INVAR_BLOCK.get(),
                SpelunkeryBlocks.INVAR_TILES.get(),
                SpelunkeryBlocks.INVAR_ANVIL.get(),
                SpelunkeryBlocks.CHIPPED_INVAR_ANVIL.get(),
                SpelunkeryBlocks.DAMAGED_INVAR_ANVIL.get(),
                SpelunkeryBlocks.ROSE_GOLD_BLOCK.get(),
                SpelunkeryBlocks.ROSE_GOLD_TILES.get(),
                SpelunkeryBlocks.ELECTRUM_BLOCK.get(),
                SpelunkeryBlocks.ELECTRUM_TILES.get(),
                SpelunkeryBlocks.CINDER_ROCK.get(),
                SpelunkeryBlocks.CINDER_ROCK_STAIRS.get(),
                SpelunkeryBlocks.CINDER_ROCK_SLAB.get(),
                SpelunkeryBlocks.CINDER_ROCK_WALL.get(),
                SpelunkeryBlocks.POLISHED_CINDER.get(),
                SpelunkeryBlocks.POLISHED_CINDER_STAIRS.get(),
                SpelunkeryBlocks.POLISHED_CINDER_SLAB.get(),
                SpelunkeryBlocks.POLISHED_CINDER_WALL.get(),
                SpelunkeryBlocks.CINDER_BRICKS.get(),
                SpelunkeryBlocks.CINDER_BRICK_STAIRS.get(),
                SpelunkeryBlocks.CINDER_BRICK_SLAB.get(),
                SpelunkeryBlocks.CINDER_BRICK_WALL.get(),
                SpelunkeryBlocks.CINDER_TILES.get(),
                SpelunkeryBlocks.CINDER_TILE_STAIRS.get(),
                SpelunkeryBlocks.CINDER_TILE_SLAB.get(),
                SpelunkeryBlocks.CINDER_TILE_WALL.get(),
                SpelunkeryBlocks.MYCELIUM_MUD.get(),
                SpelunkeryBlocks.MYCELIUM_MUD_BRICKS.get(),
                SpelunkeryBlocks.MYCELIUM_MUD_BRICK_STAIRS.get(),
                SpelunkeryBlocks.MYCELIUM_MUD_BRICK_SLAB.get(),
                SpelunkeryBlocks.MYCELIUM_MUD_BRICK_WALL.get()
        );
    }

    public static void addIngredientEntries(Consumer<ItemLike> output) {
        acceptEntries(output,
                SpelunkeryItems.RAW_TIN.get(),
                SpelunkeryItems.TIN_INGOT.get(),
                SpelunkeryItems.RAW_SILVER.get(),
                SpelunkeryItems.SILVER_INGOT.get(),
                SpelunkeryItems.RAW_NICKEL.get(),
                SpelunkeryItems.NICKEL_INGOT.get(),
                SpelunkeryItems.TOPAZ_SHARD.get(),
                SpelunkeryItems.RUBY.get(),
                SpelunkeryItems.SAPPHIRE.get(),
                SpelunkeryItems.BRONZE_INGOT.get(),
                SpelunkeryItems.INVAR_INGOT.get(),
                SpelunkeryItems.ROSE_GOLD_INGOT.get(),
                SpelunkeryItems.ELECTRUM_INGOT.get(),
                SpelunkeryItems.NICKEL_PLATING.get(),
                SpelunkeryItems.SILVER_LINING.get(),
                SpelunkeryItems.ROSE_GOLD_FILIGREE.get()
        );
    }

    public static void addToolsAndUtilityEntries(Consumer<ItemLike> output) {
        acceptEntries(output,
                SpelunkeryItems.BRONZE_COMPASS.get(),
                SpelunkeryItems.BRONZE_SHIELD.get(),
                SpelunkeryItems.MINERS_HELMET.get(),
                SpelunkeryItems.PROSPECTOR_LENS.get(),
                SpelunkeryItems.TORCH_LAUNCHER.get(),
                SpelunkeryItems.ROPE_BUNDLE.get(),
                SpelunkeryItems.ROPE.get(),
                SpelunkeryItems.ELECTRUM_BOW.get()
        );
    }

    public static void addCombatEntries(Consumer<ItemLike> output) {
        acceptEntries(output,
                SpelunkeryItems.SILVER_ARROW.get(),
                SpelunkeryItems.SILVER_SWORD.get(),
                SpelunkeryItems.BRONZE_SHIELD.get(),
                SpelunkeryItems.MINERS_HELMET.get(),
                SpelunkeryItems.ELECTRUM_BOW.get(),
                SpelunkeryItems.BRONZE_SWORD.get(),
                SpelunkeryItems.SILVER_HELMET.get(),
                SpelunkeryItems.INVAR_HELMET.get(),
                SpelunkeryItems.ROSE_GOLD_HELMET.get()
        );
    }

    public static void addFoodAndDrinkEntries(Consumer<ItemLike> output) {
        acceptEntries(output,
                SpelunkeryItems.SPELUNKERS_BREW.get(),
                SpelunkeryItems.DANGERSENSE_TONIC.get(),
                SpelunkeryItems.MINERS_TONIC.get()
        );
    }

    private static void addBlockEntries(Consumer<ItemLike> output) {
        acceptEntries(output,
                SpelunkeryBlocks.TIN_ORE.get(),
                SpelunkeryBlocks.DEEPSLATE_TIN_ORE.get(),
                SpelunkeryBlocks.RAW_TIN_BLOCK.get(),
                SpelunkeryBlocks.TIN_BLOCK.get(),
                SpelunkeryBlocks.SILVER_ORE.get(),
                SpelunkeryBlocks.DEEPSLATE_SILVER_ORE.get(),
                SpelunkeryBlocks.RAW_SILVER_BLOCK.get(),
                SpelunkeryBlocks.SILVER_BLOCK.get(),
                SpelunkeryBlocks.NICKEL_ORE.get(),
                SpelunkeryBlocks.DEEPSLATE_NICKEL_ORE.get(),
                SpelunkeryBlocks.RAW_NICKEL_BLOCK.get(),
                SpelunkeryBlocks.NICKEL_BLOCK.get(),
                SpelunkeryBlocks.TOPAZ_BLOCK.get(),
                SpelunkeryBlocks.RUBY_BLOCK.get(),
                SpelunkeryBlocks.SAPPHIRE_BLOCK.get(),
                SpelunkeryBlocks.MARBLE.get(),
                SpelunkeryBlocks.GEM_POCKET.get(),
                SpelunkeryBlocks.MARBLE_STAIRS.get(),
                SpelunkeryBlocks.MARBLE_SLAB.get(),
                SpelunkeryBlocks.MARBLE_WALL.get(),
                SpelunkeryBlocks.POLISHED_MARBLE.get(),
                SpelunkeryBlocks.POLISHED_MARBLE_STAIRS.get(),
                SpelunkeryBlocks.POLISHED_MARBLE_SLAB.get(),
                SpelunkeryBlocks.POLISHED_MARBLE_WALL.get(),
                SpelunkeryBlocks.MARBLE_BRICKS.get(),
                SpelunkeryBlocks.MARBLE_BRICK_STAIRS.get(),
                SpelunkeryBlocks.MARBLE_BRICK_SLAB.get(),
                SpelunkeryBlocks.MARBLE_BRICK_WALL.get(),
                SpelunkeryBlocks.MARBLE_TILES.get(),
                SpelunkeryBlocks.MARBLE_TILE_STAIRS.get(),
                SpelunkeryBlocks.MARBLE_TILE_SLAB.get(),
                SpelunkeryBlocks.MARBLE_TILE_WALL.get(),
                SpelunkeryBlocks.PRISMATIC_STONE.get(),
                SpelunkeryBlocks.PRISMATIC_STONE_STAIRS.get(),
                SpelunkeryBlocks.PRISMATIC_STONE_SLAB.get(),
                SpelunkeryBlocks.PRISMATIC_STONE_WALL.get(),
                SpelunkeryBlocks.POLISHED_PRISMATIC_STONE.get(),
                SpelunkeryBlocks.POLISHED_PRISMATIC_STONE_STAIRS.get(),
                SpelunkeryBlocks.POLISHED_PRISMATIC_STONE_SLAB.get(),
                SpelunkeryBlocks.POLISHED_PRISMATIC_STONE_WALL.get(),
                SpelunkeryBlocks.PRISMATIC_BRICKS.get(),
                SpelunkeryBlocks.PRISMATIC_BRICK_STAIRS.get(),
                SpelunkeryBlocks.PRISMATIC_BRICK_SLAB.get(),
                SpelunkeryBlocks.PRISMATIC_BRICK_WALL.get(),
                SpelunkeryBlocks.PRISMATIC_TILES.get(),
                SpelunkeryBlocks.PRISMATIC_TILE_STAIRS.get(),
                SpelunkeryBlocks.PRISMATIC_TILE_SLAB.get(),
                SpelunkeryBlocks.PRISMATIC_TILE_WALL.get(),
                SpelunkeryBlocks.BLUE_CRYSTAL.get(),
                SpelunkeryBlocks.GREEN_CRYSTAL.get(),
                SpelunkeryBlocks.RED_CRYSTAL.get(),
                SpelunkeryBlocks.YELLOW_CRYSTAL.get(),
                SpelunkeryBlocks.CINDER_ROCK.get(),
                SpelunkeryBlocks.CINDER_ROCK_STAIRS.get(),
                SpelunkeryBlocks.CINDER_ROCK_SLAB.get(),
                SpelunkeryBlocks.CINDER_ROCK_WALL.get(),
                SpelunkeryBlocks.POLISHED_CINDER.get(),
                SpelunkeryBlocks.POLISHED_CINDER_STAIRS.get(),
                SpelunkeryBlocks.POLISHED_CINDER_SLAB.get(),
                SpelunkeryBlocks.POLISHED_CINDER_WALL.get(),
                SpelunkeryBlocks.CINDER_BRICKS.get(),
                SpelunkeryBlocks.CINDER_BRICK_STAIRS.get(),
                SpelunkeryBlocks.CINDER_BRICK_SLAB.get(),
                SpelunkeryBlocks.CINDER_BRICK_WALL.get(),
                SpelunkeryBlocks.CINDER_TILES.get(),
                SpelunkeryBlocks.CINDER_TILE_STAIRS.get(),
                SpelunkeryBlocks.CINDER_TILE_SLAB.get(),
                SpelunkeryBlocks.CINDER_TILE_WALL.get(),
                SpelunkeryBlocks.ASHEN_DIRT.get(),
                SpelunkeryBlocks.SCORCHED_DRIPSTONE.get(),
                SpelunkeryBlocks.CHARRED_BONES.get(),
                SpelunkeryBlocks.MYCELIUM_MUD.get(),
                SpelunkeryBlocks.MYCELIUM_MUD_BRICKS.get(),
                SpelunkeryBlocks.MYCELIUM_MUD_BRICK_STAIRS.get(),
                SpelunkeryBlocks.MYCELIUM_MUD_BRICK_SLAB.get(),
                SpelunkeryBlocks.MYCELIUM_MUD_BRICK_WALL.get(),
                SpelunkeryBlocks.FUNGAL_MAT.get(),
                SpelunkeryBlocks.BIOLUMINESCENT_MOSS.get(),
                SpelunkeryBlocks.GLOWCAP.get(),
                SpelunkeryBlocks.BLUE_MUSHROOM_BLOCK.get(),
                SpelunkeryBlocks.BRONZE_BLOCK.get(),
                SpelunkeryBlocks.BRONZE_TILES.get(),
                SpelunkeryBlocks.BRONZE_BARS.get(),
                SpelunkeryBlocks.BRONZE_CHAIN.get(),
                SpelunkeryBlocks.BRONZE_LANTERN.get(),
                SpelunkeryBlocks.INVAR_BLOCK.get(),
                SpelunkeryBlocks.INVAR_TILES.get(),
                SpelunkeryBlocks.INVAR_ANVIL.get(),
                SpelunkeryBlocks.CHIPPED_INVAR_ANVIL.get(),
                SpelunkeryBlocks.DAMAGED_INVAR_ANVIL.get(),
                SpelunkeryBlocks.ROSE_GOLD_BLOCK.get(),
                SpelunkeryBlocks.ROSE_GOLD_TILES.get(),
                SpelunkeryBlocks.ELECTRUM_BLOCK.get(),
                SpelunkeryBlocks.ELECTRUM_TILES.get(),
                SpelunkeryBlocks.ROPE.get(),
                SpelunkeryBlocks.CRUCIBLE.get(),
                SpelunkeryBlocks.FOUNDRY.get()
        );
    }

    private static void addAllItemEntries(Consumer<ItemLike> output) {
        acceptEntries(output,
                SpelunkeryItems.RAW_TIN.get(),
                SpelunkeryItems.TIN_INGOT.get(),
                SpelunkeryItems.RAW_SILVER.get(),
                SpelunkeryItems.SILVER_INGOT.get(),
                SpelunkeryItems.SILVER_ARROW.get(),
                SpelunkeryItems.RAW_NICKEL.get(),
                SpelunkeryItems.NICKEL_INGOT.get(),
                SpelunkeryItems.TOPAZ_SHARD.get(),
                SpelunkeryItems.RUBY.get(),
                SpelunkeryItems.SAPPHIRE.get(),
                SpelunkeryItems.BRONZE_INGOT.get(),
                SpelunkeryItems.INVAR_INGOT.get(),
                SpelunkeryItems.ROSE_GOLD_INGOT.get(),
                SpelunkeryItems.ELECTRUM_INGOT.get(),
                SpelunkeryItems.BAT_WING.get(),
                SpelunkeryItems.NICKEL_PLATING.get(),
                SpelunkeryItems.SILVER_LINING.get(),
                SpelunkeryItems.ROSE_GOLD_FILIGREE.get(),
                SpelunkeryItems.SPELUNKERS_BREW.get(),
                SpelunkeryItems.DANGERSENSE_TONIC.get(),
                SpelunkeryItems.MINERS_TONIC.get(),
                SpelunkeryItems.BRONZE_COMPASS.get(),
                SpelunkeryItems.BRONZE_SHIELD.get(),
                SpelunkeryItems.MINERS_HELMET.get(),
                SpelunkeryItems.PROSPECTOR_LENS.get(),
                SpelunkeryItems.TORCH_LAUNCHER.get(),
                SpelunkeryItems.ROPE_BUNDLE.get(),
                SpelunkeryItems.SILVER_SWORD.get(),
                SpelunkeryItems.BRONZE_SWORD.get(),
                SpelunkeryItems.BRONZE_PICKAXE.get(),
                SpelunkeryItems.BRONZE_AXE.get(),
                SpelunkeryItems.BRONZE_SHOVEL.get(),
                SpelunkeryItems.BRONZE_HOE.get(),
                SpelunkeryItems.BRONZE_HELMET.get(),
                SpelunkeryItems.BRONZE_CHESTPLATE.get(),
                SpelunkeryItems.BRONZE_LEGGINGS.get(),
                SpelunkeryItems.BRONZE_BOOTS.get(),
                SpelunkeryItems.SILVER_PICKAXE.get(),
                SpelunkeryItems.SILVER_AXE.get(),
                SpelunkeryItems.SILVER_SHOVEL.get(),
                SpelunkeryItems.SILVER_HOE.get(),
                SpelunkeryItems.SILVER_HELMET.get(),
                SpelunkeryItems.SILVER_CHESTPLATE.get(),
                SpelunkeryItems.SILVER_LEGGINGS.get(),
                SpelunkeryItems.SILVER_BOOTS.get(),
                SpelunkeryItems.INVAR_SWORD.get(),
                SpelunkeryItems.INVAR_PICKAXE.get(),
                SpelunkeryItems.INVAR_AXE.get(),
                SpelunkeryItems.INVAR_SHOVEL.get(),
                SpelunkeryItems.INVAR_HOE.get(),
                SpelunkeryItems.INVAR_HELMET.get(),
                SpelunkeryItems.INVAR_CHESTPLATE.get(),
                SpelunkeryItems.INVAR_LEGGINGS.get(),
                SpelunkeryItems.INVAR_BOOTS.get(),
                SpelunkeryItems.ROSE_GOLD_SWORD.get(),
                SpelunkeryItems.ROSE_GOLD_PICKAXE.get(),
                SpelunkeryItems.ROSE_GOLD_AXE.get(),
                SpelunkeryItems.ROSE_GOLD_SHOVEL.get(),
                SpelunkeryItems.ROSE_GOLD_HOE.get(),
                SpelunkeryItems.ROSE_GOLD_HELMET.get(),
                SpelunkeryItems.ROSE_GOLD_CHESTPLATE.get(),
                SpelunkeryItems.ROSE_GOLD_LEGGINGS.get(),
                SpelunkeryItems.ROSE_GOLD_BOOTS.get(),
                SpelunkeryItems.ELECTRUM_BOW.get()
        );
    }
}
