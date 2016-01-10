package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.InventoryType;
import binnie.craftgui.minecraft.control.ControlSlot;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ControlPlayerInventory extends Control {
   private List slots;

   public ControlPlayerInventory(IWidget parent, boolean wide) {
      super(parent, (float)((int)(parent.getSize().x() / 2.0F) - (wide?110:81)), (float)((int)parent.getSize().y() - (wide?54:76) - 12), (float)(wide?220:162), (float)(wide?54:76));
      this.slots = new ArrayList();

      for(int row = 0; row < 3; ++row) {
         for(int column = 0; column < 9; ++column) {
            ControlSlot slot = new ControlSlot(this, (float)((wide?58:0) + column * 18), (float)(row * 18));
            this.slots.add(slot);
         }
      }

      if(wide) {
         for(int i1 = 0; i1 < 9; ++i1) {
            ControlSlot slot = new ControlSlot(this, (float)(i1 % 3 * 18), (float)(i1 / 3 * 18));
            this.slots.add(slot);
         }
      } else {
         for(int i1 = 0; i1 < 9; ++i1) {
            ControlSlot slot = new ControlSlot(this, (float)(i1 * 18), 58.0F);
            this.slots.add(slot);
         }
      }

      this.create();
   }

   public ControlPlayerInventory(IWidget parent) {
      this(parent, false);
   }

   public ControlPlayerInventory(IWidget parent, int x, int y) {
      super(parent, (float)x, (float)y, 54.0F, 220.0F);
      this.slots = new ArrayList();

      for(int row = 0; row < 6; ++row) {
         for(int column = 0; column < 6; ++column) {
            ControlSlot slot = new ControlSlot(this, (float)(column * 18), (float)(row * 18));
            this.slots.add(slot);
         }
      }

      this.create();
   }

   public void create() {
      for(int row = 0; row < 3; ++row) {
         for(int column = 0; column < 9; ++column) {
            ControlSlot slot = (ControlSlot)this.slots.get(column + row * 9);
            slot.assign(InventoryType.Player, 9 + column + row * 9);
         }
      }

      for(int i1 = 0; i1 < 9; ++i1) {
         ControlSlot slot = (ControlSlot)this.slots.get(27 + i1);
         slot.assign(InventoryType.Player, i1);
      }

   }

   public void addItem(ItemStack item) {
      if(item != null) {
         for(ControlSlot slot : this.slots) {
            if(!slot.slot.getHasStack()) {
               slot.slot.putStack(item);
               return;
            }
         }

      }
   }

   public void addInventory(IInventory inventory) {
      for(int i = 0; i < inventory.getSizeInventory(); ++i) {
         this.addItem(inventory.getStackInSlot(i));
      }

   }

   public ControlSlot getSlot(int i) {
      return i >= 0 && i < this.slots.size()?(ControlSlot)this.slots.get(i):null;
   }

   public void onUpdateClient() {
   }
}
