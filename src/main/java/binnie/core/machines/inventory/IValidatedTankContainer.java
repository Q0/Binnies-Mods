package binnie.core.machines.inventory;

import net.minecraftforge.fluids.FluidStack;

public interface IValidatedTankContainer {
    boolean isTankReadOnly(int var1);

    boolean isLiquidValidForTank(FluidStack var1, int var2);
}
