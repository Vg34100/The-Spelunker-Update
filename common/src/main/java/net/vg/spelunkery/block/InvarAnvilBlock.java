package net.vg.spelunkery.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class InvarAnvilBlock extends AnvilBlock {
    public static final MapCodec<InvarAnvilBlock> CODEC = simpleCodec(InvarAnvilBlock::new);

    public InvarAnvilBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<InvarAnvilBlock> codec() {
        return CODEC;
    }
}
