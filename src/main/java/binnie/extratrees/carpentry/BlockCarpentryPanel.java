package binnie.extratrees.carpentry;

import binnie.Binnie;
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
        super();
        this.useNeighborBrightness = true;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
        this.setLightOpacity(0);
    }

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        DesignBlock block = this.getCarpentryBlock(world, x, y, z);
        switch (block.getFacing()) {
            case DOWN:
                this.setBlockBounds(0.0F, 0.9375F, 0.0F, 1.0F, 1.0F, 1.0F);
                break;
            case EAST:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0625F, 1.0F, 1.0F);
                break;
            case NORTH:
                this.setBlockBounds(0.0F, 0.0F, 0.9375F, 1.0F, 1.0F, 1.0F);
                break;
            case SOUTH:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F);
                break;
            case UP:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
                break;
            case WEST:
                this.setBlockBounds(0.9375F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            case UNKNOWN:
        }

    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
    }

    public String getBlockName(ItemStack stack) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
        return Binnie.Language.localise(ExtraTrees.instance, "block.woodenpanel.name", new Object[]{block.getDesign().getName()});
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return AxisAlignedBB.getBoundingBox((double) par2 + this.minX, (double) par3 + this.minY, (double) par4 + this.minZ, (double) par2 + this.maxX, (double) ((float) par3) + this.maxY, (double) par4 + this.maxZ);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return super.shouldSideBeRendered(world, x, y, z, side);
    }

    public DesignBlock getCarpentryBlock(IBlockAccess world, int x, int y, int z) {
        return ModuleCarpentry.getCarpentryPanel(this.getDesignSystem(), TileEntityMetadata.getTileMetadata(world, x, y, z));
    }

    public static boolean isValidPanelPlacement(World world, int x, int y, int z, ForgeDirection facing) {
        if (facing == ForgeDirection.UNKNOWN) {
            return false;
        } else {
            int bx = x - facing.offsetX;
            int by = y - facing.offsetY;
            int bz = z - facing.offsetZ;
            Block block = world.getBlock(bx, by, bz);
            return block == null ? false : block.isSideSolid(world, bx, by, bz, facing);
        }
    }

    public int getPlacedMeta(ItemStack item, World world, int x, int y, int z, ForgeDirection clickedBlock) {
        DesignBlock block = ModuleCarpentry.getCarpentryPanel(this.getDesignSystem(), TileEntityMetadata.getItemDamage(item));
        ForgeDirection facing = clickedBlock;
        boolean valid = true;
        if (!isValidPanelPlacement(world, x, y, z, clickedBlock)) {
            valid = false;

            for (ForgeDirection direction : ForgeDirection.values()) {
                if (isValidPanelPlacement(world, x, y, z, direction)) {
                    facing = direction;
                    valid = true;
                    break;
                }
            }
        }

        if (!valid) {
            return -1;
        } else {
            block.setFacing(facing);
            return block.getBlockMetadata(this.getDesignSystem());
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block par5) {
        super.onNeighborBlockChange(world, x, y, z, par5);
        DesignBlock block = this.getCarpentryBlock(world, x, y, z);
        if (!isValidPanelPlacement(world, x, y, z, block.getFacing())) {
            for (ItemStack stack : getBlockDropped(this, world, x, y, z, 0)) {
                this.dropBlockAsItem(world, x, y, z, stack);
            }

            world.setBlockToAir(x, y, z);
        }

    }
}
