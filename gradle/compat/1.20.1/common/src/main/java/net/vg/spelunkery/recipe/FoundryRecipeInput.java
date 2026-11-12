package net.vg.spelunkery.recipe;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import java.util.List;

public final class FoundryRecipeInput extends SimpleContainer {
    public FoundryRecipeInput(List<ItemStack> items) { super(items.toArray(ItemStack[]::new)); }
    public int size() { return getContainerSize(); }
}
