package binnie.extratrees.machines;

import binnie.Binnie;
import binnie.core.liquid.IFluidType;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.alcohol.Alcohol;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.item.ExtraTreeItems;
import binnie.extratrees.machines.ExtraTreeMachine;
import cpw.mods.fml.relauncher.Side;
import forestry.api.core.INBTTagable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class Brewery {
   public static int[] slotRecipeGrains = new int[]{0, 1, 2};
   public static int slotRecipeInput = 3;
   public static int slotRecipeYeast = 4;
   public static int[] slotInventory = new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13};
   public static int tankInput = 0;
   public static int tankOutput = 1;
   private static List recipes = new ArrayList();

   public Brewery() {
      super();
   }

   public static boolean isValidGrain(ItemStack itemstack) {
      for(Brewery.IBreweryRecipe recipe : recipes) {
         for(ItemStack ingredient : recipe.getGrains()) {
            if(ingredient.isItemEqual(itemstack)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean isValidYeast(ItemStack itemstack) {
      for(Brewery.IBreweryRecipe recipe : recipes) {
         for(ItemStack ingredient : recipe.getYeasts()) {
            if(ingredient.isItemEqual(itemstack)) {
               return true;
            }
         }
      }

      return false;
   }

   public static FluidStack getOutput(FluidStack stack) {
      Brewery.BreweryCrafting crafting = new Brewery.BreweryCrafting(stack, (ItemStack)null, (ItemStack[])null, (ItemStack)null);

      for(Brewery.IBreweryRecipe recipe : recipes) {
         if(recipe.getOutput(crafting) != null) {
            return recipe.getOutput(crafting);
         }
      }

      return null;
   }

   public static FluidStack getOutput(Brewery.BreweryCrafting crafting) {
      if(crafting.currentInput == null) {
         return null;
      } else if(crafting.yeast == null) {
         return null;
      } else {
         for(Brewery.IBreweryRecipe recipe : recipes) {
            if(recipe.getOutput(crafting) != null) {
               return recipe.getOutput(crafting);
            }
         }

         return null;
      }
   }

   public static boolean isValidIngredient(ItemStack itemstack) {
      for(Brewery.IBreweryRecipe recipe : recipes) {
         for(ItemStack ingr : recipe.getIngredient()) {
            if(ingr.isItemEqual(itemstack)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean isValidInputLiquid(FluidStack fluid) {
      for(Brewery.IBreweryRecipe recipe : recipes) {
         for(FluidStack ingr : recipe.getInput()) {
            if(ingr.isFluidEqual(fluid)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean isValidOutputLiquid(FluidStack fluid) {
      for(Brewery.IBreweryRecipe recipe : recipes) {
         for(FluidStack ingr : recipe.getOutput()) {
            if(ingr.isFluidEqual(fluid)) {
               return true;
            }
         }
      }

      return false;
   }

   public static void addRecipe(FluidStack input, FluidStack output) {
      recipes.add(new Brewery.BreweryRecipe(input, output));
   }

   public static void addBeerAndMashRecipes() {
      recipes.add(new Brewery.BeerRecipe());
   }

   public static class BeerRecipe implements Brewery.IBreweryRecipe {
      Map grainCrops = new HashMap();
      List outputs = new ArrayList();
      static String barley = "seedBarley";
      static String wheat = "seedWheat";
      static String rye = "seedRye";
      static String corn = "seedCorn";
      static String roasted = "seedRoasted";
      String[] grains;
      FluidStack water;
      ItemStack hops;

      public BeerRecipe() {
         super();
         this.grains = new String[]{barley, wheat, rye, corn, roasted};

         for(String grainType : this.grains) {
            for(ItemStack stack : OreDictionary.getOres(grainType)) {
               this.grainCrops.put(stack, grainType);
            }
         }

         this.water = Binnie.Liquid.getLiquidStack("water", 5);
         this.hops = (ItemStack)OreDictionary.getOres("cropHops").get(0);
         this.add(Alcohol.Ale);
         this.add(Alcohol.Lager);
         this.add(Alcohol.Stout);
         this.add(Alcohol.CornBeer);
         this.add(Alcohol.RyeBeer);
         this.add(Alcohol.WheatBeer);
         this.add(Alcohol.Barley);
         this.add(Alcohol.Corn);
         this.add(Alcohol.Rye);
         this.add(Alcohol.Wheat);
      }

      private void add(IFluidType fluid) {
         this.outputs.add(fluid.get(1));
      }

      public FluidStack getOutput(Brewery.BreweryCrafting crafting) {
         if(!crafting.currentInput.isFluidEqual(this.water)) {
            return null;
         } else {
            int roasted = 0;
            int barley = 0;
            int wheat = 0;
            int rye = 0;
            int corn = 0;

            for(ItemStack stack : crafting.inputs) {
               if(stack == null) {
                  return null;
               }

               String name = "";

               for(Entry<ItemStack, String> entry : this.grainCrops.entrySet()) {
                  if(((ItemStack)entry.getKey()).isItemEqual(stack)) {
                     name = (String)entry.getValue();
                     break;
                  }
               }

               if(name.isEmpty()) {
                  return null;
               }

               if(name.equals(roasted)) {
                  ++roasted;
               }

               if(name.equals(barley)) {
                  ++barley;
               }

               if(name.equals(wheat)) {
                  ++wheat;
               }

               if(name.equals(rye)) {
                  ++rye;
               }

               if(name.equals(corn)) {
                  ++corn;
               }
            }

            boolean isBeer = crafting.ingr != null;
            boolean isLager = crafting.yeast.isItemEqual(ExtraTreeItems.LagerYeast.get(1));
            if(roasted >= 2 && isBeer) {
               return Alcohol.Stout.get(5);
            } else if(wheat >= 2) {
               return isBeer?Alcohol.WheatBeer.get(5):Alcohol.Wheat.get(5);
            } else if(rye >= 2) {
               return isBeer?Alcohol.RyeBeer.get(5):Alcohol.Rye.get(5);
            } else if(corn >= 2) {
               return isBeer?Alcohol.CornBeer.get(5):Alcohol.Corn.get(5);
            } else {
               return isBeer?(isLager?Alcohol.Lager.get(5):Alcohol.Ale.get(5)):Alcohol.Barley.get(5);
            }
         }
      }

      public FluidStack[] getInput() {
         return new FluidStack[]{this.water};
      }

      public FluidStack[] getOutput() {
         return (FluidStack[])this.outputs.toArray(new FluidStack[0]);
      }

      public ItemStack[] getGrains() {
         return (ItemStack[])this.grainCrops.keySet().toArray(new ItemStack[0]);
      }

      public ItemStack[] getIngredient() {
         return new ItemStack[]{this.hops};
      }

      public ItemStack[] getYeasts() {
         return new ItemStack[]{ExtraTreeItems.Yeast.get(1), ExtraTreeItems.LagerYeast.get(1)};
      }
   }

   public static class BreweryCrafting implements INBTTagable {
      public FluidStack currentInput;
      public ItemStack[] inputs;
      public ItemStack ingr;
      public ItemStack yeast;

      public BreweryCrafting(FluidStack currentInput, ItemStack ingr, ItemStack[] inputs, ItemStack yeast) {
         super();
         this.currentInput = currentInput;
         this.inputs = inputs == null?new ItemStack[3]:inputs;
         this.ingr = ingr;
         this.yeast = yeast;
      }

      public BreweryCrafting(NBTTagCompound nbt) {
         super();
         this.readFromNBT(nbt);
      }

      public void readFromNBT(NBTTagCompound nbt) {
         this.currentInput = FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag("fluid"));
         this.ingr = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("ingr"));
         this.inputs = new ItemStack[3];
         this.inputs[0] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("in1"));
         this.inputs[1] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("in2"));
         this.inputs[2] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("in3"));
         this.yeast = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("yeast"));
      }

      public void writeToNBT(NBTTagCompound nbt) {
         NBTTagCompound fluidTag = new NBTTagCompound();
         this.currentInput.writeToNBT(fluidTag);
         nbt.setTag("fluid", fluidTag);
         nbt.setTag("ingr", this.getNBT(this.ingr));
         nbt.setTag("in1", this.getNBT(this.inputs[0]));
         nbt.setTag("in2", this.getNBT(this.inputs[1]));
         nbt.setTag("in3", this.getNBT(this.inputs[2]));
         nbt.setTag("yeast", this.getNBT(this.yeast));
      }

      private NBTTagCompound getNBT(ItemStack ingr) {
         if(ingr == null) {
            return new NBTTagCompound();
         } else {
            NBTTagCompound nbt = new NBTTagCompound();
            ingr.writeToNBT(nbt);
            return nbt;
         }
      }
   }

   public static class BreweryRecipe implements Brewery.IBreweryRecipe {
      FluidStack input;
      ItemStack specificYeast;
      FluidStack output;

      public BreweryRecipe(FluidStack input, FluidStack output) {
         this(input, (ItemStack)null, output);
      }

      public BreweryRecipe(FluidStack input, ItemStack specificYeast, FluidStack output) {
         super();
         this.input = input;
         this.specificYeast = specificYeast;
         this.output = output;
      }

      public FluidStack getOutput(Brewery.BreweryCrafting crafting) {
         return this.specificYeast != null && !this.specificYeast.isItemEqual(crafting.yeast)?null:(this.input.isFluidEqual(crafting.currentInput)?this.output:null);
      }

      public FluidStack[] getInput() {
         return new FluidStack[]{this.input};
      }

      public FluidStack[] getOutput() {
         return new FluidStack[0];
      }

      public ItemStack[] getGrains() {
         return new ItemStack[0];
      }

      public ItemStack[] getIngredient() {
         return new ItemStack[0];
      }

      public ItemStack[] getYeasts() {
         return new ItemStack[]{ExtraTreeItems.Yeast.get(1)};
      }
   }

   public static class ComponentBreweryLogic extends ComponentProcessSetCost implements IProcess, INetwork.GuiNBT {
      public Brewery.BreweryCrafting currentCrafting = null;

      public ComponentBreweryLogic(Machine machine) {
         super(machine, 16000, 800);
      }

      public ErrorState canWork() {
         return (ErrorState)(this.getUtil().isTankEmpty(Brewery.tankInput) && this.currentCrafting == null?new ErrorState.InsufficientLiquid("No Input Liquid", Brewery.tankInput):(Brewery.getOutput(this.getInputCrafting()) == null && this.currentCrafting == null?new ErrorState("No Recipe", "Brewing cannot occur with these ingredients"):(!this.getUtil().hasIngredients(new int[]{0, 1, 2, 3, 4}, Brewery.slotInventory) && this.currentCrafting == null?new ErrorState("Insufficient Ingredients", "Not enough ingredients for Brewing"):super.canWork())));
      }

      private Brewery.BreweryCrafting getInputCrafting() {
         return new Brewery.BreweryCrafting(this.getUtil().getFluid(Brewery.tankInput), this.getUtil().getStack(Brewery.slotRecipeInput), this.getUtil().getStacks(Brewery.slotRecipeGrains), this.getUtil().getStack(Brewery.slotRecipeYeast));
      }

      public ErrorState canProgress() {
         return (ErrorState)(this.currentCrafting == null?new ErrorState("Brewery Empty", "No liquid in Brewery"):(!this.getUtil().spaceInTank(Brewery.tankOutput, 1000)?new ErrorState.TankSpace("No Space for Fermented Liquid", Brewery.tankOutput):(this.getUtil().getFluid(Brewery.tankOutput) != null && !this.getUtil().getFluid(Brewery.tankOutput).isFluidEqual(Brewery.getOutput(this.currentCrafting))?new ErrorState.TankSpace("Different fluid in tank", Brewery.tankOutput):super.canProgress())));
      }

      protected void onFinishTask() {
         FluidStack output = Brewery.getOutput(this.currentCrafting).copy();
         output.amount = 1000;
         this.getUtil().fillTank(Brewery.tankOutput, output);
      }

      public void onUpdate() {
         super.onUpdate();
         if(this.canWork() == null && this.currentCrafting == null && this.getUtil().getTank(Brewery.tankInput).getFluidAmount() >= 1000) {
            FluidStack stack = this.getUtil().drainTank(Brewery.tankInput, 1000);
            this.currentCrafting = this.getInputCrafting();
            this.currentCrafting.currentInput = stack;
         }

      }

      public void sendGuiNBT(Map data) {
         NBTTagCompound nbt = new NBTTagCompound();
         if(this.currentCrafting == null) {
            nbt.setBoolean("null", true);
         } else {
            this.currentCrafting.writeToNBT(nbt);
         }

         data.put("brewery-recipe", nbt);
      }

      public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
         if(name.equals("brewery-recipe")) {
            if(nbt.getBoolean("null")) {
               this.currentCrafting = null;
            } else {
               this.currentCrafting = new Brewery.BreweryCrafting(nbt);
            }
         }

      }

      public String getTooltip() {
         return this.currentCrafting == null?"Empty":"Creating " + Brewery.getOutput(this.currentCrafting).getFluid().getLocalizedName();
      }
   }

   private interface IBreweryRecipe {
      FluidStack getOutput(Brewery.BreweryCrafting var1);

      FluidStack[] getInput();

      FluidStack[] getOutput();

      ItemStack[] getGrains();

      ItemStack[] getIngredient();

      ItemStack[] getYeasts();
   }

   public static class PackageBrewery extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
      public PackageBrewery() {
         super("brewery", ExtraTreeTexture.breweryTexture, true);
      }

      public void createMachine(Machine machine) {
         new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Brewery);
         ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
         inventory.addSlotArray(Brewery.slotRecipeGrains, "grain");

         for(InventorySlot slot : inventory.getSlots(Brewery.slotRecipeGrains)) {
            slot.setValidator(new Brewery.SlotValidatorBreweryGrain());
            slot.setType(InventorySlot.Type.Recipe);
         }

         inventory.addSlotArray(Brewery.slotInventory, "inventory");

         for(InventorySlot slot : inventory.getSlots(Brewery.slotInventory)) {
            slot.forbidExtraction();
         }

         inventory.addSlot(Brewery.slotRecipeYeast, "yeast");
         inventory.getSlot(Brewery.slotRecipeYeast).setValidator(new Brewery.SlotValidatorYeast());
         inventory.getSlot(Brewery.slotRecipeYeast).setType(InventorySlot.Type.Recipe);
         inventory.addSlot(Brewery.slotRecipeInput, "ingredient");
         inventory.getSlot(Brewery.slotRecipeInput).setValidator(new Brewery.SlotValidatorBreweryIngredient());
         inventory.getSlot(Brewery.slotRecipeInput).setType(InventorySlot.Type.Recipe);
         ComponentTankContainer tanks = new ComponentTankContainer(machine);
         tanks.addTank(Brewery.tankInput, "input", 5000);
         tanks.getTankSlot(Brewery.tankInput).setValidator(new Brewery.TankValidatorFermentInput());
         tanks.getTankSlot(Brewery.tankInput).setOutputSides(MachineSide.TopAndBottom);
         tanks.addTank(Brewery.tankOutput, "output", 5000);
         tanks.getTankSlot(Brewery.tankOutput).setValidator(new Brewery.TankValidatorFermentOutput());
         tanks.getTankSlot(Brewery.tankOutput).forbidInsertion();
         tanks.getTankSlot(Brewery.tankOutput).setOutputSides(MachineSide.Sides);
         new ComponentPowerReceptor(machine);
         new Brewery.ComponentBreweryLogic(machine);
      }

      public TileEntity createTileEntity() {
         return new TileEntityMachine(this);
      }

      public void register() {
      }
   }

   public static class SlotValidatorBreweryGrain extends SlotValidator {
      public SlotValidatorBreweryGrain() {
         super(SlotValidator.IconBlock);
      }

      public boolean isValid(ItemStack itemStack) {
         return Brewery.isValidGrain(itemStack);
      }

      public String getTooltip() {
         return "Brewing Grains";
      }
   }

   public static class SlotValidatorBreweryIngredient extends SlotValidator {
      public SlotValidatorBreweryIngredient() {
         super(SlotValidator.IconBlock);
      }

      public boolean isValid(ItemStack itemStack) {
         return Brewery.isValidIngredient(itemStack);
      }

      public String getTooltip() {
         return "Brewing Ingredient";
      }
   }

   public static class SlotValidatorYeast extends SlotValidator {
      public SlotValidatorYeast() {
         super(SlotValidator.IconBlock);
      }

      public boolean isValid(ItemStack itemStack) {
         return Brewery.isValidYeast(itemStack);
      }

      public String getTooltip() {
         return "Yeast";
      }
   }

   public static class TankValidatorFermentInput extends TankValidator {
      public TankValidatorFermentInput() {
         super();
      }

      public boolean isValid(FluidStack itemStack) {
         return Brewery.isValidInputLiquid(itemStack);
      }

      public String getTooltip() {
         return "Fermentable Liquids";
      }
   }

   public static class TankValidatorFermentOutput extends TankValidator {
      public TankValidatorFermentOutput() {
         super();
      }

      public boolean isValid(FluidStack itemStack) {
         return Brewery.isValidOutputLiquid(itemStack);
      }

      public String getTooltip() {
         return "Fermented Liquids";
      }
   }
}
