package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.inventory.*;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.item.GeneticLiquid;
import binnie.genetics.item.GeneticsItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class Genepool {
    public static final int slotBee = 0;
    public static final int[] slotReserve;
    public static final int tankDNA = 0;
    public static final int tankEthanol = 1;
    public static final int slotEnzyme = 7;

    static {
        slotReserve = new int[]{1, 2, 3, 4, 5, 6};
    }

    public static class PackageGenepool extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
        public PackageGenepool() {
            super("genepool", GeneticsTexture.Genepool, 12661942, true);
        }

        @Override
        public void createMachine(final Machine machine) {
            new ComponentGeneticGUI(machine, GeneticsGUI.Genepool);
            final ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(7, "enzyme");
            inventory.getSlot(7).setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.IconEnzyme));
            inventory.getSlot(7).forbidExtraction();
            inventory.addSlot(0, "process");
            inventory.getSlot(0).setValidator(new SlotValidator.Individual());
            inventory.getSlot(0).setReadOnly();
            inventory.getSlot(0).forbidExtraction();
            inventory.addSlotArray(Genepool.slotReserve, "input");
            for (final InventorySlot slot : inventory.getSlots(Genepool.slotReserve)) {
                slot.setValidator(new SlotValidator.Individual());
                slot.forbidExtraction();
            }
            final ComponentTankContainer tanks = new ComponentTankContainer(machine);
            tanks.addTank(0, "output", 2000);
            tanks.getTankSlot(0).setReadOnly();
            tanks.addTank(1, "input", 1000);
            tanks.getTankSlot(1).forbidExtraction();
            tanks.getTankSlot(1).setValidator(new TankValidator() {
                @Override
                public String getTooltip() {
                    return "Ethanol";
                }

                @Override
                public boolean isValid(final FluidStack stack) {
                    return stack.getFluid().getName() == "bioethanol";
                }
            });
            final ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
            transfer.addRestock(Genepool.slotReserve, 0, 1);
            new ComponentPowerReceptor(machine, 1600);
            new ComponentGenepoolLogic(machine);
            final ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
            chargedSlots.addCharge(7);
            new ComponentGenepoolFX(machine);
        }

        @Override
        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        @Override
        public void register() {
        }
    }

    public static class ComponentGenepoolLogic extends ComponentProcessSetCost implements IProcess {
        float enzymePerProcess;
        float ethanolPerProcess;
        private float ethanolDrain;

        public ComponentGenepoolLogic(final Machine machine) {
            super(machine, 8000, 400);
            this.enzymePerProcess = 0.25f;
            this.ethanolPerProcess = 50.0f;
            this.ethanolDrain = 0.0f;
        }

        @Override
        public ErrorState canWork() {
            if (this.getUtil().isSlotEmpty(0)) {
                return new ErrorState.NoItem("No Individual", 0);
            }
            return super.canWork();
        }

        @Override
        public ErrorState canProgress() {
            if (!this.getUtil().spaceInTank(0, this.getDNAAmount(this.getUtil().getStack(0)))) {
                return new ErrorState.NoSpace("Not enough room in Tank", new int[]{0});
            }
            if (!this.getUtil().liquidInTank(1, 1)) {
                return new ErrorState.InsufficientLiquid("Not enough Ethanol", 1);
            }
            if (this.getUtil().getSlotCharge(7) == 0.0f) {
                return new ErrorState.NoItem("Insufficient Enzyme", 7);
            }
            return super.canProgress();
        }

        @Override
        protected void onFinishTask() {
            super.onFinishTask();
            final int amount = this.getDNAAmount(this.getUtil().getStack(0));
            this.getUtil().fillTank(0, GeneticLiquid.RawDNA.get(amount));
            this.getUtil().deleteStack(0);
        }

        private int getDNAAmount(final ItemStack stack) {
            final ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
            if (root == null) {
                return 10;
            }
            if (root != Binnie.Genetics.getBeeRoot()) {
                return 10;
            }
            if (Binnie.Genetics.getBeeRoot().isDrone(stack)) {
                return 10;
            }
            if (Binnie.Genetics.getBeeRoot().isMated(stack)) {
                return 50;
            }
            return 30;
        }

        @Override
        protected void onTickTask() {
            this.ethanolDrain += this.getDNAAmount(this.getUtil().getStack(0)) * 1.2f * this.getProgressPerTick() / 100.0f;
            if (this.ethanolDrain >= 1.0f) {
                this.getUtil().drainTank(1, 1);
                --this.ethanolDrain;
            }
            this.getMachine().getInterface(IChargedSlots.class).alterCharge(7, -this.enzymePerProcess * this.getProgressPerTick() / 100.0f);
        }
    }

    public static class ComponentGenepoolFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
        public ComponentGenepoolFX(final IMachine machine) {
            super(machine);
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void onRandomDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
        }

        @SideOnly(Side.CLIENT)
        @Override
        public void onDisplayTick(final World world, final int x, final int y, final int z, final Random rand) {
            if (rand.nextFloat() < 1.0f && this.getUtil().getProcess().isInProgress()) {
                BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect((EntityFX) new EntityFX(world, x + 0.3 + rand.nextDouble() * 0.4, (double) (y + 1), z + 0.3 + rand.nextDouble() * 0.4, 0.0, 0.0, 0.0) {
                    double axisX = this.posX;
                    double axisZ = this.posZ;
                    double angle = this.rand.nextDouble() * 2.0 * 3.1415;

                    {
                        this.axisX = 0.0;
                        this.axisZ = 0.0;
                        this.angle = 0.0;
                        this.motionX = 0.0;
                        this.motionZ = 0.0;
                        this.motionY = this.rand.nextFloat() * 0.01;
                        this.particleMaxAge = 25;
                        this.particleGravity = 0.0f;
                        this.noClip = true;
                        this.setRBGColorF(0.4f + 0.6f * this.rand.nextFloat(), 0.6f * this.rand.nextFloat(), 0.6f + 0.4f * this.rand.nextFloat());
                    }

                    public void onUpdate() {
                        super.onUpdate();
                        this.setAlphaF((float) Math.cos(1.57 * this.particleAge / this.particleMaxAge));
                    }

                    public int getFXLayer() {
                        return 0;
                    }
                });
            }
        }
    }
}
