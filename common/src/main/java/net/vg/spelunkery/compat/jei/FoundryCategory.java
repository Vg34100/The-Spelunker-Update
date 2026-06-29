package net.vg.spelunkery.compat.jei;

import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.recipe.FoundryIngredient;
import net.vg.spelunkery.recipe.FoundryRecipe;
import net.vg.spelunkery.registry.SpelunkeryBlocks;

import java.util.List;

public class FoundryCategory implements IRecipeCategory<FoundryRecipe> {
    public static final RecipeType<FoundryRecipe> TYPE = RecipeType.create(Spelunkery.MOD_ID, "foundry", FoundryRecipe.class);
    public static final Identifier UID = Identifier.fromNamespaceAndPath(Spelunkery.MOD_ID, "foundry");

    private static final int SLOT_SIZE = 18;
    private static final int INPUT_X = 1;
    private static final int OUTPUT_X = 48;
    private static final int CENTER_Y = 19;
    private static final int WIDTH = 70;
    private static final int HEIGHT = 56;

    private final IDrawable icon;
    private final Component title;

    public FoundryCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(SpelunkeryBlocks.FOUNDRY.get()));
        this.title = Component.translatable("block.spelunkery.foundry");
    }

    @Override
    public RecipeType<FoundryRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, FoundryRecipe recipe, IFocusGroup focuses) {
        List<FoundryIngredient> ingredients = recipe.ingredients();
        for (int i = 0; i < ingredients.size() && i < 3; i++) {
            FoundryIngredient fi = ingredients.get(i);
            int slotY = 1 + i * (SLOT_SIZE + 1);
            List<ItemStack> stacks = fi.ingredient().items()
                    .map(holder -> {
                        ItemStack s = new ItemStack(holder);
                        s.setCount(fi.count());
                        return s;
                    })
                    .toList();
            builder.addSlot(RecipeIngredientRole.INPUT, INPUT_X, slotY)
                    .addItemStacks(stacks);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, OUTPUT_X, CENTER_Y)
                .addItemStack(recipe.result().create());
    }
}
