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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class ModuleCarpentry implements IInitializable {
    public ModuleCarpentry() {
        super();
    }

    public void preInit() {
        ExtraTrees.blockCarpentry = new BlockCarpentry();
        ExtraTrees.blockPanel = new BlockCarpentryPanel();
        ExtraTrees.blockStained = new BlockStainedDesign();
        GameRegistry.registerBlock(ExtraTrees.blockCarpentry, ItemMetadata.class, "carpentry");
        GameRegistry.registerBlock(ExtraTrees.blockPanel, ItemMetadata.class, "panel");
        GameRegistry.registerBlock(ExtraTrees.blockStained, ItemMetadata.class, "stainedglass");
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockCarpentry), new MultipassItemRenderer());
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockStained), new MultipassItemRenderer());
        MinecraftForge.EVENT_BUS.register(ExtraTrees.blockCarpentry);
        BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockPanel), new MultipassItemRenderer());
    }

    public void init() {
    }

    public void postInit() {
        for (EnumDesign design : EnumDesign.values()) {
            CarpentryManager.carpentryInterface.registerDesign(design.ordinal(), design);
        }

    }

    public static ItemStack getItemStack(BlockDesign block, IDesignMaterial type1, IDesignMaterial type2, IDesign design) {
        return getItemStack(block, block.getDesignSystem().getMaterialIndex(type1), block.getDesignSystem().getMaterialIndex(type2), CarpentryManager.carpentryInterface.getDesignIndex(design));
    }

    public static ItemStack getItemStack(BlockDesign block, int type1, int type2, int design) {
        return TileEntityMetadata.getItemStack(block, getMetadata(type1, type2, design, 0, ForgeDirection.UP.ordinal()));
    }

    public static ItemStack getItemStack(BlockDesign blockC, DesignBlock block) {
        return getItemStack(blockC, block.getPrimaryMaterial(), block.getSecondaryMaterial(), block.getDesign());
    }

    public static int getMetadata(int plank1, int plank2, int design, int rotation, int facing) {
        return plank1 + (plank2 << 8) + (design << 16) + (rotation << 26) + (facing << 28);
    }

    public static DesignBlock getDesignBlock(IDesignSystem system, int meta) {
        int plankID1 = meta & 255;
        int plankID2 = meta >> 8 & 255;
        int tile = meta >> 16 & 1023;
        int rotation = meta >> 26 & 3;
        int axis = meta >> 28 & 7;
        IDesignMaterial type1 = system.getMaterial(plankID1);
        IDesignMaterial type2 = system.getMaterial(plankID2);
        IDesign type = CarpentryManager.carpentryInterface.getDesign(tile);
        return new DesignBlock(system, type1, type2, type, rotation, ForgeDirection.getOrientation(axis));
    }

    public static DesignBlock getCarpentryPanel(IDesignSystem system, int meta) {
        DesignBlock block = getDesignBlock(system, meta);
        block.setPanel();
        return block;
    }

    public static int getBlockMetadata(IDesignSystem system, DesignBlock block) {
        int plank1 = system.getMaterialIndex(block.getPrimaryMaterial());
        int plank2 = system.getMaterialIndex(block.getSecondaryMaterial());
        int design = CarpentryManager.carpentryInterface.getDesignIndex(block.getDesign());
        int rotation = block.getRotation();
        int facing = block.getFacing().ordinal();
        return getMetadata(plank1, plank2, design, rotation, facing);
    }

    public static int getItemMetadata(IDesignSystem system, DesignBlock block) {
        int plank1 = system.getMaterialIndex(block.getPrimaryMaterial());
        int plank2 = system.getMaterialIndex(block.getSecondaryMaterial());
        int design = CarpentryManager.carpentryInterface.getDesignIndex(block.getDesign());
        return getMetadata(plank1, plank2, design, 0, ForgeDirection.UP.ordinal());
    }

    static {
        CarpentryManager.carpentryInterface = new CarpentryInterface();
    }

    static enum Axis {
        Y,
        X,
        Z;

        private Axis() {
        }
    }
}
