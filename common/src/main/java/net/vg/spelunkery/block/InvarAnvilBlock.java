package net.vg.spelunkery.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.BlockHitResult;
import net.vg.spelunkery.menu.InvarAnvilMenu;

import java.util.function.Supplier;

public class InvarAnvilBlock extends AnvilBlock {
    private final Supplier<? extends Block> damagedVariant;

    public InvarAnvilBlock(BlockBehaviour.Properties properties, Supplier<? extends Block> damagedVariant) {
        super(properties);
        this.damagedVariant = damagedVariant;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        player.openMenu(new SimpleMenuProvider(
                (containerId, inventory, menuPlayer) -> new InvarAnvilMenu(containerId, inventory, ContainerLevelAccess.create(level, pos)),
                Component.translatable("container.repair")
        ));
        player.awardStat(net.minecraft.stats.Stats.INTERACT_WITH_ANVIL);
        return InteractionResult.CONSUME;
    }

    public static BlockState damage(BlockState state) {
        if (!(state.getBlock() instanceof InvarAnvilBlock anvil) || anvil.damagedVariant == null) {
            return null;
        }
        return anvil.damagedVariant.get().defaultBlockState().setValue(FACING, state.getValue(FACING));
    }
}
