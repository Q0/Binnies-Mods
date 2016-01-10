package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.core.util.UniqueItemStackSet;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.Lumbermill;
import com.mojang.authlib.GameProfile;
import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleFruit;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.IArboristTracker;
import forestry.api.arboriculture.IFruitProvider;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.ITreeMutation;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleInteger;
import forestry.api.genetics.IAllelePlantType;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IBreedingTracker;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IFruitFamily;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.ISpeciesRoot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

public class TreeBreedingSystem extends BreedingSystem {
   public UniqueItemStackSet allFruits = new UniqueItemStackSet();
   public UniqueItemStackSet allWoods = new UniqueItemStackSet();
   private UniqueItemStackSet discoveredFruits = new UniqueItemStackSet();
   private UniqueItemStackSet discoveredWoods = new UniqueItemStackSet();
   public UniqueItemStackSet discoveredPlanks = new UniqueItemStackSet();

   public TreeBreedingSystem() {
      super();
      this.iconUndiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/undiscoveredTree");
      this.iconDiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/discoveredTree");
   }

   public float getChance(IMutation mutation, EntityPlayer player, IAllele species1, IAllele species2) {
      IGenome genome0 = this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(species1.getUID()));
      IGenome genome1 = this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(species2.getUID()));
      return ((ITreeMutation)mutation).getChance(player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ, species1, species2, genome0, genome1);
   }

   public ISpeciesRoot getSpeciesRoot() {
      return Binnie.Genetics.getTreeRoot();
   }

   public int getColour() {
      return '켎';
   }

   public Class getTrackerClass() {
      return IArboristTracker.class;
   }

   public String getAlleleName(IChromosomeType chromosome, IAllele allele) {
      if(chromosome == EnumTreeChromosome.GIRTH) {
         return ((IAlleleInteger)allele).getValue() + "x" + ((IAlleleInteger)allele).getValue();
      } else if(chromosome == EnumTreeChromosome.PLANT) {
         EnumSet<EnumPlantType> types = ((IAllelePlantType)allele).getPlantTypes();
         return types.isEmpty()?Binnie.Language.localise(BinnieCore.instance, "allele.none"):((EnumPlantType)types.iterator().next()).toString();
      } else if(chromosome == EnumTreeChromosome.FRUITS && allele.getUID().contains(".")) {
         IFruitProvider provider = ((IAlleleFruit)allele).getProvider();
         return provider.getProducts().length == 0?Binnie.Language.localise(BinnieCore.instance, "allele.none"):provider.getProducts()[0].getDisplayName();
      } else {
         if(chromosome == EnumTreeChromosome.GROWTH) {
            if(allele.getUID().contains("Tropical")) {
               return Binnie.Language.localise(BinnieCore.instance, "allele.growth.tropical");
            }

            if(allele.getUID().contains("Lightlevel")) {
               return Binnie.Language.localise(BinnieCore.instance, "allele.growth.lightlevel");
            }
         }

         return super.getAlleleName(chromosome, allele);
      }
   }

   public void onSyncBreedingTracker(IBreedingTracker tracker) {
      this.discoveredFruits.clear();
      this.discoveredWoods.clear();

      for(IAlleleSpecies species : this.getDiscoveredSpecies(tracker)) {
         IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies)species;
         ITreeGenome genome = (ITreeGenome)this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));

         for(ItemStack wood : tSpecies.getLogStacks()) {
            this.discoveredWoods.add(wood);
         }

         for(ItemStack fruit : genome.getFruitProvider().getProducts()) {
            this.discoveredFruits.add(fruit);
         }

         for(ItemStack wood : this.discoveredWoods) {
            ;
         }
      }

   }

   public final void calculateArrays() {
      super.calculateArrays();

      for(IAlleleSpecies species : this.allActiveSpecies) {
         IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies)species;
         ITreeGenome genome = (ITreeGenome)this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));

         for(ItemStack wood : tSpecies.getLogStacks()) {
            this.allWoods.add(wood);
         }

         for(ItemStack fruit : genome.getFruitProvider().getProducts()) {
            this.allFruits.add(fruit);
         }
      }

   }

   public Collection getTreesThatBearFruit(ItemStack fruit, boolean nei, World world, GameProfile player) {
      Collection<IAlleleSpecies> set = nei?this.getAllSpecies():this.getDiscoveredSpecies(world, player);
      List<IAlleleSpecies> found = new ArrayList();

      for(IAlleleSpecies species : set) {
         IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies)species;
         ITreeGenome genome = (ITreeGenome)this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));

         for(ItemStack fruit2 : genome.getFruitProvider().getProducts()) {
            if(fruit2.isItemEqual(fruit)) {
               found.add(species);
            }
         }
      }

      return found;
   }

   public Collection getTreesThatCanBearFruit(ItemStack fruit, boolean nei, World world, GameProfile player) {
      Collection<IAlleleSpecies> set = nei?this.getAllSpecies():this.getDiscoveredSpecies(world, player);
      List<IAlleleSpecies> found = new ArrayList();
      Set<IFruitFamily> providers = new HashSet();

      for(IAlleleSpecies species : set) {
         IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies)species;
         ITreeGenome genome = (ITreeGenome)this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));

         for(ItemStack fruit2 : genome.getFruitProvider().getProducts()) {
            if(fruit2.isItemEqual(fruit)) {
               providers.add(genome.getFruitProvider().getFamily());
            }
         }
      }

      label199:
      for(IAlleleSpecies species : set) {
         IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies)species;
         Iterator i$ = providers.iterator();

         while(true) {
            if(!i$.hasNext()) {
               continue label199;
            }

            IFruitFamily family = (IFruitFamily)i$.next();
            if(tSpecies.getSuitableFruit().contains(family)) {
               break;
            }
         }

         found.add(species);
      }

      return found;
   }

   public Collection getTreesThatHaveWood(ItemStack fruit, boolean nei, World world, GameProfile player) {
      Collection<IAlleleSpecies> set = nei?this.getAllSpecies():this.getDiscoveredSpecies(world, player);
      List<IAlleleSpecies> found = new ArrayList();

      for(IAlleleSpecies species : set) {
         IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies)species;

         for(ItemStack fruit2 : tSpecies.getLogStacks()) {
            if(fruit2.isItemEqual(fruit)) {
               found.add(species);
            }
         }
      }

      return found;
   }

   public Collection getTreesThatMakePlanks(ItemStack fruit, boolean nei, World world, GameProfile player) {
      if(fruit == null) {
         return new ArrayList();
      } else {
         Collection<IAlleleSpecies> set = nei?this.getAllSpecies():this.getDiscoveredSpecies(world, player);
         List<IAlleleSpecies> found = new ArrayList();

         for(IAlleleSpecies species : set) {
            IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies)species;

            for(ItemStack fruit2 : tSpecies.getLogStacks()) {
               if(Lumbermill.getPlankProduct(fruit2) != null && fruit.isItemEqual(Lumbermill.getPlankProduct(fruit2))) {
                  found.add(species);
               }
            }
         }

         return found;
      }
   }

   public boolean isDNAManipulable(ItemStack member) {
      return ((ITreeRoot)this.getSpeciesRoot()).getType(member) == EnumGermlingType.POLLEN;
   }

   public IIndividual getConversion(ItemStack stack) {
      if(stack == null) {
         return null;
      } else {
         for(Entry<ItemStack, IIndividual> entry : AlleleManager.ersatzSaplings.entrySet()) {
            if(ItemStack.areItemStacksEqual(stack, (ItemStack)entry.getKey())) {
               return (IIndividual)entry.getValue();
            }
         }

         return null;
      }
   }

   public int[] getActiveTypes() {
      return new int[]{EnumGermlingType.SAPLING.ordinal(), EnumGermlingType.POLLEN.ordinal()};
   }

   public void addExtraAlleles(IChromosomeType chromosome, TreeSet alleles) {
      switch((EnumTreeChromosome)chromosome) {
      case FERTILITY:
         for(ForestryAllele.Saplings a : ForestryAllele.Saplings.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case GIRTH:
         for(ForestryAllele.Int a : ForestryAllele.Int.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case HEIGHT:
         for(ForestryAllele.TreeHeight a : ForestryAllele.TreeHeight.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case MATURATION:
         for(ForestryAllele.Maturation a : ForestryAllele.Maturation.values()) {
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

         return;
      case YIELD:
         for(ForestryAllele.Yield a : ForestryAllele.Yield.values()) {
            alleles.add(a.getAllele());
         }

         return;
      case FIREPROOF:
         for(ForestryAllele.Bool a : ForestryAllele.Bool.values()) {
            alleles.add(a.getAllele());
         }
      }

   }
}
