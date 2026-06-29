package net.vg.spelunkery.neoforge;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.brewing.IBrewingRecipe;
import net.vg.spelunkery.Spelunkery;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.world.entity.item.ItemEntity;
import net.vg.spelunkery.gameplay.SpelunkeryGameplayHelper;
import net.vg.spelunkery.item.ArmorUpgradeHelper;
import net.vg.spelunkery.item.BronzeShieldItem;
import net.vg.spelunkery.item.MinerHelmetHelper;
import net.vg.spelunkery.registry.SpelunkeryCreativeTabs;
import net.vg.spelunkery.registry.SpelunkeryItems;

@Mod(Spelunkery.MOD_ID)
public final class SpelunkeryNeoForge {
    public SpelunkeryNeoForge() {
        Spelunkery.init();
        NeoForge.EVENT_BUS.addListener(this::onShieldBlock);
        NeoForge.EVENT_BUS.addListener(this::onPlayerTick);
        NeoForge.EVENT_BUS.addListener(this::onBlockBreak);
        NeoForge.EVENT_BUS.addListener(this::onLivingDrops);
        NeoForge.EVENT_BUS.addListener(this::onRegisterBrewingRecipes);
    }

    private void onShieldBlock(LivingShieldBlockEvent event) {
        if (!event.getBlocked()) {
            return;
        }

        net.minecraft.world.entity.Entity sourceEntity = event.getDamageSource().getDirectEntity() != null
                ? event.getDamageSource().getDirectEntity()
                : event.getDamageSource().getEntity();
        if (sourceEntity instanceof net.minecraft.world.entity.LivingEntity attacker) {
            BronzeShieldItem.tryBash(event.getEntity(), attacker);
        }
    }

    private void onPlayerTick(PlayerTickEvent.Post event) {
        ArmorUpgradeHelper.updateEquippedArmorEffects(event.getEntity());
        MinerHelmetHelper.updateEquippedHelmet(event.getEntity());
        SpelunkeryGameplayHelper.updatePlayerEffects(event.getEntity());
    }

    private void onBlockBreak(BreakBlockEvent event) {
        if (event.getPlayer() instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
            MinerHelmetHelper.onBlockMined(serverPlayer, event.getPos(), event.getState());
        }
    }

    private void onRegisterBrewingRecipes(RegisterBrewingRecipesEvent event) {
        var builder = event.getBuilder();
        builder.addRecipe(tonicRecipe(Ingredient.of(SpelunkeryItems.TOPAZ_SHARD.get()), new ItemStack(SpelunkeryItems.SPELUNKERS_BREW.get())));
        builder.addRecipe(tonicRecipe(Ingredient.of(SpelunkeryItems.BAT_WING.get()), new ItemStack(SpelunkeryItems.DANGERSENSE_TONIC.get())));
        builder.addRecipe(tonicRecipe(Ingredient.of(Items.IRON_INGOT), new ItemStack(SpelunkeryItems.MINERS_TONIC.get())));
    }

    private static IBrewingRecipe tonicRecipe(Ingredient reagent, ItemStack output) {
        return new IBrewingRecipe() {
            @Override
            public boolean isInput(ItemStack input) {
                if (!input.is(Items.POTION)) return false;
                return input.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY)
                        .potion().map(p -> p.is(Potions.THICK)).orElse(false);
            }
            @Override public boolean isIngredient(ItemStack ingredient) { return reagent.test(ingredient); }
            @Override
            public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
                return isInput(input) && isIngredient(ingredient) ? output.copy() : ItemStack.EMPTY;
            }
        };
    }

    private void onLivingDrops(LivingDropsEvent event) {
        if (SpelunkeryGameplayHelper.shouldDropBatWing(event.getEntity())) {
            event.getDrops().add(new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), new ItemStack(SpelunkeryItems.BAT_WING.get())));
        }
    }

    @EventBusSubscriber(modid = Spelunkery.MOD_ID)
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
