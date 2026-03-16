package net.vg.spelunkery.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record FoundryRecipe(
        String id,
        List<FoundryIngredient> ingredients,
        ItemStack result,
        int processTime,
        int lavaCost
) {
    public boolean matches(Container container, ItemStack output) {
        if (!canOutput(output)) {
            return false;
        }

        for (int slot = 0; slot < 3; slot++) {
            ItemStack stack = container.getItem(slot);
            if (!stack.isEmpty() && ingredients.stream().noneMatch(ingredient -> ingredient.matches(stack))) {
                return false;
            }
        }

        return ingredients.stream().allMatch(ingredient -> countMatchingItems(container, ingredient) >= ingredient.count());
    }

    public void consumeInputs(List<ItemStack> items) {
        for (FoundryIngredient ingredient : ingredients) {
            int remaining = ingredient.count();
            for (int slot = 0; slot < 3 && remaining > 0; slot++) {
                ItemStack stack = items.get(slot);
                if (!ingredient.matches(stack)) {
                    continue;
                }

                int used = Math.min(remaining, stack.getCount());
                stack.shrink(used);
                if (stack.isEmpty()) {
                    items.set(slot, ItemStack.EMPTY);
                }
                remaining -= used;
            }
        }
    }

    private boolean canOutput(ItemStack output) {
        if (output.isEmpty()) {
            return true;
        }

        return ItemStack.isSameItemSameComponents(output, result)
                && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private int countMatchingItems(Container container, FoundryIngredient ingredient) {
        int total = 0;
        for (int slot = 0; slot < 3; slot++) {
            ItemStack stack = container.getItem(slot);
            if (ingredient.matches(stack)) {
                total += stack.getCount();
            }
        }
        return total;
    }
}
