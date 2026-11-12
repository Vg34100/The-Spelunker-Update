package net.vg.spelunkery.recipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.vg.spelunkery.item.MinerHelmetGem;
import net.vg.spelunkery.item.MinerHelmetHelper;
import net.vg.spelunkery.registry.SpelunkeryRecipeTypes;

public class MinerHelmetSocketRecipe extends CustomRecipe {
    public MinerHelmetSocketRecipe(net.minecraft.resources.ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer input, Level level) {
        return findHelmet(input) != null && findGem(input) != null && countNonEmpty(input) == 2;
    }

    @Override
    public ItemStack assemble(CraftingContainer input, RegistryAccess provider) {
        ItemStack helmet = findHelmet(input);
        MinerHelmetGem gem = findGem(input);
        return helmet != null && gem != null ? MinerHelmetHelper.socket(helmet, gem) : ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SpelunkeryRecipeTypes.MINER_HELMET_SOCKET_SERIALIZER.get();
    }

    private static ItemStack findHelmet(CraftingContainer input) {
        ItemStack found = null;
        for (int i = 0; i < input.getContainerSize(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            if (!MinerHelmetHelper.isMinerHelmet(stack)) {
                continue;
            }
            if (found != null) {
                return null;
            }
            found = stack;
        }
        return found;
    }

    private static MinerHelmetGem findGem(CraftingContainer input) {
        MinerHelmetGem found = null;
        for (int i = 0; i < input.getContainerSize(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) {
                continue;
            }
            MinerHelmetGem gem = MinerHelmetGem.fromIngredient(stack);
            if (gem == null) {
                continue;
            }
            if (found != null) {
                return null;
            }
            found = gem;
        }
        return found;
    }

    private static int countNonEmpty(CraftingContainer input) {
        int count = 0;
        for (int i = 0; i < input.getContainerSize(); i++) {
            if (!input.getItem(i).isEmpty()) {
                count++;
            }
        }
        return count;
    }
}
