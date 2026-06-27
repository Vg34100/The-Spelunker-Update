package net.vg.spelunkery.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public final class ScorchedDripstoneBlock extends SpelunkeryPointedDripstoneBlock {
    public static final MapCodec<ScorchedDripstoneBlock> CODEC = simpleCodec(ScorchedDripstoneBlock::new);

    public ScorchedDripstoneBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<net.minecraft.world.level.block.PointedDripstoneBlock> codec() {
        return (MapCodec<net.minecraft.world.level.block.PointedDripstoneBlock>) (MapCodec<?>) CODEC;
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        super.fallOn(level, state, pos, entity, fallDistance);
        if (!level.isClientSide() && state.getValue(TIP_DIRECTION) == Direction.UP && fallDistance > 0.5) {
            entity.igniteForSeconds(4.0F);
        }
    }
}
