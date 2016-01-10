package binnie.botany.api;

import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IGenome;

public interface IFlowerGenome extends IGenome {
    IAlleleFlowerSpecies getPrimary();

    IAlleleFlowerSpecies getSecondary();

    IFlowerColour getPrimaryColor();

    IFlowerColour getSecondaryColor();

    IFlowerColour getStemColor();

    int getFertility();

    int getLifespan();

    IFlowerType getType();

    EnumTolerance getToleranceTemperature();

    EnumTolerance getToleranceMoisture();

    EnumTolerance getTolerancePH();

    float getAgeChance();

    float getSappiness();

    boolean canTolerate(EnumAcidity var1);

    boolean canTolerate(EnumMoisture var1);

    boolean canTolerate(EnumTemperature var1);
}
