package net.vg.spelunkery.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.spelunkery.registry.SpelunkeryBlocks;

public final class RopeBundleItem extends Item {
    private static final int MAX_DEPLOY = 8;

    public RopeBundleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos().below();
        BlockState rope = SpelunkeryBlocks.ROPE.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, net.minecraft.core.Direction.Axis.Y);
        int placed = 0;
        while (placed < MAX_DEPLOY && level.getBlockState(pos).canBeReplaced()) {
            level.setBlock(pos, rope, 3);
            placed++;
            pos = pos.below();
        }

        if (placed == 0) {
            return InteractionResult.PASS;
        }

        if (!context.getPlayer().hasInfiniteMaterials()) {
            context.getItemInHand().shrink(1);
        }

        level.playSound(context.getPlayer(), context.getClickedPos(), rope.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 0.9F);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
