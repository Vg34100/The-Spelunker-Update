package net.vg.spelunkery.item;

public enum ArmorUpgrade {
    NICKEL("nickel", 1, "Nickel Plating", "+20% durability"),
    SILVER("silver", 2, "Silver Lining", "Wither immunity"),
    ROSE_GOLD("rose_gold", 3, "Rose Gold Filigree", "Arcane filigree");

    private final String id;
    private final int modelData;
    private final String title;
    private final String effectText;

    ArmorUpgrade(String id, int modelData, String title, String effectText) {
        this.id = id;
        this.modelData = modelData;
        this.title = title;
        this.effectText = effectText;
    }

    public String id() {
        return id;
    }

    public int modelData() {
        return modelData;
    }

    public String title() {
        return title;
    }

    public String effectText() {
        return effectText;
    }

    public static ArmorUpgrade byId(String id) {
        for (ArmorUpgrade upgrade : values()) {
            if (upgrade.id.equals(id)) {
                return upgrade;
            }
        }
        throw new IllegalArgumentException("Unknown armor upgrade: " + id);
    }
}
