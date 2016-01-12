package binnie.genetics.machine;

import binnie.core.machines.IMachineType;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.TileEntityMachine;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.genetics.Genetics;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public enum GeneticMachine implements IMachineType {
    Isolator((Class<? extends MachinePackage>) Isolator.PackageIsolator.class),
    Sequencer((Class<? extends MachinePackage>) Sequencer.PackageSequencer.class),
    Polymeriser((Class<? extends MachinePackage>) Polymeriser.PackagePolymeriser.class),
    Inoculator((Class<? extends MachinePackage>) Inoculator.PackageInoculator.class);

    Class<? extends MachinePackage> clss;

    private GeneticMachine(final Class<? extends MachinePackage> clss) {
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
        return new ItemStack((Block) Genetics.packageGenetic.getBlock(), i, this.ordinal());
    }

    public abstract static class PackageGeneticBase extends MachinePackage {
        BinnieResource renderTexture;
        int colour;

        protected PackageGeneticBase(final String uid, final IBinnieTexture renderTexture, final int flashColour, final boolean powered) {
            super(uid, powered);
            this.renderTexture = renderTexture.getTexture();
            this.colour = flashColour;
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
            MachineRendererGenetics.instance.renderMachine(machine, this.colour, this.renderTexture, x, y, z, var8);
        }
    }
}
