package net.vg.spelunkery.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.spelunkery.block.InvarAnvilBlock;
import net.vg.spelunkery.registry.SpelunkeryBlockTags;

import java.lang.reflect.Field;

public class InvarAnvilMenu extends AnvilMenu {
    private static final float DAMAGE_CHANCE = 0.04F;
    private static final Field REPAIR_ITEM_COUNT_COST_FIELD = getField("repairItemCountCost");
    private static final Field COST_FIELD = getField("cost");

    public InvarAnvilMenu(int containerId, net.minecraft.world.entity.player.Inventory inventory, ContainerLevelAccess access) {
        super(containerId, inventory, access);
    }

    @Override
    protected boolean isValidBlock(BlockState state) {
        return state.is(SpelunkeryBlockTags.INVAR_ANVILS);
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        if (!player.getAbilities().instabuild) {
            player.giveExperienceLevels(-getCostSlot().get());
        }

        this.inputSlots.setItem(0, ItemStack.EMPTY);
        int repairItemCountCost = getRepairItemCountCost();
        if (repairItemCountCost > 0) {
            ItemStack addition = this.inputSlots.getItem(1);
            if (!addition.isEmpty() && addition.getCount() > repairItemCountCost) {
                addition.shrink(repairItemCountCost);
                this.inputSlots.setItem(1, addition);
            } else {
                this.inputSlots.setItem(1, ItemStack.EMPTY);
            }
        } else {
            this.inputSlots.setItem(1, ItemStack.EMPTY);
        }

        getCostSlot().set(0);
        this.access.execute((level, pos) -> damageAnvil(level, pos, player));
    }

    private void damageAnvil(Level level, BlockPos pos, Player player) {
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof InvarAnvilBlock)) {
            return;
        }

        if (!player.getAbilities().instabuild && player.getRandom().nextFloat() < DAMAGE_CHANCE) {
            BlockState damaged = InvarAnvilBlock.damage(state);
            if (damaged == null) {
                level.removeBlock(pos, false);
                level.levelEvent(1029, pos, 0);
            } else {
                level.setBlock(pos, damaged, 2);
                level.levelEvent(1030, pos, 0);
            }
        } else {
            level.levelEvent(1030, pos, 0);
        }
    }

    private int getRepairItemCountCost() {
        try {
            return REPAIR_ITEM_COUNT_COST_FIELD.getInt(this);
        } catch (IllegalAccessException exception) {
            throw new IllegalStateException("Failed to access AnvilMenu repairItemCountCost", exception);
        }
    }

    private DataSlot getCostSlot() {
        try {
            return (DataSlot) COST_FIELD.get(this);
        } catch (IllegalAccessException exception) {
            throw new IllegalStateException("Failed to access AnvilMenu cost slot", exception);
        }
    }

    private static Field getField(String name) {
        try {
            Field field = AnvilMenu.class.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (ReflectiveOperationException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }
}
