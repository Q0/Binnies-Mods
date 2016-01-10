package binnie.genetics.machine;

import binnie.core.machines.inventory.Validator;
import net.minecraft.item.ItemStack;

public class SlotValidatorInoculatorCatalyst extends Validator {
    public SlotValidatorInoculatorCatalyst() {
        super();
    }

    public boolean isValid(ItemStack itemStack) {
        return false;
    }

    public String getTooltip() {
        return "Inoculation Catalyst";
    }
}
