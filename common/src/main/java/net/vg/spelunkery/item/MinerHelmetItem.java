package net.vg.spelunkery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class MinerHelmetItem extends Item {
    public MinerHelmetItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        MinerHelmetGem gem = MinerHelmetHelper.getGem(stack);
        consumer.accept(Component.literal("Socket in crafting with a gem.").withStyle(ChatFormatting.GRAY));
        if (gem == null) {
            consumer.accept(Component.literal("No socketed gem").withStyle(ChatFormatting.DARK_GRAY));
        } else {
            consumer.accept(Component.literal("Socketed: " + gem.title()).withStyle(ChatFormatting.AQUA));
            consumer.accept(Component.literal(gem.effectText()).withStyle(ChatFormatting.BLUE));
        }
    }
}
