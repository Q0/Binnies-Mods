package binnie.extrabees.liquids;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.ILiquidType;
import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum ExtraBeeLiquid implements ILiquidType {
    ACID("acid", 11528985),
    POISON("poison", 15406315),
    GLACIAL("liquidnitrogen", 9881800);

    String ident;
    IIcon icon;
    int colour;

    ExtraBeeLiquid(final String ident, final int colour) {
        this.ident = ident;
        this.colour = colour;
    }

    @Override
    public IIcon getIcon() {
        return this.icon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcon(final IIconRegister register) {
        this.icon = ExtraBees.proxy.getIcon(register, "liquids/" + this.getIdentifier());
    }

    @Override
    public String getName() {
        return ExtraBees.proxy.localise(this.toString().toLowerCase());
    }

    @Override
    public String getIdentifier() {
        return this.ident;
    }

    @Override
    public int getColour() {
        return 0xffffff;
    }

    @Override
    public FluidStack get(final int amount) {
        return Binnie.Liquid.getLiquidStack(this.ident, amount);
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
        return container == FluidContainer.Bucket ||
            container == FluidContainer.Can ||
            container == FluidContainer.Capsule ||
            container == FluidContainer.Refractory;
    }

    @Override
    public int getContainerColour() {
        return this.colour;
    }
}
