package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.core.genetics.Tolerance;
import binnie.core.genetics.VirtualBeeHousing;
import binnie.extrabees.ExtraBees;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.EnumBeeType;
import forestry.api.apiculture.IApiaristTracker;
import forestry.api.apiculture.IBeeMutation;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import java.util.TreeSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

class BeeBreedingSystem extends BreedingSystem {
   public BeeBreedingSystem() {
      super();
      this.iconUndiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/undiscoveredBee");
      this.iconDiscovered = Binnie.Resource.getItemIcon(ExtraBees.instance, "icon/discoveredBee");
   }

   public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
      return ((IBeeMutation)mutation).getChance(new VirtualBeeHousing(player), species1, species2, this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(species1.getUID())), this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(species2.getUID())));
   }

   public ISpeciesRoot getSpeciesRoot() {
      return Binnie.Genetics.getBeeRoot();
   }

   public int getColour() {
      return 16767232;
   }

   public Class getTrackerClass() {
      return IApiaristTracker.class;
   }

   public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
      if(chromosome == EnumBeeChromosome.FERTILITY) {
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
      return ((IBeeRoot)this.getSpeciesRoot()).getType(member) == EnumBeeType.LARVAE;
   }

   public int[] getActiveTypes() {
      return new int[]{EnumBeeType.DRONE.ordinal(), EnumBeeType.PRINCESS.ordinal(), EnumBeeType.QUEEN.ordinal(), EnumBeeType.LARVAE.ordinal()};
   }

   public void addExtraAlleles(IChromosomeType chromosome, TreeSet alleles) {
      switch((EnumBeeChromosome)chromosome) {
      case FERTILITY:
         for(ForestryAllele.Fertility a : ForestryAllele.Fertility.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case FLOWERING:
         for(ForestryAllele.Flowering a : ForestryAllele.Flowering.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case HUMIDITY_TOLERANCE:
      case TEMPERATURE_TOLERANCE:
         for(Tolerance a : Tolerance.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case LIFESPAN:
         for(ForestryAllele.Lifespan a : ForestryAllele.Lifespan.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case SPEED:
         for(ForestryAllele.Speed a : ForestryAllele.Speed.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case TERRITORY:
         for(ForestryAllele.Territory a : ForestryAllele.Territory.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case NOCTURNAL:
      case CAVE_DWELLING:
      case TOLERANT_FLYER:
         for(ForestryAllele.Bool a : ForestryAllele.Bool.values()) {
            alleles.add(a.getAllele());
         }
      }

   }
}
