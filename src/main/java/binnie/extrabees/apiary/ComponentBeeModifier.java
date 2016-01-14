package binnie.extrabees.apiary;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;

public class ComponentBeeModifier extends MachineComponent implements IBeeModifier, IBeeListener {
    public ComponentBeeModifier(final Machine machine) {
        super(machine);
    }

    public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return 1.0f;
    }

    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return 1.0f;
    }

    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }

    public boolean isSealed() {
        return false;
    }

    public boolean isSelfLighted() {
        return false;
    }

    public boolean isSunlightSimulated() {
        return false;
    }

    public boolean isHellish() {
        return false;
    }

    public void onQueenChange(final ItemStack queen) {
    }

    public void wearOutEquipment(final int amount) {
    }

    @Override
    public void onQueenDeath() {

    }

    @Override
    public boolean onPollenRetrieved(IIndividual pollen) {
        return false;
    }

    public void onQueenDeath(final IBee queen) {
    }

    public void onPostQueenDeath(final IBee queen) {
    }

    public boolean onPollenRetrieved(final IBee queen, final IIndividual pollen, final boolean isHandled) {
        return false;
    }

    public boolean onEggLaid(final IBee queen) {
        return false;
    }

    public float getGeneticDecay(final IBeeGenome genome, final float currentModifier) {
        return 1.0f;
    }
}
