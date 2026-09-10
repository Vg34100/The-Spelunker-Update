package net.vg.spelunkery.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.vg.spelunkery.block.entity.FoundryBlockEntity;
import net.vg.spelunkery.recipe.FoundryIngredient;
import net.vg.spelunkery.recipe.FoundryRecipe;

import java.util.List;

public class FoundryRecipeBookComponent extends RecipeBookComponent {
    @Override
    public void setupGhostRecipe(RecipeHolder<?> recipeHolder, List<Slot> slots) {
        if (!(recipeHolder.value() instanceof FoundryRecipe recipe)) {
            super.setupGhostRecipe(recipeHolder, slots);
            return;
        }

        ghostRecipe.clear();
        ghostRecipe.setRecipe(recipeHolder);
        ghostRecipe.addIngredient(Ingredient.of(recipe.result()), slots.get(FoundryBlockEntity.OUTPUT_SLOT).x, slots.get(FoundryBlockEntity.OUTPUT_SLOT).y);

        int ingredientSlots = Math.min(recipe.ingredients().size(), FoundryBlockEntity.INPUT_SLOT_COUNT);
        for (int slotIndex = 0; slotIndex < ingredientSlots; slotIndex++) {
            FoundryIngredient ingredient = recipe.ingredients().get(slotIndex);
            Slot slot = slots.get(slotIndex);
            ghostRecipe.addIngredient(ingredient.ingredient(), slot.x, slot.y);
        }
    }

    @Override
    public void renderGhostRecipe(GuiGraphics guiGraphics, int leftPos, int topPos, boolean singleResultSlot, float partialTick) {
        super.renderGhostRecipe(guiGraphics, leftPos, topPos, singleResultSlot, partialTick);

        if (minecraft == null || ghostRecipe.getRecipe() == null || !(ghostRecipe.getRecipe().value() instanceof FoundryRecipe recipe)) {
            return;
        }

        int ingredientSlots = Math.min(recipe.ingredients().size(), FoundryBlockEntity.INPUT_SLOT_COUNT);
        for (int slotIndex = 0; slotIndex < ingredientSlots; slotIndex++) {
            FoundryIngredient ingredient = recipe.ingredients().get(slotIndex);
            if (ingredient.count() <= 1) {
                continue;
            }

            Slot slot = menu.getSlot(slotIndex);
            guiGraphics.renderItemDecorations(
                    minecraft.font,
                    sampleStack(ingredient),
                    leftPos + slot.x,
                    topPos + slot.y,
                    Integer.toString(ingredient.count())
            );
        }
    }

    private static ItemStack sampleStack(FoundryIngredient ingredient) {
        ItemStack[] matches = ingredient.ingredient().getItems();
        return matches.length > 0 ? matches[0] : ItemStack.EMPTY;
    }
}
