package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.vg.spelunkery.Spelunkery;

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
