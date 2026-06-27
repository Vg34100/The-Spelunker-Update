package net.vg.spelunkery.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.spelunkery.registry.SpelunkeryBlockTags;

import java.util.function.Consumer;

public class ProspectorLensItem extends Item {
    private static final int SCAN_RADIUS = 10;

    public ProspectorLensItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide()) {
            BlockPos origin = player.blockPosition();
            ScanResult result = findNearest(level, origin, SpelunkeryBlockTags.PROSPECTOR_TARGETS);
            if (result == null) {
                player.sendOverlayMessage(Component.literal("No ore readings nearby."));
            } else {
                player.sendOverlayMessage(
                        Component.literal(
                                "Reading: " + result.block.getName().getString()
                                        + " at "
                                        + result.distance
                                        + "m"
                                        + " (Y "
                                        + result.pos.getY()
                                        + ")"
                        )
                );
            }
            player.getCooldowns().addCooldown(player.getItemInHand(usedHand), 20);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, TooltipDisplay tooltipDisplay, Consumer<Component> consumer, TooltipFlag tooltipFlag) {
        consumer.accept(Component.literal("Scans nearby stone for ore signatures."));
        consumer.accept(Component.literal("Best used while exploring fresh cave walls."));
    }

    private static ScanResult findNearest(Level level, BlockPos origin, TagKey<Block> targetTag) {
        ScanResult closest = null;
        int bestDistance = Integer.MAX_VALUE;

        for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-SCAN_RADIUS, -SCAN_RADIUS, -SCAN_RADIUS), origin.offset(SCAN_RADIUS, SCAN_RADIUS, SCAN_RADIUS))) {
            BlockState state = level.getBlockState(pos);
            if (!state.is(targetTag)) {
                continue;
            }

            int distance = (int) Math.round(Math.sqrt(pos.distSqr(origin)));
            if (distance < bestDistance) {
                bestDistance = distance;
                closest = new ScanResult(pos.immutable(), state.getBlock(), distance);
            }
        }

        return closest;
    }

    private record ScanResult(BlockPos pos, Block block, int distance) {
    }
}
