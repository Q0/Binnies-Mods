package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum Liqueur implements IFluidType, ICocktailLiquid {
    Almond("Almond", 14966063, 0.3D, 0.2D),
    Orange("Orange", 16353536, 0.4D, 0.2D),
    Banana("Banana", 16302592, 0.5D, 0.2D),
    Chocolate("Chocolate", 12667680, 0.3D, 0.2D),
    Mint("Mint", 2737788, 0.4D, 0.2D),
    Hazelnut("Hazelnut", 15570987, 0.3D, 0.2D),
    Cinnamon("Cinnamon", 15028224, 0.3D, 0.2D),
    Coffee("Coffee", 9847577, 0.4D, 0.2D),
    Melon("Melon", 11584049, 0.4D, 0.2D),
    Anise("Anise", 14344681, 0.3D, 0.2D),
    Peach("Peach", 16684384, 0.4D, 0.2D),
    Lemon("Lemon", 16311405, 0.4D, 0.2D),
    Herbal("Herbal", 16700673, 0.3D, 0.2D),
    Cherry("Cherry", 14096641, 0.5D, 0.2D),
    Blackcurrant("Blackcurrant", 6962541, 0.5D, 0.2D),
    Blackberry("Blackberry", 6837581, 0.5D, 0.2D),
    Raspberry("Raspberry", 10158848, 0.5D, 0.2D);

    String name;
    String ident;
    IIcon icon;
    int colour;
    float transparency;
    float abv;

    private void addFlavour(String oreDict) {
    }

    private Liqueur(String name, int colour, double transparency, double abv) {
        this(name + " Liqueur", "liqueur" + name, colour, transparency, abv);
    }

    private Liqueur(String name, String ident, int colour, double transparency, double abv) {
        this.name = name;
        this.ident = ident;
        this.colour = colour;
        this.transparency = (float) transparency;
        this.abv = (float) abv;
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
        return this.abv;
    }

    static {
        Almond.addFlavour("cropAlmond");
        Orange.addFlavour("cropOrange");
        Banana.addFlavour("cropBanana");
        Chocolate.addFlavour("cropCocoa");
        Mint.addFlavour("cropMint");
        Hazelnut.addFlavour("cropHazelnut");
        Cinnamon.addFlavour("cropCinnamon");
        Coffee.addFlavour("cropCoffee");
        Melon.addFlavour("cropMelon");
        Anise.addFlavour("cropAnise");
        Peach.addFlavour("cropPeach");
        Lemon.addFlavour("cropLemon");
        Herbal.addFlavour("cropHerbal");
        Cherry.addFlavour("cropCherry");
        Blackcurrant.addFlavour("cropBlackcurrant");
        Blackberry.addFlavour("cropBlackberry");
        Raspberry.addFlavour("cropRaspberry");
    }
}
