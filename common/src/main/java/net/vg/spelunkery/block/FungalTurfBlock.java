package net.vg.spelunkery.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.vg.spelunkery.registry.SpelunkeryBlocks;

public final class FungalTurfBlock extends SpreadingSnowyDirtBlock {
    public static final MapCodec<FungalTurfBlock> CODEC = simpleCodec(FungalTurfBlock::new);

    public FungalTurfBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends SpreadingSnowyDirtBlock> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canRemainFungalTurf(level, pos)) {
            level.setBlockAndUpdate(pos, SpelunkeryBlocks.MYCELIUM_MUD.get().defaultBlockState());
            return;
        }

        if (level.getMaxLocalRawBrightness(pos.above()) < 9) {
            return;
        }

        BlockState spreadState = this.defaultBlockState();

        for (int i = 0; i < 4; i++) {
            BlockPos spreadPos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
            if (!level.getBlockState(spreadPos).is(SpelunkeryBlocks.MYCELIUM_MUD.get())) {
                continue;
            }

            if (canPropagateTo(level, spreadPos)) {
                level.setBlockAndUpdate(
                        spreadPos,
                        spreadState.setValue(SNOWY, level.getBlockState(spreadPos.above()).is(Blocks.SNOW))
                );
            }
        }
    }

    private static boolean canRemainFungalTurf(ServerLevel level, BlockPos pos) {
        BlockState aboveState = level.getBlockState(pos.above());
        if (aboveState.is(Blocks.SNOW) && aboveState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        }

        return level.getFluidState(pos.above()).getType() != Fluids.WATER && aboveState.getLightBlock(level, pos.above()) < level.getMaxLightLevel();
    }

    private static boolean canPropagateTo(ServerLevel level, BlockPos pos) {
        return canRemainFungalTurf(level, pos) && !level.getFluidState(pos.above()).is(FluidTags.WATER);
    }
}
