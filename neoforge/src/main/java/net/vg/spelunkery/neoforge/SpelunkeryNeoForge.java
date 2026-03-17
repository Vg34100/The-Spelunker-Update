package net.vg.spelunkery.neoforge;

import net.vg.spelunkery.Spelunkery;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.fml.common.Mod;
import net.vg.spelunkery.item.BronzeShieldItem;

@Mod(Spelunkery.MOD_ID)
public final class SpelunkeryNeoForge {
    public SpelunkeryNeoForge() {
        Spelunkery.init();
        NeoForge.EVENT_BUS.addListener(this::onShieldBlock);
    }

    private void onShieldBlock(LivingShieldBlockEvent event) {
        if (!event.getBlocked()) {
            return;
        }

        if (event.getDamageSource().getDirectEntity() instanceof net.minecraft.world.entity.LivingEntity attacker) {
            BronzeShieldItem.tryBash(event.getEntity(), attacker);
        }
    }
}
