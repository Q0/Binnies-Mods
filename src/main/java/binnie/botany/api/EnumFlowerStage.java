package binnie.botany.api;

import forestry.api.genetics.ISpeciesType;

public enum EnumFlowerStage implements ISpeciesType {
    FLOWER("Flower"),
    SEED("Seed"),
    POLLEN("Pollen"),
    NONE("NONE");

    public static final EnumFlowerStage[] VALUES;

    static {
        VALUES = values();
    }

    String name;

    EnumFlowerStage(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
