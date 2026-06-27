package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.block.entity.FoundryBlockEntity;

public final class SpelunkeryBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Spelunkery.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<FoundryBlockEntity>> FOUNDRY = BLOCK_ENTITY_TYPES.register(
            "foundry",
            () -> new BlockEntityType<>(FoundryBlockEntity::new, java.util.Set.of(SpelunkeryBlocks.FOUNDRY.get()))
    );

    private static boolean initialized;

    private SpelunkeryBlockEntities() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        BLOCK_ENTITY_TYPES.register();
    }
}
