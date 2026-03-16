package net.vg.spelunkery.neoforge;

import net.vg.spelunkery.Spelunkery;
import net.neoforged.fml.common.Mod;

@Mod(Spelunkery.MOD_ID)
public final class SpelunkeryNeoForge {
    public SpelunkeryNeoForge() {
        // Run our common setup.
        Spelunkery.init();
    }
}
