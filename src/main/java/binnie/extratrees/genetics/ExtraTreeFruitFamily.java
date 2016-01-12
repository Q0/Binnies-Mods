package binnie.extratrees.genetics;

import forestry.api.genetics.IFruitFamily;

public enum ExtraTreeFruitFamily implements IFruitFamily {
    Berry("Berries", "berry", "berri"),
    Citrus("Citrus", "citrus", "citrus");

    String name;
    String uid;
    String scientific;

    private ExtraTreeFruitFamily(final String name, final String uid, final String scientific) {
        this.name = name;
        this.uid = uid;
        this.scientific = scientific;
    }

    public String getUID() {
        return "binnie.family." + this.uid;
    }

    public String getName() {
        return this.name;
    }

    public String getScientific() {
        return this.scientific;
    }

    public String getDescription() {
        return this.name;
    }
}
