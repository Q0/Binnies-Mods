package binnie.core.liquid;

import binnie.core.ManagerBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ManagerLiquid extends ManagerBase {
    Map fluids = new LinkedHashMap();

    public ManagerLiquid() {
        super();
    }

    public Collection getFluidTypes() {
        return this.fluids.values();
    }

    public void createLiquids(IFluidType[] liquids, int startID) {
        for (IFluidType liquid : liquids) {
            BinnieFluid fluid = this.createLiquid(liquid, startID++);
            if (fluid == null) {
                throw new RuntimeException("Liquid registered incorrectly - " + liquid.getIdentifier());
            }
        }

    }

    public BinnieFluid createLiquid(IFluidType fluid, int id) {
        this.fluids.put(fluid.getIdentifier().toLowerCase(), fluid);
        BinnieFluid bFluid = new BinnieFluid(fluid);
        FluidRegistry.registerFluid(bFluid);
        ItemFluidContainer.registerFluid(fluid, id);
        return bFluid;
    }

    public FluidStack getLiquidStack(String name, int amount) {
        return FluidRegistry.getFluidStack(name.toLowerCase(), amount);
    }

    @SideOnly(Side.CLIENT)
    public void reloadIcons(IIconRegister register) {
        for (IFluidType type : this.fluids.values()) {
            Fluid fluid = this.getLiquidStack(type.getIdentifier(), 1).getFluid();
            type.registerIcon(register);
            if (fluid == null) {
                throw new RuntimeException("[Binnie] Liquid not registered properly - " + type.getIdentifier());
            }

            fluid.setIcons(type.getIcon());
        }

    }

    public void init() {
    }

    public void postInit() {
        for (IFluidType fluid : this.fluids.values()) {
            for (FluidContainer container : FluidContainer.values()) {
                if (container.isActive() && fluid.canPlaceIn(container)) {
                    container.registerContainerData(fluid);
                }
            }
        }

    }

    public IFluidType getFluidType(String liquid) {
        return (IFluidType) this.fluids.get(liquid.toLowerCase());
    }
}
