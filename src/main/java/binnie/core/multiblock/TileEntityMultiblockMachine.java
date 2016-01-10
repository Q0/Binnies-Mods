package binnie.core.multiblock;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import net.minecraft.tileentity.TileEntity;

class TileEntityMultiblockMachine extends TileEntity {
   private boolean inStructure;
   private int tileX;
   private int tileY;
   private int tileZ;

   TileEntityMultiblockMachine() {
      super();
   }

   boolean inStructure() {
      return this.inStructure;
   }

   public Machine getMachine() {
      return this.getMasterMachine();
   }

   private Machine getMasterMachine() {
      if(!this.inStructure) {
         return null;
      } else {
         TileEntity tile = this.worldObj.getTileEntity(this.xCoord + this.tileX, this.yCoord + this.tileY, this.zCoord + this.tileZ);
         return tile instanceof TileEntityMachine?((TileEntityMachine)tile).getMachine():null;
      }
   }
}
