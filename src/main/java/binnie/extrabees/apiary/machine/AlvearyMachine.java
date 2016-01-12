package binnie.extrabees.apiary.machine;

import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.MachineRendererBlock;
import binnie.core.resource.BinnieResource;
import binnie.extrabees.apiary.ModuleApiary;
import binnie.extrabees.apiary.TileExtraBeeAlveary;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public enum AlvearyMachine implements IMachineType {
    Mutator((Class<? extends MachinePackage>) AlvearyMutator.PackageAlvearyMutator.class),
    Frame((Class<? extends MachinePackage>) AlvearyFrame.PackageAlvearyFrame.class),
    RainShield((Class<? extends MachinePackage>) AlvearyRainShield.PackageAlvearyRainShield.class),
    Lighting((Class<? extends MachinePackage>) AlvearyLighting.PackageAlvearyLighting.class),
    Stimulator((Class<? extends MachinePackage>) AlvearyStimulator.PackageAlvearyStimulator.class),
    Hatchery((Class<? extends MachinePackage>) AlvearyHatchery.PackageAlvearyHatchery.class),
    Transmission((Class<? extends MachinePackage>) AlvearyTransmission.PackageAlvearyTransmission.class);

    Class<? extends MachinePackage> clss;

    private AlvearyMachine(final Class<? extends MachinePackage> clss) {
        this.clss = clss;
    }

    @Override
    public Class<? extends MachinePackage> getPackageClass() {
        return this.clss;
    }

    public ItemStack get(final int size) {
        return new ItemStack(ModuleApiary.blockComponent, size, this.ordinal());
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public abstract static class AlvearyPackage extends MachinePackage {
        BinnieResource machineTexture;

        public AlvearyPackage(final String id, final BinnieResource machineTexture, final boolean powered) {
            super(id, powered);
            this.machineTexture = machineTexture;
        }

        @Override
        public void createMachine(final Machine machine) {
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileExtraBeeAlveary(this);
        }

        @Override
        public void register() {
        }

        @Override
        public void renderMachine(final Machine machine, final double x, final double y, final double z, final float var8, final RenderBlocks renderer) {
            MachineRendererBlock.instance.renderMachine(this.machineTexture, x, y, z, var8);
        }
    }
}
