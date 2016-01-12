package binnie.botany.api;

import forestry.api.genetics.*;

public enum EnumFlowerChromosome implements IChromosomeType {
    SPECIES((Class<? extends IAllele>) IAlleleFlowerSpecies.class),
    PRIMARY((Class<? extends IAllele>) IAlleleInteger.class),
    SECONDARY((Class<? extends IAllele>) IAlleleInteger.class),
    FERTILITY((Class<? extends IAllele>) IAlleleInteger.class),
    TERRITORY((Class<? extends IAllele>) IAlleleArea.class),
    EFFECT((Class<? extends IAllele>) IAlleleFlowerEffect.class),
    LIFESPAN((Class<? extends IAllele>) IAlleleInteger.class),
    TEMPERATURE_TOLERANCE((Class<? extends IAllele>) IAlleleTolerance.class),
    HUMIDITY_TOLERANCE((Class<? extends IAllele>) IAlleleTolerance.class),
    PH_TOLERANCE((Class<? extends IAllele>) IAlleleTolerance.class),
    SAPPINESS((Class<? extends IAllele>) IAlleleFloat.class),
    STEM((Class<? extends IAllele>) IAlleleInteger.class);

    private Class<? extends IAllele> cls;

    private EnumFlowerChromosome(final Class<? extends IAllele> cls) {
        this.cls = cls;
    }

    public Class<? extends IAllele> getAlleleClass() {
        return this.cls;
    }

    public String getName() {
        return this.toString().toLowerCase();
    }

    public ISpeciesRoot getSpeciesRoot() {
        return AlleleManager.alleleRegistry.getSpeciesRoot("rootFlowers");
    }
}
