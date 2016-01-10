package binnie.extratrees.machines;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.machines.ExtraTreeMachine;
import binnie.extratrees.machines.TileEntityNursery;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;

public class Nursery {
   public static int slotCaterpillar = 0;

   public Nursery() {
      super();
   }

   public static class PackageNursery extends ExtraTreeMachine.PackageExtraTreeMachine {
      public PackageNursery() {
         super("nursery", ExtraTreeTexture.Nursery.getTexture(), false);
      }

      public void createMachine(Machine machine) {
         ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
         inventory.addSlot(Nursery.slotCaterpillar, "caterpillar");
      }

      public TileEntity createTileEntity() {
         return new TileEntityNursery(this);
      }

      public void renderMachine(Machine machine, double x, double y, double z, float var8, RenderBlocks renderer) {
      }
   }
}
