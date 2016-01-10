package binnie.genetics.gui;

import binnie.core.util.UniqueFluidStackSet;
import binnie.core.util.UniqueItemStackSet;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.extratrees.machines.Brewery;
import binnie.extratrees.machines.Distillery;
import binnie.extratrees.machines.Press;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidContainerRegistry.FluidContainerData;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

public abstract class AnalystPageProduce extends ControlAnalystPage {
    public AnalystPageProduce(IWidget parent, IArea area) {
        super(parent, area);
    }

    protected Collection getAllProducts(ItemStack key) {
        Collection<ItemStack> products = new UniqueItemStackSet();
        products.addAll(this.getCentrifuge(key));
        products.addAll(this.getSqueezer(key));
        products.add(FurnaceRecipes.smelting().getSmeltingResult(key));
        products.addAll(this.getCrafting(key));
        return products;
    }

    public Collection getCentrifuge(ItemStack stack) {
        List<ItemStack> products = new ArrayList();

        for (Entry<Object[], Object[]> recipe : RecipeManagers.centrifugeManager.getRecipes().entrySet()) {
            boolean isRecipe = false;

            for (Object obj : (Object[]) recipe.getKey()) {
                if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
                    isRecipe = true;
                }
            }

            if (isRecipe) {
                for (Object obj : (Object[]) recipe.getValue()) {
                    if (obj instanceof ItemStack) {
                        products.add((ItemStack) obj);
                    }
                }
            }
        }

        return products;
    }

    public Collection getSqueezer(ItemStack stack) {
        List<ItemStack> products = new ArrayList();

        for (Entry<Object[], Object[]> recipe : RecipeManagers.squeezerManager.getRecipes().entrySet()) {
            boolean isRecipe = false;

            for (Object obj : (Object[]) recipe.getKey()) {
                if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
                    isRecipe = true;
                }
            }

            if (isRecipe) {
                for (Object obj : (Object[]) recipe.getValue()) {
                    if (obj instanceof ItemStack) {
                        products.add((ItemStack) obj);
                    }
                }
            }
        }

        return products;
    }

    public Collection getCrafting(ItemStack stack) {
        List<ItemStack> products = new ArrayList();

        for (Object recipeO : CraftingManager.getInstance().getRecipeList()) {
            if (recipeO instanceof ShapelessRecipes) {
                ShapelessRecipes recipe = (ShapelessRecipes) recipeO;
                boolean match = true;

                for (Object rec : recipe.recipeItems) {
                    if (rec != null && (!(rec instanceof ItemStack) || !stack.isItemEqual((ItemStack) rec))) {
                        match = false;
                    }
                }

                if (match) {
                    products.add(recipe.getRecipeOutput());
                }
            }

            if (recipeO instanceof ShapedRecipes) {
                ShapedRecipes recipe = (ShapedRecipes) recipeO;
                boolean match = true;

                for (Object rec : recipe.recipeItems) {
                    if (rec != null && (!(rec instanceof ItemStack) || !stack.isItemEqual((ItemStack) rec))) {
                        match = false;
                    }
                }

                if (match) {
                    products.add(recipe.getRecipeOutput());
                }
            }

            if (recipeO instanceof ShapelessOreRecipe) {
                ShapelessOreRecipe recipe = (ShapelessOreRecipe) recipeO;
                boolean match = true;

                for (Object rec : recipe.getInput()) {
                    if (rec != null && (!(rec instanceof ItemStack) || !stack.isItemEqual((ItemStack) rec))) {
                        match = false;
                    }
                }

                if (match) {
                    products.add(recipe.getRecipeOutput());
                }
            }
        }

        return products;
    }

    public Collection getAllFluids(ItemStack stack) {
        List<FluidStack> products = new ArrayList();
        products.addAll(this.getSqueezerFluid(stack));
        if (Press.getOutput(stack) != null) {
            products.add(Press.getOutput(stack));
        }

        return products;
    }

    public Collection getSqueezerFluid(ItemStack stack) {
        List<FluidStack> products = new ArrayList();

        for (Entry<Object[], Object[]> recipe : RecipeManagers.squeezerManager.getRecipes().entrySet()) {
            boolean isRecipe = false;

            for (Object obj : (Object[]) recipe.getKey()) {
                if (obj instanceof ItemStack && stack.isItemEqual((ItemStack) obj)) {
                    isRecipe = true;
                }
            }

            if (isRecipe) {
                for (Object obj : (Object[]) recipe.getValue()) {
                    if (obj instanceof FluidStack) {
                        products.add((FluidStack) obj);
                    }
                }
            }
        }

        return products;
    }

    protected Collection getAllProducts(FluidStack stack) {
        Collection<FluidStack> fluids = new UniqueFluidStackSet();
        fluids.add(Brewery.getOutput(stack));
        fluids.add(Distillery.getOutput(stack, 0));
        fluids.add(Distillery.getOutput(stack, 1));
        fluids.add(Distillery.getOutput(stack, 2));
        return fluids;
    }

    protected Collection getAllProductsAndFluids(Collection collection) {
        Collection<ItemStack> products = new UniqueItemStackSet();

        for (ItemStack stack : collection) {
            products.addAll(this.getAllProducts(stack));
        }

        Collection<ItemStack> products2 = new UniqueItemStackSet();

        for (ItemStack stack : products) {
            products2.addAll(this.getAllProducts(stack));
        }

        Collection<ItemStack> products3 = new UniqueItemStackSet();

        for (ItemStack stack : products2) {
            products3.addAll(this.getAllProducts(stack));
        }

        products.addAll(products2);
        products.addAll(products3);
        Collection<FluidStack> allFluids = new UniqueFluidStackSet();

        for (ItemStack stack : collection) {
            allFluids.addAll(this.getAllFluids(stack));
        }

        Collection<FluidStack> fluids2 = new UniqueFluidStackSet();

        for (FluidStack stack : allFluids) {
            fluids2.addAll(this.getAllProducts(stack));
        }

        Collection<FluidStack> fluids3 = new UniqueFluidStackSet();

        for (FluidStack stack : fluids2) {
            fluids3.addAll(this.getAllProducts(stack));
        }

        allFluids.addAll(fluids2);
        allFluids.addAll(fluids3);

        for (FluidStack fluid : allFluids) {
            ItemStack container = null;

            for (FluidContainerData data : FluidContainerRegistry.getRegisteredFluidContainerData()) {
                if (data.emptyContainer.isItemEqual(new ItemStack(Items.glass_bottle)) && data.fluid.isFluidEqual(fluid)) {
                    container = data.filledContainer;
                    break;
                }

                if (data.emptyContainer.isItemEqual(new ItemStack(Items.bucket)) && data.fluid.isFluidEqual(fluid)) {
                    container = data.filledContainer;
                    break;
                }

                if (data.fluid.isFluidEqual(fluid)) {
                    container = data.filledContainer;
                    break;
                }
            }

            if (container != null) {
                products.add(container);
            }
        }

        return products;
    }

    protected int getRefined(String string, int y, Collection products) {
        (new ControlTextCentered(this, (float) y, string)).setColour(this.getColour());
        y = y + 10;
        int maxBiomePerLine = (int) ((this.w() + 2.0F - 16.0F) / 18.0F);
        float biomeListX = (this.w() - (float) (Math.min(maxBiomePerLine, products.size()) * 18 - 2)) / 2.0F;
        int dx = 0;
        int dy = 0;

        for (ItemStack soilStack : products) {
            if (dx >= 18 * maxBiomePerLine) {
                dx = 0;
                dy += 18;
            }

            FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(soilStack);
            soilStack.stackSize = 1;
            ControlItemDisplay display = new ControlItemDisplay(this, biomeListX + (float) dx, (float) (y + dy), soilStack, fluid == null);
            if (fluid != null) {
                display.addTooltip(fluid.getLocalizedName());
            }

            dx += 18;
        }

        if (dx != 0) {
            dy += 18;
        }

        y = y + dy;
        return y;
    }
}
