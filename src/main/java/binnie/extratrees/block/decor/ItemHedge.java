package binnie.extratrees.block.decor;

import binnie.extratrees.genetics.ExtraTreeSpecies;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemHedge extends ItemBlockWithMetadata {
    public ItemHedge(final Block block) {
        super(block, block);
    }

    public String getItemStackDisplayName(final ItemStack item) {
        final int meta = item.getItemDamage();
        return ExtraTreeSpecies.LeafType.values()[meta % 6].descript + ((meta >= 8) ? " Full" : "") + " Hedge";
    }
}
