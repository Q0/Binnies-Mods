package binnie.core.util;

import net.minecraftforge.fluids.FluidStack;

public class UniqueFluidStackSet extends FluidStackSet {
    public UniqueFluidStackSet() {
        super();
    }

    public boolean add(FluidStack e) {
        return e != null && this.getExisting(e) == null ? this.itemStacks.add(e.copy()) : false;
    }

    public boolean remove(Object o) {
        if (this.contains(o)) {
            FluidStack r = (FluidStack) o;
            FluidStack existing = this.getExisting(r);
            this.itemStacks.remove(existing);
        }

        return false;
    }
}
