package binnie.extrabees.apiary;

import binnie.core.genetics.BeeModifier;
import binnie.core.genetics.BeeModifierLogic.FloatModifier;

public class ItemManager {
    public static ItemHiveFrame cocoaFrame;
    public static ItemHiveFrame soulFrame;
    public static ItemHiveFrame cageFrame;
    public static ItemHiveFrame clayFrame;
    public static ItemHiveFrame debugFrame;

    //---------------------------------------------------------------------------
    //
    // PUBLIC METHODS
    //
    //---------------------------------------------------------------------------

    public static void registerItems() {
        /**
         * Frames
         */
        cocoaFrame = new ItemHiveFrame("hiveFrame.cocoa", 240);
        BeeModifier modifier = (BeeModifier) cocoaFrame.getBeeModifier();
        modifier.addModifier(FloatModifier.GeneticDecay, 1.0f, 1.0f);
        modifier.addModifier(FloatModifier.Production, 1.5f, 5.0f);
        modifier.addModifier(FloatModifier.Lifespan, 0.75f, 0.25f);

        cageFrame = new ItemHiveFrame("hiveFrame.cage", 240);
        modifier = (BeeModifier) cageFrame.getBeeModifier();
        modifier.addModifier(FloatModifier.GeneticDecay, 1.0f, 1.0f);
        modifier.addModifier(FloatModifier.Production, 0.75f, 0.5f);
        modifier.addModifier(FloatModifier.Lifespan, 0.75f, 0.5f);
        modifier.addModifier(FloatModifier.Territory, 0.5f, 0.1f);

        soulFrame = new ItemHiveFrame("hiveFrame.soul", 80);
        modifier = (BeeModifier) soulFrame.getBeeModifier();
        modifier.addModifier(FloatModifier.GeneticDecay, 1.0f, 1.0f);
        modifier.addModifier(FloatModifier.Production, 0.75f, 0.5f);
        modifier.addModifier(FloatModifier.Lifespan, 0.75f, 0.5f);
        modifier.addModifier(FloatModifier.Territory, 0.5f, 0.1f);
        modifier.addModifier(FloatModifier.Mutation, 1.5f, 5.0f);

        clayFrame = new ItemHiveFrame("hiveFrame.clay", 80);
        modifier = (BeeModifier) clayFrame.getBeeModifier();
        modifier.addModifier(FloatModifier.GeneticDecay, 1.0f, 1.0f);
        modifier.addModifier(FloatModifier.Production, 0.75f, 0.2f);
        modifier.addModifier(FloatModifier.Lifespan, 1.5f, 5.0f);
        modifier.addModifier(FloatModifier.Mutation, 0.5f, 0.2f);

        debugFrame = new ItemHiveFrame("hiveFrame.debug", 1000);
        modifier = (BeeModifier) soulFrame.getBeeModifier();
        modifier.addModifier(FloatModifier.GeneticDecay, 1.0f, 1.0f);
        modifier.addModifier(FloatModifier.Lifespan, 1.0E-4f, 1.0E-4f);
    }
}
