package net.vg.spelunkery.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.recipe.FoundryRecipe;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.vg.spelunkery.registry.SpelunkeryItems;

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
        registerFoundryRecipes(registration);
        registerBrewingRecipes(registration);
    }

    private void registerFoundryRecipes(IRecipeRegistration registration) {
        var server = Minecraft.getInstance().getSingleplayerServer();
        if (server == null) return;

        List<FoundryRecipe> foundryRecipes = server.getRecipeManager().getRecipes().stream()
                .filter(h -> h.value() instanceof FoundryRecipe)
                .map(h -> (FoundryRecipe) h.value())
                .toList();
        if (!foundryRecipes.isEmpty()) {
            registration.addRecipes(FoundryCategory.TYPE, foundryRecipes);
        }
    }

    private void registerBrewingRecipes(IRecipeRegistration registration) {
        IVanillaRecipeFactory factory = registration.getVanillaRecipeFactory();
        ItemStack thickPotion = PotionContents.createItemStack(Items.POTION, Potions.THICK);

        List<IJeiBrewingRecipe> brewingRecipes = List.of(
                factory.createBrewingRecipe(
                        List.of(new ItemStack(SpelunkeryItems.TOPAZ_SHARD.get())),
                        thickPotion,
                        new ItemStack(SpelunkeryItems.SPELUNKERS_BREW.get()),
                        Identifier.fromNamespaceAndPath(Spelunkery.MOD_ID, "brewing/spelunkers_brew")
                ),
                factory.createBrewingRecipe(
                        List.of(new ItemStack(SpelunkeryItems.BAT_WING.get())),
                        thickPotion,
                        new ItemStack(SpelunkeryItems.DANGERSENSE_TONIC.get()),
                        Identifier.fromNamespaceAndPath(Spelunkery.MOD_ID, "brewing/dangersense_tonic")
                ),
                factory.createBrewingRecipe(
                        List.of(new ItemStack(Items.IRON_INGOT)),
                        thickPotion,
                        new ItemStack(SpelunkeryItems.MINERS_TONIC.get()),
                        Identifier.fromNamespaceAndPath(Spelunkery.MOD_ID, "brewing/miners_tonic")
                )
        );
        registration.addRecipes(RecipeTypes.BREWING, brewingRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(SpelunkeryBlocks.FOUNDRY.get()), FoundryCategory.TYPE);
    }
}
