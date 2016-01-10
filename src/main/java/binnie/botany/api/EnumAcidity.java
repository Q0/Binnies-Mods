package binnie.botany.api;

public enum EnumAcidity {
   Acid,
   Neutral,
   Alkaline;

   private EnumAcidity() {
   }

   public String getID() {
      return this.name().toLowerCase();
   }
}
