package net.vg.spelunkery.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
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
        return !level.getBlockState(pos.below()).is(SpelunkeryBlocks.FUNGAL_MAT.get()) && super.canSurvive(state, level, pos);
    }
}
