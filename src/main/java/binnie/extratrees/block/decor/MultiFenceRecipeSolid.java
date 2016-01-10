package binnie.extratrees.block.decor;

import binnie.extratrees.block.WoodManager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class MultiFenceRecipeSolid implements IRecipe {
    ItemStack cached;

    public MultiFenceRecipeSolid() {
        super();
    }

    public boolean matches(InventoryCrafting inv, World world) {
        String pattern = "";
        FenceType type = null;

        for (int row = 0; row < 3; ++row) {
            ItemStack a = inv.getStackInSlot(row * 3);
            ItemStack b = inv.getStackInSlot(row * 3 + 1);
            ItemStack c = inv.getStackInSlot(row * 3 + 2);
            if (a != null && b != null && c != null) {
                type = WoodManager.getFenceType(a);
                FenceType type2 = WoodManager.getFenceType(b);
                FenceType type3 = WoodManager.getFenceType(c);
                if (type != null && type2 != null && type3 != null && type.equals(type2) && type.equals(type3)) {
                    this.cached = WoodManager.getFence(WoodManager.getFenceDescription(a).getPlankType(), WoodManager.getFenceDescription(a).getSecondaryPlankType(), new FenceType(type.size, true, type.solid), 2);
                    return true;
                }
            }
        }

        return false;
    }

    public ItemStack getCraftingResult(InventoryCrafting inv) {
        return this.getRecipeOutput();
    }

    public int getRecipeSize() {
        return 3;
    }

    public ItemStack getRecipeOutput() {
        return this.cached == null ? new ItemStack(Blocks.fence) : this.cached;
    }
}
