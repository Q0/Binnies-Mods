package binnie.core.machines.inventory;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.inventory.BaseSlot;
import binnie.core.machines.inventory.SlotValidator;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class InventorySlot extends BaseSlot {
   private ItemStack itemStack = null;
   private InventorySlot.Type type = InventorySlot.Type.Standard;

   public InventorySlot(int index, String unlocName) {
      super(index, unlocName);
   }

   public ItemStack getContent() {
      return this.itemStack;
   }

   public ItemStack getItemStack() {
      return this.getContent();
   }

   public void setContent(ItemStack itemStack) {
      this.itemStack = itemStack;
   }

   public ItemStack decrStackSize(int amount) {
      if(this.itemStack == null) {
         return null;
      } else if(this.itemStack.stackSize <= amount) {
         ItemStack returnStack = this.itemStack.copy();
         this.itemStack = null;
         return returnStack;
      } else {
         ItemStack returnStack = this.itemStack.copy();
         this.itemStack.stackSize -= amount;
         returnStack.stackSize = amount;
         return returnStack;
      }
   }

   public void readFromNBT(NBTTagCompound slotNBT) {
      if(slotNBT.hasKey("item")) {
         NBTTagCompound itemNBT = slotNBT.getCompoundTag("item");
         this.itemStack = ItemStack.loadItemStackFromNBT(itemNBT);
      } else {
         this.itemStack = null;
      }

   }

   public void writeToNBT(NBTTagCompound slotNBT) {
      NBTTagCompound itemNBT = new NBTTagCompound();
      if(this.itemStack != null) {
         this.itemStack.writeToNBT(itemNBT);
      }

      slotNBT.setTag("item", itemNBT);
   }

   public void setItemStack(ItemStack duplicate) {
      this.setContent(duplicate);
   }

   public SlotValidator getValidator() {
      return (SlotValidator)this.validator;
   }

   public void setType(InventorySlot.Type type) {
      this.type = type;
      if(type == InventorySlot.Type.Recipe) {
         this.setReadOnly();
         this.forbidInteraction();
      }

   }

   public InventorySlot.Type getType() {
      return this.type;
   }

   public boolean isRecipe() {
      return this.type == InventorySlot.Type.Recipe;
   }

   public String getName() {
      return Binnie.Language.localise(BinnieCore.instance, "gui.slot." + this.unlocName);
   }

   public static enum Type {
      Standard,
      Recipe;

      private Type() {
      }
   }
}
