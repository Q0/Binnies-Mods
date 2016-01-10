package binnie.genetics.api;

import binnie.core.machines.MachineUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface IIncubatorRecipe {
   boolean isInputLiquid(FluidStack var1);

   boolean isInputLiquidSufficient(FluidStack var1);

   boolean isItemStack(ItemStack var1);

   void doTask(MachineUtil var1);

   float getChance();

   boolean roomForOutput(MachineUtil var1);
}
