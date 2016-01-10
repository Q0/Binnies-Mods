package binnie.botany.api;

public enum EnumSoilType {
    SOIL,
    LOAM,
    FLOWERBED;

    private EnumSoilType() {
    }

    public String getID() {
        return this.name().toLowerCase();
    }
}
