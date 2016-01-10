package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.Gene;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.GeneArrayItem;
import binnie.genetics.genetics.IGeneItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemSerumArray extends ItemGene implements IItemSerum {
    public ItemSerumArray() {
        super("serumArray");
        this.setMaxDamage(16);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer entityPlayer, List list, boolean par4) {
        super.addInformation(itemstack, entityPlayer, list, par4);
    }

    public int getCharges(ItemStack stack) {
        return stack.getItem().getMaxDamage() - stack.getItemDamage();
    }

    public IGene[] getGenes(ItemStack stack) {
        return (IGene[]) this.getGeneItem(stack).getGenes().toArray(new IGene[0]);
    }

    public ISpeciesRoot getSpeciesRoot(ItemStack stack) {
        return this.getGeneItem(stack).getSpeciesRoot();
    }

    public IGene getGene(ItemStack stack, int chromosome) {
        return this.getGeneItem(stack).getGene(chromosome);
    }

    public GeneArrayItem getGeneItem(ItemStack stack) {
        return new GeneArrayItem(stack);
    }

    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        for (ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
            for (IIndividual template : root.getIndividualTemplates()) {
                if (!template.getGenome().getPrimary().isSecret()) {
                    IGeneItem item = new GeneArrayItem();

                    for (IChromosomeType type : root.getKaryotype()) {
                        IChromosome c = template.getGenome().getChromosomes()[type.ordinal()];
                        if (c != null) {
                            IAllele active = c.getActiveAllele();
                            if (active != null) {
                                item.addGene(new Gene(active, type, root));
                            }
                        }
                    }

                    ItemStack array = new ItemStack(this);
                    item.writeToItem(array);
                    itemList.add(array);
                }
            }
        }

    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.icons[0] = Genetics.proxy.getIcon(register, "machines/genome.glass");
        this.icons[1] = Genetics.proxy.getIcon(register, "machines/genome.cap");
        this.icons[2] = Genetics.proxy.getIcon(register, "machines/genome.edges");
        this.icons[3] = Genetics.proxy.getIcon(register, "machines/genome.dna");
    }

    public String getItemStackDisplayName(ItemStack itemstack) {
        IGeneItem gene = this.getGeneItem(itemstack);
        return Binnie.Genetics.getSystem(gene.getSpeciesRoot()).getDescriptor() + " Serum Array";
    }

    public ItemStack addGene(ItemStack stack, IGene gene) {
        IGeneItem geneI = this.getGeneItem(stack);
        geneI.addGene(gene);
        geneI.writeToItem(stack);
        return stack;
    }

    public static ItemStack create(IGene gene) {
        ItemStack item = new ItemStack(Genetics.itemSerumArray);
        item.setItemDamage(item.getMaxDamage());
        GeneArrayItem seq = new GeneArrayItem(gene);
        seq.writeToItem(item);
        return item;
    }
}
