package binnie.extratrees.block.decor;

import binnie.extratrees.block.ModuleBlocks;
import binnie.extratrees.genetics.ExtraTreeSpecies;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.ForestryAPI;
import forestry.api.core.Tabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockHedge extends Block implements IBlockFence {
    public BlockHedge() {
        super(Material.leaves);
        this.setCreativeTab(Tabs.tabArboriculture);
        this.setBlockName("hedge");
    }

    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity p_149743_7_) {
        boolean connectNegZ = this.canConnectFenceTo(world, x, y, z - 1);
        boolean connectPosZ = this.canConnectFenceTo(world, x, y, z + 1);
        boolean connectNegX = this.canConnectFenceTo(world, x - 1, y, z);
        boolean connectPosX = this.canConnectFenceTo(world, x + 1, y, z);
        float f = 0.25F;
        float f1 = 0.75F;
        float f2 = 0.25F;
        float f3 = 0.75F;
        if (connectNegZ) {
            f2 = 0.0F;
        }

        if (connectPosZ) {
            f3 = 1.0F;
        }

        if (connectNegZ || connectPosZ) {
            this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, p_149743_7_);
        }

        f2 = 0.25F;
        f3 = 0.75F;
        if (connectNegX) {
            f = 0.0F;
        }

        if (connectPosX) {
            f1 = 1.0F;
        }

        if (connectNegX || connectPosX || !connectNegZ && !connectPosZ) {
            this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
            super.addCollisionBoxesToList(world, x, y, z, aabb, list, p_149743_7_);
        }

        if (connectNegZ) {
            f2 = 0.0F;
        }

        if (connectPosZ) {
            f3 = 1.0F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        boolean connectNegZ = this.canConnectFenceTo(world, x, y, z - 1);
        boolean connectPosZ = this.canConnectFenceTo(world, x, y, z + 1);
        boolean connectNegX = this.canConnectFenceTo(world, x - 1, y, z);
        boolean connectPosX = this.canConnectFenceTo(world, x + 1, y, z);
        float f = 0.25F;
        float f1 = 0.75F;
        float f2 = 0.25F;
        float f3 = 0.75F;
        if (connectNegZ) {
            f2 = 0.0F;
        }

        if (connectPosZ) {
            f3 = 1.0F;
        }

        if (connectNegX) {
            f = 0.0F;
        }

        if (connectPosX) {
            f1 = 1.0F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess p_149655_1_, int p_149655_2_, int p_149655_3_, int p_149655_4_) {
        return false;
    }

    public int getRenderType() {
        return ModuleBlocks.hedgeRenderID;
    }

    public boolean canConnectFenceTo(IBlockAccess p_149826_1_, int p_149826_2_, int p_149826_3_, int p_149826_4_) {
        Block block = p_149826_1_.getBlock(p_149826_2_, p_149826_3_, p_149826_4_);
        return block != this && block != Blocks.fence_gate && !func_149825_a(block) && !block.isLeaves(p_149826_1_, p_149826_2_, p_149826_3_, p_149826_4_) ? (block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false) : true;
    }

    public static boolean func_149825_a(Block p_149825_0_) {
        return BlockFence.canConnect(p_149825_0_);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_) {
    }

    private ExtraTreeSpecies.LeafType getType(int meta) {
        return ExtraTreeSpecies.LeafType.values()[meta % 8];
    }

    private boolean isFull(int meta) {
        return meta / 8 > 0;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int meta) {
        ExtraTreeSpecies.LeafType type = this.getType(meta);
        return ForestryAPI.textureManager.getIcon(this.isFull(meta) ? type.plainUID : type.fancyUID);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < 6; ++i) {
            for (int f = 0; f < 2; ++f) {
                list.add(new ItemStack(item, 1, i + f * 8));
            }
        }

    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        return getColor(world.getBlockMetadata(x, y, z));
    }

    public static int getColor(int meta) {
        ExtraTreeSpecies.LeafType type = ExtraTreeSpecies.LeafType.values()[meta % 6];
        if (type == ExtraTreeSpecies.LeafType.Conifer) {
            return ColorizerFoliage.getFoliageColorPine();
        } else {
            double d0 = 0.5D;
            double d1 = 1.0D;
            return ColorizerFoliage.getFoliageColor(d0, d1);
        }
    }
}
