package binnie.extrabees.apiary.modifiers;

import binnie.core.genetics.BeeModifier;
import binnie.core.genetics.BeeModifierLogic;
import binnie.extrabees.apiary.items.StimulatorCircuit;
import forestry.api.circuits.ICircuitLayout;

public class CircuitModifier extends BeeModifier {
    private int recipe;
    private int power;

    //---------------------------------------------------------------------------
    //
    // CONSTRUCTOR
    //
    //---------------------------------------------------------------------------

    public CircuitModifier(final int recipe, final int power, final ICircuitLayout layout) {
        super(new BeeModifierLogic());
        this.recipe = recipe;
        this.power = power;

        init(layout);
    }

    //---------------------------------------------------------------------------
    //
    // PRIVATE METHODS
    //
    //---------------------------------------------------------------------------

    private void init(final ICircuitLayout layout) {
        final StimulatorCircuit circuit = new StimulatorCircuit(this, layout);

        for (final BeeModifierLogic.FloatModifier modifier : BeeModifierLogic.FloatModifier.values()) {
            final float mod = logic.getModifier(modifier, 1.0f);

            if (mod != 1.0f) {
                if (mod > 1.0f) {
                    final int increase = (int) ((mod - 1.0f) * 100.0f);
                    circuit.addTooltipString("Increases " + modifier.getName() + " by " + increase + "%");
                } else {
                    final int decrease = (int) ((1.0f - mod) * 100.0f);
                    circuit.addTooltipString("Decreases " + modifier.getName() + " by " + decrease + "%");
                }
            }
        }
    }

    //---------------------------------------------------------------------------
    //
    // ACCESSORS
    //
    //---------------------------------------------------------------------------

    public int getRecipe() {
        return recipe;
    }

    public int getPower() {
        return power;
    }
}
