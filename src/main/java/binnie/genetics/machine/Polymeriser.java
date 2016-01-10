package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.inventory.ComponentChargedSlots;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.Validator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.api.IItemChargable;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import binnie.genetics.machine.ModuleMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Random;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class Polymeriser {
   public static final int tankBacteria = 0;
   public static final int tankDNA = 1;
   public static final int slotSerum = 0;
   public static final int slotGold = 1;
   public static final int[] slotSerumReserve = new int[]{2, 3, 4, 5};
   public static final int[] slotSerumFinished = new int[]{6, 7, 8, 9};

   public Polymeriser() {
      super();
   }

   public static class ComponentPolymeriserFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
      public ComponentPolymeriserFX(IMachine machine) {
         super(machine);
      }

      @SideOnly(Side.CLIENT)
      public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
      }

      @SideOnly(Side.CLIENT)
      public void onDisplayTick(final World world, int x, int y, int z, Random rand) {
         int tick = (int)(world.getTotalWorldTime() % 8L);
         if((tick == 0 || tick == 3) && this.getUtil().getProcess().isInProgress()) {
            BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, (double)x + 0.5D, (double)z + 0.5D, 0.0D, x4, x5, x6) {
               double axisX = 0.0D;
               double axisZ = 0.0D;
               double angle = 0.0D;

               {
                  this.motionX = 0.0D;
                  this.motionZ = 0.0D;
                  this.motionY = -0.006D;
                  this.particleMaxAge = 140;
                  this.axisX = this.posX;
                  this.axisZ = this.posZ;
                  this.particleGravity = 0.0F;
                  this.angle = 0.7D + (double)((int)(this.worldObj.getTotalWorldTime() % 2L)) * 3.1415D;
                  this.noClip = true;
                  this.setRBGColorF(0.8F, 0.0F, 1.0F);
               }

               public void onUpdate() {
                  super.onUpdate();
                  this.angle += 0.1D;
                  this.motionY = -0.006D;
                  double dist = 0.2D;
                  if(this.particleAge > 60) {
                     if(this.particleAge > 120) {
                        dist = 0.1D;
                     } else {
                        dist = 0.2D - 0.1D * (double)(((float)this.particleAge - 60.0F) / 60.0F);
                     }
                  }

                  this.setPosition(this.axisX + dist * Math.sin(this.angle), this.posY, this.axisZ + dist * Math.cos(this.angle));
                  if(this.particleAge <= 40) {
                     this.setAlphaF((float)this.particleAge / 40.0F);
                  }

                  if(this.particleAge > 80) {
                     this.setRBGColorF(this.particleRed + (0.0F - this.particleRed) / 10.0F, this.particleGreen + (1.0F - this.particleGreen) / 10.0F, this.particleBlue + (1.0F - this.particleBlue) / 10.0F);
                  }

               }

               public int getFXLayer() {
                  return 0;
               }
            });
         }

      }
   }

   public static class ComponentPolymeriserLogic extends ComponentProcessSetCost implements IProcess {
      private static float chargePerProcess = 0.4F;
      private float dnaDrain = 0.0F;
      private float bacteriaDrain = 0.0F;

      public ComponentPolymeriserLogic(Machine machine) {
         super(machine, 96000, 2400);
      }

      private float getCatalyst() {
         return this.getUtil().getSlotCharge(1) > 0.0F?0.2F:1.0F;
      }

      public int getProcessLength() {
         return (int)((float)(super.getProcessLength() * this.getNumberOfGenes()) * this.getCatalyst());
      }

      public int getProcessEnergy() {
         return (int)((float)(super.getProcessEnergy() * this.getNumberOfGenes()) * this.getCatalyst());
      }

      private float getDNAPerProcess() {
         return (float)(this.getNumberOfGenes() * 50);
      }

      public void onTickTask() {
         super.onTickTask();
         this.getUtil().useCharge(1, chargePerProcess * this.getProgressPerTick() / 100.0F);
         this.dnaDrain += this.getDNAPerProcess() * this.getProgressPerTick() / 100.0F;
         this.bacteriaDrain += 0.2F * this.getDNAPerProcess() * this.getProgressPerTick() / 100.0F;
         if(this.dnaDrain >= 1.0F) {
            this.getUtil().drainTank(1, 1);
            --this.dnaDrain;
         }

         if(this.bacteriaDrain >= 1.0F) {
            this.getUtil().drainTank(0, 1);
            --this.bacteriaDrain;
         }

      }

      private int getNumberOfGenes() {
         ItemStack serum = this.getUtil().getStack(0);
         return serum == null?1:Engineering.getGenes(serum).length;
      }

      public String getTooltip() {
         int n = this.getNumberOfGenes();
         return "Replicating " + n + " genes" + (n > 1?"s":"");
      }

      public ErrorState canWork() {
         return (ErrorState)(this.getUtil().isSlotEmpty(0)?new ErrorState.NoItem("No item to replicate", 0):(!this.getUtil().getStack(0).isItemDamaged()?new ErrorState.InvalidItem("Item filled", 0):super.canWork()));
      }

      public ErrorState canProgress() {
         return (ErrorState)(this.getUtil().getFluid(0) == null?new ErrorState.InsufficientLiquid("Insufficient Bacteria", 0):(this.getUtil().getFluid(1) == null?new ErrorState.InsufficientLiquid("Insufficient DNA", 1):super.canProgress()));
      }

      protected void onFinishTask() {
         super.onFinishTask();
         this.getUtil().damageItem(0, -1);
      }
   }

   public static class PackagePolymeriser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
      public PackagePolymeriser() {
         super("polymeriser", GeneticsTexture.Polymeriser, '\ue5c3', true);
      }

      public void createMachine(Machine machine) {
         new ComponentGeneticGUI(machine, GeneticsGUI.Replicator);
         ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
         inventory.addSlot(1, "catalyst");
         inventory.getSlot(1).setValidator(new SlotValidator.Item(new ItemStack(Items.gold_nugget, 1), ModuleMachine.IconNugget));
         inventory.getSlot(1).forbidExtraction();
         inventory.addSlot(0, "process");
         inventory.getSlot(0).setValidator(new Polymeriser.SlotValidatorUnfilledSerum());
         inventory.getSlot(0).forbidInteraction();
         inventory.getSlot(0).setReadOnly();

         for(InventorySlot slot : inventory.addSlotArray(Polymeriser.slotSerumReserve, "input")) {
            slot.setValidator(new Polymeriser.SlotValidatorUnfilledSerum());
            slot.forbidExtraction();
         }

         for(InventorySlot slot : inventory.addSlotArray(Polymeriser.slotSerumFinished, "output")) {
            slot.setReadOnly();
         }

         ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
         transfer.addRestock(Polymeriser.slotSerumReserve, 0, 1);
         transfer.addStorage(0, Polymeriser.slotSerumFinished, new ComponentInventoryTransfer.Condition() {
            public boolean fufilled(ItemStack stack) {
               return !stack.isItemDamaged();
            }
         });
         ComponentTankContainer tank = new ComponentTankContainer(machine);
         tank.addTank(0, "input", 1000);
         tank.getTankSlot(0).setValidator(new Validator() {
            public boolean isValid(FluidStack itemStack) {
               return GeneticLiquid.BacteriaPoly.get(1).isFluidEqual(itemStack);
            }

            public String getTooltip() {
               return "Polymerising Bacteria";
            }
         });
         tank.addTank(1, "input", 1000);
         tank.getTankSlot(1).setValidator(new Validator() {
            public boolean isValid(FluidStack itemStack) {
               return GeneticLiquid.RawDNA.get(1).isFluidEqual(itemStack);
            }

            public String getTooltip() {
               return "Raw DNA";
            }
         });
         (new ComponentChargedSlots(machine)).addCharge(1);
         new ComponentPowerReceptor(machine, 8000);
         new Polymeriser.ComponentPolymeriserLogic(machine);
         new Polymeriser.ComponentPolymeriserFX(machine);
      }

      public TileEntity createTileEntity() {
         return new TileEntityMachine(this);
      }

      public void register() {
      }
   }

   public static class SlotValidatorUnfilledSerum extends SlotValidator {
      public SlotValidatorUnfilledSerum() {
         super(ModuleMachine.IconSerum);
      }

      public boolean isValid(ItemStack itemStack) {
         return itemStack.getItem() instanceof IItemChargable;
      }

      public String getTooltip() {
         return "Unfilled Serum";
      }
   }
}
