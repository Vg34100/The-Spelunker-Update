package net.vg.spelunkery.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.vg.spelunkery.registry.SpelunkeryFeatures;
import net.vg.spelunkery.worldgen.GiantMushroomFeature;

public final class GlowcapBlock extends BushBlock implements BonemealableBlock {
    public static final MapCodec<GlowcapBlock> CODEC = simpleCodec(GlowcapBlock::new);
    private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D);

    public GlowcapBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MapCodec<BushBlock> codec() {
        return (MapCodec<BushBlock>) (MapCodec<?>) CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(SpelunkeryBlocks.MYCELIUM_MUD.get())
                || state.is(SpelunkeryBlocks.BIOLUMINESCENT_MOSS.get())
                || state.is(net.minecraft.world.level.block.Blocks.ROOTED_DIRT)
                || state.is(net.minecraft.world.level.block.Blocks.MOSS_BLOCK)
                || state.is(net.minecraft.world.level.block.Blocks.MYCELIUM);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        return this.mayPlaceOn(level.getBlockState(belowPos), level, belowPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(net.minecraft.world.level.Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        level.removeBlock(pos, false);
        GiantMushroomFeature giantFeature = (GiantMushroomFeature) SpelunkeryFeatures.GIANT_BLUE_MUSHROOM.get();
        if (!giantFeature.growFromOrigin(level, pos, random)) {
            level.setBlock(pos, state, 3);
        }
    }
}
