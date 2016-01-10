package binnie.genetics.machine;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.component.IRender;
import binnie.core.machines.inventory.*;
import binnie.core.machines.network.INetwork;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessSetCost;
import binnie.core.machines.power.ErrorState;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.api.IGene;
import binnie.genetics.api.IItemSerum;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.genetics.Engineering;
import binnie.genetics.item.GeneticLiquid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBeeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Inoculator {
    public static final int slotSerumVial = 0;
    public static final int[] slotSerumReserve = new int[]{1, 2};
    public static final int[] slotSerumExpended = new int[]{3, 4};
    public static final int[] slotReserve = new int[]{5, 6, 7, 8};
    public static final int slotTarget = 9;
    public static final int[] slotFinished = new int[]{10, 11, 12, 13};
    public static final int tankVector = 0;

    public Inoculator() {
        super();
    }

    public static void setGene(IGene gene, ItemStack target, int chromoN) {
        int chromosome = gene.getChromosome().ordinal();
        int chromosomeID = chromosome;
        if (chromosome >= EnumBeeChromosome.HUMIDITY.ordinal() && gene.getSpeciesRoot() instanceof IBeeRoot) {
            chromosomeID = chromosome - 1;
        }

        Class<? extends IAllele> cls = gene.getChromosome().getAlleleClass();
        if (cls.isInstance(gene.getAllele())) {
            NBTTagCompound beeNBT = target.getTagCompound();
            NBTTagCompound genomeNBT = beeNBT.getCompoundTag("Genome");
            NBTTagList chromosomes = genomeNBT.getTagList("Chromosomes", 10);
            NBTTagCompound chromosomeNBT = chromosomes.getCompoundTagAt(chromosomeID);
            chromosomeNBT.setString("UID" + chromoN, gene.getAllele().getUID());
            target.setTagCompound(beeNBT);
        }
    }

    public static class ComponentInoculatorFX extends MachineComponent implements IRender.RandomDisplayTick, IRender.DisplayTick, IRender.Render, INetwork.TilePacketSync {
        private final EntityItem dummyEntityItem = new EntityItem((World) null);

        public ComponentInoculatorFX(IMachine machine) {
            super(machine);
        }

        @SideOnly(Side.CLIENT)
        public void onRandomDisplayTick(World world, int x, int y, int z, Random rand) {
        }

        @SideOnly(Side.CLIENT)
        public void onDisplayTick(final World world, int x, int y, int z, Random rand) {
            int tick = (int) (world.getTotalWorldTime() % 3L);
            if (tick == 0 && this.getUtil().getProcess().isInProgress()) {
                BinnieCore.proxy.getMinecraftInstance().effectRenderer.addEffect(new EntityFX(world, (double) x + 0.5D, (double) z + 0.5D, 0.0D, x4, x5, x6) {
                    double axisX = 0.0D;
                    double axisZ = 0.0D;
                    double angle = 0.0D;

                    {
                        this.motionX = 0.0D;
                        this.motionZ = 0.0D;
                        this.motionY = 0.007D + this.rand.nextDouble() * 0.002D;
                        this.particleMaxAge = 240;
                        this.axisX = this.posX;
                        this.axisZ = this.posZ;
                        this.particleGravity = 0.0F;
                        this.angle = (double) ((int) (this.worldObj.getTotalWorldTime() % 4L)) * 0.5D * 3.1415D;
                        this.noClip = true;
                        this.setRBGColorF(0.8F, 0.0F, 1.0F);
                    }

                    public void onUpdate() {
                        super.onUpdate();
                        double speed = 5.0E-4D;
                        if (this.particleAge > 60) {
                            speed += (double) ((float) (this.particleAge - 60) / 4000.0F);
                        }

                        this.angle += speed;
                        double dist = 0.27D;
                        this.setPosition(this.axisX + dist * Math.sin(this.angle), this.posY, this.axisZ + dist * Math.cos(this.angle));
                        this.setAlphaF((float) Math.cos(1.57D * (double) this.particleAge / (double) this.particleMaxAge));
                        if (this.particleAge > 40) {
                            this.setRBGColorF(this.particleRed + (1.0F - this.particleRed) / 10.0F, this.particleGreen + (0.0F - this.particleGreen) / 10.0F, this.particleBlue + (0.0F - this.particleBlue) / 10.0F);
                        }

                    }

                    public int getFXLayer() {
                        return 0;
                    }
                });
            }

        }

        @SideOnly(Side.CLIENT)
        public void renderInWorld(RenderItem customRenderItem, double x, double y, double z) {
            if (this.getUtil().getProcess().isInProgress()) {
                ItemStack stack = this.getUtil().getStack(9);
                this.dummyEntityItem.worldObj = this.getMachine().getWorld();
                this.dummyEntityItem.setEntityItemStack(stack);
                ++this.dummyEntityItem.age;
                this.dummyEntityItem.hoverStart = 0.0F;
                if (stack != null) {
                    EntityPlayer player = BinnieCore.proxy.getPlayer();
                    double dx = x + 0.5D - player.lastTickPosX;
                    double dz = z + 0.5D - player.lastTickPosZ;
                    double t = Math.atan2(dz, dx) * 180.0D / 3.1415D;
                    GL11.glPushMatrix();
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                    customRenderItem.doRender(this.dummyEntityItem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    GL11.glPopMatrix();
                }
            }
        }

        public void syncToNBT(NBTTagCompound nbt) {
            NBTTagCompound item = new NBTTagCompound();
            ItemStack stack = this.getUtil().getStack(9);
            if (stack != null) {
                stack.writeToNBT(item);
                nbt.setTag("item", item);
            }

        }

        public void syncFromNBT(NBTTagCompound nbt) {
            if (nbt.hasKey("item")) {
                this.getUtil().setStack(9, ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("item")));
            } else {
                this.getUtil().setStack(9, (ItemStack) null);
            }

        }

        public void onInventoryUpdate() {
            if (this.getUtil().isServer()) {
                this.getUtil().refreshBlock();
            }
        }
    }

    public static class ComponentInoculatorLogic extends ComponentProcessSetCost implements IProcess {
        private float bacteriaDrain = 0.0F;

        public int getProcessLength() {
            return super.getProcessLength() * this.getNumberOfGenes();
        }

        public int getProcessEnergy() {
            return super.getProcessEnergy() * this.getNumberOfGenes();
        }

        private int getNumberOfGenes() {
            ItemStack serum = this.getUtil().getStack(0);
            return serum == null ? 1 : Engineering.getGenes(serum).length;
        }

        public String getTooltip() {
            int n = this.getNumberOfGenes();
            return "Inoculating with " + n + " gene" + (n > 1 ? "s" : "");
        }

        public ComponentInoculatorLogic(Machine machine) {
            super(machine, 600000, 12000);
        }

        public ErrorState canWork() {
            if (this.getUtil().isSlotEmpty(9)) {
                return new ErrorState.NoItem("No Individual to Inoculate", 9);
            } else if (this.getUtil().isSlotEmpty(0)) {
                return new ErrorState.NoItem("No Serum", 0);
            } else {
                ErrorState state = this.isValidSerum();
                return state != null ? state : (this.getUtil().getStack(0) != null && Engineering.getCharges(this.getUtil().getStack(0)) == 0 ? new ErrorState("Empty Serum", "Serum is empty") : super.canWork());
            }
        }

        public ErrorState isValidSerum() {
            ItemStack serum = this.getUtil().getStack(0);
            ItemStack target = this.getUtil().getStack(9);
            IGene[] genes = Engineering.getGenes(serum);
            if (genes.length == 0) {
                return new ErrorState("Invalid Serum", "Serum does not hold any genes");
            } else if (!genes[0].getSpeciesRoot().isMember(target)) {
                return new ErrorState("Invalid Serum", "Mismatch of Serum Type and Target");
            } else {
                IIndividual individual = genes[0].getSpeciesRoot().getMember(target);
                boolean hasAll = true;

                for (IGene gene : genes) {
                    if (hasAll) {
                        IAllele a = individual.getGenome().getActiveAllele(gene.getChromosome());
                        IAllele b = individual.getGenome().getInactiveAllele(gene.getChromosome());
                        hasAll = hasAll && a.getUID().equals(gene.getAllele().getUID()) && b.getUID().equals(gene.getAllele().getUID());
                    }
                }

                if (hasAll) {
                    return new ErrorState("Defunct Serum", "Individual already possesses this allele");
                } else {
                    return null;
                }
            }
        }

        public ErrorState canProgress() {
            return super.canProgress();
        }

        protected void onFinishTask() {
            super.onFinishTask();
            ItemStack serum = this.getUtil().getStack(0);
            ItemStack target = this.getUtil().getStack(9);
            IIndividual ind = AlleleManager.alleleRegistry.getIndividual(target);
            if (!ind.isAnalyzed()) {
                ind.analyze();
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                ind.writeToNBT(nbttagcompound);
                target.setTagCompound(nbttagcompound);
            }

            IGene[] genes = ((IItemSerum) serum.getItem()).getGenes(serum);

            for (IGene gene : genes) {
                Inoculator.setGene(gene, target, 0);
                Inoculator.setGene(gene, target, 1);
            }

            this.getUtil().damageItem(0, 1);
        }

        protected void onTickTask() {
            this.bacteriaDrain += 15.0F * this.getProgressPerTick() / 100.0F;
            if (this.bacteriaDrain >= 1.0F) {
                this.getUtil().drainTank(0, 1);
                --this.bacteriaDrain;
            }

        }
    }

    public static class PackageInoculator extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
        public PackageInoculator() {
            super("inoculator", GeneticsTexture.Inoculator, 14819893, true);
        }

        public void createMachine(Machine machine) {
            new ComponentGeneticGUI(machine, GeneticsGUI.Inoculator);
            ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(0, "serum.active");
            inventory.getSlot(0).forbidInteraction();
            inventory.getSlot(0).setReadOnly();
            SlotValidator serumValid = new SlotValidator(ModuleMachine.IconSerum) {
                public boolean isValid(ItemStack itemStack) {
                    return itemStack.getItem() instanceof IItemSerum;
                }

                public String getTooltip() {
                    return "Serum Vials & Arrays";
                }
            };
            inventory.getSlot(0).setValidator(serumValid);
            inventory.addSlotArray(Inoculator.slotSerumReserve, "serum.input");

            for (InventorySlot slot : inventory.getSlots(Inoculator.slotSerumReserve)) {
                slot.setValidator(serumValid);
                slot.forbidExtraction();
            }

            inventory.addSlotArray(Inoculator.slotSerumExpended, "serum.output");

            for (InventorySlot slot : inventory.getSlots(Inoculator.slotSerumExpended)) {
                slot.setValidator(serumValid);
                slot.setReadOnly();
            }

            inventory.addSlotArray(Inoculator.slotReserve, "input");

            for (InventorySlot slot : inventory.getSlots(Inoculator.slotReserve)) {
                slot.forbidExtraction();
                slot.setValidator(new Inoculator.ValidatorIndividualInoculate());
            }

            inventory.addSlot(9, "process");
            inventory.getSlot(9).setValidator(new Inoculator.ValidatorIndividualInoculate());
            inventory.getSlot(9).setReadOnly();
            inventory.getSlot(9).forbidInteraction();
            inventory.addSlotArray(Inoculator.slotFinished, "output");

            for (InventorySlot slot : inventory.getSlots(Inoculator.slotFinished)) {
                slot.setReadOnly();
                slot.forbidInsertion();
                slot.setValidator(new Inoculator.ValidatorIndividualInoculate());
            }

            ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
            transfer.addRestock(Inoculator.slotReserve, 9, 1);
            transfer.addRestock(Inoculator.slotSerumReserve, 0);
            transfer.addStorage(0, Inoculator.slotSerumExpended, new ComponentInventoryTransfer.Condition() {
                public boolean fufilled(ItemStack stack) {
                    return Engineering.getCharges(stack) == 0;
                }
            });
            transfer.addStorage(9, Inoculator.slotFinished, new ComponentInventoryTransfer.Condition() {
                public boolean fufilled(ItemStack stack) {
                    return stack != null && this.transfer.getMachine().getMachineUtil().getStack(0) != null && ((Inoculator.ComponentInoculatorLogic) this.transfer.getMachine().getInterface(Inoculator.ComponentInoculatorLogic.class)).isValidSerum() != null;
                }
            });
            new ComponentPowerReceptor(machine, 15000);
            new Inoculator.ComponentInoculatorLogic(machine);
            new Inoculator.ComponentInoculatorFX(machine);
            (new ComponentTankContainer(machine)).addTank(0, "input", 1000).setValidator(new Validator() {
                public boolean isValid(FluidStack object) {
                    return GeneticLiquid.BacteriaVector.get(1).isFluidEqual(object);
                }

                public String getTooltip() {
                    return GeneticLiquid.BacteriaVector.getName();
                }
            });
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }
    }

    public static class ValidatorIndividualInoculate extends SlotValidator {
        public ValidatorIndividualInoculate() {
            super((ValidatorIcon) null);
        }

        public boolean isValid(ItemStack object) {
            ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(object);
            return root == null ? false : Binnie.Genetics.getSystem(root).isDNAManipulable(object);
        }

        public String getTooltip() {
            return "Inoculable Individual";
        }
    }
}
