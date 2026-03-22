package net.vg.spelunkery.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.block.state.BlockState;
import net.vg.spelunkery.gameplay.SpelunkeryGameplayHelper;
import net.vg.spelunkery.registry.SpelunkeryItems;

import java.util.List;

public final class MinerHelmetHelper {
    private static final String GEM_KEY = "spelunkery_miner_helmet_gem";
    private static final int PASSIVE_DURATION = 220;
    private static final int DURABILITY_DRAIN_INTERVAL = 120;

    private MinerHelmetHelper() {
    }

    public static boolean isMinerHelmet(ItemStack stack) {
        return stack.is(SpelunkeryItems.MINERS_HELMET.get());
    }

    public static MinerHelmetGem getGem(ItemStack stack) {
        if (!isMinerHelmet(stack)) {
            return null;
        }

        CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        if (customData.isEmpty()) {
            return null;
        }

        CompoundTag tag = customData.copyTag();
        return tag.contains(GEM_KEY) ? MinerHelmetGem.byId(tag.getString(GEM_KEY)) : null;
    }

    public static ItemStack socket(ItemStack original, MinerHelmetGem gem) {
        ItemStack result = original.copy();
        CustomData.update(DataComponents.CUSTOM_DATA, result, tag -> tag.putString(GEM_KEY, gem.id()));
        result.set(DataComponents.LORE, new ItemLore(List.of(
                Component.literal("Socketed: " + gem.title()).withStyle(ChatFormatting.GRAY),
                Component.literal(gem.effectText()).withStyle(ChatFormatting.AQUA)
        )));
        return result;
    }

    public static float modelPredicate(ItemStack stack) {
        MinerHelmetGem gem = getGem(stack);
        if (gem == null) {
            return 0.0F;
        }

        return switch (gem) {
            case RUBY -> 1.0F;
            case SAPPHIRE -> 2.0F;
            case TOPAZ -> 3.0F;
            case AMETHYST -> 4.0F;
            case EMERALD -> 5.0F;
            case DIAMOND -> 6.0F;
        };
    }

    public static void updateEquippedHelmet(Player player) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        MinerHelmetGem gem = getGem(helmet);
        if (gem == null) {
            return;
        }

        if (!player.level().isClientSide && !player.isCreative() && player.tickCount % DURABILITY_DRAIN_INTERVAL == 0) {
            helmet.hurtAndBreak(1, player, EquipmentSlot.HEAD);
        }

        switch (gem) {
            case RUBY -> apply(player, MobEffects.FIRE_RESISTANCE);
            case SAPPHIRE -> apply(player, MobEffects.NIGHT_VISION);
            case DIAMOND -> apply(player, MobEffects.DAMAGE_RESISTANCE);
            case TOPAZ -> {
            }
            case AMETHYST -> pulseMobs(player);
            case EMERALD -> {
            }
        }
    }

    public static void onBlockMined(ServerPlayer player, BlockPos pos, BlockState state) {
        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
        if (getGem(helmet) != MinerHelmetGem.EMERALD || !isValuableMinedBlock(state)) {
            return;
        }

        ExperienceOrb.award((ServerLevel) player.level(), net.minecraft.world.phys.Vec3.atCenterOf(pos), 1);
        SpelunkeryGameplayHelper.onBlockMined(player, pos, state);
    }

    private static void apply(Player player, net.minecraft.core.Holder<net.minecraft.world.effect.MobEffect> effect) {
        player.addEffect(new MobEffectInstance(effect, PASSIVE_DURATION, 0, true, false, true));
    }

    private static void pulseMobs(Player player) {
        if (player.tickCount % 90 != 0) {
            return;
        }

        for (LivingEntity entity : player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(12.0D), entity -> entity instanceof Monster && entity.isAlive())) {
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 18, 0, true, false, true));
        }
    }

    private static boolean isValuableMinedBlock(BlockState state) {
        String path = state.getBlockHolder().unwrapKey().map(key -> key.location().getPath()).orElse("");
        return path.endsWith("_ore")
                || path.equals("topaz_block")
                || path.equals("ruby_block")
                || path.equals("sapphire_block");
    }
}
