package binnie.extratrees.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IToolHammer {
   boolean isActive(ItemStack var1);

   void onHammerUsed(ItemStack var1, EntityPlayer var2);
}
