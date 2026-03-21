package net.vg.spelunkery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.spelunkery.registry.SpelunkeryItems;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MinerHelmetHelper {
    private static final String GEM_KEY = "spelunkery_miner_helmet_gem";
    private static final int PASSIVE_DURATION = 220;

    private MinerHelmetHelper() {
    }

    public static boolean isMinerHelmet(ItemStack stack) {
        return stack.is(SpelunkeryItems.MINERS_HELMET.get());
    }

    public static MinerHelmetGem getGem(ItemStack stack) {
        if (!isMinerHelmet(stack)) {
            return null;
        }

        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (customData.isEmpty()) {
            return null;
        }

        CompoundTag tag = customData.copyTag();
        return tag.contains(GEM_KEY) ? MinerHelmetGem.byId(tag.getString(GEM_KEY)) : null;
    }

    public static ItemStack socket(ItemStack original, MinerHelmetGem gem) {
        ItemStack result = original.copy();
        CustomData.update(DataComponents.CUSTOM_DATA, result, tag -> tag.putString(GEM_KEY, gem.id()));
        result.set(DataComponents.LORE, new ItemLore(List.of(
                Component.literal("Socketed: " + gem.title()).withStyle(ChatFormatting.GRAY),
                Component.literal(gem.effectText()).withStyle(ChatFormatting.AQUA)
        )));
        return result;
    }

    public static void updateEquippedHelmet(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        MinerHelmetGem gem = getGem(helmet);
        if (gem == null) {
            return;
        }

        switch (gem) {
            case RUBY -> apply(player, MobEffects.FIRE_RESISTANCE);
            case SAPPHIRE -> apply(player, MobEffects.NIGHT_VISION);
            case DIAMOND -> apply(player, MobEffects.DAMAGE_RESISTANCE);
            case TOPAZ -> pulseOres(player);
            case AMETHYST -> pulseMobs(player);
            case EMERALD -> {
            }
        }
    }

    public static void onBlockMined(ServerPlayer player, BlockPos pos, BlockState state) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (getGem(helmet) != MinerHelmetGem.EMERALD || !isValuableMinedBlock(state)) {
            return;
        }

        ExperienceOrb.award((ServerLevel) player.level(), net.minecraft.world.phys.Vec3.atCenterOf(pos), 1);
    }

    private static void apply(Player player, net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect> effect) {
        player.addEffect(new MobEffectInstance(effect, PASSIVE_DURATION, 0, true, false, true));
    }

    private static void pulseOres(Player player) {
        if (!(player.level() instanceof ServerLevel level) || player.tickCount % 45 != 0) {
            return;
        }

        BlockPos center = player.blockPosition();
        List<BlockPos> ores = new ArrayList<>();
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-10, -6, -10), center.offset(10, 6, 10))) {
            BlockState state = level.getBlockState(pos);
            if (isValuableMinedBlock(state)) {
                ores.add(pos.immutable());
            }
        }

        ores.sort(Comparator.comparingDouble(pos -> pos.distSqr(center)));
        int shown = 0;
        for (BlockPos orePos : ores) {
            BlockPos markerPos = findVisiblePulsePosition(level, orePos, center);
            if (markerPos == null) {
                continue;
            }
            level.sendParticles(ParticleTypes.END_ROD, markerPos.getX() + 0.5D, markerPos.getY() + 0.55D, markerPos.getZ() + 0.5D, 3, 0.12D, 0.12D, 0.12D, 0.0D);
            shown++;
            if (shown >= 12) {
                break;
            }
        }
    }

    private static void pulseMobs(Player player) {
        if (player.tickCount % 90 != 0) {
            return;
        }

        for (LivingEntity entity : player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(12.0D), entity -> entity instanceof Monster && entity.isAlive())) {
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 18, 0, true, false, true));
        }
    }

    private static BlockPos findVisiblePulsePosition(ServerLevel level, BlockPos orePos, BlockPos playerPos) {
        double dx = playerPos.getX() + 0.5D - (orePos.getX() + 0.5D);
        double dy = playerPos.getY() + 0.5D - (orePos.getY() + 0.5D);
        double dz = playerPos.getZ() + 0.5D - (orePos.getZ() + 0.5D);
        double length = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (length < 0.001D) {
            return orePos;
        }

        double stepX = dx / length;
        double stepY = dy / length;
        double stepZ = dz / length;
        for (int step = 1; step <= 6; step++) {
            BlockPos testPos = BlockPos.containing(
                    orePos.getX() + 0.5D + stepX * step,
                    orePos.getY() + 0.5D + stepY * step,
                    orePos.getZ() + 0.5D + stepZ * step
            );
            if (level.getBlockState(testPos).isAir()) {
                return testPos;
            }
        }

        return null;
    }

    private static boolean isValuableMinedBlock(BlockState state) {
        String path = state.getBlockHolder().unwrapKey().map(key -> key.location().getPath()).orElse("");
        return path.endsWith("_ore")
                || path.equals("topaz_block")
                || path.equals("ruby_block")
                || path.equals("sapphire_block");
    }
}
