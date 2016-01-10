package binnie.extratrees.machines;

import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.machines.DesignerType;
import net.minecraft.item.ItemStack;

public class Validators {
   public Validators() {
      super();
   }

   public static class SlotValidatorBeeswax extends SlotValidator {
      DesignerType type;

      public SlotValidatorBeeswax(DesignerType type) {
         super(new ValidatorIcon(ExtraTrees.instance, "validator/polish.0", "validator/polish.1"));
         this.type = type;
      }

      public boolean isValid(ItemStack itemStack) {
         return this.type.getSystem().getAdhesive().isItemEqual(itemStack);
      }

      public String getTooltip() {
         return this.type.getSystem().getAdhesive().getDisplayName();
      }
   }

   public static class SlotValidatorPlanks extends SlotValidator {
      DesignerType type;

      public SlotValidatorPlanks(DesignerType type) {
         super(SlotValidator.IconBlock);
         this.type = type;
      }

      public boolean isValid(ItemStack itemStack) {
         IDesignMaterial mat = this.type.getSystem().getMaterial(itemStack);
         return itemStack == null?false:mat != null;
      }

      public String getTooltip() {
         return this.type.getMaterialTooltip();
      }
   }
}
