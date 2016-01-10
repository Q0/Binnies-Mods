package binnie.genetics.genetics;

import binnie.genetics.Genetics;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemChargable;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemSerum;
import binnie.genetics.item.ItemSerumArray;
import net.minecraft.item.ItemStack;

public class Engineering {
    public Engineering() {
        super();
    }

    public static boolean isGeneAcceptor(ItemStack stack) {
        return stack == null ? false : (stack.getItem() instanceof IItemSerum ? ((IItemSerum) stack.getItem()).getCharges(stack) == 0 : stack.getItem() == Genetics.itemGenetics && (stack.getItemDamage() == GeneticsItems.EmptySerum.ordinal() || stack.getItemDamage() == GeneticsItems.EmptyGenome.ordinal()));
    }

    public static boolean canAcceptGene(ItemStack stack, IGene gene) {
        return stack.getItem() instanceof ItemSerum ? true : (stack.getItem() instanceof IItemSerum ? ((IItemSerum) stack.getItem()).getSpeciesRoot(stack) == gene.getSpeciesRoot() : isGeneAcceptor(stack));
    }

    public static IGene getGene(ItemStack stack, int chromosome) {
        return stack.getItem() instanceof IItemSerum ? ((IItemSerum) stack.getItem()).getGene(stack, chromosome) : null;
    }

    public static ItemStack addGene(ItemStack stack, IGene gene) {
        if (stack.getItem() instanceof IItemSerum) {
            ((IItemSerum) stack.getItem()).addGene(stack, gene);
        }

        return stack.getItem() == Genetics.itemGenetics && stack.getItemDamage() == GeneticsItems.EmptySerum.ordinal() ? ItemSerum.create(gene) : (stack.getItem() == Genetics.itemGenetics && stack.getItemDamage() == GeneticsItems.EmptyGenome.ordinal() ? ItemSerumArray.create(gene) : stack);
    }

    public static IGene[] getGenes(ItemStack serum) {
        return serum.getItem() instanceof IItemSerum ? ((IItemSerum) serum.getItem()).getGenes(serum) : (serum.getItem() == Genetics.itemSequencer ? new IGene[]{(new SequencerItem(serum)).gene} : new IGene[0]);
    }

    public static int getCharges(ItemStack serum) {
        return ((IItemChargable) serum.getItem()).getCharges(serum);
    }
}
