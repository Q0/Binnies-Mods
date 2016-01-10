package binnie.genetics.machine;

import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import binnie.core.machines.network.INetwork;
import binnie.extrabees.ExtraBees;
import binnie.extratrees.ExtraTrees;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.Acclimatiser;
import binnie.genetics.machine.Analyser;
import binnie.genetics.machine.Genepool;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.Incubator;
import binnie.genetics.machine.MachineRendererLab;
import buildcraft.api.tools.IToolWrench;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public enum LaboratoryMachine implements IMachineType {
   LabMachine(LaboratoryMachine.PackageLabMachine.class),
   Analyser(Analyser.PackageAnalyser.class),
   Incubator(Incubator.PackageIncubator.class),
   Genepool(Genepool.PackageGenepool.class),
   Acclimatiser(Acclimatiser.PackageAcclimatiser.class);

   Class clss;

   private LaboratoryMachine(Class clss) {
      this.clss = clss;
   }

   public Class getPackageClass() {
      return this.clss;
   }

   public boolean isActive() {
      return true;
   }

   public ItemStack get(int i) {
      return new ItemStack(Genetics.packageLabMachine.getBlock(), i, this.ordinal());
   }

   public static class ComponentGUIHolder extends MachineComponent implements INetwork.TilePacketSync, IInteraction.RightClick {
      private ItemStack stack;

      public ComponentGUIHolder(IMachine machine) {
         super(machine);
      }

      public ItemStack getStack() {
         return this.stack;
      }

      public void readFromNBT(NBTTagCompound nbttagcompound) {
         super.readFromNBT(nbttagcompound);
         if(nbttagcompound != null) {
            this.stack = ItemStack.loadItemStackFromNBT(nbttagcompound.getCompoundTag("Item"));
         }
      }

      public void writeToNBT(NBTTagCompound nbttagcompound) {
         super.writeToNBT(nbttagcompound);
         NBTTagCompound nbt = new NBTTagCompound();
         if(this.stack != null) {
            this.stack.writeToNBT(nbt);
         }

         nbttagcompound.setTag("Item", nbt);
      }

      public void syncToNBT(NBTTagCompound nbt) {
         this.writeToNBT(nbt);
      }

      public void syncFromNBT(NBTTagCompound nbt) {
         this.readFromNBT(nbt);
      }

      public void onDestruction() {
         super.onDestruction();
         if(this.stack != null) {
            float f = this.getMachine().getWorld().rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.getMachine().getWorld().rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.getMachine().getWorld().rand.nextFloat() * 0.8F + 0.1F;
            if(this.stack.stackSize == 0) {
               this.stack.stackSize = 1;
            }

            EntityItem entityitem = new EntityItem(this.getMachine().getWorld(), (double)((float)this.getMachine().getTileEntity().xCoord + f), (double)((float)this.getMachine().getTileEntity().yCoord + f1), (double)((float)this.getMachine().getTileEntity().zCoord + f2), this.stack.copy());
            float accel = 0.05F;
            entityitem.motionX = (double)((float)this.getMachine().getWorld().rand.nextGaussian() * accel);
            entityitem.motionY = (double)((float)this.getMachine().getWorld().rand.nextGaussian() * accel + 0.2F);
            entityitem.motionZ = (double)((float)this.getMachine().getWorld().rand.nextGaussian() * accel);
            this.getMachine().getWorld().spawnEntityInWorld(entityitem);
         }

      }

      public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
         if(BinnieCore.proxy.isSimulating(world) && player.getHeldItem() != null) {
            if(this.stack != null && player.getHeldItem().getItem() instanceof IToolWrench) {
               float f = 0.7F;
               double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
               double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
               double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
               EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, this.stack);
               entityitem.delayBeforeCanPickup = 10;
               world.spawnEntityInWorld(entityitem);
               this.stack = null;
               ((IToolWrench)player.getHeldItem().getItem()).wrenchUsed(player, x, y, z);
               this.getUtil().refreshBlock();
               return;
            }

            List<Item> validSelections = new ArrayList();
            if(BinnieCore.isBotanyActive()) {
               validSelections.add(Botany.database);
            }

            if(BinnieCore.isExtraBeesActive()) {
               validSelections.add(ExtraBees.dictionary);
            }

            if(BinnieCore.isExtraTreesActive()) {
               validSelections.add(ExtraTrees.itemDictionary);
            }

            if(BinnieCore.isLepidopteryActive()) {
               validSelections.add(ExtraTrees.itemDictionaryLepi);
            }

            validSelections.add(Genetics.database);
            validSelections.add(Genetics.analyst);
            validSelections.add(Genetics.registry);
            validSelections.add(Genetics.masterRegistry);
            validSelections.add(BinnieCore.genesis);
            if(this.stack == null && validSelections.contains(player.getHeldItem().getItem())) {
               this.stack = player.getHeldItem().copy();
               --player.getHeldItem().stackSize;
               world.markBlockForUpdate(x, y, z);
               return;
            }

            if(this.stack != null && player.getHeldItem().getItem() instanceof IToolWrench) {
               this.stack.getItem().onItemRightClick(this.stack, world, player);
            }
         }

         if(this.stack != null) {
            this.stack.getItem().onItemRightClick(this.stack, world, player);
         }

      }
   }

   public static class PackageLabMachine extends GeneticMachine.PackageGeneticBase {
      public PackageLabMachine() {
         super("labMachine", GeneticsTexture.LabMachine, 16777215, false);
      }

      public void createMachine(Machine machine) {
         new LaboratoryMachine.ComponentGUIHolder(machine);
      }

      public void renderMachine(Machine machine, double x, double y, double z, float var8, RenderBlocks renderer) {
         MachineRendererLab.instance.renderMachine(machine, this.colour, this.renderTexture, x, y, z, var8);
      }
   }
}
