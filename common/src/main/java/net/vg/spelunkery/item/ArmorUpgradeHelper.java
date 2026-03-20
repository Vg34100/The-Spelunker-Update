package net.vg.spelunkery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.CustomModelData;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemLore;
import net.vg.spelunkery.Spelunkery;

public final class ArmorUpgradeHelper {
    private static final String UPGRADE_KEY = "spelunkery_armor_upgrade";
    private static final ResourceLocation NICKEL_TOUGHNESS_ID = Spelunkery.id("nickel_plating_toughness");
    private static final ResourceLocation ROSE_GOLD_LUCK_ID = Spelunkery.id("rose_gold_filigree_luck");

    private ArmorUpgradeHelper() {
    }

    public static boolean isUpgradeableArmor(ItemStack stack) {
        return stack.is(Items.IRON_HELMET)
                || stack.is(Items.IRON_CHESTPLATE)
                || stack.is(Items.IRON_LEGGINGS)
                || stack.is(Items.IRON_BOOTS);
    }

    public static ArmorUpgrade getUpgrade(ItemStack stack) {
        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (customData.isEmpty()) {
            return null;
        }

        CompoundTag tag = customData.copyTag();
        return tag.contains(UPGRADE_KEY) ? ArmorUpgrade.byId(tag.getString(UPGRADE_KEY)) : null;
    }

    public static boolean hasUpgrade(ItemStack stack, ArmorUpgrade upgrade) {
        return getUpgrade(stack) == upgrade;
    }

    public static ItemStack applyUpgrade(ItemStack original, ArmorUpgrade upgrade) {
        ItemStack result = original.copy();

        CustomData.update(DataComponents.CUSTOM_DATA, result, tag -> tag.putString(UPGRADE_KEY, upgrade.id()));
        if (isUpgradeableArmor(result)) {
            result.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(upgrade.modelData()));
        }

        result.set(DataComponents.LORE, new ItemLore(java.util.List.of(
                Component.literal("Equipped: " + upgrade.title()).withStyle(ChatFormatting.GRAY),
                Component.literal(upgrade.effectText()).withStyle(ChatFormatting.BLUE)
        )));

        if (upgrade == ArmorUpgrade.NICKEL) {
            applyNickelDurability(result, original);
            addAttributeModifier(result, Attributes.ARMOR_TOUGHNESS, NICKEL_TOUGHNESS_ID, 1.0D, getSlotGroup(result));
        } else if (upgrade == ArmorUpgrade.ROSE_GOLD) {
            result.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
            addAttributeModifier(result, Attributes.LUCK, ROSE_GOLD_LUCK_ID, 1.0D, getSlotGroup(result));
        }

        return result;
    }

    public static void clearWitherIfSilverLined(net.minecraft.world.entity.player.Player player) {
        if (!player.hasEffect(net.minecraft.world.effect.MobEffects.WITHER)) {
            return;
        }

        for (ItemStack armor : player.getArmorSlots()) {
            if (hasUpgrade(armor, ArmorUpgrade.SILVER)) {
                player.removeEffect(net.minecraft.world.effect.MobEffects.WITHER);
                return;
            }
        }
    }

    private static void applyNickelDurability(ItemStack result, ItemStack original) {
        int oldMaxDamage = original.getMaxDamage();
        if (oldMaxDamage <= 0) {
            return;
        }

        int newMaxDamage = (int) Math.ceil(oldMaxDamage * 1.2D);
        int oldDamage = original.getDamageValue();
        double remainingRatio = (oldMaxDamage - oldDamage) / (double) oldMaxDamage;
        int newDamage = Math.max(0, newMaxDamage - (int) Math.round(newMaxDamage * remainingRatio));

        result.set(DataComponents.MAX_DAMAGE, newMaxDamage);
        result.set(DataComponents.DAMAGE, Math.min(newDamage, newMaxDamage - 1));
    }

    private static void addAttributeModifier(
            ItemStack stack,
            net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
            ResourceLocation modifierId,
            double amount,
            EquipmentSlotGroup slotGroup
    ) {
        ItemAttributeModifiers existing = stack.getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);
        stack.set(
                DataComponents.ATTRIBUTE_MODIFIERS,
                existing.withModifierAdded(attribute, new AttributeModifier(modifierId, amount, Operation.ADD_VALUE), slotGroup)
        );
    }

    private static EquipmentSlotGroup getSlotGroup(ItemStack stack) {
        if (stack.is(Items.IRON_HELMET)) {
            return EquipmentSlotGroup.HEAD;
        }
        if (stack.is(Items.IRON_CHESTPLATE)) {
            return EquipmentSlotGroup.CHEST;
        }
        if (stack.is(Items.IRON_LEGGINGS)) {
            return EquipmentSlotGroup.LEGS;
        }
        if (stack.is(Items.IRON_BOOTS)) {
            return EquipmentSlotGroup.FEET;
        }
        return EquipmentSlotGroup.ARMOR;
    }
}
