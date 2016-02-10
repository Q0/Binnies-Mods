package binnie.extratrees.block;

import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemETStairs extends ItemBlock {
    public ItemETStairs(final Block block) {
        super(block);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setUnlocalizedName("stairs");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int par1) {
        return PlankType.ExtraTreePlanks.values()[par1].getIcon();
    }

    public boolean placeBlockAt(final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ, final int metadata) {
        final boolean done = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        final TileEntityMetadata tile = (TileEntityMetadata) world.getTileEntity(x, y, z);
        if (tile != null) {
            tile.setTileMetadata(stack.getItemDamage(), false);
        }
        return done;
    }

    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(final ItemStack par1ItemStack) {
        return ((IBlockMetadata) this.field_150939_a).getBlockName(par1ItemStack);
    }
}
