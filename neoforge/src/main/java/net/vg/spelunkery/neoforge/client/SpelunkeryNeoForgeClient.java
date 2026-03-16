package net.vg.spelunkery.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.client.SpelunkeryClient;

@EventBusSubscriber(modid = Spelunkery.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SpelunkeryNeoForgeClient {
    private SpelunkeryNeoForgeClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(SpelunkeryClient::init);
    }
}
