package binnie.core.block;

import binnie.core.BinnieCore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class BlockMetadata extends BlockContainer implements IBlockMetadata {
    static int temporyMeta;

    static {
        BlockMetadata.temporyMeta = -1;
    }

    public BlockMetadata(final Material material) {
        super(material);
    }

    public static ArrayList<ItemStack> getBlockDropped(final IBlockMetadata block, final World world, final int x, final int y, final int z, final int blockMeta) {
        final ArrayList<ItemStack> array = new ArrayList<ItemStack>();
        final TileEntityMetadata tile = TileEntityMetadata.getTile((IBlockAccess) world, x, y, z);
        if (tile != null && !tile.hasDroppedBlock()) {
            final int meta = block.getDroppedMeta(world.getBlockMetadata(x, y, z), tile.getTileMetadata());
            array.add(TileEntityMetadata.getItemStack((Block) block, meta));
        }
        return array;
    }

    public static boolean breakBlock(final IBlockMetadata block, final EntityPlayer player, final World world, final int i, final int j, final int k) {
        List<ItemStack> drops = new ArrayList<ItemStack>();
        final Block block2 = (Block) block;
        final TileEntityMetadata tile = TileEntityMetadata.getTile((IBlockAccess) world, i, j, k);
        if (tile != null && !tile.hasDroppedBlock()) {
            final int tileMeta = TileEntityMetadata.getTileMetadata((IBlockAccess) world, i, j, k);
            drops = (List<ItemStack>) block2.getDrops(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
        }
        final boolean hasBeenBroken = world.setBlockToAir(i, j, k);
        if (hasBeenBroken && BinnieCore.proxy.isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
            for (final ItemStack drop : drops) {
                block.dropAsStack(world, i, j, k, drop);
            }
            tile.dropBlock();
        }
        return hasBeenBroken;
    }

    public static ItemStack getPickBlock(final World world, final int x, final int y, final int z) {
        final List<ItemStack> list = getBlockDropped((IBlockMetadata) world.getBlock(x, y, z), world, x, y, z, world.getBlockMetadata(x, y, z));
        return list.isEmpty() ? null : list.get(0);
    }

    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int blockMeta, final int fortune) {
        return getBlockDropped(this, world, x, y, z, blockMeta);
    }

    public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
        return breakBlock(this, player, world, x, y, z);
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

    public IIcon getIcon(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4, final int par5) {
        final int metadata = TileEntityMetadata.getTileMetadata(par1IBlockAccess, par2, par3, par4);
        return this.getIcon(par5, metadata);
    }

    public String getBlockName(final ItemStack par1ItemStack) {
        return this.getLocalizedName();
    }

    public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
    }

    public int getPlacedMeta(final ItemStack item, final World world, final int x, final int y, final int z, final ForgeDirection clickedBlock) {
        final int damage = TileEntityMetadata.getItemDamage(item);
        return damage;
    }

    public int getDroppedMeta(final int tileMeta, final int blockMeta) {
        return tileMeta;
    }

    public void dropAsStack(final World world, final int x, final int y, final int z, final ItemStack drop) {
        this.dropBlockAsItem(world, x, y, z, drop);
    }

    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final Block par5, final int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeTileEntity(par2, par3, par4);
    }

    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
        return getPickBlock(world, x, y, z);
    }
}
