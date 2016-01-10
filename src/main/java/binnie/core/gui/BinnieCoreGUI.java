package binnie.core.gui;

import binnie.core.gui.IBinnieGUID;
import binnie.core.machines.storage.WindowCompartment;
import binnie.craftgui.binniecore.WindowFieldKit;
import binnie.craftgui.binniecore.WindowGenesis;
import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public enum BinnieCoreGUI implements IBinnieGUID {
   Compartment,
   FieldKit,
   Genesis;

   private BinnieCoreGUI() {
   }

   public Window getWindow(EntityPlayer player, IInventory object, Side side) throws Exception {
      switch(this) {
      case Compartment:
         return new WindowCompartment(player, object, side);
      case FieldKit:
         return new WindowFieldKit(player, (IInventory)null, side);
      case Genesis:
         return new WindowGenesis(player, (IInventory)null, side);
      default:
         return null;
      }
   }

   public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
      Window window = null;
      TileEntity tileEntity = world.getTileEntity(x, y, z);
      IInventory object = null;
      if(tileEntity instanceof IInventory) {
         object = (IInventory)tileEntity;
      }

      try {
         window = this.getWindow(player, object, side);
      } catch (Exception var11) {
         var11.printStackTrace();
      }

      return window;
   }
}
