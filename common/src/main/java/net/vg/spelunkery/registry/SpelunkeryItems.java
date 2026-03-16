package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.vg.spelunkery.Spelunkery;

import java.util.function.Supplier;

public final class SpelunkeryItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Spelunkery.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> RAW_TIN = ITEMS.register("raw_tin", () -> new Item(new Item.Properties()));
    public static final RegistrySupplier<Item> TIN_INGOT = ITEMS.register("tin_ingot", () -> new Item(new Item.Properties()));

    private static boolean initialized;

    private SpelunkeryItems() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        ITEMS.register();
    }

    public static void registerBlockItem(String name, Supplier<? extends Block> blockSupplier) {
        ITEMS.register(name, () -> new BlockItem(blockSupplier.get(), new Item.Properties()));
    }
}
