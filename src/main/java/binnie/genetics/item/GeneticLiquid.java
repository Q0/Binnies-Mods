package binnie.genetics.item;

import binnie.Binnie;
import binnie.core.liquid.FluidContainer;
import binnie.core.liquid.IFluidType;
import binnie.genetics.Genetics;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

public enum GeneticLiquid implements IFluidType {
   GrowthMedium("Growth Medium", "growthMedium", 15460533),
   Bacteria("Bacteria", "bacteria", 14203521),
   BacteriaPoly("Polymerising Bacteria", "bacteriaPoly", 11443396),
   RawDNA("Raw DNA", "dna.raw", 15089129),
   BacteriaVector("Bacteria Vector", "bacteriaVector", 15960958);

   String name;
   String ident;
   IIcon icon;
   int colour;
   float transparency;

   private GeneticLiquid(String name, String ident, int colour) {
      this.name = name;
      this.ident = ident;
      this.colour = colour;
      this.transparency = 1.0F;
   }

   public String toString() {
      return this.name;
   }

   public IIcon getIcon() {
      return this.icon;
   }

   public void registerIcon(IIconRegister register) {
      this.icon = Genetics.proxy.getIcon(register, "liquids/" + this.ident);
   }

   public String getName() {
      return this.name;
   }

   public String getIdentifier() {
      return "binnie." + this.ident;
   }

   public int getColour() {
      return 16777215;
   }

   public int getContainerColour() {
      return this.colour;
   }

   public FluidStack get(int amount) {
      return Binnie.Liquid.getLiquidStack(this.getIdentifier(), amount);
   }

   public int getTransparency() {
      return 0;
   }

   public boolean canPlaceIn(FluidContainer container) {
      return this == GrowthMedium?true:container == FluidContainer.Cylinder;
   }

   public boolean showInCreative(FluidContainer container) {
      return container == FluidContainer.Cylinder;
   }
}
