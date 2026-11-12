package net.vg.spelunkery.forge.client;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.client.TopazPulseRenderer;

@Mod.EventBusSubscriber(modid = Spelunkery.MOD_ID, value = Dist.CLIENT)
public final class SpelunkeryForgeRenderHooks {
    private SpelunkeryForgeRenderHooks() {
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            TopazPulseRenderer.render(event.getPoseStack(), Minecraft.getInstance().renderBuffers().bufferSource(), event.getCamera());
        }
    }
}
