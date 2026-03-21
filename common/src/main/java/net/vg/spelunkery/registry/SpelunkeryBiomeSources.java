package net.vg.spelunkery.registry;

import com.mojang.serialization.MapCodec;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.BiomeSource;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.worldgen.CrystalCavernsBiomeSource;

public final class SpelunkeryBiomeSources {
    public static final DeferredRegister<MapCodec<? extends BiomeSource>> BIOME_SOURCES = DeferredRegister.create(Spelunkery.MOD_ID, Registries.BIOME_SOURCE);
    public static final RegistrySupplier<MapCodec<? extends BiomeSource>> CRYSTAL_CAVERNS = BIOME_SOURCES.register("crystal_caverns", () -> CrystalCavernsBiomeSource.CODEC);

    private static boolean initialized;

    private SpelunkeryBiomeSources() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        BIOME_SOURCES.register();
    }
}
