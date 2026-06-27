package net.vg.spelunkery.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.vg.spelunkery.client.SpelunkeryClient;

public final class SpelunkeryFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpelunkeryClient.init();
        // Block render layers are now data-driven — set via block state JSON render_type field.
        // Item model predicates (bow/shield/helmet) are now data-driven — see assets/spelunkery/items/.
    }
}
