package binnie.extratrees.block.decor;

import binnie.Binnie;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.IPlankType;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.block.WoodManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockMultiFence extends BlockFence implements IBlockMetadata {
    public BlockMultiFence() {
        super();
        this.setBlockName("multifence");
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        for (FenceType type : FenceType.values()) {
            itemList.add(WoodManager.getFence(PlankType.VanillaPlanks.SPRUCE, PlankType.VanillaPlanks.BIRCH, type, 1));
        }

    }

    public IIcon getIcon(int side, int meta) {
        return FenceRenderer.layer == 0 ? this.getDescription(meta).getPlankType().getIcon() : this.getDescription(meta).getSecondaryPlankType().getIcon();
    }

    public String getBlockName(ItemStack par1ItemStack) {
        int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
        IPlankType type1 = this.getDescription(meta).getPlankType();
        IPlankType type2 = this.getDescription(meta).getSecondaryPlankType();
        boolean twoTypes = type1 != type2;
        FenceType type = this.getDescription(meta).getFenceType();
        return Binnie.Language.localise(ExtraTrees.instance, "block.woodslab.name" + (twoTypes ? "2" : ""), new Object[]{type.getPrefix(), type1.getName(), type2.getName()});
    }
}
