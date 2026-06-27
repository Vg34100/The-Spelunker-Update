package net.vg.spelunkery.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.vg.spelunkery.item.MinerHelmetGem;
import net.vg.spelunkery.item.MinerHelmetHelper;
import net.vg.spelunkery.registry.SpelunkeryRecipeTypes;

public class MinerHelmetSocketRecipe extends CustomRecipe {
    public MinerHelmetSocketRecipe() {
        super();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        return findHelmet(input) != null && findGem(input) != null && countNonEmpty(input) == 2;
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        ItemStack helmet = findHelmet(input);
        MinerHelmetGem gem = findGem(input);
        return helmet != null && gem != null ? MinerHelmetHelper.socket(helmet, gem) : ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SpelunkeryRecipeTypes.MINER_HELMET_SOCKET_SERIALIZER.get();
    }

    private static ItemStack findHelmet(CraftingInput input) {
        ItemStack found = null;
        for (int i = 0; i < input.size(); i++) {
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

    private static MinerHelmetGem findGem(CraftingInput input) {
        MinerHelmetGem found = null;
        for (int i = 0; i < input.size(); i++) {
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

    private static int countNonEmpty(CraftingInput input) {
        int count = 0;
        for (int i = 0; i < input.size(); i++) {
            if (!input.getItem(i).isEmpty()) {
                count++;
            }
        }
        return count;
    }
}
