package binnie.core.machines.inventory;

import binnie.core.machines.inventory.InventorySlot;

public interface IInventorySlots {
   InventorySlot addSlot(int var1, String var2);

   InventorySlot[] addSlotArray(int[] var1, String var2);

   InventorySlot getSlot(int var1);

   InventorySlot[] getSlots(int[] var1);

   InventorySlot[] getAllSlots();
}
