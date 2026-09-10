package net.vg.spelunkery.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
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
    private static final RenderType ORE_PULSE_LINES = RenderType.create(
            "spelunkery:ore_pulse_lines",
            DefaultVertexFormat.POSITION_COLOR_NORMAL,
            VertexFormat.Mode.LINES,
            1536,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderType.RENDERTYPE_LINES_SHADER)
                    .setLineState(new RenderType.LineStateShard(java.util.OptionalDouble.of(2.0D)))
                    .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                    .setCullState(RenderType.NO_CULL)
                    .setDepthTestState(RenderType.NO_DEPTH_TEST)
                    .createCompositeState(false)
    );

    private TopazPulseRenderer() {
    }

    public static void render(PoseStack poseStack, MultiBufferSource consumers, Camera camera) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || minecraft.player == null || poseStack == null || consumers == null) {
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
        long phase = tick % PULSE_PERIOD_TICKS;
        if (CACHED_TARGETS.isEmpty() || phase >= PULSE_VISIBLE_TICKS) {
            return;
        }

        float alpha = (float) Math.sin(Math.PI * phase / PULSE_VISIBLE_TICKS);
        Vec3 cameraPos = camera.getPosition();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        var lineBuffer = consumers.getBuffer(ORE_PULSE_LINES);
        for (BlockPos pos : CACHED_TARGETS) {
            poseStack.pushPose();
            poseStack.translate(pos.getX() - cameraPos.x, pos.getY() - cameraPos.y, pos.getZ() - cameraPos.z);
            LevelRenderer.renderLineBox(poseStack, lineBuffer, 0.02D, 0.02D, 0.02D, 0.98D, 0.98D, 0.98D, 0.98F, 0.86F, 0.22F, alpha);
            poseStack.popPose();
        }
        RenderSystem.disableBlend();
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
        String path = state.getBlockHolder().unwrapKey().map(key -> key.location().getPath()).orElse("");
        return path.endsWith("_ore")
                || path.equals("topaz_block")
                || path.equals("ruby_block")
                || path.equals("sapphire_block");
    }
}
