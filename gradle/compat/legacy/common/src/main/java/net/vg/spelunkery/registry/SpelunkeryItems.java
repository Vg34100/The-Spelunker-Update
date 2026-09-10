package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
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
            () -> new MinerHelmetItem(SpelunkeryEquipmentMaterials.MINERS_HELMET_ARMOR, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).durability(192))
    );
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
    public static final RegistrySupplier<Item> ROPE = ITEMS.register("rope", () -> new RopeBlockItem(SpelunkeryBlocks.ROPE.get(), new Item.Properties()));
    public static final RegistrySupplier<Item> ROPE_BUNDLE = ITEMS.register("rope_bundle", () -> new RopeBundleItem(new Item.Properties().stacksTo(16)));

    public static final RegistrySupplier<Item> BRONZE_SWORD = ITEMS.register("bronze_sword", () -> sword("bronze_sword", SpelunkeryEquipmentMaterials.BRONZE_TIER, 3));
    public static final RegistrySupplier<Item> BRONZE_PICKAXE = ITEMS.register("bronze_pickaxe", () -> new PickaxeItem(SpelunkeryEquipmentMaterials.BRONZE_TIER, toolProperties(SpelunkeryEquipmentMaterials.BRONZE_TIER)));
    public static final RegistrySupplier<Item> BRONZE_AXE = ITEMS.register("bronze_axe", () -> new AxeItem(SpelunkeryEquipmentMaterials.BRONZE_TIER, toolProperties(SpelunkeryEquipmentMaterials.BRONZE_TIER)));
    public static final RegistrySupplier<Item> BRONZE_SHOVEL = ITEMS.register("bronze_shovel", () -> new ShovelItem(SpelunkeryEquipmentMaterials.BRONZE_TIER, toolProperties(SpelunkeryEquipmentMaterials.BRONZE_TIER)));
    public static final RegistrySupplier<Item> BRONZE_HOE = ITEMS.register("bronze_hoe", () -> new HoeItem(SpelunkeryEquipmentMaterials.BRONZE_TIER, toolProperties(SpelunkeryEquipmentMaterials.BRONZE_TIER)));
    public static final RegistrySupplier<Item> BRONZE_HELMET = ITEMS.register("bronze_helmet", () -> new ArmorItem(SpelunkeryEquipmentMaterials.BRONZE_ARMOR, ArmorItem.Type.HELMET, armorProperties(ArmorItem.Type.HELMET, 15)));
    public static final RegistrySupplier<Item> BRONZE_CHESTPLATE = ITEMS.register("bronze_chestplate", () -> new ArmorItem(SpelunkeryEquipmentMaterials.BRONZE_ARMOR, ArmorItem.Type.CHESTPLATE, armorProperties(ArmorItem.Type.CHESTPLATE, 15)));
    public static final RegistrySupplier<Item> BRONZE_LEGGINGS = ITEMS.register("bronze_leggings", () -> new ArmorItem(SpelunkeryEquipmentMaterials.BRONZE_ARMOR, ArmorItem.Type.LEGGINGS, armorProperties(ArmorItem.Type.LEGGINGS, 15)));
    public static final RegistrySupplier<Item> BRONZE_BOOTS = ITEMS.register("bronze_boots", () -> new ArmorItem(SpelunkeryEquipmentMaterials.BRONZE_ARMOR, ArmorItem.Type.BOOTS, armorProperties(ArmorItem.Type.BOOTS, 15)));

    public static final RegistrySupplier<Item> SILVER_PICKAXE = ITEMS.register("silver_pickaxe", () -> new PickaxeItem(SpelunkeryEquipmentMaterials.SILVER_TIER, toolProperties(SpelunkeryEquipmentMaterials.SILVER_TIER)));
    public static final RegistrySupplier<Item> SILVER_AXE = ITEMS.register("silver_axe", () -> new AxeItem(SpelunkeryEquipmentMaterials.SILVER_TIER, toolProperties(SpelunkeryEquipmentMaterials.SILVER_TIER)));
    public static final RegistrySupplier<Item> SILVER_SHOVEL = ITEMS.register("silver_shovel", () -> new ShovelItem(SpelunkeryEquipmentMaterials.SILVER_TIER, toolProperties(SpelunkeryEquipmentMaterials.SILVER_TIER)));
    public static final RegistrySupplier<Item> SILVER_HOE = ITEMS.register("silver_hoe", () -> new HoeItem(SpelunkeryEquipmentMaterials.SILVER_TIER, toolProperties(SpelunkeryEquipmentMaterials.SILVER_TIER)));
    public static final RegistrySupplier<Item> SILVER_HELMET = ITEMS.register("silver_helmet", () -> new ArmorItem(SpelunkeryEquipmentMaterials.SILVER_ARMOR, ArmorItem.Type.HELMET, armorProperties(ArmorItem.Type.HELMET, 14)));
    public static final RegistrySupplier<Item> SILVER_CHESTPLATE = ITEMS.register("silver_chestplate", () -> new ArmorItem(SpelunkeryEquipmentMaterials.SILVER_ARMOR, ArmorItem.Type.CHESTPLATE, armorProperties(ArmorItem.Type.CHESTPLATE, 14)));
    public static final RegistrySupplier<Item> SILVER_LEGGINGS = ITEMS.register("silver_leggings", () -> new ArmorItem(SpelunkeryEquipmentMaterials.SILVER_ARMOR, ArmorItem.Type.LEGGINGS, armorProperties(ArmorItem.Type.LEGGINGS, 14)));
    public static final RegistrySupplier<Item> SILVER_BOOTS = ITEMS.register("silver_boots", () -> new ArmorItem(SpelunkeryEquipmentMaterials.SILVER_ARMOR, ArmorItem.Type.BOOTS, armorProperties(ArmorItem.Type.BOOTS, 14)));

    public static final RegistrySupplier<Item> INVAR_SWORD = ITEMS.register("invar_sword", () -> sword("invar_sword", SpelunkeryEquipmentMaterials.INVAR_TIER, 3));
    public static final RegistrySupplier<Item> INVAR_PICKAXE = ITEMS.register("invar_pickaxe", () -> new PickaxeItem(SpelunkeryEquipmentMaterials.INVAR_TIER, toolProperties(SpelunkeryEquipmentMaterials.INVAR_TIER)));
    public static final RegistrySupplier<Item> INVAR_AXE = ITEMS.register("invar_axe", () -> new AxeItem(SpelunkeryEquipmentMaterials.INVAR_TIER, toolProperties(SpelunkeryEquipmentMaterials.INVAR_TIER)));
    public static final RegistrySupplier<Item> INVAR_SHOVEL = ITEMS.register("invar_shovel", () -> new ShovelItem(SpelunkeryEquipmentMaterials.INVAR_TIER, toolProperties(SpelunkeryEquipmentMaterials.INVAR_TIER)));
    public static final RegistrySupplier<Item> INVAR_HOE = ITEMS.register("invar_hoe", () -> new HoeItem(SpelunkeryEquipmentMaterials.INVAR_TIER, toolProperties(SpelunkeryEquipmentMaterials.INVAR_TIER)));
    public static final RegistrySupplier<Item> INVAR_HELMET = ITEMS.register("invar_helmet", () -> new ArmorItem(SpelunkeryEquipmentMaterials.INVAR_ARMOR, ArmorItem.Type.HELMET, armorProperties(ArmorItem.Type.HELMET, 24)));
    public static final RegistrySupplier<Item> INVAR_CHESTPLATE = ITEMS.register("invar_chestplate", () -> new ArmorItem(SpelunkeryEquipmentMaterials.INVAR_ARMOR, ArmorItem.Type.CHESTPLATE, armorProperties(ArmorItem.Type.CHESTPLATE, 24)));
    public static final RegistrySupplier<Item> INVAR_LEGGINGS = ITEMS.register("invar_leggings", () -> new ArmorItem(SpelunkeryEquipmentMaterials.INVAR_ARMOR, ArmorItem.Type.LEGGINGS, armorProperties(ArmorItem.Type.LEGGINGS, 24)));
    public static final RegistrySupplier<Item> INVAR_BOOTS = ITEMS.register("invar_boots", () -> new ArmorItem(SpelunkeryEquipmentMaterials.INVAR_ARMOR, ArmorItem.Type.BOOTS, armorProperties(ArmorItem.Type.BOOTS, 24)));

    public static final RegistrySupplier<Item> ROSE_GOLD_SWORD = ITEMS.register("rose_gold_sword", () -> sword("rose_gold_sword", SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER, 3));
    public static final RegistrySupplier<Item> ROSE_GOLD_PICKAXE = ITEMS.register("rose_gold_pickaxe", () -> new PickaxeItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER, toolProperties(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER)));
    public static final RegistrySupplier<Item> ROSE_GOLD_AXE = ITEMS.register("rose_gold_axe", () -> new AxeItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER, toolProperties(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER)));
    public static final RegistrySupplier<Item> ROSE_GOLD_SHOVEL = ITEMS.register("rose_gold_shovel", () -> new ShovelItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER, toolProperties(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER)));
    public static final RegistrySupplier<Item> ROSE_GOLD_HOE = ITEMS.register("rose_gold_hoe", () -> new HoeItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER, toolProperties(SpelunkeryEquipmentMaterials.ROSE_GOLD_TIER)));
    public static final RegistrySupplier<Item> ROSE_GOLD_HELMET = ITEMS.register("rose_gold_helmet", () -> new ArmorItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_ARMOR, ArmorItem.Type.HELMET, armorProperties(ArmorItem.Type.HELMET, 12)));
    public static final RegistrySupplier<Item> ROSE_GOLD_CHESTPLATE = ITEMS.register("rose_gold_chestplate", () -> new ArmorItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_ARMOR, ArmorItem.Type.CHESTPLATE, armorProperties(ArmorItem.Type.CHESTPLATE, 12)));
    public static final RegistrySupplier<Item> ROSE_GOLD_LEGGINGS = ITEMS.register("rose_gold_leggings", () -> new ArmorItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_ARMOR, ArmorItem.Type.LEGGINGS, armorProperties(ArmorItem.Type.LEGGINGS, 12)));
    public static final RegistrySupplier<Item> ROSE_GOLD_BOOTS = ITEMS.register("rose_gold_boots", () -> new ArmorItem(SpelunkeryEquipmentMaterials.ROSE_GOLD_ARMOR, ArmorItem.Type.BOOTS, armorProperties(ArmorItem.Type.BOOTS, 12)));

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

    private static Item sword(String name, net.minecraft.world.item.Tier tier, int attackDamageBonus) {
        return new SwordItem(
                tier,
                new Item.Properties()
                        .durability(tier.getUses())
                        .attributes(SwordItem.createAttributes(tier, attackDamageBonus, -2.4F))
        );
    }

    private static Item.Properties toolProperties(net.minecraft.world.item.Tier tier) {
        return new Item.Properties().durability(tier.getUses());
    }

    private static Item.Properties armorProperties(ArmorItem.Type type, int durabilityMultiplier) {
        return new Item.Properties().durability(type.getDurability(durabilityMultiplier));
    }
}
