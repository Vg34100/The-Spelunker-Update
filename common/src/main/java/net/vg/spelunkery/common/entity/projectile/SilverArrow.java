package net.vg.spelunkery.common.entity.projectile;

import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;

public class SilverArrow extends Arrow {
    public SilverArrow(net.minecraft.world.level.Level level, LivingEntity shooter, ItemStack ammo, ItemStack weapon) {
        super(level, shooter, ammo, weapon);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity target && target.getType().is(EntityTypeTags.UNDEAD)) {
            DamageSource source = this.damageSources().arrow(this, this.getOwner() == null ? this : this.getOwner());
            target.hurt(source, (float) this.getBaseDamage());
        }
    }
}
