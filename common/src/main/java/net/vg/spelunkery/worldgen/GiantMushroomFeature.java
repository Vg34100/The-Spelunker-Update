package net.vg.spelunkery.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.vg.spelunkery.registry.SpelunkeryBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class GiantMushroomFeature extends Feature<NoneFeatureConfiguration> {
    public enum CapShape {
        BROWN,
        RED,
        BLUE
    }

    private final Supplier<Block> capBlock;
    private final CapShape capShape;

    public GiantMushroomFeature(Codec<NoneFeatureConfiguration> codec, Supplier<Block> capBlock, CapShape capShape) {
        super(codec);
        this.capBlock = capBlock;
        this.capShape = capShape;
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        int radius = this.capShape == CapShape.BROWN ? 3 : 2;
        int height = 5 + random.nextInt(4) + (random.nextInt(3) == 0 ? 1 : 0);
        BlockPos stemBase = findStemBase(level, origin, height, radius, random);
        if (stemBase == null || !fitsInChunk(origin, stemBase, radius)) {
            return false;
        }

        return placeAt(level, origin, random, stemBase, height, radius);
    }

    public boolean growFromOrigin(WorldGenLevel level, BlockPos stemBase, RandomSource random) {
        int radius = this.capShape == CapShape.BROWN ? 3 : 2;
        int height = 5 + random.nextInt(4) + (random.nextInt(3) == 0 ? 1 : 0);
        if (!isSupport(level.getBlockState(stemBase.below())) || !fitsInChunk(stemBase, stemBase, radius)) {
            return false;
        }

        return placeAt(level, stemBase, random, stemBase, height, radius);
    }

    private boolean placeAt(WorldGenLevel level, BlockPos origin, RandomSource random, BlockPos stemBase, int height, int radius) {
        if (!canPlace(level, stemBase, height, radius)) {
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
        switch (this.capShape) {
            case RED -> placeRedCap(level, capTop, capPositions);
            case BLUE -> placeBlueCap(level, capTop, capPositions);
            default -> placeBrownCap(level, capTop, capPositions);
        }

        updateMushroomStates(level, stemPositions, Blocks.MUSHROOM_STEM);
        updateMushroomStates(level, capPositions, this.capBlock.get());
        return !capPositions.isEmpty();
    }

    private static BlockPos findStemBase(WorldGenLevel level, BlockPos origin, int height, int radius, RandomSource random) {
        for (int attempt = 0; attempt < 96; attempt++) {
            int x = origin.getX() + random.nextInt(21) - 10;
            int z = origin.getZ() + random.nextInt(21) - 10;
            int maxY = origin.getY() + 18;
            int minY = Math.max(level.getMinBuildHeight() + 1, origin.getY() - 40);

            for (int y = maxY; y >= minY; y--) {
                BlockPos pos = new BlockPos(x, y, z);
                BlockPos below = pos.below();
                if (!isSupport(level.getBlockState(below)) || level.getBlockState(pos).is(Blocks.BEDROCK)) {
                    continue;
                }
                if (hasNearbyStem(level, pos)) {
                    continue;
                }

                return pos;
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
        return topY < level.getMaxBuildHeight();
    }

    private static boolean hasNearbyStem(WorldGenLevel level, BlockPos stemBase) {
        for (int dx = -4; dx <= 4; dx++) {
            for (int dz = -4; dz <= 4; dz++) {
                if (dx == 0 && dz == 0) {
                    continue;
                }
                BlockPos checkPos = stemBase.offset(dx, 0, dz);
                for (int dy = -1; dy <= 7; dy++) {
                    if (level.getBlockState(checkPos.above(dy)).is(Blocks.MUSHROOM_STEM)) {
                        return true;
                    }
                }
            }
        }
        return false;
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

    private void placeBrownCap(WorldGenLevel level, BlockPos top, List<BlockPos> capPositions) {
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                if (Math.abs(dx) == 3 && Math.abs(dz) == 3) {
                    continue;
                }
                BlockPos pos = top.offset(dx, 0, dz);
                if (!level.getBlockState(pos).is(Blocks.BEDROCK)) {
                    level.setBlock(pos, this.capBlock.get().defaultBlockState(), 2);
                    capPositions.add(pos);
                }
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
                    if (!level.getBlockState(pos).is(Blocks.BEDROCK)) {
                        level.setBlock(pos, this.capBlock.get().defaultBlockState(), 2);
                        capPositions.add(pos);
                    }
                }
            }
        }
    }

    private void placeBlueCap(WorldGenLevel level, BlockPos top, List<BlockPos> capPositions) {
        for (int layer = -2; layer <= 1; layer++) {
            int radius;
            if (layer <= -1) {
                radius = 3 + layer;
            } else if (layer == 0) {
                radius = 2;
            } else {
                radius = 1;
            }

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (Math.abs(dx) == radius && Math.abs(dz) == radius && layer != 1) {
                        continue;
                    }
                    BlockPos pos = top.offset(dx, layer, dz);
                    if (!level.getBlockState(pos).is(Blocks.BEDROCK)) {
                        level.setBlock(pos, this.capBlock.get().defaultBlockState(), 2);
                        capPositions.add(pos);
                    }
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
