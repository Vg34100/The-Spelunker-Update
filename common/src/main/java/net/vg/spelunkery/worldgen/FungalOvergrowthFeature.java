package net.vg.spelunkery.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.vg.spelunkery.registry.SpelunkeryBlocks;

public final class FungalOvergrowthFeature extends Feature<NoneFeatureConfiguration> {
    private static final int ATTEMPTS = 64;

    public FungalOvergrowthFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();
        boolean placedAny = false;

        for (int i = 0; i < ATTEMPTS; i++) {
            BlockPos sample = origin.offset(random.nextInt(16) - 8, random.nextInt(24) - 12, random.nextInt(16) - 8);
            placedAny |= tryPlaceGroundCover(level, sample, random);
            placedAny |= tryPlaceGlowLichen(level, sample, random);
            placedAny |= tryPlaceVines(level, sample, random);
        }

        return placedAny;
    }

    private boolean tryPlaceGroundCover(WorldGenLevel level, BlockPos sample, RandomSource random) {
        BlockPos groundPos = findGroundAir(level, sample);
        if (groundPos == null) {
            return false;
        }

        BlockState below = level.getBlockState(groundPos.below());
        if (!isFungalSupport(below)) {
            return false;
        }

        if (random.nextFloat() < 0.7F) {
            level.setBlock(groundPos, SpelunkeryBlocks.FUNGAL_MAT.get().defaultBlockState(), 2);
            return true;
        }

        if (random.nextFloat() < 0.55F && SpelunkeryBlocks.GLOWCAP.get().defaultBlockState().canSurvive(level, groundPos)) {
            level.setBlock(groundPos, SpelunkeryBlocks.GLOWCAP.get().defaultBlockState(), 2);
            return true;
        }

        return false;
    }

    private boolean tryPlaceGlowLichen(WorldGenLevel level, BlockPos sample, RandomSource random) {
        BlockPos airPos = findNearbyAir(level, sample);
        if (airPos == null || !level.getBlockState(airPos).isAir()) {
            return false;
        }

        Direction[] directions = Direction.values();
        Direction start = directions[random.nextInt(directions.length)];
        for (int i = 0; i < directions.length; i++) {
            Direction direction = directions[(start.ordinal() + i) % directions.length];
            BlockPos supportPos = airPos.relative(direction);
            BlockState supportState = level.getBlockState(supportPos);
            if (!supportState.isSolidRender(level, supportPos)) {
                continue;
            }

            BlockState lichen = Blocks.GLOW_LICHEN.defaultBlockState()
                    .setValue(MultifaceBlock.getFaceProperty(direction), true);
            if (lichen.canSurvive(level, airPos)) {
                level.setBlock(airPos, lichen, 2);
                return true;
            }
        }

        return false;
    }

    private boolean tryPlaceVines(WorldGenLevel level, BlockPos sample, RandomSource random) {
        BlockPos airPos = findNearbyAir(level, sample);
        if (airPos == null || !level.getBlockState(airPos).isAir()) {
            return false;
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos supportPos = airPos.relative(direction);
            BlockState supportState = level.getBlockState(supportPos);
            if (!supportState.isSolidRender(level, supportPos)) {
                continue;
            }

            Direction face = direction.getOpposite();
            int length = 2 + random.nextInt(6);
            boolean placed = false;
            for (int dy = 0; dy < length; dy++) {
                BlockPos vinePos = airPos.below(dy);
                BlockState existing = level.getBlockState(vinePos);
                if (!existing.isAir() && !existing.is(Blocks.VINE)) {
                    break;
                }

                BlockState vine = Blocks.VINE.defaultBlockState().setValue(VineBlock.getPropertyForFace(face), true);
                if (!vine.canSurvive(level, vinePos)) {
                    break;
                }

                level.setBlock(vinePos, vine, 2);
                placed = true;
            }

            if (placed) {
                return true;
            }
        }

        return false;
    }

    private static BlockPos findGroundAir(WorldGenLevel level, BlockPos sample) {
        for (int offset = -8; offset <= 8; offset++) {
            BlockPos candidate = sample.offset(0, offset, 0);
            if (!level.getBlockState(candidate).isAir()) {
                continue;
            }

            BlockPos below = candidate.below();
            if (isFungalSupport(level.getBlockState(below))) {
                return candidate;
            }
        }
        return null;
    }

    private static BlockPos findNearbyAir(WorldGenLevel level, BlockPos sample) {
        for (int offset = -8; offset <= 8; offset++) {
            BlockPos candidate = sample.offset(0, offset, 0);
            if (level.getBlockState(candidate).isAir()) {
                return candidate;
            }
        }
        return null;
    }

    private static boolean isFungalSupport(BlockState state) {
        return state.is(SpelunkeryBlocks.MYCELIUM_MUD.get())
                || state.is(SpelunkeryBlocks.BIOLUMINESCENT_MOSS.get())
                || state.is(Blocks.ROOTED_DIRT)
                || state.is(Blocks.MOSS_BLOCK)
                || state.is(Blocks.MYCELIUM);
    }
}
