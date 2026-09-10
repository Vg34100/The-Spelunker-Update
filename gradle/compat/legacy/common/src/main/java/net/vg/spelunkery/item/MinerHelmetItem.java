package net.vg.spelunkery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class MinerHelmetItem extends ArmorItem {
    public MinerHelmetItem(Holder<ArmorMaterial> armorMaterial, Type type, Item.Properties properties) {
        super(armorMaterial, type, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        MinerHelmetGem gem = MinerHelmetHelper.getGem(stack);
        tooltipComponents.add(Component.literal("Socket in crafting with a gem.").withStyle(ChatFormatting.GRAY));
        if (gem == null) {
            tooltipComponents.add(Component.literal("No socketed gem").withStyle(ChatFormatting.DARK_GRAY));
        } else {
            tooltipComponents.add(Component.literal("Socketed: " + gem.title()).withStyle(ChatFormatting.AQUA));
            tooltipComponents.add(Component.literal(gem.effectText()).withStyle(ChatFormatting.BLUE));
        }
    }
}
