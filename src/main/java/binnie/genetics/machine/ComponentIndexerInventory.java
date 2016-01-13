package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventory;
import binnie.core.machines.inventory.SetList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ComponentIndexerInventory<T> extends ComponentInventory implements IInventory {
    int indexerSize;
    public int guiRefreshCounter;
    List<ItemStack> indexerInventory;
    public List<Integer> sortedInventory;
    T sortingMode;
    boolean needsSorting;

    public ComponentIndexerInventory(final Machine machine) {
        super(machine);
        this.indexerSize = -1;
        this.guiRefreshCounter = 0;
        this.indexerInventory = new SetList<ItemStack>();
        this.sortedInventory = new SetList<Integer>();
        this.needsSorting = true;
    }

    public int getSizeInventory() {
        if (this.indexerSize > 0) {
            return this.indexerSize + 1;
        }
        return this.indexerInventory.size() + 1;
    }

    public ItemStack getStackInSlot(final int index) {
        if (index >= 0 && index < this.indexerInventory.size()) {
            return this.indexerInventory.get(index);
        }
        return null;
    }

    public ItemStack decrStackSize(final int index, final int amount) {
        if (index >= 0) {
            final ItemStack returnStack = this.getStackInSlot(index).copy();
            this.setInventorySlotContents(index, null);
            return returnStack;
        }
        return null;
    }

    public ItemStack getStackInSlotOnClosing(final int var1) {
        return null;
    }

    @Override
    public void markDirty() {
        super.markDirty();
        ++this.guiRefreshCounter;
    }

    public void setInventorySlotContents(final int index, final ItemStack itemStack) {
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

    public boolean isUseableByPlayer(final EntityPlayer var1) {
        return true;
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }

    public void setMode(final T mode) {
        this.sortingMode = mode;
        this.needsSorting = true;
    }

    public T getMode() {
        return this.sortingMode;
    }

    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        final NBTTagList indexerNBT = new NBTTagList();
        for (final ItemStack item : this.indexerInventory) {
            final NBTTagCompound itemNBT = new NBTTagCompound();
            item.writeToNBT(itemNBT);
            indexerNBT.appendTag((NBTBase) itemNBT);
        }
        nbttagcompound.setTag("indexer", (NBTBase) indexerNBT);
    }

    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        final NBTTagList indexerNBT = nbttagcompound.getTagList("indexer", 10);
        this.indexerInventory.clear();
        for (int i = 0; i < indexerNBT.tagCount(); ++i) {
            final NBTTagCompound itemNBT = indexerNBT.getCompoundTagAt(i);
            this.setInventorySlotContents(i, ItemStack.loadItemStackFromNBT(itemNBT));
        }
        this.needsSorting = true;
        this.markDirty();
    }

    public abstract void Sort();

    public static class ComponentApiaristIndexerInventory extends ComponentIndexerInventory<ComponentApiaristIndexerInventory.Mode> implements IInventory {
        public ComponentApiaristIndexerInventory(final Machine machine) {
            super(machine);
        }

        @Override
        public void Sort() {
            int i = 0;
            while (i < this.indexerInventory.size()) {
                if (this.indexerInventory.get(i) == null) {
                    this.indexerInventory.remove(i);
                } else {
                    ++i;
                }
            }
            if (!this.needsSorting) {
                return;
            }
            this.needsSorting = false;
            ++this.guiRefreshCounter;
            switch ((Mode) this.sortingMode) {
                case Species:
                case Type: {
                    class SpeciesList {
                        public List<Integer> drones;
                        public List<Integer> queens;
                        public List<Integer> princesses;
                        public List<ItemStack> bees;

                        SpeciesList() {
                            this.drones = new ArrayList<Integer>();
                            this.queens = new ArrayList<Integer>();
                            this.princesses = new ArrayList<Integer>();
                            this.bees = new ArrayList<ItemStack>();
                        }

                        public void add(final ItemStack stack) {
                            this.bees.add(stack);
                        }
                    }
                    final Map<Integer, SpeciesList> speciesList = new HashMap<Integer, SpeciesList>();
                    for (final ItemStack itemStack : this.indexerInventory) {
                        final int species = itemStack.getItemDamage();
                        if (!speciesList.containsKey(species)) {
                            speciesList.put(species, new SpeciesList());
                        }
                        speciesList.get(species).add(itemStack);
                    }
                    for (final SpeciesList sortableList : speciesList.values()) {
                        for (final ItemStack beeStack : sortableList.bees) {
                            if (Binnie.Genetics.getBeeRoot().isDrone(beeStack)) {
                                sortableList.drones.add(this.indexerInventory.indexOf(beeStack));
                            } else if (Binnie.Genetics.getBeeRoot().isMated(beeStack)) {
                                sortableList.queens.add(this.indexerInventory.indexOf(beeStack));
                            } else {
                                sortableList.princesses.add(this.indexerInventory.indexOf(beeStack));
                            }
                        }
                    }
                    this.sortedInventory = new SetList<Integer>();
                    switch ((Mode) this.sortingMode) {
                        case Species: {
                            for (int j = 0; j < 1024; ++j) {
                                if (speciesList.containsKey(j)) {
                                    this.sortedInventory.addAll(speciesList.get(j).queens);
                                    this.sortedInventory.addAll(speciesList.get(j).princesses);
                                    this.sortedInventory.addAll(speciesList.get(j).drones);
                                }
                            }
                            break;
                        }
                        case Type: {
                            for (int j = 0; j < 1024; ++j) {
                                if (speciesList.containsKey(j)) {
                                    this.sortedInventory.addAll(speciesList.get(j).queens);
                                }
                            }
                            for (int j = 0; j < 1024; ++j) {
                                if (speciesList.containsKey(j)) {
                                    this.sortedInventory.addAll(speciesList.get(j).princesses);
                                }
                            }
                            for (int j = 0; j < 1024; ++j) {
                                if (speciesList.containsKey(j)) {
                                    this.sortedInventory.addAll(speciesList.get(j).drones);
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
                default: {
                    this.sortedInventory.clear();
                    for (i = 0; i < this.indexerInventory.size(); ++i) {
                        this.sortedInventory.add(i);
                    }
                    break;
                }
            }
        }

        public boolean hasCustomInventoryName() {
            return false;
        }

        public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
            return true;
        }

        public enum Mode {
            None,
            Species,
            Type;
        }
    }
}
