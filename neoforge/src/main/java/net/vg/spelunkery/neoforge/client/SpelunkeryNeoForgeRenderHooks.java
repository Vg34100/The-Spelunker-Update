package net.vg.spelunkery.neoforge.client;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.client.TopazPulseRenderer;

@EventBusSubscriber(modid = Spelunkery.MOD_ID, value = Dist.CLIENT)
public final class SpelunkeryNeoForgeRenderHooks {
    private SpelunkeryNeoForgeRenderHooks() {
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            TopazPulseRenderer.render(event.getPoseStack(), Minecraft.getInstance().renderBuffers().bufferSource(), event.getCamera());
        }
    }
}
