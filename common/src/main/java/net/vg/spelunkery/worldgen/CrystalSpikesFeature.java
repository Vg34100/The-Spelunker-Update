package net.vg.spelunkery.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.vg.spelunkery.registry.SpelunkeryBlocks;

public final class CrystalSpikesFeature extends Feature<NoneFeatureConfiguration> {
    private static final int ATTEMPTS = 20;

    public CrystalSpikesFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        boolean placedAny = false;

        for (int i = 0; i < ATTEMPTS; i++) {
            BlockPos sample = origin.offset(random.nextInt(16) - 8, random.nextInt(12) - 6, random.nextInt(16) - 8);
            placedAny |= tryPlaceSpike(level, sample, Direction.UP, 3 + random.nextInt(5), pickCrystal(random));
            placedAny |= tryPlaceSpike(level, sample, Direction.DOWN, 3 + random.nextInt(5), pickCrystal(random));
        }

        return placedAny;
    }

    private boolean tryPlaceSpike(WorldGenLevel level, BlockPos sample, Direction direction, int desiredLength, Block block) {
        BlockPos root = findRoot(level, sample, direction);
        if (root == null) {
            return false;
        }

        int length = 0;
        BlockPos cursor = root;
        while (length < desiredLength
                && level.getBlockState(cursor).canBeReplaced()
                && level.getFluidState(cursor).isEmpty()) {
            length++;
            cursor = cursor.relative(direction);
        }

        if (length == 0) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            BlockPos pos = root.relative(direction, i);
            level.setBlock(pos, createSegment(block, direction, i, length), 3);
        }

        return true;
    }

    private BlockPos findRoot(WorldGenLevel level, BlockPos sample, Direction direction) {
        for (int offset = -5; offset <= 5; offset++) {
            BlockPos candidate = sample.offset(0, offset, 0);
            if (!level.getBlockState(candidate).canBeReplaced() || !level.getFluidState(candidate).isEmpty()) {
                continue;
            }

            BlockPos supportPos = candidate.relative(direction.getOpposite());
            if (level.getBlockState(supportPos).isSolidRender(level, supportPos)
                    && level.getFluidState(supportPos).getType() != Fluids.WATER) {
                return candidate;
            }
        }

        return null;
    }

    private BlockState createSegment(Block block, Direction direction, int index, int length) {
        DripstoneThickness thickness;
        if (length == 1) {
            thickness = DripstoneThickness.TIP;
        } else if (index == 0) {
            thickness = DripstoneThickness.BASE;
        } else if (index == length - 1) {
            thickness = DripstoneThickness.TIP;
        } else if (index == 1 || index == length - 2) {
            thickness = DripstoneThickness.FRUSTUM;
        } else {
            thickness = DripstoneThickness.MIDDLE;
        }

        return block.defaultBlockState()
                .setValue(PointedDripstoneBlock.TIP_DIRECTION, direction)
                .setValue(PointedDripstoneBlock.THICKNESS, thickness)
                .setValue(PointedDripstoneBlock.WATERLOGGED, false);
    }

    private Block pickCrystal(RandomSource random) {
        return switch (random.nextInt(4)) {
            case 0 -> SpelunkeryBlocks.BLUE_CRYSTAL.get();
            case 1 -> SpelunkeryBlocks.GREEN_CRYSTAL.get();
            case 2 -> SpelunkeryBlocks.RED_CRYSTAL.get();
            default -> SpelunkeryBlocks.YELLOW_CRYSTAL.get();
        };
    }
}
