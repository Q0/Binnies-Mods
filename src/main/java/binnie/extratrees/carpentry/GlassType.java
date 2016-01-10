package binnie.extratrees.carpentry;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.api.IDesignMaterial;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class GlassType implements IDesignMaterial {
   static Map types = new LinkedHashMap();
   String name;
   int colour;
   int id;

   private GlassType(int id, String name, int colour) {
      super();
      this.id = id;
      this.name = name;
      this.colour = colour;
   }

   public ItemStack getStack() {
      return this.id < 128?new ItemStack(Blocks.stained_glass, 1, this.id):TileEntityMetadata.getItemStack(Botany.stained, this.id - 128);
   }

   public String getName() {
      return this.name;
   }

   public int getColour() {
      return this.colour;
   }

   public static int getIndex(IDesignMaterial id) {
      for(Entry<Integer, GlassType> entry : types.entrySet()) {
         if(entry.getValue() == id) {
            return ((Integer)entry.getKey()).intValue();
         }
      }

      return 0;
   }

   public static IDesignMaterial get(int id) {
      return (IDesignMaterial)types.get(Integer.valueOf(id));
   }

   public static IDesignMaterial get(ItemStack stack) {
      if(stack == null) {
         return null;
      } else {
         for(Entry<Integer, GlassType> entry : types.entrySet()) {
            if(stack.isItemEqual(((GlassType)entry.getValue()).getStack())) {
               return (IDesignMaterial)entry.getValue();
            }
         }

         return null;
      }
   }

   static {
      for(GlassType.StandardColor c : GlassType.StandardColor.values()) {
         types.put(Integer.valueOf(c.ordinal()), new GlassType(c.ordinal(), c.name, c.colour));
      }

      for(EnumFlowerColor c : EnumFlowerColor.values()) {
         types.put(Integer.valueOf(128 + c.ordinal()), new GlassType(128 + c.ordinal(), c.getName(), c.getColor(false)));
      }

   }

   private static enum StandardColor {
      White("White", 16777215),
      Orange("Orange", 14188339),
      Magenta("Magenta", 11685080),
      LightBlue("Light Blue", 6724056),
      Yellow("Yellow", 15066419),
      Lime("Lime", 8375321),
      Pink("Pink", 15892389),
      Gray("Gray", 5000268),
      LightGray("Light Gray", 10066329),
      Cyan("Cyan", 5013401),
      Purple("Purple", 8339378),
      Blue("Blue", 3361970),
      Brown("Brown", 6704179),
      Green("Green", 6717235),
      Red("Red", 10040115),
      Black("Black", 1644825);

      String name;
      int colour;

      private StandardColor(String name, int colour) {
         this.name = name;
         this.colour = colour;
      }
   }
}
