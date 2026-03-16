package net.vg.spelunkery.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.vg.spelunkery.block.entity.FoundryBlockEntity;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.vg.spelunkery.registry.SpelunkeryMenuTypes;

public class FoundryMenu extends AbstractContainerMenu {
    private static final int INPUT_START = 0;
    private static final int OUTPUT_SLOT = FoundryBlockEntity.OUTPUT_SLOT;
    private static final int PLAYER_INV_START = 4;
    private static final int PLAYER_INV_END = 31;
    private static final int HOTBAR_START = 31;
    private static final int HOTBAR_END = 40;

    private final Container container;
    private final ContainerData data;
    private final ContainerLevelAccess access;

    public FoundryMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, readContainer(playerInventory, buf));
    }

    public FoundryMenu(int containerId, Inventory playerInventory, FoundryBlockEntity foundry) {
        this(containerId, playerInventory, new FoundryContainer(foundry), foundry.getData(), foundry.getBlockPos());
    }

    private FoundryMenu(int containerId, Inventory playerInventory, ClientContainerAccess clientAccess) {
        this(containerId, playerInventory, clientAccess.container(), clientAccess.data(), clientAccess.pos());
    }

    public FoundryMenu(int containerId, Inventory playerInventory, Container container, ContainerData data, BlockPos pos) {
        super(SpelunkeryMenuTypes.FOUNDRY.get(), containerId);
        checkContainerSize(container, 4);
        checkContainerDataCount(data, 4);
        this.container = container;
        this.data = data;
        this.access = ContainerLevelAccess.create(playerInventory.player.level(), pos);

        addSlot(new Slot(container, 0, 29, 17));
        addSlot(new Slot(container, 1, 29, 35));
        addSlot(new Slot(container, 2, 29, 53));
        addSlot(new Slot(container, OUTPUT_SLOT, 123, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
            }
        }

        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }

        addDataSlot(DataSlot.forContainer(data, 0));
        addDataSlot(DataSlot.forContainer(data, 1));
        addDataSlot(DataSlot.forContainer(data, 2));
        addDataSlot(DataSlot.forContainer(data, 3));
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, SpelunkeryBlocks.FOUNDRY.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack moved = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = slot.getItem();
        moved = stack.copy();
        if (index == OUTPUT_SLOT) {
            if (!moveItemStackTo(stack, PLAYER_INV_START, HOTBAR_END, true)) {
                return ItemStack.EMPTY;
            }
            slot.onQuickCraft(stack, moved);
        } else if (index >= PLAYER_INV_START) {
            if (!moveItemStackTo(stack, INPUT_START, OUTPUT_SLOT, false)) {
                return ItemStack.EMPTY;
            }
        } else if (!moveItemStackTo(stack, PLAYER_INV_START, HOTBAR_END, false)) {
            return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == moved.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);
        return moved;
    }

    public boolean hasHeat() {
        return data.get(2) > 0;
    }

    public int getLavaLevel() {
        return data.get(3);
    }

    public int getScaledProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        return maxProgress > 0 && progress > 0 ? progress * 24 / maxProgress : 0;
    }

    public int getScaledHeatLevel() {
        int lava = data.get(3);
        return lava > 0 ? lava * 12 / 3 : 0;
    }

    private static Container getContainer(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof FoundryBlockEntity foundry) {
            return new FoundryContainer(foundry);
        }
        return new SimpleContainer(4);
    }

    private static ContainerData getData(Inventory inventory, BlockPos pos) {
        if (inventory.player.level().getBlockEntity(pos) instanceof FoundryBlockEntity foundry) {
            return foundry.getData();
        }
        return new SimpleContainerData(4);
    }

    private static ClientContainerAccess readContainer(Inventory inventory, FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        return new ClientContainerAccess(getContainer(inventory, pos), getData(inventory, pos), pos);
    }

    private record ClientContainerAccess(Container container, ContainerData data, BlockPos pos) {
    }

    private static final class FoundryContainer extends SimpleContainer {
        private final FoundryBlockEntity foundry;

        private FoundryContainer(FoundryBlockEntity foundry) {
            super(foundry.getContainerSize());
            this.foundry = foundry;
        }

        @Override
        public int getContainerSize() {
            return foundry.getContainerSize();
        }

        @Override
        public boolean isEmpty() {
            return foundry.isEmpty();
        }

        @Override
        public ItemStack getItem(int slot) {
            return foundry.getItem(slot);
        }

        @Override
        public ItemStack removeItem(int slot, int amount) {
            return foundry.removeItem(slot, amount);
        }

        @Override
        public ItemStack removeItemNoUpdate(int slot) {
            return foundry.removeItemNoUpdate(slot);
        }

        @Override
        public void setItem(int slot, ItemStack stack) {
            foundry.setItem(slot, stack);
        }

        @Override
        public void setChanged() {
            foundry.setChanged();
        }

        @Override
        public boolean stillValid(Player player) {
            return !foundry.isRemoved() && player.distanceToSqr(foundry.getBlockPos().getCenter()) <= 64.0D;
        }

        @Override
        public boolean canPlaceItem(int slot, ItemStack stack) {
            return foundry.canPlaceItem(slot, stack);
        }

        @Override
        public void clearContent() {
            foundry.clearContent();
        }
    }
}
