package binnie.botany.flower;

import binnie.botany.Botany;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerType;
import binnie.botany.core.BotanyCore;
import binnie.botany.gardening.Gardening;
import binnie.botany.genetics.EnumFlowerType;
import binnie.core.BinnieCore;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockFlower extends BlockContainer {
    public BlockFlower() {
        super(Material.plants);
        final float f = 0.2f;
        this.setBlockBounds(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 3.0f, 0.5f + f);
        this.setTickRandomly(true);
        this.setBlockName("flower");
    }

    public int getRenderType() {
        return RendererBotany.renderID;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        for (final EnumFlowerType type : EnumFlowerType.values()) {
            type.registerIcons(par1IconRegister);
        }
    }

    public TileEntity createNewTileEntity(final World var1, final int i) {
        return new TileEntityFlower();
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
        return null;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase living, final ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, living, stack);
        final TileEntity flower = world.getTileEntity(x, y, z);
        if (!BinnieCore.proxy.isSimulating(world)) {
            if (flower != null && flower instanceof TileEntityFlower) {
                final IFlower f = BotanyCore.getFlowerRoot().getMember(stack);
                ((TileEntityFlower) flower).setRender(new TileEntityFlower.RenderInfo(f, (TileEntityFlower) flower));
            }
            return;
        }
        final TileEntity below = world.getTileEntity(x, y - 1, z);
        if (flower != null && flower instanceof TileEntityFlower) {
            if (below instanceof TileEntityFlower) {
                ((TileEntityFlower) flower).setSection(((TileEntityFlower) below).getSection());
            } else {
                final GameProfile owner = (living instanceof EntityPlayer) ? ((EntityPlayer) living).getGameProfile() : null;
                ((TileEntityFlower) flower).create(stack, owner);
            }
        }
        Gardening.tryGrowSection(world, x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final IBlockAccess world, final int x, final int y, final int z, final int side) {
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityFlower) {
            final TileEntityFlower f = (TileEntityFlower) tile;
            final EnumFlowerStage stage = (f.getAge() == 0) ? EnumFlowerStage.SEED : EnumFlowerStage.FLOWER;
            final IFlowerType flower = f.getType();
            final int section = f.getRenderSection();
            final boolean flowered = f.isFlowered();
            return (RendererBotany.pass == 0) ? flower.getStem(stage, flowered, section) : ((RendererBotany.pass == 1) ? flower.getPetalIcon(stage, flowered, section) : flower.getVariantIcon(stage, flowered, section));
        }
        return super.getIcon(world, x, y, z, side);
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(final IBlockAccess world, final int x, final int y, final int z) {
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityFlower) {
            final TileEntityFlower f = (TileEntityFlower) tile;
            return (RendererBotany.pass == 0) ? f.getStemColour() : ((RendererBotany.pass == 1) ? f.getPrimaryColour() : f.getSecondaryColour());
        }
        return 16777215;
    }

    public boolean canPlaceBlockAt(final World world, final int x, final int y, final int z) {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
    }

    protected boolean canPlaceBlockOn(final Block block) {
        return block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland || Gardening.isSoil(block);
    }

    public void onNeighborBlockChange(final World world, final int x, final int y, final int z, final Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        this.checkAndDropBlock(world, x, y, z);
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityFlower) {
            final TileEntityFlower flower = (TileEntityFlower) tile;
            if (flower.getSection() == 0 && flower.getFlower() != null && flower.getFlower().getAge() > 0 && flower.getFlower().getGenome().getPrimary().getType().getSections() > 1 && world.getBlock(x, y + 1, z) != Botany.flower) {
                this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                world.setBlockToAir(x, y, z);
            }
        }
    }

    public void updateTick(final World world, final int x, final int y, final int z, final Random rand) {
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityFlower) {
            ((TileEntityFlower) tile).randomUpdate(rand);
            this.checkAndDropBlock(world, x, y, z);
            return;
        }
        world.setBlockToAir(x, y, z);
    }

    protected void checkAndDropBlock(final World world, final int x, final int y, final int z) {
        if (!this.canBlockStay(world, x, y, z)) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    public boolean canBlockStay(final World world, final int x, final int y, final int z) {
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).getSection() > 0) {
            return world.getBlock(x, y - 1, z) == Botany.flower;
        }
        return this.canPlaceBlockOn(world.getBlock(x, y - 1, z));
    }

    public ArrayList<ItemStack> getDrops(final World world, final int x, final int y, final int z, final int blockMeta, final int fortune) {
        final ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
        final TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileEntityFlower && ((TileEntityFlower) tile).getSection() == 0) {
            final ItemStack flower = ((TileEntityFlower) tile).getItemStack();
            if (flower != null) {
                drops.add(flower);
            }
        }
        return drops;
    }

    public boolean removedByPlayer(final World world, final EntityPlayer player, final int x, final int y, final int z) {
        final List<ItemStack> drops = this.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        final boolean hasBeenBroken = world.setBlockToAir(x, y, z);
        if (hasBeenBroken && BinnieCore.proxy.isSimulating(world) && drops.size() > 0 && (player == null || !player.capabilities.isCreativeMode)) {
            for (final ItemStack drop : drops) {
                this.dropBlockAsItem(world, x, y, z, drop);
            }
        }
        return hasBeenBroken;
    }
}
