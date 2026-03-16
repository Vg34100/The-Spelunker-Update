package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.recipe.FoundryRecipe;

public final class SpelunkeryRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Spelunkery.MOD_ID, Registries.RECIPE_SERIALIZER);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Spelunkery.MOD_ID, Registries.RECIPE_TYPE);

    public static final RegistrySupplier<RecipeSerializer<FoundryRecipe>> FOUNDRY_SERIALIZER = RECIPE_SERIALIZERS.register(
            "foundry",
            FoundryRecipe.Serializer::new
    );

    public static final RegistrySupplier<RecipeType<FoundryRecipe>> FOUNDRY_TYPE = RECIPE_TYPES.register(
            "foundry",
            () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "spelunkery:foundry";
                }
            }
    );

    private static boolean initialized;

    private SpelunkeryRecipeTypes() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        RECIPE_SERIALIZERS.register();
        RECIPE_TYPES.register();
    }
}
