package binnie.core.machines.base;

import binnie.core.machines.inventory.IInventoryMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

class DefaultInventory implements IInventoryMachine {
    public int getSizeInventory() {
        return 0;
    }

    public ItemStack getStackInSlot(final int i) {
        return null;
    }

    public ItemStack decrStackSize(final int i, final int j) {
        return null;
    }

    public ItemStack getStackInSlotOnClosing(final int i) {
        return null;
    }

    public void setInventorySlotContents(final int i, final ItemStack itemstack) {
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return false;
    }

    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return false;
    }

    public int[] getAccessibleSlotsFromSide(final int var1) {
        return new int[0];
    }

    public boolean canInsertItem(final int i, final ItemStack itemstack, final int j) {
        return false;
    }

    public boolean canExtractItem(final int i, final ItemStack itemstack, final int j) {
        return false;
    }

    public boolean isReadOnly(final int slot) {
        return false;
    }

    public String getInventoryName() {
        return "";
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public void markDirty() {
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }
}
