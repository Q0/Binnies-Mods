package binnie.core.machines.power;

import binnie.core.machines.power.IErrorStateSource;
import binnie.core.machines.power.ProcessInfo;

public interface IProcess extends IErrorStateSource {
   float getEnergyPerTick();

   String getTooltip();

   boolean isInProgress();

   ProcessInfo getInfo();
}
