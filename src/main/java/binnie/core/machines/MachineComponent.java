package binnie.core.machines;

import binnie.Binnie;
import binnie.core.network.packet.MachinePayload;
import forestry.api.core.INBTTagable;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;

public class MachineComponent implements INBTTagable {
    private IMachine machine;

    public MachineComponent(final IMachine machine) {
        this.setMachine(machine);
        machine.addComponent(this);
    }

    public void setMachine(final IMachine machine) {
        this.machine = machine;
    }

    public IMachine getMachine() {
        return this.machine;
    }

    public void readFromNBT(final NBTTagCompound nbttagcompound) {
    }

    public void writeToNBT(final NBTTagCompound nbttagcompound) {
    }

    public void onUpdate() {
    }

    public Class[] getComponentInterfaces() {
        return Binnie.Machine.getComponentInterfaces(this.getClass());
    }

    public void onInventoryUpdate() {
    }

    public final MachinePayload getPayload() {
        return new MachinePayload(Binnie.Machine.getNetworkID(this.getClass()));
    }

    public void recieveData(final MachinePayload payload) {
    }

    public MachineUtil getUtil() {
        return this.getMachine().getMachineUtil();
    }

    public void onDestruction() {
    }

    public IInventory getInventory() {
        return this.getMachine().getInterface(IInventory.class);
    }
}
