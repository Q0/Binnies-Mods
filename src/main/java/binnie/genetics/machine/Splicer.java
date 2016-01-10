package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.machine.AdvGeneticMachine;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.ModuleMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class Splicer {
   public static final int slotSerumVial = 0;
   public static final int[] slotSerumReserve = new int[]{1, 2};
   public static final int[] slotSerumExpended = new int[]{3, 4};
   public static final int[] slotReserve = new int[]{5, 6, 7, 8, 9};
   public static final int slotTarget = 9;
   public static final int[] slotFinished = new int[]{10, 11, 12, 13};

   public Splicer() {
      super();
   }

   public static void setGene(IGene gene, ItemStack target, int chromoN) {
      int chromosome = gene.getChromosome().ordinal();
      int chromosomeID = chromosome;
      if(chromosome >= EnumBeeChromosome.HUMIDITY.ordinal() && gene.getSpeciesRoot() instanceof IBeeRoot) {
         chromosomeID = chromosome - 1;
      }

      Class<? extends IAllele> cls = gene.getChromosome().getAlleleClass();
      if(cls.isInstance(gene.getAllele())) {
         NBTTagCompound beeNBT = target.getTagCompound();
         NBTTagCompound genomeNBT = beeNBT.getCompoundTag("Genome");
         NBTTagList chromosomes = genomeNBT.getTagList("Chromosomes", 10);
         NBTTagCompound chromosomeNBT = chromosomes.getCompoundTagAt(chromosomeID);
         chromosomeNBT.setString("UID" + chromoN, gene.getAllele().getUID());
         target.setTagCompound(beeNBT);
      }
   }

   public static class ComponentSplicerFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
      private final EntityItem dummyEntityItem = new EntityItem((World)null);

      public ComponentSplicerFX(IMachine machine) {
         super(machine);
      }

      @SideOnly(Side.CLIENT)
      public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
      }

      @SideOnly(Side.CLIENT)
      public void onDisplayTick(final World world, int x, int y, int z, Random rand) {
         int tick = (int)(world.getTotalWorldTime() % 3L);
         if(tick == 0 && this.getUtil().getProcess().isInProgress()) {
            BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, (double)x + 0.5D, (double)z + 0.5D, 0.0D, x4, x5, x6) {
               double axisX = 0.0D;
               double axisZ = 0.0D;
               double angle = 0.0D;

               {
                  this.motionX = 0.0D;
                  this.motionZ = 0.0D;
                  this.motionY = (this.rand.nextDouble() - 0.5D) * 0.02D;
                  this.particleMaxAge = 240;
                  this.axisX = this.posX;
                  this.axisZ = this.posZ;
                  this.particleGravity = 0.0F;
                  this.angle = (double)((int)(this.worldObj.getTotalWorldTime() % 4L)) * 0.5D * 3.1415D;
                  this.noClip = true;
                  this.setRBGColorF(0.3F + this.rand.nextFloat() * 0.5F, 0.3F + this.rand.nextFloat() * 0.5F, 0.0F);
               }

               public void onUpdate() {
                  super.onUpdate();
                  double speed = 0.04D;
                  this.angle -= speed;
                  double dist = 0.25D + 0.2D * Math.sin((double)((float)this.particleAge / 50.0F));
                  this.setPosition(this.axisX + dist * Math.sin(this.angle), this.posY, this.axisZ + dist * Math.cos(this.angle));
                  this.setAlphaF((float)Math.cos(1.57D * (double)this.particleAge / (double)this.particleMaxAge));
               }

               public int getFXLayer() {
                  return 0;
               }
            });
         }

      }

      @SideOnly(Side.CLIENT)
      public void renderInWorld(RenderItem customRenderItem, double x, double y, double z) {
         if(this.getUtil().getProcess().isInProgress()) {
            ItemStack stack = this.getUtil().getStack(9);
            this.dummyEntityItem.worldObj = this.getMachine().getWorld();
            this.dummyEntityItem.setEntityItemStack(stack);
            ++this.dummyEntityItem.age;
            this.dummyEntityItem.hoverStart = 0.0F;
            if(stack != null) {
               EntityPlayer player = BinnieCore.proxy.getPlayer();
               double dx = x + 0.5D - player.lastTickPosX;
               double dz = z + 0.5D - player.lastTickPosZ;
               double t = Math.atan2(dz, dx) * 180.0D / 3.1415D;
               GL11.glPushMatrix();
               GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
               GL11.glTranslatef(0.0F, -0.25F, 0.0F);
               customRenderItem.doRender(this.dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
               GL11.glPopMatrix();
            }
         }
      }

      public void syncToNBT(NBTTagCompound nbt) {
         NBTTagCompound item = new NBTTagCompound();
         ItemStack stack = this.getUtil().getStack(9);
         if(stack != null) {
            stack.writeToNBT(item);
            nbt.setTag("item", item);
         }

      }

      public void syncFromNBT(NBTTagCompound nbt) {
         if(nbt.hasKey("item")) {
            this.getUtil().setStack(9, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
         } else {
            this.getUtil().setStack(9, (ItemStack)null);
         }

      }

      public void onInventoryUpdate() {
         if(this.getUtil().isServer()) {
            this.getUtil().refreshBlock();
         }
      }
   }

   public static class ComponentSplicerLogic extends ComponentProcessSetCost implements IProcess {
      int nOfGenes = 0;

      public int getProcessLength() {
         float n = (float)this.getNumberOfGenes();
         if(n > 1.0F) {
            n = 1.0F + (n - 1.0F) * 0.5F;
         }

         return (int)((float)super.getProcessLength() * n);
      }

      public int getProcessEnergy() {
         float n = (float)this.getNumberOfGenes();
         if(n > 1.0F) {
            n = 1.0F + (n - 1.0F) * 0.5F;
         }

         return (int)((float)super.getProcessEnergy() * n);
      }

      public void onInventoryUpdate() {
         super.onInventoryUpdate();
         this.nOfGenes = this.getGenesToUse();
      }

      protected int getGenesToUse() {
         ItemStack serum = this.getUtil().getStack(0);
         ItemStack target = this.getUtil().getStack(9);
         if(serum != null && target != null) {
            IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
            IGene[] genes = ((IItemSerum)serum.getItem()).getGenes(serum);
            if(ind.getGenome().getSpeciesRoot() != ((IItemSerum)serum.getItem()).getSpeciesRoot(serum)) {
               return 1;
            } else {
               int i = 0;

               for(IGene gene : genes) {
                  if(ind.getGenome().getActiveAllele(gene.getChromosome()) != gene.getAllele() || ind.getGenome().getInactiveAllele(gene.getChromosome()) != gene.getAllele()) {
                     ++i;
                  }
               }

               return i < 1?1:i;
            }
         } else {
            return 1;
         }
      }

      private int getFullNumberOfGenes() {
         ItemStack serum = this.getUtil().getStack(0);
         return serum == null?1:Engineering.getGenes(serum).length;
      }

      private int getNumberOfGenes() {
         return this.nOfGenes;
      }

      public String getTooltip() {
         int n = this.getNumberOfGenes();
         int f = this.getFullNumberOfGenes();
         return "Splicing in " + n + (f > 1?"/" + f:"") + " gene" + (n > 1?"s":"");
      }

      public ComponentSplicerLogic(Machine machine) {
         super(machine, 12000000, 1200);
      }

      public ErrorState canWork() {
         if(this.getUtil().isSlotEmpty(9)) {
            return new ErrorState.NoItem("No Individual to Splice", 9);
         } else if(this.getUtil().isSlotEmpty(0)) {
            return new ErrorState.NoItem("No Serum", 0);
         } else {
            ErrorState state = this.isValidSerum();
            return state != null?state:(this.getUtil().getStack(0) != null && Engineering.getCharges(this.getUtil().getStack(0)) == 0?new ErrorState("Empty Serum", "Serum is empty"):super.canWork());
         }
      }

      public ErrorState isValidSerum() {
         ItemStack serum = this.getUtil().getStack(0);
         ItemStack target = this.getUtil().getStack(9);
         IGene[] genes = Engineering.getGenes(serum);
         if(genes.length == 0) {
            return new ErrorState("Invalid Serum", "Serum does not hold any genes");
         } else if(!genes[0].getSpeciesRoot().isMember(target)) {
            return new ErrorState("Invalid Serum", "Mismatch of Serum Type and Target");
         } else {
            IIndividual individual = genes[0].getSpeciesRoot().getMember(target);
            boolean hasAll = true;

            for(IGene gene : genes) {
               if(hasAll) {
                  IAllele a = individual.getGenome().getActiveAllele(gene.getChromosome());
                  IAllele b = individual.getGenome().getInactiveAllele(gene.getChromosome());
                  hasAll = hasAll && a.getUID().equals(gene.getAllele().getUID()) && b.getUID().equals(gene.getAllele().getUID());
               }
            }

            if(hasAll) {
               return new ErrorState("Defunct Serum", "Individual already possesses this allele");
            } else {
               return null;
            }
         }
      }

      public ErrorState canProgress() {
         return super.canProgress();
      }

      protected void onFinishTask() {
         super.onFinishTask();
         ItemStack serum = this.getUtil().getStack(0);
         ItemStack target = this.getUtil().getStack(9);
         IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
         if(!ind.isAnalyzed()) {
            ind.analyze();
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            ind.writeToNBT(nbttagcompound);
            target.setTagCompound(nbttagcompound);
         }

         IGene[] genes = ((IItemSerum)serum.getItem()).getGenes(serum);

         for(IGene gene : genes) {
            Splicer.setGene(gene, target, 0);
            Splicer.setGene(gene, target, 1);
         }

         this.getUtil().damageItem(0, 1);
      }

      protected void onTickTask() {
      }
   }

   public static class PackageSplicer extends AdvGeneticMachine.PackageAdvGeneticBase implements IMachineInformation {
      public PackageSplicer() {
         super("splicer", GeneticsTexture.Splicer, 14819893, true);
      }

      public void createMachine(Machine machine) {
         new ComponentGeneticGUI(machine, GeneticsGUI.Splicer);
         ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
         inventory.addSlot(0, "serum.active");
         inventory.getSlot(0).forbidInteraction();
         inventory.getSlot(0).setReadOnly();
         SlotValidator serumValid = new SlotValidator(ModuleMachine.IconSerum) {
            public boolean isValid(ItemStack itemStack) {
               return itemStack.getItem() instanceof IItemSerum;
            }

            public String getTooltip() {
               return "Serum Vials & Arrays";
            }
         };
         inventory.getSlot(0).setValidator(serumValid);
         inventory.addSlotArray(Splicer.slotSerumReserve, "serum.input");

         for(InventorySlot slot : inventory.getSlots(Splicer.slotSerumReserve)) {
            slot.setValidator(serumValid);
            slot.forbidExtraction();
         }

         inventory.addSlotArray(Splicer.slotSerumExpended, "serum.output");

         for(InventorySlot slot : inventory.getSlots(Splicer.slotSerumExpended)) {
            slot.setValidator(serumValid);
            slot.setReadOnly();
         }

         inventory.addSlotArray(Splicer.slotReserve, "input");

         for(InventorySlot slot : inventory.getSlots(Splicer.slotReserve)) {
            slot.forbidExtraction();
            slot.setValidator(new Splicer.ValidatorIndividualInoculate());
         }

         inventory.addSlot(9, "process");
         inventory.getSlot(9).setValidator(new Splicer.ValidatorIndividualInoculate());
         inventory.getSlot(9).setReadOnly();
         inventory.getSlot(9).forbidInteraction();
         inventory.addSlotArray(Splicer.slotFinished, "output");

         for(InventorySlot slot : inventory.getSlots(Splicer.slotFinished)) {
            slot.setReadOnly();
            slot.forbidInsertion();
            slot.setValidator(new Splicer.ValidatorIndividualInoculate());
         }

         ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
         transfer.addRestock(Splicer.slotReserve, 9, 1);
         transfer.addRestock(Splicer.slotSerumReserve, 0);
         transfer.addStorage(0, Splicer.slotSerumExpended, new ComponentInventoryTransfer.Condition() {
            public boolean fufilled(ItemStack stack) {
               return Engineering.getCharges(stack) == 0;
            }
         });
         transfer.addStorage(9, Splicer.slotFinished, new ComponentInventoryTransfer.Condition() {
            public boolean fufilled(ItemStack stack) {
               return stack != null && this.transfer.getMachine().getMachineUtil().getStack(0) != null && ((Splicer.ComponentSplicerLogic)this.transfer.getMachine().getInterface(Splicer.ComponentSplicerLogic.class)).isValidSerum() != null;
            }
         });
         new ComponentPowerReceptor(machine, 20000);
         new Splicer.ComponentSplicerLogic(machine);
         new Splicer.ComponentSplicerFX(machine);
      }

      public TileEntity createTileEntity() {
         return new TileEntityMachine(this);
      }

      public void register() {
      }
   }

   public static class ValidatorIndividualInoculate extends SlotValidator {
      public ValidatorIndividualInoculate() {
         super((ValidatorIcon)null);
      }

      public boolean isValid(ItemStack object) {
         ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(object);
         return root == null?false:Binnie.Genetics.getSystem(root).isDNAManipulable(object);
      }

      public String getTooltip() {
         return "Splicable Individual";
      }
   }
}
