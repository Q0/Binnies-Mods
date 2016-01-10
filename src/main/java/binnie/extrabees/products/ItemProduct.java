package binnie.extrabees.products;

import binnie.core.item.IItemEnum;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemProduct extends Item {
   IItemEnum[] types;

   public ItemProduct(IItemEnum[] types) {
      super();
      this.setMaxStackSize(64);
      this.setMaxDamage(0);
      this.setHasSubtypes(true);
      this.types = types;
   }

   public IItemEnum get(ItemStack stack) {
      int i = stack.getItemDamage();
      return i >= 0 && i < this.types.length?this.types[i]:this.types[0];
   }

   public String getItemStackDisplayName(ItemStack itemstack) {
      return this.get(itemstack).getName(itemstack);
   }

   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
      for(IItemEnum type : this.types) {
         if(type.isActive()) {
            itemList.add(new ItemStack(this, 1, type.ordinal()));
         }
      }

   }
}
