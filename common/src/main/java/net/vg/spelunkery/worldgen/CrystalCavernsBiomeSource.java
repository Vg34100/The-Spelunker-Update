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
            Biome.CODEC.fieldOf("crystal_biome").forGetter(source -> source.crystalBiome),
            Biome.CODEC.optionalFieldOf("marble_biome").forGetter(source -> java.util.Optional.of(source.marbleBiome))
    ).apply(instance, (preset, crystalBiome, marbleBiome) -> new CrystalCavernsBiomeSource(preset, crystalBiome, marbleBiome.orElse(crystalBiome))));

    private final Holder<MultiNoiseBiomeSourceParameterList> preset;
    private final Holder<Biome> crystalBiome;
    private final Holder<Biome> marbleBiome;
    private final MultiNoiseBiomeSource delegate;

    public CrystalCavernsBiomeSource(Holder<MultiNoiseBiomeSourceParameterList> preset, Holder<Biome> crystalBiome, Holder<Biome> marbleBiome) {
        this.preset = preset;
        this.crystalBiome = crystalBiome;
        this.marbleBiome = marbleBiome;
        this.delegate = MultiNoiseBiomeSource.createFromPreset(preset);
    }

    @Override
    protected MapCodec<? extends BiomeSource> codec() {
        return SpelunkeryBiomeSources.CRYSTAL_CAVERNS.get();
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.concat(this.delegate.possibleBiomes().stream(), Stream.of(this.crystalBiome, this.marbleBiome)).distinct();
    }

    @Override
    public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler) {
        Climate.TargetPoint targetPoint = sampler.sample(x, y, z);
        Holder<Biome> vanilla = this.delegate.getNoiseBiome(targetPoint);
        if (shouldUseMarbleCaves(vanilla, targetPoint, x, y, z)) {
            return this.marbleBiome;
        }
        if (shouldUseCrystalCaverns(vanilla, targetPoint, x, y, z)) {
            return this.crystalBiome;
        }
        return vanilla;
    }

    private boolean shouldUseMarbleCaves(Holder<Biome> vanilla, Climate.TargetPoint targetPoint, int x, int y, int z) {
        if (vanilla.is(Biomes.DEEP_DARK) || y > 12) {
            return false;
        }

        float humidity = Climate.unquantizeCoord(targetPoint.humidity());
        float erosion = Climate.unquantizeCoord(targetPoint.erosion());
        float depth = Climate.unquantizeCoord(targetPoint.depth());

        if (depth < 0.2F || depth > 1.0F || humidity < 0.02F || humidity > 0.24F || erosion > 0.18F) {
            return false;
        }

        if (vanilla.is(Biomes.LUSH_CAVES) || vanilla.is(Biomes.DRIPSTONE_CAVES)) {
            return matchesBand(x, z, 15L, 1L, 3L);
        }

        return matchesBand(x, z, 31L, 1L, 3L);
    }

    private boolean shouldUseCrystalCaverns(Holder<Biome> vanilla, Climate.TargetPoint targetPoint, int x, int y, int z) {
        if (vanilla.is(Biomes.DEEP_DARK) || vanilla.is(Biomes.LUSH_CAVES)) {
            return false;
        }

        float humidity = Climate.unquantizeCoord(targetPoint.humidity());
        float erosion = Climate.unquantizeCoord(targetPoint.erosion());
        float depth = Climate.unquantizeCoord(targetPoint.depth());

        if (y > 0 || depth < 0.2F || depth > 1.0F || humidity < -0.08F || humidity > 0.12F || erosion > 0.1F) {
            return false;
        }

        return matchesBand(x, z, 7L, 1L, 4L);
    }

    private boolean matchesBand(int x, int z, long mask, long threshold, long salt) {
        int cellX = Math.floorDiv(x, 8);
        int cellZ = Math.floorDiv(z, 8);
        long hash = 341873128712L * cellX + 42317861L * cellZ;
        hash += salt * 982451653L;
        hash ^= hash >>> 13;
        hash *= 1274126177L;
        return (hash & mask) < threshold;
    }
}
