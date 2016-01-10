package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.inventory.ComponentChargedSlots;
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
import binnie.genetics.api.IItemAnalysable;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.ModuleMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class Analyser {
   public static final int[] slotReserve = new int[]{0, 1, 2, 3, 4, 5};
   public static final int slotTarget = 6;
   public static final int[] slotFinished = new int[]{7, 8, 9, 10, 11, 12};
   public static final int slotDye = 13;

   public Analyser() {
      super();
   }

   public static boolean isAnalysable(ItemStack stack) {
      IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
      return ind != null?true:(stack.getItem() instanceof IItemAnalysable?true:Binnie.Genetics.getConversion(stack) != null);
   }

   public static boolean isAnalysed(ItemStack stack) {
      IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
      return ind != null?ind.isAnalyzed():(stack.getItem() instanceof IItemAnalysable?((IItemAnalysable)stack.getItem()).isAnalysed(stack):false);
   }

   public static ItemStack analyse(ItemStack stack) {
      ItemStack conv = Binnie.Genetics.getConversionStack(stack);
      if(conv != null) {
         stack = conv;
      }

      ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
      if(root != null) {
         IIndividual ind = root.getMember(stack);
         ind.analyze();
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         ind.writeToNBT(nbttagcompound);
         stack.setTagCompound(nbttagcompound);
         return stack;
      } else {
         return stack.getItem() instanceof IItemAnalysable?((IItemAnalysable)stack.getItem()).analyse(stack):stack;
      }
   }

   public static class ComponentAnalyserFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
      private final EntityItem dummyEntityItem = new EntityItem((World)null);

      public ComponentAnalyserFX(IMachine machine) {
         super(machine);
      }

      @SideOnly(Side.CLIENT)
      public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
      }

      @SideOnly(Side.CLIENT)
      public void onDisplayTick(final World world, int x, int y, int z, Random rand) {
         if(rand.nextFloat() < 1.0F && this.getUtil().getProcess().isInProgress()) {
            BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, (double)x + 0.5D, (double)z + 0.5D, 0.0D, x4, x5, x6) {
               double axisX = 0.0D;
               double axisZ = 0.0D;
               double angle = 0.0D;

               {
                  this.motionX = 0.05D * (this.rand.nextDouble() - 0.5D);
                  this.motionZ = 0.05D * (this.rand.nextDouble() - 0.5D);
                  this.motionY = 0.0D;
                  this.particleMaxAge = 25;
                  this.axisX = this.posX;
                  this.axisZ = this.posZ;
                  this.particleGravity = 0.05F;
                  this.angle = this.rand.nextDouble() * 2.0D * 3.1415D;
                  this.noClip = true;
                  this.setRBGColorF(0.6F, 0.0F, 1.0F);
               }

               public void onUpdate() {
                  super.onUpdate();
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
            ItemStack stack = this.getUtil().getStack(6);
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
               GL11.glTranslatef(0.0F, -0.2F, 0.0F);
               customRenderItem.doRender(this.dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
               GL11.glPopMatrix();
            }
         }
      }

      public void syncToNBT(NBTTagCompound nbt) {
         NBTTagCompound item = new NBTTagCompound();
         ItemStack stack = this.getUtil().getStack(6);
         if(stack != null) {
            stack.writeToNBT(item);
            nbt.setTag("item", item);
         }

      }

      public void syncFromNBT(NBTTagCompound nbt) {
         if(nbt.hasKey("item")) {
            this.getUtil().setStack(6, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
         } else {
            this.getUtil().setStack(6, (ItemStack)null);
         }

      }

      public void onInventoryUpdate() {
         if(this.getUtil().isServer()) {
            this.getMachine().getWorld().markBlockForUpdate(this.getMachine().getTileEntity().xCoord, this.getMachine().getTileEntity().yCoord, this.getMachine().getTileEntity().zCoord);
         }

      }
   }

   public static class ComponentAnalyserLogic extends ComponentProcessSetCost implements IProcess {
      private static final float DYE_PER_TICK = 0.002F;

      public ComponentAnalyserLogic(Machine machine) {
         super(machine, 9000, 300);
      }

      public ErrorState canWork() {
         if(this.getUtil().isSlotEmpty(6)) {
            return new ErrorState.NoItem("No item to analyse", 6);
         } else {
            boolean analysed = Analyser.isAnalysed(this.getUtil().getStack(6));
            return (ErrorState)(analysed?new ErrorState.InvalidItem("Already Analysed", "Item has already been analysed", 6):super.canWork());
         }
      }

      public ErrorState canProgress() {
         return (ErrorState)(this.getUtil().getSlotCharge(13) == 0.0F?new ErrorState.Item("Insufficient Dye", "Not enough DNA dye to analyse", new int[]{13}):super.canProgress());
      }

      protected void onFinishTask() {
         super.onFinishTask();
         ItemStack itemStack = this.getUtil().getStack(6);
         itemStack = Analyser.analyse(itemStack);
         this.getInventory().setInventorySlotContents(6, itemStack);
      }

      protected void onTickTask() {
         this.getUtil().useCharge(13, 0.002F);
      }
   }

   public static class PackageAnalyser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
      public PackageAnalyser() {
         super("analyser", GeneticsTexture.Analyser, 9961727, true);
      }

      public void createMachine(Machine machine) {
         new ComponentGeneticGUI(machine, GeneticsGUI.Analyser);
         ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
         inventory.addSlotArray(Analyser.slotReserve, "input");

         for(InventorySlot slot : inventory.getSlots(Analyser.slotReserve)) {
            slot.setValidator(new Analyser.SlotValidatorUnanalysed());
            slot.forbidExtraction();
         }

         inventory.addSlot(6, "analyse");
         inventory.getSlot(6).setValidator(new Analyser.SlotValidatorUnanalysed());
         inventory.getSlot(6).setReadOnly();
         inventory.getSlot(6).forbidInteraction();
         inventory.addSlot(13, "dye");
         inventory.getSlot(13).forbidExtraction();
         inventory.getSlot(13).setValidator(new SlotValidator(ModuleMachine.IconDye) {
            public boolean isValid(ItemStack itemStack) {
               return itemStack.isItemEqual(GeneticsItems.DNADye.get(1));
            }

            public String getTooltip() {
               return "DNA Dye";
            }
         });
         inventory.addSlotArray(Analyser.slotFinished, "output");

         for(InventorySlot slot : inventory.getSlots(Analyser.slotFinished)) {
            slot.forbidInsertion();
            slot.setReadOnly();
         }

         ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
         transfer.addRestock(Analyser.slotReserve, 6, 1);
         transfer.addStorage(6, Analyser.slotFinished, new ComponentInventoryTransfer.Condition() {
            public boolean fufilled(ItemStack stack) {
               return Analyser.isAnalysed(stack);
            }
         });
         (new ComponentChargedSlots(machine)).addCharge(13);
         new ComponentPowerReceptor(machine, 500);
         new Analyser.ComponentAnalyserLogic(machine);
         new Analyser.ComponentAnalyserFX(machine);
      }

      public TileEntity createTileEntity() {
         return new TileEntityMachine(this);
      }

      public void register() {
      }
   }

   public static class SlotValidatorUnanalysed extends SlotValidator {
      public SlotValidatorUnanalysed() {
         super((ValidatorIcon)null);
      }

      public boolean isValid(ItemStack itemStack) {
         return Analyser.isAnalysable(itemStack) && !Analyser.isAnalysed(itemStack);
      }

      public String getTooltip() {
         return "Unanalysed Item";
      }
   }
}
