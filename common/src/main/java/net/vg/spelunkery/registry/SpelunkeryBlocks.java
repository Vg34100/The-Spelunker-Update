package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.block.CrucibleBlock;
import net.vg.spelunkery.block.FoundryBlock;
import net.vg.spelunkery.block.InvarAnvilBlock;

import java.util.function.Supplier;

public final class SpelunkeryBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Spelunkery.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<Block> TIN_ORE = registerBlock(
            "tin_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(3.0F, 3.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> DEEPSLATE_TIN_ORE = registerBlock(
            "deepslate_tin_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(4.5F, 3.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE))
    );

    public static final RegistrySupplier<Block> RAW_TIN_BLOCK = registerBlock(
            "raw_tin_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> TIN_BLOCK = registerBlock(
            "tin_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> NICKEL_ORE = registerBlock(
            "nickel_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 4), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(4.0F, 4.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> DEEPSLATE_NICKEL_ORE = registerBlock(
            "deepslate_nickel_ore",
            () -> new DropExperienceBlock(UniformInt.of(1, 5), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(5.0F, 4.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE))
    );

    public static final RegistrySupplier<Block> RAW_NICKEL_BLOCK = registerBlock(
            "raw_nickel_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> NICKEL_BLOCK = registerBlock(
            "nickel_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> SILVER_ORE = registerBlock(
            "silver_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 2), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(3.5F, 3.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> DEEPSLATE_SILVER_ORE = registerBlock(
            "deepslate_silver_ore",
            () -> new DropExperienceBlock(UniformInt.of(0, 3), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(4.5F, 4.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE))
    );

    public static final RegistrySupplier<Block> RAW_SILVER_BLOCK = registerBlock(
            "raw_silver_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> SILVER_BLOCK = registerBlock(
            "silver_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> TOPAZ_BLOCK = registerBlock(
            "topaz_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .strength(1.5F, 1.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .lightLevel(state -> 5))
    );

    public static final RegistrySupplier<Block> RUBY_BLOCK = registerBlock(
            "ruby_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(1.7F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .lightLevel(state -> 5))
    );

    public static final RegistrySupplier<Block> SAPPHIRE_BLOCK = registerBlock(
            "sapphire_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE)
                    .strength(1.7F, 1.8F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.AMETHYST)
                    .lightLevel(state -> 5))
    );

    public static final RegistrySupplier<Block> BRONZE_BLOCK = registerBlock(
            "bronze_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> BRONZE_TILES = registerBlock(
            "bronze_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> BRONZE_GRATE = registerBlock(
            "bronze_grate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> BRONZE_BARS = registerBlock(
            "bronze_bars",
            () -> new IronBarsBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.CHAIN)
                    .noOcclusion())
    );

    public static final RegistrySupplier<Block> BRONZE_CHAIN = registerBlock(
            "bronze_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.CHAIN)
                    .noOcclusion())
    );

    public static final RegistrySupplier<Block> BRONZE_LANTERN = registerBlock(
            "bronze_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(3.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.LANTERN)
                    .noOcclusion()
                    .lightLevel(state -> 15))
    );

    public static final RegistrySupplier<Block> INVAR_BLOCK = registerBlock(
            "invar_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.5F, 6.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> INVAR_TILES = registerBlock(
            "invar_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(5.5F, 6.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> DAMAGED_INVAR_ANVIL = registerBlock(
            "damaged_invar_anvil",
            () -> new InvarAnvilBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(6.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANVIL), null)
    );

    public static final RegistrySupplier<Block> CHIPPED_INVAR_ANVIL = registerBlock(
            "chipped_invar_anvil",
            () -> new InvarAnvilBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(6.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANVIL), DAMAGED_INVAR_ANVIL::get)
    );

    public static final RegistrySupplier<Block> INVAR_ANVIL = registerBlock(
            "invar_anvil",
            () -> new InvarAnvilBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(6.0F, 1200.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANVIL), CHIPPED_INVAR_ANVIL::get)
    );

    public static final RegistrySupplier<Block> ROSE_GOLD_BLOCK = registerBlock(
            "rose_gold_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> ROSE_GOLD_TILES = registerBlock(
            "rose_gold_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> ELECTRUM_BLOCK = registerBlock(
            "electrum_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> ELECTRUM_TILES = registerBlock(
            "electrum_tiles",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .strength(5.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.METAL))
    );

    public static final RegistrySupplier<Block> CRUCIBLE = registerBlock(
            "crucible",
            () -> new CrucibleBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(3.5F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    public static final RegistrySupplier<Block> FOUNDRY = registerBlock(
            "foundry",
            () -> new FoundryBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GRAY)
                    .strength(4.0F, 6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.STONE))
    );

    private static boolean initialized;

    private SpelunkeryBlocks() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        BLOCKS.register();
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> supplier) {
        RegistrySupplier<T> block = BLOCKS.register(name, supplier);
        SpelunkeryItems.registerBlockItem(name, block::get);
        return block;
    }
}
