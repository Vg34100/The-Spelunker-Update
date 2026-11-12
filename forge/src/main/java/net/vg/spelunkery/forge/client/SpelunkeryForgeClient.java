package net.vg.spelunkery.forge.client;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.client.screen.FoundryScreen;
import net.vg.spelunkery.item.MinerHelmetGem;
import net.vg.spelunkery.item.MinerHelmetHelper;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.vg.spelunkery.registry.SpelunkeryItems;
import net.vg.spelunkery.registry.SpelunkeryMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;

import java.lang.reflect.Method;

@Mod.EventBusSubscriber(modid = Spelunkery.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SpelunkeryForgeClient {
    private SpelunkeryForgeClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.BRONZE_BARS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.BRONZE_CHAIN.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.BRONZE_LANTERN.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.ROPE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.BLUE_CRYSTAL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.GREEN_CRYSTAL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.RED_CRYSTAL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.YELLOW_CRYSTAL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.SCORCHED_DRIPSTONE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.GLOWCAP.get(), RenderType.cutout());
            MenuScreens.register(SpelunkeryMenuTypes.FOUNDRY.get(), FoundryScreen::new);
            registerBowPredicates();
            registerShieldPredicates();
            registerMinerHelmetPredicates();
        });
    }

    private static void registerBowPredicates() {
        registerPredicate(
                SpelunkeryItems.ELECTRUM_BOW.get(),
                new ResourceLocation("pull"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack
                        ? (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / 20.0F
                        : 0.0F
        );
        registerPredicate(
                SpelunkeryItems.ELECTRUM_BOW.get(),
                new ResourceLocation("pulling"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );
    }

    private static void registerShieldPredicates() {
        registerPredicate(
                SpelunkeryItems.BRONZE_SHIELD.get(),
                new ResourceLocation("blocking"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );
    }

    private static void registerMinerHelmetPredicates() {
        registerMinerHelmetPredicate("ruby", MinerHelmetGem.RUBY);
        registerMinerHelmetPredicate("sapphire", MinerHelmetGem.SAPPHIRE);
        registerMinerHelmetPredicate("topaz", MinerHelmetGem.TOPAZ);
        registerMinerHelmetPredicate("amethyst", MinerHelmetGem.AMETHYST);
        registerMinerHelmetPredicate("emerald", MinerHelmetGem.EMERALD);
        registerMinerHelmetPredicate("diamond", MinerHelmetGem.DIAMOND);
    }

    private static void registerMinerHelmetPredicate(String name, MinerHelmetGem gem) {
        registerPredicate(
                SpelunkeryItems.MINERS_HELMET.get(),
                new ResourceLocation("spelunkery", name),
                (stack, level, entity, seed) -> MinerHelmetHelper.gemPredicate(stack, gem)
        );
    }

    private static void registerPredicate(Item item, ResourceLocation id, ClampedItemPropertyFunction function) {
        ItemProperties.register(item, id, function);
    }
}
