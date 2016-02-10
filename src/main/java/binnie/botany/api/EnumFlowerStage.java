package binnie.botany.api;

public enum EnumFlowerStage {
    FLOWER("Flower"),
    SEED("Seed"),
    POLLEN("Pollen"),
    NONE("NONE");

    public static final EnumFlowerStage[] VALUES;

    static {
        VALUES = values();
    }

    String name;

    private EnumFlowerStage(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
