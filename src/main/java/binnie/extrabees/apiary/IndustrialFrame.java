package binnie.extrabees.apiary;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public enum IndustrialFrame implements IBeeModifier {
    Empty("Empty", 5, 0),
    Light("Glowstone Lighting", 2, 4),
    Rain("Rain Shielding", 2, 4),
    Sunlight("Sunlight Simulator", 4, 8),
    Soul("Low Grade Mutagen", 5, 15),
    Uranium("High Grade Mutagen", 10, 50),
    Cage("Meshed Restrainer", 3, 12),
    Freedom("Territory Extension", 3, 16),
    Honey("Honey Amplifier", 4, 12),
    Jelly("Gelatin Amplifier", 8, 36),
    Leaf("Pollinator MK I", 3, 15),
    Pollen("Pollinator MK II", 7, 25),
    Clay("Lifespan Extensor", 2, 10),
    Emerald("Eon Simulator", 7, 20),
    NetherStar("Immortality Gate", 12, 50),
    Poison("Mortality Inhibitor", 8, 18);

    static {
        IndustrialFrame.Light.lighted = true;
        IndustrialFrame.Rain.rain = true;
        IndustrialFrame.Sunlight.lighted = true;
        IndustrialFrame.Sunlight.sunlight = true;
        IndustrialFrame.Soul.mutationMod = 1.3f;
        IndustrialFrame.Uranium.mutationMod = 2.0f;
        IndustrialFrame.Cage.territoryMod = 0.4f;
        IndustrialFrame.Freedom.territoryMod = 1.4f;
        IndustrialFrame.Honey.productionMod = 1.4f;
        IndustrialFrame.Jelly.productionMod = 1.8f;
        IndustrialFrame.Leaf.floweringMod = 1.4f;
        IndustrialFrame.Pollen.floweringMod = 2.0f;
        IndustrialFrame.Clay.lifespanMod = 1.4f;
        IndustrialFrame.Emerald.lifespanMod = 2.0f;
        IndustrialFrame.NetherStar.lifespanMod = 20.0f;
        IndustrialFrame.Poison.lifespanMod = 0.5f;
    }

    String name;
    float territoryMod;
    float mutationMod;
    float lifespanMod;
    float productionMod;
    float floweringMod;
    boolean lighted;
    boolean sunlight;
    boolean rain;
    int wearMod;
    int power;

    IndustrialFrame(final String name, final int wear, final int power) {
        territoryMod = 1.0f;
        mutationMod = 1.0f;
        lifespanMod = 1.0f;
        productionMod = 1.0f;
        floweringMod = 1.0f;
        wearMod = wear;
        this.name = name;
        this.power = power;
    }

    public static ItemStack getItemStack(final Item item, final IndustrialFrame frame) {
        final ItemStack stack = new ItemStack(item);
        final NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("frame", frame.ordinal());
        stack.setTagCompound(nbt);
        return stack;
    }

    public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
        return territoryMod;
    }

    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return mutationMod;
    }

    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return lifespanMod;
    }

    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        return productionMod;
    }

    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        return floweringMod;
    }

    public boolean isSealed() {
        return rain;
    }

    public boolean isSelfLighted() {
        return lighted;
    }

    public boolean isSunlightSimulated() {
        return sunlight;
    }

    public boolean isHellish() {
        return false;
    }

    public Object getName() {
        return name;
    }

    public int getWearModifier() {
        return wearMod;
    }

    public int getPowerUsage() {
        return power;
    }

    public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }
}
