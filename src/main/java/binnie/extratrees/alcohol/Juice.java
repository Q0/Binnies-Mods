package binnie.extratrees.alcohol;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.ICocktailLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum Juice implements IFluidType, ICocktailLiquid {
   Apple("Apple Juice", "juiceApple", 16763442, 0.7D, "Apple"),
   Apricot("Apricot Juice", "juiceApricot", 16758046, 0.6D, "Apricot"),
   Banana("Banana Juice", "juiceBanana", 15324291, 0.6D, "Banana"),
   Cherry("Cherry Juice", "juiceCherry", 13044511, 0.6D, "Cherry"),
   Elderberry("Elderberry Juice", "juiceElderberry", 4204073, 0.6D, "Elderberry"),
   Lemon("Lemon Juice", "juiceLemon", 14604882, 0.7D, "Lemon"),
   Lime("Lime Juice", "juiceLime", 12177007, 0.6D, "Lime"),
   Orange("Orange Juice", "juiceOrange", 16359983, 0.8D, "Orange"),
   Peach("Peach Juice", "juicePeach", 16434525, 0.7D, "Peach"),
   Plum("Plum Juice", "juicePlum", 10559249, 0.7D, "Plum"),
   Carrot("Carrot Juice", "juiceCarrot", 16485911, 0.7D, "Carrot"),
   Tomato("Tomato Juice", "juiceTomato", 12731438, 0.7D, "Tomato"),
   Cranberry("Cranberry Juice", "juiceCranberry", 12920629, 0.7D, "Cranberry"),
   Grapefruit("Grapefruit Juice", "juiceGrapefruit", 15897173, 0.6D, "Grapefruit"),
   Olive("Olive Oil", "juiceOlive", 16042240, 0.6D, "Olive"),
   Pineapple("Pineapple Juice", "juicePineapple", 15189319, 0.6D, "Pineapple"),
   Pear("Pear Juice", "juicePear", 14204773, 0.6D, "Pear"),
   WhiteGrape("White Grape Juice", "juiceWhiteGrape", 16507769, 0.6D, "WhiteGrape"),
   RedGrape("Red Grape Juice", "juiceRedGrape", 9775412, 0.6D, "RedGrape");

   String name;
   String ident;
   public String squeezing;
   IIcon icon;
   int colour;
   float transparency;

   private void addSqueezing(String oreDict) {
      this.squeezing = oreDict;
   }

   private Juice(String name, String ident, int colour, double transparency, String squeezing) {
      this.name = name;
      this.ident = ident;
      this.colour = colour;
      this.transparency = (float)transparency;
      this.addSqueezing("crop" + squeezing);
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
      return 0.0F;
   }
}
