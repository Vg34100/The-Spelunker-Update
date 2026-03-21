package net.vg.spelunkery.neoforge;

import net.vg.spelunkery.Spelunkery;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.fml.common.Mod;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.vg.spelunkery.gameplay.SpelunkeryGameplayHelper;
import net.vg.spelunkery.item.ArmorUpgradeHelper;
import net.vg.spelunkery.item.BronzeShieldItem;
import net.vg.spelunkery.item.MinerHelmetHelper;
import net.vg.spelunkery.registry.SpelunkeryItems;

@Mod(Spelunkery.MOD_ID)
public final class SpelunkeryNeoForge {
    public SpelunkeryNeoForge() {
        Spelunkery.init();
        NeoForge.EVENT_BUS.addListener(this::onShieldBlock);
        NeoForge.EVENT_BUS.addListener(this::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(this::onBlockBreak);
        NeoForge.EVENT_BUS.addListener(this::onLivingDrops);
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
        MinerHelmetHelper.updateEquippedHelmet(event.getEntity());
        SpelunkeryGameplayHelper.updatePlayerEffects(event.getEntity());
    }

    private void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            MinerHelmetHelper.onBlockMined(serverPlayer, event.getPos(), event.getState());
        }
    }

    private void onLivingDrops(LivingDropsEvent event) {
        if (SpelunkeryGameplayHelper.shouldDropBatWing(event.getEntity())) {
            event.getDrops().add(new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), new ItemStack(SpelunkeryItems.BAT_WING.get())));
        }
    }
}
