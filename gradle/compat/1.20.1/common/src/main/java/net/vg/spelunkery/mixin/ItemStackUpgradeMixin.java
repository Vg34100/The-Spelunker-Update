package net.vg.spelunkery.mixin;

import net.minecraft.world.item.ItemStack;
import net.vg.spelunkery.item.ArmorUpgrade;
import net.vg.spelunkery.item.ArmorUpgradeHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Pre-component equivalents of MAX_DAMAGE and ENCHANTMENT_GLINT_OVERRIDE. */
@Mixin(ItemStack.class)
public abstract class ItemStackUpgradeMixin {
    @Inject(method = "getMaxDamage", at = @At("RETURN"), cancellable = true)
    private void spelunkery$nickelDurability(CallbackInfoReturnable<Integer> cir) {
        if (ArmorUpgradeHelper.hasUpgrade((ItemStack) (Object) this, ArmorUpgrade.NICKEL)) {
            cir.setReturnValue((int) Math.ceil(cir.getReturnValue() * 1.2D));
        }
    }

    @Inject(method = "hasFoil", at = @At("RETURN"), cancellable = true)
    private void spelunkery$roseGoldGlint(CallbackInfoReturnable<Boolean> cir) {
        if (ArmorUpgradeHelper.hasUpgrade((ItemStack) (Object) this, ArmorUpgrade.ROSE_GOLD)) cir.setReturnValue(true);
    }
}
