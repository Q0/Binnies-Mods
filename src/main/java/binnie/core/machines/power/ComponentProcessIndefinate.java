package binnie.core.machines.power;

import binnie.core.machines.IMachine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.network.INetwork;
import net.minecraft.nbt.NBTTagCompound;

public abstract class ComponentProcessIndefinate extends MachineComponent implements IProcess, INetwork.TilePacketSync {
    private float energyPerTick = 0.1F;
    private boolean inProgress;
    private float actionPauseProcess = 0.0F;
    private float actionCancelTask = 0.0F;
    int clientEnergyPerSecond = 0;
    int clientInProgress;

    public void syncFromNBT(NBTTagCompound nbt) {
        this.inProgress = nbt.getBoolean("progress");
    }

    public void syncToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("progress", this.inProgress);
    }

    public ComponentProcessIndefinate(IMachine machine, float energyPerTick) {
        super(machine);
        this.energyPerTick = energyPerTick;
    }

    protected final IPoweredMachine getPower() {
        return (IPoweredMachine) this.getMachine().getInterface(IPoweredMachine.class);
    }

    public float getEnergyPerTick() {
        return this.energyPerTick;
    }

    public void onUpdate() {
        float var10000 = (float) this.getPower().getInterface().useEnergy(PowerSystem.RF, (double) this.getEnergyPerTick(), false);
        if (this.canWork() == null) {
            if (!this.isInProgress() && this.canProgress() == null) {
                this.onStartTask();
            } else if (this.canProgress() == null) {
                this.progressTick();
                this.onTickTask();
            }
        } else if (this.isInProgress()) {
            this.onCancelTask();
        }

        if (this.actionPauseProcess > 0.0F) {
            --this.actionPauseProcess;
        }

        if (this.actionCancelTask > 0.0F) {
            --this.actionCancelTask;
        }

        super.onUpdate();
        if (this.inProgress != this.inProgress()) {
            this.inProgress = this.inProgress();
            this.getUtil().refreshBlock();
        }

    }

    protected void progressTick() {
        this.getPower().getInterface().useEnergy(PowerSystem.RF, (double) this.getEnergyPerTick(), true);
    }

    public ErrorState canWork() {
        return this.actionCancelTask == 0.0F ? null : new ErrorState("Task Cancelled", "Cancelled by Buildcraft Gate");
    }

    public ErrorState canProgress() {
        return (ErrorState) (this.actionPauseProcess != 0.0F ? new ErrorState("Process Paused", "Paused by Buildcraft Gate") : (this.getPower().getInterface().getEnergy(PowerSystem.RF) < (double) this.getEnergyPerTick() ? new ErrorState.InsufficientPower() : null));
    }

    public final boolean isInProgress() {
        return this.inProgress;
    }

    protected abstract boolean inProgress();

    protected void onCancelTask() {
    }

    protected void onStartTask() {
    }

    protected void onTickTask() {
    }

    public String getTooltip() {
        return "Processing";
    }

    public final ProcessInfo getInfo() {
        return new ProcessInfo(this);
    }
}
