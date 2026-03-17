package net.vg.spelunkery.fabric;

import net.vg.spelunkery.Spelunkery;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.vg.spelunkery.item.BronzeShieldItem;
import net.vg.spelunkery.worldgen.SpelunkeryWorldgen;

public final class SpelunkeryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Spelunkery.init();
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> {
            if (!blocked) {
                return;
            }

            net.minecraft.world.entity.Entity sourceEntity = source.getDirectEntity() != null ? source.getDirectEntity() : source.getEntity();
            if (sourceEntity instanceof net.minecraft.world.entity.LivingEntity attacker) {
                BronzeShieldItem.tryBash(entity, attacker);
            }
        });
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
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                SpelunkeryWorldgen.TOPAZ_GEODE_PLACED
        );
    }
}
