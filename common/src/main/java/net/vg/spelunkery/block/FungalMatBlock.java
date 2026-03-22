package net.vg.spelunkery.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.spelunkery.registry.SpelunkeryBlocks;

public final class FungalMatBlock extends CarpetBlock {
    public static final MapCodec<FungalMatBlock> CODEC = simpleCodec(FungalMatBlock::new);

    public FungalMatBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends CarpetBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        return !belowState.is(SpelunkeryBlocks.FUNGAL_MAT.get())
                && !belowState.is(Blocks.BROWN_MUSHROOM_BLOCK)
                && !belowState.is(Blocks.RED_MUSHROOM_BLOCK)
                && !belowState.is(Blocks.MUSHROOM_STEM)
                && super.canSurvive(state, level, pos);
    }
}
