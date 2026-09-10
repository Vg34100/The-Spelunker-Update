package net.vg.spelunkery.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.vg.spelunkery.registry.SpelunkeryEffects;
import net.vg.spelunkery.gameplay.SpelunkeryGameplayHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {
    @Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
    private void spelunkery$boostMinerFocus(BlockState state, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        Holder<MobEffect> minersFocus = SpelunkeryGameplayHelper.holder(SpelunkeryEffects.MINERS_FOCUS.get());
        if (!player.hasEffect(minersFocus) || !isBoostedBlock(state)) {
            return;
        }

        cir.setReturnValue(cir.getReturnValueF() * 1.6F);
    }

    private static boolean isBoostedBlock(BlockState state) {
        String path = state.getBlockHolder().unwrapKey().map(key -> key.location().getPath()).orElse("");
        return path.endsWith("_ore")
                || state.is(Blocks.STONE)
                || state.is(Blocks.DEEPSLATE)
                || state.is(Blocks.COBBLED_DEEPSLATE)
                || state.is(Blocks.TUFF)
                || state.is(Blocks.CALCITE)
                || state.is(Blocks.GRANITE)
                || state.is(Blocks.DIORITE)
                || state.is(Blocks.ANDESITE)
                || state.is(Blocks.DRIPSTONE_BLOCK)
                || state.is(Blocks.NETHERRACK)
                || state.is(Blocks.BLACKSTONE)
                || state.is(SpelunkeryBlocks.MARBLE.get())
                || state.is(SpelunkeryBlocks.POLISHED_MARBLE.get())
                || state.is(SpelunkeryBlocks.MARBLE_BRICKS.get())
                || state.is(SpelunkeryBlocks.MARBLE_TILES.get());
    }
}
