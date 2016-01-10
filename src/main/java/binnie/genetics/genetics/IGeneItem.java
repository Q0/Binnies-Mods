package binnie.genetics.genetics;

import binnie.genetics.api.IGene;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IGeneItem {
    ISpeciesRoot getSpeciesRoot();

    void getInfo(List var1);

    int getColour(int var1);

    void writeToItem(ItemStack var1);

    void addGene(IGene var1);
}
