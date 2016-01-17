package binnie.extrabees.apiary;

import forestry.api.apiculture.DefaultBeeModifier;
import forestry.api.apiculture.IBeeGenome;

/**
 * @author Alexander Kornilov (KorDum)
 */

public class HiveFrameBeeModifier extends DefaultBeeModifier {
    private final float geneticDecay;
    private float productionModifier;
    private float maxProductionModifier;
    private float lifespanModifier;
    private float maxLifespanModifier;
    private float territoryModifier;
    private float maxTerritoryModifier;
    private float mutationModifier;
    private float maxMutationModifier;

    //---------------------------------------------------------------------------
    //
    // CONSTRUCTOR
    //
    //---------------------------------------------------------------------------

    public HiveFrameBeeModifier(float geneticDecay) {
        this.geneticDecay = geneticDecay;
    }

    //---------------------------------------------------------------------------
    //
    // HANDLERS
    //
    //---------------------------------------------------------------------------

    //---------------------------------------------------------------------------
    //
    // PRIVATE METHODS
    //
    //---------------------------------------------------------------------------

    private float getModifier(final float value, final float max, final float currentModifier) {
        if (value == 0) {
            return 1.0f;
        }

        if (max >= 1.0f) {
            if (max <= currentModifier) {
                return 1.0f;
            }

            return Math.min(max / currentModifier, value);
        }

        if (max >= currentModifier) {
            return 1.0f;
        }

        return Math.max(max / currentModifier, value);
    }

    //---------------------------------------------------------------------------
    //
    // PUBLIC METHODS
    //
    //---------------------------------------------------------------------------

    //---------------------------------------------------------------------------
    //
    // ACCESSORS
    //
    //---------------------------------------------------------------------------

    @Override
    public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
        return geneticDecay;
    }

    @Override
    public float getProductionModifier(IBeeGenome genome, float currentModifier) {
        return getModifier(productionModifier, maxProductionModifier, currentModifier);
    }

    public void setProductionModifier(float value, float max) {
        productionModifier = value;
        maxProductionModifier = max;
    }

    @Override
    public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return getModifier(lifespanModifier, maxLifespanModifier, currentModifier);
    }

    public void setLifespanModifier(float value, float max) {
        lifespanModifier = value;
        maxLifespanModifier = max;
    }

    @Override
    public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
        return getModifier(territoryModifier, maxTerritoryModifier, currentModifier);
    }

    public void setTerritoryModifier(float value, float max) {
        territoryModifier = value;
        maxTerritoryModifier = max;
    }

    @Override
    public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
        return getModifier(mutationModifier, maxMutationModifier, currentModifier);
    }

    public void setMutationModifier(float value, float max) {
        mutationModifier = value;
        maxMutationModifier = max;
    }
}