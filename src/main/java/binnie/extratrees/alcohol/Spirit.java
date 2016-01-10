package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.ICocktailLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum Spirit implements IFluidType, ICocktailLiquid {
   NeutralSpirit("Neutral Spirit", "spiritNeutral", 16777215, 0.05D, 0.8D),
   Vodka("Vodka", "vodka", 16053751, 0.05D, 0.4D),
   WhiteRum("White Rum", "rumWhite", 15132132, 0.05D, 0.4D),
   DarkRum("Dark Rum", "rumDark", 11018752, 0.4D, 0.4D),
   Whiskey("Whiskey", "whiskey", 13594368, 0.2D, 0.4D),
   CornWhiskey("Bourbon", "whiskeyCorn", 9835009, 0.2D, 0.4D),
   RyeWhiskey("Rye Whiskey", "whiskeyRye", 16085800, 0.2D, 0.4D),
   WheatWhiskey("Wheat Whiskey", "whiskeyWheat", 14976530, 0.2D, 0.4D),
   FortifiedWine("Fortified Wine", "wineFortified", 15569439, 0.2D, 0.2D),
   Tequila("Tequila", "tequila", 16116160, 0.05D, 0.4D),
   Brandy("Brandy", "brandyGrape", 16228128, 0.2D, 0.4D),
   AppleBrandy("Apple Brandy", "brandyApple", 14985790, 0.2D, 0.4D),
   PearBrandy("Pear Brandy", "brandyPear", 16696883, 0.2D, 0.4D),
   ApricotBrandy("Apricot Brandy", "brandyApricot", 13336387, 0.2D, 0.4D),
   PlumBrandy("Plum Brandy", "brandyPlum", 9511697, 0.2D, 0.4D),
   CherryBrandy("Cherry Brandy", "brandyCherry", 8588062, 0.2D, 0.4D),
   ElderberryBrandy("Elderberry Brandy", "brandyElderberry", 12462919, 0.2D, 0.4D),
   CitrusBrandy("Citrus Brandy", "brandyCitrus", 13336387, 0.2D, 0.4D),
   FruitBrandy("Fruit Brandy", "brandyFruit", 14985790, 0.2D, 0.4D),
   Cachaca("Cachaca", "spiritSugarcane", 15331535, 0.1D, 0.4D),
   Gin("Gin", "spiritGin", 16185078, 0.05D, 0.4D),
   AppleLiquor("Apple Liquor", "liquorApple", 13421772, 0.05D, 0.4D),
   PearLiquor("Pear Liquor", "liquorPear", 13421772, 0.05D, 0.4D),
   CherryLiquor("Cherry Liquor", "liquorCherry", 13421772, 0.05D, 0.4D),
   ElderberryLiquor("Elderberry Liquor", "liquorElderberry", 13421772, 0.05D, 0.4D),
   ApricotLiquor("Apricot Liquor", "liquorApricot", 13421772, 0.05D, 0.4D),
   FruitLiquor("Fruit Liquor", "liquorFruit", 13421772, 0.05D, 0.4D);

   String name;
   String ident;
   IIcon icon;
   int colour;
   float transparency;
   float abv;

   private Spirit(String name, String ident, int colour, double transparency, double abv) {
      this.name = name;
      this.ident = ident;
      this.colour = colour;
      this.transparency = (float)transparency;
      this.abv = (float)abv;
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
      return (int)(Math.min(1.0D, (double)this.transparency + 0.3D) * 255.0D);
   }

   public String getTooltip(int ratio) {
      return ratio + " Part" + (ratio > 1?"s ":" ") + this.getName();
   }

   public int getContainerColour() {
      return this.getColour();
   }

   public float getABV() {
      return this.abv;
   }
}
