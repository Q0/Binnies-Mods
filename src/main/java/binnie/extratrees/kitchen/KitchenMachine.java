package binnie.extratrees.kitchen;

import binnie.Binnie;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public enum KitchenMachine implements IMachineType {
    Worktop((Class) null),
    Cupboard((Class) null),
    BottleRack(BottleRack.PackageBottleRack.class);

    Class clss;

    private KitchenMachine(Class clss) {
        this.clss = clss;
    }

    public Class getPackageClass() {
        return this.clss;
    }

    public boolean isActive() {
        return this.clss != null;
    }

    public ItemStack get(int i) {
        return new ItemStack(ExtraTrees.blockKitchen, i, this.ordinal());
    }

    public abstract static class PackageKitchenMachine extends MachinePackage {
        BinnieResource textureName;

        protected PackageKitchenMachine(String uid, String textureName) {
            super(uid, false);
            this.textureName = Binnie.Resource.getFile(ExtraTrees.instance, ResourceType.Tile, textureName);
        }

        protected PackageKitchenMachine(String uid, BinnieResource textureName) {
            super(uid, false);
            this.textureName = textureName;
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }

        public void renderMachine(Machine machine, double x, double y, double z, float var8, RenderBlocks renderer) {
            MachineRendererKitchen.instance.renderMachine(machine, this.textureName, x, y, z, var8, renderer);
        }
    }
}
