package binnie.core.liquid;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidType {
    IIcon getIcon();

    void registerIcon(final IIconRegister p0);

    String getName();

    String getIdentifier();

    FluidStack get(final int p0);

    int getColour();

    int getContainerColour();

    int getTransparency();

    boolean canPlaceIn(final FluidContainer p0);

    boolean showInCreative(final FluidContainer p0);
}
