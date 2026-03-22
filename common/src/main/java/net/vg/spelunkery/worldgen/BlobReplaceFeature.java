package net.vg.spelunkery.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public final class BlobReplaceFeature extends Feature<BlobReplaceConfiguration> {
    public BlobReplaceFeature(Codec<BlobReplaceConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BlobReplaceConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        BlobReplaceConfiguration config = context.config();

        int replaced = 0;
        int baseRadius = Math.max(2, (int) Math.ceil(Math.cbrt(config.size()) * 1.35D));
        int clusters = Math.max(1, config.size() / 24);

        for (int i = 0; i < clusters; i++) {
            BlockPos center = origin.offset(
                    random.nextInt(baseRadius * 2 + 1) - baseRadius,
                    random.nextInt(Math.max(2, baseRadius)) - baseRadius / 2,
                    random.nextInt(baseRadius * 2 + 1) - baseRadius
            );

            double radiusX = Math.max(2.0D, baseRadius * (0.8D + random.nextDouble() * 0.9D));
            double radiusY = Math.max(1.5D, radiusX * (config.exposedOnly() ? 0.35D : 0.65D));
            double radiusZ = Math.max(2.0D, baseRadius * (0.8D + random.nextDouble() * 0.9D));

            int minX = (int) Math.floor(-radiusX);
            int maxX = (int) Math.ceil(radiusX);
            int minY = (int) Math.floor(-radiusY);
            int maxY = (int) Math.ceil(radiusY);
            int minZ = (int) Math.floor(-radiusZ);
            int maxZ = (int) Math.ceil(radiusZ);

            for (int dx = minX; dx <= maxX; dx++) {
                double normX = dx / radiusX;
                for (int dy = minY; dy <= maxY; dy++) {
                    double normY = dy / radiusY;
                    for (int dz = minZ; dz <= maxZ; dz++) {
                        double normZ = dz / radiusZ;
                        double distance = normX * normX + normY * normY + normZ * normZ;
                        if (distance > 1.0D || distance > random.nextDouble() * 1.15D) {
                            continue;
                        }

                        BlockPos pos = center.offset(dx, dy, dz);
                        BlockState state = level.getBlockState(pos);
                        if (config.exposedOnly() && !isExposed(level, pos)) {
                            continue;
                        }

                        for (BlobReplaceConfiguration.TargetBlockState target : config.targets()) {
                            if (target.target().test(state, random)) {
                                level.setBlock(pos, target.state(), 2);
                                replaced++;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return replaced > 0;
    }

    private static boolean isExposed(WorldGenLevel level, BlockPos pos) {
        return level.isEmptyBlock(pos.above())
                || level.isEmptyBlock(pos.below())
                || level.isEmptyBlock(pos.north())
                || level.isEmptyBlock(pos.south())
                || level.isEmptyBlock(pos.east())
                || level.isEmptyBlock(pos.west());
    }
}
