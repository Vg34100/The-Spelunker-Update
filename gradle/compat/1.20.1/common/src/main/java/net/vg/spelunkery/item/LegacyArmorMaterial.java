package net.vg.spelunkery.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import java.util.Map;
import java.util.function.Supplier;

public record LegacyArmorMaterial(String name, Map<ArmorItem.Type, Integer> defense, int enchantment,
                                  float toughness, float knockback, Supplier<Ingredient> repair) implements ArmorMaterial {
    @Override public int getDurabilityForType(ArmorItem.Type type) {
        return (switch (type) { case BOOTS -> 13; case LEGGINGS -> 15; case CHESTPLATE -> 16; case HELMET -> 11; }) * 15;
    }
    @Override public int getDefenseForType(ArmorItem.Type type) { return defense.getOrDefault(type, 0); }
    @Override public int getEnchantmentValue() { return enchantment; }
    @Override public SoundEvent getEquipSound() { return SoundEvents.ARMOR_EQUIP_IRON; }
    @Override public Ingredient getRepairIngredient() { return repair.get(); }
    @Override public String getName() { return "spelunkery:" + name; }
    @Override public float getToughness() { return toughness; }
    @Override public float getKnockbackResistance() { return knockback; }
}
