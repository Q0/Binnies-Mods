package binnie.extratrees.machines;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.item.ExtraTreeItems;
import binnie.extratrees.machines.ExtraTreeMachine;
import binnie.extratrees.machines.TileEntityNursery;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.core.Tabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModuleMachine implements IInitializable {
   public ModuleMachine() {
      super();
   }

   public void preInit() {
      MachineGroup machineGroup = new MachineGroup(ExtraTrees.instance, "machine", "machine", ExtraTreeMachine.values());
      machineGroup.setCreativeTab(Tabs.tabArboriculture);
      ExtraTrees.blockMachine = machineGroup.getBlock();
      BinnieCore.proxy.registerTileEntity(TileEntityNursery.class, "binnie.tile.nursery", BinnieCore.proxy.createObject("binnie.core.machines.RendererMachine"));
   }

   public void init() {
   }

   public void postInit() {
      GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Lumbermill.get(1), new Object[]{"gAg", "GsG", "gPg", Character.valueOf('G'), Blocks.glass, Character.valueOf('g'), ExtraTreeItems.ProvenGear.get(1), Character.valueOf('A'), Items.iron_axe, Character.valueOf('s'), Mods.Forestry.stack("sturdyMachine"), Character.valueOf('P'), "gearBronze"}));
      GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Press.get(1), new Object[]{"iGi", "tSt", "tPt", Character.valueOf('i'), "ingotIron", Character.valueOf('G'), Blocks.glass, Character.valueOf('t'), "ingotTin", Character.valueOf('S'), Mods.Forestry.stack("sturdyMachine"), Character.valueOf('P'), "gearBronze"}));
      GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Brewery.get(1), new Object[]{"bGb", "iSi", "bPb", Character.valueOf('i'), "ingotIron", Character.valueOf('G'), Blocks.glass, Character.valueOf('b'), "gearBronze", Character.valueOf('S'), Mods.Forestry.stack("sturdyMachine"), Character.valueOf('P'), "gearBronze"}));
      GameRegistry.addRecipe(new ShapedOreRecipe(ExtraTreeMachine.Distillery.get(1), new Object[]{"rGr", "iSi", "rPr", Character.valueOf('i'), "ingotIron", Character.valueOf('G'), Blocks.glass, Character.valueOf('r'), "dustRedstone", Character.valueOf('S'), Mods.Forestry.stack("sturdyMachine"), Character.valueOf('P'), "gearBronze"}));
      CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeMachine.Woodworker.get(1), new Object[]{"wGw", "GsG", "ggg", Character.valueOf('G'), Blocks.glass, Character.valueOf('g'), ExtraTreeItems.ProvenGear.get(1), Character.valueOf('w'), Blocks.planks, Character.valueOf('s'), Mods.Forestry.stack("impregnatedCasing")}));
      CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeMachine.Panelworker.get(1), new Object[]{"wGw", "GsG", "ggg", Character.valueOf('G'), Blocks.glass, Character.valueOf('g'), ExtraTreeItems.ProvenGear.get(1), Character.valueOf('w'), Blocks.wooden_slab, Character.valueOf('s'), Mods.Forestry.stack("impregnatedCasing")}));
      CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeMachine.Glassworker.get(1), new Object[]{"wGw", "GsG", "ggg", Character.valueOf('G'), Blocks.glass, Character.valueOf('g'), ExtraTreeItems.ProvenGear.get(1), Character.valueOf('w'), Blocks.glass, Character.valueOf('s'), Mods.Forestry.stack("impregnatedCasing")}));
      CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(ExtraTreeMachine.Tileworker.get(1), new Object[]{"wGw", "GsG", "ggg", Character.valueOf('G'), Blocks.glass, Character.valueOf('g'), ExtraTreeItems.ProvenGear.get(1), Character.valueOf('w'), Items.clay_ball, Character.valueOf('s'), Mods.Forestry.stack("impregnatedCasing")}));
   }
}
