package binnie.core.machines.power;

import binnie.core.machines.inventory.IValidatedTankContainer;
import binnie.core.machines.inventory.TankSlot;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

public interface ITankMachine extends IFluidHandler, IValidatedTankContainer {
    TankInfo[] getTankInfos();

    IFluidTank[] getTanks();

    TankSlot addTank(int var1, String var2, int var3);

    IFluidTank getTank(int var1);

    TankSlot getTankSlot(int var1);
}
