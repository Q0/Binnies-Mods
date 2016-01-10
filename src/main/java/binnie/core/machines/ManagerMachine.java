package binnie.core.machines;

import binnie.core.BinnieCore;
import binnie.core.ManagerBase;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import forestry.api.core.INBTTagable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ManagerMachine extends ManagerBase {
    private Map componentInterfaceMap = new HashMap();
    private Map machineGroups = new HashMap();
    private Map networkIDToComponent = new HashMap();
    private Map componentToNetworkID = new HashMap();
    private int nextNetworkID = 0;
    private int machineRenderID;

    public ManagerMachine() {
        super();
    }

    public void registerMachineGroup(MachineGroup group) {
        this.machineGroups.put(group.getUID(), group);
    }

    public MachineGroup getGroup(String name) {
        return (MachineGroup) this.machineGroups.get(name);
    }

    public MachinePackage getPackage(String group, String name) {
        MachineGroup machineGroup = this.getGroup(group);
        return machineGroup == null ? null : machineGroup.getPackage(name);
    }

    private void registerComponentClass(Class component) {
        if (!this.componentInterfaceMap.containsKey(component)) {
            Set<Class<?>> interfaces = new HashSet();
            Class<?> currentClass = component;

            while (currentClass != null) {
                for (Class<?> clss : currentClass.getInterfaces()) {
                    interfaces.add(clss);
                }

                currentClass = currentClass.getSuperclass();
                if (currentClass == Object.class) {
                    currentClass = null;
                }
            }

            interfaces.remove(INBTTagable.class);
            this.componentInterfaceMap.put(component, interfaces.toArray(new Class[0]));
            int networkID = this.nextNetworkID++;
            this.networkIDToComponent.put(Integer.valueOf(networkID), component);
            this.componentToNetworkID.put(component, Integer.valueOf(networkID));
        }
    }

    public int getNetworkID(Class component) {
        return ((Integer) this.componentToNetworkID.get(component)).intValue();
    }

    public Class getComponentClass(int networkID) {
        return (Class) this.networkIDToComponent.get(Integer.valueOf(networkID));
    }

    public int getMachineRenderID() {
        return this.machineRenderID;
    }

    public void init() {
        this.machineRenderID = BinnieCore.proxy.getUniqueRenderID();
        SlotValidator.IconBee = new ValidatorIcon(BinnieCore.instance, "validator/bee.0", "validator/bee.1");
        SlotValidator.IconFrame = new ValidatorIcon(BinnieCore.instance, "validator/frame.0", "validator/frame.1");
        SlotValidator.IconCircuit = new ValidatorIcon(BinnieCore.instance, "validator/circuit.0", "validator/circuit.1");
        SlotValidator.IconBlock = new ValidatorIcon(BinnieCore.instance, "validator/block.0", "validator/block.1");
    }

    public void postInit() {
        BinnieCore.proxy.registerBlockRenderer(BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
        BinnieCore.proxy.registerTileEntity(TileEntityMachine.class, "binnie.tile.machine", BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
    }

    public Class[] getComponentInterfaces(Class clss) {
        if (!this.componentInterfaceMap.containsKey(clss)) {
            this.registerComponentClass(clss);
        }

        return (Class[]) this.componentInterfaceMap.get(clss);
    }
}
