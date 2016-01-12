package binnie.core.machines.storage;

import binnie.core.BinnieCore;
import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

enum Compartment implements IMachineType {
    Compartment((Class<? extends MachinePackage>) StandardCompartment.PackageCompartment.class),
    CompartmentCopper((Class<? extends MachinePackage>) StandardCompartment.PackageCompartmentCopper.class),
    CompartmentBronze((Class<? extends MachinePackage>) StandardCompartment.PackageCompartmentBronze.class),
    CompartmentIron((Class<? extends MachinePackage>) StandardCompartment.PackageCompartmentIron.class),
    CompartmentGold((Class<? extends MachinePackage>) StandardCompartment.PackageCompartmentGold.class),
    CompartmentDiamond((Class<? extends MachinePackage>) StandardCompartment.PackageCompartmentDiamond.class);

    Class<? extends MachinePackage> clss;

    private Compartment(final Class<? extends MachinePackage> clss) {
        this.clss = clss;
    }

    @Override
    public Class<? extends MachinePackage> getPackageClass() {
        return this.clss;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public ItemStack get(final int i) {
        return new ItemStack((Block) BinnieCore.packageCompartment.getBlock(), i, this.ordinal());
    }

    public abstract static class PackageCompartment extends MachinePackage {
        private BinnieResource renderTexture;

        protected PackageCompartment(final String uid, final IBinnieTexture renderTexture) {
            super(uid, false);
            this.renderTexture = renderTexture.getTexture();
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        @Override
        public void register() {
        }

        @Override
        public void renderMachine(final Machine machine, final double x, final double y, final double z, final float var8, final RenderBlocks renderer) {
            MachineRendererCompartment.instance.renderMachine(machine, 16777215, this.renderTexture, x, y, z, var8);
        }
    }
}
