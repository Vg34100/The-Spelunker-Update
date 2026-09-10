package net.vg.spelunkery.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.item.SimpleSpelunkeryTier;

import java.util.List;
import java.util.Map;

public final class SpelunkeryEquipmentMaterials {
    public static final Tier BRONZE_TIER = new SimpleSpelunkeryTier(320, 6.5F, 2.0F, BlockTags.INCORRECT_FOR_IRON_TOOL, 11, () -> Ingredient.of(SpelunkeryItems.BRONZE_INGOT.get()));
    public static final Tier SILVER_TIER = new SimpleSpelunkeryTier(210, 6.0F, 2.0F, BlockTags.INCORRECT_FOR_IRON_TOOL, 18, () -> Ingredient.of(SpelunkeryItems.SILVER_INGOT.get()));
    public static final Tier INVAR_TIER = new SimpleSpelunkeryTier(780, 7.5F, 2.5F, BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 12, () -> Ingredient.of(SpelunkeryItems.INVAR_INGOT.get()));
    public static final Tier ROSE_GOLD_TIER = new SimpleSpelunkeryTier(180, 11.0F, 1.0F, BlockTags.INCORRECT_FOR_IRON_TOOL, 24, () -> Ingredient.of(SpelunkeryItems.ROSE_GOLD_INGOT.get()));

    public static final Holder<ArmorMaterial> BRONZE_ARMOR = Holder.direct(material(
            "bronze",
            Map.of(
                    ArmorItem.Type.BOOTS, 2,
                    ArmorItem.Type.LEGGINGS, 5,
                    ArmorItem.Type.CHESTPLATE, 6,
                    ArmorItem.Type.HELMET, 2
            ),
            11,
            0.0F,
            0.0F,
            () -> Ingredient.of(SpelunkeryItems.BRONZE_INGOT.get())
    ));
    public static final Holder<ArmorMaterial> SILVER_ARMOR = Holder.direct(material(
            "silver",
            Map.of(
                    ArmorItem.Type.BOOTS, 2,
                    ArmorItem.Type.LEGGINGS, 5,
                    ArmorItem.Type.CHESTPLATE, 5,
                    ArmorItem.Type.HELMET, 2
            ),
            18,
            0.0F,
            0.0F,
            () -> Ingredient.of(SpelunkeryItems.SILVER_INGOT.get())
    ));
    public static final Holder<ArmorMaterial> INVAR_ARMOR = Holder.direct(material(
            "invar",
            Map.of(
                    ArmorItem.Type.BOOTS, 3,
                    ArmorItem.Type.LEGGINGS, 6,
                    ArmorItem.Type.CHESTPLATE, 8,
                    ArmorItem.Type.HELMET, 3
            ),
            12,
            1.0F,
            0.0F,
            () -> Ingredient.of(SpelunkeryItems.INVAR_INGOT.get())
    ));
    public static final Holder<ArmorMaterial> ROSE_GOLD_ARMOR = Holder.direct(material(
            "rose_gold",
            Map.of(
                    ArmorItem.Type.BOOTS, 2,
                    ArmorItem.Type.LEGGINGS, 5,
                    ArmorItem.Type.CHESTPLATE, 6,
                    ArmorItem.Type.HELMET, 2
            ),
            24,
            0.0F,
            0.0F,
            () -> Ingredient.of(SpelunkeryItems.ROSE_GOLD_INGOT.get())
    ));
    public static final Holder<ArmorMaterial> MINERS_HELMET_ARMOR = Holder.direct(material(
            "miners_helmet",
            Map.of(
                    ArmorItem.Type.BOOTS, 0,
                    ArmorItem.Type.LEGGINGS, 0,
                    ArmorItem.Type.CHESTPLATE, 0,
                    ArmorItem.Type.HELMET, 2
            ),
            12,
            0.0F,
            0.0F,
            () -> Ingredient.of(SpelunkeryItems.BRONZE_INGOT.get())
    ));

    private SpelunkeryEquipmentMaterials() {
    }

    private static ArmorMaterial material(
            String name,
            Map<ArmorItem.Type, Integer> defense,
            int enchantmentValue,
            float toughness,
            float knockbackResistance,
            java.util.function.Supplier<Ingredient> repairIngredient
    ) {
        return new ArmorMaterial(
                defense,
                enchantmentValue,
                SoundEvents.ARMOR_EQUIP_IRON,
                repairIngredient,
                List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Spelunkery.MOD_ID, name))),
                toughness,
                knockbackResistance
        );
    }
}
