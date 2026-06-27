package net.vg.spelunkery.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.spelunkery.gameplay.SpelunkeryGameplayHelper;
import net.vg.spelunkery.item.MinerHelmetGem;
import net.vg.spelunkery.item.MinerHelmetHelper;
import net.vg.spelunkery.registry.SpelunkeryEffects;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class TopazPulseRenderer {
    private static final int PULSE_PERIOD_TICKS = 120;
    private static final int PULSE_VISIBLE_TICKS = 50;
    private static final List<BlockPos> CACHED_TARGETS = new ArrayList<>();
    private static long lastRefreshTick = Long.MIN_VALUE;

    private TopazPulseRenderer() {
    }

    public static void render() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || minecraft.player == null) {
            return;
        }

        ItemStack helmet = minecraft.player.getItemBySlot(EquipmentSlot.HEAD);
        boolean helmetActive = MinerHelmetHelper.getGem(helmet) == MinerHelmetGem.TOPAZ;
        boolean brewActive = minecraft.player.hasEffect(SpelunkeryGameplayHelper.holder(SpelunkeryEffects.SPELUNKING.get()));
        if (!helmetActive && !brewActive) {
            CACHED_TARGETS.clear();
            lastRefreshTick = Long.MIN_VALUE;
            return;
        }

        long tick = minecraft.level.getGameTime();
        refreshTargets(minecraft);
        if (CACHED_TARGETS.isEmpty() || (!brewActive && tick % PULSE_PERIOD_TICKS >= PULSE_VISIBLE_TICKS)) {
            return;
        }

        // TODO: Re-implement ore outline rendering using the MC 26.1.2 Gizmos API.
        // LevelRenderer.renderLineBox was removed; the new system uses Gizmos.cuboid(pos, GizmoStyle.stroke(...)).
    }

    private static void refreshTargets(Minecraft minecraft) {
        long tick = minecraft.level.getGameTime();
        if (tick == lastRefreshTick || tick % PULSE_PERIOD_TICKS != 0) {
            return;
        }

        lastRefreshTick = tick;
        CACHED_TARGETS.clear();
        BlockPos center = minecraft.player.blockPosition();
        List<BlockPos> found = new ArrayList<>();
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-10, -6, -10), center.offset(10, 6, 10))) {
            BlockState state = minecraft.level.getBlockState(pos);
            if (isValuableBlock(state)) {
                found.add(pos.immutable());
            }
        }

        found.sort(Comparator.comparingDouble(pos -> pos.distSqr(center)));
        for (int i = 0; i < found.size() && i < 12; i++) {
            CACHED_TARGETS.add(found.get(i));
        }
    }

    private static boolean isValuableBlock(BlockState state) {
        String path = state.getBlock().builtInRegistryHolder().unwrapKey()
                .map(key -> key.identifier().getPath()).orElse("");
        return path.endsWith("_ore")
                || path.equals("topaz_block")
                || path.equals("ruby_block")
                || path.equals("sapphire_block");
    }
}
