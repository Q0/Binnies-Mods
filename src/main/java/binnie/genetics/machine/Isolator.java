package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.genetics.Gene;
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
import binnie.genetics.item.GeneticsItems;
import binnie.genetics.item.ItemSequence;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.genetics.*;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class Isolator {
    public static final int slotEnzyme = 0;
    public static final int slotSequencerVial = 1;
    public static final int[] slotReserve = new int[]{2, 3, 4};
    public static final int slotTarget = 5;
    public static final int slotResult = 6;
    public static final int[] slotFinished = new int[]{7, 8, 9, 10, 11, 12};
    public static final int tankEthanol = 0;

    public Isolator() {
        super();
    }

    public static class ComponentIsolaterFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
        public ComponentIsolaterFX(IMachine machine) {
            super(machine);
        }

        @SideOnly(Side.CLIENT)
        public void onRandomDisplayTick(final World world, int x, int y, int z, Random rand) {
            if (this.getUtil().getProcess().isInProgress()) {
                BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, (double) x + 0.4D + 0.2D * rand.nextDouble(), (double) z + 0.4D + rand.nextDouble() * 0.2D, 0.0D, x4, x5, x6) {
                    double axisX = 0.0D;
                    double axisZ = 0.0D;
                    double angle = 0.0D;

                    {
                        this.motionX = 0.0D;
                        this.motionZ = 0.0D;
                        this.motionY = -0.012D;
                        this.particleMaxAge = 100;
                        this.particleGravity = 0.0F;
                        this.noClip = true;
                        this.setRBGColorF(0.8F, 0.4F, 0.0F);
                    }

                    public void onUpdate() {
                        super.onUpdate();
                        this.angle += 0.06D;
                        this.setAlphaF((float) Math.sin(3.14D * (double) this.particleAge / (double) this.particleMaxAge));
                    }

                    public int getFXLayer() {
                        return 0;
                    }
                });
            }
        }

        @SideOnly(Side.CLIENT)
        public void onDisplayTick(final World world, int x, int y, int z, Random rand) {
            int tick = (int) (world.getTotalWorldTime() % 6L);
            if ((tick == 0 || tick == 5) && this.getUtil().getProcess().isInProgress()) {
                BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, (double) x + 0.5D, (double) z + 0.5D, 0.0D, x4, x5, x6) {
                    double axisX = 0.0D;
                    double axisZ = 0.0D;
                    double angle = 0.0D;

                    {
                        this.motionX = 0.0D;
                        this.motionZ = 0.0D;
                        this.motionY = 0.012D;
                        this.particleMaxAge = 100;
                        this.axisX = this.posX;
                        this.axisZ = this.posZ;
                        this.particleGravity = 0.0F;
                        this.angle = 0.7D + (double) ((int) (this.worldObj.getTotalWorldTime() % 2L)) * 3.1415D;
                        this.noClip = true;
                        this.setRBGColorF(0.8F, 0.0F, 1.0F);
                    }

                    public void onUpdate() {
                        super.onUpdate();
                        this.angle += 0.06D;
                        this.setPosition(this.axisX + 0.26D * Math.sin(this.angle), this.posY, this.axisZ + 0.26D * Math.cos(this.angle));
                        this.setAlphaF((float) Math.cos(1.57D * (double) this.particleAge / (double) this.particleMaxAge));
                    }

                    public int getFXLayer() {
                        return 0;
                    }
                });
            }

        }
    }

    public static class ComponentIsolatorLogic extends ComponentProcessSetCost implements IProcess {
        float enzymePerProcess = 0.5F;
        float ethanolPerProcess = 10.0F;

        public ComponentIsolatorLogic(Machine machine) {
            super(machine, 192000, 4800);
        }

        public ErrorState canWork() {
            return (ErrorState) (this.getUtil().isSlotEmpty(5) ? new ErrorState.NoItem("No individual to isolate", 5) : (!this.getUtil().isSlotEmpty(6) ? new ErrorState.NoSpace("No room for DNA sequences", Isolator.slotFinished) : (this.getUtil().isSlotEmpty(1) ? new ErrorState.NoItem("No empty sequencer vials", 1) : super.canWork())));
        }

        public ErrorState canProgress() {
            return (ErrorState) (!this.getUtil().liquidInTank(0, (int) this.ethanolPerProcess) ? new ErrorState.InsufficientLiquid("Insufficient ethanol", 0) : (this.getUtil().getSlotCharge(0) == 0.0F ? new ErrorState.NoItem("No enzyme", 0) : super.canProgress()));
        }

        protected void onFinishTask() {
            super.onFinishTask();
            Random rand = this.getMachine().getWorld().rand;
            ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(this.getUtil().getStack(5));
            if (root != null) {
                IIndividual individual = root.getMember(this.getUtil().getStack(5));
                if (individual != null) {
                    IAllele allele = null;
                    IChromosomeType chromosome = null;
                    Gene gene = null;
                    if (this.getMachine().getWorld().rand.nextFloat() < 2.0F) {
                        while (gene == null) {
                            try {
                                chromosome = root.getKaryotype()[rand.nextInt(root.getKaryotype().length)];
                                allele = rand.nextBoolean() ? individual.getGenome().getActiveAllele(chromosome) : individual.getGenome().getInactiveAllele(chromosome);
                                gene = Gene.create(allele, chromosome, root);
                            } catch (Exception var8) {
                                ;
                            }
                        }
                    }

                    ItemStack serum = ItemSequence.create(gene);
                    this.getUtil().setStack(6, serum);
                    this.getUtil().decreaseStack(1, 1);
                    if (rand.nextFloat() < 0.05F) {
                        this.getUtil().decreaseStack(5, 1);
                    }

                    this.getUtil().drainTank(0, (int) this.ethanolPerProcess);
                }
            }
        }

        protected void onTickTask() {
            ((IChargedSlots) this.getMachine().getInterface(IChargedSlots.class)).alterCharge(0, -this.enzymePerProcess * this.getProgressPerTick() / 100.0F);
        }
    }

    public static class PackageIsolator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
        public PackageIsolator() {
            super("isolator", GeneticsTexture.Isolator, 16740111, true);
        }

        public void createMachine(Machine machine) {
            new ComponentGeneticGUI(machine, GeneticsGUI.Isolator);
            ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(0, "enzyme");
            inventory.getSlot(0).setValidator(new SlotValidator.Item(GeneticsItems.Enzyme.get(1), ModuleMachine.IconEnzyme));
            inventory.getSlot(0).forbidExtraction();
            inventory.addSlot(1, "sequencervial");
            inventory.getSlot(1).setValidator(new SlotValidator.Item(GeneticsItems.EmptySequencer.get(1), ModuleMachine.IconSequencer));
            inventory.getSlot(1).forbidExtraction();
            inventory.addSlotArray(Isolator.slotReserve, "input");

            for (InventorySlot slot : inventory.getSlots(Isolator.slotReserve)) {
                slot.setValidator(new SlotValidator.Individual());
                slot.forbidExtraction();
            }

            inventory.addSlot(5, "process");
            inventory.getSlot(5).setValidator(new SlotValidator.Individual());
            inventory.getSlot(5).setReadOnly();
            inventory.getSlot(5).forbidInteraction();
            inventory.addSlot(6, "output");
            inventory.getSlot(6).setReadOnly();
            inventory.getSlot(6).forbidInteraction();
            inventory.addSlotArray(Isolator.slotFinished, "output");

            for (InventorySlot slot : inventory.getSlots(Isolator.slotFinished)) {
                slot.setReadOnly();
                slot.forbidInsertion();
            }

            ComponentTankContainer tanks = new ComponentTankContainer(machine);
            tanks.addTank(0, "input", 1000);
            tanks.getTankSlot(0).setValidator(new TankValidator() {
                @Override
                public boolean isValid(Object var1) {
                    return false;
                }

                public String getTooltip() {
                    return "Ethanol";
                }

                public boolean isValid(FluidStack stack) {
                    return stack.getFluid().getName() == "bioethanol";
                }
            });
            ComponentChargedSlots chargedSlots = new ComponentChargedSlots(machine);
            chargedSlots.addCharge(0);
            ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
            transfer.addRestock(Isolator.slotReserve, 5, 1);
            transfer.addStorage(6, Isolator.slotFinished);
            new ComponentPowerReceptor(machine, 20000);
            new Isolator.ComponentIsolatorLogic(machine);
            new Isolator.ComponentIsolaterFX(machine);
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }
    }
}
