package net.vg.spelunkery.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;

public class ElectrumBowItem extends BowItem {
    private static final float DRAW_SPEED_MULTIPLIER = 4.0F / 3.0F;

    public ElectrumBowItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (!(livingEntity instanceof Player player)) {
            return false;
        }

        ItemStack projectile = player.getProjectile(stack);
        if (projectile.isEmpty()) {
            return false;
        }

        int useTicks = this.getUseDuration(stack, livingEntity) - timeLeft;
        float power = getPowerForTime(Math.round(useTicks * DRAW_SPEED_MULTIPLIER));
        if (power < 0.1F) {
            return false;
        }

        List<ItemStack> drawnProjectiles = draw(stack, projectile, player);
        if (level instanceof ServerLevel serverLevel && !drawnProjectiles.isEmpty()) {
            this.shoot(
                    serverLevel,
                    player,
                    player.getUsedItemHand(),
                    stack,
                    drawnProjectiles,
                    power * 3.0F,
                    1.0F,
                    power == 1.0F,
                    null
            );
        }

        level.playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ARROW_SHOOT,
                SoundSource.PLAYERS,
                1.0F,
                1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + power * 0.5F
        );
        player.awardStat(Stats.ITEM_USED.get(this));
        return true;
    }
}
