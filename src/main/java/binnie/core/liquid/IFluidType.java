package binnie.core.liquid;

import binnie.core.liquid.FluidContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidType {
   IIcon getIcon();

   void registerIcon(IIconRegister var1);

   String getName();

   String getIdentifier();

   FluidStack get(int var1);

   int getColour();

   int getContainerColour();

   int getTransparency();

   boolean canPlaceIn(FluidContainer var1);

   boolean showInCreative(FluidContainer var1);
}
