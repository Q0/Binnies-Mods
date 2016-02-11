package binnie.extrabees.apiary.machine;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.transfer.TransferRequest;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extrabees.apiary.ComponentBeeModifier;
import binnie.extrabees.apiary.ComponentExtraBeeGUI;
//import binnie.extrabees.apiary.TileExtraBeeAlveary; //TODO: UPD TO Forestry4
import binnie.extrabees.core.ExtraBeeGUID;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.apiculture.*;
import forestry.api.genetics.IIndividual;
//import forestry.core.EnumErrorCode; //TODO: UPD TO Forestry4
import forestry.core.errors.EnumErrorCode;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class AlvearyHatchery {
    public static int[] slotLarvae;

    static {
        AlvearyHatchery.slotLarvae = new int[]{0, 1, 2, 3, 4};
    }

    public static class PackageAlvearyHatchery extends AlvearyMachine.AlvearyPackage implements IMachineInformation {
        public PackageAlvearyHatchery() {
            super("hatchery", ExtraBeeTexture.AlvearyHatchery.getTexture(), false);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentExtraBeeGUI(machine, ExtraBeeGUID.AlvearyHatchery);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            for (final InventorySlot slot : inventory.addSlotArray(AlvearyHatchery.slotLarvae, "hatchery")) {
                slot.setValidator(new SlotValidatorLarvae());
            }
            new ComponentFrameModifier(machine);
        }
    }

    public static class SlotValidatorLarvae extends SlotValidator {
        public SlotValidatorLarvae() {
            super(SlotValidator.IconBee);
        }

        @Override
        public boolean isValid(final ItemStack itemStack) {
            return Binnie.Genetics.getBeeRoot().isMember(itemStack) && Binnie.Genetics.getBeeRoot().getType(itemStack) == EnumBeeType.LARVAE;
        }

        @Override
        public String getTooltip() {
            return "Larvae";
        }
    }

    public static class ComponentFrameModifier extends ComponentBeeModifier implements IBeeModifier, IBeeListener {
        public ComponentFrameModifier(final Machine machine) {
            super(machine);
        }

        public void onUpdate() {
            if (new Random().nextInt(2400) == 0) {
                final TileEntity tile = this.getMachine().getTileEntity();
                if (tile instanceof TileExtraBeeAlveary) {
                    final IBeeHousing house = ((TileExtraBeeAlveary) tile).getBeeHousing();
                    if (house != null && house.getErrorState() == EnumErrorCode.OK) {
                        final ItemStack queenStack = house.getQueen();
                        final IBee queen = (queenStack == null) ? null : Binnie.Genetics.getBeeRoot().getMember(queenStack);
                        if (queen != null) {
                            final ItemStack larvae = Binnie.Genetics.getBeeRoot().getMemberStack(Binnie.Genetics.getBeeRoot().getBee(this.getMachine().getWorld(), queen.getGenome()), EnumBeeType.LARVAE.ordinal());
                            new TransferRequest(larvae, this.getInventory()).transfer(true);
                        }
                    }
                }
            }
        }

        @Override
        public void onQueenDeath() {
            //TODO: UPD TO Forestry4
        }

        @Override
        public boolean onPollenRetrieved(IIndividual pollen) {
            //TODO: UPD TO Forestry4
            return false;
        }
    }
}
