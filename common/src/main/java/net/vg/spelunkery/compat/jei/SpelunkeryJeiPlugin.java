package net.vg.spelunkery.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.recipe.FoundryRecipe;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.vg.spelunkery.registry.SpelunkeryRecipeTypes;

import java.util.List;

@JeiPlugin
public class SpelunkeryJeiPlugin implements IModPlugin {
    private static final Identifier PLUGIN_ID = Identifier.fromNamespaceAndPath(Spelunkery.MOD_ID, "jei_plugin");

    @Override
    public Identifier getPluginUid() {
        return PLUGIN_ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new FoundryCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null) return;

        List<FoundryRecipe> foundryRecipes = server.getRecipeManager().getRecipes().stream()
                .filter(holder -> holder.value().getType() == SpelunkeryRecipeTypes.FOUNDRY_TYPE.get())
                .map(holder -> (RecipeHolder<FoundryRecipe>) holder)
                .map(RecipeHolder::value)
                .toList();
        registration.addRecipes(FoundryCategory.TYPE, foundryRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(SpelunkeryBlocks.FOUNDRY.get()), FoundryCategory.TYPE);
    }
}
