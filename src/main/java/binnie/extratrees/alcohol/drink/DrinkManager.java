package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.drink.IDrinkLiquid;
import java.util.HashMap;
import java.util.Map;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class DrinkManager {
   static Map drinkLiquids = new HashMap();

   public DrinkManager() {
      super();
   }

   public static IDrinkLiquid getLiquid(String id) {
      return (IDrinkLiquid)drinkLiquids.get(id.toLowerCase());
   }

   public static void registerDrinkLiquid(String id, IDrinkLiquid liquid) {
      liquid.setIdent(id.toLowerCase());
      drinkLiquids.put(id.toLowerCase(), liquid);
   }

   public static IDrinkLiquid getLiquid(Fluid fluid) {
      return getLiquid(fluid.getName());
   }

   public static IDrinkLiquid getLiquid(FluidStack fluid) {
      return fluid != null?getLiquid(fluid.getFluid()):null;
   }
}
