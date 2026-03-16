package net.vg.spelunkery.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.vg.spelunkery.Spelunkery;

public final class SpelunkeryWorldgen {
    public static final ResourceKey<PlacedFeature> TIN_ORE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, Spelunkery.id("tin_ore"));

    private SpelunkeryWorldgen() {
    }
}
