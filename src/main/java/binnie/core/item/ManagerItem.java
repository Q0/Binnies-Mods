package binnie.core.item;

import net.minecraft.creativetab.CreativeTabs;

public class ManagerItem {
    public ManagerItem() {
        super();
    }

    public ItemMisc registerMiscItems(IItemMisc[] items, CreativeTabs tab) {
        return new ItemMisc(tab, items);
    }
}
