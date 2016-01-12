package binnie.botany.ceramic;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IFlower;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class PigmentRecipe implements IRecipe {
    private final ItemStack unknown;
    ItemStack cached;

    public PigmentRecipe() {
        this.unknown = null;
    }

    public boolean matches(final InventoryCrafting crafting, final World world) {
        return this.getCraftingResult(crafting) != null;
    }

    public ItemStack getRecipeOutput() {
        if (this.cached != null) {
            return this.cached;
        }
        return this.unknown;
    }

    public ItemStack getCraftingResult(final InventoryCrafting crafting) {
        int n = 0;
        ItemStack stack = null;
        for (int i = 0; i < crafting.getSizeInventory(); ++i) {
            if (crafting.getStackInSlot(i) != null) {
                if (++n > 1) {
                    return null;
                }
                if (Binnie.Genetics.getFlowerRoot().isMember(crafting.getStackInSlot(i))) {
                    final IFlower flower = Binnie.Genetics.getFlowerRoot().getMember(crafting.getStackInSlot(i));
                    if (flower.getAge() >= 1) {
                        stack = new ItemStack((Item) Botany.pigment, 1, flower.getGenome().getPrimaryColor().getID());
                    }
                }
            }
        }
        return stack;
    }

    public int getRecipeSize() {
        return 1;
    }
}
