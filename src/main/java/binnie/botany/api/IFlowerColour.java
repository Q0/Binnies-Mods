package binnie.botany.api;

import forestry.api.genetics.IAlleleInteger;

public interface IFlowerColour {
   int getColor(boolean var1);

   IAlleleInteger getAllele();

   int getID();

   String getName();
}
