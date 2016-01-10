package binnie.genetics.core;

import binnie.core.gui.IBinnieGUID;
import binnie.craftgui.genetics.machine.WindowAcclimatiser;
import binnie.craftgui.genetics.machine.WindowAnalyser;
import binnie.craftgui.genetics.machine.WindowGeneBank;
import binnie.craftgui.genetics.machine.WindowGeneBankNEI;
import binnie.craftgui.genetics.machine.WindowGenepool;
import binnie.craftgui.genetics.machine.WindowIncubator;
import binnie.craftgui.genetics.machine.WindowInoculator;
import binnie.craftgui.genetics.machine.WindowIsolator;
import binnie.craftgui.genetics.machine.WindowPolymeriser;
import binnie.craftgui.genetics.machine.WindowSequencer;
import binnie.craftgui.genetics.machine.WindowSplicer;
import binnie.craftgui.minecraft.Window;
import binnie.genetics.gui.WindowAnalyst;
import cpw.mods.fml.relauncher.Side;
import java.lang.reflect.Constructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public enum GeneticsGUI implements IBinnieGUID {
   Genepool(WindowGenepool.class),
   Isolator(WindowIsolator.class),
   Sequencer(WindowSequencer.class),
   Replicator(WindowPolymeriser.class),
   Inoculator(WindowInoculator.class),
   GeneBank(WindowGeneBank.class),
   Analyser(WindowAnalyser.class),
   Incubator(WindowIncubator.class),
   Database(WindowGeneBank.class),
   DatabaseNEI(WindowGeneBankNEI.class),
   Acclimatiser(WindowAcclimatiser.class),
   Splicer(WindowSplicer.class),
   Analyst,
   Registry,
   MasterRegistry;

   Class windowClass;

   private GeneticsGUI(Class window) {
      this.windowClass = window;
   }

   private GeneticsGUI() {
   }

   public Window getWindow(EntityPlayer player, IInventory object, Side side) throws Exception {
      switch(this) {
      case Analyst:
         return new WindowAnalyst(player, (IInventory)null, side, false, false);
      case MasterRegistry:
         return new WindowAnalyst(player, (IInventory)null, side, true, true);
      case Registry:
         return new WindowAnalyst(player, (IInventory)null, side, true, false);
      default:
         Constructor constr = this.windowClass.getConstructor(new Class[]{EntityPlayer.class, IInventory.class, Side.class});
         return (Window)constr.newInstance(new Object[]{player, object, side});
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
         if(this != Database && this != GeneBank) {
            if(this == DatabaseNEI) {
               window = WindowGeneBankNEI.create(player, object, side);
            } else {
               window = this.getWindow(player, object, side);
            }
         } else {
            window = WindowGeneBank.create(player, object, side);
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }

      return window;
   }
}
