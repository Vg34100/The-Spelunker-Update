package net.vg.spelunkery.client;

import dev.architectury.event.events.client.ClientRecipeUpdateEvent;
import dev.architectury.registry.client.gui.MenuScreenRegistry;
import net.vg.spelunkery.client.screen.FoundryScreen;
import net.vg.spelunkery.compat.jei.SpelunkeryJeiPlugin;
import net.vg.spelunkery.registry.SpelunkeryMenuTypes;

public final class SpelunkeryClient {
    private SpelunkeryClient() {
    }

    public static void init() {
        MenuScreenRegistry.registerScreenFactory(SpelunkeryMenuTypes.FOUNDRY.get(), FoundryScreen::new);
        ClientRecipeUpdateEvent.EVENT.register(manager -> SpelunkeryJeiPlugin.onRecipesUpdated(manager));
    }
}
