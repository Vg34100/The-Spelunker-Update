package net.vg.spelunkery.fabric;

import net.vg.spelunkery.Spelunkery;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.vg.spelunkery.gameplay.SpelunkeryGameplayHelper;
import net.vg.spelunkery.item.ArmorUpgradeHelper;
import net.vg.spelunkery.item.BronzeShieldItem;
import net.vg.spelunkery.item.MinerHelmetHelper;
import net.vg.spelunkery.registry.SpelunkeryCreativeTabs;
import net.vg.spelunkery.registry.SpelunkeryItems;
import net.vg.spelunkery.worldgen.SpelunkeryWorldgen;

public final class SpelunkeryFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Spelunkery.init();
        registerVanillaCreativeTabs();
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                ArmorUpgradeHelper.updateEquippedArmorEffects(player);
                MinerHelmetHelper.updateEquippedHelmet(player);
                SpelunkeryGameplayHelper.updatePlayerEffects(player);
            }
        });
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if (player instanceof ServerPlayer serverPlayer) {
                MinerHelmetHelper.onBlockMined(serverPlayer, pos, state);
            }
        });
        ServerLivingEntityEvents.AFTER_DAMAGE.register((entity, source, baseDamageTaken, damageTaken, blocked) -> {
            if (!blocked) {
                return;
            }

            net.minecraft.world.entity.Entity sourceEntity = source.getDirectEntity() != null ? source.getDirectEntity() : source.getEntity();
            if (sourceEntity instanceof net.minecraft.world.entity.LivingEntity attacker) {
                BronzeShieldItem.tryBash(entity, attacker);
            }
        });
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, source) -> {
            if (SpelunkeryGameplayHelper.shouldDropBatWing(entity)) {
                entity.level().addFreshEntity(new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(SpelunkeryItems.BAT_WING.get())));
            }
        });
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                SpelunkeryWorldgen.TIN_ORE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                SpelunkeryWorldgen.NICKEL_ORE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                SpelunkeryWorldgen.SILVER_ORE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                SpelunkeryWorldgen.MARBLE_PATCH_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                SpelunkeryWorldgen.TOPAZ_GEODE_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                SpelunkeryWorldgen.RUBY_POCKET_PLACED
        );
        BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS,
                SpelunkeryWorldgen.SAPPHIRE_POCKET_PLACED
        );
    }

    private static void registerVanillaCreativeTabs() {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register(output ->
                SpelunkeryCreativeTabs.addNaturalBlockEntries(output::accept));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output ->
                SpelunkeryCreativeTabs.addIngredientEntries(output::accept));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output ->
                SpelunkeryCreativeTabs.addToolsAndUtilityEntries(output::accept));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COMBAT).register(output ->
                SpelunkeryCreativeTabs.addCombatEntries(output::accept));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(output ->
                SpelunkeryCreativeTabs.addFoodAndDrinkEntries(output::accept));
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(output ->
                SpelunkeryCreativeTabs.addBuildingBlockEntries(output::accept));
    }
}
