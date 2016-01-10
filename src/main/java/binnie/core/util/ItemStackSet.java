package binnie.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.item.ItemStack;

public class ItemStackSet implements Set {
   List itemStacks = new ArrayList();

   public ItemStackSet() {
      super();
   }

   public String toString() {
      return this.itemStacks.toString();
   }

   protected ItemStack getExisting(ItemStack stack) {
      for(ItemStack stack2 : this.itemStacks) {
         if(stack2.isItemEqual(stack)) {
            return stack2;
         }
      }

      return null;
   }

   public boolean add(ItemStack e) {
      if(e != null) {
         if(this.getExisting(e) == null) {
            return this.itemStacks.add(e.copy());
         }

         ItemStack var10000 = this.getExisting(e);
         var10000.stackSize += e.stackSize;
      }

      return false;
   }

   public boolean addAll(Collection c) {
      boolean addedAll = true;

      for(ItemStack stack : c) {
         addedAll = this.add(stack) && addedAll;
      }

      return addedAll;
   }

   public void clear() {
      this.itemStacks.clear();
   }

   public boolean contains(Object o) {
      return !(o instanceof ItemStack)?false:this.getExisting((ItemStack)o) != null;
   }

   public boolean containsAll(Collection c) {
      boolean addedAll = true;

      for(Object o : c) {
         addedAll = addedAll && this.contains(o);
      }

      return false;
   }

   public boolean isEmpty() {
      return this.itemStacks.isEmpty();
   }

   public Iterator iterator() {
      return this.itemStacks.iterator();
   }

   public boolean remove(Object o) {
      if(this.contains(o)) {
         ItemStack r = (ItemStack)o;
         ItemStack existing = this.getExisting(r);
         if(existing.stackSize > r.stackSize) {
            existing.stackSize -= r.stackSize;
         } else {
            this.itemStacks.remove(existing);
         }
      }

      return false;
   }

   public boolean removeAll(Collection c) {
      boolean addedAll = true;

      for(Object o : c) {
         boolean removed = this.remove(o);
         addedAll = removed && addedAll;
      }

      return false;
   }

   public boolean retainAll(Collection c) {
      return this.itemStacks.retainAll(c);
   }

   public int size() {
      return this.itemStacks.size();
   }

   public Object[] toArray() {
      return this.itemStacks.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.itemStacks.toArray(a);
   }
}
