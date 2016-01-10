package binnie.botany.api;

import binnie.botany.api.IAlleleFlowerEffect;
import binnie.botany.api.IAlleleFlowerSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAlleleArea;
import forestry.api.genetics.IAlleleFloat;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;

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
