package net.vg.spelunkery.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record FoundryIngredient(Ingredient ingredient, int count) {
    public static final Codec<FoundryIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(FoundryIngredient::ingredient),
            Codec.intRange(1, 64).optionalFieldOf("count", 1).forGetter(FoundryIngredient::count)
    ).apply(instance, FoundryIngredient::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, FoundryIngredient> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, FoundryIngredient::ingredient,
            ByteBufCodecs.VAR_INT, FoundryIngredient::count,
            FoundryIngredient::new
    );

    public boolean matches(ItemStack stack) {
        return ingredient.test(stack);
    }
}
