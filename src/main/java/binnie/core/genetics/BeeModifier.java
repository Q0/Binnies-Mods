package binnie.core.genetics;

import binnie.core.genetics.BeeModifierLogic.BooleanModifier;
import binnie.core.genetics.BeeModifierLogic.FloatModifier;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;

/**
 * @author Alexander Kornilov (KorDum)
 */

public class BeeModifier implements IBeeModifier {
    protected BeeModifierLogic logic;

    //---------------------------------------------------------------------------
    //
    // CONSTRUCTOR
    //
    //---------------------------------------------------------------------------

    public BeeModifier(BeeModifierLogic logic) {
        this.logic = logic;
    }

    //---------------------------------------------------------------------------
    //
    // PUBLIC METHODS
    //
    //---------------------------------------------------------------------------

    public void addModifier(final BooleanModifier modifier) {
        logic.addModifier(modifier);
    }

    public void addModifier(final FloatModifier modifier, final float value, final float max) {
        logic.addModifier(modifier, value, max);
    }

    //---------------------------------------------------------------------------
    //
    // ACCESSORS
    //
    //---------------------------------------------------------------------------

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(FloatModifier.Territory, currentModifier);
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return logic.getModifier(FloatModifier.Mutation, currentModifier);
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return logic.getModifier(FloatModifier.Lifespan, currentModifier);
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(FloatModifier.Production, currentModifier);
    }

    @Override
    public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(FloatModifier.Flowering, currentModifier);
    }

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        return logic.getModifier(FloatModifier.GeneticDecay, currentModifier);
    }

    @Override
    public boolean isSealed() {
        return logic.getModifier(BooleanModifier.Sealed);
    }

    @Override
    public boolean isSelfLighted() {
        return logic.getModifier(BooleanModifier.SelfLighted);
    }

    @Override
    public boolean isSunlightSimulated() {
        return logic.getModifier(BooleanModifier.SunlightStimulated);
    }

    @Override
    public boolean isHellish() {
        return logic.getModifier(BooleanModifier.Hellish);
    }
}
