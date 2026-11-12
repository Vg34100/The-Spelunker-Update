package net.vg.spelunkery.fabric.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.vg.spelunkery.item.BronzeShieldItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class ShieldBashMixin {
    @Inject(method = "blockUsingShield", at = @At("TAIL"))
    private void spelunkery$shieldBash(LivingEntity attacker, CallbackInfo ci) {
        BronzeShieldItem.tryBash((LivingEntity) (Object) this, attacker);
    }
}
