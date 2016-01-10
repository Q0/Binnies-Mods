package binnie.core.machines.inventory;

import binnie.core.machines.inventory.Validator;
import binnie.core.machines.inventory.ValidatorIcon;
import forestry.api.genetics.AlleleManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class SlotValidator extends Validator {
   public static ValidatorIcon IconBee;
   public static ValidatorIcon IconFrame;
   public static ValidatorIcon IconCircuit;
   public static ValidatorIcon IconBlock;
   private ValidatorIcon icon;

   public SlotValidator(ValidatorIcon icon) {
      super();
      this.icon = icon;
   }

   public IIcon getIcon(boolean input) {
      return this.icon == null?null:this.icon.getIcon(input).getIcon();
   }

   public static class Individual extends SlotValidator {
      public Individual() {
         super((ValidatorIcon)null);
      }

      public boolean isValid(ItemStack itemStack) {
         return AlleleManager.alleleRegistry.getIndividual(itemStack) != null;
      }

      public String getTooltip() {
         return "Breedable Individual";
      }
   }

   public static class Item extends SlotValidator {
      private ItemStack target;

      public Item(ItemStack target, ValidatorIcon icon) {
         super(icon);
         this.target = target;
      }

      public boolean isValid(ItemStack itemStack) {
         return itemStack.isItemEqual(this.target);
      }

      public String getTooltip() {
         return this.target.getDisplayName();
      }
   }
}
