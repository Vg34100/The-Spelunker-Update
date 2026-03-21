package net.vg.spelunkery.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.vg.spelunkery.registry.SpelunkeryItems;

public enum MinerHelmetGem {
    RUBY("ruby", "Ruby Socket", "Fire Resistance"),
    SAPPHIRE("sapphire", "Sapphire Socket", "Night Vision"),
    TOPAZ("topaz", "Topaz Socket", "Ore Pulse"),
    AMETHYST("amethyst", "Amethyst Socket", "Mob Pulse"),
    EMERALD("emerald", "Emerald Socket", "Extra mining XP"),
    DIAMOND("diamond", "Diamond Socket", "Resistance");

    private final String id;
    private final String title;
    private final String effectText;

    MinerHelmetGem(String id, String title, String effectText) {
        this.id = id;
        this.title = title;
        this.effectText = effectText;
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String effectText() {
        return effectText;
    }

    public static MinerHelmetGem byId(String id) {
        for (MinerHelmetGem gem : values()) {
            if (gem.id.equals(id)) {
                return gem;
            }
        }

        throw new IllegalArgumentException("Unknown miner helmet gem: " + id);
    }

    public static MinerHelmetGem fromIngredient(ItemStack stack) {
        if (stack.is(SpelunkeryItems.RUBY.get())) {
            return RUBY;
        }
        if (stack.is(SpelunkeryItems.SAPPHIRE.get())) {
            return SAPPHIRE;
        }
        if (stack.is(SpelunkeryItems.TOPAZ_SHARD.get())) {
            return TOPAZ;
        }
        if (stack.is(Items.AMETHYST_SHARD)) {
            return AMETHYST;
        }
        if (stack.is(Items.EMERALD)) {
            return EMERALD;
        }
        if (stack.is(Items.DIAMOND)) {
            return DIAMOND;
        }

        return null;
    }
}
