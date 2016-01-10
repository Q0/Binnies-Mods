package binnie.extratrees.machines;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentInventoryTransfer;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.SlotValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Press {
    public static int slotFruit = 0;
    public static int slotCurrent = 1;
    public static int tankWater = 0;
    private static Map pressRecipes = new HashMap();

    public Press() {
        super();
    }

    public static boolean isInput(ItemStack itemstack) {
        return getOutput(itemstack) != null;
    }

    public static FluidStack getOutput(ItemStack itemstack) {
        if (itemstack == null) {
            return null;
        } else {
            for (Entry<ItemStack, FluidStack> entry : pressRecipes.entrySet()) {
                if (itemstack.isItemEqual((ItemStack) entry.getKey())) {
                    return (FluidStack) entry.getValue();
                }
            }

            return null;
        }
    }

    public static void addRecipe(ItemStack stack, FluidStack fluid) {
        if (getOutput(stack) == null) {
            pressRecipes.put(stack, fluid);
        }
    }

    public static class ComponentFruitPressLogic extends ComponentProcessSetCost implements IProcess {
        int lastProgress = 0;

        public ComponentFruitPressLogic(Machine machine) {
            super(machine, 1000, 50);
        }

        public ErrorState canWork() {
            return (ErrorState) (this.getUtil().isSlotEmpty(Press.slotCurrent) ? new ErrorState.NoItem("No Fruit", Press.slotCurrent) : super.canWork());
        }

        public ErrorState canProgress() {
            return (ErrorState) (!this.getUtil().spaceInTank(Press.tankWater, 5) ? new ErrorState.TankSpace("No room in tank", Press.tankWater) : (this.getUtil().getFluid(Press.tankWater) != null && !this.getUtil().getFluid(Press.tankWater).isFluidEqual(Press.getOutput(this.getUtil().getStack(Press.slotCurrent))) ? new ErrorState.TankSpace("Different fluid in tank", Press.tankWater) : super.canProgress()));
        }

        protected void onFinishTask() {
            this.getUtil().decreaseStack(Press.slotCurrent, 1);
        }

        protected void onTickTask() {
        }

        public void onUpdate() {
            super.onUpdate();
            FluidStack output = Press.getOutput(this.getUtil().getStack(Press.slotCurrent));
            if (output != null) {
                int newProgress;
                for (newProgress = (int) this.getProgress(); this.lastProgress + 4 <= newProgress; this.lastProgress += 4) {
                    int change = newProgress - this.lastProgress;
                    int amount = output.amount * change / 100;
                    FluidStack tank = new FluidStack(output, amount);
                    this.getUtil().fillTank(Press.tankWater, tank);
                }

                if (this.lastProgress > newProgress) {
                    this.lastProgress = 0;
                }

            }
        }

        protected void onStartTask() {
            super.onStartTask();
            this.lastProgress = 0;
        }
    }

    public static class PackagePress extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
        public PackagePress() {
            super("press", ExtraTreeTexture.pressTexture, true);
        }

        public void createMachine(Machine machine) {
            new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Press);
            ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(Press.slotFruit, "input");
            inventory.getSlot(Press.slotFruit).setValidator(new Press.SlotValidatorSqueezable());
            inventory.getSlot(Press.slotFruit).forbidExtraction();
            inventory.addSlot(Press.slotCurrent, "process");
            inventory.getSlot(Press.slotCurrent).setValidator(new Press.SlotValidatorSqueezable());
            inventory.getSlot(Press.slotCurrent).forbidInteraction();
            ComponentTankContainer tanks = new ComponentTankContainer(machine);
            tanks.addTank(Press.tankWater, "output", 5000);
            new ComponentPowerReceptor(machine);
            (new ComponentInventoryTransfer(machine)).addRestock(new int[]{Press.slotFruit}, Press.slotCurrent, 1);
            new Press.ComponentFruitPressLogic(machine);
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }
    }

    public static class SlotValidatorSqueezable extends SlotValidator {
        public SlotValidatorSqueezable() {
            super(SlotValidator.IconBlock);
        }

        public boolean isValid(ItemStack itemStack) {
            return Press.isInput(itemStack);
        }

        public String getTooltip() {
            return "Fruit";
        }
    }
}
