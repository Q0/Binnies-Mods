package binnie.extrabees.apiary.modifiers;

import binnie.core.machines.Machine;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;

public class ComponentLightingModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {

    //---------------------------------------------------------------------------
    //
    // CONSTRUCTOR
    //
    //---------------------------------------------------------------------------

    public ComponentLightingModifier(final Machine machine) {
        super(machine);
    }

    //---------------------------------------------------------------------------
    //
    // ACCESSOR
    //
    //---------------------------------------------------------------------------

    @Override
    public boolean isSelfLighted() {
        return true;
    }
}
