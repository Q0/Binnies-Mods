package binnie.extratrees.item;

import binnie.core.item.IItemMisc;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public enum ExtraTreeItems implements IItemMisc {
    /**
     * @deprecated
     */
    @Deprecated
    CarpentryHammer("Fake Hammer", "carpentryHammer"),
    Sawdust("Sawdust", "sawdust"),
    Bark("Bark", "bark"),
    ProvenGear("Proven Gear", "provenGear"),
    WoodWax("Wood Polish", "woodWax"),
    Hops("Hops", "hops"),
    Yeast("Yeast", "yeast"),
    LagerYeast("Lager Yeast", "yeastLager"),
    GrainWheat("Wheat Grain", "grainWheat"),
    GrainBarley("Barley Grain", "grainBarley"),
    GrainRye("Rye Grain", "grainRye"),
    GrainCorn("Corn Grain", "grainCorn"),
    GrainRoasted("Roasted Grain", "grainRoasted"),
    GlassFitting("Glass Fittings", "glassFitting");

    String name;
    String iconPath;
    IIcon icon;

    private ExtraTreeItems(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public IIcon getIcon(ItemStack stack) {
        return this.icon;
    }

    public void registerIcons(IIconRegister register) {
        this.icon = ExtraTrees.proxy.getIcon(register, this.iconPath);
    }

    public void addInformation(List par3List) {
    }

    public String getName(ItemStack stack) {
        return this.name;
    }

    public boolean isActive() {
        return this != CarpentryHammer;
    }

    public ItemStack get(int i) {
        return new ItemStack(ExtraTrees.itemMisc, i, this.ordinal());
    }
}
