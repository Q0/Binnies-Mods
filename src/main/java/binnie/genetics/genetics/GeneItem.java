package binnie.genetics.genetics;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.genetics.api.IGene;
import forestry.api.core.INBTTagable;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class GeneItem implements INBTTagable, IGeneItem {
    IGene gene;

    public GeneItem(final ItemStack stack) {
        if (stack == null) {
            return;
        }
        this.readFromNBT(stack.getTagCompound());
    }

    public GeneItem(final IGene gene) {
        this.gene = gene;
    }

    public boolean isCorrupted() {
        return this.gene == null;
    }

    public void writeToItem(final ItemStack stack) {
        if (this.gene == null || stack == null) {
            return;
        }
        final NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound() : new NBTTagCompound();
        this.writeToNBT(nbt);
        stack.setTagCompound(nbt);
    }

    public int getColour(final int renderPass) {
        if (renderPass == 2 && this.getBreedingSystem() != null) {
            return this.getBreedingSystem().getColour();
        }
        return 16777215;
    }

    public void getInfo(final List list) {
        final String chromosomeName = this.getBreedingSystem().getChromosomeName(this.gene.getChromosome());
        list.add("§6" + chromosomeName + "§7: " + this.gene.getName());
    }

    public BreedingSystem getBreedingSystem() {
        if (this.gene == null) {
            return null;
        }
        return Binnie.Genetics.getSystem(this.gene.getSpeciesRoot().getUID());
    }

    public IGene getGene() {
        return this.gene;
    }

    public void readFromNBT(final NBTTagCompound nbt) {
        if (nbt != null) {
            this.gene = Gene.create(nbt.getCompoundTag("gene"));
        }
    }

    public void writeToNBT(final NBTTagCompound nbt) {
        if (this.gene == null) {
            return;
        }
        final NBTTagCompound geneNBT = this.gene.getNBTTagCompound();
        nbt.setTag("gene", (NBTBase) geneNBT);
    }

    public ISpeciesRoot getSpeciesRoot() {
        return this.gene.getSpeciesRoot();
    }

    public void addGene(final IGene gene) {
        this.gene = gene;
    }
}
