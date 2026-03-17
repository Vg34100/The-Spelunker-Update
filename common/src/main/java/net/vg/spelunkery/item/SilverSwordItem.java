package net.vg.spelunkery.item;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;

public class SilverSwordItem extends SwordItem {
    public SilverSwordItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean hurt = super.hurtEnemy(stack, target, attacker);
        if (target.getType().is(EntityTypeTags.UNDEAD)) {
            DamageSource source = attacker instanceof Player player
                    ? attacker.damageSources().playerAttack(player)
                    : attacker.damageSources().mobAttack(attacker);
            float bonusDamage = (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
            target.hurt(source, bonusDamage);
        }
        return hurt;
    }
}
