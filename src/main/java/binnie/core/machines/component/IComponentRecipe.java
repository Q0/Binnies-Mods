package binnie.core.machines.component;

import net.minecraft.item.ItemStack;

public interface IComponentRecipe {
    boolean isRecipe();

    ItemStack doRecipe(boolean var1);

    ItemStack getProduct();
}
