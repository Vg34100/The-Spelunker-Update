package net.vg.spelunkery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.vg.spelunkery.Spelunkery;

public final class ArmorUpgradeHelper {
    private static final String UPGRADE_KEY = "spelunkery_armor_upgrade";
    private static final ResourceLocation NICKEL_TOUGHNESS_ID = Spelunkery.id("nickel_plating_toughness");
    private static final ResourceLocation ROSE_GOLD_LUCK_ID = Spelunkery.id("rose_gold_filigree_luck");

    private ArmorUpgradeHelper() {
    }

    public static boolean isUpgradeableArmor(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem;
    }

    public static ArmorUpgrade getUpgrade(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) return null;
        return tag.contains(UPGRADE_KEY) ? ArmorUpgrade.byId(tag.getString(UPGRADE_KEY)) : null;
    }

    public static boolean hasUpgrade(ItemStack stack, ArmorUpgrade upgrade) {
        return getUpgrade(stack) == upgrade;
    }

    public static ItemStack applyUpgrade(ItemStack original, ArmorUpgrade upgrade) {
        ItemStack result = original.copy();

        result.getOrCreateTag().putString(UPGRADE_KEY, upgrade.id());
        if (isUpgradeableArmor(result) && isIronArmor(result)) {
            result.getOrCreateTag().putInt("CustomModelData", upgrade.modelData());
        }

        MinerHelmetHelper.setLore(result, java.util.List.of(
                Component.literal("Equipped: " + upgrade.title()).withStyle(ChatFormatting.GRAY),
                Component.literal(upgrade.effectText()).withStyle(ChatFormatting.BLUE)
        ));

        if (upgrade == ArmorUpgrade.NICKEL) {
            applyNickelDurability(result, original);
            addAttributeModifier(result, Attributes.ARMOR_TOUGHNESS, NICKEL_TOUGHNESS_ID, 1.0D);
        } else if (upgrade == ArmorUpgrade.ROSE_GOLD) {
            addAttributeModifier(result, Attributes.LUCK, ROSE_GOLD_LUCK_ID, 1.0D);
        }

        return result;
    }

    public static void updateEquippedArmorEffects(Player player) {
        if (player.hasEffect(MobEffects.WITHER) && countPieces(player, ArmorUpgrade.SILVER) > 0) {
            player.removeEffect(MobEffects.WITHER);
        }
    }

    private static int countPieces(Player player, ArmorUpgrade upgrade) {
        int count = 0;
        for (ItemStack armor : player.getArmorSlots()) {
            if (hasUpgrade(armor, upgrade)) {
                count++;
            }
        }
        return count;
    }

    private static void addAttributeModifier(ItemStack stack, net.minecraft.world.entity.ai.attributes.Attribute attribute, ResourceLocation id, double amount) {
        if (!(stack.getItem() instanceof ArmorItem armor)) return;
        var slot = armor.getEquipmentSlot();
        // NBT modifiers replace defaults in 1.20.1: retain the complete current set.
        var defaults = com.google.common.collect.ImmutableMultimap.copyOf(stack.getAttributeModifiers(slot));
        stack.removeTagKey("AttributeModifiers");
        defaults.forEach((existing, modifier) -> stack.addAttributeModifier(existing, modifier, slot));
        stack.addAttributeModifier(attribute, new AttributeModifier(
                java.util.UUID.nameUUIDFromBytes(id.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8)),
                id.toString(), amount, Operation.ADDITION), slot);
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

        result.setDamageValue(Math.min(newDamage, newMaxDamage - 1));
    }

    private static boolean isIronArmor(ItemStack stack) {
        return stack.is(Items.IRON_HELMET)
                || stack.is(Items.IRON_CHESTPLATE)
                || stack.is(Items.IRON_LEGGINGS)
                || stack.is(Items.IRON_BOOTS);
    }

}
