package binnie.extrabees.apiary.modifiers;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.extrabees.apiary.machine.AlvearyFrame;
import forestry.api.apiculture.*;
import net.minecraft.world.World;

public class ComponentFrameModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
    public ComponentFrameModifier(final Machine machine) {
        super(machine);
    }

    @Override
    public void wearOutEquipment(final int amount) {
        if (getHiveFrame() == null) {
            return;
        }
        final World world = getMachine().getTileEntity().getWorldObj();
        final int wear = Math.round(amount * 5 * Binnie.Genetics.getBeeRoot().getBeekeepingMode(world).getWearModifier());
        //TODO: FIX
        getInventory().setInventorySlotContents(AlvearyFrame.slotFrame, getHiveFrame().frameUsed((IBeeHousing) getMachine().getTileEntity(), getInventory().getStackInSlot(AlvearyFrame.slotFrame), (IBee) null, wear));
    }

    public IHiveFrame getHiveFrame() {
        if (getInventory().getStackInSlot(AlvearyFrame.slotFrame) != null) {
            return (IHiveFrame) getInventory().getStackInSlot(AlvearyFrame.slotFrame).getItem();
        }
        return null;
    }

    @Override
    public float getTerritoryModifier(final IBeeGenome genome, final float currentModifier) {
        return (getHiveFrame() == null) ? 1.0f : getHiveFrame().getBeeModifier().getTerritoryModifier(genome, currentModifier);
    }

    @Override
    public float getMutationModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return (getHiveFrame() == null) ? 1.0f : getHiveFrame().getBeeModifier().getMutationModifier(genome, mate, currentModifier);
    }

    @Override
    public float getLifespanModifier(final IBeeGenome genome, final IBeeGenome mate, final float currentModifier) {
        return (getHiveFrame() == null) ? 1.0f : getHiveFrame().getBeeModifier().getLifespanModifier(genome, mate, currentModifier);
    }

    @Override
    public float getProductionModifier(final IBeeGenome genome, final float currentModifier) {
        return (getHiveFrame() == null) ? 1.0f : getHiveFrame().getBeeModifier().getProductionModifier(genome, currentModifier);
    }

    @Override
    public float getFloweringModifier(final IBeeGenome genome, final float currentModifier) {
        return (getHiveFrame() == null) ? 1.0f : getHiveFrame().getBeeModifier().getFloweringModifier(genome, currentModifier);
    }
}
