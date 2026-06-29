package net.vg.spelunkery.fabric.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.vg.spelunkery.fabric.ThickPotionBrewingRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {

    private static boolean isThickPotion(ItemStack stack) {
        if (!stack.is(Items.POTION)) return false;
        return stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                .potion().map(p -> p.is(Potions.THICK)).orElse(false);
    }

    @Inject(method = "isIngredient", at = @At("RETURN"), cancellable = true)
    private void spelunkery$isIngredient(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) return;
        for (var recipe : ThickPotionBrewingRegistry.IMMUTABLE) {
            if (recipe.ingredient().test(stack)) {
                cir.setReturnValue(true);
                return;
            }
        }
    }

    @Inject(method = "hasContainerMix", at = @At("RETURN"), cancellable = true)
    private void spelunkery$hasContainerMix(ItemStack container, ItemStack ingredient, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() || !isThickPotion(container)) return;
        for (var recipe : ThickPotionBrewingRegistry.IMMUTABLE) {
            if (recipe.ingredient().test(ingredient)) {
                cir.setReturnValue(true);
                return;
            }
        }
    }

    // mix(ingredient, container) — ingredient is top slot, container is bottom slot
    @Inject(method = "mix", at = @At("HEAD"), cancellable = true)
    private void spelunkery$mix(ItemStack ingredient, ItemStack container, CallbackInfoReturnable<ItemStack> cir) {
        if (!isThickPotion(container)) return;
        for (var recipe : ThickPotionBrewingRegistry.IMMUTABLE) {
            if (recipe.ingredient().test(ingredient)) {
                cir.setReturnValue(new ItemStack(recipe.output()));
                return;
            }
        }
    }
}
