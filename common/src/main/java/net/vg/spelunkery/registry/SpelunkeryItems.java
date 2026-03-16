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

    public static final RegistrySupplier<Item> RAW_TIN = registerSimpleItem("raw_tin");
    public static final RegistrySupplier<Item> TIN_INGOT = registerSimpleItem("tin_ingot");
    public static final RegistrySupplier<Item> RAW_NICKEL = registerSimpleItem("raw_nickel");
    public static final RegistrySupplier<Item> NICKEL_INGOT = registerSimpleItem("nickel_ingot");
    public static final RegistrySupplier<Item> RAW_SILVER = registerSimpleItem("raw_silver");
    public static final RegistrySupplier<Item> SILVER_INGOT = registerSimpleItem("silver_ingot");

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

    private static RegistrySupplier<Item> registerSimpleItem(String name) {
        return ITEMS.register(name, () -> new Item(new Item.Properties()));
    }
}
