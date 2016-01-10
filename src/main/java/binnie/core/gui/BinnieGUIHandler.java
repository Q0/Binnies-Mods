package binnie.core.gui;

import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public final class BinnieGUIHandler implements IGuiHandler {
   private AbstractMod mod;

   public BinnieGUIHandler(AbstractMod mod) {
      super();
      this.mod = mod;
   }

   public final Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
      Window window = this.getWindow(id, player, world, x, y, z, Side.SERVER);
      if(window == null) {
         return null;
      } else {
         window.initialiseServer();
         return window.getContainer();
      }
   }

   public final Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
      if(BinnieCore.proxy.isSimulating(world)) {
         return this.getServerGuiElement(id, player, world, x, y, z);
      } else {
         Window window = this.getWindow(id, player, world, x, y, z, Side.CLIENT);
         return window == null?null:window.getGui();
      }
   }

   public Window getWindow(int id, EntityPlayer player, World world, int x, int y, int z, Side side) {
      for(IBinnieGUID guid : this.mod.getGUIDs()) {
         if(guid.ordinal() == id) {
            return guid.getWindow(player, world, x, y, z, side);
         }
      }

      return null;
   }
}
