package net.vg.spelunkery.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.resources.ResourceLocation;
import net.vg.spelunkery.client.SpelunkeryClient;
import net.vg.spelunkery.registry.SpelunkeryItems;

public final class SpelunkeryFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SpelunkeryClient.init();
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
    }
}
