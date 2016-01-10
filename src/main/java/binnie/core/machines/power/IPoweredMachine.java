package binnie.core.machines.power;

import binnie.core.machines.power.PowerInfo;
import binnie.core.machines.power.PowerInterface;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.Optional.Interface;
import ic2.api.energy.tile.IEnergySink;

@Interface(
   iface = "ic2.api.energy.tile.IEnergySink",
   modid = "IC2"
)
public interface IPoweredMachine extends IEnergySink, IEnergyHandler {
   PowerInfo getPowerInfo();

   PowerInterface getInterface();
}
