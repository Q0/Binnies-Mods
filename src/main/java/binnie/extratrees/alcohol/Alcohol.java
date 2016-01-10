package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.extratrees.ExtraTrees;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public enum Alcohol implements IFluidType, ICocktailLiquid {
    Apple("ciderApple", 16432700, 0.3D, 0.05D),
    Apricot("wineApricot", 15781686, 0.3D, 0.1D),
    Banana("wineBanana", 14993485, 0.3D, 0.1D),
    Cherry("wineCherry", 11207702, 0.3D, 0.1D),
    Elderberry("wineElderberry", 9764865, 0.3D, 0.1D),
    Peach("ciderPeach", 15361563, 0.3D, 0.05D),
    Pear("ciderPear", 15061095, 0.3D, 0.05D),
    Plum("winePlum", 12063752, 0.3D, 0.1D),
    Carrot("wineCarrot", 16219394, 0.3D, 0.1D),
    WhiteWine("wineWhite", 15587989, 0.1D, 0.1D),
    RedWine("wineRed", 7670539, 0.2D, 0.1D),
    SparklingWine("wineSparkling", 16709566, 0.1D, 0.1D),
    Agave("wineAgave", 13938276, 0.2D, 0.1D),
    Potato("fermentedPotatoes", 12028240, 0.8D, 0.1D),
    Citrus("wineCitrus", 16776960, 0.2D, 0.1D),
    Cranberry("wineCranberry", 11599874, 0.2D, 0.1D),
    Pineapple("winePineapple", 14724150, 0.2D, 0.1D),
    Tomato("wineTomato", 12458521, 0.2D, 0.1D),
    Fruit("juice", 16432700, 0.2D, 0.1D),
    Ale("beerAle", 12991009, 0.7D, 0.05D),
    Lager("beerLager", 15301637, 0.7D, 0.05D),
    WheatBeer("beerWheat", 14380552, 0.7D, 0.05D),
    RyeBeer("beerRye", 10836007, 0.7D, 0.05D),
    CornBeer("beerCorn", 13411364, 0.7D, 0.05D),
    Stout("beerStout", 5843201, 0.8D, 0.05D),
    Barley("mashGrain", 12991009, 0.9D, 0.05D),
    Wheat("mashWheat", 12991009, 0.9D, 0.05D),
    Rye("mashRye", 10836007, 0.9D, 0.05D),
    Corn("mashCorn", 13411364, 0.9D, 0.05D);

    List fermentationLiquid = new ArrayList();
    String fermentationSolid = "";
    String ident;
    IIcon icon;
    int colour;
    float transparency;
    float abv;

    private void setFementation(Juice juice) {
        this.fermentationLiquid.add(juice.getIdentifier());
    }

    private void setFementation(String oreDict) {
        this.fermentationSolid = oreDict;
    }

    private Alcohol(String ident, int colour, double transparency, double abv) {
        this.ident = ident;
        this.colour = colour;
        this.transparency = (float) transparency;
        this.abv = (float) abv;
    }

    public String toString() {
        return this.getName();
    }

    public IIcon getIcon() {
        return this.icon;
    }

    public void registerIcon(IIconRegister register) {
        this.icon = ExtraTrees.proxy.getIcon(register, "liquids/liquid");
    }

    public String getName() {
        return ExtraTrees.proxy.localise("fluid.alcohol." + this.name().toLowerCase());
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

    public boolean canPlaceIn(FluidContainer container) {
        return true;
    }

    public boolean showInCreative(FluidContainer container) {
        return container == FluidContainer.Glass;
    }

    public int getContainerColour() {
        return this.getColour();
    }

    public float getABV() {
        return this.abv;
    }

    static {
        Apple.setFementation(Juice.Apple);
        Apricot.setFementation(Juice.Apricot);
        Banana.setFementation(Juice.Banana);
        Cherry.setFementation(Juice.Cherry);
        Elderberry.setFementation(Juice.Elderberry);
        Peach.setFementation(Juice.Peach);
        Pear.setFementation(Juice.Pear);
        Plum.setFementation(Juice.Plum);
        Carrot.setFementation(Juice.Carrot);
        WhiteWine.setFementation(Juice.WhiteGrape);
        RedWine.setFementation(Juice.RedGrape);
        Citrus.setFementation(Juice.Lemon);
        Citrus.setFementation(Juice.Lime);
        Citrus.setFementation(Juice.Orange);
        Citrus.setFementation(Juice.Grapefruit);
        Tomato.setFementation(Juice.Tomato);
        Cranberry.setFementation(Juice.Cranberry);
        Pineapple.setFementation(Juice.Pineapple);
        Potato.setFementation("cropPotato");
    }
}
