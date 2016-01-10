package binnie.core.item;

import binnie.core.item.IItemMisc;
import binnie.core.item.ItemMisc;
import net.minecraft.creativetab.CreativeTabs;

public class ManagerItem {
   public ManagerItem() {
      super();
   }

   public ItemMisc registerMiscItems(IItemMisc[] items, CreativeTabs tab) {
      return new ItemMisc(tab, items);
   }
}
