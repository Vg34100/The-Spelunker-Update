package net.vg.spelunkery.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.material.Fluids;

public class SpelunkeryPointedDripstoneBlock extends PointedDripstoneBlock {
    public static final MapCodec<SpelunkeryPointedDripstoneBlock> CODEC = simpleCodec(SpelunkeryPointedDripstoneBlock::new);

    public SpelunkeryPointedDripstoneBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<PointedDripstoneBlock> codec() {
        return (MapCodec<PointedDripstoneBlock>) (MapCodec<?>) CODEC;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction tipDirection = state.getValue(TIP_DIRECTION);
        BlockPos supportPos = pos.relative(tipDirection.getOpposite());
        BlockState supportState = level.getBlockState(supportPos);
        return supportState.isFaceSturdy(level, supportPos, tipDirection) || isSameDripstoneWithDirection(supportState, tipDirection);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction preferredDirection = context.getNearestLookingVerticalDirection().getOpposite();
        Direction tipDirection = calculateTipDirection(level, pos, preferredDirection);
        if (tipDirection == null) {
            return null;
        }

        boolean mergeTips = !context.isSecondaryUseActive();
        DripstoneThickness thickness = calculateThickness(level, pos, tipDirection, mergeTips);
        if (thickness == null) {
            return null;
        }

        return defaultBlockState()
                .setValue(TIP_DIRECTION, tipDirection)
                .setValue(THICKNESS, thickness)
                .setValue(WATERLOGGED, level.getFluidState(pos).getType() == Fluids.WATER);
    }

    @Override
    protected BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return state;
        }

        Direction tipDirection = state.getValue(TIP_DIRECTION);
        if (direction == tipDirection.getOpposite() && !canSurvive(state, level, pos)) {
            level.scheduleTick(pos, this, tipDirection == Direction.DOWN ? 2 : 1);
            return state;
        }

        boolean mergeTips = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
        DripstoneThickness thickness = calculateThickness(level, pos, tipDirection, mergeTips);
        return thickness == null ? state : state.setValue(THICKNESS, thickness);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    protected boolean isSameDripstone(BlockState state) {
        return state.is(this);
    }

    private Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction preferredDirection) {
        if (isValidPlacement(level, pos, preferredDirection)) {
            return preferredDirection;
        }

        Direction opposite = preferredDirection.getOpposite();
        return isValidPlacement(level, pos, opposite) ? opposite : null;
    }

    private boolean isValidPlacement(LevelReader level, BlockPos pos, Direction tipDirection) {
        return canSurvive(defaultBlockState().setValue(TIP_DIRECTION, tipDirection), level, pos);
    }

    private DripstoneThickness calculateThickness(LevelReader level, BlockPos pos, Direction tipDirection, boolean mergeTips) {
        Direction opposite = tipDirection.getOpposite();
        BlockState oppositeState = level.getBlockState(pos.relative(opposite));
        if (isSameDripstoneWithDirection(oppositeState, opposite)) {
            return mergeTips && !level.isWaterAt(pos.relative(tipDirection)) ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP;
        }

        BlockState sameDirectionState = level.getBlockState(pos.relative(tipDirection));
        if (!isSameDripstoneWithDirection(sameDirectionState, tipDirection)) {
            return DripstoneThickness.TIP;
        }

        DripstoneThickness sameThickness = sameDirectionState.getValue(THICKNESS);
        if (sameThickness != DripstoneThickness.TIP && sameThickness != DripstoneThickness.TIP_MERGE) {
            BlockState furtherState = level.getBlockState(pos.relative(tipDirection, 2));
            return !isSameDripstoneWithDirection(furtherState, tipDirection) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
        }

        return DripstoneThickness.FRUSTUM;
    }

    private boolean isSameDripstoneWithDirection(BlockState state, Direction direction) {
        return isSameDripstone(state) && state.getValue(TIP_DIRECTION) == direction;
    }
}
