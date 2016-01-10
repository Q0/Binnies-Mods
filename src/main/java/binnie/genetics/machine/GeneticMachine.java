package binnie.genetics.machine;

import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.Inoculator;
import binnie.genetics.machine.Isolator;
import binnie.genetics.machine.MachineRendererGenetics;
import binnie.genetics.machine.Polymeriser;
import binnie.genetics.machine.Sequencer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public enum GeneticMachine implements IMachineType {
   Isolator(Isolator.PackageIsolator.class),
   Sequencer(Sequencer.PackageSequencer.class),
   Polymeriser(Polymeriser.PackagePolymeriser.class),
   Inoculator(Inoculator.PackageInoculator.class);

   Class clss;

   private GeneticMachine(Class clss) {
      this.clss = clss;
   }

   public Class getPackageClass() {
      return this.clss;
   }

   public boolean isActive() {
      return true;
   }

   public ItemStack get(int i) {
      return new ItemStack(Genetics.packageGenetic.getBlock(), i, this.ordinal());
   }

   public abstract static class PackageGeneticBase extends MachinePackage {
      BinnieResource renderTexture;
      int colour;

      protected PackageGeneticBase(String uid, IBinnieTexture renderTexture, int flashColour, boolean powered) {
         super(uid, powered);
         this.renderTexture = renderTexture.getTexture();
         this.colour = flashColour;
      }

      public TileEntity createTileEntity() {
         return new TileEntityMachine(this);
      }

      public void register() {
      }

      public void renderMachine(Machine machine, double x, double y, double z, float var8, RenderBlocks renderer) {
         MachineRendererGenetics.instance.renderMachine(machine, this.colour, this.renderTexture, x, y, z, var8);
      }
   }
}
