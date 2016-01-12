package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.genetics.Gene;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemAnalysable;
import binnie.genetics.api.IItemChargable;
import binnie.genetics.genetics.GeneItem;
import binnie.genetics.genetics.SequencerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemSequence extends Item implements IItemAnalysable, IItemChargable {
    public ItemSequence() {
        this.setMaxStackSize(1);
        this.setMaxDamage(5);
        this.setUnlocalizedName("sequence");
        this.setCreativeTab(CreativeTabGenetics.instance);
    }

    public String getItemStackDisplayName(final ItemStack itemstack) {
        final GeneItem gene = new GeneItem(itemstack);
        if (gene.isCorrupted()) {
            return "Corrupted Sequence";
        }
        return gene.getBreedingSystem().getDescriptor() + " DNA Sequence";
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityPlayer, final List list, final boolean par4) {
        super.addInformation(itemstack, entityPlayer, list, par4);
        list.add(Genetics.proxy.localise("item.sequence." + (5 - itemstack.getItemDamage() % 6)));
        final SequencerItem gene = new SequencerItem(itemstack);
        if (gene.isCorrupted()) {
            return;
        }
        if (gene.analysed) {
            gene.getInfo(list);
        } else {
            list.add("<Unknown>");
        }
        final int seq = gene.sequenced;
        if (seq == 0) {
            list.add("Unsequenced");
        } else if (seq < 100) {
            list.add("Partially Sequenced (" + seq + "%)");
        } else {
            list.add("Fully Sequenced");
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.itemIcon = Genetics.proxy.getIcon(register, "sequencer");
    }

    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        final IAlleleBeeSpecies species = (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.speciesMeadows");
        itemList.add(create(new Gene((IAllele) species, (IChromosomeType) EnumBeeChromosome.SPECIES, (ISpeciesRoot) Binnie.Genetics.getBeeRoot()), false));
    }

    public boolean isAnalysed(final ItemStack stack) {
        final SequencerItem seq = new SequencerItem(stack);
        return seq.isCorrupted() || seq.analysed;
    }

    public ItemStack analyse(final ItemStack stack) {
        final SequencerItem seq = new SequencerItem(stack);
        seq.analysed = true;
        seq.writeToItem(stack);
        return stack;
    }

    public float getAnalyseTimeMult(final ItemStack stack) {
        return 1.0f;
    }

    public static ItemStack create(final IGene gene) {
        return create(gene, false);
    }

    public static ItemStack create(final IGene gene, final boolean sequenced) {
        final ItemStack item = new ItemStack(Genetics.itemSequencer);
        item.setItemDamage(sequenced ? 0 : item.getMaxDamage());
        final SequencerItem seq = new SequencerItem(gene);
        seq.writeToItem(item);
        return item;
    }

    public int getCharges(final ItemStack stack) {
        return stack.getItem().getMaxDamage() - stack.getItemDamage();
    }
}
