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
    public static final int[] slotReserve = new int[]{1, 2, 3, 4, 5, 6};
    public static final int tankDNA = 0;
    public static final int tankEthanol = 1;
    public static final int slotEnzyme = 7;

    public Genepool() {
        super();
    }

    public static class ComponentGenepoolFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
        public ComponentGenepoolFX(IMachine machine) {
            super(machine);
        }

        @SideOnly(Side.CLIENT)
        public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
        }

        @SideOnly(Side.CLIENT)
        public void onDisplayTick(final World world, int x, int y, int z, Random rand) {
            if (rand.nextFloat() < 1.0F && this.getUtil().getProcess().isInProgress()) {
                BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, (double) x + 0.3D + rand.nextDouble() * 0.4D, (double) z + 0.3D + rand.nextDouble() * 0.4D, 0.0D, x4, x5, x6) {
                    double axisX = 0.0D;
                    double axisZ = 0.0D;
                    double angle = 0.0D;

                    {
                        this.motionX = 0.0D;
                        this.motionZ = 0.0D;
                        this.motionY = (double) this.rand.nextFloat() * 0.01D;
                        this.particleMaxAge = 25;
                        this.axisX = this.posX;
                        this.axisZ = this.posZ;
                        this.particleGravity = 0.0F;
                        this.angle = this.rand.nextDouble() * 2.0D * 3.1415D;
                        this.noClip = true;
                        this.setRBGColorF(0.4F + 0.6F * this.rand.nextFloat(), 0.6F * this.rand.nextFloat(), 0.6F + 0.4F * this.rand.nextFloat());
                    }

                    public void onUpdate() {
                        super.onUpdate();
                        this.setAlphaF((float) Math.cos(1.57D * (double) this.particleAge / (double) this.particleMaxAge));
                    }

                    public int getFXLayer() {
                        return 0;
                    }
                });
            }

        }
    }

    public static class ComponentGenepoolLogic extends ComponentProcessSetCost implements IProcess {
        float enzymePerProcess = 0.25F;
        float ethanolPerProcess = 50.0F;
        private float ethanolDrain = 0.0F;

        public ComponentGenepoolLogic(Machine machine) {
            super(machine, 8000, 400);
        }

        public ErrorState canWork() {
            return (ErrorState) (this.getUtil().isSlotEmpty(0) ? new ErrorState.NoItem("No Individual", 0) : super.canWork());
        }

        public ErrorState canProgress() {
            return (ErrorState) (!this.getUtil().spaceInTank(0, this.getDNAAmount(this.getUtil().getStack(0))) ? new ErrorState.NoSpace("Not enough room in Tank", new int[]{0}) : (!this.getUtil().liquidInTank(1, 1) ? new ErrorState.InsufficientLiquid("Not enough Ethanol", 1) : (this.getUtil().getSlotCharge(7) == 0.0F ? new ErrorState.NoItem("Insufficient Enzyme", 7) : super.canProgress())));
        }

        protected void onFinishTask() {
            super.onFinishTask();
            int amount = this.getDNAAmount(this.getUtil().getStack(0));
            this.getUtil().fillTank(0, GeneticLiquid.RawDNA.get(amount));
            this.getUtil().deleteStack(0);
        }

        private int getDNAAmount(ItemStack stack) {
            ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
            return root == null ? 10 : (root == Binnie.Genetics.getBeeRoot() ? (Binnie.Genetics.getBeeRoot().isDrone(stack) ? 10 : (Binnie.Genetics.getBeeRoot().isMated(stack) ? 50 : 30)) : 10);
        }

        protected void onTickTask() {
            this.ethanolDrain += (float) this.getDNAAmount(this.getUtil().getStack(0)) * 1.2F * this.getProgressPerTick() / 100.0F;
            if (this.ethanolDrain >= 1.0F) {
                this.getUtil().drainTank(1, 1);
                --this.ethanolDrain;
            }

            ((IChargedSlots) this.getMachine().getInterface(IChargedSlots.class)).alterCharge(7, -this.enzymePerProcess * this.getProgressPerTick() / 100.0F);
        }
    }

    public static class PackageGenepool extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
        public PackageGenepool() {
            super("genepool", GeneticsTexture.Genepool, 12661942, true);
        }

        public void createMachine(Machine machine) {
            new ComponentGeneticGUI(machine, GeneticsGUI.Genepool);
            ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(7, "enzyme");
            inventory.getSlot(7).setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.IconEnzyme));
            inventory.getSlot(7).forbidExtraction();
            inventory.addSlot(0, "process");
            inventory.getSlot(0).setValidator(new SlotValidator.Individual());
            inventory.getSlot(0).setReadOnly();
            inventory.getSlot(0).forbidExtraction();
            inventory.addSlotArray(Genepool.slotReserve, "input");

            for (InventorySlot slot : inventory.getSlots(Genepool.slotReserve)) {
                slot.setValidator(new SlotValidator.Individual());
                slot.forbidExtraction();
            }

            ComponentTankContainer tanks = new ComponentTankContainer(machine);
            tanks.addTank(0, "output", 2000);
            tanks.getTankSlot(0).setReadOnly();
            tanks.addTank(1, "input", 1000);
            tanks.getTankSlot(1).forbidExtraction();
            tanks.getTankSlot(1).setValidator(new TankValidator() {
                public String getTooltip() {
                    return "Ethanol";
                }

                public boolean isValid(FluidStack stack) {
                    return stack.getFluid().getName() == "bioethanol";
                }
            });
            ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
            transfer.addRestock(Genepool.slotReserve, 0, 1);
            new ComponentPowerReceptor(machine, 1600);
            new Genepool.ComponentGenepoolLogic(machine);
            ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
            chargedSlots.addCharge(7);
            new Genepool.ComponentGenepoolFX(machine);
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }
    }
}
