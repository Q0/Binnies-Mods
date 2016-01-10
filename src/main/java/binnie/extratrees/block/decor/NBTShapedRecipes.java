package binnie.extratrees.block.decor;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class NBTShapedRecipes implements IRecipe {
    static List recipes = new ArrayList();

    public NBTShapedRecipes() {
        super();
    }

    public boolean matches(InventoryCrafting inventory, World world) {
        for (NBTShapedRecipe recipe : recipes) {
            if (recipe.matches(inventory, world)) {
                return true;
            }
        }

        return false;
    }

    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        for (NBTShapedRecipe recipe : recipes) {
            if (recipe.matches(inventory, (World) null)) {
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

    public static void addRecipe(NBTShapedRecipe nbtShapedRecipe) {
        recipes.add(nbtShapedRecipe);
    }
}
