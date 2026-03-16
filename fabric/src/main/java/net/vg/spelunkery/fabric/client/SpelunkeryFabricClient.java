package net.vg.spelunkery.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.vg.spelunkery.client.SpelunkeryClient;

public final class SpelunkeryFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpelunkeryClient.init();
    }
}
