package binnie.extratrees.carpentry;

import binnie.core.BinnieCore;
import binnie.core.block.BlockMetadata;
import binnie.core.block.IMultipassBlock;
import binnie.core.block.MultipassBlockRenderer;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IToolHammer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import java.util.List;

public abstract class BlockDesign extends BlockMetadata implements IMultipassBlock {
    IDesignSystem designSystem;
    public static final ForgeDirection[] RENDER_DIRECTIONS = new ForgeDirection[]{ForgeDirection.DOWN, ForgeDirection.UP, ForgeDirection.EAST, ForgeDirection.WEST, ForgeDirection.NORTH, ForgeDirection.SOUTH};

    @SubscribeEvent
    public void onClick(PlayerInteractEvent event) {
        if (event.action == Action.RIGHT_CLICK_BLOCK) {
            World world = event.entityPlayer.worldObj;
            EntityPlayer player = event.entityPlayer;
            int x = event.x;
            int y = event.y;
            int z = event.z;
            if (world.getBlock(x, y, z) instanceof BlockDesign) {
                BlockDesign blockC = (BlockDesign) world.getBlock(x, y, z);
                ItemStack item = player.getHeldItem();
                if (item != null) {
                    if (item.getItem() instanceof IToolHammer) {
                        if (((IToolHammer) item.getItem()).isActive(item)) {
                            DesignBlock block = blockC.getCarpentryBlock(world, x, y, z);
                            TileEntityMetadata tile = (TileEntityMetadata) world.getTileEntity(x, y, z);
                            block.rotate(event.face, item, player, world, x, y, z);
                            int meta = block.getBlockMetadata(blockC.getDesignSystem());
                            tile.setTileMetadata(meta, true);
                        }
                    }
                }
            }
        }
    }

    public BlockDesign(IDesignSystem system, Material material) {
        super(material);
        this.designSystem = system;
    }

    public abstract ItemStack getCreativeStack(IDesign var1);

    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
        for (IDesign design : CarpentryManager.carpentryInterface.getSortedDesigns()) {
            itemList.add(this.getCreativeStack(design));
        }

    }

    public IDesignSystem getDesignSystem() {
        return this.designSystem;
    }

    public int getRenderType() {
        return BinnieCore.multipassRenderID;
    }

    public String getBlockName(ItemStack stack) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
        return this.getBlockName(block);
    }

    public abstract String getBlockName(DesignBlock var1);

    public DesignBlock getCarpentryBlock(IBlockAccess world, int x, int y, int z) {
        return ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getTileMetadata(world, x, y, z));
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        DesignBlock block = this.getCarpentryBlock(world, x, y, z);
        return MultipassBlockRenderer.getLayer() > 0 ? block.getSecondaryColour() : block.getPrimaryColour();
    }

    public int colorMultiplier(int meta) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), meta);
        return MultipassBlockRenderer.getLayer() > 0 ? block.getSecondaryColour() : block.getPrimaryColour();
    }

    public IIcon getIcon(int side, int damage) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), damage);
        IIcon icon = MultipassBlockRenderer.getLayer() > 0 ? block.getSecondaryIcon(this.getDesignSystem(), RENDER_DIRECTIONS[side]) : block.getPrimaryIcon(this.getDesignSystem(), RENDER_DIRECTIONS[side]);
        return icon;
    }

    public void getBlockTooltip(ItemStack stack, List par3List) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), TileEntityMetadata.getItemDamage(stack));
        if (block.getPrimaryMaterial() != block.getSecondaryMaterial()) {
            par3List.add(block.getPrimaryMaterial().getName() + " and " + block.getSecondaryMaterial().getName());
        } else {
            par3List.add(block.getPrimaryMaterial().getName());
        }

    }

    public int primaryColor(int damage) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), damage);
        return block.getPrimaryColour();
    }

    public int secondaryColor(int damage) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), damage);
        return block.getSecondaryColour();
    }

    public ItemStack getItemStack(int plank1, int plank2, int design) {
        return TileEntityMetadata.getItemStack(this, getMetadata(plank1, plank2, design));
    }

    public static int getMetadata(int plank1, int plank2, int design) {
        return plank1 + (plank2 << 9) + (design << 18);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        for (IDesignSystem system : DesignerManager.instance.getDesignSystems()) {
            system.registerIcons(register);
        }

    }

    public int getDroppedMeta(int blockMeta, int tileMeta) {
        DesignBlock block = ModuleCarpentry.getDesignBlock(this.getDesignSystem(), tileMeta);
        return block.getItemMetadata(this.getDesignSystem());
    }

    public int getNumberOfPasses() {
        return 2;
    }
}
