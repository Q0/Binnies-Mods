package binnie.extrabees.apiary.machine;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.modifiers.ComponentStimulatorModifier;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.circuits.ChipsetManager;
import net.minecraft.item.ItemStack;

public class AlvearyStimulator {
    public static int slotCircuit;

    public static class PackageAlvearyStimulator extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
        public PackageAlvearyStimulator() {
            super("stimulator", ExtraBeeTexture.AlvearyStimulator.getTexture(), true);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyStimulator);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(AlvearyStimulator.slotCircuit, "circuit");
            inventory.getSlot(AlvearyStimulator.slotCircuit).setValidator(new SlotValidatorCircuit());
            final ComponentPowerReceptor power = new ComponentPowerReceptor(machine);
            new ComponentStimulatorModifier(machine);
        }
    }

    public static class SlotValidatorCircuit extends SlotValidator {
        public SlotValidatorCircuit() {
            super(SlotValidator.IconCircuit);
        }

        @Override
        public boolean isValid(final ItemStack itemStack) {
            return itemStack != null && ChipsetManager.circuitRegistry.isChipset(itemStack);
        }

        @Override
        public String getTooltip() {
            return "Forestry Circuits";
        }
    }
}
