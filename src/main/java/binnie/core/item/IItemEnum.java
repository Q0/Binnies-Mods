package binnie.core.item;

import net.minecraft.item.ItemStack;

public interface IItemEnum {
   boolean isActive();

   String getName(ItemStack var1);

   int ordinal();

   ItemStack get(int var1);
}
