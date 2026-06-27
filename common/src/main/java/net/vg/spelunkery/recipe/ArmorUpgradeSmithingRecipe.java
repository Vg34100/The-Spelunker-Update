package net.vg.spelunkery.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.vg.spelunkery.item.ArmorUpgrade;
import net.vg.spelunkery.item.ArmorUpgradeHelper;
import net.vg.spelunkery.registry.SpelunkeryRecipeTypes;

import java.util.Optional;

public record ArmorUpgradeSmithingRecipe(
        Ingredient template,
        Ingredient base,
        Ingredient addition,
        ArmorUpgrade upgrade
) implements SmithingRecipe {
    public static final MapCodec<ArmorUpgradeSmithingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("template").forGetter(ArmorUpgradeSmithingRecipe::template),
            Ingredient.CODEC.fieldOf("base").forGetter(ArmorUpgradeSmithingRecipe::base),
            Ingredient.CODEC.fieldOf("addition").forGetter(ArmorUpgradeSmithingRecipe::addition),
            com.mojang.serialization.Codec.STRING.xmap(ArmorUpgrade::byId, ArmorUpgrade::id).fieldOf("upgrade").forGetter(ArmorUpgradeSmithingRecipe::upgrade)
    ).apply(instance, ArmorUpgradeSmithingRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ArmorUpgradeSmithingRecipe> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, ArmorUpgradeSmithingRecipe::template,
            Ingredient.CONTENTS_STREAM_CODEC, ArmorUpgradeSmithingRecipe::base,
            Ingredient.CONTENTS_STREAM_CODEC, ArmorUpgradeSmithingRecipe::addition,
            net.minecraft.network.codec.ByteBufCodecs.STRING_UTF8, recipe -> recipe.upgrade.id(),
            (t, b, a, upgradeId) -> new ArmorUpgradeSmithingRecipe(t, b, a, ArmorUpgrade.byId(upgradeId))
    );

    @Override
    public boolean matches(SmithingRecipeInput input, net.minecraft.world.level.Level level) {
        return template.test(input.template())
                && base.test(input.base())
                && addition.test(input.addition())
                && ArmorUpgradeHelper.isUpgradeableArmor(input.base())
                && ArmorUpgradeHelper.getUpgrade(input.base()) == null;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input) {
        return ArmorUpgradeHelper.applyUpgrade(input.base(), upgrade);
    }

    @Override
    public Optional<Ingredient> templateIngredient() {
        return Optional.of(template);
    }

    @Override
    public Ingredient baseIngredient() {
        return base;
    }

    @Override
    public Optional<Ingredient> additionIngredient() {
        return Optional.of(addition);
    }

    @Override
    public net.minecraft.world.item.crafting.RecipeSerializer<? extends SmithingRecipe> getSerializer() {
        return SpelunkeryRecipeTypes.ARMOR_UPGRADE_SMITHING_SERIALIZER.get();
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
        return SmithingRecipe.super.recipeBookCategory();
    }
}
