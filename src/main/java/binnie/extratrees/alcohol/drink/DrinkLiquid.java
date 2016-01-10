package binnie.extratrees.alcohol.drink;

import binnie.Binnie;
import net.minecraftforge.fluids.FluidStack;

public class DrinkLiquid implements IDrinkLiquid {
    String name;
    int colour;
    float transparency;
    float abv;
    public String ident;

    public DrinkLiquid(String name, int colour, float transparency, float abv) {
        super();
        this.name = name;
        this.colour = colour;
        this.transparency = transparency;
        this.abv = abv;
    }

    public boolean isConsumable() {
        return true;
    }

    public int getColour() {
        return this.colour;
    }

    public String getName() {
        return this.name;
    }

    public float getTransparency() {
        return this.transparency;
    }

    public String getIdentifier() {
        return this.ident;
    }

    public void setIdent(String lowerCase) {
        this.ident = lowerCase;
    }

    public float getABV() {
        return this.abv;
    }

    public FluidStack get(int amount) {
        return Binnie.Liquid.getLiquidStack(this.ident, amount);
    }
}
