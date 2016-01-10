package binnie.craftgui.genetics.machine;

import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public abstract class WindowMachine extends Window {
   public WindowMachine(int width, int height, EntityPlayer player, IInventory inventory, Side side) {
      super((float)width, (float)height, player, inventory, side);
   }

   public abstract String getTitle();

   public void initialiseClient() {
      this.setTitle(this.getTitle());
   }
}
