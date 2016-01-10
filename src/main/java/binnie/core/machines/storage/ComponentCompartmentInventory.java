package binnie.core.machines.storage;

import binnie.core.machines.IMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.network.INetwork;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;
import java.util.Map;

class ComponentCompartmentInventory extends ComponentInventorySlots implements INetwork.GuiNBT {
    private int numberOfTabs;
    private int slotsPerPage;
    private Map tabs;

    public ComponentCompartmentInventory(IMachine machine, int sections) {
        this(machine, sections, 4);
    }

    public ComponentCompartmentInventory(IMachine machine, int tabs, int pageSize) {
        super(machine);
        this.tabs = new HashMap();
        this.numberOfTabs = tabs;
        this.slotsPerPage = pageSize;

        for (int i = 0; i < this.numberOfTabs * this.slotsPerPage; ++i) {
            this.addSlot(i, "compartment");
        }

    }

    public int getPageSize() {
        return this.slotsPerPage;
    }

    public int getTabNumber() {
        return this.numberOfTabs;
    }

    public int[] getSlotsForTab(int currentTab) {
        int[] slots = new int[this.slotsPerPage];

        for (int i = 0; i < this.slotsPerPage; ++i) {
            slots[i] = i + currentTab * this.slotsPerPage;
        }

        return slots;
    }

    public CompartmentTab getTab(int i) {
        if (!this.tabs.containsKey(Integer.valueOf(i))) {
            this.tabs.put(Integer.valueOf(i), new CompartmentTab(i));
        }

        return (CompartmentTab) this.tabs.get(Integer.valueOf(i));
    }

    public void sendGuiNBT(Map nbt) {
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < this.numberOfTabs; ++i) {
            NBTTagCompound nbt2 = new NBTTagCompound();
            this.getTab(i).writeToNBT(nbt2);
            list.appendTag(nbt2);
        }

        NBTTagCompound tag = new NBTTagCompound();
        tag.setTag("tabs", list);
        nbt.put("comp-tabs", tag);
    }

    public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
        if (name.equals("comp-tabs")) {
            NBTTagList tags = nbt.getTagList("tabs", 10);

            for (int i = 0; i < tags.tagCount(); ++i) {
                NBTTagCompound tag = tags.getCompoundTagAt(i);
                CompartmentTab tab = new CompartmentTab(0);
                tab.readFromNBT(tag);
                this.tabs.put(Integer.valueOf(tab.getId()), tab);
            }
        }

        if (name.equals("comp-change-tab")) {
            CompartmentTab tab = new CompartmentTab(0);
            tab.readFromNBT(nbt);
            this.tabs.put(Integer.valueOf(tab.getId()), tab);
            this.getMachine().getTileEntity().markDirty();
        }

    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagList tags = nbt.getTagList("tabs", 10);

        for (int i = 0; i < tags.tagCount(); ++i) {
            NBTTagCompound tag = tags.getCompoundTagAt(i);
            CompartmentTab tab = new CompartmentTab(0);
            tab.readFromNBT(tag);
            this.tabs.put(Integer.valueOf(tab.getId()), tab);
        }

    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList list = new NBTTagList();

        for (int i = 0; i < this.numberOfTabs; ++i) {
            NBTTagCompound nbt2 = new NBTTagCompound();
            this.getTab(i).writeToNBT(nbt2);
            list.appendTag(nbt2);
        }

        nbt.setTag("tabs", list);
    }
}
