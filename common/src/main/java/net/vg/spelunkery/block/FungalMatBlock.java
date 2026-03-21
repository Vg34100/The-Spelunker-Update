package net.vg.spelunkery.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.CarpetBlock;

public final class FungalMatBlock extends CarpetBlock {
    public static final MapCodec<FungalMatBlock> CODEC = simpleCodec(FungalMatBlock::new);

    public FungalMatBlock(Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<? extends CarpetBlock> codec() {
        return CODEC;
    }
}
