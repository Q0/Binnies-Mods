package binnie.extrabees.apiary.machine;

import binnie.core.machines.Machine;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.modifiers.ComponentLightingModifier;
import binnie.extrabees.core.ExtraBeeTexture;

public class AlvearyLighting {
    public static class PackageAlvearyLighting extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
        public PackageAlvearyLighting() {
            super("lighting", ExtraBeeTexture.AlvearyLighting.getTexture(), false);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentLightingModifier(machine);
        }
    }
}
