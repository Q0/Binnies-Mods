package binnie.genetics.api;

import net.minecraft.item.ItemStack;

public interface IItemAnalysable {
    boolean isAnalysed(ItemStack var1);

    ItemStack analyse(ItemStack var1);

    /**
     * @deprecated
     */
    @Deprecated
    float getAnalyseTimeMult(ItemStack var1);
}
