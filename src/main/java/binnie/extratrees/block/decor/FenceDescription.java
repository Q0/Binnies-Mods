package binnie.extratrees.block.decor;

import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.block.decor.FenceType;

public class FenceDescription {
   FenceType fenceType;
   IPlankType plankType;
   IPlankType secondaryPlankType;

   public FenceDescription(FenceType fenceType, IPlankType plankType, IPlankType secondaryPlankType) {
      super();
      this.fenceType = fenceType;
      this.plankType = plankType;
      this.secondaryPlankType = secondaryPlankType;
   }

   public FenceDescription(int meta) {
      super();
      this.fenceType = new FenceType(meta >> 8 & 255);
      this.plankType = WoodManager.getPlankType(meta & 255);
      this.secondaryPlankType = WoodManager.getPlankType(meta >> 16 & 255);
   }

   public FenceType getFenceType() {
      return this.fenceType;
   }

   public IPlankType getPlankType() {
      return this.plankType;
   }

   public IPlankType getSecondaryPlankType() {
      return this.secondaryPlankType;
   }
}
