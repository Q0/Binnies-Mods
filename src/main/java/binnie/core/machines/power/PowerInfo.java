package binnie.core.machines.power;

import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.PowerSystem;
import forestry.api.core.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;

public class PowerInfo implements INBTTagable {
   private float currentEnergy = 0.0F;
   private float maxEnergy = 0.0F;

   public PowerInfo(IPoweredMachine machine, float currentInput) {
      super();
      this.currentEnergy = (float)machine.getInterface().getEnergy(PowerSystem.RF);
      this.maxEnergy = (float)machine.getInterface().getCapacity(PowerSystem.RF);
   }

   public PowerInfo() {
      super();
   }

   public int getStoredEnergy() {
      return (int)this.currentEnergy;
   }

   public int getMaxEnergy() {
      return (int)this.maxEnergy;
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      this.currentEnergy = (float)nbttagcompound.getInteger("current");
      this.maxEnergy = (float)nbttagcompound.getInteger("max");
   }

   public void writeToNBT(NBTTagCompound nbttagcompound) {
      nbttagcompound.setInteger("current", this.getStoredEnergy());
      nbttagcompound.setInteger("max", this.getMaxEnergy());
   }
}
