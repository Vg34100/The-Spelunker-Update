package net.vg.spelunkery.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.vg.spelunkery.client.SpelunkeryClient;
import net.vg.spelunkery.client.TopazPulseRenderer;

public final class SpelunkeryFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpelunkeryClient.init();
        LevelRenderEvents.BEFORE_GIZMOS.register(ctx -> TopazPulseRenderer.render());
    }
}
