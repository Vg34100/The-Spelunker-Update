package net.vg.spelunkery.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.minecraft.util.RandomSource;

public final class GlowcapBlock extends BushBlock implements BonemealableBlock {
    public static final MapCodec<GlowcapBlock> CODEC = simpleCodec(GlowcapBlock::new);
    private static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D);
    private static final ResourceKey<ConfiguredFeature<?, ?>> GIANT_GLOWCAP_FEATURE = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(Spelunkery.MOD_ID, "fungal_giant_blue_mushroom")
    );

    public GlowcapBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(SpelunkeryBlocks.MYCELIUM_MUD.get())
                || state.is(SpelunkeryBlocks.BIOLUMINESCENT_MOSS.get())
                || state.is(net.minecraft.world.level.block.Blocks.ROOTED_DIRT)
                || state.is(net.minecraft.world.level.block.Blocks.MOSS_BLOCK)
                || state.is(net.minecraft.world.level.block.Blocks.MYCELIUM);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        return this.mayPlaceOn(level.getBlockState(belowPos), level, belowPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(net.minecraft.world.level.Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        RegistryAccess registryAccess = level.registryAccess();
        ConfiguredFeature<?, ?> feature = registryAccess.lookupOrThrow(Registries.CONFIGURED_FEATURE)
                .getOrThrow(GIANT_GLOWCAP_FEATURE)
                .value();

        level.removeBlock(pos, false);
        if (!feature.place(level, level.getChunkSource().getGenerator(), random, pos)) {
            level.setBlock(pos, state, 3);
        }
    }
}
