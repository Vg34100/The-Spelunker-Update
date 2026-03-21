package net.vg.spelunkery.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.vg.spelunkery.Spelunkery;
import net.vg.spelunkery.effect.SpelunkeryMobEffect;

public final class SpelunkeryEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Spelunkery.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistrySupplier<MobEffect> SPELUNKING = EFFECTS.register("spelunking", () -> new SpelunkeryMobEffect(MobEffectCategory.BENEFICIAL, 0xE7C45A));
    public static final RegistrySupplier<MobEffect> DANGER_SENSE = EFFECTS.register("danger_sense", () -> new SpelunkeryMobEffect(MobEffectCategory.BENEFICIAL, 0xA56AD8));
    public static final RegistrySupplier<MobEffect> MINERS_FOCUS = EFFECTS.register("miners_focus", () -> new SpelunkeryMobEffect(MobEffectCategory.BENEFICIAL, 0xD18D36));

    private static boolean initialized;

    private SpelunkeryEffects() {
    }

    public static void init() {
        if (initialized) {
            return;
        }

        initialized = true;
        EFFECTS.register();
    }
}
