package net.vg.spelunkery.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.vg.spelunkery.common.entity.projectile.SilverArrow;

public class SilverArrowItem extends ArrowItem {
    public SilverArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, ItemStack weapon) {
        return new SilverArrow(level, shooter, ammo.copyWithCount(1), weapon);
    }
}
