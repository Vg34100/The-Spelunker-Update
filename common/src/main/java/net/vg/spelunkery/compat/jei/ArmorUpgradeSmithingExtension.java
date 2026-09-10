package net.vg.spelunkery.compat.jei;

import mezz.jei.api.gui.builder.IIngredientAcceptor;
import mezz.jei.api.recipe.category.extensions.vanilla.smithing.ISmithingCategoryExtension;
import net.minecraft.world.item.ItemStack;
import net.vg.spelunkery.item.ArmorUpgradeHelper;
import net.vg.spelunkery.recipe.ArmorUpgradeSmithingRecipe;

import java.util.List;

public class ArmorUpgradeSmithingExtension implements ISmithingCategoryExtension<ArmorUpgradeSmithingRecipe> {
    @Override
    public <T extends IIngredientAcceptor<T>> void setTemplate(ArmorUpgradeSmithingRecipe recipe, T slot) {
        slot.addIngredients(recipe.template());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setBase(ArmorUpgradeSmithingRecipe recipe, T slot) {
        slot.addIngredients(recipe.base());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setAddition(ArmorUpgradeSmithingRecipe recipe, T slot) {
        slot.addIngredients(recipe.addition());
    }

    @Override
    public <T extends IIngredientAcceptor<T>> void setOutput(ArmorUpgradeSmithingRecipe recipe, T slot) {
        List<ItemStack> outputs = recipe.base().items()
                .map(holder -> ArmorUpgradeHelper.applyUpgrade(new ItemStack(holder.value()), recipe.upgrade()))
                .toList();
        if (!outputs.isEmpty()) {
            slot.addItemStacks(outputs);
        }
    }
}
