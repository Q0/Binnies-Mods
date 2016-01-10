package binnie.core.machines.inventory;

enum AccessDirection {
   Both,
   In,
   Out,
   Neither;

   private AccessDirection() {
   }

   boolean canInsert() {
      return this == Both || this == In;
   }

   boolean canExtract() {
      return this == Both || this == Out;
   }

   boolean canAccess() {
      return this != Neither;
   }

   AccessDirection changeInsert(boolean b) {
      if(b) {
         if(this == Out) {
            return Both;
         }

         if(this == Neither) {
            return In;
         }
      } else {
         if(this == Both) {
            return Out;
         }

         if(this == In) {
            return Neither;
         }
      }

      return this;
   }

   AccessDirection changeExtract(boolean b) {
      if(b) {
         if(this == In) {
            return Both;
         }

         if(this == Neither) {
            return Out;
         }
      } else {
         if(this == Both) {
            return In;
         }

         if(this == Out) {
            return Neither;
         }
      }

      return this;
   }

   public String getTextColour() {
      switch(this) {
      case Both:
         return "§a";
      case In:
         return "§e";
      case Neither:
         return "§c";
      case Out:
      default:
         return "§b";
      }
   }

   public int getShadeColour() {
      switch(this) {
      case Both:
         return 1431699285;
      case In:
         return 1442840405;
      case Neither:
         return 1442796885;
      case Out:
      default:
         return 1431699455;
      }
   }
}
