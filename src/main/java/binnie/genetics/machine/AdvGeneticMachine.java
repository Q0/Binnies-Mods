package binnie.genetics.machine;

import binnie.core.machines.IMachineType;
import binnie.core.resource.IBinnieTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.Splicer;
import net.minecraft.item.ItemStack;

public enum AdvGeneticMachine implements IMachineType {
   Splicer(Splicer.PackageSplicer.class);

   Class clss;

   private AdvGeneticMachine(Class clss) {
      this.clss = clss;
   }

   public Class getPackageClass() {
      return this.clss;
   }

   public boolean isActive() {
      return true;
   }

   public ItemStack get(int i) {
      return new ItemStack(Genetics.packageAdvGenetic.getBlock(), i, this.ordinal());
   }

   public abstract static class PackageAdvGeneticBase extends GeneticMachine.PackageGeneticBase {
      protected PackageAdvGeneticBase(String uid, IBinnieTexture renderTexture, int flashColour, boolean powered) {
         super(uid, renderTexture, flashColour, powered);
      }
   }
}
