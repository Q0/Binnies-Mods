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
import binnie.core.machines.power.ComponentProcess;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.core.resource.BinnieIcon;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.GeneTracker;
import binnie.genetics.genetics.SequencerItem;
import binnie.genetics.item.GeneticsItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class Sequencer {
    public static final int slotDye = 0;
    public static final int[] slotReserve = new int[]{1, 2, 3, 4};
    public static final int slotTarget = 5;
    public static final int slotDone = 6;
    public static BinnieIcon fxSeqA;
    public static BinnieIcon fxSeqG;
    public static BinnieIcon fxSeqT;
    public static BinnieIcon fxSeqC;

    public Sequencer() {
        super();
    }

    public static class ComponentSequencerFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick {
        public ComponentSequencerFX(IMachine machine) {
            super(machine);
        }

        @SideOnly(Side.CLIENT)
        public void onRandomDisplayTick(final World world, int x, int y, int z, Random rand) {
            if (this.getUtil().getProcess().isInProgress()) {
                BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, (double) x + 0.5D, (double) z + 0.5D, 0.0D, x4, x5, x6) {
                    double axisX = 0.0D;
                    double axisZ = 0.0D;
                    double angle = 0.0D;

                    {
                        this.motionX = 0.0D;
                        this.motionZ = 0.0D;
                        this.motionY = 0.0D;
                        this.particleMaxAge = 200;
                        this.axisX = this.posX;
                        this.axisZ = this.posZ;
                        this.particleGravity = 0.0F;
                        this.angle = this.rand.nextDouble() * 2.0D * 3.1415D;
                        this.noClip = true;
                        this.setRBGColorF(0.6F + this.rand.nextFloat() * 0.2F, 1.0F, 0.8F * this.rand.nextFloat() * 0.2F);
                    }

                    public void onUpdate() {
                        super.onUpdate();
                        this.angle += 0.03D;
                        this.setPosition(this.axisX + 0.4D * Math.sin(this.angle), this.posY, this.axisZ + 0.4D * Math.cos(this.angle));
                        this.motionY = 0.0D;
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
            int ticks = (int) (world.getTotalWorldTime() % 16L);
            if (ticks == 0 && this.getUtil().getProcess().isInProgress()) {
                BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, (double) x + 0.5D, (double) z + 0.5D, 0.0D, x4, x5, x6) {
                    double axisX = 0.0D;
                    double axisZ = 0.0D;
                    double angle = 0.0D;

                    {
                        this.motionX = 0.0D;
                        this.motionZ = 0.0D;
                        this.motionY = 0.012D;
                        this.particleMaxAge = 50;
                        this.particleGravity = 0.0F;
                        this.noClip = true;
                        this.particleScale = 2.0F;
                        this.setParticleIcon((new BinnieIcon[]{Sequencer.fxSeqA, Sequencer.fxSeqG, Sequencer.fxSeqC, Sequencer.fxSeqT})[this.rand.nextInt(4)].getIcon());
                    }

                    public void onUpdate() {
                        super.onUpdate();
                        this.motionY = 0.012D;
                        if (this.particleAge > 40) {
                            this.setAlphaF((float) (50 - this.particleAge) / 10.0F);
                        }

                    }

                    public int getFXLayer() {
                        return 1;
                    }
                });
            }

        }
    }

    public static class ComponentSequencerLogic extends ComponentProcess implements IProcess {
        public ComponentSequencerLogic(Machine machine) {
            super(machine);
        }

        public float getSequenceStrength() {
            ItemStack stack = this.getUtil().getStack(5);
            if (stack == null) {
                return 1.0F;
            } else {
                float mult = 1.0F - (float) (stack.getItemDamage() % 6) / 5.0F;
                return 1.0F - mult * mult * 0.75F;
            }
        }

        public int getProcessLength() {
            return (int) (19200.0F * this.getSequenceStrength());
        }

        public int getProcessEnergy() {
            return this.getProcessLength() * 20;
        }

        public ErrorState canWork() {
            return (ErrorState) (this.getUtil().isSlotEmpty(5) ? new ErrorState.NoItem("No DNA sequence", 5) : super.canWork());
        }

        public ErrorState canProgress() {
            return (ErrorState) (this.getMachine().getOwner() == null ? new ErrorState("No Owner", "Replace this block to claim this machine") : (this.getUtil().getSlotCharge(0) == 0.0F ? new ErrorState.NoItem("Insufficient Dye", 0) : (this.getUtil().getStack(6) != null && this.getUtil().getStack(6).stackSize >= 64 ? new ErrorState.NoSpace("No space for empty sequences", new int[]{6}) : super.canProgress())));
        }

        protected void onStartTask() {
            super.onStartTask();
            ItemStack item = this.getUtil().getStack(5);
            SequencerItem seqItem = new SequencerItem(item);
            int seq = seqItem.sequenced;
            if (seq != 0) {
                this.setProgress((float) seq);
            }

        }

        protected void onFinishTask() {
            super.onFinishTask();
            this.updateSequence();
            SequencerItem seqItem = new SequencerItem(this.getUtil().getStack(5));
            GeneTracker.getTracker(this.getMachine().getWorld(), this.getMachine().getOwner()).registerGene(seqItem.getGene());
            this.getUtil().decreaseStack(5, 1);
            if (this.getUtil().getStack(6) == null) {
                this.getUtil().setStack(6, GeneticsItems.EmptySequencer.get(1));
            } else {
                this.getUtil().decreaseStack(6, -1);
            }

        }

        protected void onTickTask() {
            this.updateSequence();
            this.getUtil().useCharge(0, 0.4F * this.getProgressPerTick() / 100.0F);
        }

        private void updateSequence() {
            int prog = (int) this.getProgress();
            ItemStack item = this.getUtil().getStack(5);
            SequencerItem seqItem = new SequencerItem(item);
            int seq = seqItem.sequenced;
            if (prog != seq) {
                seqItem.sequenced = prog;
                seqItem.writeToItem(item);
            }

        }
    }

    public static class PackageSequencer extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
        public PackageSequencer() {
            super("sequencer", GeneticsTexture.Sequencer, 12058418, true);
            Sequencer.fxSeqA = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.a");
            Sequencer.fxSeqG = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.g");
            Sequencer.fxSeqT = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.t");
            Sequencer.fxSeqC = Binnie.Resource.getBlockIcon(Genetics.instance, "fx/sequencer.c");
        }

        public void createMachine(Machine machine) {
            new ComponentGeneticGUI(machine, GeneticsGUI.Sequencer);
            ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(0, "dye");
            inventory.getSlot(0).setValidator(new SlotValidator.Item(GeneticsItems.FluorescentDye.get(1), ModuleMachine.IconDye));
            inventory.getSlot(0).forbidExtraction();
            inventory.addSlotArray(Sequencer.slotReserve, "input");

            for (InventorySlot slot : inventory.getSlots(Sequencer.slotReserve)) {
                slot.setValidator(new Sequencer.SlotValidatorUnsequenced());
                slot.forbidExtraction();
            }

            inventory.addSlot(5, "process");
            inventory.getSlot(5).setValidator(new Sequencer.SlotValidatorUnsequenced());
            inventory.getSlot(5).setReadOnly();
            inventory.getSlot(5).forbidInteraction();
            inventory.addSlot(6, "output");
            inventory.getSlot(6).setReadOnly();
            ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
            transfer.addRestock(Sequencer.slotReserve, 5, 1);
            (new ComponentChargedSlots(machine)).addCharge(0);
            new ComponentPowerReceptor(machine, 10000);
            new Sequencer.ComponentSequencerLogic(machine);
            new Sequencer.ComponentSequencerFX(machine);
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }
    }

    public static class SlotValidatorUnsequenced extends SlotValidator {
        public SlotValidatorUnsequenced() {
            super(ModuleMachine.IconSequencer);
        }

        public boolean isValid(ItemStack itemStack) {
            if (itemStack.getItem() == Genetics.itemSequencer) {
                SequencerItem seq = new SequencerItem(itemStack);
                return seq.sequenced < 100;
            } else {
                return false;
            }
        }

        public String getTooltip() {
            return "Unsequenced DNA";
        }
    }
}
