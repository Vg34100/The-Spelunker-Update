package net.vg.spelunkery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.spelunkery.block.CrucibleBlock;
import net.vg.spelunkery.menu.FoundryMenu;
import net.vg.spelunkery.recipe.FoundryRecipe;
import net.vg.spelunkery.recipe.FoundryRecipeInput;
import net.vg.spelunkery.registry.SpelunkeryBlockEntities;
import net.vg.spelunkery.registry.SpelunkeryRecipeTypes;

import java.util.List;
import java.util.Optional;

public class FoundryBlockEntity extends BlockEntity implements MenuProvider {
    public static final int INPUT_SLOT_COUNT = 3;
    public static final int OUTPUT_SLOT = 3;
    private static final int SLOT_COUNT = 4;
    private static final int[] TOP_SLOTS = new int[]{0, 1, 2};
    private static final int[] BOTTOM_SLOTS = new int[]{OUTPUT_SLOT};
    private static final int[] SIDE_SLOTS = new int[]{0, 1, 2};

    private final NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private final ContainerData data = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> progress;
                case 1 -> maxProgress;
                case 2 -> hasHeat() ? 1 : 0;
                case 3 -> getLavaLevel();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> progress = value;
                case 1 -> maxProgress = value;
                default -> {
                }
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    private int progress;
    private int maxProgress = 200;

    public FoundryBlockEntity(BlockPos pos, BlockState blockState) {
        super(SpelunkeryBlockEntities.FOUNDRY.get(), pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FoundryBlockEntity foundry) {
        foundry.serverTick();
    }

    private void serverTick() {
        if (level == null || level.isClientSide) {
            return;
        }

        Optional<RecipeHolder<FoundryRecipe>> match = getMatchingRecipe();
        if (match.isEmpty() || !hasHeat()) {
            if (progress != 0) {
                progress = 0;
                setChanged();
            }
            return;
        }

        FoundryRecipe recipe = match.get().value();
        maxProgress = recipe.processTime();
        progress++;

        if (progress >= maxProgress) {
            craft(recipe);
            progress = 0;
        }

        setChanged();
    }

    public SimpleContainer getInventory() {
        SimpleContainer container = new SimpleContainer(SLOT_COUNT);
        for (int slot = 0; slot < SLOT_COUNT; slot++) {
            container.setItem(slot, items.get(slot).copy());
        }
        return container;
    }

    public ContainerData getData() {
        return data;
    }

    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (slot < INPUT_SLOT_COUNT) {
            progress = 0;
        }
        setChanged();
    }

    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = items.get(slot);
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack split = stack.split(amount);
        if (stack.isEmpty()) {
            items.set(slot, ItemStack.EMPTY);
        }
        if (slot < INPUT_SLOT_COUNT) {
            progress = 0;
        }
        setChanged();
        return split;
    }

    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = items.get(slot);
        items.set(slot, ItemStack.EMPTY);
        return stack;
    }

    public boolean canPlaceItem(int slot, ItemStack stack) {
        return slot < INPUT_SLOT_COUNT;
    }

    public boolean canTakeItem(int slot, Direction direction) {
        return slot == OUTPUT_SLOT;
    }

    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return BOTTOM_SLOTS;
        }
        return direction == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
    }

    public int getContainerSize() {
        return SLOT_COUNT;
    }

    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void clearContent() {
        for (int slot = 0; slot < items.size(); slot++) {
            items.set(slot, ItemStack.EMPTY);
        }
        setChanged();
    }

    public void dropContents() {
        if (level != null) {
            Containers.dropContents(level, worldPosition, getInventory());
        }
    }

    @Override
    public net.minecraft.network.chat.Component getDisplayName() {
        return net.minecraft.network.chat.Component.translatable("block.spelunkery.foundry");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new FoundryMenu(containerId, playerInventory, this);
    }

    public boolean hasHeat() {
        return level != null && CrucibleBlock.isFilled(level, worldPosition.below());
    }

    public int getLavaLevel() {
        if (level == null) {
            return 0;
        }

        BlockState crucibleState = level.getBlockState(worldPosition.below());
        if (!(crucibleState.getBlock() instanceof CrucibleBlock)) {
            return 0;
        }

        return crucibleState.getValue(CrucibleBlock.LEVEL);
    }

    public boolean isCrafting() {
        return progress > 0 && hasHeat();
    }

    public int getScaledProgress(int pixels) {
        return maxProgress > 0 && progress > 0 ? progress * pixels / maxProgress : 0;
    }

    private Optional<RecipeHolder<FoundryRecipe>> getMatchingRecipe() {
        if (level == null) {
            return Optional.empty();
        }

        FoundryRecipeInput input = new FoundryRecipeInput(List.of(
                items.get(0).copy(),
                items.get(1).copy(),
                items.get(2).copy()
        ));

        return level.getRecipeManager()
                .getRecipeFor(SpelunkeryRecipeTypes.FOUNDRY_TYPE.get(), input, level)
                .filter(holder -> holder.value().canOutput(items.get(OUTPUT_SLOT)));
    }

    private void craft(FoundryRecipe recipe) {
        if (level == null) {
            return;
        }

        for (int use = 0; use < recipe.lavaCost(); use++) {
            if (!CrucibleBlock.consumeLava(level, worldPosition.below())) {
                return;
            }
        }

        recipe.consumeInputs(items);

        ItemStack output = items.get(OUTPUT_SLOT);
        ItemStack result = recipe.result().copy();
        if (output.isEmpty()) {
            items.set(OUTPUT_SLOT, result);
        } else {
            output.grow(result.getCount());
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        for (int slot = 0; slot < items.size(); slot++) {
            if (!items.get(slot).isEmpty()) {
                tag.put("Item" + slot, items.get(slot).saveOptional(registries));
            }
        }
        tag.putInt("Progress", progress);
        tag.putInt("MaxProgress", maxProgress);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        for (int slot = 0; slot < SLOT_COUNT; slot++) {
            String key = "Item" + slot;
            items.set(slot, ItemStack.parseOptional(registries, tag.getCompound(key)));
        }
        progress = tag.getInt("Progress");
        maxProgress = tag.contains("MaxProgress") ? tag.getInt("MaxProgress") : 200;
    }
}
