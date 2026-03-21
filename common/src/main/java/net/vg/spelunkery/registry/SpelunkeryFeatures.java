package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.worldgen.CrystalSpikesFeature;
import net.vg.spelunkery.worldgen.ScorchedDripstoneFeature;

public final class SpelunkeryFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Spelunkery.MOD_ID, Registries.FEATURE);
    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> CRYSTAL_SPIKES = FEATURES.register("crystal_spikes", () -> new CrystalSpikesFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistrySupplier<Feature<NoneFeatureConfiguration>> SCORCHED_DRIPSTONE = FEATURES.register("scorched_dripstone", () -> new ScorchedDripstoneFeature(NoneFeatureConfiguration.CODEC));

    private static boolean initialized;

    private SpelunkeryFeatures() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        FEATURES.register();
    }
}
