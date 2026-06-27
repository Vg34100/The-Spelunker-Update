package net.vg.spelunkery.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
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
            ItemStack.CODEC.fieldOf("result").forGetter(FoundryRecipe::result),
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

    @Override
    public ItemStack assemble(FoundryRecipeInput input) {
        return result.copy();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return new RecipeBookCategory();
    }

    @Override
    public RecipeSerializer<? extends Recipe<FoundryRecipeInput>> getSerializer() {
        return SpelunkeryRecipeTypes.FOUNDRY_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<FoundryRecipeInput>> getType() {
        return SpelunkeryRecipeTypes.FOUNDRY_TYPE.get();
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
}
