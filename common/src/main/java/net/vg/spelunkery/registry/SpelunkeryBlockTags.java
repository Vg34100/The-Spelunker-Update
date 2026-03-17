package net.vg.spelunkery.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.vg.spelunkery.Spelunkery;

public final class SpelunkeryBlockTags {
    public static final TagKey<Block> PROSPECTOR_TARGETS = TagKey.create(Registries.BLOCK, Spelunkery.id("prospector_targets"));
    public static final TagKey<Block> INVAR_ANVILS = TagKey.create(Registries.BLOCK, Spelunkery.id("invar_anvils"));

    private SpelunkeryBlockTags() {
    }
}
