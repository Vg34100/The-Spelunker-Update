package net.vg.spelunkery.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.client.screen.FoundryScreen;
import net.vg.spelunkery.registry.SpelunkeryMenuTypes;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = Spelunkery.MOD_ID, value = Dist.CLIENT)
public final class SpelunkeryNeoForgeClient {
    private SpelunkeryNeoForgeClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Block render layers are now data-driven in MC 26.1.2 — set via block state JSON render_type field.
        // Item model predicates (bow/shield/helmet) are now data-driven — see assets/spelunkery/items/.
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(SpelunkeryMenuTypes.FOUNDRY.get(), FoundryScreen::new);
    }
}
