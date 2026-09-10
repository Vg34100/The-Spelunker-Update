package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.recipe.ArmorUpgradeSmithingRecipe;
import net.vg.spelunkery.recipe.FoundryRecipe;
import net.vg.spelunkery.recipe.MinerHelmetSocketRecipe;

public final class SpelunkeryRecipeTypes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Spelunkery.MOD_ID, Registries.RECIPE_SERIALIZER);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Spelunkery.MOD_ID, Registries.RECIPE_TYPE);

    public static final RegistrySupplier<RecipeSerializer<FoundryRecipe>> FOUNDRY_SERIALIZER = RECIPE_SERIALIZERS.register(
            "foundry",
            FoundryRecipe.Serializer::new
    );
    public static final RegistrySupplier<RecipeSerializer<ArmorUpgradeSmithingRecipe>> ARMOR_UPGRADE_SMITHING_SERIALIZER = RECIPE_SERIALIZERS.register(
            "armor_upgrade_smithing",
            ArmorUpgradeSmithingRecipe.Serializer::new
    );
    public static final RegistrySupplier<SimpleCraftingRecipeSerializer<MinerHelmetSocketRecipe>> MINER_HELMET_SOCKET_SERIALIZER = RECIPE_SERIALIZERS.register(
            "miner_helmet_socket",
            () -> new SimpleCraftingRecipeSerializer<>(MinerHelmetSocketRecipe::new)
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
