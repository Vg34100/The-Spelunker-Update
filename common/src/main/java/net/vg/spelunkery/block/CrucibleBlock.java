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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class CrucibleBlock extends Block {
    public static final MapCodec<CrucibleBlock> CODEC = simpleCodec(CrucibleBlock::new);
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_CAULDRON;
    private static final int MAX_LAVA_USES = 3;

    public CrucibleBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(LEVEL, 0));
    }

    @Override
    public MapCodec<CrucibleBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, net.minecraft.world.InteractionHand hand, BlockHitResult hitResult) {
        int lavaLevel = state.getValue(LEVEL);

        if (stack.is(Items.LAVA_BUCKET)) {
            if (lavaLevel >= MAX_LAVA_USES) {
                if (!level.isClientSide) {
                    player.displayClientMessage(Component.literal("The crucible is already full of lava."), true);
                }
                return ItemInteractionResult.SUCCESS;
            }

            if (!level.isClientSide) {
                level.setBlockAndUpdate(pos, state.setValue(LEVEL, MAX_LAVA_USES));
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                }
                player.displayClientMessage(Component.literal("The crucible is filled with lava."), true);
            }

            return ItemInteractionResult.SUCCESS;
        }

        if (stack.is(Items.BUCKET) && lavaLevel >= MAX_LAVA_USES) {
            if (!level.isClientSide) {
                level.setBlockAndUpdate(pos, state.setValue(LEVEL, 0));
                if (!player.getAbilities().instabuild) {
                    player.setItemInHand(hand, new ItemStack(Items.LAVA_BUCKET));
                }
                player.displayClientMessage(Component.literal("You bucket the unused lava back out."), true);
            }

            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            int lavaLevel = state.getValue(LEVEL);
            player.displayClientMessage(Component.literal(lavaLevel > 0 ? "The crucible has " + lavaLevel + " lava uses left." : "The crucible is empty."), true);
        }
        return InteractionResult.SUCCESS;
    }

    public static boolean consumeLava(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof CrucibleBlock)) {
            return false;
        }

        int lavaLevel = state.getValue(LEVEL);
        if (lavaLevel <= 0) {
            return false;
        }

        level.setBlockAndUpdate(pos, state.setValue(LEVEL, lavaLevel - 1));
        return true;
    }

    public static boolean isFilled(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof CrucibleBlock && state.getValue(LEVEL) > 0;
    }
}
