package net.vg.spelunkery.client;

import dev.architectury.registry.menu.MenuRegistry;
import net.vg.spelunkery.client.screen.FoundryScreen;
import net.vg.spelunkery.registry.SpelunkeryMenuTypes;

public final class SpelunkeryClient {
    private SpelunkeryClient() {
    }

    public static void init() {
        MenuRegistry.registerScreenFactory(SpelunkeryMenuTypes.FOUNDRY.get(), FoundryScreen::new);
    }
}
