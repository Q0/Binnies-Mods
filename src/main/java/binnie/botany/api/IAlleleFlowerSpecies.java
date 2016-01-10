package binnie.botany.api;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.IFlowerType;
import forestry.api.genetics.IAlleleSpecies;

public interface IAlleleFlowerSpecies extends IAlleleSpecies {
   IFlowerType getType();

   EnumAcidity getPH();

   EnumMoisture getMoisture();
}
