package binnie.core.machines.power;

import forestry.api.core.INBTTagable;
import net.minecraft.nbt.NBTTagCompound;

public class ProcessInfo implements INBTTagable {
    private float currentProgress;
    private int processEnergy;
    private int processTime;
    private float energyPerTick;

    public ProcessInfo(final IProcess process) {
        this.currentProgress = 0.0f;
        this.processEnergy = 0;
        this.processTime = 0;
        this.energyPerTick = 0.0f;
        this.energyPerTick = process.getEnergyPerTick();
        if (process instanceof IProcessTimed) {
            final IProcessTimed time = (IProcessTimed) process;
            this.currentProgress = time.getProgress();
            this.processEnergy = time.getProcessEnergy();
            this.processTime = time.getProcessLength();
        } else {
            this.currentProgress = (process.isInProgress() ? 100.0f : 0.0f);
        }
    }

    public ProcessInfo() {
        this.currentProgress = 0.0f;
        this.processEnergy = 0;
        this.processTime = 0;
        this.energyPerTick = 0.0f;
    }

    public float getCurrentProgress() {
        return this.currentProgress;
    }

    public int getProcessEnergy() {
        return this.processEnergy;
    }

    public int getProcessTime() {
        return this.processTime;
    }

    public float getEnergyPerTick() {
        return this.energyPerTick;
    }

    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        this.energyPerTick = nbttagcompound.getFloat("ept");
        this.processEnergy = nbttagcompound.getInteger("e");
        this.processTime = nbttagcompound.getInteger("t");
        this.currentProgress = nbttagcompound.getFloat("p");
    }

    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        nbttagcompound.setFloat("ept", this.energyPerTick);
        nbttagcompound.setFloat("p", this.currentProgress);
        nbttagcompound.setInteger("e", this.processEnergy);
        nbttagcompound.setInteger("t", this.processTime);
    }
}
