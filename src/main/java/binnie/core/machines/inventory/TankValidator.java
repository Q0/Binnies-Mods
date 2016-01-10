package binnie.core.machines.inventory;

import binnie.Binnie;
import binnie.core.machines.inventory.Validator;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public abstract class TankValidator extends Validator {
   public TankValidator() {
      super();
   }

   public abstract boolean isValid(FluidStack var1);

   public static class Basic extends TankValidator {
      private Fluid fluid;

      public Basic(String name) {
         super();
         this.fluid = Binnie.Liquid.getLiquidStack(name, 1).getFluid();
      }

      public boolean isValid(FluidStack stack) {
         return (new FluidStack(this.fluid, 1)).isFluidEqual(stack);
      }

      public String getTooltip() {
         return (new FluidStack(this.fluid, 1)).getLocalizedName();
      }
   }
}
