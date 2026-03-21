package net.vg.spelunkery.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.vg.spelunkery.client.SpelunkeryClient;
import net.vg.spelunkery.client.TopazPulseRenderer;
import net.vg.spelunkery.registry.SpelunkeryBlocks;
import net.vg.spelunkery.registry.SpelunkeryItems;

public final class SpelunkeryFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpelunkeryClient.init();
        BlockRenderLayerMap.INSTANCE.putBlocks(
                RenderType.cutout(),
                SpelunkeryBlocks.BRONZE_BARS.get(),
                SpelunkeryBlocks.BRONZE_CHAIN.get(),
                SpelunkeryBlocks.BRONZE_LANTERN.get(),
                SpelunkeryBlocks.ROPE.get()
        );
        FabricModelPredicateProviderRegistry.register(
                SpelunkeryItems.ELECTRUM_BOW.get(),
                ResourceLocation.withDefaultNamespace("pull"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack
                        ? (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F
                        : 0.0F
        );
        FabricModelPredicateProviderRegistry.register(
                SpelunkeryItems.ELECTRUM_BOW.get(),
                ResourceLocation.withDefaultNamespace("pulling"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );
        FabricModelPredicateProviderRegistry.register(
                SpelunkeryItems.BRONZE_SHIELD.get(),
                ResourceLocation.withDefaultNamespace("blocking"),
                (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );
        WorldRenderEvents.AFTER_ENTITIES.register(context -> TopazPulseRenderer.render(context.matrixStack(), context.consumers(), context.camera()));
    }
}
