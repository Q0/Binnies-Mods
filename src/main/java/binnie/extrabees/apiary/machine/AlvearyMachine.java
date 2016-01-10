package binnie.extrabees.apiary.machine;

import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.MachineRendererBlock;
import binnie.core.resource.BinnieResource;
import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import binnie.extrabees.apiary.machine.AlvearyFrame;
import binnie.extrabees.apiary.machine.AlvearyHatchery;
import binnie.extrabees.apiary.machine.AlvearyLighting;
import binnie.extrabees.apiary.machine.AlvearyMutator;
import binnie.extrabees.apiary.machine.AlvearyRainShield;
import binnie.extrabees.apiary.machine.AlvearyStimulator;
import binnie.extrabees.apiary.machine.AlvearyTransmission;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public enum AlvearyMachine implements IMachineType {
   Mutator(AlvearyMutator.PackageAlvearyMutator.class),
   Frame(AlvearyFrame.PackageAlvearyFrame.class),
   RainShield(AlvearyRainShield.PackageAlvearyRainShield.class),
   Lighting(AlvearyLighting.PackageAlvearyLighting.class),
   Stimulator(AlvearyStimulator.PackageAlvearyStimulator.class),
   Hatchery(AlvearyHatchery.PackageAlvearyHatchery.class),
   Transmission(AlvearyTransmission.PackageAlvearyTransmission.class);

   Class clss;

   private AlvearyMachine(Class clss) {
      this.clss = clss;
   }

   public Class getPackageClass() {
      return this.clss;
   }

   public ItemStack get(int size) {
      return new ItemStack(ModuleApiary.blockComponent, size, this.ordinal());
   }

   public boolean isActive() {
      return true;
   }

   public abstract static class AlvearyPackage extends MachinePackage {
      BinnieResource machineTexture;

      public AlvearyPackage(String id, BinnieResource machineTexture, boolean powered) {
         super(id, powered);
         this.machineTexture = machineTexture;
      }

      public void createMachine(Machine machine) {
      }

      public TileEntity createTileEntity() {
         return new TileExtraBeeAlveary(this);
      }

      public void register() {
      }

      public void renderMachine(Machine machine, double x, double y, double z, float var8, RenderBlocks renderer) {
         MachineRendererBlock.instance.renderMachine(this.machineTexture, x, y, z, var8);
      }
   }
}
