package binnie.extrabees.apiary.modifiers;

import binnie.core.machines.Machine;
import binnie.extrabees.apiary.items.StimulatorCircuit;
import binnie.extrabees.apiary.machine.AlvearyStimulator;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitBoard;

import java.util.ArrayList;
import java.util.List;

public class ComponentStimulatorModifier extends ComponentBeeModifier {
    private StimulatorCircuit[] circuits;
    private float powerUsage;
    private boolean powered;

    //---------------------------------------------------------------------------
    //
    // CONSTRUCTOR
    //
    //---------------------------------------------------------------------------

    public ComponentStimulatorModifier(final Machine machine) {
        super(machine);
        powerUsage = 0.0f;
        powered = false;
        circuits = new StimulatorCircuit[0];
    }

    //---------------------------------------------------------------------------
    //
    // HANDLERS
    //
    //---------------------------------------------------------------------------

    public void onUpdate() {
        super.onUpdate();
        circuits = getCircuits();
        powerUsage = 0.0f;

        for (final StimulatorCircuit circuit : circuits) {
            powerUsage += circuit.getModifier().getPower();
        }

        powered = getUtil().hasEnergyMJ(powerUsage);
    }

    //---------------------------------------------------------------------------
    //
    // PUBLIC METHODS
    //
    //---------------------------------------------------------------------------

    @Override
    public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
        float mod = 1.0f;

        if (powered) {
            for (final StimulatorCircuit circuit : circuits) {
                mod *= circuit.getModifier().getTerritoryModifier(genome, mod);
            }
        }

        return mod;
    }

    @Override
    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        float mod = 1.0f;

        if (powered) {
            for (final StimulatorCircuit circuit : circuits) {
                mod *= circuit.getModifier().getMutationModifier(genome, mate, mod);
            }
        }

        return mod;
    }

    @Override
    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        float mod = 1.0f;

        if (powered) {
            for (final StimulatorCircuit circuit : circuits) {
                mod *= circuit.getModifier().getLifespanModifier(genome, mate, mod);
            }
        }

        return mod;
    }

    @Override
    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        float mod = 1.0f;

        if (powered) {
            for (final StimulatorCircuit circuit : circuits) {
                mod *= circuit.getModifier().getProductionModifier(genome, mod);
            }
        }

        return mod;
    }

    @Override
    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        float mod = 1.0f;

        if (powered) {
            for (final StimulatorCircuit circuit : circuits) {
                mod *= circuit.getModifier().getFloweringModifier(genome, mod);
            }
        }

        return mod;
    }

    @Override
    public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
        float mod = 1.0f;

        if (powered) {
            for (final StimulatorCircuit circuit : circuits) {
                mod *= circuit.getModifier().getGeneticDecay(genome, mod);
            }
        }

        return mod;
    }

    //---------------------------------------------------------------------------
    //
    // ACCESSOR
    //
    //---------------------------------------------------------------------------

    public ICircuitBoard getHiveFrame() {
        if (!getUtil().isSlotEmpty(AlvearyStimulator.slotCircuit)) {
            return ChipsetManager.circuitRegistry.getCircuitboard(getUtil().getStack(AlvearyStimulator.slotCircuit));
        }

        return null;
    }

    public StimulatorCircuit[] getCircuits() {
        final ICircuitBoard board = getHiveFrame();

        if (board == null) {
            return new StimulatorCircuit[0];
        }

        final ICircuit[] circuits = board.getCircuits();
        final List<StimulatorCircuit> mod = new ArrayList<StimulatorCircuit>();

        for (final ICircuit circuit : circuits) {
            if (circuit instanceof StimulatorCircuit) {
                mod.add((StimulatorCircuit) circuit);
            }
        }

        return mod.toArray(new StimulatorCircuit[0]);
    }

    @Override
    public boolean isSealed() {
        if (powered) {
            for (final StimulatorCircuit circuit : circuits) {
                if (circuit.getModifier().isSealed()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isSelfLighted() {
        if (powered) {
            for (final StimulatorCircuit circuit : circuits) {
                if (circuit.getModifier().isSelfLighted()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isSunlightSimulated() {
        if (powered) {
            for (final StimulatorCircuit circuit : circuits) {
                if (circuit.getModifier().isSunlightSimulated()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean isHellish() {
        if (powered) {
            for (final StimulatorCircuit circuit : circuits) {
                if (circuit.getModifier().isHellish()) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void wearOutEquipment(final int amount) {
        getUtil().useEnergyMJ(powerUsage);
    }
}
