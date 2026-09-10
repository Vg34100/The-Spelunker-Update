package net.vg.spelunkery.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.vg.spelunkery.item.ArmorUpgrade;
import net.vg.spelunkery.item.ArmorUpgradeHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "canBeAffected", at = @At("HEAD"), cancellable = true)
    private void spelunkery$blockWitherForSilverArmor(MobEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (!effect.getEffect().is(MobEffects.WITHER) || !((Object) this instanceof Player player)) {
            return;
        }

        for (ItemStack armor : player.getArmorSlots()) {
            if (ArmorUpgradeHelper.hasUpgrade(armor, ArmorUpgrade.SILVER)) {
                cir.setReturnValue(false);
                return;
            }
        }
    }
}
