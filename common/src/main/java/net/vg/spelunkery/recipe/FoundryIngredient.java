package net.vg.spelunkery.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record FoundryIngredient(Ingredient ingredient, int count) {
    public boolean matches(ItemStack stack) {
        return ingredient.test(stack);
    }
}
