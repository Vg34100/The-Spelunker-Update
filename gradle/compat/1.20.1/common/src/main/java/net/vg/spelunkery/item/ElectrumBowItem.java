package net.vg.spelunkery.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ElectrumBowItem extends BowItem {
    public ElectrumBowItem(Properties properties) { super(properties); }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        // Retain native arrow/enchantment/infinity handling with the same 4/3 draw rate.
        int used = getUseDuration(stack) - timeLeft;
        super.releaseUsing(stack, level, entity, getUseDuration(stack) - Math.round(used * (4.0F / 3.0F)));
    }
}
