package net.vg.spelunkery.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.vg.spelunkery.registry.SpelunkeryRecipeTypes;

import java.util.List;

public record FoundryRecipe(
        List<FoundryIngredient> ingredients,
        ItemStack result,
        int processTime,
        int lavaCost
) implements Recipe<FoundryRecipeInput> {
    public static final MapCodec<FoundryRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            FoundryIngredient.CODEC.listOf().fieldOf("ingredients").forGetter(FoundryRecipe::ingredients),
            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(FoundryRecipe::result),
            Codec.intRange(1, 1200).optionalFieldOf("process_time", 200).forGetter(FoundryRecipe::processTime),
            Codec.intRange(1, 8).optionalFieldOf("lava_cost", 1).forGetter(FoundryRecipe::lavaCost)
    ).apply(instance, FoundryRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FoundryRecipe> STREAM_CODEC = StreamCodec.composite(
            FoundryIngredient.STREAM_CODEC.apply(ByteBufCodecs.list()), FoundryRecipe::ingredients,
            ItemStack.STREAM_CODEC, FoundryRecipe::result,
            ByteBufCodecs.VAR_INT, FoundryRecipe::processTime,
            ByteBufCodecs.VAR_INT, FoundryRecipe::lavaCost,
            FoundryRecipe::new
    );

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
    public ItemStack assemble(FoundryRecipeInput input, HolderLookup.Provider provider) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
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

    public static final class Serializer implements RecipeSerializer<FoundryRecipe> {
        @Override
        public MapCodec<FoundryRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FoundryRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
