package binnie.extrabees.apiary.machine;

import binnie.core.Mods;
import binnie.core.circuits.BinnieCircuit;
import binnie.core.genetics.BeeModifier;
import binnie.core.genetics.BeeModifierLogic;
import binnie.core.genetics.BeeModifierLogic.FloatModifier;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitBoard;
import forestry.api.circuits.ICircuitLayout;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AlvearyStimulator {
    public static int slotCircuit;

    static {
        AlvearyStimulator.slotCircuit = 0;
    }

    public static class PackageAlvearyStimulator extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
        public PackageAlvearyStimulator() {
            super("stimulator", ExtraBeeTexture.AlvearyStimulator.getTexture(), true);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyStimulator);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(AlvearyStimulator.slotCircuit, "circuit");
            inventory.getSlot(AlvearyStimulator.slotCircuit).setValidator(new SlotValidatorCircuit());
            final ComponentPowerReceptor power = new ComponentPowerReceptor(machine);
            new ComponentStimulatorModifier(machine);
        }
    }

    public static class SlotValidatorCircuit extends SlotValidator {
        public SlotValidatorCircuit() {
            super(SlotValidator.IconCircuit);
        }

        @Override
        public boolean isValid(final ItemStack itemStack) {
            return itemStack != null && ChipsetManager.circuitRegistry.isChipset(itemStack);
        }

        @Override
        public String getTooltip() {
            return "Forestry Circuits";
        }
    }

    public static class ComponentStimulatorModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
        float powerUsage;
        boolean powered;
        StimulatorCircuit[] modifiers;

        public ComponentStimulatorModifier(final Machine machine) {
            super(machine);
            powerUsage = 0.0f;
            powered = false;
            modifiers = new StimulatorCircuit[0];
        }

        public void onUpdate() {
            super.onUpdate();
            modifiers = getCircuits();
            powerUsage = 0.0f;

            for (final StimulatorCircuit beeMod : modifiers) {
                powerUsage += beeMod.getPowerUsage();
            }

            powered = getUtil().hasEnergyMJ(powerUsage);
        }

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
            final List<IBeeModifier> mod = new ArrayList<IBeeModifier>();

            for (final ICircuit circuit : circuits) {
                if (circuit instanceof StimulatorCircuit) {
                    mod.add((IBeeModifier) circuit);
                }
            }

            return mod.toArray(new StimulatorCircuit[0]);
        }

        @Override
        public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
            float mod = 1.0f;

            if (powered) {
                for (final IBeeModifier beeMod : modifiers) {
                    mod *= beeMod.getTerritoryModifier(genome, mod);
                }
            }

            return mod;
        }

        @Override
        public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
            float mod = 1.0f;

            if (powered) {
                for (final IBeeModifier beeMod : modifiers) {
                    mod *= beeMod.getMutationModifier(genome, mate, mod);
                }
            }

            return mod;
        }

        @Override
        public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
            float mod = 1.0f;

            if (powered) {
                for (final IBeeModifier beeMod : modifiers) {
                    mod *= beeMod.getLifespanModifier(genome, mate, mod);
                }
            }

            return mod;
        }

        @Override
        public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
            float mod = 1.0f;

            if (powered) {
                for (final IBeeModifier beeMod : modifiers) {
                    mod *= beeMod.getProductionModifier(genome, mod);
                }
            }

            return mod;
        }

        @Override
        public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
            float mod = 1.0f;

            if (powered) {
                for (final IBeeModifier beeMod : modifiers) {
                    mod *= beeMod.getFloweringModifier(genome, mod);
                }
            }

            return mod;
        }

        @Override
        public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
            float mod = 1.0f;

            if (powered) {
                for (final IBeeModifier beeMod : modifiers) {
                    mod *= beeMod.getGeneticDecay(genome, mod);
                }
            }

            return mod;
        }

        @Override
        public boolean isSealed() {
            if (powered) {
                for (final IBeeModifier beeMod : modifiers) {
                    if (beeMod.isSealed()) {
                        return true;
                    }
                }
            }

            return false;
        }

        @Override
        public boolean isSelfLighted() {
            if (powered) {
                for (final IBeeModifier beeMod : modifiers) {
                    if (beeMod.isSelfLighted()) {
                        return true;
                    }
                }
            }

            return false;
        }

        @Override
        public boolean isSunlightSimulated() {
            if (powered) {
                for (final IBeeModifier beeMod : modifiers) {
                    if (beeMod.isSunlightSimulated()) {
                        return true;
                    }
                }
            }

            return false;
        }

        @Override
        public boolean isHellish() {
            if (powered) {
                for (final IBeeModifier beeMod : modifiers) {
                    if (beeMod.isHellish()) {
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

    public static class StimulatorCircuit extends BinnieCircuit implements IBeeModifier {
        Circuit type;

        public StimulatorCircuit(final Circuit type, final ICircuitLayout layout) {
            super("stimulator." + type.toString().toLowerCase(), 4, layout, Mods.Forestry.item("thermionicTubes"), type.recipe);
            this.type = type;
        }

        public int getPowerUsage() {
            return type.power;
        }

        public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
            return type.getTerritoryModifier(genome, currentModifier);
        }

        public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
            return type.getMutationModifier(genome, mate, currentModifier);
        }

        public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
            return type.getLifespanModifier(genome, mate, currentModifier);
        }

        public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
            return type.getProductionModifier(genome, currentModifier);
        }

        public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
            return type.getFloweringModifier(genome, currentModifier);
        }

        public boolean isSealed() {
            return type.isSealed();
        }

        public boolean isSelfLighted() {
            return type.isSelfLighted();
        }

        public boolean isSunlightSimulated() {
            return type.isSunlightSimulated();
        }

        public boolean isHellish() {
            return type.isHellish();
        }

        public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
            return type.getGeneticDecay(genome, currentModifier);
        }
    }

    public static class Circuit extends BeeModifier {
        public int recipe;
        public int power;

        public Circuit(final int recipe, final int power, final ICircuitLayout layout) {
            super(new BeeModifierLogic());
            this.recipe = recipe;
            this.power = power;

            createCircuit(layout);
        }

        private void createCircuit(final ICircuitLayout layout) {
            final StimulatorCircuit circuit = new StimulatorCircuit(this, layout);
            for (final FloatModifier modifier : FloatModifier.values()) {
                final float mod = logic.getModifier(modifier, 1.0f);

                if (mod != 1.0f) {
                    if (mod > 1.0f) {
                        final int increase = (int) ((mod - 1.0f) * 100.0f);
                        circuit.addTooltipString("Increases " + modifier.getName() + " by " + increase + "%");
                    }
                    else {
                        final int decrease = (int) ((1.0f - mod) * 100.0f);
                        circuit.addTooltipString("Decreases " + modifier.getName() + " by " + decrease + "%");
                    }
                }
            }
        }
    }
}
