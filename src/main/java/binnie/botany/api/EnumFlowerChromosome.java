package binnie.botany.api;

import forestry.api.genetics.*;

public enum EnumFlowerChromosome implements IChromosomeType {
    SPECIES(IAlleleFlowerSpecies.class),
    PRIMARY(IAlleleInteger.class),
    SECONDARY(IAlleleInteger.class),
    FERTILITY(IAlleleInteger.class),
    TERRITORY(IAlleleArea.class),
    EFFECT(IAlleleFlowerEffect.class),
    LIFESPAN(IAlleleInteger.class),
    TEMPERATURE_TOLERANCE(IAlleleTolerance.class),
    HUMIDITY_TOLERANCE(IAlleleTolerance.class),
    PH_TOLERANCE(IAlleleTolerance.class),
    SAPPINESS(IAlleleFloat.class),
    STEM(IAlleleInteger.class);

    private Class cls;

    private EnumFlowerChromosome(Class cls) {
        this.cls = cls;
    }

    public Class getAlleleClass() {
        return this.cls;
    }

    public String getName() {
        return this.toString().toLowerCase();
    }

    public ISpeciesRoot getSpeciesRoot() {
        return AlleleManager.alleleRegistry.getSpeciesRoot("rootFlowers");
    }
}
