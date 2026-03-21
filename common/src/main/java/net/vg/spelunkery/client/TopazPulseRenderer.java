package net.vg.spelunkery.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.vg.spelunkery.item.MinerHelmetGem;
import net.vg.spelunkery.item.MinerHelmetHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class TopazPulseRenderer {
    private static final List<BlockPos> CACHED_TARGETS = new ArrayList<>();
    private static long lastRefreshTick = Long.MIN_VALUE;

    private TopazPulseRenderer() {
    }

    public static void render(PoseStack poseStack, MultiBufferSource consumers, Camera camera) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || minecraft.player == null || poseStack == null || consumers == null) {
            return;
        }

        ItemStack helmet = minecraft.player.getItemBySlot(EquipmentSlot.HEAD);
        if (MinerHelmetHelper.getGem(helmet) != MinerHelmetGem.TOPAZ) {
            CACHED_TARGETS.clear();
            lastRefreshTick = Long.MIN_VALUE;
            return;
        }

        refreshTargets(minecraft);
        if (CACHED_TARGETS.isEmpty()) {
            return;
        }

        Vec3 cameraPos = camera.getPosition();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        var lineBuffer = consumers.getBuffer(RenderType.lines());
        for (BlockPos pos : CACHED_TARGETS) {
            poseStack.pushPose();
            poseStack.translate(pos.getX() - cameraPos.x, pos.getY() - cameraPos.y, pos.getZ() - cameraPos.z);
            LevelRenderer.renderLineBox(poseStack, lineBuffer, 0.02D, 0.02D, 0.02D, 0.98D, 0.98D, 0.98D, 0.98F, 0.86F, 0.22F, 0.95F);
            poseStack.popPose();
        }
        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }

    private static void refreshTargets(Minecraft minecraft) {
        long tick = minecraft.level.getGameTime();
        if (tick == lastRefreshTick || tick % 20 != 0) {
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
