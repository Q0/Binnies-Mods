package binnie.core.liquid;

import net.minecraftforge.fluids.Fluid;

class BinnieFluid extends Fluid {
    final IFluidType fluidType;
    private final String name;

    public BinnieFluid(final IFluidType fluid) {
        super(fluid.getIdentifier());
        this.fluidType = fluid;
        this.name = fluid.getName();
    }

    public String getLocalizedName() {
        return this.name;
    }

    public int getColor() {
        return this.fluidType.getColour();
    }

    public IFluidType getType() {
        return this.fluidType;
    }
}
