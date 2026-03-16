package net.vg.spelunkery.registry;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.menu.FoundryMenu;

public final class SpelunkeryMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Spelunkery.MOD_ID, Registries.MENU);

    public static final RegistrySupplier<MenuType<FoundryMenu>> FOUNDRY = MENUS.register(
            "foundry",
            () -> MenuRegistry.ofExtended(FoundryMenu::new)
    );

    private static boolean initialized;

    private SpelunkeryMenuTypes() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        MENUS.register();
    }
}
