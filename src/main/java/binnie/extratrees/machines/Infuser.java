package binnie.extratrees.machines;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Infuser {
    public static int tankInput = 0;
    public static int tankOutput = 1;
    static Map recipes = new HashMap();

    public Infuser() {
        super();
    }

    public static FluidStack getOutput(FluidStack fluid, ItemStack stack) {
        return fluid == null ? null : (FluidStack) recipes.get(fluid.getFluid());
    }

    public static boolean isValidInputLiquid(FluidStack fluid) {
        return recipes.containsKey(fluid.getFluid());
    }

    public static boolean isValidOutputLiquid(FluidStack fluid) {
        for (Entry<Fluid, FluidStack> entry : recipes.entrySet()) {
            if (((FluidStack) entry.getValue()).isFluidEqual(fluid)) {
                return true;
            }
        }

        return false;
    }

    public static void addRecipe(FluidStack input, FluidStack output) {
        recipes.put(input.getFluid(), output);
    }

    public static class ComponentInfuserLogic extends ComponentProcessSetCost implements IProcess {
        ItemStack infusing;

        public ComponentInfuserLogic(Machine machine) {
            super(machine, 16000, 800);
        }

        public int getProcessEnergy() {
            return this.getProcessLength() * 2;
        }

        public int getProcessLength() {
            return 20;
        }

        public ErrorState canWork() {
            return (ErrorState) (this.getUtil().isTankEmpty(Infuser.tankInput) ? new ErrorState.InsufficientLiquid("No Input Liquid", Infuser.tankInput) : super.canWork());
        }

        public ErrorState canProgress() {
            return (ErrorState) (!this.getUtil().isTankEmpty(Infuser.tankOutput) && this.getOutput() != null && !this.getOutput().isFluidEqual(this.getUtil().getFluid(Infuser.tankOutput)) ? new ErrorState.Tank("No Room", "No room for liquid", new int[]{Infuser.tankOutput}) : (this.getUtil().getFluid(Infuser.tankOutput) != null && !this.getUtil().getFluid(Infuser.tankOutput).isFluidEqual(this.getOutput()) ? new ErrorState.TankSpace("Different fluid in tank", Infuser.tankOutput) : super.canProgress()));
        }

        private FluidStack getOutput() {
            return Infuser.getOutput(this.getUtil().getFluid(Infuser.tankInput), this.infusing);
        }

        protected void onFinishTask() {
            FluidStack output = this.getOutput().copy();
            this.getUtil().fillTank(Infuser.tankOutput, output);
        }

        protected void onTickTask() {
        }

        public String getTooltip() {
            return "Infusing";
        }
    }

    public static class PackageInfuser extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
        public PackageInfuser() {
            super("infuser", ExtraTreeTexture.infuserTexture, true);
        }

        public void createMachine(Machine machine) {
            new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Infuser);
            new ComponentInventorySlots(machine);
            ComponentTankContainer tanks = new ComponentTankContainer(machine);
            tanks.addTank(Infuser.tankInput, "input", 5000);
            tanks.getTankSlot(Infuser.tankInput).setValidator(new Infuser.TankValidatorInfuserInput());
            tanks.getTankSlot(Infuser.tankInput).setOutputSides(MachineSide.TopAndBottom);
            tanks.addTank(Infuser.tankOutput, "output", 5000);
            tanks.getTankSlot(Infuser.tankOutput).setValidator(new Infuser.TankValidatorInfuserOutput());
            tanks.getTankSlot(Infuser.tankOutput).setReadOnly();
            tanks.getTankSlot(Infuser.tankOutput).setOutputSides(MachineSide.Sides);
            new ComponentPowerReceptor(machine);
            new Infuser.ComponentInfuserLogic(machine);
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }
    }

    public static class TankValidatorInfuserInput extends TankValidator {
        public TankValidatorInfuserInput() {
            super();
        }

        public boolean isValid(FluidStack itemStack) {
            return Infuser.isValidInputLiquid(itemStack);
        }

        public String getTooltip() {
            return "Infusable Liquids";
        }
    }

    public static class TankValidatorInfuserOutput extends TankValidator {
        public TankValidatorInfuserOutput() {
            super();
        }

        public boolean isValid(FluidStack itemStack) {
            return Infuser.isValidOutputLiquid(itemStack);
        }

        public String getTooltip() {
            return "Infused Liquids";
        }
    }
}
