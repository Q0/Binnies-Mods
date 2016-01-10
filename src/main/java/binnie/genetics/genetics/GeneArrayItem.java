package binnie.genetics.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.api.IGene;
import binnie.genetics.genetics.IGeneItem;
import forestry.api.core.INBTTagable;
import forestry.api.genetics.ISpeciesRoot;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GeneArrayItem implements INBTTagable, IGeneItem {
   List genes = new ArrayList();

   public GeneArrayItem(ItemStack stack) {
      super();
      if(stack != null) {
         this.readFromNBT(stack.getTagCompound());
      }
   }

   public GeneArrayItem(IGene gene) {
      super();
      this.addGene(gene);
   }

   public GeneArrayItem() {
      super();
   }

   public int getColour(int renderPass) {
      return renderPass == 2?this.getBreedingSystem().getColour():16777215;
   }

   public void getInfo(List list) {
      List<Object> totalList = new ArrayList();

      for(IGene gene : this.genes) {
         String chromosomeName = this.getBreedingSystem().getChromosomeName(gene.getChromosome());
         totalList.add("§6" + chromosomeName + "§7: " + gene.getName());
      }

      if(totalList.size() >= 4 && !BinnieCore.proxy.isShiftDown()) {
         list.add(totalList.get(0));
         list.add(totalList.get(1));
         list.add(totalList.size() - 2 + " more genes. Hold shift to display.");
      } else {
         list.addAll(totalList);
      }

   }

   public BreedingSystem getBreedingSystem() {
      if(this.genes.size() == 0) {
         return null;
      } else {
         BreedingSystem system = Binnie.Genetics.getSystem(((IGene)this.genes.get(0)).getSpeciesRoot().getUID());
         return system == null?(BreedingSystem)Binnie.Genetics.getActiveSystems().iterator().next():system;
      }
   }

   public List getGenes() {
      return this.genes;
   }

   public void readFromNBT(NBTTagCompound nbt) {
      this.genes.clear();
      if(nbt != null) {
         NBTTagList list = nbt.getTagList("genes", 10);

         for(int i = 0; i < list.tagCount(); ++i) {
            Gene gene = Gene.create(list.getCompoundTagAt(i));
            this.genes.add(gene);
         }
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
      if(this.genes.size() != 0) {
         NBTTagList list = new NBTTagList();

         for(IGene gene : this.genes) {
            NBTTagCompound geneNBT = gene.getNBTTagCompound();
            list.appendTag(geneNBT);
         }

         nbt.setTag("genes", list);
      }
   }

   public ISpeciesRoot getSpeciesRoot() {
      return this.genes.size() == 0?null:((IGene)this.genes.get(0)).getSpeciesRoot();
   }

   public IGene getGene(int chromosome) {
      for(IGene gene : this.genes) {
         if(gene.getChromosome().ordinal() == chromosome) {
            return gene;
         }
      }

      return null;
   }

   public void writeToItem(ItemStack stack) {
      if(stack != null) {
         NBTTagCompound nbt = stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
         this.writeToNBT(nbt);
         stack.setTagCompound(nbt);
      }
   }

   public void addGene(IGene gene) {
      if(this.getGene(gene.getChromosome().ordinal()) != null) {
         this.genes.remove(this.getGene(gene.getChromosome().ordinal()));
      }

      this.genes.add(gene);
   }
}
