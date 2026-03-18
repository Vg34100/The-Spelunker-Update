package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.item.BronzeCompassItem;
import net.vg.spelunkery.item.BronzeShieldItem;
import net.vg.spelunkery.item.ElectrumBowItem;
import net.vg.spelunkery.item.ProspectorLensItem;
import net.vg.spelunkery.item.SilverArrowItem;
import net.vg.spelunkery.item.SilverLinedArmorItem;
import net.vg.spelunkery.item.SilverSwordItem;

import java.util.function.Supplier;

public final class SpelunkeryItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Spelunkery.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<Item> RAW_TIN = registerSimpleItem("raw_tin");
    public static final RegistrySupplier<Item> TIN_INGOT = registerSimpleItem("tin_ingot");
    public static final RegistrySupplier<Item> RAW_NICKEL = registerSimpleItem("raw_nickel");
    public static final RegistrySupplier<Item> NICKEL_INGOT = registerSimpleItem("nickel_ingot");
    public static final RegistrySupplier<Item> RAW_SILVER = registerSimpleItem("raw_silver");
    public static final RegistrySupplier<Item> SILVER_INGOT = registerSimpleItem("silver_ingot");
    public static final RegistrySupplier<Item> SILVER_ARROW = ITEMS.register("silver_arrow", () -> new SilverArrowItem(new Item.Properties()));
    public static final RegistrySupplier<Item> TOPAZ_SHARD = registerSimpleItem("topaz_shard");
    public static final RegistrySupplier<Item> BRONZE_INGOT = registerSimpleItem("bronze_ingot");
    public static final RegistrySupplier<Item> INVAR_INGOT = registerSimpleItem("invar_ingot");
    public static final RegistrySupplier<Item> ROSE_GOLD_INGOT = registerSimpleItem("rose_gold_ingot");
    public static final RegistrySupplier<Item> ELECTRUM_INGOT = registerSimpleItem("electrum_ingot");
    public static final RegistrySupplier<Item> NICKEL_PLATING = registerSimpleItem("nickel_plating");
    public static final RegistrySupplier<Item> SILVER_LINING = registerSimpleItem("silver_lining");
    public static final RegistrySupplier<Item> ROSE_GOLD_FILIGREE = registerSimpleItem("rose_gold_filigree");
    public static final RegistrySupplier<Item> BRONZE_COMPASS = ITEMS.register("bronze_compass", () -> new BronzeCompassItem(new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> BRONZE_SHIELD = ITEMS.register("bronze_shield", () -> new BronzeShieldItem(new Item.Properties().durability(448)));
    public static final RegistrySupplier<Item> PROSPECTOR_LENS = ITEMS.register("prospector_lens", () -> new ProspectorLensItem(new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> SILVER_SWORD = ITEMS.register(
            "silver_sword",
            () -> new SilverSwordItem(
                    Tiers.IRON,
                    new Item.Properties()
                            .durability(Tiers.IRON.getUses())
                            .attributes(SwordItem.createAttributes(Tiers.IRON, 3, -2.4F))
            )
    );
    public static final RegistrySupplier<Item> ELECTRUM_BOW = ITEMS.register("electrum_bow", () -> new ElectrumBowItem(new Item.Properties().durability(512)));
    public static final RegistrySupplier<Item> NICKEL_PLATED_IRON_HELMET = registerArmor("nickel_plated_iron_helmet", SpelunkeryArmorMaterials.NICKEL_PLATED_IRON, ArmorItem.Type.HELMET);
    public static final RegistrySupplier<Item> NICKEL_PLATED_IRON_CHESTPLATE = registerArmor("nickel_plated_iron_chestplate", SpelunkeryArmorMaterials.NICKEL_PLATED_IRON, ArmorItem.Type.CHESTPLATE);
    public static final RegistrySupplier<Item> NICKEL_PLATED_IRON_LEGGINGS = registerArmor("nickel_plated_iron_leggings", SpelunkeryArmorMaterials.NICKEL_PLATED_IRON, ArmorItem.Type.LEGGINGS);
    public static final RegistrySupplier<Item> NICKEL_PLATED_IRON_BOOTS = registerArmor("nickel_plated_iron_boots", SpelunkeryArmorMaterials.NICKEL_PLATED_IRON, ArmorItem.Type.BOOTS);
    public static final RegistrySupplier<Item> SILVER_LINED_IRON_HELMET = registerSilverLinedArmor("silver_lined_iron_helmet", ArmorItem.Type.HELMET);
    public static final RegistrySupplier<Item> SILVER_LINED_IRON_CHESTPLATE = registerSilverLinedArmor("silver_lined_iron_chestplate", ArmorItem.Type.CHESTPLATE);
    public static final RegistrySupplier<Item> SILVER_LINED_IRON_LEGGINGS = registerSilverLinedArmor("silver_lined_iron_leggings", ArmorItem.Type.LEGGINGS);
    public static final RegistrySupplier<Item> SILVER_LINED_IRON_BOOTS = registerSilverLinedArmor("silver_lined_iron_boots", ArmorItem.Type.BOOTS);
    public static final RegistrySupplier<Item> ROSE_GOLD_PLATED_IRON_HELMET = registerArmor("rose_gold_plated_iron_helmet", SpelunkeryArmorMaterials.ROSE_GOLD_PLATED_IRON, ArmorItem.Type.HELMET);
    public static final RegistrySupplier<Item> ROSE_GOLD_PLATED_IRON_CHESTPLATE = registerArmor("rose_gold_plated_iron_chestplate", SpelunkeryArmorMaterials.ROSE_GOLD_PLATED_IRON, ArmorItem.Type.CHESTPLATE);
    public static final RegistrySupplier<Item> ROSE_GOLD_PLATED_IRON_LEGGINGS = registerArmor("rose_gold_plated_iron_leggings", SpelunkeryArmorMaterials.ROSE_GOLD_PLATED_IRON, ArmorItem.Type.LEGGINGS);
    public static final RegistrySupplier<Item> ROSE_GOLD_PLATED_IRON_BOOTS = registerArmor("rose_gold_plated_iron_boots", SpelunkeryArmorMaterials.ROSE_GOLD_PLATED_IRON, ArmorItem.Type.BOOTS);

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

    private static RegistrySupplier<Item> registerArmor(String name, net.minecraft.core.Holder<net.minecraft.world.item.ArmorMaterial> material, ArmorItem.Type type) {
        return ITEMS.register(name, () -> new ArmorItem(material, type, new Item.Properties().stacksTo(1)));
    }

    private static RegistrySupplier<Item> registerSilverLinedArmor(String name, ArmorItem.Type type) {
        return ITEMS.register(name, () -> new SilverLinedArmorItem(SpelunkeryArmorMaterials.SILVER_LINED_IRON, type, new Item.Properties().stacksTo(1)));
    }
}
