package binnie.core.genetics;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IBotanistTracker;
import binnie.botany.api.IFlowerMutation;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.core.genetics.Tolerance;
import binnie.extrabees.ExtraBees;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import java.util.TreeSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

class FlowerBreedingSystem extends BreedingSystem {
   public FlowerBreedingSystem() {
      super();
      this.iconUndiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/undiscoveredBee");
      this.iconDiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/discoveredBee");
   }

   public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
      return ((IFlowerMutation)mutation).getBaseChance();
   }

   public ISpeciesRoot getSpeciesRoot() {
      return Binnie.Genetics.getFlowerRoot();
   }

   public int getColour() {
      return 14563127;
   }

   public Class getTrackerClass() {
      return IBotanistTracker.class;
   }

   public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
      if(chromosome == EnumFlowerChromosome.FERTILITY) {
         if(allele.getUID().contains("Low")) {
            return Binnie.Language.localise(BinnieCore.instance, "allele.fertility.low");
         }

         if(allele.getUID().contains("Normal")) {
            return Binnie.Language.localise(BinnieCore.instance, "allele.fertility.normal");
         }

         if(allele.getUID().contains("High")) {
            return Binnie.Language.localise(BinnieCore.instance, "allele.fertility.high");
         }

         if(allele.getUID().contains("Maximum")) {
            return Binnie.Language.localise(BinnieCore.instance, "allele.fertility.maximum");
         }
      }

      return super.getAlleleName(chromosome, allele);
   }

   public boolean isDNAManipulable(ItemStack member) {
      return ((IFlowerRoot)this.getSpeciesRoot()).getType(member) == EnumFlowerStage.POLLEN;
   }

   public IIndividual getConversion(ItemStack stack) {
      return BotanyCore.getFlowerRoot().getConversion(stack);
   }

   public int[] getActiveTypes() {
      return new int[]{EnumFlowerStage.FLOWER.ordinal(), EnumFlowerStage.POLLEN.ordinal(), EnumFlowerStage.SEED.ordinal()};
   }

   public void addExtraAlleles(IChromosomeType chromosome, TreeSet alleles) {
      switch((EnumFlowerChromosome)chromosome) {
      case FERTILITY:
         for(ForestryAllele.Fertility a : ForestryAllele.Fertility.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case LIFESPAN:
         for(ForestryAllele.Lifespan a : ForestryAllele.Lifespan.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case HUMIDITY_TOLERANCE:
      case PH_TOLERANCE:
      case TEMPERATURE_TOLERANCE:
         for(Tolerance a : Tolerance.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case PRIMARY:
      case SECONDARY:
      case STEM:
         for(EnumFlowerColor a : EnumFlowerColor.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case SAPPINESS:
         for(ForestryAllele.Sappiness a : ForestryAllele.Sappiness.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case TERRITORY:
         for(ForestryAllele.Territory a : ForestryAllele.Territory.values()) {
            alleles.add(a.getAllele());
         }
      }

   }
}
