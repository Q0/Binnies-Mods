package binnie.botany.genetics;

import binnie.botany.genetics.EnumFlowerColor;
import forestry.api.genetics.IAlleleInteger;

public class AlleleColor implements IAlleleInteger {
   String uid;
   boolean dominant;
   String name;
   int value;
   EnumFlowerColor color;

   public AlleleColor(EnumFlowerColor color, String uid, String name, int value) {
      super();
      this.color = color;
      this.uid = uid;
      this.name = name;
      this.value = value;
   }

   public String getUID() {
      return this.uid;
   }

   public boolean isDominant() {
      return true;
   }

   public String getName() {
      return this.color.getName();
   }

   public int getValue() {
      return this.value;
   }

   public EnumFlowerColor getColor() {
      return this.color;
   }

   public String getUnlocalizedName() {
      return this.getUID();
   }
}
