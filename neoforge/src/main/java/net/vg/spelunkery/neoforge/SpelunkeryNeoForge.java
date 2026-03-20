package net.vg.spelunkery.neoforge;

import net.vg.spelunkery.Spelunkery;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.fml.common.Mod;
import net.vg.spelunkery.item.ArmorUpgradeHelper;
import net.vg.spelunkery.item.BronzeShieldItem;

@Mod(Spelunkery.MOD_ID)
public final class SpelunkeryNeoForge {
    public SpelunkeryNeoForge() {
        Spelunkery.init();
        NeoForge.EVENT_BUS.addListener(this::onShieldBlock);
        NeoForge.EVENT_BUS.addListener(this::onPlayerTick);
    }

    private void onShieldBlock(LivingShieldBlockEvent event) {
        if (!event.getBlocked()) {
            return;
        }

        net.minecraft.world.entity.Entity sourceEntity = event.getDamageSource().getDirectEntity() != null
                ? event.getDamageSource().getDirectEntity()
                : event.getDamageSource().getEntity();
        if (sourceEntity instanceof net.minecraft.world.entity.LivingEntity attacker) {
            BronzeShieldItem.tryBash(event.getEntity(), attacker);
        }
    }

    private void onPlayerTick(PlayerTickEvent.Post event) {
        ArmorUpgradeHelper.updateEquippedArmorEffects(event.getEntity());
    }
}
