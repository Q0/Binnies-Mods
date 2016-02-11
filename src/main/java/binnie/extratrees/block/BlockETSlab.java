package binnie.extratrees.block;

import binnie.Binnie;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWoodSlab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class BlockETSlab extends BlockWoodSlab implements IBlockMetadata {
    public BlockETSlab(final boolean par2) {
        super(par2);
        this.setCreativeTab(Tabs.tabArboriculture);
        this.setHardness(2.0f).setResistance(5.0f).setStepSound(BlockETSlab.soundTypeWood);
        if (!this.field_150004_a) {
            this.useNeighborBrightness = true;
        }
        this.setLightOpacity(0);
        this.setBlockName("slabs");
    }

    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int blockMeta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        drops.addAll(BlockMetadata.getBlockDropped((IBlockMetadata) ExtraTrees.blockSlab, world, x, y, z, blockMeta));
        if (this.field_150004_a) {
            drops.addAll(BlockMetadata.getBlockDropped((IBlockMetadata) ExtraTrees.blockSlab, world, x, y, z, blockMeta));
        }
        return drops;
    }

    public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
        return BlockMetadata.breakBlock(this, player, world, x, y, z);
    }

    public TileEntity createNewTileEntity(final World var1, final int i) {
        return new TileEntityMetadata();
    }

    public boolean hasTileEntity(final int meta) {
        return true;
    }

    public boolean onBlockEventReceived(final World par1World, final int par2, final int par3, final int par4, final int par5, final int par6) {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        final TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
        return tileentity != null && tileentity.receiveClientEvent(par5, par6);
    }

    public int getPlacedMeta(final ItemStack stack, final World world, final int x, final int y, final int z, final ForgeDirection clickedBlock) {
        return TileEntityMetadata.getItemDamage(stack);
    }

    public int getDroppedMeta(final int blockMeta, final int tileMeta) {
        return tileMeta;
    }

    public String getBlockName(final ItemStack par1ItemStack) {
        final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
        return Binnie.Language.localise(ExtraTrees.instance, "block.woodslab.name", PlankType.ExtraTreePlanks.values()[meta].getName());
    }

    public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
    }

    public void dropAsStack(final World world, final int x, final int y, final int z, final ItemStack drop) {
        this.dropBlockAsItem(world, x, y, z, drop);
    }

    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        if (this.field_150004_a) {
            return;
        }
        for (int i = 0; i < PlankType.ExtraTreePlanks.values().length; ++i) {
            itemList.add(TileEntityMetadata.getItemStack(this, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
        final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        if (tile != null) {
            return this.getIcon(side, tile.getTileMetadata());
        }
        return super.getIcon(world, x, y, z, side);
    }

    public IIcon getIcon(final int side, final int meta) {
        return PlankType.ExtraTreePlanks.values()[meta].getIcon();
    }

    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final Block par5, final int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeTileEntity(par2, par3, par4);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean isWood(final IBlockAccess world, final int x, final int y, final int z) {
        return true;
    }

    public int getFlammability(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection face) {
        return 20;
    }

    public boolean isFlammable(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection face) {
        return true;
    }

    public int getFireSpreadSpeed(final IBlockAccess world, final int x, final int y, final int z, final ForgeDirection face) {
        return 5;
    }

    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
        return BlockMetadata.getPickBlock(world, x, y, z);
    }
}
