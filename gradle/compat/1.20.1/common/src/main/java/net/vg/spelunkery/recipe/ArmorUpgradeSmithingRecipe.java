package net.vg.spelunkery.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.vg.spelunkery.item.ArmorUpgrade;
import net.vg.spelunkery.item.ArmorUpgradeHelper;
import net.vg.spelunkery.registry.SpelunkeryRecipeTypes;

public record ArmorUpgradeSmithingRecipe(
        net.minecraft.resources.ResourceLocation id,
        Ingredient template,
        Ingredient base,
        Ingredient addition,
        ArmorUpgrade upgrade
) implements SmithingRecipe {
    @Override
    public boolean matches(net.minecraft.world.Container input, net.minecraft.world.level.Level level) {
        return template.test(input.getItem(0))
                && base.test(input.getItem(1))
                && addition.test(input.getItem(2))
                && ArmorUpgradeHelper.isUpgradeableArmor(input.getItem(1))
                && ArmorUpgradeHelper.getUpgrade(input.getItem(1)) == null;
    }

    @Override
    public ItemStack assemble(net.minecraft.world.Container input, net.minecraft.core.RegistryAccess provider) {
        return ArmorUpgradeHelper.applyUpgrade(input.getItem(1), upgrade);
    }

    @Override
    public ItemStack getResultItem(net.minecraft.core.RegistryAccess provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SpelunkeryRecipeTypes.ARMOR_UPGRADE_SMITHING_SERIALIZER.get();
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        return template.test(stack);
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return base.test(stack);
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        return addition.test(stack);
    }

    @Override public net.minecraft.resources.ResourceLocation getId() { return id; }

    public static final class Serializer implements RecipeSerializer<ArmorUpgradeSmithingRecipe> {
        @Override
        public ArmorUpgradeSmithingRecipe fromJson(net.minecraft.resources.ResourceLocation id, com.google.gson.JsonObject json) {
            return new ArmorUpgradeSmithingRecipe(id, Ingredient.fromJson(json.get("template")), Ingredient.fromJson(json.get("base")),
                    Ingredient.fromJson(json.get("addition")), ArmorUpgrade.byId(net.minecraft.util.GsonHelper.getAsString(json, "upgrade")));
        }
        @Override
        public ArmorUpgradeSmithingRecipe fromNetwork(net.minecraft.resources.ResourceLocation id, net.minecraft.network.FriendlyByteBuf buf) {
            return new ArmorUpgradeSmithingRecipe(id, Ingredient.fromNetwork(buf), Ingredient.fromNetwork(buf), Ingredient.fromNetwork(buf), ArmorUpgrade.byId(buf.readUtf()));
        }
        @Override
        public void toNetwork(net.minecraft.network.FriendlyByteBuf buf, ArmorUpgradeSmithingRecipe recipe) {
            recipe.template().toNetwork(buf); recipe.base().toNetwork(buf); recipe.addition().toNetwork(buf); buf.writeUtf(recipe.upgrade().id());
        }
    }
}
