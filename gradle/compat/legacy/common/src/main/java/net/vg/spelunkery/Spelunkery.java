package net.vg.spelunkery;

import net.minecraft.resources.ResourceLocation;
import net.vg.spelunkery.registry.SpelunkeryBlockEntities;
import net.vg.spelunkery.registry.SpelunkeryBiomeSources;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.vg.spelunkery.registry.SpelunkeryCreativeTabs;
import net.vg.spelunkery.registry.SpelunkeryEffects;
import net.vg.spelunkery.registry.SpelunkeryFeatures;
import net.vg.spelunkery.registry.SpelunkeryItems;
import net.vg.spelunkery.registry.SpelunkeryMenuTypes;
import net.vg.spelunkery.registry.SpelunkeryRecipeTypes;

public final class Spelunkery {
    public static final String MOD_ID = "spelunkery";

    public static void init() {
        SpelunkeryBiomeSources.init();
        SpelunkeryFeatures.init();
        SpelunkeryBlocks.init();
        SpelunkeryEffects.init();
        SpelunkeryItems.init();
        SpelunkeryBlockEntities.init();
        SpelunkeryMenuTypes.init();
        SpelunkeryRecipeTypes.init();
        SpelunkeryCreativeTabs.init();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
