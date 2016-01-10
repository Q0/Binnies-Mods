package binnie.extratrees.machines;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.*;
import binnie.core.machines.component.IInteraction;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.core.ExtraTreesGUID;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public enum ExtraTreeMachine implements IMachineType {
    Lumbermill(Lumbermill.PackageLumbermill.class),
    Woodworker(Designer.PackageWoodworker.class),
    Panelworker(Designer.PackagePanelworker.class),
    /**
     * @deprecated
     */
    @Deprecated
    Nursery(Nursery.PackageNursery.class),
    Press(Press.PackagePress.class),
    Brewery(Brewery.PackageBrewery.class),
    Distillery(Distillery.PackageDistillery.class),
    Glassworker(Designer.PackageGlassworker.class),
    Tileworker(Designer.PackageTileworker.class);

    Class clss;

    private ExtraTreeMachine(Class clss) {
        this.clss = clss;
    }

    public Class getPackageClass() {
        return this.clss;
    }

    public boolean isActive() {
        return this == Tileworker ? BinnieCore.isBotanyActive() : this != Nursery;
    }

    public ItemStack get(int i) {
        return new ItemStack(ExtraTrees.blockMachine, i, this.ordinal());
    }

    public static class ComponentExtraTreeGUI extends MachineComponent implements IInteraction.RightClick {
        ExtraTreesGUID id;

        public ComponentExtraTreeGUI(Machine machine, ExtraTreesGUID id) {
            super(machine);
            this.id = id;
        }

        public void onRightClick(World world, EntityPlayer player, int x, int y, int z) {
            ExtraTrees.proxy.openGui(this.id, player, x, y, z);
        }
    }

    public abstract static class PackageExtraTreeMachine extends MachinePackage {
        BinnieResource textureName;

        protected PackageExtraTreeMachine(String uid, String textureName, boolean powered) {
            super(uid, powered);
            this.textureName = Binnie.Resource.getFile(ExtraTrees.instance, ResourceType.Tile, textureName);
        }

        protected PackageExtraTreeMachine(String uid, BinnieResource textureName, boolean powered) {
            super(uid, powered);
            this.textureName = textureName;
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }

        public void renderMachine(Machine machine, double x, double y, double z, float var8, RenderBlocks renderer) {
            MachineRendererForestry.renderMachine(this.textureName.getShortPath(), x, y, z, var8);
        }
    }
}
