package binnie.extratrees.alcohol;

import binnie.extratrees.alcohol.ICocktailIngredient;
import net.minecraftforge.fluids.FluidStack;

public interface ICocktailLiquid extends ICocktailIngredient {
   FluidStack get(int var1);
}
