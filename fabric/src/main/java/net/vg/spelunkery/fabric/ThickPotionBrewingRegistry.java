package net.vg.spelunkery.fabric;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ThickPotionBrewingRegistry {
    private static final List<TonicRecipe> RECIPES = new ArrayList<>();
    public static final List<TonicRecipe> IMMUTABLE = Collections.unmodifiableList(RECIPES);

    private ThickPotionBrewingRegistry() {}

    public static void register(Ingredient ingredient, Item output) {
        RECIPES.add(new TonicRecipe(ingredient, output));
    }

    public record TonicRecipe(Ingredient ingredient, Item output) {}
}
