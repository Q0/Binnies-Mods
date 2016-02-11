package binnie.extratrees.carpentry;

import binnie.Binnie;
import binnie.core.block.BlockMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockCarpentryPanel extends BlockCarpentry {
    public BlockCarpentryPanel() {
        this.useNeighborBrightness = true;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
        this.setLightOpacity(0);
    }

    public static boolean isValidPanelPlacement(final World world, final int x, final int y, final int z, final ForgeDirection facing) {
        if (facing == ForgeDirection.UNKNOWN) {
            return false;
        }
        final int bx = x - facing.offsetX;
        final int by = y - facing.offsetY;
        final int bz = z - facing.offsetZ;
        final Block block = world.getBlock(bx, by, bz);
        return block != null && block.isSideSolid(world, bx, by, bz, facing);
    }

    @Override
    public void getSubBlocks(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
    }

    public void setBlockBoundsBasedOnState(final IBlockAccess world, final int x, final int y, final int z) {
        final DesignBlock block = this.getCarpentryBlock(world, x, y, z);
        switch (block.getFacing()) {
            case DOWN: {
                this.setBlockBounds(0.0f, 0.9375f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case EAST: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.0625f, 1.0f, 1.0f);
                break;
            }
            case NORTH: {
                this.setBlockBounds(0.0f, 0.0f, 0.9375f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case SOUTH: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0625f);
                break;
            }
            case UP: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
                break;
            }
            case WEST: {
                this.setBlockBounds(0.9375f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
        }
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.0625f, 1.0f);
    }

    @Override
    public String getBlockName(final ItemStack stack) {
        final DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
        return Binnie.Language.localise(ExtraTrees.instance, "block.woodenpanel.name", block.getDesign().getName());
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return AxisAlignedBB.getBoundingBox(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockAccess world, final int x, final int y, final int z, final int side) {
        return super.shouldSideBeRendered(world, x, y, z, side);
    }

    @Override
    public DesignBlock getCarpentryBlock(final IBlockAccess world, final int x, final int y, final int z) {
        return ModuleCarpentry.getCarpentryPanel(this.getDesignSystem(), TileEntityMetadata.getTileMetadata(world, x, y, z));
    }

    @Override
    public int getPlacedMeta(final ItemStack item, final World world, final int x, final int y, final int z, final ForgeDirection clickedBlock) {
        final DesignBlock block = ModuleCarpentry.getCarpentryPanel(this.getDesignSystem(), TileEntityMetadata.getItemDamage(item));
        ForgeDirection facing = clickedBlock;
        boolean valid = true;
        if (!isValidPanelPlacement(world, x, y, z, facing)) {
            valid = false;
            for (final ForgeDirection direction : ForgeDirection.values()) {
                if (isValidPanelPlacement(world, x, y, z, direction)) {
                    facing = direction;
                    valid = true;
                    break;
                }
            }
        }
        if (!valid) {
            return -1;
        }
        block.setFacing(facing);
        return block.getBlockMetadata(this.getDesignSystem());
    }

    public void onNeighborBlockChange(final World world, final int x, final int y, final int z, final Block par5) {
        super.onNeighborBlockChange(world, x, y, z, par5);
        final DesignBlock block = this.getCarpentryBlock(world, x, y, z);
        if (!isValidPanelPlacement(world, x, y, z, block.getFacing())) {
            for (final ItemStack stack : BlockMetadata.getBlockDropped(this, world, x, y, z, 0)) {
                this.dropBlockAsItem(world, x, y, z, stack);
            }
            world.setBlockToAir(x, y, z);
        }
    }
}
