package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import forestry.api.genetics.*;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.EnumFlutterType;
import forestry.api.lepidopterology.IButterflyRoot;
import forestry.api.lepidopterology.ILepidopteristTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.TreeSet;

class MothBreedingSystem extends BreedingSystem {
    public MothBreedingSystem() {
        super();
        this.iconUndiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/undiscoveredMoth");
        this.iconDiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/discoveredMoth");
    }

    public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
        return 0.0F;
    }

    public ISpeciesRoot getSpeciesRoot() {
        return Binnie.Genetics.getButterflyRoot();
    }

    public int getColour() {
        return '\uf2f2';
    }

    public Class getTrackerClass() {
        return ILepidopteristTracker.class;
    }

    public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
        if (chromosome == EnumButterflyChromosome.METABOLISM) {
            int metabolism = ((IAlleleInteger) allele).getValue();
            return metabolism >= 19 ? Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.highest") : (metabolism >= 16 ? Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.higher") : (metabolism >= 13 ? Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.high") : (metabolism >= 10 ? Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.normal") : (metabolism >= 7 ? Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.slow") : (metabolism >= 4 ? Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.slower") : Binnie.Language.localise(BinnieCore.instance, "allele.metabolism.slowest"))))));
        } else if (chromosome == EnumButterflyChromosome.FERTILITY) {
            int metabolism = ((IAlleleInteger) allele).getValue();
            return metabolism + "x";
        } else {
            return super.getAlleleName(chromosome, allele);
        }
    }

    public boolean isDNAManipulable(ItemStack member) {
        return ((IButterflyRoot) this.getSpeciesRoot()).getType(member) == EnumFlutterType.SERUM;
    }

    public int[] getActiveTypes() {
        return new int[]{EnumFlutterType.BUTTERFLY.ordinal(), EnumFlutterType.CATERPILLAR.ordinal(), EnumFlutterType.SERUM.ordinal()};
    }

    public void addExtraAlleles(IChromosomeType chromosome, TreeSet alleles) {
        switch ((EnumButterflyChromosome) chromosome) {
            case FERTILITY:
                for (ForestryAllele.Int a : ForestryAllele.Int.values()) {
                    alleles.add(a.getAllele());
                }

                return;
            case LIFESPAN:
                for (ForestryAllele.Lifespan a : ForestryAllele.Lifespan.values()) {
                    alleles.add(a.getAllele());
                }

                return;
            case METABOLISM:
                for (ForestryAllele.Int a : ForestryAllele.Int.values()) {
                    alleles.add(a.getAllele());
                }

                return;
            case TOLERANT_FLYER:
            case FIRE_RESIST:
            case NOCTURNAL:
                for (ForestryAllele.Bool a : ForestryAllele.Bool.values()) {
                    alleles.add(a.getAllele());
                }

                return;
            case SIZE:
                for (ForestryAllele.Size a : ForestryAllele.Size.values()) {
                    alleles.add(a.getAllele());
                }

                return;
            case SPEED:
                for (ForestryAllele.Speed a : ForestryAllele.Speed.values()) {
                    alleles.add(a.getAllele());
                }

                return;
            case HUMIDITY_TOLERANCE:
            case TEMPERATURE_TOLERANCE:
                for (Tolerance a : Tolerance.values()) {
                    alleles.add(a.getAllele());
                }
        }

    }
}
