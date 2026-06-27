package net.vg.spelunkery.item;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public final class RopeBlockItem extends BlockItem {
    public RopeBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = context.getLevel().getBlockState(clickedPos);
        BlockPos placePos = clickedState.canBeReplaced() ? clickedPos : clickedPos.relative(context.getClickedFace());
        BlockState placeState = getBlock().defaultBlockState();

        if (!context.getLevel().getBlockState(placePos).canBeReplaced()) {
            return InteractionResult.FAIL;
        }

        if (!context.getLevel().setBlock(placePos, placeState, 3)) {
            return InteractionResult.FAIL;
        }

        SoundType soundType = placeState.getSoundType();
        context.getLevel().playSound(context.getPlayer(), placePos, soundType.getPlaceSound(), SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
        if (!context.getPlayer().hasInfiniteMaterials()) {
            context.getItemInHand().shrink(1);
        }

        return InteractionResult.SUCCESS;
    }
}
