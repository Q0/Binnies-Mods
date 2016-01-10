package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum MiscFluid implements IFluidType, ICocktailLiquid {
    CarbonatedWater("Carbonated Water", "water.carbonated", 13421823, 0.10000000149011612D),
    TonicWater("Tonic Water", "water.tonic", 13421823, 0.10000000149011612D),
    Cream("Carbonated Water", "cream", 15395550, 2.0D),
    GingerAle("Ginger Ale", "gingerAle", 16777215, 0.6000000238418579D),
    Coffee("Coffee", "coffee", 5910789, 0.30000001192092896D),
    SugarSyrup("Simple Syrup", "syrup.simple", 16120049, 0.10000000149011612D),
    AgaveNectar("Agave Nectar", "syrup.agave", 13598245, 0.699999988079071D),
    GrenadineSyrup("Grenadine Syrup", "syrup.grenadine", 16009573, 0.800000011920929D);

    String name;
    String ident;
    IIcon icon;
    int colour;
    float transparency;

    private MiscFluid(String name, String ident, int colour, double transparency) {
        this.name = name;
        this.ident = ident;
        this.colour = colour;
        this.transparency = (float) transparency;
    }

    public String toString() {
        return this.name;
    }

    public boolean canPlaceIn(FluidContainer container) {
        return true;
    }

    public boolean showInCreative(FluidContainer container) {
        return container == FluidContainer.Glass;
    }

    public IIcon getIcon() {
        return this.icon;
    }

    public void registerIcon(IIconRegister register) {
        this.icon = ExtraTrees.proxy.getIcon(register, "liquids/liquid");
    }

    public String getName() {
        return this.name;
    }

    public String getIdentifier() {
        return "binnie." + this.ident;
    }

    public int getColour() {
        return this.colour;
    }

    public FluidStack get(int amount) {
        return Binnie.Liquid.getLiquidStack(this.getIdentifier(), amount);
    }

    public int getTransparency() {
        return (int) (Math.min(1.0D, (double) this.transparency + 0.3D) * 255.0D);
    }

    public String getTooltip(int ratio) {
        return ratio + " Part" + (ratio > 1 ? "s " : " ") + this.getName();
    }

    public int getContainerColour() {
        return this.getColour();
    }

    public float getABV() {
        return 0.0F;
    }
}
