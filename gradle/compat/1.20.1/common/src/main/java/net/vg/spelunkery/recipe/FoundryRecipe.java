package net.vg.spelunkery.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.vg.spelunkery.registry.SpelunkeryRecipeTypes;

import java.util.List;

public record FoundryRecipe(
        net.minecraft.resources.ResourceLocation id,
        List<FoundryIngredient> ingredients,
        ItemStack result,
        int processTime,
        int lavaCost
) implements Recipe<FoundryRecipeInput> {
    @Override
    public boolean matches(FoundryRecipeInput input, Level level) {
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (!stack.isEmpty() && ingredients.stream().noneMatch(ingredient -> ingredient.matches(stack))) {
                return false;
            }
        }

        return ingredients.stream().allMatch(ingredient -> countMatchingItems(input, ingredient) >= ingredient.count());
    }

    public boolean canOutput(ItemStack output) {
        if (output.isEmpty()) {
            return true;
        }

        return ItemStack.isSameItemSameComponents(output, result)
                && output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    public void consumeInputs(List<ItemStack> items) {
        for (FoundryIngredient ingredient : ingredients) {
            int remaining = ingredient.count();
            for (int slot = 0; slot < 3 && remaining > 0; slot++) {
                ItemStack stack = items.get(slot);
                if (!ingredient.matches(stack)) {
                    continue;
                }

                int used = Math.min(remaining, stack.getCount());
                stack.shrink(used);
                if (stack.isEmpty()) {
                    items.set(slot, ItemStack.EMPTY);
                }
                remaining -= used;
            }
        }
    }

    @Override
    public ItemStack assemble(FoundryRecipeInput input, net.minecraft.core.RegistryAccess provider) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(net.minecraft.core.RegistryAccess provider) {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SpelunkeryRecipeTypes.FOUNDRY_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return SpelunkeryRecipeTypes.FOUNDRY_TYPE.get();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> expanded = NonNullList.create();
        for (FoundryIngredient ingredient : ingredients) {
            for (int count = 0; count < ingredient.count(); count++) {
                expanded.add(ingredient.ingredient());
            }
        }
        return expanded;
    }

    private int countMatchingItems(FoundryRecipeInput input, FoundryIngredient ingredient) {
        int total = 0;
        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack stack = input.getItem(slot);
            if (ingredient.matches(stack)) {
                total += stack.getCount();
            }
        }
        return total;
    }

    @Override public net.minecraft.resources.ResourceLocation getId() { return id; }

    public static final class Serializer implements RecipeSerializer<FoundryRecipe> {
        @Override
        public FoundryRecipe fromJson(net.minecraft.resources.ResourceLocation id, com.google.gson.JsonObject json) {
            java.util.List<FoundryIngredient> ingredients = new java.util.ArrayList<>();
            for (var element : net.minecraft.util.GsonHelper.getAsJsonArray(json, "ingredients")) {
                var value = element.getAsJsonObject();
                int count = net.minecraft.util.GsonHelper.getAsInt(value, "count", 1);
                if (count < 1 || count > 64) throw new com.google.gson.JsonSyntaxException("Invalid foundry ingredient count");
                ingredients.add(new FoundryIngredient(Ingredient.fromJson(value.get("ingredient")), count));
            }
            int time = net.minecraft.util.GsonHelper.getAsInt(json, "process_time", 200);
            int lava = net.minecraft.util.GsonHelper.getAsInt(json, "lava_cost", 1);
            if (time < 1 || time > 1200 || lava < 1 || lava > 8) throw new com.google.gson.JsonSyntaxException("Invalid foundry process parameters");
            return new FoundryRecipe(id, ingredients, net.minecraft.world.item.crafting.ShapedRecipe.itemStackFromJson(
                    net.minecraft.util.GsonHelper.getAsJsonObject(json, "result")), time, lava);
        }

        @Override
        public FoundryRecipe fromNetwork(net.minecraft.resources.ResourceLocation id, net.minecraft.network.FriendlyByteBuf buffer) {
            var ingredients = buffer.readList(buf -> new FoundryIngredient(Ingredient.fromNetwork(buf), buf.readVarInt()));
            return new FoundryRecipe(id, ingredients, buffer.readItem(), buffer.readVarInt(), buffer.readVarInt());
        }

        @Override
        public void toNetwork(net.minecraft.network.FriendlyByteBuf buffer, FoundryRecipe recipe) {
            buffer.writeCollection(recipe.ingredients(), (buf, value) -> { value.ingredient().toNetwork(buf); buf.writeVarInt(value.count()); });
            buffer.writeItem(recipe.result());
            buffer.writeVarInt(recipe.processTime());
            buffer.writeVarInt(recipe.lavaCost());
        }
    }
}
