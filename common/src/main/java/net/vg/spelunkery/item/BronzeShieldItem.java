package net.vg.spelunkery.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.phys.Vec3;
import net.vg.spelunkery.registry.SpelunkeryItems;

public class BronzeShieldItem extends ShieldItem {
    private static final int BASH_COOLDOWN_TICKS = 20;
    private static final double MIN_HORIZONTAL_SPEED_SQUARED = 0.0025D;
    private static final double MOVEMENT_ALIGNMENT_THRESHOLD = 0.2D;
    private static final double LOOK_ALIGNMENT_THRESHOLD = 0.6D;
    private static final double BASH_STRENGTH = 1.35D;

    public BronzeShieldItem(Properties properties) {
        super(properties);
    }

    public static void tryBash(LivingEntity blocker, Entity attacker) {
        if (attacker == null || attacker == blocker || !isBlockingWithBronzeShield(blocker)) {
            return;
        }

        Vec3 toAttacker = new Vec3(attacker.getX() - blocker.getX(), 0.0D, attacker.getZ() - blocker.getZ());
        if (toAttacker.lengthSqr() < 1.0E-4D) {
            return;
        }
        Vec3 toAttackerDirection = toAttacker.normalize();

        Vec3 movement = blocker.getDeltaMovement();
        Vec3 horizontalMovement = new Vec3(movement.x, 0.0D, movement.z);
        boolean movingIntoAttacker = horizontalMovement.lengthSqr() >= MIN_HORIZONTAL_SPEED_SQUARED
                && horizontalMovement.normalize().dot(toAttackerDirection) >= MOVEMENT_ALIGNMENT_THRESHOLD;

        Vec3 lookVector = blocker.getLookAngle();
        Vec3 horizontalLook = new Vec3(lookVector.x, 0.0D, lookVector.z);
        boolean facingAttacker = horizontalLook.lengthSqr() > 1.0E-4D
                && horizontalLook.normalize().dot(toAttackerDirection) >= LOOK_ALIGNMENT_THRESHOLD;

        if (!movingIntoAttacker && !facingAttacker) {
            return;
        }

        if (blocker instanceof Player player) {
            ItemStack shieldStack = player.getUseItem();
            if (player.getCooldowns().isOnCooldown(shieldStack)) {
                return;
            }
            player.getCooldowns().addCooldown(shieldStack, BASH_COOLDOWN_TICKS);
        }

        double xKnockback = blocker.getX() - attacker.getX();
        double zKnockback = blocker.getZ() - attacker.getZ();
        if (attacker instanceof LivingEntity livingAttacker) {
            livingAttacker.knockback(BASH_STRENGTH, xKnockback, zKnockback);
            livingAttacker.push(0.0D, 0.1D, 0.0D);
            livingAttacker.hurtMarked = true;
        } else {
            attacker.push(-xKnockback * 0.2D, 0.2D, -zKnockback * 0.2D);
            attacker.hurtMarked = true;
        }

        blocker.level().playSound(
                null,
                blocker.getX(),
                blocker.getY(),
                blocker.getZ(),
                SoundEvents.SHIELD_BLOCK,
                SoundSource.PLAYERS,
                1.0F,
                0.85F + blocker.getRandom().nextFloat() * 0.2F
        );
    }

    public static boolean isBlockingWithBronzeShield(LivingEntity entity) {
        ItemStack useItem = entity.getUseItem();
        return entity.isBlocking() && !useItem.isEmpty() && useItem.is(SpelunkeryItems.BRONZE_SHIELD.get());
    }
}
