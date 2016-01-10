package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineUtil;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.machines.transfer.TransferRequest;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.api.IIncubatorRecipe;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.machine.ComponentGeneticGUI;
import binnie.genetics.machine.GeneticMachine;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IBee;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class Incubator {
   public static final int[] slotQueue = new int[]{0, 1, 2};
   public static final int slotIncubator = 3;
   public static final int[] slotOutput = new int[]{4, 5, 6};
   public static final int tankInput = 0;
   public static final int tankOutput = 1;
   private static List RECIPES = new ArrayList();

   public Incubator() {
      super();
   }

   public static void addRecipes() {
      RECIPES.add(new Incubator.IncubatorRecipe(Binnie.Liquid.getLiquidStack("water", 25), GeneticLiquid.GrowthMedium.get(25), 0.2F) {
         public boolean isItemStack(ItemStack stack) {
            return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
         }
      });
      RECIPES.add(new Incubator.IncubatorRecipe(GeneticLiquid.GrowthMedium.get(25), GeneticLiquid.Bacteria.get(5), 0.2F) {
         public boolean isItemStack(ItemStack stack) {
            return (new ItemStack(Items.wheat)).isItemEqual(stack);
         }
      });
      RECIPES.add(new Incubator.IncubatorRecipe(GeneticLiquid.Bacteria.get(0), GeneticLiquid.Bacteria.get(5), 0.05F) {
         public boolean isItemStack(ItemStack stack) {
            return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
         }
      });
      RECIPES.add((new Incubator.IncubatorRecipe(GeneticLiquid.Bacteria.get(2), (FluidStack)null, 0.5F, 0.2F) {
         public boolean isItemStack(ItemStack stack) {
            return stack.getItem() == Items.sugar;
         }
      }).setOutputStack(GeneticsItems.Enzyme.get(1)));
      RECIPES.add(new Incubator.IncubatorRecipe(GeneticLiquid.BacteriaPoly.get(0), GeneticLiquid.BacteriaPoly.get(5), 0.05F) {
         public boolean isItemStack(ItemStack stack) {
            return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
         }
      });
      RECIPES.add(new Incubator.IncubatorRecipe(GeneticLiquid.BacteriaVector.get(0), GeneticLiquid.BacteriaVector.get(5), 0.05F) {
         public boolean isItemStack(ItemStack stack) {
            return GeneticsItems.GrowthMedium.get(1).isItemEqual(stack);
         }
      });
      RECIPES.add(new Incubator.IncubatorRecipe(GeneticLiquid.Bacteria.get(10), GeneticLiquid.BacteriaPoly.get(10), 0.1F) {
         public boolean isItemStack(ItemStack stack) {
            return (new ItemStack(Items.dye, 1, 15)).isItemEqual(stack);
         }
      });
      RECIPES.add(new Incubator.IncubatorRecipe(GeneticLiquid.Bacteria.get(10), GeneticLiquid.BacteriaVector.get(10), 0.05F) {
         public boolean isItemStack(ItemStack stack) {
            return (new ItemStack(Items.blaze_powder)).isItemEqual(stack);
         }
      });
      if(BinnieCore.isApicultureActive()) {
         RECIPES.add(new Incubator.IncubatorRecipe(GeneticLiquid.GrowthMedium.get(50), (FluidStack)null, 1.0F, 0.05F) {
            public boolean isItemStack(ItemStack stack) {
               return Binnie.Genetics.getBeeRoot().isMember(stack) && Binnie.Genetics.getBeeRoot().getType(stack) == EnumBeeType.LARVAE;
            }

            public ItemStack getOutputStack(MachineUtil machine) {
               ItemStack larvae = machine.getStack(3);
               IBee bee = Binnie.Genetics.getBeeRoot().getMember(larvae);
               return Binnie.Genetics.getBeeRoot().getMemberStack(bee, EnumBeeType.DRONE.ordinal());
            }
         });
      }

   }

   public static class ComponentIncubatorLogic extends ComponentProcessIndefinate implements IProcess {
      IIncubatorRecipe recipe = null;
      private Random rand = new Random();
      private boolean roomForOutput = true;

      public ComponentIncubatorLogic(Machine machine) {
         super(machine, 2.0F);
      }

      public ErrorState canWork() {
         return this.recipe == null?new ErrorState("No Recipe", "There is no valid recipe"):super.canWork();
      }

      public ErrorState canProgress() {
         if(this.recipe != null) {
            if(!this.recipe.isInputLiquidSufficient(this.getUtil().getFluid(0))) {
               return new ErrorState.InsufficientLiquid("Not enough incubation liquid", 0);
            }

            if(!this.roomForOutput) {
               return new ErrorState.TankSpace("No room for output", 1);
            }
         }

         return super.canProgress();
      }

      protected void onTickTask() {
         if(this.rand.nextInt(20) == 0 && this.recipe != null && this.rand.nextFloat() < this.recipe.getChance()) {
            this.recipe.doTask(this.getUtil());
         }

      }

      public boolean inProgress() {
         return this.recipe != null;
      }

      private IIncubatorRecipe getRecipe(ItemStack stack, FluidStack liquid) {
         for(IIncubatorRecipe recipe : Incubator.RECIPES) {
            boolean rightLiquid = recipe.isInputLiquid(liquid);
            boolean rightItem = recipe.isItemStack(stack);
            if(rightLiquid && rightItem) {
               return recipe;
            }
         }

         return null;
      }

      public void onInventoryUpdate() {
         super.onInventoryUpdate();
         if(this.getUtil().isServer()) {
            FluidStack liquid = this.getUtil().getFluid(0);
            ItemStack incubator = this.getUtil().getStack(3);
            if(this.recipe != null && (incubator == null || liquid == null || !this.recipe.isInputLiquid(liquid) || !this.recipe.isItemStack(incubator))) {
               this.recipe = null;
               ItemStack leftover = (new TransferRequest(incubator, this.getInventory())).setTargetSlots(Incubator.slotOutput).ignoreValidation().transfer(true);
               this.getUtil().setStack(3, leftover);
            }

            if(this.recipe == null) {
               if(liquid == null) {
                  return;
               }

               if(incubator != null) {
                  IIncubatorRecipe recipe = this.getRecipe(incubator, liquid);
                  if(recipe != null) {
                     this.recipe = recipe;
                     return;
                  }
               }

               IIncubatorRecipe potential = null;
               int potentialSlot = 0;

               for(int slot : Incubator.slotQueue) {
                  ItemStack stack = this.getUtil().getStack(slot);
                  if(stack != null && potential == null) {
                     for(IIncubatorRecipe recipe : Incubator.RECIPES) {
                        boolean rightLiquid = recipe.isInputLiquid(liquid);
                        boolean rightItem = recipe.isItemStack(stack);
                        if(rightLiquid && rightItem) {
                           potential = recipe;
                           potentialSlot = slot;
                           break;
                        }
                     }
                  }
               }

               if(potential != null) {
                  TransferRequest removal = (new TransferRequest(incubator, this.getInventory())).setTargetSlots(Incubator.slotOutput).ignoreValidation();
                  if(removal.transfer(false) == null) {
                     this.recipe = potential;
                  }

                  removal.transfer(true);
                  ItemStack stack = this.getUtil().getStack(potentialSlot);
                  this.getUtil().setStack(potentialSlot, (ItemStack)null);
                  this.getUtil().setStack(3, stack);
               }
            }

            if(this.recipe != null) {
               this.roomForOutput = this.recipe.roomForOutput(this.getUtil());
            }

         }
      }
   }

   private class IncubatorCrafting {
      ItemStack input;
      FluidStack fluid;

      private IncubatorCrafting() {
         super();
      }
   }

   private abstract static class IncubatorRecipe implements IIncubatorRecipe {
      FluidStack input;
      FluidStack output;
      float lossChance;
      ItemStack outputStack;
      float tickChance;

      public float getChance() {
         return this.tickChance;
      }

      public IncubatorRecipe(FluidStack input, FluidStack output, float lossChance) {
         this(input, output, lossChance, 1.0F);
      }

      public IncubatorRecipe(FluidStack input, FluidStack output, float lossChance, float chance) {
         super();
         this.input = input;
         this.output = output;
         this.lossChance = lossChance;
         this.tickChance = chance;
      }

      public boolean isInputLiquid(FluidStack fluid) {
         return fluid != null && this.input.isFluidEqual(fluid);
      }

      public boolean isInputLiquidSufficient(FluidStack fluid) {
         return fluid != null && fluid.amount >= 500;
      }

      public void doTask(MachineUtil machine) {
         machine.drainTank(0, this.input.amount);
         if(this.output != null) {
            machine.fillTank(1, this.output);
         }

         this.outputStack = this.getOutputStack(machine);
         if(this.outputStack != null) {
            ItemStack output = this.outputStack.copy();
            TransferRequest product = (new TransferRequest(output, machine.getInventory())).setTargetSlots(Incubator.slotOutput).ignoreValidation();
            product.transfer(true);
         }

         Random rand = machine.getRandom();
         if(rand.nextFloat() < this.lossChance) {
            machine.decreaseStack(3, 1);
         }

      }

      public Incubator.IncubatorRecipe setOutputStack(ItemStack stack) {
         this.outputStack = stack;
         return this;
      }

      protected ItemStack getOutputStack(MachineUtil util) {
         return this.outputStack;
      }

      public boolean roomForOutput(MachineUtil machine) {
         if(this.output != null && !machine.isTankEmpty(1)) {
            if(!machine.getFluid(1).isFluidEqual(this.output)) {
               return false;
            }

            if(!machine.spaceInTank(1, this.output.amount)) {
               return false;
            }
         }

         ItemStack outputStack = this.getOutputStack(machine);
         if(outputStack != null) {
            ItemStack output = outputStack.copy();
            TransferRequest product = (new TransferRequest(output, machine.getInventory())).setTargetSlots(Incubator.slotOutput).ignoreValidation();
            ItemStack leftover = product.transfer(false);
            return leftover == null;
         } else {
            return true;
         }
      }
   }

   public static class PackageIncubator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
      public PackageIncubator() {
         super("incubator", GeneticsTexture.Incubator, 16767313, true);
      }

      public void createMachine(Machine machine) {
         new ComponentGeneticGUI(machine, GeneticsGUI.Incubator);
         ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
         inventory.addSlotArray(Incubator.slotQueue, "input");

         for(InventorySlot slot : inventory.getSlots(Incubator.slotQueue)) {
            slot.forbidExtraction();
         }

         inventory.addSlot(3, "incubator");
         inventory.getSlot(3).forbidInteraction();
         inventory.getSlot(3).setReadOnly();
         inventory.addSlotArray(Incubator.slotOutput, "output");

         for(InventorySlot slot : inventory.getSlots(Incubator.slotOutput)) {
            slot.forbidInsertion();
            slot.setReadOnly();
         }

         new ComponentPowerReceptor(machine, 2000);
         ComponentTankContainer tanks = new ComponentTankContainer(machine);
         tanks.addTank(0, "input", 2000).forbidExtraction();
         tanks.addTank(1, "output", 2000).setReadOnly();
         new Incubator.ComponentIncubatorLogic(machine);
      }

      public TileEntity createTileEntity() {
         return new TileEntityMachine(this);
      }

      public void register() {
      }
   }
}
