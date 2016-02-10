package binnie.extrabees.products;

import binnie.core.item.IItemEnum;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemProduct extends Item {
    IItemEnum[] types;

    public ItemProduct(final IItemEnum[] types) {
        this.setMaxStackSize(64);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.types = types;
    }

    public IItemEnum get(final ItemStack stack) {
        final int i = stack.getItemDamage();
        if (i >= 0 && i < this.types.length) {
            return this.types[i];
        }
        return this.types[0];
    }

    public String getItemStackDisplayName(final ItemStack itemstack) {
        return this.get(itemstack).getName(itemstack);
    }

    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (final IItemEnum type : this.types) {
            if (type.isActive()) {
                itemList.add(new ItemStack((Item) this, 1, type.ordinal()));
            }
        }
    }
}
