package binnie.extrabees.apiary.machine;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.modifiers.ComponentFrameModifier;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IHiveFrame;
import net.minecraft.item.ItemStack;

public class AlvearyFrame {
    public static int slotFrame;

    public static class PackageAlvearyFrame extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
        public PackageAlvearyFrame() {
            super("frame", ExtraBeeTexture.AlvearyFrame.getTexture(), false);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyFrame);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(AlvearyFrame.slotFrame, "frame");
            inventory.getSlot(AlvearyFrame.slotFrame).setValidator(new SlotValidatorFrame());
            new ComponentFrameModifier(machine);
        }
    }

    public static class SlotValidatorFrame extends SlotValidator {
        public SlotValidatorFrame() {
            super(SlotValidator.IconFrame);
        }

        @Override
        public boolean isValid(final ItemStack itemStack) {
            return itemStack != null && itemStack.getItem() instanceof IHiveFrame;
        }

        @Override
        public String getTooltip() {
            return "Hive Frames";
        }
    }
}
