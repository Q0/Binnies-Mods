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

    public String name;
    String ident;
    IIcon icon;
    int colour;

    ExtraTreeLiquid(final String name, final String ident, final int colour) {
        this.name = "";
        this.name = name;
        this.ident = ident;
        this.colour = colour;
    }

    @Override
    public IIcon getIcon() {
        return this.icon;
    }

    @Override
    public void registerIcon(final IIconRegister register) {
        this.icon = ExtraTrees.proxy.getIcon(register, "liquids/" + this.getIdentifier());
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getIdentifier() {
        return this.ident;
    }

    @Override
    public int getColour() {
        return 16777215;
    }

    @Override
    public FluidStack get(final int i) {
        return Binnie.Liquid.getLiquidStack(this.ident, i);
    }

    @Override
    public int getTransparency() {
        return 255;
    }

    @Override
    public boolean canPlaceIn(final FluidContainer container) {
        return true;
    }

    @Override
    public boolean showInCreative(final FluidContainer container) {
        return container == FluidContainer.Bucket || container == FluidContainer.Can || container == FluidContainer.Capsule;
    }

    @Override
    public int getContainerColour() {
        return this.colour;
    }
}
