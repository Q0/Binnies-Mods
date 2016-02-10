package binnie.extratrees.carpentry;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.block.ItemMetadata;
import binnie.core.block.MultipassItemRenderer;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class ModuleCarpentry implements IInitializable {
    static {
        CarpentryManager.carpentryInterface = new CarpentryInterface();
    }

    public static ItemStack getItemStack(final BlockDesign block, final IDesignMaterial type1, final IDesignMaterial type2, final IDesign design) {
        return getItemStack(block, block.getDesignSystem().getMaterialIndex(type1), block.getDesignSystem().getMaterialIndex(type2), CarpentryManager.carpentryInterface.getDesignIndex(design));
    }

    public static ItemStack getItemStack(final BlockDesign block, final int type1, final int type2, final int design) {
        return TileEntityMetadata.getItemStack((Block) block, getMetadata(type1, type2, design, 0, ForgeDirection.UP.ordinal()));
    }

    public static ItemStack getItemStack(final BlockDesign blockC, final DesignBlock block) {
        return getItemStack(blockC, block.getPrimaryMaterial(), block.getSecondaryMaterial(), block.getDesign());
    }

    public static int getMetadata(final int plank1, final int plank2, final int design, final int rotation, final int facing) {
        return plank1 + (plank2 << 8) + (design << 16) + (rotation << 26) + (facing << 28);
    }

    public static DesignBlock getDesignBlock(final IDesignSystem system, final int meta) {
        final int plankID1 = meta & 0xFF;
        final int plankID2 = meta >> 8 & 0xFF;
        final int tile = meta >> 16 & 0x3FF;
        final int rotation = meta >> 26 & 0x3;
        final int axis = meta >> 28 & 0x7;
        final IDesignMaterial type1 = system.getMaterial(plankID1);
        final IDesignMaterial type2 = system.getMaterial(plankID2);
        final IDesign type3 = CarpentryManager.carpentryInterface.getDesign(tile);
        return new DesignBlock(system, type1, type2, type3, rotation, ForgeDirection.getOrientation(axis));
    }

    public static DesignBlock getCarpentryPanel(final IDesignSystem system, final int meta) {
        final DesignBlock block = getDesignBlock(system, meta);
        block.setPanel();
        return block;
    }

    public static int getBlockMetadata(final IDesignSystem system, final DesignBlock block) {
        final int plank1 = system.getMaterialIndex(block.getPrimaryMaterial());
        final int plank2 = system.getMaterialIndex(block.getSecondaryMaterial());
        final int design = CarpentryManager.carpentryInterface.getDesignIndex(block.getDesign());
        final int rotation = block.getRotation();
        final int facing = block.getFacing().ordinal();
        return getMetadata(plank1, plank2, design, rotation, facing);
    }

    public static int getItemMetadata(final IDesignSystem system, final DesignBlock block) {
        final int plank1 = system.getMaterialIndex(block.getPrimaryMaterial());
        final int plank2 = system.getMaterialIndex(block.getSecondaryMaterial());
        final int design = CarpentryManager.carpentryInterface.getDesignIndex(block.getDesign());
        return getMetadata(plank1, plank2, design, 0, ForgeDirection.UP.ordinal());
    }

    @Override
    public void preInit() {
        ExtraTrees.blockCarpentry = new BlockCarpentry();
        ExtraTrees.blockPanel = new BlockCarpentryPanel();
        ExtraTrees.blockStained = new BlockStainedDesign();
        GameRegistry.registerBlock((Block) ExtraTrees.blockCarpentry, (Class) ItemMetadata.class, "carpentry");
        GameRegistry.registerBlock((Block) ExtraTrees.blockPanel, (Class) ItemMetadata.class, "panel");
        GameRegistry.registerBlock((Block) ExtraTrees.blockStained, (Class) ItemMetadata.class, "stainedglass");
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock((Block) ExtraTrees.blockCarpentry), (IItemRenderer) new MultipassItemRenderer());
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock((Block) ExtraTrees.blockStained), (IItemRenderer) new MultipassItemRenderer());
        MinecraftForge.EVENT_BUS.register((Object) ExtraTrees.blockCarpentry);
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock((Block) ExtraTrees.blockPanel), (IItemRenderer) new MultipassItemRenderer());
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {
        for (final EnumDesign design : EnumDesign.values()) {
            CarpentryManager.carpentryInterface.registerDesign(design.ordinal(), design);
        }
    }

    enum Axis {
        Y,
        X,
        Z;
    }
}
