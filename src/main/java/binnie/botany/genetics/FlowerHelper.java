package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IBotanistTracker;
import binnie.botany.api.IColourMix;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerMutation;
import binnie.botany.api.IFlowerRoot;
import binnie.botany.genetics.BotanistTracker;
import binnie.botany.genetics.Flower;
import binnie.botany.genetics.FlowerGenome;
import binnie.botany.genetics.FlowerTemplates;
import com.mojang.authlib.GameProfile;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.core.genetics.SpeciesRoot;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class FlowerHelper extends SpeciesRoot implements IFlowerRoot {
   public static int flowerSpeciesCount = -1;
   static final String UID = "rootFlowers";
   public static ArrayList flowerTemplates = new ArrayList();
   private static ArrayList flowerMutations = new ArrayList();
   Map conversions = new HashMap();
   private static ArrayList colourMixes = new ArrayList();

   public FlowerHelper() {
      super();
   }

   public String getUID() {
      return "rootFlowers";
   }

   public int getSpeciesCount() {
      if(flowerSpeciesCount < 0) {
         flowerSpeciesCount = 0;

         for(Entry<String, IAllele> entry : AlleleManager.alleleRegistry.getRegisteredAlleles().entrySet()) {
            if(entry.getValue() instanceof IAlleleFlowerSpecies && ((IAlleleFlowerSpecies)entry.getValue()).isCounted()) {
               ++flowerSpeciesCount;
            }
         }
      }

      return flowerSpeciesCount;
   }

   public boolean isMember(ItemStack stack) {
      return stack != null && this.getType(stack) != EnumFlowerStage.NONE;
   }

   public boolean isMember(ItemStack stack, int type) {
      return this.getType(stack).ordinal() == type;
   }

   public boolean isMember(IIndividual individual) {
      return individual instanceof IFlower;
   }

   public ItemStack getMemberStack(IIndividual flower, int type) {
      if(!this.isMember(flower)) {
         return null;
      } else {
         Item flowerItem = Botany.flowerItem;
         if(type == EnumFlowerStage.SEED.ordinal()) {
            flowerItem = Botany.seed;
         }

         if(type == EnumFlowerStage.POLLEN.ordinal()) {
            flowerItem = Botany.pollen;
         }

         if(flowerItem != Botany.flowerItem) {
            ((IFlower)flower).setAge(0);
         }

         NBTTagCompound nbttagcompound = new NBTTagCompound();
         flower.writeToNBT(nbttagcompound);
         ItemStack flowerStack = new ItemStack(flowerItem);
         flowerStack.setTagCompound(nbttagcompound);
         return flowerStack;
      }
   }

   public EnumFlowerStage getType(ItemStack stack) {
      return stack == null?EnumFlowerStage.NONE:(stack.getItem() == Botany.flowerItem?EnumFlowerStage.FLOWER:(stack.getItem() == Botany.pollen?EnumFlowerStage.POLLEN:(stack.getItem() == Botany.seed?EnumFlowerStage.SEED:EnumFlowerStage.NONE)));
   }

   public IFlower getMember(ItemStack stack) {
      return !this.isMember(stack)?null:new Flower(stack.getTagCompound());
   }

   public IFlower getFlower(World world, IFlowerGenome genome) {
      return new Flower(genome, 2);
   }

   public IFlowerGenome templateAsGenome(IAllele[] template) {
      return new FlowerGenome(this.templateAsChromosomes(template));
   }

   public IFlowerGenome templateAsGenome(IAllele[] templateActive, IAllele[] templateInactive) {
      return new FlowerGenome(this.templateAsChromosomes(templateActive, templateInactive));
   }

   public IFlower templateAsIndividual(IAllele[] template) {
      return new Flower(this.templateAsGenome(template), 2);
   }

   public IFlower templateAsIndividual(IAllele[] templateActive, IAllele[] templateInactive) {
      return new Flower(this.templateAsGenome(templateActive, templateInactive), 2);
   }

   public ArrayList getIndividualTemplates() {
      return flowerTemplates;
   }

   public void registerTemplate(IAllele[] template) {
      this.registerTemplate(template[0].getUID(), template);
   }

   public void registerTemplate(String identifier, IAllele[] template) {
      flowerTemplates.add(new Flower(this.templateAsGenome(template), 2));
      if(!this.speciesTemplates.containsKey(identifier)) {
         this.speciesTemplates.put(identifier, template);
      }

   }

   public IAllele[] getTemplate(String identifier) {
      return (IAllele[])this.speciesTemplates.get(identifier);
   }

   public IAllele[] getDefaultTemplate() {
      return FlowerTemplates.getDefaultTemplate();
   }

   public IAllele[] getRandomTemplate(Random rand) {
      return ((IAllele[][])this.speciesTemplates.values().toArray(new IAllele[0][]))[rand.nextInt(this.speciesTemplates.values().size())];
   }

   public Collection getMutations(boolean shuffle) {
      if(shuffle) {
         Collections.shuffle(flowerMutations);
      }

      return flowerMutations;
   }

   public void registerMutation(IMutation mutation) {
      if(!AlleleManager.alleleRegistry.isBlacklisted(mutation.getTemplate()[0].getUID())) {
         if(!AlleleManager.alleleRegistry.isBlacklisted(mutation.getAllele0().getUID())) {
            if(!AlleleManager.alleleRegistry.isBlacklisted(mutation.getAllele1().getUID())) {
               flowerMutations.add((IFlowerMutation)mutation);
            }
         }
      }
   }

   public IBotanistTracker getBreedingTracker(World world, GameProfile player) {
      String filename = "BotanistTracker." + (player == null?"common":player.getId());
      BotanistTracker tracker = (BotanistTracker)world.loadItemData(BotanistTracker.class, filename);
      if(tracker == null) {
         tracker = new BotanistTracker(filename, player);
         world.setItemData(filename, tracker);
      }

      return tracker;
   }

   public IIndividual getMember(NBTTagCompound compound) {
      return new Flower(compound);
   }

   public Class getMemberClass() {
      return IFlower.class;
   }

   public IChromosomeType[] getKaryotype() {
      return EnumFlowerChromosome.values();
   }

   public IChromosomeType getKaryotypeKey() {
      return EnumFlowerChromosome.SPECIES;
   }

   public void addConversion(ItemStack itemstack, IAllele[] template) {
      IFlower flower = this.getFlower((World)null, this.templateAsGenome(template));
      this.conversions.put(itemstack, flower);
   }

   public IFlower getConversion(ItemStack itemstack) {
      for(Entry<ItemStack, IFlower> entry : this.conversions.entrySet()) {
         if(((ItemStack)entry.getKey()).isItemEqual(itemstack)) {
            return (IFlower)((IFlower)entry.getValue()).copy();
         }
      }

      return null;
   }

   public void registerColourMix(IColourMix colourMix) {
      colourMixes.add(colourMix);
   }

   public Collection getColourMixes(boolean shuffle) {
      if(shuffle) {
         Collections.shuffle(colourMixes);
      }

      return colourMixes;
   }
}
