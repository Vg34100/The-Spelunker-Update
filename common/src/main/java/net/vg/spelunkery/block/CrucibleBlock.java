package net.vg.spelunkery.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class CrucibleBlock extends Block {
    public static final MapCodec<CrucibleBlock> CODEC = simpleCodec(CrucibleBlock::new);
    public static final BooleanProperty FILLED = BlockStateProperties.LIT;

    public CrucibleBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FILLED, false));
    }

    @Override
    public MapCodec<CrucibleBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FILLED);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.InteractionHand hand, BlockHitResult hitResult) {
        if (!stack.is(Items.LAVA_BUCKET)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (state.getValue(FILLED)) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.literal("The crucible is already filled with lava."), true);
            }
            return ItemInteractionResult.SUCCESS;
        }

        if (!level.isClientSide) {
            level.setBlockAndUpdate(pos, state.setValue(FILLED, true));
            if (!player.getAbilities().instabuild) {
                player.setItemInHand(hand, new ItemStack(Items.BUCKET));
            }
            player.displayClientMessage(Component.literal("The crucible is filled with lava."), true);
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            player.displayClientMessage(Component.literal(state.getValue(FILLED) ? "The crucible is hot and ready." : "The crucible is empty."), true);
        }
        return InteractionResult.SUCCESS;
    }

    public static boolean consumeLava(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof CrucibleBlock) || !state.getValue(FILLED)) {
            return false;
        }

        level.setBlockAndUpdate(pos, state.setValue(FILLED, false));
        return true;
    }

    public static boolean isFilled(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof CrucibleBlock && state.getValue(FILLED);
    }
}
