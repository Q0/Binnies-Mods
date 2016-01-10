package binnie.botany.api;

public enum EnumMoisture {
    Dry,
    Normal,
    Damp;

    private EnumMoisture() {
    }

    public String getID() {
        return this.name().toLowerCase();
    }
}
