package binnie.botany.api;

import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutation;

public interface IFlowerMutation extends IMutation {
   float getChance(IAllele var1, IAllele var2, IGenome var3, IGenome var4);
}
