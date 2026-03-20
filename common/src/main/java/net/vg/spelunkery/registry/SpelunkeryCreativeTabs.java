package net.vg.spelunkery.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.vg.spelunkery.Spelunkery;

public final class SpelunkeryCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Spelunkery.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> SPELUNKERY_TAB = TABS.register(
            "spelunkery",
            () -> CreativeTabRegistry.create(builder -> builder
                    .title(Component.translatable("itemGroup.spelunkery"))
                    .icon(() -> new ItemStack(SpelunkeryBlocks.TOPAZ_BLOCK.get()))
                    .displayItems((parameters, output) -> {
                        addBlockEntries(output);
                        addIngredientEntries(output);
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

        CreativeTabRegistry.modify(CreativeTabRegistry.defer(CreativeModeTabs.NATURAL_BLOCKS), (flags, output, hasPermissions) -> {
            output.acceptAfter(Blocks.DEEPSLATE_COPPER_ORE, SpelunkeryBlocks.TIN_ORE.get());
            output.acceptAfter(SpelunkeryBlocks.TIN_ORE.get(), SpelunkeryBlocks.DEEPSLATE_TIN_ORE.get());
            output.acceptAfter(Blocks.DEEPSLATE_GOLD_ORE, SpelunkeryBlocks.SILVER_ORE.get());
            output.acceptAfter(SpelunkeryBlocks.SILVER_ORE.get(), SpelunkeryBlocks.DEEPSLATE_SILVER_ORE.get());
            output.acceptAfter(Blocks.DEEPSLATE_REDSTONE_ORE, SpelunkeryBlocks.NICKEL_ORE.get());
            output.acceptAfter(SpelunkeryBlocks.NICKEL_ORE.get(), SpelunkeryBlocks.DEEPSLATE_NICKEL_ORE.get());
            output.acceptAfter(Blocks.AMETHYST_BLOCK, SpelunkeryBlocks.TOPAZ_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.TOPAZ_BLOCK.get(), SpelunkeryBlocks.RUBY_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.RUBY_BLOCK.get(), SpelunkeryBlocks.SAPPHIRE_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.SAPPHIRE_BLOCK.get(), SpelunkeryBlocks.CRUCIBLE.get());
            output.acceptAfter(SpelunkeryBlocks.CRUCIBLE.get(), SpelunkeryBlocks.FOUNDRY.get());
        });

        CreativeTabRegistry.modify(CreativeTabRegistry.defer(CreativeModeTabs.INGREDIENTS), (flags, output, hasPermissions) -> {
            output.acceptAfter(Items.RAW_COPPER, SpelunkeryItems.RAW_TIN.get());
            output.acceptAfter(SpelunkeryItems.RAW_TIN.get(), SpelunkeryItems.TIN_INGOT.get());
            output.acceptAfter(SpelunkeryItems.TIN_INGOT.get(), SpelunkeryItems.RAW_SILVER.get());
            output.acceptAfter(SpelunkeryItems.RAW_SILVER.get(), SpelunkeryItems.SILVER_INGOT.get());
            output.acceptAfter(SpelunkeryItems.SILVER_INGOT.get(), SpelunkeryItems.RAW_NICKEL.get());
            output.acceptAfter(SpelunkeryItems.RAW_NICKEL.get(), SpelunkeryItems.NICKEL_INGOT.get());
            output.acceptAfter(Items.AMETHYST_SHARD, SpelunkeryItems.TOPAZ_SHARD.get());
            output.acceptAfter(SpelunkeryItems.TOPAZ_SHARD.get(), SpelunkeryItems.RUBY.get());
            output.acceptAfter(SpelunkeryItems.RUBY.get(), SpelunkeryItems.SAPPHIRE.get());
            output.acceptAfter(SpelunkeryItems.NICKEL_INGOT.get(), SpelunkeryItems.BRONZE_INGOT.get());
            output.acceptAfter(SpelunkeryItems.BRONZE_INGOT.get(), SpelunkeryItems.INVAR_INGOT.get());
            output.acceptAfter(SpelunkeryItems.INVAR_INGOT.get(), SpelunkeryItems.ROSE_GOLD_INGOT.get());
            output.acceptAfter(SpelunkeryItems.ROSE_GOLD_INGOT.get(), SpelunkeryItems.ELECTRUM_INGOT.get());
            output.acceptAfter(SpelunkeryItems.ELECTRUM_INGOT.get(), SpelunkeryItems.NICKEL_PLATING.get());
            output.acceptAfter(SpelunkeryItems.NICKEL_PLATING.get(), SpelunkeryItems.SILVER_LINING.get());
            output.acceptAfter(SpelunkeryItems.SILVER_LINING.get(), SpelunkeryItems.ROSE_GOLD_FILIGREE.get());
        });

        CreativeTabRegistry.modify(CreativeTabRegistry.defer(CreativeModeTabs.TOOLS_AND_UTILITIES), (flags, output, hasPermissions) -> {
            output.acceptAfter(Items.COMPASS, SpelunkeryItems.BRONZE_COMPASS.get());
            output.acceptAfter(SpelunkeryItems.BRONZE_COMPASS.get(), SpelunkeryItems.BRONZE_SHIELD.get());
            output.acceptAfter(SpelunkeryItems.BRONZE_SHIELD.get(), SpelunkeryItems.PROSPECTOR_LENS.get());
            output.acceptAfter(SpelunkeryItems.PROSPECTOR_LENS.get(), SpelunkeryItems.ELECTRUM_BOW.get());
        });

        CreativeTabRegistry.modify(CreativeTabRegistry.defer(CreativeModeTabs.COMBAT), (flags, output, hasPermissions) -> {
            output.acceptAfter(Items.ARROW, SpelunkeryItems.SILVER_ARROW.get());
            output.acceptAfter(Items.IRON_SWORD, SpelunkeryItems.SILVER_SWORD.get());
            output.acceptAfter(Items.SHIELD, SpelunkeryItems.BRONZE_SHIELD.get());
            output.acceptAfter(Items.BOW, SpelunkeryItems.ELECTRUM_BOW.get());
        });

        CreativeTabRegistry.modify(CreativeTabRegistry.defer(CreativeModeTabs.BUILDING_BLOCKS), (flags, output, hasPermissions) -> {
            output.acceptAfter(Blocks.CUT_COPPER, SpelunkeryBlocks.TIN_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.TIN_BLOCK.get(), SpelunkeryBlocks.RAW_TIN_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.RAW_TIN_BLOCK.get(), SpelunkeryBlocks.SILVER_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.SILVER_BLOCK.get(), SpelunkeryBlocks.RAW_SILVER_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.RAW_SILVER_BLOCK.get(), SpelunkeryBlocks.NICKEL_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.NICKEL_BLOCK.get(), SpelunkeryBlocks.RAW_NICKEL_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.RAW_NICKEL_BLOCK.get(), SpelunkeryBlocks.BRONZE_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.BRONZE_BLOCK.get(), SpelunkeryBlocks.BRONZE_TILES.get());
            output.acceptAfter(SpelunkeryBlocks.BRONZE_TILES.get(), SpelunkeryBlocks.BRONZE_GRATE.get());
            output.acceptAfter(SpelunkeryBlocks.BRONZE_GRATE.get(), SpelunkeryBlocks.BRONZE_BARS.get());
            output.acceptAfter(SpelunkeryBlocks.BRONZE_BARS.get(), SpelunkeryBlocks.BRONZE_CHAIN.get());
            output.acceptAfter(SpelunkeryBlocks.BRONZE_CHAIN.get(), SpelunkeryBlocks.BRONZE_LANTERN.get());
            output.acceptAfter(SpelunkeryBlocks.BRONZE_LANTERN.get(), SpelunkeryBlocks.INVAR_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.INVAR_BLOCK.get(), SpelunkeryBlocks.INVAR_TILES.get());
            output.acceptAfter(SpelunkeryBlocks.INVAR_TILES.get(), SpelunkeryBlocks.INVAR_ANVIL.get());
            output.acceptAfter(SpelunkeryBlocks.INVAR_ANVIL.get(), SpelunkeryBlocks.CHIPPED_INVAR_ANVIL.get());
            output.acceptAfter(SpelunkeryBlocks.CHIPPED_INVAR_ANVIL.get(), SpelunkeryBlocks.DAMAGED_INVAR_ANVIL.get());
            output.acceptAfter(SpelunkeryBlocks.DAMAGED_INVAR_ANVIL.get(), SpelunkeryBlocks.ROSE_GOLD_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.ROSE_GOLD_BLOCK.get(), SpelunkeryBlocks.ROSE_GOLD_TILES.get());
            output.acceptAfter(SpelunkeryBlocks.ROSE_GOLD_TILES.get(), SpelunkeryBlocks.ELECTRUM_BLOCK.get());
            output.acceptAfter(SpelunkeryBlocks.ELECTRUM_BLOCK.get(), SpelunkeryBlocks.ELECTRUM_TILES.get());
        });
    }

    private static void addBlockEntries(CreativeModeTab.Output output) {
        output.accept(SpelunkeryBlocks.TIN_ORE.get());
        output.accept(SpelunkeryBlocks.DEEPSLATE_TIN_ORE.get());
        output.accept(SpelunkeryBlocks.RAW_TIN_BLOCK.get());
        output.accept(SpelunkeryBlocks.TIN_BLOCK.get());
        output.accept(SpelunkeryBlocks.SILVER_ORE.get());
        output.accept(SpelunkeryBlocks.DEEPSLATE_SILVER_ORE.get());
        output.accept(SpelunkeryBlocks.RAW_SILVER_BLOCK.get());
        output.accept(SpelunkeryBlocks.SILVER_BLOCK.get());
        output.accept(SpelunkeryBlocks.NICKEL_ORE.get());
        output.accept(SpelunkeryBlocks.DEEPSLATE_NICKEL_ORE.get());
        output.accept(SpelunkeryBlocks.RAW_NICKEL_BLOCK.get());
        output.accept(SpelunkeryBlocks.NICKEL_BLOCK.get());
        output.accept(SpelunkeryBlocks.TOPAZ_BLOCK.get());
        output.accept(SpelunkeryBlocks.RUBY_BLOCK.get());
        output.accept(SpelunkeryBlocks.SAPPHIRE_BLOCK.get());
        output.accept(SpelunkeryBlocks.BRONZE_BLOCK.get());
        output.accept(SpelunkeryBlocks.BRONZE_TILES.get());
        output.accept(SpelunkeryBlocks.BRONZE_GRATE.get());
        output.accept(SpelunkeryBlocks.BRONZE_BARS.get());
        output.accept(SpelunkeryBlocks.BRONZE_CHAIN.get());
        output.accept(SpelunkeryBlocks.BRONZE_LANTERN.get());
        output.accept(SpelunkeryBlocks.INVAR_BLOCK.get());
        output.accept(SpelunkeryBlocks.INVAR_TILES.get());
        output.accept(SpelunkeryBlocks.INVAR_ANVIL.get());
        output.accept(SpelunkeryBlocks.CHIPPED_INVAR_ANVIL.get());
        output.accept(SpelunkeryBlocks.DAMAGED_INVAR_ANVIL.get());
        output.accept(SpelunkeryBlocks.ROSE_GOLD_BLOCK.get());
        output.accept(SpelunkeryBlocks.ROSE_GOLD_TILES.get());
        output.accept(SpelunkeryBlocks.ELECTRUM_BLOCK.get());
        output.accept(SpelunkeryBlocks.ELECTRUM_TILES.get());
        output.accept(SpelunkeryBlocks.CRUCIBLE.get());
        output.accept(SpelunkeryBlocks.FOUNDRY.get());
    }

    private static void addIngredientEntries(CreativeModeTab.Output output) {
        output.accept(SpelunkeryItems.RAW_TIN.get());
        output.accept(SpelunkeryItems.TIN_INGOT.get());
        output.accept(SpelunkeryItems.RAW_SILVER.get());
        output.accept(SpelunkeryItems.SILVER_INGOT.get());
        output.accept(SpelunkeryItems.SILVER_ARROW.get());
        output.accept(SpelunkeryItems.RAW_NICKEL.get());
        output.accept(SpelunkeryItems.NICKEL_INGOT.get());
        output.accept(SpelunkeryItems.TOPAZ_SHARD.get());
        output.accept(SpelunkeryItems.RUBY.get());
        output.accept(SpelunkeryItems.SAPPHIRE.get());
        output.accept(SpelunkeryItems.BRONZE_INGOT.get());
        output.accept(SpelunkeryItems.INVAR_INGOT.get());
        output.accept(SpelunkeryItems.ROSE_GOLD_INGOT.get());
        output.accept(SpelunkeryItems.ELECTRUM_INGOT.get());
        output.accept(SpelunkeryItems.NICKEL_PLATING.get());
        output.accept(SpelunkeryItems.SILVER_LINING.get());
        output.accept(SpelunkeryItems.ROSE_GOLD_FILIGREE.get());
        output.accept(SpelunkeryItems.BRONZE_COMPASS.get());
        output.accept(SpelunkeryItems.BRONZE_SHIELD.get());
        output.accept(SpelunkeryItems.PROSPECTOR_LENS.get());
        output.accept(SpelunkeryItems.SILVER_SWORD.get());
        output.accept(SpelunkeryItems.ELECTRUM_BOW.get());
    }
}
