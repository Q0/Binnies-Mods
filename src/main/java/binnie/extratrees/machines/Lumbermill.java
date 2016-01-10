package binnie.extratrees.machines;

import binnie.core.item.ItemMisc;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import binnie.extratrees.item.ExtraTreeItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.*;
import java.util.Map.Entry;

public class Lumbermill {
    public static int slotWood = 0;
    public static int slotPlanks = 1;
    public static int slotBark = 2;
    public static int slotSawdust = 3;
    public static int tankWater = 0;
    static Map recipes = new HashMap();

    public Lumbermill() {
        super();
    }

    public static ItemStack getPlankProduct(ItemStack item) {
        ItemStack stack = null;
        if (recipes.isEmpty()) {
            calculateLumbermillProducts();
        }

        for (Entry<ItemStack, ItemStack> entry : recipes.entrySet()) {
            if (((ItemStack) entry.getKey()).isItemEqual(item)) {
                stack = ((ItemStack) entry.getValue()).copy();
                break;
            }
        }

        stack.stackSize = 6;
        return stack;
    }

    private static void calculateLumbermillProducts() {
        for (IPlankType type : WoodManager.getAllPlankTypes()) {
            for (ItemStack wood : getRecipeResult(type.getStack())) {
                recipes.put(wood, type.getStack());
            }
        }

    }

    private static Collection getRecipeResult(ItemStack output) {
        List<ItemStack> list = new ArrayList();
        Iterator i$ = CraftingManager.getInstance().getRecipeList().iterator();

        while (true) {
            Object recipeO;
            while (true) {
                while (true) {
                    if (!i$.hasNext()) {
                        return list;
                    }

                    recipeO = i$.next();
                    if (!(recipeO instanceof ShapelessRecipes)) {
                        break;
                    }

                    ShapelessRecipes recipe = (ShapelessRecipes) recipeO;
                    if (recipe.recipeItems.size() == 1 && recipe.recipeItems.get(0) instanceof ItemStack) {
                        ItemStack input = (ItemStack) recipe.recipeItems.get(0);
                        if (recipe.getRecipeOutput() != null && recipe.getRecipeOutput().isItemEqual(output)) {
                            list.add(input);
                        }
                        break;
                    }
                }

                if (!(recipeO instanceof ShapedRecipes)) {
                    break;
                }

                ShapedRecipes recipe = (ShapedRecipes) recipeO;
                if (recipe.recipeItems.length == 1) {
                    ItemStack input = recipe.recipeItems[0];
                    if (recipe.getRecipeOutput() != null && recipe.getRecipeOutput().isItemEqual(output)) {
                        list.add(input);
                    }
                    break;
                }
            }

            if (recipeO instanceof ShapelessOreRecipe) {
                ShapelessOreRecipe recipe = (ShapelessOreRecipe) recipeO;
                if (recipe.getInput().size() == 1 && recipe.getInput().get(0) instanceof ItemStack) {
                    ItemStack input = (ItemStack) recipe.getInput().get(0);
                    if (recipe.getRecipeOutput() != null && recipe.getRecipeOutput().isItemEqual(output)) {
                        list.add(input);
                    }
                }
            }
        }
    }

    public static class ComponentLumbermillLogic extends ComponentProcessSetCost implements IProcess {
        public ComponentLumbermillLogic(Machine machine) {
            super(machine, 900, 30);
        }

        public ErrorState canWork() {
            if (this.getUtil().isSlotEmpty(Lumbermill.slotWood)) {
                return new ErrorState.NoItem("No Wood", Lumbermill.slotWood);
            } else {
                ItemStack result = Lumbermill.getPlankProduct(this.getUtil().getStack(Lumbermill.slotWood));
                if (!this.getUtil().isSlotEmpty(Lumbermill.slotPlanks) && result != null) {
                    ItemStack currentPlank = this.getUtil().getStack(Lumbermill.slotPlanks);
                    if (!result.isItemEqual(currentPlank) || result.stackSize + currentPlank.stackSize > currentPlank.getMaxStackSize()) {
                        return new ErrorState.NoSpace("No room for new planks", new int[]{Lumbermill.slotPlanks});
                    }
                }

                return super.canWork();
            }
        }

        public ErrorState canProgress() {
            return (ErrorState) (!this.getUtil().liquidInTank(Lumbermill.tankWater, 5) ? new ErrorState.InsufficientLiquid("Not Enough Water", Lumbermill.tankWater) : super.canProgress());
        }

        protected void onFinishTask() {
            ItemStack result = Lumbermill.getPlankProduct(this.getUtil().getStack(Lumbermill.slotWood));
            if (result != null) {
                this.getUtil().addStack(Lumbermill.slotPlanks, result);
                this.getUtil().addStack(Lumbermill.slotSawdust, ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.Sawdust, 1));
                this.getUtil().addStack(Lumbermill.slotBark, ((ItemMisc) ExtraTrees.itemMisc).getStack(ExtraTreeItems.Bark, 1));
                this.getUtil().decreaseStack(Lumbermill.slotWood, 1);
            }
        }

        protected void onTickTask() {
            this.getUtil().drainTank(Lumbermill.tankWater, 10);
        }
    }

    public static class PackageLumbermill extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
        public PackageLumbermill() {
            super("lumbermill", ExtraTreeTexture.lumbermillTexture, true);
        }

        public void createMachine(Machine machine) {
            new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Lumbermill);
            ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(Lumbermill.slotWood, "input");
            inventory.getSlot(Lumbermill.slotWood).setValidator(new Lumbermill.SlotValidatorLog());
            inventory.getSlot(Lumbermill.slotWood).forbidExtraction();
            inventory.addSlot(Lumbermill.slotPlanks, "output");
            inventory.addSlot(Lumbermill.slotBark, "byproduct");
            inventory.addSlot(Lumbermill.slotSawdust, "byproduct");
            inventory.getSlot(Lumbermill.slotPlanks).setReadOnly();
            inventory.getSlot(Lumbermill.slotBark).setReadOnly();
            inventory.getSlot(Lumbermill.slotSawdust).setReadOnly();
            ComponentTankContainer tanks = new ComponentTankContainer(machine);
            tanks.addTank(Lumbermill.tankWater, "input", 10000);
            tanks.getTankSlot(Lumbermill.tankWater).setValidator(new TankValidator.Basic("water"));
            new ComponentPowerReceptor(machine);
            new Lumbermill.ComponentLumbermillLogic(machine);
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }
    }

    public static class SlotValidatorLog extends SlotValidator {
        public SlotValidatorLog() {
            super(SlotValidator.IconBlock);
        }

        public boolean isValid(ItemStack itemStack) {
            String name = OreDictionary.getOreName(OreDictionary.getOreID(itemStack));
            return name.contains("logWood") && Lumbermill.getPlankProduct(itemStack) != null;
        }

        public String getTooltip() {
            return "Logs";
        }
    }
}
