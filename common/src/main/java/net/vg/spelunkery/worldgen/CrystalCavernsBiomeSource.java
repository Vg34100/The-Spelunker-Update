package net.vg.spelunkery.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.vg.spelunkery.registry.SpelunkeryBiomeSources;

import java.util.stream.Stream;

public final class CrystalCavernsBiomeSource extends BiomeSource {
    public static final MapCodec<CrystalCavernsBiomeSource> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            MultiNoiseBiomeSourceParameterList.CODEC.fieldOf("preset").forGetter(source -> source.preset),
            Biome.CODEC.fieldOf("crystal_biome").forGetter(source -> source.crystalBiome)
    ).apply(instance, CrystalCavernsBiomeSource::new));

    private final Holder<MultiNoiseBiomeSourceParameterList> preset;
    private final Holder<Biome> crystalBiome;
    private final MultiNoiseBiomeSource delegate;

    public CrystalCavernsBiomeSource(Holder<MultiNoiseBiomeSourceParameterList> preset, Holder<Biome> crystalBiome) {
        this.preset = preset;
        this.crystalBiome = crystalBiome;
        this.delegate = MultiNoiseBiomeSource.createFromPreset(preset);
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return SpelunkeryBiomeSources.CRYSTAL_CAVERNS.get();
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.concat(this.delegate.possibleBiomes().stream(), Stream.of(this.crystalBiome)).distinct();
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        Climate.TargetPoint targetPoint = sampler.sample(x, y, z);
        Holder<Biome> vanilla = this.delegate.getNoiseBiome(targetPoint);
        if (shouldUseCrystalCaverns(vanilla, targetPoint, x, y, z)) {
            return this.crystalBiome;
        }
        return vanilla;
    }

    private boolean shouldUseCrystalCaverns(Holder<Biome> vanilla, Climate.TargetPoint targetPoint, int x, int y, int z) {
        if (vanilla.is(Biomes.DEEP_DARK)) {
            return false;
        }

        float humidity = Climate.unquantizeCoord(targetPoint.humidity());
        float erosion = Climate.unquantizeCoord(targetPoint.erosion());
        float depth = Climate.unquantizeCoord(targetPoint.depth());

        if (y > 12 || depth < 0.15F || depth > 1.1F || humidity < -0.15F || erosion > 0.45F) {
            return false;
        }

        if (vanilla.is(Biomes.LUSH_CAVES) || vanilla.is(Biomes.DRIPSTONE_CAVES)) {
            return true;
        }

        return humidity > 0.05F && erosion < 0.2F && matchesCrystalBand(x, y, z);
    }

    private boolean matchesCrystalBand(int x, int y, int z) {
        long hash = 341873128712L * x + 132897987541L * y + 42317861L * z;
        hash ^= hash >>> 13;
        hash *= 1274126177L;
        return (hash & 7L) < 2L;
    }
}
