package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.Gene;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.genetics.GeneItem;
import binnie.genetics.genetics.IGeneItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ItemSerum extends ItemGene implements IItemSerum {
    public ItemSerum() {
        super("serum");
        this.setMaxDamage(16);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer entityPlayer, List list, boolean par4) {
        super.addInformation(itemstack, entityPlayer, list, par4);
    }

    public int getCharges(ItemStack stack) {
        return stack.getItem().getMaxDamage() - stack.getItemDamage();
    }

    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        for (ISpeciesRoot root : AlleleManager.alleleRegistry.getSpeciesRoot().values()) {
            Map<IChromosomeType, List<IAllele>> chromosomeMap = Binnie.Genetics.getChromosomeMap(root);

            for (Entry<IChromosomeType, List<IAllele>> entry : chromosomeMap.entrySet()) {
                IChromosomeType chromosome = (IChromosomeType) entry.getKey();

                for (IAllele allele : (List) entry.getValue()) {
                    Gene gene = Gene.create(allele, chromosome, root);
                    if (gene != null) {
                        IGeneItem item = new GeneItem(gene);
                        ItemStack stack = new ItemStack(this);
                        item.writeToItem(stack);
                        itemList.add(stack);
                    }
                }
            }
        }

    }

    public IGene[] getGenes(ItemStack stack) {
        return new IGene[]{this.getGeneItem(stack).getGene()};
    }

    public ISpeciesRoot getSpeciesRoot(ItemStack stack) {
        return this.getGeneItem(stack).getSpeciesRoot();
    }

    public IGene getGene(ItemStack stack, int chromosome) {
        return this.getGeneItem(stack).getGene();
    }

    public GeneItem getGeneItem(ItemStack stack) {
        return new GeneItem(stack);
    }

    public String getItemStackDisplayName(ItemStack itemstack) {
        IGeneItem gene = this.getGeneItem(itemstack);
        return Binnie.Genetics.getSystem(gene.getSpeciesRoot()).getDescriptor() + " Serum";
    }

    public ItemStack addGene(ItemStack stack, IGene gene) {
        IGeneItem geneI = this.getGeneItem(stack);
        geneI.addGene(gene);
        geneI.writeToItem(stack);
        return stack;
    }

    public static ItemStack create(IGene gene) {
        ItemStack item = new ItemStack(Genetics.itemSerum);
        item.setItemDamage(item.getMaxDamage());
        GeneItem seq = new GeneItem(gene);
        seq.writeToItem(item);
        return item;
    }
}
