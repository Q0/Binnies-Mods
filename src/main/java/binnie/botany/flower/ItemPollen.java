package binnie.botany.flower;

import binnie.botany.api.EnumFlowerStage;
import binnie.botany.flower.ItemBotany;

public class ItemPollen extends ItemBotany {
   public ItemPollen() {
      super("pollen");
   }

   public EnumFlowerStage getStage() {
      return EnumFlowerStage.POLLEN;
   }

   public String getTag() {
      return " Pollen";
   }
}
