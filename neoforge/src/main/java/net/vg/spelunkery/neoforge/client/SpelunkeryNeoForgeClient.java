package net.vg.spelunkery.neoforge.client;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.client.SpelunkeryClient;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.vg.spelunkery.registry.SpelunkeryItems;

import java.lang.reflect.Method;

@EventBusSubscriber(modid = Spelunkery.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SpelunkeryNeoForgeClient {
    private SpelunkeryNeoForgeClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            SpelunkeryClient.init();
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.BRONZE_BARS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.BRONZE_CHAIN.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.BRONZE_LANTERN.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.ROPE.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.BLUE_CRYSTAL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.GREEN_CRYSTAL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.RED_CRYSTAL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(SpelunkeryBlocks.YELLOW_CRYSTAL.get(), RenderType.cutout());
            registerBowPredicates();
            registerShieldPredicates();
        });
    }

    private static void registerBowPredicates() {
        registerPredicate(
                SpelunkeryItems.ELECTRUM_BOW.get(),
                ResourceLocation.withDefaultNamespace("pull"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack
                        ? (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F
                        : 0.0F
        );
        registerPredicate(
                SpelunkeryItems.ELECTRUM_BOW.get(),
                ResourceLocation.withDefaultNamespace("pulling"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );
    }

    private static void registerShieldPredicates() {
        registerPredicate(
                SpelunkeryItems.BRONZE_SHIELD.get(),
                ResourceLocation.withDefaultNamespace("blocking"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );
    }

    private static void registerPredicate(Item item, ResourceLocation id, ClampedItemPropertyFunction function) {
        try {
            Method register = ItemProperties.class.getDeclaredMethod("register", Item.class, ResourceLocation.class, ClampedItemPropertyFunction.class);
            register.setAccessible(true);
            register.invoke(null, item, id, function);
        } catch (ReflectiveOperationException exception) {
            throw new RuntimeException("Failed to register item property " + id, exception);
        }
    }

}
