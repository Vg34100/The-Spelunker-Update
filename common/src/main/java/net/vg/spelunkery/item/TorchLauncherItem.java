package net.vg.spelunkery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class TorchLauncherItem extends Item {
    private static final double RANGE = 24.0D;

    public TorchLauncherItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack launcher = player.getItemInHand(hand);
        if (!player.hasInfiniteMaterials() && !player.getInventory().contains(new ItemStack(Items.TORCH))) {
            if (!level.isClientSide) {
                player.displayClientMessage(Component.literal("You need torches to load the launcher.").withStyle(ChatFormatting.GRAY), true);
            }
            return InteractionResultHolder.fail(launcher);
        }

        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
        if (hitResult.getType() != net.minecraft.world.phys.HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(launcher);
        }

        BlockPos anchorPos = hitResult.getBlockPos();
        Direction hitDirection = hitResult.getDirection();
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            spawnTrail(serverLevel, player.getEyePosition(), hitResult.getLocation());
        }
        level.playSound(null, player.blockPosition(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 0.8F, 1.15F);

        if (tryPlaceTorch(level, player, launcher, anchorPos.relative(hitDirection), hitDirection)
                || tryPlaceTorch(level, player, launcher, anchorPos.above(), Direction.UP)) {
            return InteractionResultHolder.sidedSuccess(launcher, level.isClientSide);
        }

        if (!level.isClientSide) {
            player.displayClientMessage(Component.literal("No valid torch spot in range.").withStyle(ChatFormatting.GRAY), true);
        }
        return InteractionResultHolder.fail(launcher);
    }

    private void spawnTrail(ServerLevel level, Vec3 start, Vec3 end) {
        Vec3 delta = end.subtract(start);
        int steps = Math.max(8, (int) (delta.length() * 2.0D));
        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            Vec3 point = start.add(delta.scale(t));
            level.sendParticles(ParticleTypes.FLAME, point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            if (i % 2 == 0) {
                level.sendParticles(ParticleTypes.SMOKE, point.x, point.y, point.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private boolean tryPlaceTorch(Level level, Player player, ItemStack launcher, BlockPos placePos, Direction supportDirection) {
        if (!level.getBlockState(placePos).canBeReplaced()) {
            return false;
        }

        BlockState placedState = null;
        if (supportDirection == Direction.UP && Blocks.TORCH.defaultBlockState().canSurvive(level, placePos)) {
            placedState = Blocks.TORCH.defaultBlockState();
        } else if (supportDirection.getAxis().isHorizontal()) {
            BlockState wallTorch = Blocks.WALL_TORCH.defaultBlockState().setValue(WallTorchBlock.FACING, supportDirection);
            if (wallTorch.canSurvive(level, placePos)) {
                placedState = wallTorch;
            }
        }

        if (placedState == null) {
            return false;
        }

        if (!level.isClientSide) {
            level.setBlock(placePos, placedState, 3);
            level.playSound(null, placePos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.1F);
            if (!player.hasInfiniteMaterials()) {
                consumeTorch(player);
                launcher.hurtAndBreak(1, player, player.getUsedItemHand() == InteractionHand.MAIN_HAND ? net.minecraft.world.entity.EquipmentSlot.MAINHAND : net.minecraft.world.entity.EquipmentSlot.OFFHAND);
            }
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.swing(player.getUsedItemHand(), true);
            }
        }

        return true;
    }

    private void consumeTorch(Player player) {
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack stack = player.getInventory().getItem(slot);
            if (stack.is(Items.TORCH)) {
                stack.shrink(1);
                return;
            }
        }
    }
}
