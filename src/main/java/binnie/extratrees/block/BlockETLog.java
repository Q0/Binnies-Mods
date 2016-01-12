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
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
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

public class BlockETLog extends BlockLog implements IBlockMetadata {
    public BlockETLog() {
        this.setCreativeTab(Tabs.tabArboriculture);
        this.setBlockName("log");
        this.setResistance(5.0f);
        this.setHardness(2.0f);
        this.setStepSound(BlockETLog.soundTypeWood);
    }

    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (int i = 0; i < ILogType.ExtraTreeLog.values().length; ++i) {
            itemList.add(TileEntityMetadata.getItemStack((Block) this, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
        final TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        if (tile != null) {
            return this.getIcon(side, tile.getTileMetadata(), world.getBlockMetadata(x, y, z));
        }
        return super.getIcon(world, x, y, z, side);
    }

    public IIcon getIcon(final int side, final int tileMeta, final int blockMeta) {
        final int oriented = blockMeta & 0xC;
        final ILogType.ExtraTreeLog log = ILogType.ExtraTreeLog.values()[tileMeta];
        switch (oriented) {
            case 4: {
                if (side > 3) {
                    return log.getTrunk();
                }
                return log.getBark();
            }
            case 8: {
                if (side == 2 || side == 3) {
                    return log.getTrunk();
                }
                return log.getBark();
            }
            default: {
                if (side < 2) {
                    return log.getTrunk();
                }
                return log.getBark();
            }
        }
    }

    public IIcon getIcon(final int side, final int tileMeta) {
        return this.getIcon(side, tileMeta, 0);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister register) {
        ILogType.ExtraTreeLog.registerIcons(register);
    }

    public int getRenderType() {
        return 31;
    }

    public void dropAsStack(final World world, final int x, final int y, final int z, final ItemStack drop) {
        this.dropBlockAsItem(world, x, y, z, drop);
    }

    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int blockMeta, final int fortune) {
        return BlockMetadata.getBlockDropped(this, world, x, y, z, blockMeta);
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

    public int getDroppedMeta(final int blockMeta, final int tileMeta) {
        return tileMeta;
    }

    public String getBlockName(final ItemStack par1ItemStack) {
        final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
        return Binnie.Language.localise(ExtraTrees.instance, "block.log.name", ILogType.ExtraTreeLog.values()[meta].getName());
    }

    public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
    }

    public boolean canSustainLeaves(final IBlockAccess world, final int x, final int y, final int z) {
        return true;
    }

    public int getPlacedMeta(final ItemStack stack, final World world, final int x, final int y, final int z, final ForgeDirection clickedBlock) {
        return TileEntityMetadata.getItemDamage(stack);
    }

    public int onBlockPlaced(final World world, final int x, final int y, final int z, final int side, final float par6, final float par7, final float par8, final int meta) {
        byte b0 = 0;
        switch (side) {
            case 0:
            case 1: {
                b0 = 0;
                break;
            }
            case 2:
            case 3: {
                b0 = 8;
                break;
            }
            case 4:
            case 5: {
                b0 = 4;
                break;
            }
        }
        return b0;
    }

    public void breakBlock(final World par1World, final int par2, final int par3, final int par4, final Block par5, final int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeTileEntity(par2, par3, par4);
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
