package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeeModifierLogic {
    private Map<FloatModifier, Float[]> modifiers;
    private List<BooleanModifier> booleanModifiers;

    //---------------------------------------------------------------------------
    //
    // CONSTRUCTOR
    //
    //---------------------------------------------------------------------------

    public BeeModifierLogic() {
        modifiers = new HashMap<FloatModifier, Float[]>();
        booleanModifiers = new ArrayList<BooleanModifier>();
    }

    //---------------------------------------------------------------------------
    //
    // PUBLIC METHODS
    //
    //---------------------------------------------------------------------------

    public float getModifier(final FloatModifier modifier, final float currentModifier) {
        if (!modifiers.containsKey(modifier)) {
            return 1.0f;
        }

        final float multiplier = modifiers.get(modifier)[0];
        final float max = modifiers.get(modifier)[1];

        if (max >= 1.0f) {
            if (max <= currentModifier) {
                return 1.0f;
            }

            return Math.min(max / currentModifier, multiplier);
        }

        if (max >= currentModifier) {
            return 1.0f;
        }

        return Math.max(max / currentModifier, multiplier);
    }

    public boolean getModifier(final BooleanModifier modifier) {
        return booleanModifiers.contains(modifier);
    }

    public void addModifier(final BooleanModifier modifier) {
        booleanModifiers.add(modifier);
    }

    public void addModifier(final FloatModifier modifier, final float multiplier, final float max) {
        modifiers.put(modifier, new Float[]{
                multiplier,
                max
        });
    }

    //---------------------------------------------------------------------------
    //
    // ENUMS
    //
    //---------------------------------------------------------------------------

    public enum BooleanModifier {
        Sealed,
        SelfLighted,
        SunlightStimulated,
        Hellish
    }

    public enum FloatModifier {
        Territory,
        Mutation,
        Lifespan,
        Production,
        Flowering,
        GeneticDecay;

        public String getName() {
            return Binnie.Language.localise(BinnieCore.instance, "beemodifier." + name().toLowerCase());
        }
    }
}
