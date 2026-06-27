package net.vg.spelunkery.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.vg.spelunkery.Spelunkery;

import java.util.Map;

public final class SpelunkeryEquipmentMaterials {
    public static final ToolMaterial BRONZE_TIER = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            320,
            6.5F,
            2.0F,
            11,
            repairTag("repairs_bronze_tool")
    );
    public static final ToolMaterial SILVER_TIER = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            210,
            6.0F,
            2.0F,
            18,
            repairTag("repairs_silver_tool")
    );
    public static final ToolMaterial INVAR_TIER = new ToolMaterial(
            BlockTags.INCORRECT_FOR_DIAMOND_TOOL,
            780,
            7.5F,
            2.5F,
            12,
            repairTag("repairs_invar_tool")
    );
    public static final ToolMaterial ROSE_GOLD_TIER = new ToolMaterial(
            BlockTags.INCORRECT_FOR_IRON_TOOL,
            180,
            11.0F,
            1.0F,
            24,
            repairTag("repairs_rose_gold_tool")
    );

    public static final ArmorMaterial BRONZE_ARMOR = new ArmorMaterial(
            15,
            Map.of(ArmorType.BOOTS, 2, ArmorType.LEGGINGS, 5, ArmorType.CHESTPLATE, 6, ArmorType.HELMET, 2, ArmorType.BODY, 0),
            11,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            repairTag("repairs_bronze_armor"),
            assetKey("bronze")
    );
    public static final ArmorMaterial SILVER_ARMOR = new ArmorMaterial(
            14,
            Map.of(ArmorType.BOOTS, 2, ArmorType.LEGGINGS, 5, ArmorType.CHESTPLATE, 5, ArmorType.HELMET, 2, ArmorType.BODY, 0),
            18,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            repairTag("repairs_silver_armor"),
            assetKey("silver")
    );
    public static final ArmorMaterial INVAR_ARMOR = new ArmorMaterial(
            24,
            Map.of(ArmorType.BOOTS, 3, ArmorType.LEGGINGS, 6, ArmorType.CHESTPLATE, 8, ArmorType.HELMET, 3, ArmorType.BODY, 0),
            12,
            SoundEvents.ARMOR_EQUIP_IRON,
            1.0F,
            0.0F,
            repairTag("repairs_invar_armor"),
            assetKey("invar")
    );
    public static final ArmorMaterial ROSE_GOLD_ARMOR = new ArmorMaterial(
            12,
            Map.of(ArmorType.BOOTS, 2, ArmorType.LEGGINGS, 5, ArmorType.CHESTPLATE, 6, ArmorType.HELMET, 2, ArmorType.BODY, 0),
            24,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            repairTag("repairs_rose_gold_armor"),
            assetKey("rose_gold")
    );
    public static final ArmorMaterial MINERS_HELMET_ARMOR = new ArmorMaterial(
            17,
            Map.of(ArmorType.BOOTS, 0, ArmorType.LEGGINGS, 0, ArmorType.CHESTPLATE, 0, ArmorType.HELMET, 2, ArmorType.BODY, 0),
            12,
            SoundEvents.ARMOR_EQUIP_IRON,
            0.0F,
            0.0F,
            repairTag("repairs_bronze_armor"),
            assetKey("miners_helmet")
    );

    private SpelunkeryEquipmentMaterials() {
    }

    private static TagKey<Item> repairTag(String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Spelunkery.MOD_ID, name));
    }

    private static ResourceKey<EquipmentAsset> assetKey(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(Spelunkery.MOD_ID, name));
    }
}
