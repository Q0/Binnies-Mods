package binnie.genetics.genetics;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.api.IGene;
import binnie.genetics.genetics.IGeneItem;
import forestry.api.core.INBTTagable;
import forestry.api.genetics.ISpeciesRoot;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class GeneItem implements INBTTagable, IGeneItem {
   IGene gene;

   public GeneItem(ItemStack stack) {
      super();
      if(stack != null) {
         this.readFromNBT(stack.getTagCompound());
      }
   }

   public GeneItem(IGene gene) {
      super();
      this.gene = gene;
   }

   public boolean isCorrupted() {
      return this.gene == null;
   }

   public void writeToItem(ItemStack stack) {
      if(this.gene != null && stack != null) {
         NBTTagCompound nbt = stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
         this.writeToNBT(nbt);
         stack.setTagCompound(nbt);
      }
   }

   public int getColour(int renderPass) {
      return renderPass == 2 && this.getBreedingSystem() != null?this.getBreedingSystem().getColour():16777215;
   }

   public void getInfo(List list) {
      String chromosomeName = this.getBreedingSystem().getChromosomeName(this.gene.getChromosome());
      list.add("§6" + chromosomeName + "§7: " + this.gene.getName());
   }

   public BreedingSystem getBreedingSystem() {
      return this.gene == null?null:Binnie.Genetics.getSystem(this.gene.getSpeciesRoot().getUID());
   }

   public IGene getGene() {
      return this.gene;
   }

   public void readFromNBT(NBTTagCompound nbt) {
      if(nbt != null) {
         this.gene = Gene.create(nbt.getCompoundTag("gene"));
      }

   }

   public void writeToNBT(NBTTagCompound nbt) {
      if(this.gene != null) {
         NBTTagCompound geneNBT = this.gene.getNBTTagCompound();
         nbt.setTag("gene", geneNBT);
      }
   }

   public ISpeciesRoot getSpeciesRoot() {
      return this.gene.getSpeciesRoot();
   }

   public void addGene(IGene gene) {
      this.gene = gene;
   }
}
