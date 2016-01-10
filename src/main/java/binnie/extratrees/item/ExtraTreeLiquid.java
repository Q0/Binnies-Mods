package binnie.extratrees.item;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum ExtraTreeLiquid implements ILiquidType {
    Sap("Sap", "sap", 12481858),
    Resin("Resin", "resin", 13199360),
    Latex("Latex", "latex", 14212041),
    Turpentine("Turpentine", "turpentine", 7951162);

    public String name = "";
    String ident;
    IIcon icon;
    int colour;

    private ExtraTreeLiquid(String name, String ident, int colour) {
        this.name = name;
        this.ident = ident;
        this.colour = colour;
    }

    public IIcon getIcon() {
        return this.icon;
    }

    public void registerIcon(IIconRegister register) {
        this.icon = ExtraTrees.proxy.getIcon(register, "liquids/" + this.getIdentifier());
    }

    public String getName() {
        return this.name;
    }

    public String getIdentifier() {
        return this.ident;
    }

    public int getColour() {
        return 16777215;
    }

    public FluidStack get(int i) {
        return Binnie.Liquid.getLiquidStack(this.ident, i);
    }

    public int getTransparency() {
        return 255;
    }

    public boolean canPlaceIn(FluidContainer container) {
        return true;
    }

    public boolean showInCreative(FluidContainer container) {
        return container == FluidContainer.Bucket || container == FluidContainer.Can || container == FluidContainer.Capsule;
    }

    public int getContainerColour() {
        return this.colour;
    }
}
