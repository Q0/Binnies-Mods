package binnie.extrabees.apiary.items;

import binnie.core.Mods;
import binnie.core.circuits.BinnieCircuit;
import binnie.extrabees.apiary.modifiers.CircuitModifier;
import forestry.api.circuits.ICircuitLayout;

public class StimulatorCircuit extends BinnieCircuit {
    CircuitModifier modifier;

    //---------------------------------------------------------------------------
    //
    // CONSTRUCTOR
    //
    //---------------------------------------------------------------------------

    public StimulatorCircuit(final CircuitModifier modifier, final ICircuitLayout layout) {
        super(
                "stimulator." + modifier.toString().toLowerCase(),
                4,
                layout,
                Mods.Forestry.item("thermionicTubes"), modifier.getRecipe()
        );

        this.modifier = modifier;
    }

    //---------------------------------------------------------------------------
    //
    // ACCESSORS
    //
    //---------------------------------------------------------------------------

    public CircuitModifier getModifier() {
        return modifier;
    }
}
