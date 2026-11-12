package net.vg.spelunkery.forge;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.vg.spelunkery.Spelunkery;
import net.minecraftforge.common.MinecraftForge;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraft.world.entity.item.ItemEntity;
import net.vg.spelunkery.gameplay.SpelunkeryGameplayHelper;
import net.vg.spelunkery.item.ArmorUpgradeHelper;
import net.vg.spelunkery.item.BronzeShieldItem;
import net.vg.spelunkery.item.MinerHelmetHelper;
import net.vg.spelunkery.registry.SpelunkeryCreativeTabs;
import net.vg.spelunkery.registry.SpelunkeryItems;
import net.minecraftforge.common.brewing.IBrewingRecipe;

@Mod(Spelunkery.MOD_ID)
public final class SpelunkeryForge {
    public SpelunkeryForge() {
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Spelunkery.MOD_ID, bus);
        Spelunkery.init();
        bus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.addListener(this::onShieldBlock);
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
        MinecraftForge.EVENT_BUS.addListener(this::onBlockBreak);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingDrops);
    }

    private void onShieldBlock(ShieldBlockEvent event) {
        if (event.getBlockedDamage() <= 0) {
            return;
        }

        net.minecraft.world.entity.Entity sourceEntity = event.getDamageSource().getDirectEntity() != null
                ? event.getDamageSource().getDirectEntity()
                : event.getDamageSource().getEntity();
        if (sourceEntity instanceof net.minecraft.world.entity.LivingEntity attacker) {
            BronzeShieldItem.tryBash(event.getEntity(), attacker);
        }
    }

    private void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level().isClientSide) return;
        ArmorUpgradeHelper.updateEquippedArmorEffects(event.player);
        MinerHelmetHelper.updateEquippedHelmet(event.player);
        SpelunkeryGameplayHelper.updatePlayerEffects(event.player);
    }

    private void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            MinerHelmetHelper.onBlockMined(serverPlayer, event.getPos(), event.getState());
        }
    }

    private void onLivingDrops(LivingDropsEvent event) {
        if (SpelunkeryGameplayHelper.shouldDropBatWing(event.getEntity())) {
            event.getDrops().add(new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), new ItemStack(SpelunkeryItems.BAT_WING.get())));
        }
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        BrewingRecipeRegistry.addRecipe(tonicRecipe(Ingredient.of(SpelunkeryItems.TOPAZ_SHARD.get()), new ItemStack(SpelunkeryItems.SPELUNKERS_BREW.get())));
        BrewingRecipeRegistry.addRecipe(tonicRecipe(Ingredient.of(SpelunkeryItems.BAT_WING.get()), new ItemStack(SpelunkeryItems.DANGERSENSE_TONIC.get())));
        BrewingRecipeRegistry.addRecipe(tonicRecipe(Ingredient.of(Items.IRON_INGOT), new ItemStack(SpelunkeryItems.MINERS_TONIC.get())));
        });
    }

    private static IBrewingRecipe tonicRecipe(Ingredient reagent, ItemStack output) {
        return new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                if (!input.is(Items.POTION)) return false;
                return PotionUtils.getPotion(input) == Potions.THICK;
            }

            @Override
            public boolean isIngredient(ItemStack ingredient) {
                return reagent.test(ingredient);
            }

            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                return isInput(input) && isIngredient(ingredient) ? output.copy() : ItemStack.EMPTY;
            }
        };
    }

    @EventBusSubscriber(modid = Spelunkery.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static final class CreativeTabEvents {
        private CreativeTabEvents() {
        }

        @SubscribeEvent
        public static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
                SpelunkeryCreativeTabs.addNaturalBlockEntries(item -> accept(event, item));
            } else if (event.getTabKey().equals(CreativeModeTabs.INGREDIENTS)) {
                SpelunkeryCreativeTabs.addIngredientEntries(item -> accept(event, item));
            } else if (event.getTabKey().equals(CreativeModeTabs.TOOLS_AND_UTILITIES)) {
                SpelunkeryCreativeTabs.addToolsAndUtilityEntries(item -> accept(event, item));
            } else if (event.getTabKey().equals(CreativeModeTabs.COMBAT)) {
                SpelunkeryCreativeTabs.addCombatEntries(item -> accept(event, item));
            } else if (event.getTabKey().equals(CreativeModeTabs.FOOD_AND_DRINKS)) {
                SpelunkeryCreativeTabs.addFoodAndDrinkEntries(item -> accept(event, item));
            } else if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
                SpelunkeryCreativeTabs.addBuildingBlockEntries(item -> accept(event, item));
            }
        }

        private static void accept(BuildCreativeModeTabContentsEvent event, ItemLike item) {
            event.accept(new ItemStack(item), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
