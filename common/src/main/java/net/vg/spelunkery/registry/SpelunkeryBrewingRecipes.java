package net.vg.spelunkery.registry;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;

public final class SpelunkeryBrewingRecipes {
    private SpelunkeryBrewingRecipes() {}

    public static void register(PotionBrewing.Builder builder) {
        builder.addContainerRecipe(Items.GLASS_BOTTLE, SpelunkeryItems.TOPAZ_SHARD.get(), SpelunkeryItems.SPELUNKERS_BREW.get());
        builder.addContainerRecipe(Items.GLASS_BOTTLE, SpelunkeryItems.BAT_WING.get(), SpelunkeryItems.DANGERSENSE_TONIC.get());
        builder.addContainerRecipe(Items.GLASS_BOTTLE, Items.IRON_INGOT, SpelunkeryItems.MINERS_TONIC.get());
    }
}
