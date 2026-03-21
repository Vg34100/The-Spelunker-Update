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

        int radius = Math.max(1, (int) Math.ceil(Math.cbrt(config.size()) * 1.9D));
        int replaced = 0;

        for (int i = 0; i < config.size() * 6; i++) {
            int dx = random.nextInt(radius * 2 + 1) - radius;
            int dy = random.nextInt(Math.max(2, radius + 1)) - radius / 2;
            int dz = random.nextInt(radius * 2 + 1) - radius;
            int distance = dx * dx + dy * dy + dz * dz;
            if (distance > radius * radius + random.nextInt(radius + 1)) {
                continue;
            }

            BlockPos pos = origin.offset(dx, dy, dz);
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
