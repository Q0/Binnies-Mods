package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventory;
import binnie.core.machines.inventory.SetList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ComponentIndexerInventory extends ComponentInventory implements IInventory {
    int indexerSize = -1;
    public int guiRefreshCounter = 0;
    List indexerInventory = new SetList();
    public List sortedInventory = new SetList();
    Object sortingMode;
    boolean needsSorting = true;

    public ComponentIndexerInventory(Machine machine) {
        super(machine);
    }

    public int getSizeInventory() {
        return this.indexerSize > 0 ? this.indexerSize + 1 : this.indexerInventory.size() + 1;
    }

    public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < this.indexerInventory.size() ? (ItemStack) this.indexerInventory.get(index) : null;
    }

    public ItemStack decrStackSize(int index, int amount) {
        if (index >= 0) {
            ItemStack returnStack = this.getStackInSlot(index).copy();
            this.setInventorySlotContents(index, (ItemStack) null);
            return returnStack;
        } else {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int var1) {
        return null;
    }

    public void markDirty() {
        super.markDirty();
        ++this.guiRefreshCounter;
    }

    public void setInventorySlotContents(int index, ItemStack itemStack) {
        if (index >= 0 && index < this.indexerInventory.size()) {
            this.indexerInventory.set(index, itemStack);
        } else if (itemStack != null) {
            this.indexerInventory.add(itemStack);
        }

        this.needsSorting = true;
        this.markDirty();
    }

    public String getInventoryName() {
        return "";
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }

    public void setMode(Object mode) {
        this.sortingMode = mode;
        this.needsSorting = true;
    }

    public Object getMode() {
        return this.sortingMode;
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagList indexerNBT = new NBTTagList();

        for (ItemStack item : this.indexerInventory) {
            NBTTagCompound itemNBT = new NBTTagCompound();
            item.writeToNBT(itemNBT);
            indexerNBT.appendTag(itemNBT);
        }

        nbttagcompound.setTag("indexer", indexerNBT);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        NBTTagList indexerNBT = nbttagcompound.getTagList("indexer", 10);
        this.indexerInventory.clear();

        for (int i = 0; i < indexerNBT.tagCount(); ++i) {
            NBTTagCompound itemNBT = indexerNBT.getCompoundTagAt(i);
            this.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(itemNBT));
        }

        this.needsSorting = true;
        this.markDirty();
    }

    public abstract void Sort();

    public static class ComponentApiaristIndexerInventory extends ComponentIndexerInventory implements IInventory {
        public ComponentApiaristIndexerInventory(Machine machine) {
            super(machine);
        }

        public void Sort() {
            int i = 0;

            while (i < this.indexerInventory.size()) {
                if (this.indexerInventory.get(i) == null) {
                    this.indexerInventory.remove(i);
                } else {
                    ++i;
                }
            }

            if (this.needsSorting) {
                this.needsSorting = false;
                ++this.guiRefreshCounter;
                label56:
                switch ((ComponentIndexerInventory.ComponentApiaristIndexerInventory.Mode) this.sortingMode) {
                    case Species:
                    case Type:
                        Map<Integer, SpeciesList> speciesList = new HashMap();

                        class SpeciesList {
                            public List drones = new ArrayList();
                            public List queens = new ArrayList();
                            public List princesses = new ArrayList();
                            public List bees = new ArrayList();

                            SpeciesList() {
                                super();
                            }

                            public void add(ItemStack stack) {
                                this.bees.add(stack);
                            }
                        }

                        for (ItemStack itemStack : this.indexerInventory) {
                            int species = itemStack.getItemDamage();
                            if (!speciesList.containsKey(Integer.valueOf(species))) {
                                speciesList.put(Integer.valueOf(species), new SpeciesList());
                            }

                            ((SpeciesList) speciesList.get(Integer.valueOf(species))).add(itemStack);
                        }

                        for (SpeciesList sortableList : speciesList.values()) {
                            for (ItemStack beeStack : sortableList.bees) {
                                if (Binnie.Genetics.getBeeRoot().isDrone(beeStack)) {
                                    sortableList.drones.add(Integer.valueOf(this.indexerInventory.indexOf(beeStack)));
                                } else if (Binnie.Genetics.getBeeRoot().isMated(beeStack)) {
                                    sortableList.queens.add(Integer.valueOf(this.indexerInventory.indexOf(beeStack)));
                                } else {
                                    sortableList.princesses.add(Integer.valueOf(this.indexerInventory.indexOf(beeStack)));
                                }
                            }
                        }

                        this.sortedInventory = new SetList();
                        switch ((ComponentIndexerInventory.ComponentApiaristIndexerInventory.Mode) this.sortingMode) {
                            case Species:
                                for (int i = 0; i < 1024; ++i) {
                                    if (speciesList.containsKey(Integer.valueOf(i))) {
                                        this.sortedInventory.addAll(((SpeciesList) speciesList.get(Integer.valueOf(i))).queens);
                                        this.sortedInventory.addAll(((SpeciesList) speciesList.get(Integer.valueOf(i))).princesses);
                                        this.sortedInventory.addAll(((SpeciesList) speciesList.get(Integer.valueOf(i))).drones);
                                    }
                                }

                                return;
                            case Type:
                                for (int i = 0; i < 1024; ++i) {
                                    if (speciesList.containsKey(Integer.valueOf(i))) {
                                        this.sortedInventory.addAll(((SpeciesList) speciesList.get(Integer.valueOf(i))).queens);
                                    }
                                }

                                for (int i = 0; i < 1024; ++i) {
                                    if (speciesList.containsKey(Integer.valueOf(i))) {
                                        this.sortedInventory.addAll(((SpeciesList) speciesList.get(Integer.valueOf(i))).princesses);
                                    }
                                }

                                for (int i = 0; i < 1024; ++i) {
                                    if (speciesList.containsKey(Integer.valueOf(i))) {
                                        this.sortedInventory.addAll(((SpeciesList) speciesList.get(Integer.valueOf(i))).drones);
                                    }
                                }
                                break label56;
                            default:
                                return;
                        }
                    default:
                        this.sortedInventory.clear();

                        for (i = 0; i < this.indexerInventory.size(); ++i) {
                            this.sortedInventory.add(Integer.valueOf(i));
                        }
                }

            }
        }

        public boolean hasCustomInventoryName() {
            return false;
        }

        public boolean isItemValidForSlot(int i, ItemStack itemstack) {
            return true;
        }

        public static enum Mode {
            None,
            Species,
            Type;

            private Mode() {
            }
        }
    }
}
