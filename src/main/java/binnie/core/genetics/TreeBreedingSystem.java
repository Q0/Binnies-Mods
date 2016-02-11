package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.util.UniqueItemStackSet;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.Lumbermill;
import com.mojang.authlib.GameProfile;
import forestry.api.arboriculture.*;
import forestry.api.genetics.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import java.util.*;

public class TreeBreedingSystem extends BreedingSystem {
    public UniqueItemStackSet allFruits;
    public UniqueItemStackSet allWoods;
    public UniqueItemStackSet discoveredPlanks;
    private UniqueItemStackSet discoveredFruits;
    private UniqueItemStackSet discoveredWoods;

    public TreeBreedingSystem() {
        this.allFruits = new UniqueItemStackSet();
        this.allWoods = new UniqueItemStackSet();
        this.discoveredFruits = new UniqueItemStackSet();
        this.discoveredWoods = new UniqueItemStackSet();
        this.discoveredPlanks = new UniqueItemStackSet();
        this.iconUndiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/undiscoveredTree");
        this.iconDiscovered = Binnie.Resource.getItemIcon(ExtraTrees.instance, "icon/discoveredTree");
    }

    @Override
    public float getChance(final IMutation mutation, final EntityPlayer player, final IAllele species1, final IAllele species2) {
        final IGenome genome0 = this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(species1.getUID()));
        final IGenome genome2 = this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(species2.getUID()));
        return ((ITreeMutation) mutation).getChance(player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ, species1, species2, genome0, genome2);
    }

    @Override
    public ISpeciesRoot getSpeciesRoot() {
        return Binnie.Genetics.getTreeRoot();
    }

    @Override
    public int getColour() {
        return 53006;
    }

    @Override
    public Class<? extends IBreedingTracker> getTrackerClass() {
        return IArboristTracker.class;
    }

    @Override
    public String getAlleleName(final IChromosomeType chromosome, final IAllele allele) {
        if (chromosome == EnumTreeChromosome.GIRTH) {
            return ((IAlleleInteger) allele).getValue() + "x" + ((IAlleleInteger) allele).getValue();
        }
        if (chromosome == EnumTreeChromosome.PLANT) {
            final EnumSet<EnumPlantType> types = ((IAllelePlantType) allele).getPlantTypes();
            return types.isEmpty() ? Binnie.Language.localise(BinnieCore.instance, "allele.none") : types.iterator().next().toString();
        }
        if (chromosome == EnumTreeChromosome.FRUITS && allele.getUID().contains(".")) {
            final IFruitProvider provider = ((IAlleleFruit) allele).getProvider();
            return (provider.getProducts().length == 0) ? Binnie.Language.localise(BinnieCore.instance, "allele.none") : provider.getProducts()[0].getDisplayName();
        }
        if (chromosome == EnumTreeChromosome.GROWTH) {
            if (allele.getUID().contains("Tropical")) {
                return Binnie.Language.localise(BinnieCore.instance, "allele.growth.tropical");
            }
            if (allele.getUID().contains("Lightlevel")) {
                return Binnie.Language.localise(BinnieCore.instance, "allele.growth.lightlevel");
            }
        }
        return super.getAlleleName(chromosome, allele);
    }

    @Override
    public void onSyncBreedingTracker(final IBreedingTracker tracker) {
        this.discoveredFruits.clear();
        this.discoveredWoods.clear();
        for (final IAlleleSpecies species : this.getDiscoveredSpecies(tracker)) {
            final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            final ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));
            for (final ItemStack wood : tSpecies.getLogStacks()) {
                this.discoveredWoods.add(wood);
            }
            for (final ItemStack fruit : genome.getFruitProvider().getProducts()) {
                this.discoveredFruits.add(fruit);
            }
            for (ItemStack wood2 : this.discoveredWoods) {
            }
        }
    }

    @Override
    public final void calculateArrays() {
        super.calculateArrays();
        for (final IAlleleSpecies species : this.allActiveSpecies) {
            final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            final ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));
            for (final ItemStack wood : tSpecies.getLogStacks()) {
                this.allWoods.add(wood);
            }
            for (final ItemStack fruit : genome.getFruitProvider().getProducts()) {
                this.allFruits.add(fruit);
            }
        }
    }

    public Collection<IAlleleSpecies> getTreesThatBearFruit(final ItemStack fruit, final boolean nei, final World world, final GameProfile player) {
        final Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
        final List<IAlleleSpecies> found = new ArrayList<IAlleleSpecies>();
        for (final IAlleleSpecies species : set) {
            final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            final ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));
            for (final ItemStack fruit2 : genome.getFruitProvider().getProducts()) {
                if (fruit2.isItemEqual(fruit)) {
                    found.add(species);
                }
            }
        }
        return found;
    }

    public Collection<IAlleleSpecies> getTreesThatCanBearFruit(final ItemStack fruit, final boolean nei, final World world, final GameProfile player) {
        final Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
        final List<IAlleleSpecies> found = new ArrayList<IAlleleSpecies>();
        final Set<IFruitFamily> providers = new HashSet<IFruitFamily>();
        for (final IAlleleSpecies species : set) {
            final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            final ITreeGenome genome = (ITreeGenome) this.getSpeciesRoot().templateAsGenome(this.getSpeciesRoot().getTemplate(tSpecies.getUID()));
            for (final ItemStack fruit2 : genome.getFruitProvider().getProducts()) {
                if (fruit2.isItemEqual(fruit)) {
                    providers.add(genome.getFruitProvider().getFamily());
                }
            }
        }
        for (final IAlleleSpecies species : set) {
            final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            for (final IFruitFamily family : providers) {
                if (tSpecies.getSuitableFruit().contains(family)) {
                    found.add(species);
                    break;
                }
            }
        }
        return found;
    }

    public Collection<IAlleleSpecies> getTreesThatHaveWood(final ItemStack fruit, final boolean nei, final World world, final GameProfile player) {
        final Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
        final List<IAlleleSpecies> found = new ArrayList<IAlleleSpecies>();
        for (final IAlleleSpecies species : set) {
            final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            for (final ItemStack fruit2 : tSpecies.getLogStacks()) {
                if (fruit2.isItemEqual(fruit)) {
                    found.add(species);
                }
            }
        }
        return found;
    }

    public Collection<IAlleleSpecies> getTreesThatMakePlanks(final ItemStack fruit, final boolean nei, final World world, final GameProfile player) {
        if (fruit == null) {
            return new ArrayList<IAlleleSpecies>();
        }
        final Collection<IAlleleSpecies> set = nei ? this.getAllSpecies() : this.getDiscoveredSpecies(world, player);
        final List<IAlleleSpecies> found = new ArrayList<IAlleleSpecies>();
        for (final IAlleleSpecies species : set) {
            final IAlleleTreeSpecies tSpecies = (IAlleleTreeSpecies) species;
            for (final ItemStack fruit2 : tSpecies.getLogStacks()) {
                if (Lumbermill.getPlankProduct(fruit2) != null && fruit.isItemEqual(Lumbermill.getPlankProduct(fruit2))) {
                    found.add(species);
                }
            }
        }
        return found;
    }

    @Override
    public boolean isDNAManipulable(final ItemStack member) {
        return ((ITreeRoot) this.getSpeciesRoot()).getType(member) == EnumGermlingType.POLLEN;
    }

    @Override
    public IIndividual getConversion(final ItemStack stack) {
        if (stack == null) {
            return null;
        }
        for (final Map.Entry<ItemStack, IIndividual> entry : AlleleManager.ersatzSaplings.entrySet()) {
            if (ItemStack.areItemStacksEqual(stack, entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public int[] getActiveTypes() {
        return new int[]{EnumGermlingType.SAPLING.ordinal(), EnumGermlingType.POLLEN.ordinal()};
    }

    @Override
    public void addExtraAlleles(final IChromosomeType chromosome, final TreeSet<IAllele> alleles) {
        switch ((EnumTreeChromosome) chromosome) {
            case FERTILITY: {
                for (final ForestryAllele.Saplings a : ForestryAllele.Saplings.values()) {
                    alleles.add(a.getAllele());
                }
                break;
            }
            case GIRTH: {
                for (final ForestryAllele.Int a2 : ForestryAllele.Int.values()) {
                    alleles.add(a2.getAllele());
                }
                break;
            }
            case HEIGHT: {
                for (final ForestryAllele.TreeHeight a3 : ForestryAllele.TreeHeight.values()) {
                    alleles.add(a3.getAllele());
                }
                break;
            }
            case MATURATION: {
                for (final ForestryAllele.Maturation a4 : ForestryAllele.Maturation.values()) {
                    alleles.add(a4.getAllele());
                }
                break;
            }
            case SAPPINESS: {
                for (final ForestryAllele.Sappiness a5 : ForestryAllele.Sappiness.values()) {
                    alleles.add(a5.getAllele());
                }
                break;
            }
            case TERRITORY: {
                for (final ForestryAllele.Territory a6 : ForestryAllele.Territory.values()) {
                    alleles.add(a6.getAllele());
                }
                break;
            }
            case YIELD: {
                for (final ForestryAllele.Yield a7 : ForestryAllele.Yield.values()) {
                    alleles.add(a7.getAllele());
                }
                break;
            }
            case FIREPROOF: {
                for (final ForestryAllele.Bool a8 : ForestryAllele.Bool.values()) {
                    alleles.add(a8.getAllele());
                }
                break;
            }
        }
    }
}
