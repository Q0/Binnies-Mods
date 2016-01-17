package binnie.extrabees.products;

import binnie.core.item.IItemEnum;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemProduct extends Item {
    IItemEnum[] types;

    public ItemProduct(final IItemEnum[] types) {
        setMaxStackSize(64);
        setMaxDamage(0);
        setHasSubtypes(true);
        this.types = types;
    }

    public IItemEnum get(final ItemStack stack) {
        final int i = stack.getItemDamage();

        if (i >= 0 && i < types.length) {
            return types[i];
        }

        return types[0];
    }

    public String getItemStackDisplayName(final ItemStack itemstack) {
        return get(itemstack).getName(itemstack);
    }

    public void getSubItems(final Item item, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (final IItemEnum type : types) {
            if (type.isActive()) {
                itemList.add(new ItemStack(this, 1, type.ordinal()));
            }
        }
    }
}
