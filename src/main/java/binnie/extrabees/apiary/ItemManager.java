package binnie.extrabees.apiary;

import binnie.core.circuits.BinnieCircuitLayout;
import binnie.core.genetics.BeeModifier;
import binnie.core.genetics.BeeModifierLogic.BooleanModifier;
import binnie.core.genetics.BeeModifierLogic.FloatModifier;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.apiary.machine.AlvearyStimulator;

public class ItemManager {
    public static ItemHiveFrame cocoaFrame;
    public static ItemHiveFrame soulFrame;
    public static ItemHiveFrame cageFrame;
    public static ItemHiveFrame clayFrame;
    public static ItemHiveFrame debugFrame;
    
    public static AlvearyStimulator.Circuit lowVoltageCircuit;
    public static AlvearyStimulator.Circuit highVoltageCircuit;
    public static AlvearyStimulator.Circuit plantCircuit;
    public static AlvearyStimulator.Circuit deathCircuit;
    public static AlvearyStimulator.Circuit lifeCircuit;
    public static AlvearyStimulator.Circuit netherCircuit;
    public static AlvearyStimulator.Circuit mutationCircuit;
    public static AlvearyStimulator.Circuit inhibitorCircuit;
    public static AlvearyStimulator.Circuit territoryCircuit;

    private static BinnieCircuitLayout stimulatorLayout;

    //---------------------------------------------------------------------------
    //
    // PRIVATE METHODS
    //
    //---------------------------------------------------------------------------
    
    private static void registerFrames() {
        cocoaFrame = new ItemHiveFrame("hiveFrame.cocoa", 240);
        BeeModifier modifier = (BeeModifier) cocoaFrame.getBeeModifier();
        modifier.addModifier(FloatModifier.GeneticDecay, 1.0f, 10.0f);
        modifier.addModifier(FloatModifier.Production, 1.5f, 5.0f);
        modifier.addModifier(FloatModifier.Lifespan, 0.75f, 0.25f);

        cageFrame = new ItemHiveFrame("hiveFrame.cage", 240);
        modifier = (BeeModifier) cageFrame.getBeeModifier();
        modifier.addModifier(FloatModifier.GeneticDecay, 1.0f, 10.0f);
        modifier.addModifier(FloatModifier.Production, 0.75f, 0.5f);
        modifier.addModifier(FloatModifier.Lifespan, 0.75f, 0.5f);
        modifier.addModifier(FloatModifier.Territory, 0.5f, 0.1f);

        soulFrame = new ItemHiveFrame("hiveFrame.soul", 80);
        modifier = (BeeModifier) soulFrame.getBeeModifier();
        modifier.addModifier(FloatModifier.GeneticDecay, 1.0f, 10.0f);
        modifier.addModifier(FloatModifier.Production, 0.75f, 0.5f);
        modifier.addModifier(FloatModifier.Lifespan, 0.75f, 0.5f);
        modifier.addModifier(FloatModifier.Territory, 0.5f, 0.1f);
        modifier.addModifier(FloatModifier.Mutation, 1.5f, 5.0f);

        clayFrame = new ItemHiveFrame("hiveFrame.clay", 80);
        modifier = (BeeModifier) clayFrame.getBeeModifier();
        modifier.addModifier(FloatModifier.GeneticDecay, 1.0f, 10.0f);
        modifier.addModifier(FloatModifier.Production, 0.75f, 0.2f);
        modifier.addModifier(FloatModifier.Lifespan, 1.5f, 5.0f);
        modifier.addModifier(FloatModifier.Mutation, 0.5f, 0.2f);

        debugFrame = new ItemHiveFrame("hiveFrame.debug", 1000);
        modifier = (BeeModifier) soulFrame.getBeeModifier();
        modifier.addModifier(FloatModifier.GeneticDecay, 1.0f, 10.0f);
        modifier.addModifier(FloatModifier.Lifespan, 1.0E-4f, 1.0E-4f);
    }
    
    private static void registerCircuits() {
        lowVoltageCircuit = new AlvearyStimulator.Circuit(3, 10, stimulatorLayout);
        lowVoltageCircuit.addModifier(FloatModifier.Production, 1.5f, 5.0f);
        lowVoltageCircuit.addModifier(FloatModifier.GeneticDecay, 1.5f, 10.0f);
        
        highVoltageCircuit = new AlvearyStimulator.Circuit(5, 20, stimulatorLayout);
        highVoltageCircuit.addModifier(FloatModifier.Production, 2.5f, 10.0f);
        highVoltageCircuit.addModifier(FloatModifier.GeneticDecay, 1.5f, 10.0f);

        plantCircuit = new AlvearyStimulator.Circuit(10, 10, stimulatorLayout);
        plantCircuit.addModifier(FloatModifier.Flowering, 1.5f, 5.0f);
        plantCircuit.addModifier(FloatModifier.GeneticDecay, 1.5f, 10.0f);

        deathCircuit = new AlvearyStimulator.Circuit(6, 10, stimulatorLayout);
        deathCircuit.addModifier(FloatModifier.Lifespan, 0.8f, 0.2f);
        deathCircuit.addModifier(FloatModifier.GeneticDecay, 1.5f, 10.0f);

        lifeCircuit = new AlvearyStimulator.Circuit(11, 10, stimulatorLayout);
        lifeCircuit.addModifier(FloatModifier.Lifespan, 1.5f, 5.0f);
        lifeCircuit.addModifier(FloatModifier.GeneticDecay, 1.5f, 10.0f);

        netherCircuit = new AlvearyStimulator.Circuit(7, 15, stimulatorLayout);
        netherCircuit.addModifier(BooleanModifier.Hellish);
        netherCircuit.addModifier(FloatModifier.GeneticDecay, 1.5f, 10.0f);

        mutationCircuit = new AlvearyStimulator.Circuit(4, 15, stimulatorLayout);
        mutationCircuit.addModifier(FloatModifier.Mutation, 1.5f, 5.0f);
        mutationCircuit.addModifier(FloatModifier.GeneticDecay, 1.5f, 10.0f);

        inhibitorCircuit = new AlvearyStimulator.Circuit(1, 10, stimulatorLayout);
        inhibitorCircuit.addModifier(FloatModifier.Territory, 0.4f, 0.1f);
        inhibitorCircuit.addModifier(FloatModifier.Production, 0.9f, 0.5f);
        inhibitorCircuit.addModifier(FloatModifier.GeneticDecay, 1.5f, 10.0f);

        territoryCircuit = new AlvearyStimulator.Circuit(2, 10, stimulatorLayout);
        territoryCircuit.addModifier(FloatModifier.Territory, 1.5f, 5.0f);
        territoryCircuit.addModifier(FloatModifier.GeneticDecay, 1.5f, 10.0f);
    }

    //---------------------------------------------------------------------------
    //
    // PUBLIC METHODS
    //
    //---------------------------------------------------------------------------

    public static void registerItems() {
        stimulatorLayout = new BinnieCircuitLayout(ExtraBees.instance, "Stimulator");
        
        registerFrames();
        registerCircuits();
    }
}
