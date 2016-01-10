package binnie.extratrees.machines;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.ComponentInventorySlots;
import binnie.core.machines.inventory.ComponentTankContainer;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.TankValidator;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.extratrees.core.ExtraTreeTexture;
import binnie.extratrees.core.ExtraTreesGUID;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Distillery {
    public static int tankInput = 0;
    public static int tankOutput = 1;
    static List recipes = new ArrayList();

    public Distillery() {
        super();
    }

    public static FluidStack getOutput(FluidStack fluid, int level) {
        return fluid == null ? null : (FluidStack) ((Map) recipes.get(level)).get(fluid.getFluid());
    }

    public static boolean isValidInputLiquid(FluidStack fluid) {
        for (int i = 0; i < 3; ++i) {
            if (((Map) recipes.get(i)).containsKey(fluid.getFluid())) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidOutputLiquid(FluidStack fluid) {
        for (int i = 0; i < 3; ++i) {
            for (Entry<Fluid, FluidStack> entry : ((Map) recipes.get(i)).entrySet()) {
                if (((FluidStack) entry.getValue()).isFluidEqual(fluid)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void addRecipe(FluidStack input, FluidStack output, int level) {
        ((Map) recipes.get(level)).put(input.getFluid(), output);
    }

    static {
        recipes.add(new HashMap());
        recipes.add(new HashMap());
        recipes.add(new HashMap());
    }

    public static class ComponentDistilleryLogic extends ComponentProcessSetCost implements IProcess, INetwork.SendGuiNBT, INetwork.RecieveGuiNBT {
        public FluidStack currentFluid = null;
        public int level = 0;
        int guiLevel;

        public ComponentDistilleryLogic(Machine machine) {
            super(machine, 16000, 800);
            this.guiLevel = machine.getUniqueProgressBarID();
        }

        public float getEnergyPerTick() {
            return 2.0F;
        }

        public int getProcessLength() {
            return 2000 + 800 * this.level;
        }

        public void readFromNBT(NBTTagCompound nbt) {
            super.readFromNBT(nbt);
            this.level = nbt.getByte("dlevel");
        }

        public void writeToNBT(NBTTagCompound nbt) {
            super.writeToNBT(nbt);
            nbt.setByte("dlevel", (byte) this.level);
        }

        public ErrorState canWork() {
            return (ErrorState) (this.getUtil().isTankEmpty(Distillery.tankInput) && this.currentFluid == null ? new ErrorState.InsufficientLiquid("No Input Liquid", Distillery.tankInput) : super.canWork());
        }

        public ErrorState canProgress() {
            return (ErrorState) (this.currentFluid == null ? new ErrorState("Distillery Empty", "No liquid in Distillery") : (!this.getUtil().isTankEmpty(Distillery.tankOutput) && this.getOutput() != null && !this.getOutput().isFluidEqual(this.getUtil().getFluid(Distillery.tankOutput)) ? new ErrorState.Tank("No Room", "No room for liquid", new int[]{Distillery.tankOutput}) : (this.getUtil().getFluid(Distillery.tankOutput) != null && !this.getUtil().getFluid(Distillery.tankOutput).isFluidEqual(Distillery.getOutput(this.getUtil().getFluid(Distillery.tankInput), this.level)) ? new ErrorState.TankSpace("Different fluid in tank", Distillery.tankOutput) : super.canProgress())));
        }

        private FluidStack getOutput() {
            return Distillery.getOutput(this.getUtil().getFluid(Distillery.tankInput), this.level);
        }

        protected void onFinishTask() {
            FluidStack output = Distillery.getOutput(this.currentFluid, this.level).copy();
            output.amount = 1000;
            this.getUtil().fillTank(Distillery.tankOutput, output);
        }

        protected void onTickTask() {
        }

        public void recieveGuiNBT(Side side, EntityPlayer player, String name, NBTTagCompound nbt) {
            if (name.equals("still-level")) {
                this.level = nbt.getByte("i");
            }

            if (name.equals("still-recipe")) {
                if (nbt.getBoolean("null")) {
                    this.currentFluid = null;
                } else {
                    this.currentFluid = FluidStack.loadFluidStackFromNBT(nbt);
                }
            }

        }

        public void sendGuiNBT(Map data) {
            NBTTagCompound nbt = new NBTTagCompound();
            if (this.currentFluid == null) {
                nbt.setBoolean("null", true);
            } else {
                this.currentFluid.writeToNBT(nbt);
            }

            data.put("still-recipe", nbt);
            new NBTTagCompound();
            nbt.setByte("i", (byte) this.level);
            data.put("still-level", nbt);
        }

        public void onUpdate() {
            super.onUpdate();
            if (this.canWork() == null && this.currentFluid == null && this.getUtil().getTank(Distillery.tankInput).getFluidAmount() >= 1000) {
                FluidStack stack = this.getUtil().drainTank(Distillery.tankInput, 1000);
                this.currentFluid = stack;
            }

        }

        public String getTooltip() {
            return this.currentFluid == null ? "Empty" : "Creating " + Distillery.getOutput(this.currentFluid, this.level).getFluid().getLocalizedName();
        }
    }

    public static class PackageDistillery extends ExtraTreeMachine.PackageExtraTreeMachine implements IMachineInformation {
        public PackageDistillery() {
            super("distillery", ExtraTreeTexture.distilleryTexture, true);
        }

        public void createMachine(Machine machine) {
            new ExtraTreeMachine.ComponentExtraTreeGUI(machine, ExtraTreesGUID.Distillery);
            new ComponentInventorySlots(machine);
            ComponentTankContainer tanks = new ComponentTankContainer(machine);
            tanks.addTank(Distillery.tankInput, "input", 5000);
            tanks.getTankSlot(Distillery.tankInput).setValidator(new Distillery.TankValidatorDistilleryInput());
            tanks.getTankSlot(Distillery.tankInput).setOutputSides(MachineSide.TopAndBottom);
            tanks.addTank(Distillery.tankOutput, "output", 5000);
            tanks.getTankSlot(Distillery.tankOutput).setValidator(new Distillery.TankValidatorDistilleryOutput());
            tanks.getTankSlot(Distillery.tankOutput).setReadOnly();
            tanks.getTankSlot(Distillery.tankOutput).setOutputSides(MachineSide.Sides);
            new ComponentPowerReceptor(machine);
            new Distillery.ComponentDistilleryLogic(machine);
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }
    }

    public static class TankValidatorDistilleryInput extends TankValidator {
        public TankValidatorDistilleryInput() {
            super();
        }

        public boolean isValid(FluidStack itemStack) {
            return Distillery.isValidInputLiquid(itemStack);
        }

        public String getTooltip() {
            return "Distillable Liquids";
        }
    }

    public static class TankValidatorDistilleryOutput extends TankValidator {
        public TankValidatorDistilleryOutput() {
            super();
        }

        public boolean isValid(FluidStack itemStack) {
            return Distillery.isValidOutputLiquid(itemStack);
        }

        public String getTooltip() {
            return "Distilled Liquids";
        }
    }
}
