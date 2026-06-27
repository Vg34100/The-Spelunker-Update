package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.block.Block;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.item.BronzeCompassItem;
import net.vg.spelunkery.item.BronzeShieldItem;
import net.vg.spelunkery.item.ElectrumBowItem;
import net.vg.spelunkery.item.MinerHelmetItem;
import net.vg.spelunkery.item.ProspectorLensItem;
import net.vg.spelunkery.item.RopeBlockItem;
import net.vg.spelunkery.item.RopeBundleItem;
import net.vg.spelunkery.item.SilverArrowItem;
import net.vg.spelunkery.item.SilverSwordItem;
import net.vg.spelunkery.item.TonicItem;
import net.vg.spelunkery.item.TorchLauncherItem;

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
    public static final RegistrySupplier<Item> RUBY = registerSimpleItem("ruby");
    public static final RegistrySupplier<Item> SAPPHIRE = registerSimpleItem("sapphire");
    public static final RegistrySupplier<Item> BRONZE_INGOT = registerSimpleItem("bronze_ingot");
    public static final RegistrySupplier<Item> INVAR_INGOT = registerSimpleItem("invar_ingot");
    public static final RegistrySupplier<Item> ROSE_GOLD_INGOT = registerSimpleItem("rose_gold_ingot");
    public static final RegistrySupplier<Item> ELECTRUM_INGOT = registerSimpleItem("electrum_ingot");
    public static final RegistrySupplier<Item> NICKEL_PLATING = registerSimpleItem("nickel_plating");
    public static final RegistrySupplier<Item> SILVER_LINING = registerSimpleItem("silver_lining");
    public static final RegistrySupplier<Item> ROSE_GOLD_FILIGREE = registerSimpleItem("rose_gold_filigree");
    public static final RegistrySupplier<Item> BAT_WING = registerSimpleItem("bat_wing");
    public static final RegistrySupplier<Item> BRONZE_COMPASS = ITEMS.register("bronze_compass", () -> new BronzeCompassItem(new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> BRONZE_SHIELD = ITEMS.register("bronze_shield", () -> new BronzeShieldItem(new Item.Properties().durability(448)));
    public static final RegistrySupplier<Item> PROSPECTOR_LENS = ITEMS.register("prospector_lens", () -> new ProspectorLensItem(new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> TORCH_LAUNCHER = ITEMS.register("torch_launcher", () -> new TorchLauncherItem(new Item.Properties().stacksTo(1).durability(384)));
    public static final RegistrySupplier<Item> SPELUNKERS_BREW = ITEMS.register("spelunkers_brew", () -> new TonicItem(net.vg.spelunkery.gameplay.SpelunkeryGameplayHelper.holder(SpelunkeryEffects.SPELUNKING.get()), 20 * 180, 0, new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistrySupplier<Item> DANGERSENSE_TONIC = ITEMS.register("dangersense_tonic", () -> new TonicItem(net.vg.spelunkery.gameplay.SpelunkeryGameplayHelper.holder(SpelunkeryEffects.DANGER_SENSE.get()), 20 * 180, 0, new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistrySupplier<Item> MINERS_TONIC = ITEMS.register("miners_tonic", () -> new TonicItem(net.vg.spelunkery.gameplay.SpelunkeryGameplayHelper.holder(SpelunkeryEffects.MINERS_FOCUS.get()), 20 * 180, 0, new Item.Properties().stacksTo(16).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistrySupplier<Item> MINERS_HELMET = ITEMS.register(
            "miners_helmet",
            () -> new MinerHelmetItem(new Item.Properties()
                    .humanoidArmor(SpelunkeryEquipmentMaterials.MINERS_HELMET_ARMOR, ArmorType.HELMET)
                    .durability(192))
    );
    public static final RegistrySupplier<Item> SILVER_SWORD = ITEMS.register(
            "silver_sword",
            () -> new SilverSwordItem(new Item.Properties().sword(ToolMaterial.IRON, 3, -2.4F))
    );
    public static final RegistrySupplier<Item> ELECTRUM_BOW = ITEMS.register("electrum_bow", () -> new ElectrumBowItem(new Item.Properties().durability(512)));
    public static final RegistrySupplier<Item> ROPE = ITEMS.register("rope", () -> new RopeBlockItem(SpelunkeryBlocks.ROPE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> ROPE_BUNDLE = ITEMS.register("rope_bundle", () -> new RopeBundleItem(new Item.Properties().stacksTo(16)));

    public static final RegistrySupplier<Item> BRONZE_SWORD = ITEMS.register("bronze_sword", () -> sword(SpelunkeryEquipmentMaterials.BRONZE_TIER, 3));
    public static final RegistrySupplier<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe", () -> new Item(new Item.Properties().pickaxe(SpelunkeryEquipmentMaterials.BRONZE_TIER, 1.0F, -2.8F)));
    public static final RegistrySupplier<Item> BRONZE_AXE = ITEMS.register("bronze_axe", () -> new AxeItem(SpelunkeryEquipmentMaterials.BRONZE_TIER, 4.0F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel", () -> new ShovelItem(SpelunkeryEquipmentMaterials.BRONZE_TIER, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> BRONZE_HOE = ITEMS.register("bronze_hoe", () -> new HoeItem(SpelunkeryEquipmentMaterials.BRONZE_TIER, 0.0F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.BRONZE_ARMOR, ArmorType.HELMET)));
    public static final RegistrySupplier<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.BRONZE_ARMOR, ArmorType.CHESTPLATE)));
    public static final RegistrySupplier<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.BRONZE_ARMOR, ArmorType.LEGGINGS)));
    public static final RegistrySupplier<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.BRONZE_ARMOR, ArmorType.BOOTS)));

    public static final RegistrySupplier<Item> SILVER_PICKAXE = ITEMS.register("silver_pickaxe", () -> new Item(new Item.Properties().pickaxe(SpelunkeryEquipmentMaterials.SILVER_TIER, 1.0F, -2.8F)));
    public static final RegistrySupplier<Item> SILVER_AXE = ITEMS.register("silver_axe", () -> new AxeItem(SpelunkeryEquipmentMaterials.SILVER_TIER, 4.0F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> SILVER_SHOVEL = ITEMS.register("silver_shovel", () -> new ShovelItem(SpelunkeryEquipmentMaterials.SILVER_TIER, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> SILVER_HOE = ITEMS.register("silver_hoe", () -> new HoeItem(SpelunkeryEquipmentMaterials.SILVER_TIER, 0.0F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> SILVER_HELMET = ITEMS.register("silver_helmet", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.SILVER_ARMOR, ArmorType.HELMET)));
    public static final RegistrySupplier<Item> SILVER_CHESTPLATE = ITEMS.register("silver_chestplate", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.SILVER_ARMOR, ArmorType.CHESTPLATE)));
    public static final RegistrySupplier<Item> SILVER_LEGGINGS = ITEMS.register("silver_leggings", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.SILVER_ARMOR, ArmorType.LEGGINGS)));
    public static final RegistrySupplier<Item> SILVER_BOOTS = ITEMS.register("silver_boots", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.SILVER_ARMOR, ArmorType.BOOTS)));

    public static final RegistrySupplier<Item> INVAR_SWORD = ITEMS.register("invar_sword", () -> sword(SpelunkeryEquipmentMaterials.INVAR_TIER, 3));
    public static final RegistrySupplier<Item> INVAR_PICKAXE = ITEMS.register("invar_pickaxe", () -> new Item(new Item.Properties().pickaxe(SpelunkeryEquipmentMaterials.INVAR_TIER, 1.0F, -2.8F)));
    public static final RegistrySupplier<Item> INVAR_AXE = ITEMS.register("invar_axe", () -> new AxeItem(SpelunkeryEquipmentMaterials.INVAR_TIER, 5.0F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> INVAR_SHOVEL = ITEMS.register("invar_shovel", () -> new ShovelItem(SpelunkeryEquipmentMaterials.INVAR_TIER, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> INVAR_HOE = ITEMS.register("invar_hoe", () -> new HoeItem(SpelunkeryEquipmentMaterials.INVAR_TIER, 0.0F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> INVAR_HELMET = ITEMS.register("invar_helmet", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.INVAR_ARMOR, ArmorType.HELMET)));
    public static final RegistrySupplier<Item> INVAR_CHESTPLATE = ITEMS.register("invar_chestplate", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.INVAR_ARMOR, ArmorType.CHESTPLATE)));
    public static final RegistrySupplier<Item> INVAR_LEGGINGS = ITEMS.register("invar_leggings", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.INVAR_ARMOR, ArmorType.LEGGINGS)));
    public static final RegistrySupplier<Item> INVAR_BOOTS = ITEMS.register("invar_boots", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.INVAR_ARMOR, ArmorType.BOOTS)));

    public static final RegistrySupplier<Item> ROSE_GOLD_SWORD = ITEMS.register("rose_gold_sword", () -> sword(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER, 3));
    public static final RegistrySupplier<Item> ROSE_GOLD_PICKAXE = ITEMS.register("rose_gold_pickaxe", () -> new Item(new Item.Properties().pickaxe(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER, 1.0F, -2.8F)));
    public static final RegistrySupplier<Item> ROSE_GOLD_AXE = ITEMS.register("rose_gold_axe", () -> new AxeItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER, 4.0F, -2.5F, new Item.Properties()));
    public static final RegistrySupplier<Item> ROSE_GOLD_SHOVEL = ITEMS.register("rose_gold_shovel", () -> new ShovelItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> ROSE_GOLD_HOE = ITEMS.register("rose_gold_hoe", () -> new HoeItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER, 0.0F, -3.0F, new Item.Properties()));
    public static final RegistrySupplier<Item> ROSE_GOLD_HELMET = ITEMS.register("rose_gold_helmet", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.ROSE_GOLD_ARMOR, ArmorType.HELMET)));
    public static final RegistrySupplier<Item> ROSE_GOLD_CHESTPLATE = ITEMS.register("rose_gold_chestplate", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.ROSE_GOLD_ARMOR, ArmorType.CHESTPLATE)));
    public static final RegistrySupplier<Item> ROSE_GOLD_LEGGINGS = ITEMS.register("rose_gold_leggings", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.ROSE_GOLD_ARMOR, ArmorType.LEGGINGS)));
    public static final RegistrySupplier<Item> ROSE_GOLD_BOOTS = ITEMS.register("rose_gold_boots", () -> new Item(new Item.Properties().humanoidArmor(SpelunkeryEquipmentMaterials.ROSE_GOLD_ARMOR, ArmorType.BOOTS)));

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

    private static Item sword(ToolMaterial material, int attackDamageBonus) {
        return new Item(new Item.Properties().sword(material, attackDamageBonus, -2.4F));
    }
}
