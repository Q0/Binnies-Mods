package binnie.core.util;

import binnie.core.util.ItemStackSet;
import net.minecraft.item.ItemStack;

public class UniqueItemStackSet extends ItemStackSet {
   public UniqueItemStackSet() {
      super();
   }

   public boolean add(ItemStack e) {
      return e != null && this.getExisting(e) == null?this.itemStacks.add(e.copy()):false;
   }

   public boolean remove(Object o) {
      if(this.contains(o)) {
         ItemStack r = (ItemStack)o;
         ItemStack existing = this.getExisting(r);
         this.itemStacks.remove(existing);
      }

      return false;
   }
}
