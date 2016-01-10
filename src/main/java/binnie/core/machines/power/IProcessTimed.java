package binnie.core.machines.power;

import binnie.core.machines.power.IErrorStateSource;
import binnie.core.machines.power.IProcess;

interface IProcessTimed extends IProcess, IErrorStateSource {
   int getProcessLength();

   int getProcessEnergy();

   float getProgress();

   float getProgressPerTick();
}
