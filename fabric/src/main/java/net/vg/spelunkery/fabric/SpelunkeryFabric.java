package net.vg.spelunkery.fabric;

import net.vg.spelunkery.Spelunkery;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.vg.spelunkery.worldgen.SpelunkeryWorldgen;

public final class SpelunkeryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Spelunkery.init();
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                SpelunkeryWorldgen.TIN_ORE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                SpelunkeryWorldgen.NICKEL_ORE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                SpelunkeryWorldgen.SILVER_ORE_PLACED
        );
    }
}
