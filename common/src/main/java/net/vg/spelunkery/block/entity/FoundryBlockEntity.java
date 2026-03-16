package net.vg.spelunkery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.spelunkery.block.CrucibleBlock;
import net.vg.spelunkery.registry.SpelunkeryBlockEntities;
import net.vg.spelunkery.registry.SpelunkeryItems;

public class FoundryBlockEntity extends BlockEntity {
    private static final int COPPER_SLOT = 0;
    private static final int TIN_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    private final SimpleContainer inventory = new SimpleContainer(3);

    public FoundryBlockEntity(BlockPos pos, BlockState blockState) {
        super(SpelunkeryBlockEntities.FOUNDRY.get(), pos, blockState);
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    public boolean tryInsert(ItemStack heldStack, Player player, net.minecraft.world.InteractionHand hand) {
        if (level == null) {
            return false;
        }

        if (heldStack.is(Items.COPPER_INGOT)) {
            ItemStack copper = inventory.getItem(COPPER_SLOT);
            if (!copper.isEmpty() && copper.getCount() >= 3) {
                player.displayClientMessage(Component.literal("The foundry already has enough copper."), true);
                return true;
            }

            insertOne(COPPER_SLOT, Items.COPPER_INGOT, heldStack, player, hand);
            player.displayClientMessage(Component.literal("Added copper to the foundry."), true);
            return true;
        }

        if (heldStack.is(SpelunkeryItems.TIN_INGOT.get())) {
            ItemStack tin = inventory.getItem(TIN_SLOT);
            if (!tin.isEmpty() && tin.getCount() >= 1) {
                player.displayClientMessage(Component.literal("The foundry already has enough tin."), true);
                return true;
            }

            insertOne(TIN_SLOT, SpelunkeryItems.TIN_INGOT.get(), heldStack, player, hand);
            player.displayClientMessage(Component.literal("Added tin to the foundry."), true);
            return true;
        }

        return false;
    }

    public void tryProcess() {
        if (level == null || level.isClientSide) {
            return;
        }

        ItemStack copper = inventory.getItem(COPPER_SLOT);
        ItemStack tin = inventory.getItem(TIN_SLOT);
        ItemStack output = inventory.getItem(OUTPUT_SLOT);

        if (copper.getCount() < 3 || tin.getCount() < 1) {
            return;
        }

        BlockPos cruciblePos = worldPosition.below();
        if (!CrucibleBlock.isFilled(level, cruciblePos)) {
            return;
        }

        if (!output.isEmpty() && (!output.is(SpelunkeryItems.BRONZE_INGOT.get()) || output.getCount() > 60)) {
            return;
        }

        copper.shrink(3);
        tin.shrink(1);
        if (copper.isEmpty()) {
            inventory.setItem(COPPER_SLOT, ItemStack.EMPTY);
        }
        if (tin.isEmpty()) {
            inventory.setItem(TIN_SLOT, ItemStack.EMPTY);
        }

        if (output.isEmpty()) {
            inventory.setItem(OUTPUT_SLOT, new ItemStack(SpelunkeryItems.BRONZE_INGOT.get(), 4));
        } else {
            output.grow(4);
        }

        CrucibleBlock.consumeLava(level, cruciblePos);
        setChanged();
    }

    public boolean tryExtract(Player player) {
        ItemStack output = inventory.getItem(OUTPUT_SLOT);
        if (output.isEmpty()) {
            return false;
        }

        ItemStack extracted = output.copy();
        if (!player.addItem(extracted)) {
            player.drop(extracted, false);
        }

        inventory.setItem(OUTPUT_SLOT, ItemStack.EMPTY);
        setChanged();
        return true;
    }

    public String describeState() {
        int copper = inventory.getItem(COPPER_SLOT).getCount();
        int tin = inventory.getItem(TIN_SLOT).getCount();
        int bronze = inventory.getItem(OUTPUT_SLOT).getCount();
        boolean lava = level != null && CrucibleBlock.isFilled(level, worldPosition.below());
        return "Foundry: " + copper + "/3 copper, " + tin + "/1 tin, lava " + (lava ? "ready" : "missing") + ", bronze " + bronze;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Items", inventory.createTag(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.fromTag(tag.getList("Items", 10), registries);
    }

    private void insertOne(int slot, net.minecraft.world.item.Item item, ItemStack heldStack, Player player, net.minecraft.world.InteractionHand hand) {
        ItemStack current = inventory.getItem(slot);
        if (current.isEmpty()) {
            inventory.setItem(slot, new ItemStack(item, 1));
        } else {
            current.grow(1);
        }

        if (!player.getAbilities().instabuild) {
            heldStack.shrink(1);
            player.setItemInHand(hand, heldStack);
        }

        setChanged();
    }
}
