package binnie.genetics.genetics;

import binnie.core.genetics.BreedingSystem;
import forestry.api.genetics.*;

import java.util.*;

public class GeneticEngineeringSystem {
    BreedingSystem breedingSystem;
    public Map chromosomeMap = new HashMap();
    static List speciesList;

    public GeneticEngineeringSystem(BreedingSystem system) {
        super();
        this.breedingSystem = system;

        for (IIndividual indiv : this.breedingSystem.getSpeciesRoot().getIndividualTemplates()) {
            for (IChromosomeType chromosome : this.breedingSystem.getSpeciesRoot().getKaryotype()) {
                if (!this.chromosomeMap.containsKey(chromosome)) {
                    this.chromosomeMap.put(chromosome, new ArrayList());
                }

                try {
                    IAllele a1 = indiv.getGenome().getActiveAllele(chromosome);
                    IAllele a2 = indiv.getGenome().getInactiveAllele(chromosome);
                    if (a1 != null) {
                        ((List) this.chromosomeMap.get(chromosome)).add(a1);
                    }

                    if (a2 != null) {
                        ((List) this.chromosomeMap.get(chromosome)).add(a2);
                    }
                } catch (Exception var10) {
                    ;
                }
            }
        }

        for (IChromosomeType chromosome : this.breedingSystem.getSpeciesRoot().getKaryotype()) {
            List<IAllele> alleles = this.getAlleles(chromosome);
            TreeSet<IAllele> set = new TreeSet(new GeneticEngineeringSystem.ComparatorAllele());
            set.addAll(alleles);
            List<IAllele> list = new ArrayList();
            list.addAll(set);
            this.chromosomeMap.put(chromosome, list);
        }

    }

    public List getAlleles(IChromosomeType chromosome) {
        return (List) this.chromosomeMap.get(chromosome);
    }

    public ISpeciesRoot getSpeciesRoot() {
        return this.breedingSystem.getSpeciesRoot();
    }

    class ComparatorAllele implements Comparator {
        ComparatorAllele() {
            super();
        }

        public int compare(IAllele o1, IAllele o2) {
            return o1 instanceof IAlleleFloat && o2 instanceof IAlleleFloat ? Float.valueOf(((IAlleleFloat) o1).getValue()).compareTo(Float.valueOf(((IAlleleFloat) o2).getValue())) : (o1 instanceof IAlleleInteger && o2 instanceof IAlleleInteger ? Integer.valueOf(((IAlleleInteger) o1).getValue()).compareTo(Integer.valueOf(((IAlleleInteger) o2).getValue())) : o1.getUID().compareTo(o2.getUID()));
        }
    }
}
