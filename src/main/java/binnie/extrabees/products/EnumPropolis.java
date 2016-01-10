package binnie.extrabees.products;

import binnie.Binnie;
import binnie.core.item.IItemEnum;
import binnie.extrabees.ExtraBees;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public enum EnumPropolis implements IItemEnum {
   WATER(2405321, 12762791, "Water"),
   OIL(1519411, 12762791, "oil"),
   FUEL(10718482, 12762791, "fuel"),
   /** @deprecated */
   @Deprecated
   MILK,
   /** @deprecated */
   @Deprecated
   FRUIT,
   /** @deprecated */
   @Deprecated
   SEED,
   /** @deprecated */
   @Deprecated
   ALCOHOL,
   CREOSOTE(8877313, 12428819, "creosote"),
   /** @deprecated */
   @Deprecated
   GLACIAL,
   /** @deprecated */
   @Deprecated
   PEAT;

   int[] colour;
   String liquidName;
   boolean active;

   private EnumPropolis() {
      this(16777215, 16777215, "");
      this.active = false;
   }

   private EnumPropolis(int colour, int colour2, String liquid) {
      this.colour = new int[0];
      this.active = true;
      this.colour = new int[]{colour, colour2};
      this.liquidName = liquid;
   }

   public void addRecipe() {
      FluidStack liquid = Binnie.Liquid.getLiquidStack(this.liquidName, 500);
      if(liquid != null) {
         RecipeManagers.squeezerManager.addRecipe(20, new ItemStack[]{this.get(1)}, liquid, (ItemStack)null, 0);
      }

   }

   public boolean isActive() {
      return this.active && Binnie.Liquid.getLiquidStack(this.liquidName, 100) != null;
   }

   public static EnumPropolis get(ItemStack itemStack) {
      int i = itemStack.getItemDamage();
      return i >= 0 && i < values().length?values()[i]:values()[0];
   }

   public ItemStack get(int size) {
      return new ItemStack(ExtraBees.propolis, size, this.ordinal());
   }

   public String getName(ItemStack stack) {
      return ExtraBees.proxy.localise("item.propolis." + this.name().toLowerCase());
   }
}
