package binnie.core.machines.inventory;

import forestry.api.genetics.AlleleManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public abstract class SlotValidator extends Validator<ItemStack> {
    public static ValidatorIcon IconBee;
    public static ValidatorIcon IconFrame;
    public static ValidatorIcon IconCircuit;
    public static ValidatorIcon IconBlock;
    private ValidatorIcon icon;

    public SlotValidator(final ValidatorIcon icon) {
        this.icon = icon;
    }

    public IIcon getIcon(final boolean input) {
        return (this.icon == null) ? null : this.icon.getIcon(input).getIcon();
    }

    public static class Item extends SlotValidator {
        private ItemStack target;

        public Item(final ItemStack target, final ValidatorIcon icon) {
            super(icon);
            this.target = target;
        }

        @Override
        public boolean isValid(final ItemStack itemStack) {
            return itemStack.isItemEqual(this.target);
        }

        @Override
        public String getTooltip() {
            return this.target.getDisplayName();
        }
    }

    public static class Individual extends SlotValidator {
        public Individual() {
            super(null);
        }

        @Override
        public boolean isValid(final ItemStack itemStack) {
            return AlleleManager.alleleRegistry.getIndividual(itemStack) != null;
        }

        @Override
        public String getTooltip() {
            return "Breedable Individual";
        }
    }
}
