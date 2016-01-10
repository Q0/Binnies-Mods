package binnie.botany.genetics;

import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IColourMix;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerColour;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.api.IFlowerMutation;
import binnie.botany.core.BotanyCore;
import binnie.botany.genetics.FlowerGenome;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosome;
import forestry.core.genetics.Chromosome;
import forestry.core.genetics.Individual;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Flower extends Individual implements IFlower {
   public IFlowerGenome genome;
   public IFlowerGenome mate;
   int age = 0;
   boolean wilting = false;
   boolean flowered;

   public Flower(NBTTagCompound nbttagcompound) {
      super();
      this.readFromNBT(nbttagcompound);
   }

   public Flower(IFlowerGenome genome, int age) {
      super();
      this.genome = genome;
      this.age = age;
      if(age > 0) {
         this.flowered = true;
      } else {
         this.flowered = false;
      }

   }

   public String getDisplayName() {
      IAlleleFlowerSpecies species = this.getGenome().getPrimary();
      String name = "";
      if(species != null) {
         name = name + species.getName();
      }

      if(this.age == 0) {
         name = name + "";
      }

      return name;
   }

   public void addTooltip(List list) {
      IAlleleFlowerSpecies primary = this.genome.getPrimary();
      IAlleleFlowerSpecies secondary = this.genome.getSecondary();
      if(!this.isPureBred(EnumFlowerChromosome.SPECIES)) {
         list.add("§9" + primary.getName() + "-" + secondary.getName() + " Hybrid");
      }

      list.add("§6Age: " + this.getAge());
      list.add("§6T: " + this.getGenome().getPrimary().getTemperature() + " / " + this.getGenome().getToleranceTemperature());
      list.add("§6M: " + this.getGenome().getPrimary().getMoisture() + " / " + this.getGenome().getToleranceMoisture());
      list.add("§6pH: " + this.getGenome().getPrimary().getHumidity() + " / " + this.getGenome().getTolerancePH());
      list.add("§6Fert: " + this.getGenome().getFertility() + "x");
   }

   public String getIdent() {
      return this.getGenome().getPrimary().getUID();
   }

   public void readFromNBT(NBTTagCompound nbttagcompound) {
      super.readFromNBT(nbttagcompound);
      if(nbttagcompound == null) {
         this.genome = BotanyCore.getFlowerRoot().templateAsGenome(BotanyCore.getFlowerRoot().getDefaultTemplate());
      } else {
         this.age = nbttagcompound.getInteger("Age");
         this.wilting = nbttagcompound.getBoolean("Wilt");
         this.flowered = nbttagcompound.getBoolean("Flowered");
         if(nbttagcompound.hasKey("Genome")) {
            this.genome = new FlowerGenome(nbttagcompound.getCompoundTag("Genome"));
         }

         if(nbttagcompound.hasKey("Mate")) {
            this.mate = new FlowerGenome(nbttagcompound.getCompoundTag("Mate"));
         }

      }
   }

   public void writeToNBT(NBTTagCompound nbttagcompound) {
      super.writeToNBT(nbttagcompound);
      nbttagcompound.setInteger("Age", this.age);
      nbttagcompound.setBoolean("Wilt", this.wilting);
      nbttagcompound.setBoolean("Flowered", this.flowered);
      if(this.genome != null) {
         NBTTagCompound NBTmachine = new NBTTagCompound();
         this.genome.writeToNBT(NBTmachine);
         nbttagcompound.setTag("Genome", NBTmachine);
      }

      if(this.mate != null) {
         NBTTagCompound NBTmachine = new NBTTagCompound();
         this.mate.writeToNBT(NBTmachine);
         nbttagcompound.setTag("Mate", NBTmachine);
      }

   }

   public int getMetadata() {
      return 0;
   }

   public IFlowerGenome getGenome() {
      return this.genome;
   }

   private IChromosome inheritChromosome(Random rand, IChromosome parent1, IChromosome parent2) {
      IAllele choice1;
      if(rand.nextBoolean()) {
         choice1 = parent1.getPrimaryAllele();
      } else {
         choice1 = parent1.getSecondaryAllele();
      }

      IAllele choice2;
      if(rand.nextBoolean()) {
         choice2 = parent2.getPrimaryAllele();
      } else {
         choice2 = parent2.getSecondaryAllele();
      }

      return rand.nextBoolean()?new Chromosome(choice1, choice2):new Chromosome(choice2, choice1);
   }

   public void mate(IFlower other) {
      this.mate = new FlowerGenome(other.getGenome().getChromosomes());
   }

   public IFlowerGenome getMate() {
      return this.mate;
   }

   public IFlower copy() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      this.writeToNBT(nbttagcompound);
      return new Flower(nbttagcompound);
   }

   public int getAge() {
      return this.age;
   }

   public void age() {
      if(this.age < 15) {
         ++this.age;
      }

   }

   public void setAge(int i) {
      this.age = i;
   }

   public int getMaxAge() {
      return this.getGenome().getLifespan();
   }

   public boolean isWilted() {
      return this.wilting;
   }

   public void setWilted(boolean wilted) {
      this.wilting = wilted;
   }

   public boolean hasFlowered() {
      return this.flowered;
   }

   public void setFlowered(boolean flowered) {
      this.flowered = flowered;
   }

   public void removeMate() {
      this.mate = null;
   }

   public IFlower getOffspring(World world) {
      IChromosome[] chromosomes = new IChromosome[this.genome.getChromosomes().length];
      IChromosome[] parent1 = this.genome.getChromosomes();
      IChromosome[] parent2 = this.mate.getChromosomes();
      parent1 = this.mutateSpecies(world, this.genome, this.mate);
      parent2 = this.mutateSpecies(world, this.mate, this.genome);

      for(int i = 0; i < parent1.length; ++i) {
         if(parent1[i] != null && parent2[i] != null) {
            chromosomes[i] = Chromosome.inheritChromosome(world.rand, parent1[i], parent2[i]);
         }
      }

      return new Flower(new FlowerGenome(chromosomes), 0);
   }

   private IChromosome[] mutateSpecies(World world, IFlowerGenome genomeOne, IFlowerGenome genomeTwo) {
      IChromosome[] parent1 = genomeOne.getChromosomes();
      IChromosome[] parent2 = genomeTwo.getChromosomes();
      IFlowerGenome genome0;
      IFlowerGenome genome1;
      IAllele allele0;
      IAllele allele1;
      if(world.rand.nextBoolean()) {
         allele0 = parent1[EnumTreeChromosome.SPECIES.ordinal()].getPrimaryAllele();
         allele1 = parent2[EnumTreeChromosome.SPECIES.ordinal()].getSecondaryAllele();
         genome0 = genomeOne;
         genome1 = genomeTwo;
      } else {
         allele0 = parent2[EnumTreeChromosome.SPECIES.ordinal()].getPrimaryAllele();
         allele1 = parent1[EnumTreeChromosome.SPECIES.ordinal()].getSecondaryAllele();
         genome0 = genomeTwo;
         genome1 = genomeOne;
      }

      IFlowerColour colour1 = genome0.getPrimaryColor();
      IFlowerColour colour2 = genome1.getPrimaryColor();
      if(colour1 != colour2) {
         for(IColourMix mutation : BotanyCore.getFlowerRoot().getColourMixes(true)) {
            if(mutation.isMutation(colour1, colour2) && world.rand.nextFloat() * 100.0F < (float)mutation.getChance()) {
               parent1[EnumFlowerChromosome.PRIMARY.ordinal()] = new Chromosome(mutation.getResult().getAllele());
            }
         }
      }

      colour1 = genome0.getSecondaryColor();
      colour2 = genome1.getSecondaryColor();
      if(colour1 != colour2) {
         for(IColourMix mutation : BotanyCore.getFlowerRoot().getColourMixes(true)) {
            if(mutation.isMutation(colour1, colour2) && world.rand.nextFloat() * 100.0F < (float)mutation.getChance()) {
               parent1[EnumFlowerChromosome.SECONDARY.ordinal()] = new Chromosome(mutation.getResult().getAllele());
            }
         }
      }

      colour1 = genome0.getStemColor();
      colour2 = genome1.getStemColor();
      if(colour1 != colour2) {
         for(IColourMix mutation : BotanyCore.getFlowerRoot().getColourMixes(true)) {
            if(mutation.isMutation(colour1, colour2) && world.rand.nextFloat() * 100.0F < (float)mutation.getChance()) {
               parent1[EnumFlowerChromosome.STEM.ordinal()] = new Chromosome(mutation.getResult().getAllele());
            }
         }
      }

      IChromosome[] template = null;

      for(IFlowerMutation mutation : BotanyCore.getFlowerRoot().getMutations(true)) {
         float chance = mutation.getChance(allele0, allele1, genome0, genome1);
         if(chance > 0.0F && world.rand.nextFloat() * 100.0F < chance && template == null) {
            template = BotanyCore.getFlowerRoot().templateAsChromosomes(mutation.getTemplate());
         }
      }

      if(template != null) {
         parent1 = template;
      }

      return parent1;
   }
}
