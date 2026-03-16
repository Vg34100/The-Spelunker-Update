package net.vg.spelunkery.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.vg.spelunkery.Spelunkery;

public final class SpelunkeryWorldgen {
    public static final ResourceKey<PlacedFeature> TIN_ORE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, Spelunkery.id("tin_ore"));
    public static final ResourceKey<PlacedFeature> NICKEL_ORE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, Spelunkery.id("nickel_ore"));
    public static final ResourceKey<PlacedFeature> SILVER_ORE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, Spelunkery.id("silver_ore"));
    public static final ResourceKey<PlacedFeature> TOPAZ_GEODE_PLACED = ResourceKey.create(Registries.PLACED_FEATURE, Spelunkery.id("topaz_geode"));

    private SpelunkeryWorldgen() {
    }
}
