package net.vg.spelunkery.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.vg.spelunkery.registry.SpelunkeryBlocks;

import java.util.ArrayList;
import java.util.List;

public final class GiantMushroomFeature extends Feature<NoneFeatureConfiguration> {
    private final Block capBlock;
    private final boolean redCap;

    public GiantMushroomFeature(Codec<NoneFeatureConfiguration> codec, Block capBlock, boolean redCap) {
        super(codec);
        this.capBlock = capBlock;
        this.redCap = redCap;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        int radius = this.redCap ? 2 : 3;
        int height = 4 + random.nextInt(3) + (random.nextInt(5) == 0 ? 1 : 0);
        BlockPos stemBase = findStemBase(level, origin, height, radius, random);
        if (stemBase == null || !fitsInChunk(origin, stemBase, radius) || !canPlace(level, stemBase, height, radius)) {
            return false;
        }

        List<BlockPos> stemPositions = new ArrayList<>();
        List<BlockPos> capPositions = new ArrayList<>();

        for (int dy = 0; dy < height; dy++) {
            BlockPos pos = stemBase.above(dy);
            level.setBlock(pos, Blocks.MUSHROOM_STEM.defaultBlockState(), 2);
            stemPositions.add(pos);
        }

        BlockPos capTop = stemBase.above(height - 1);
        if (this.redCap) {
            placeRedCap(level, capTop, capPositions);
        } else {
            placeBrownCap(level, capTop, capPositions);
        }

        updateMushroomStates(level, stemPositions, Blocks.MUSHROOM_STEM);
        updateMushroomStates(level, capPositions, this.capBlock);
        return !capPositions.isEmpty();
    }

    private static BlockPos findStemBase(WorldGenLevel level, BlockPos origin, int height, int radius, RandomSource random) {
        for (int attempt = 0; attempt < 24; attempt++) {
            int x = origin.getX() + random.nextInt(15) - 7;
            int z = origin.getZ() + random.nextInt(15) - 7;
            int maxY = origin.getY() + 12;
            int minY = Math.max(level.getMinBuildHeight() + 1, origin.getY() - 40);

            for (int y = maxY; y >= minY; y--) {
                BlockPos pos = new BlockPos(x, y, z);
                BlockPos below = pos.below();
                if (!level.isEmptyBlock(pos) || !isSupport(level.getBlockState(below))) {
                    continue;
                }

                if (canPlace(level, pos, height, radius)) {
                    return pos;
                }
            }
        }

        return null;
    }

    private static boolean fitsInChunk(BlockPos origin, BlockPos stemBase, int radius) {
        int chunkMinX = (origin.getX() >> 4) << 4;
        int chunkMinZ = (origin.getZ() >> 4) << 4;
        int chunkMaxX = chunkMinX + 15;
        int chunkMaxZ = chunkMinZ + 15;
        return stemBase.getX() - radius >= chunkMinX
                && stemBase.getX() + radius <= chunkMaxX
                && stemBase.getZ() - radius >= chunkMinZ
                && stemBase.getZ() + radius <= chunkMaxZ;
    }

    private static boolean canPlace(WorldGenLevel level, BlockPos stemBase, int height, int radius) {
        int topY = stemBase.getY() + height + 2;
        if (topY >= level.getMaxBuildHeight()) {
            return false;
        }

        for (int y = stemBase.getY(); y <= topY; y++) {
            int testRadius = y < stemBase.getY() + height - 2 ? 0 : radius;
            for (int dx = -testRadius; dx <= testRadius; dx++) {
                for (int dz = -testRadius; dz <= testRadius; dz++) {
                    BlockPos pos = new BlockPos(stemBase.getX() + dx, y, stemBase.getZ() + dz);
                    if (!isReplaceable(level.getBlockState(pos))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static boolean isSupport(BlockState state) {
        return state.is(SpelunkeryBlocks.MYCELIUM_MUD.get())
                || state.is(SpelunkeryBlocks.BIOLUMINESCENT_MOSS.get())
                || state.is(Blocks.ROOTED_DIRT)
                || state.is(Blocks.MOSS_BLOCK)
                || state.is(Blocks.MYCELIUM)
                || state.is(Blocks.DIRT)
                || state.is(Blocks.PODZOL);
    }

    private static boolean isReplaceable(BlockState state) {
        return state.isAir()
                || state.is(Blocks.VINE)
                || state.is(Blocks.GLOW_LICHEN)
                || state.is(Blocks.BROWN_MUSHROOM)
                || state.is(Blocks.RED_MUSHROOM)
                || state.is(SpelunkeryBlocks.GLOWCAP.get())
                || state.is(SpelunkeryBlocks.FUNGAL_MAT.get())
                || state.is(Blocks.SHORT_GRASS)
                || state.is(Blocks.TALL_GRASS);
    }

    private void placeBrownCap(WorldGenLevel level, BlockPos top, List<BlockPos> capPositions) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                if (Math.abs(dx) == 3 && Math.abs(dz) == 3) {
                    continue;
                }
                BlockPos pos = top.offset(dx, 0, dz);
                level.setBlock(pos, this.capBlock.defaultBlockState(), 2);
                capPositions.add(pos);
            }
        }
    }

    private void placeRedCap(WorldGenLevel level, BlockPos top, List<BlockPos> capPositions) {
        for (int layer = -1; layer <= 1; layer++) {
            int radius = layer == 1 ? 1 : 2;
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (Math.abs(dx) == radius && Math.abs(dz) == radius && layer != 1) {
                        continue;
                    }
                    BlockPos pos = top.offset(dx, layer, dz);
                    level.setBlock(pos, this.capBlock.defaultBlockState(), 2);
                    capPositions.add(pos);
                }
            }
        }
    }

    private static void updateMushroomStates(WorldGenLevel level, List<BlockPos> positions, Block block) {
        for (BlockPos pos : positions) {
            BlockState state = mushroomStateFor(level, pos, block);
            level.setBlock(pos, state, 2);
        }
    }

    private static BlockState mushroomStateFor(WorldGenLevel level, BlockPos pos, Block block) {
        BlockState state = block.defaultBlockState();
        if (!(block instanceof HugeMushroomBlock)) {
            return state;
        }

        return state
                .setValue(HugeMushroomBlock.DOWN, !level.getBlockState(pos.below()).is(block))
                .setValue(HugeMushroomBlock.UP, !level.getBlockState(pos.above()).is(block))
                .setValue(HugeMushroomBlock.NORTH, !level.getBlockState(pos.north()).is(block))
                .setValue(HugeMushroomBlock.SOUTH, !level.getBlockState(pos.south()).is(block))
                .setValue(HugeMushroomBlock.WEST, !level.getBlockState(pos.west()).is(block))
                .setValue(HugeMushroomBlock.EAST, !level.getBlockState(pos.east()).is(block));
    }
}
