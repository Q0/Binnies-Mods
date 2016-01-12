package binnie.botany.api;

public enum EnumAcidity {
    Acid,
    Neutral,
    Alkaline;

    public String getID() {
        return this.name().toLowerCase();
    }
}
