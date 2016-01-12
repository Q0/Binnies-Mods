package binnie.extratrees.block.decor;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.IFenceProvider;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.block.WoodManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class BlockFence extends net.minecraft.block.BlockFence implements IBlockMetadata, IBlockFence {
    public BlockFence() {
        super("", Material.wood);
        this.setCreativeTab(Tabs.tabArboriculture);
        this.setBlockName("fence");
        this.setResistance(5.0f);
        this.setHardness(2.0f);
        this.setStepSound(BlockFence.soundTypeWood);
    }

    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
        for (final IFenceProvider type : PlankType.ExtraTreePlanks.values()) {
            itemList.add(type.getFence());
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

    public FenceDescription getDescription(final int meta) {
        return WoodManager.getFenceDescription(meta);
    }

    public IIcon getIcon(final int side, final int meta) {
        return this.getDescription(meta).getPlankType().getIcon();
    }

    public int getRenderType() {
        return ExtraTrees.fenceID;
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

    public int getPlacedMeta(final ItemStack stack, final World world, final int x, final int y, final int z, final ForgeDirection clickedBlock) {
        return TileEntityMetadata.getItemDamage(stack);
    }

    public int getDroppedMeta(final int blockMeta, final int tileMeta) {
        return tileMeta;
    }

    public String getBlockName(final ItemStack par1ItemStack) {
        final int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
        return Binnie.Language.localise(ExtraTrees.instance, "block.woodfence.name", this.getDescription(meta).getPlankType().getName());
    }

    public void getBlockTooltip(final ItemStack par1ItemStack, final List par3List) {
    }

    public boolean canPlaceTorchOnTop(final World world, final int x, final int y, final int z) {
        return true;
    }

    public boolean canConnectFenceTo(final IBlockAccess world, final int x, final int y, final int z) {
        if (!this.isFence(world, x, y, z)) {
            final Block block = world.getBlock(x, y, z);
            return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock();
        }
        return true;
    }

    public boolean isFence(final IBlockAccess world, final int x, final int y, final int z) {
        final Block block = world.getBlock(x, y, z);
        return canConnect(block);
    }

    public static boolean canConnect(final Block block) {
        return block == ((ItemBlock) Mods.Forestry.item("fences")).field_150939_a || block == ((ItemBlock) Mods.Forestry.item("fences2")).field_150939_a || block == Blocks.fence || block == Blocks.fence_gate || block == Blocks.nether_brick_fence || block instanceof IBlockFence || block == ExtraTrees.blockGate;
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

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
    }

    public ItemStack getPickBlock(final MovingObjectPosition target, final World world, final int x, final int y, final int z) {
        return BlockMetadata.getPickBlock(world, x, y, z);
    }
}
