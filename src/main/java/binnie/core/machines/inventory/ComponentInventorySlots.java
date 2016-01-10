package binnie.core.machines.inventory;

import binnie.core.machines.IMachine;
import binnie.core.machines.inventory.ComponentInventory;
import binnie.core.machines.inventory.IInventoryMachine;
import binnie.core.machines.inventory.IInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

public class ComponentInventorySlots extends ComponentInventory implements IInventoryMachine, IInventorySlots {
   private Map inventory = new LinkedHashMap();

   public ComponentInventorySlots(IMachine machine) {
      super(machine);
   }

   public int getSizeInventory() {
      int size = 0;

      for(Integer index : this.inventory.keySet()) {
         size = Math.max(size, index.intValue() + 1);
      }

      return size;
   }

   public ItemStack getStackInSlot(int index) {
      return this.inventory.containsKey(Integer.valueOf(index))?((InventorySlot)this.inventory.get(Integer.valueOf(index))).getContent():null;
   }

   public ItemStack decrStackSize(int index, int amount) {
      if(this.inventory.containsKey(Integer.valueOf(index))) {
         ItemStack stack = ((InventorySlot)this.inventory.get(Integer.valueOf(index))).decrStackSize(amount);
         this.markDirty();
         return stack;
      } else {
         return null;
      }
   }

   public ItemStack getStackInSlotOnClosing(int var1) {
      return null;
   }

   public void setInventorySlotContents(int index, ItemStack itemStack) {
      if(this.inventory.containsKey(Integer.valueOf(index)) && (itemStack == null || ((InventorySlot)this.inventory.get(Integer.valueOf(index))).isValid(itemStack))) {
         ((InventorySlot)this.inventory.get(Integer.valueOf(index))).setContent(itemStack);
      }

      this.markDirty();
   }

   protected void transferItem(int indexFrom, int indexTo) {
      if(this.inventory.containsKey(Integer.valueOf(indexFrom)) && this.inventory.containsKey(Integer.valueOf(indexTo))) {
         ItemStack newStack = ((InventorySlot)this.inventory.get(Integer.valueOf(indexFrom))).getContent().copy();
         ((InventorySlot)this.inventory.get(Integer.valueOf(indexFrom))).setContent((ItemStack)null);
         ((InventorySlot)this.inventory.get(Integer.valueOf(indexTo))).setContent(newStack);
      }

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

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      super.readFromNBT(nbttagcompound);
      if(nbttagcompound.hasKey("inventory")) {
         NBTTagList inventoryNBT = nbttagcompound.getTagList("inventory", 10);

         for(int i = 0; i < inventoryNBT.tagCount(); ++i) {
            NBTTagCompound slotNBT = inventoryNBT.getCompoundTagAt(i);
            int index = slotNBT.getInteger("id");
            if(slotNBT.hasKey("Slot")) {
               index = slotNBT.getByte("Slot") & 255;
            }

            if(this.inventory.containsKey(Integer.valueOf(index))) {
               ((InventorySlot)this.inventory.get(Integer.valueOf(index))).readFromNBT(slotNBT);
            }
         }
      }

      this.markDirty();
   }

   public void writeToNBT(NBTTagCompound nbttagcompound) {
      super.writeToNBT(nbttagcompound);
      NBTTagList inventoryNBT = new NBTTagList();

      for(Entry<Integer, InventorySlot> entry : this.inventory.entrySet()) {
         NBTTagCompound slotNBT = new NBTTagCompound();
         slotNBT.setInteger("id", ((Integer)entry.getKey()).intValue());
         ((InventorySlot)entry.getValue()).writeToNBT(slotNBT);
         inventoryNBT.appendTag(slotNBT);
      }

      nbttagcompound.setTag("inventory", inventoryNBT);
   }

   public final InventorySlot addSlot(int index, String unlocName) {
      this.inventory.put(Integer.valueOf(index), new InventorySlot(index, unlocName));
      return this.getSlot(index);
   }

   public final InventorySlot[] addSlotArray(int[] indexes, String unlocName) {
      for(int k : indexes) {
         this.addSlot(k, unlocName);
      }

      return this.getSlots(indexes);
   }

   public InventorySlot getSlot(int index) {
      return this.inventory.containsKey(Integer.valueOf(index))?(InventorySlot)this.inventory.get(Integer.valueOf(index)):null;
   }

   public InventorySlot[] getAllSlots() {
      return (InventorySlot[])this.inventory.values().toArray(new InventorySlot[0]);
   }

   public InventorySlot[] getSlots(int[] indexes) {
      List<InventorySlot> list = new ArrayList();

      for(int i : indexes) {
         if(this.getSlot(i) != null) {
            list.add(this.getSlot(i));
         }
      }

      return (InventorySlot[])list.toArray(new InventorySlot[0]);
   }

   public boolean isReadOnly(int slot) {
      InventorySlot iSlot = this.getSlot(slot);
      return iSlot == null?true:iSlot.isReadOnly();
   }

   public boolean hasCustomInventoryName() {
      return true;
   }

   public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
      InventorySlot iSlot = this.getSlot(slot);
      return iSlot == null?false:iSlot.isValid(itemStack) && !this.isReadOnly(slot);
   }

   public void onDestruction() {
      for(InventorySlot slot : this.inventory.values()) {
         ItemStack stack = slot.getContent();
         if(!slot.isRecipe() && stack != null) {
            float f = this.getMachine().getWorld().rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.getMachine().getWorld().rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.getMachine().getWorld().rand.nextFloat() * 0.8F + 0.1F;
            if(stack.stackSize == 0) {
               stack.stackSize = 1;
            }

            EntityItem entityitem = new EntityItem(this.getMachine().getWorld(), (double)((float)this.getMachine().getTileEntity().xCoord + f), (double)((float)this.getMachine().getTileEntity().yCoord + f1), (double)((float)this.getMachine().getTileEntity().zCoord + f2), stack.copy());
            float accel = 0.05F;
            entityitem.motionX = (double)((float)this.getMachine().getWorld().rand.nextGaussian() * accel);
            entityitem.motionY = (double)((float)this.getMachine().getWorld().rand.nextGaussian() * accel + 0.2F);
            entityitem.motionZ = (double)((float)this.getMachine().getWorld().rand.nextGaussian() * accel);
            this.getMachine().getWorld().spawnEntityInWorld(entityitem);
         }
      }

   }

   public int[] getAccessibleSlotsFromSide(int var1) {
      List<Integer> slots = new ArrayList();

      for(InventorySlot slot : this.inventory.values()) {
         if(slot.canInsert() || slot.canExtract()) {
            slots.add(Integer.valueOf(slot.getIndex()));
         }
      }

      int[] ids = new int[slots.size()];

      for(int i = 0; i < ((List)slots).size(); ++i) {
         ids[i] = ((Integer)slots.get(i)).intValue();
      }

      return ids;
   }

   public boolean canInsertItem(int i, ItemStack itemstack, int j) {
      return this.isItemValidForSlot(i, itemstack) && this.getSlot(i).canInsert(ForgeDirection.getOrientation(j));
   }

   public boolean canExtractItem(int i, ItemStack itemstack, int j) {
      return this.getSlot(i).canExtract(ForgeDirection.getOrientation(j));
   }
}
