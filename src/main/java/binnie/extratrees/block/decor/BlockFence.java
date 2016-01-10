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
        this.setResistance(5.0F);
        this.setHardness(2.0F);
        this.setStepSound(soundTypeWood);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        for (IFenceProvider type : PlankType.ExtraTreePlanks.values()) {
            itemList.add(type.getFence());
        }

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        return tile != null ? this.getIcon(side, tile.getTileMetadata()) : super.getIcon(world, x, y, z, side);
    }

    public FenceDescription getDescription(int meta) {
        return WoodManager.getFenceDescription(meta);
    }

    public IIcon getIcon(int side, int meta) {
        return this.getDescription(meta).getPlankType().getIcon();
    }

    public int getRenderType() {
        return ExtraTrees.fenceID;
    }

    public void dropAsStack(World world, int x, int y, int z, ItemStack drop) {
        this.dropBlockAsItem(world, x, y, z, drop);
    }

    public ArrayList getDrops(World world, int x, int y, int z, int blockMeta, int fortune) {
        return BlockMetadata.getBlockDropped(this, world, x, y, z, blockMeta);
    }

    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        return BlockMetadata.breakBlock(this, player, world, x, y, z);
    }

    public TileEntity createNewTileEntity(World var1, int i) {
        return new TileEntityMetadata();
    }

    public boolean hasTileEntity(int meta) {
        return true;
    }

    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6) {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
        return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
    }

    public int getPlacedMeta(ItemStack stack, World world, int x, int y, int z, ForgeDirection clickedBlock) {
        return TileEntityMetadata.getItemDamage(stack);
    }

    public int getDroppedMeta(int blockMeta, int tileMeta) {
        return tileMeta;
    }

    public String getBlockName(ItemStack par1ItemStack) {
        int meta = TileEntityMetadata.getItemDamage(par1ItemStack);
        return Binnie.Language.localise(ExtraTrees.instance, "block.woodfence.name", new Object[]{this.getDescription(meta).getPlankType().getName()});
    }

    public void getBlockTooltip(ItemStack par1ItemStack, List par3List) {
    }

    public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
        return true;
    }

    public boolean canConnectFenceTo(IBlockAccess world, int x, int y, int z) {
        if (this.isFence(world, x, y, z)) {
            return true;
        } else {
            Block block = world.getBlock(x, y, z);
            return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock();
        }
    }

    public boolean isFence(IBlockAccess world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        return canConnect(block);
    }

    public static boolean canConnect(Block block) {
        return block == ((ItemBlock) Mods.Forestry.item("fences")).field_150939_a || block == ((ItemBlock) Mods.Forestry.item("fences2")).field_150939_a || block == Blocks.fence || block == Blocks.fence_gate || block == Blocks.nether_brick_fence || block instanceof IBlockFence || block == ExtraTrees.blockGate;
    }

    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeTileEntity(par2, par3, par4);
    }

    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return 20;
    }

    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return true;
    }

    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return 5;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_) {
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return BlockMetadata.getPickBlock(world, x, y, z);
    }
}
