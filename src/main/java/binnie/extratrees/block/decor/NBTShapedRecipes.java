package binnie.extratrees.block.decor;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class NBTShapedRecipes implements IRecipe {
    static List<NBTShapedRecipe> recipes;

    public boolean matches(final InventoryCrafting inventory, final World world) {
        for (final NBTShapedRecipe recipe : NBTShapedRecipes.recipes) {
            if (recipe.matches(inventory, world)) {
                return true;
            }
        }
        return false;
    }

    public ItemStack getCraftingResult(final InventoryCrafting inventory) {
        for (final NBTShapedRecipe recipe : NBTShapedRecipes.recipes) {
            if (recipe.matches(inventory, null)) {
                return recipe.getCraftingResult(inventory);
            }
        }
        return null;
    }

    public int getRecipeSize() {
        return 9;
    }

    public ItemStack getRecipeOutput() {
        return null;
    }

    public static void addRecipe(final NBTShapedRecipe nbtShapedRecipe) {
        NBTShapedRecipes.recipes.add(nbtShapedRecipe);
    }

    static {
        NBTShapedRecipes.recipes = new ArrayList<NBTShapedRecipe>();
    }
}
