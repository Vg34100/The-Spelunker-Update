package net.vg.spelunkery.registry;

import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.vg.spelunkery.Spelunkery;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class SpelunkeryArmorMaterials {
    private static final Map<ArmorItem.Type, Integer> IRON_DEFENSE = Map.of(
            ArmorItem.Type.BOOTS, 2,
            ArmorItem.Type.LEGGINGS, 5,
            ArmorItem.Type.CHESTPLATE, 6,
            ArmorItem.Type.HELMET, 2
    );

    public static final Holder<ArmorMaterial> NICKEL_PLATED_IRON = Holder.direct(create(
            "nickel_plated_iron",
            22,
            12,
            () -> Ingredient.of(SpelunkeryItems.NICKEL_INGOT.get()),
            1.0F,
            0.0F
    ));

    public static final Holder<ArmorMaterial> SILVER_LINED_IRON = Holder.direct(create(
            "silver_lined_iron",
            17,
            11,
            () -> Ingredient.of(SpelunkeryItems.SILVER_INGOT.get()),
            0.0F,
            0.0F
    ));

    public static final Holder<ArmorMaterial> ROSE_GOLD_PLATED_IRON = Holder.direct(create(
            "rose_gold_plated_iron",
            18,
            20,
            () -> Ingredient.of(SpelunkeryItems.ROSE_GOLD_INGOT.get()),
            0.0F,
            0.0F
    ));

    private SpelunkeryArmorMaterials() {
    }

    private static ArmorMaterial create(
            String name,
            int durabilityMultiplier,
            int enchantmentValue,
            Supplier<Ingredient> repairIngredient,
            float toughness,
            float knockbackResistance
    ) {
        return new ArmorMaterial(
                IRON_DEFENSE,
                enchantmentValue,
                SoundEvents.ARMOR_EQUIP_IRON,
                repairIngredient,
                List.of(new ArmorMaterial.Layer(Spelunkery.id(name))),
                toughness,
                knockbackResistance
        );
    }
}
