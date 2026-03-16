package net.vg.spelunkery;

import net.minecraft.resources.ResourceLocation;
import net.vg.spelunkery.registry.SpelunkeryBlockEntities;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.vg.spelunkery.registry.SpelunkeryCreativeTabs;
import net.vg.spelunkery.registry.SpelunkeryItems;
import net.vg.spelunkery.registry.SpelunkeryMenuTypes;

public final class Spelunkery {
    public static final String MOD_ID = "spelunkery";

    public static void init() {
        SpelunkeryBlocks.init();
        SpelunkeryItems.init();
        SpelunkeryBlockEntities.init();
        SpelunkeryMenuTypes.init();
        SpelunkeryCreativeTabs.init();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
