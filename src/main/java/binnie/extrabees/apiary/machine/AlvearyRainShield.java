package binnie.extrabees.apiary.machine;

import binnie.core.machines.Machine;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

public class AlvearyRainShield {
    public AlvearyRainShield() {
        super();
    }

    public static class ComponentRainShield extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
        public ComponentRainShield(Machine machine) {
            super(machine);
        }

        public boolean isSealed() {
            return true;
        }
    }

    public static class PackageAlvearyRainShield extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
        public PackageAlvearyRainShield() {
            super("rainShield", ExtraBeeTexture.AlvearyRainShield.getTexture(), false);
        }

        public void createMachine(Machine machine) {
            new AlvearyRainShield.ComponentRainShield(machine);
        }
    }
}
