package binnie.extratrees.block;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.block.ItemMetadataRenderer;
import binnie.core.block.TileEntityMetadata;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.BlockETDoor;
import binnie.extratrees.block.BlockETLog;
import binnie.extratrees.block.BlockETPlanks;
import binnie.extratrees.block.BlockETSlab;
import binnie.extratrees.block.BlockETStairs;
import binnie.extratrees.block.BranchBlockRenderer;
import binnie.extratrees.block.DoorBlockRenderer;
import binnie.extratrees.block.DoorType;
import binnie.extratrees.block.GateItemRenderer;
import binnie.extratrees.block.ILogType;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.ItemETDoor;
import binnie.extratrees.block.ItemETSlab;
import binnie.extratrees.block.ItemETStairs;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.block.StairItemRenderer;
import binnie.extratrees.block.StairsRenderer;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.block.decor.BlockFence;
import binnie.extratrees.block.decor.BlockGate;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.block.decor.FenceRenderer;
import binnie.extratrees.block.decor.FenceType;
import binnie.extratrees.block.decor.HedgeRenderer;
import binnie.extratrees.block.decor.MultiFenceRecipeEmbedded;
import binnie.extratrees.block.decor.MultiFenceRecipeSize;
import binnie.extratrees.block.decor.MultiFenceRecipeSolid;
import binnie.extratrees.item.ExtraTreeLiquid;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.RecipeSorter.Category;

public class ModuleBlocks implements IInitializable {
   public static int hedgeRenderID;

   public ModuleBlocks() {
      super();
   }

   public void preInit() {
      PlankType.setup();
      ExtraTrees.blockPlanks = new BlockETPlanks();
      ExtraTrees.blockFence = new BlockFence();
      ExtraTrees.blockLog = new BlockETLog();
      ExtraTrees.blockGate = new BlockGate();
      ExtraTrees.blockDoor = new BlockETDoor();
      ExtraTrees.blockMultiFence = new BlockMultiFence();
      ExtraTrees.blockSlab = new BlockETSlab(false);
      ExtraTrees.blockDoubleSlab = new BlockETSlab(true);
      ExtraTrees.blockStairs = new BlockETStairs(ExtraTrees.blockPlanks);
      GameRegistry.registerBlock(ExtraTrees.blockPlanks, ItemMetadata.class, "planks");
      GameRegistry.registerBlock(ExtraTrees.blockFence, ItemMetadata.class, "fence");
      GameRegistry.registerBlock(ExtraTrees.blockMultiFence, ItemMetadata.class, "multifence");
      BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockMultiFence), new ItemMetadataRenderer());
      GameRegistry.registerBlock(ExtraTrees.blockLog, ItemMetadata.class, "log");
      GameRegistry.registerBlock(ExtraTrees.blockGate, ItemMetadata.class, "gate");
      GameRegistry.registerBlock(ExtraTrees.blockSlab, ItemETSlab.class, "slab");
      GameRegistry.registerBlock(ExtraTrees.blockDoubleSlab, ItemETSlab.class, "doubleSlab");
      GameRegistry.registerBlock(ExtraTrees.blockDoor, ItemETDoor.class, "door");
      GameRegistry.registerBlock(ExtraTrees.blockStairs, ItemETStairs.class, "stairs");
      BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockStairs), new StairItemRenderer());
      BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockGate), new GateItemRenderer());

      for(ILogType plank : ILogType.ExtraTreeLog.values()) {
         OreDictionary.registerOre("logWood", plank.getItemStack());
      }

      GameRegistry.addSmelting(ExtraTrees.blockLog, new ItemStack(Items.coal, 1, 1), 0.15F);

      for(IPlankType plank : PlankType.ExtraTreePlanks.values()) {
         OreDictionary.registerOre("plankWood", plank.getStack());
      }

      FMLInterModComms.sendMessage("Forestry", "add-fence-block", "ExtraTrees:fence");
      FMLInterModComms.sendMessage("Forestry", "add-fence-block", "ExtraTrees:gate");
      FMLInterModComms.sendMessage("Forestry", "add-alveary-slab", "ExtraTrees:slab");
      FMLInterModComms.sendMessage("Forestry", "add-fence-block", "ExtraTrees:multifence");
      hedgeRenderID = BinnieCore.proxy.getUniqueRenderID();
   }

   public void init() {
      ExtraTrees.fenceID = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new FenceRenderer());
      ExtraTrees.stairsID = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new StairsRenderer());
      ExtraTrees.doorRenderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new DoorBlockRenderer());
      ExtraTrees.branchRenderId = RenderingRegistry.getNextAvailableRenderId();
      RenderingRegistry.registerBlockHandler(new BranchBlockRenderer());
      RenderingRegistry.registerBlockHandler(new HedgeRenderer());
      RecipeSorter.register("extratrees:multifence", MultiFenceRecipeSize.class, Category.SHAPED, "");
      RecipeSorter.register("extratrees:multifence2", MultiFenceRecipeEmbedded.class, Category.SHAPED, "");
      RecipeSorter.register("extratrees:multifence3", MultiFenceRecipeSolid.class, Category.SHAPED, "");
   }

   public void postInit() {
      for(PlankType.ExtraTreePlanks plank : PlankType.ExtraTreePlanks.values()) {
         ItemStack planks = plank.getStack();
         ItemStack slabs = TileEntityMetadata.getItemStack(ExtraTrees.blockSlab, plank.ordinal());
         ItemStack stairs = TileEntityMetadata.getItemStack(ExtraTrees.blockStairs, plank.ordinal());
         stairs.stackSize = 4;
         GameRegistry.addRecipe(stairs.copy(), new Object[]{"#  ", "## ", "###", Character.valueOf('#'), planks.copy()});
         slabs.stackSize = 6;
         CraftingManager.getInstance().getRecipeList().add(0, new ShapedOreRecipe(slabs.copy(), new Object[]{"###", Character.valueOf('#'), planks.copy()}));
      }

      GameRegistry.addRecipe(new MultiFenceRecipeSize());
      GameRegistry.addRecipe(new MultiFenceRecipeEmbedded());
      GameRegistry.addRecipe(new MultiFenceRecipeSolid());

      for(IPlankType plank : WoodManager.getAllPlankTypes()) {
         ItemStack planks = plank.getStack();
         ItemStack fenceNormal = WoodManager.getFence(plank, new FenceType(0), 1);
         ItemStack gate = WoodManager.getGate(plank);
         ItemStack doorStandard = WoodManager.getDoor(plank, DoorType.Standard);
         ItemStack doorSolid = WoodManager.getDoor(plank, DoorType.Solid);
         ItemStack doorSplit = WoodManager.getDoor(plank, DoorType.Double);
         ItemStack doorFull = WoodManager.getDoor(plank, DoorType.Full);
         if(planks != null && gate != null) {
            gate.stackSize = 1;
            GameRegistry.addRecipe(gate.copy(), new Object[]{"fpf", Character.valueOf('f'), fenceNormal.copy(), Character.valueOf('p'), planks.copy()});
            fenceNormal.stackSize = 4;
            GameRegistry.addRecipe(fenceNormal.copy(), new Object[]{"###", "# #", Character.valueOf('#'), planks.copy()});
            GameRegistry.addRecipe(doorSolid.copy(), new Object[]{"###", "###", "###", Character.valueOf('#'), planks.copy()});
            GameRegistry.addRecipe(doorStandard.copy(), new Object[]{"# #", "###", "###", Character.valueOf('#'), planks.copy()});
            GameRegistry.addRecipe(doorSplit.copy(), new Object[]{"# #", "###", "# #", Character.valueOf('#'), planks.copy()});
            GameRegistry.addRecipe(doorFull.copy(), new Object[]{"# #", "# #", "# #", Character.valueOf('#'), planks.copy()});
         }
      }

      this.addSqueezer(ILogType.VanillaLog.Spruce, ExtraTreeLiquid.Resin, 50);
   }

   public void addSqueezer(ILogType log, ILiquidType liquid, int amount, float pulpChance) {
      FluidStack liquidStack = liquid.get(amount);
      RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{log.getItemStack()}, liquidStack, Mods.Forestry.stack("woodPulp"), (int)(100.0F * pulpChance));
   }

   public void addSqueezer(ILogType log, ILiquidType liquid, int amount) {
      this.addSqueezer(log, liquid, amount, 0.5F);
   }
}
