package binnie.genetics.api;

import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemChargable;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;

public interface IItemSerum extends IItemChargable {
   IGene[] getGenes(ItemStack var1);

   ISpeciesRoot getSpeciesRoot(ItemStack var1);

   IGene getGene(ItemStack var1, int var2);

   ItemStack addGene(ItemStack var1, IGene var2);
}
