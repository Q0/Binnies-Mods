package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.api.*;
import binnie.botany.api.IFlower;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.*;
import forestry.core.genetics.SpeciesRoot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import java.util.*;

public class FlowerHelper extends SpeciesRoot implements IFlowerRoot {
    static final String UID = "rootFlowers";
    public static int flowerSpeciesCount;
    public static ArrayList<IFlower> flowerTemplates;
    private static ArrayList<IFlowerMutation> flowerMutations;
    private static ArrayList<IColourMix> colourMixes;

    static {
        FlowerHelper.flowerSpeciesCount = -1;
        FlowerHelper.flowerTemplates = new ArrayList<IFlower>();
        FlowerHelper.flowerMutations = new ArrayList<IFlowerMutation>();
        FlowerHelper.colourMixes = new ArrayList<IColourMix>();
    }

    Map<ItemStack, IFlower> conversions;

    public FlowerHelper() {
        this.conversions = new HashMap<ItemStack, IFlower>();
    }

    public String getUID() {
        return "rootFlowers";
    }

    public int getSpeciesCount() {
        if (FlowerHelper.flowerSpeciesCount < 0) {
            FlowerHelper.flowerSpeciesCount = 0;
            for (final Map.Entry<String, IAllele> entry : AlleleManager.alleleRegistry.getRegisteredAlleles().entrySet()) {
                if (entry.getValue() instanceof IAlleleFlowerSpecies && ((IAlleleFlowerSpecies) entry.getValue()).isCounted()) {
                    ++FlowerHelper.flowerSpeciesCount;
                }
            }
        }
        return FlowerHelper.flowerSpeciesCount;
    }

    public boolean isMember(final ItemStack stack) {
        return stack != null && this.getType(stack) != EnumFlowerStage.NONE;
    }

    public boolean isMember(final ItemStack stack, final int type) {
        return this.getType(stack).ordinal() == type;
    }

    public boolean isMember(final IIndividual individual) {
        return individual instanceof IFlower;
    }

    public ItemStack getMemberStack(final IIndividual flower, final int type) {
        if (!this.isMember(flower)) {
            return null;
        }
        Item flowerItem = Botany.flowerItem;
        if (type == EnumFlowerStage.SEED.ordinal()) {
            flowerItem = Botany.seed;
        }
        if (type == EnumFlowerStage.POLLEN.ordinal()) {
            flowerItem = Botany.pollen;
        }
        if (flowerItem != Botany.flowerItem) {
            ((IFlower) flower).setAge(0);
        }
        final NBTTagCompound nbttagcompound = new NBTTagCompound();
        flower.writeToNBT(nbttagcompound);
        final ItemStack flowerStack = new ItemStack(flowerItem);
        flowerStack.setTagCompound(nbttagcompound);
        return flowerStack;
    }

    public ISpeciesType getType(final ItemStack stack) {
        return (stack == null) ? EnumFlowerStage.NONE : ((stack.getItem() == Botany.flowerItem) ? EnumFlowerStage.FLOWER : ((stack.getItem() == Botany.pollen) ? EnumFlowerStage.POLLEN : ((stack.getItem() == Botany.seed) ? EnumFlowerStage.SEED : EnumFlowerStage.NONE)));
    }

    public IFlower getMember(final ItemStack stack) {
        if (!this.isMember(stack)) {
            return null;
        }
        return new Flower(stack.getTagCompound());
    }

    public IFlower getFlower(final World world, final IFlowerGenome genome) {
        return new Flower(genome, 2);
    }

    public IFlowerGenome templateAsGenome(final IAllele[] template) {
        return new FlowerGenome(this.templateAsChromosomes(template));
    }

    public IFlowerGenome templateAsGenome(final IAllele[] templateActive, final IAllele[] templateInactive) {
        return new FlowerGenome(this.templateAsChromosomes(templateActive, templateInactive));
    }

    public IFlower templateAsIndividual(final IAllele[] template) {
        return new Flower(this.templateAsGenome(template), 2);
    }

    public IFlower templateAsIndividual(final IAllele[] templateActive, final IAllele[] templateInactive) {
        return new Flower(this.templateAsGenome(templateActive, templateInactive), 2);
    }

    public ArrayList<IFlower> getIndividualTemplates() {
        return FlowerHelper.flowerTemplates;
    }

    public void registerTemplate(final IAllele[] template) {
        this.registerTemplate(template[0].getUID(), template);
    }

    public void registerTemplate(final String identifier, final IAllele[] template) {
        FlowerHelper.flowerTemplates.add(new Flower(this.templateAsGenome(template), 2));
        if (!this.speciesTemplates.containsKey(identifier)) {
            this.speciesTemplates.put(identifier, template);
        }
    }

    public IAllele[] getTemplate(final String identifier) {
        return this.speciesTemplates.get(identifier);
    }

    public IAllele[] getDefaultTemplate() {
        return FlowerTemplates.getDefaultTemplate();
    }

    public IAllele[] getRandomTemplate(final Random rand) {
        return ((IAllele[][]) this.speciesTemplates.values().toArray(new IAllele[0][]))[rand.nextInt(this.speciesTemplates.values().size())];
    }

    public Collection<IFlowerMutation> getMutations(final boolean shuffle) {
        if (shuffle) {
            Collections.shuffle(FlowerHelper.flowerMutations);
        }
        return FlowerHelper.flowerMutations;
    }

    public void registerMutation(final IMutation mutation) {
        if (AlleleManager.alleleRegistry.isBlacklisted(mutation.getTemplate()[0].getUID())) {
            return;
        }
        if (AlleleManager.alleleRegistry.isBlacklisted(mutation.getAllele0().getUID())) {
            return;
        }
        if (AlleleManager.alleleRegistry.isBlacklisted(mutation.getAllele1().getUID())) {
            return;
        }
        FlowerHelper.flowerMutations.add((IFlowerMutation) mutation);
    }

    public IBotanistTracker getBreedingTracker(final World world, final GameProfile player) {
        final String filename = "BotanistTracker." + ((player == null) ? "common" : player.getId());
        BotanistTracker tracker = (BotanistTracker) world.loadItemData((Class) BotanistTracker.class, filename);
        if (tracker == null) {
            tracker = new BotanistTracker(filename, player);
            world.setItemData(filename, (WorldSavedData) tracker);
        }
        return tracker;
    }

    public IIndividual getMember(final NBTTagCompound compound) {
        return (IIndividual) new Flower(compound);
    }

    public Class getMemberClass() {
        return IFlower.class;
    }

    public IChromosomeType[] getKaryotype() {
        return (IChromosomeType[]) EnumFlowerChromosome.values();
    }

    public IChromosomeType getKaryotypeKey() {
        return (IChromosomeType) EnumFlowerChromosome.SPECIES;
    }

    public void addConversion(final ItemStack itemstack, final IAllele[] template) {
        final IFlower flower = this.getFlower(null, this.templateAsGenome(template));
        this.conversions.put(itemstack, flower);
    }

    public IFlower getConversion(final ItemStack itemstack) {
        for (final Map.Entry<ItemStack, IFlower> entry : this.conversions.entrySet()) {
            if (entry.getKey().isItemEqual(itemstack)) {
                return (IFlower) entry.getValue().copy();
            }
        }
        return null;
    }

    public void registerColourMix(final IColourMix colourMix) {
        FlowerHelper.colourMixes.add(colourMix);
    }

    public Collection<IColourMix> getColourMixes(final boolean shuffle) {
        if (shuffle) {
            Collections.shuffle(FlowerHelper.colourMixes);
        }
        return FlowerHelper.colourMixes;
    }
}
