package binnie.extrabees.liquids;

import binnie.Binnie;
import binnie.core.IInitializable;
import binnie.core.liquid.ItemFluidContainer;

public class ModuleLiquids implements IInitializable {
    public ModuleLiquids() {
        super();
    }

    public void preInit() {
        Binnie.Liquid.createLiquids(ExtraBeeLiquid.values(), ItemFluidContainer.LiquidExtraBee);
    }

    public void init() {
    }

    public void postInit() {
    }
}
