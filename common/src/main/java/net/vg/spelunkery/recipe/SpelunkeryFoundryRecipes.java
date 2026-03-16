package net.vg.spelunkery.recipe;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.vg.spelunkery.registry.SpelunkeryItems;

import java.util.List;
import java.util.Optional;

public final class SpelunkeryFoundryRecipes {
    private static final List<FoundryRecipe> RECIPES = List.of(
            new FoundryRecipe(
                    "bronze",
                    List.of(
                            new FoundryIngredient(Ingredient.of(Items.COPPER_INGOT), 3),
                            new FoundryIngredient(Ingredient.of(SpelunkeryItems.TIN_INGOT.get()), 1)
                    ),
                    new ItemStack(SpelunkeryItems.BRONZE_INGOT.get(), 4),
                    200,
                    1
            )
    );

    private SpelunkeryFoundryRecipes() {
    }

    public static Optional<FoundryRecipe> findMatch(Container container, ItemStack output) {
        return RECIPES.stream().filter(recipe -> recipe.matches(container, output)).findFirst();
    }
}
