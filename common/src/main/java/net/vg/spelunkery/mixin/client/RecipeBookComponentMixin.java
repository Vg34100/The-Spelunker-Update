package net.vg.spelunkery.mixin.client;

import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeCollection;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.vg.spelunkery.menu.FoundryMenu;
import net.vg.spelunkery.recipe.FoundryRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(RecipeBookComponent.class)
public abstract class RecipeBookComponentMixin {
    @Shadow
    protected RecipeBookMenu<?, ?> menu;

    @Shadow
    private RecipeBookPage recipeBookPage;

    @Inject(method = "updateCollections", at = @At("TAIL"))
    private void spelunkery$filterFoundryCollections(boolean resetPage, CallbackInfo ci) {
        if (!(menu instanceof FoundryMenu)) {
            return;
        }

        List<RecipeCollection> currentCollections = ((RecipeBookPageAccessor) recipeBookPage).spelunkery$getRecipeCollections();
        List<RecipeCollection> foundryCollections = currentCollections.stream()
                .filter(collection -> collection.getRecipes().stream().anyMatch(holder -> holder.value() instanceof FoundryRecipe))
                .toList();

        recipeBookPage.updateCollections(foundryCollections, resetPage);
    }
}
