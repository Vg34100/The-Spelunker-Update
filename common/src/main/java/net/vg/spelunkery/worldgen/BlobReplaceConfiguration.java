package net.vg.spelunkery.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.List;

public record BlobReplaceConfiguration(List<TargetBlockState> targets, int size, boolean exposedOnly) implements FeatureConfiguration {
    public static final Codec<BlobReplaceConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TargetBlockState.CODEC.listOf().fieldOf("targets").forGetter(BlobReplaceConfiguration::targets),
            Codec.intRange(1, 256).fieldOf("size").forGetter(BlobReplaceConfiguration::size),
            Codec.BOOL.optionalFieldOf("exposed_only", false).forGetter(BlobReplaceConfiguration::exposedOnly)
    ).apply(instance, BlobReplaceConfiguration::new));

    public record TargetBlockState(RuleTest target, BlockState state) {
        public static final Codec<TargetBlockState> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                RuleTest.CODEC.fieldOf("target").forGetter(TargetBlockState::target),
                BlockState.CODEC.fieldOf("state").forGetter(TargetBlockState::state)
        ).apply(instance, TargetBlockState::new));

        public static TargetBlockState fromOreTarget(OreConfiguration.TargetBlockState target) {
            return new TargetBlockState(target.target, target.state);
        }
    }
}
