package net.vg.spelunkery.gameplay;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.vg.spelunkery.registry.SpelunkeryEffects;
import net.vg.spelunkery.registry.SpelunkeryItems;

public final class SpelunkeryGameplayHelper {
    private static final int DANGER_SENSE_RADIUS = 18;

    private SpelunkeryGameplayHelper() {
    }

    public static void updatePlayerEffects(Player player) {
        if (player.level().isClientSide) {
            return;
        }

        if (player.hasEffect(holder(SpelunkeryEffects.DANGER_SENSE.get()))) {
            for (LivingEntity entity : player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(DANGER_SENSE_RADIUS), entity -> entity instanceof Monster && entity.isAlive())) {
                entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 14, 0, true, false, true));
            }
        }
    }

    public static void onBlockMined(Player player, BlockPos pos, BlockState state) {
        if (!player.hasEffect(holder(SpelunkeryEffects.MINERS_FOCUS.get())) || !isValuableMinedBlock(state)) {
            return;
        }

        ExperienceOrb.award((ServerLevel) player.level(), Vec3.atCenterOf(pos), 1);
    }

    public static boolean shouldDropBatWing(LivingEntity entity) {
        return entity instanceof Bat bat && bat.getRandom().nextFloat() < 0.67F;
    }

    private static boolean isValuableMinedBlock(BlockState state) {
        String path = state.getBlockHolder().unwrapKey().map(key -> key.location().getPath()).orElse("");
        return path.endsWith("_ore")
                || path.equals("topaz_block")
                || path.equals("ruby_block")
                || path.equals("sapphire_block");
    }

    public static Holder<MobEffect> holder(MobEffect effect) {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
    }
}
