package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IBlockMetadata;
import binnie.core.block.TileEntityMetadata;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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

public class BlockCeramic extends Block implements IBlockMetadata {
    public BlockCeramic() {
        super(Material.rock);
        this.setHardness(1.0F);
        this.setResistance(5.0F);
        this.setBlockName("ceramic");
        this.setCreativeTab(CreativeTabBotany.instance);
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
        return EnumFlowerColor.get(meta).getName() + " Ceramic Block";
    }

    public void getBlockTooltip(ItemStack par1ItemStack, List par3List) {
    }

    public void dropAsStack(World world, int x, int y, int z, ItemStack drop) {
        this.dropBlockAsItem(world, x, y, z, drop);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        for (EnumFlowerColor c : EnumFlowerColor.values()) {
            itemList.add(TileEntityMetadata.getItemStack(this, c.ordinal()));
        }

    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        return tile != null ? this.getIcon(side, tile.getTileMetadata()) : super.getIcon(world, x, y, z, side);
    }

    public IIcon getIcon(int side, int meta) {
        return this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.blockIcon = Botany.proxy.getIcon(register, "ceramic");
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        TileEntityMetadata tile = TileEntityMetadata.getTile(world, x, y, z);
        return tile != null ? this.getRenderColor(tile.getTileMetadata()) : 16777215;
    }

    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
        par1World.removeTileEntity(par2, par3, par4);
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        return BlockMetadata.getPickBlock(world, x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int meta) {
        return EnumFlowerColor.get(meta).getColor(false);
    }
}
