package binnie.genetics.machine;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.gardening.Gardening;
import binnie.core.BinnieCore;
import binnie.core.Mods;
import binnie.core.genetics.Gene;
import binnie.core.genetics.Tolerance;
import binnie.core.machines.IMachine;
import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.inventory.*;
import binnie.core.machines.power.ComponentPowerReceptor;
import binnie.core.machines.power.ComponentProcessIndefinate;
import binnie.core.machines.power.ErrorState;
import binnie.craftgui.minecraft.IMachineInformation;
import binnie.genetics.core.GeneticsGUI;
import binnie.genetics.core.GeneticsTexture;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.genetics.*;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

public class Acclimatiser {
    public static final int[] slotReserve = new int[]{0, 1, 2, 3};
    public static final int slotTarget = 4;
    public static final int[] slotAcclimatiser = new int[]{5, 6, 7};
    public static final int[] slotDone = new int[]{8, 9, 10, 11};
    private static List toleranceSystems = new ArrayList();
    static Map temperatureItems = new HashMap();
    static Map humidityItems = new HashMap();

    public Acclimatiser() {
        super();
    }

    private static Acclimatiser.ToleranceSystem getToleranceSystem(ItemStack stack, ItemStack acclim) {
        ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
        if (root == null) {
            return null;
        } else {
            for (Acclimatiser.ToleranceSystem system : toleranceSystems) {
                if (root.getUID() == system.uid && system.type.hasEffect(acclim)) {
                    return system;
                }
            }

            return null;
        }
    }

    public static void addTolerance(String uid, IChromosomeType chromosome, Acclimatiser.ToleranceType type) {
        toleranceSystems.add(new Acclimatiser.ToleranceSystem(uid, chromosome, type));
    }

    public static float getTemperatureEffect(ItemStack item) {
        for (ItemStack stack : temperatureItems.keySet()) {
            if (stack.isItemEqual(item)) {
                return ((Float) temperatureItems.get(stack)).floatValue();
            }
        }

        return 0.0F;
    }

    public static float getHumidityEffect(ItemStack item) {
        for (ItemStack stack : humidityItems.keySet()) {
            if (stack.isItemEqual(item)) {
                return ((Float) humidityItems.get(stack)).floatValue();
            }
        }

        return 0.0F;
    }

    public static void addTemperatureItem(ItemStack itemstack, float amount) {
        if (itemstack != null) {
            temperatureItems.put(itemstack, Float.valueOf(amount));
        }
    }

    public static void addHumidityItem(ItemStack itemstack, float amount) {
        if (itemstack != null) {
            humidityItems.put(itemstack, Float.valueOf(amount));
        }
    }

    public static void setupRecipes() {
        if (BinnieCore.isApicultureActive()) {
            addTolerance(Binnie.Genetics.getBeeRoot().getUID(), EnumBeeChromosome.HUMIDITY_TOLERANCE, Acclimatiser.ToleranceType.Humidity);
            addTolerance(Binnie.Genetics.getBeeRoot().getUID(), EnumBeeChromosome.TEMPERATURE_TOLERANCE, Acclimatiser.ToleranceType.Temperature);
        }

        if (BinnieCore.isLepidopteryActive()) {
            addTolerance(Binnie.Genetics.getButterflyRoot().getUID(), EnumButterflyChromosome.HUMIDITY_TOLERANCE, Acclimatiser.ToleranceType.Humidity);
            addTolerance(Binnie.Genetics.getButterflyRoot().getUID(), EnumButterflyChromosome.TEMPERATURE_TOLERANCE, Acclimatiser.ToleranceType.Temperature);
        }

        if (BinnieCore.isBotanyActive()) {
            addTolerance(Binnie.Genetics.getFlowerRoot().getUID(), EnumFlowerChromosome.HUMIDITY_TOLERANCE, Acclimatiser.ToleranceType.Humidity);
            addTolerance(Binnie.Genetics.getFlowerRoot().getUID(), EnumFlowerChromosome.TEMPERATURE_TOLERANCE, Acclimatiser.ToleranceType.Temperature);
            addTolerance(Binnie.Genetics.getFlowerRoot().getUID(), EnumFlowerChromosome.PH_TOLERANCE, Acclimatiser.ToleranceType.PH);
        }

        addTemperatureItem(new ItemStack(Items.blaze_powder), 0.5F);
        addTemperatureItem(new ItemStack(Items.blaze_rod), 0.75F);
        addTemperatureItem(new ItemStack(Items.lava_bucket), 0.75F);
        addTemperatureItem(new ItemStack(Items.snowball), -0.15F);
        addTemperatureItem(new ItemStack(Blocks.ice), -0.75F);
        addHumidityItem(new ItemStack(Items.water_bucket), 0.75F);
        addHumidityItem(new ItemStack(Blocks.sand), -0.15F);
        addTemperatureItem(Mods.Forestry.stack("canLava"), 0.75F);
        addTemperatureItem(Mods.Forestry.stack("refractoryLava"), 0.75F);
        addHumidityItem(Mods.Forestry.stack("canWater"), 0.75F);
        addHumidityItem(Mods.Forestry.stack("refractoryWater"), 0.75F);
        addHumidityItem(Mods.Forestry.stack("waxCapsuleWater"), 0.75F);
    }

    public static boolean canAcclimatise(ItemStack stack, List acclimatisers) {
        if (stack != null && !acclimatisers.isEmpty()) {
            for (ItemStack acclim : acclimatisers) {
                if (canAcclimatise(stack, acclim)) {
                    return true;
                }
            }

            return false;
        } else {
            return true;
        }
    }

    public static boolean canAcclimatise(ItemStack stack, ItemStack acclim) {
        Acclimatiser.ToleranceSystem system = getToleranceSystem(stack, acclim);
        return system == null ? false : system.canAlter(stack, acclim);
    }

    public static ItemStack acclimatise(ItemStack stack, ItemStack acc) {
        Acclimatiser.ToleranceSystem system = getToleranceSystem(stack, acc);
        return system.alter(stack, acc);
    }

    public static Tolerance alterTolerance(Tolerance tol, float effect) {
        int[] is = tol.getBounds();
        int[] range = new int[2];
        if (effect < 0.0F) {
            range = new int[]{is[0] - 1, is[1]};
        } else {
            range = new int[]{is[0], is[1] + 1};
        }

        if (range[0] < -5) {
            range[0] = -5;
        }

        if (range[1] > 5) {
            range[1] = 5;
        }

        EnumTolerance[] up = new EnumTolerance[]{EnumTolerance.NONE, EnumTolerance.UP_1, EnumTolerance.UP_2, EnumTolerance.UP_3, EnumTolerance.UP_4, EnumTolerance.UP_5};
        EnumTolerance[] down = new EnumTolerance[]{EnumTolerance.NONE, EnumTolerance.DOWN_1, EnumTolerance.DOWN_2, EnumTolerance.DOWN_3, EnumTolerance.DOWN_4, EnumTolerance.DOWN_5};
        EnumTolerance[] both = new EnumTolerance[]{EnumTolerance.NONE, EnumTolerance.BOTH_1, EnumTolerance.BOTH_2, EnumTolerance.BOTH_3, EnumTolerance.BOTH_4, EnumTolerance.BOTH_5};
        if (range[0] == 0) {
            return Tolerance.get(up[range[1]]);
        } else if (range[1] == 0) {
            return Tolerance.get(down[-range[0]]);
        } else {
            int avg = (int) ((float) (-range[0] + range[1]) / 2.0F + 0.6F);
            return Tolerance.get(both[avg]);
        }
    }

    public static class ComponentAcclimatiserLogic extends ComponentProcessIndefinate {
        public ComponentAcclimatiserLogic(IMachine machine) {
            super(machine, 2.0F);
        }

        public ErrorState canWork() {
            return (ErrorState) (this.getUtil().getStack(4) == null ? new ErrorState.NoItem("No Individual to Acclimatise", 4) : (this.getUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser).isEmpty() ? new ErrorState.NoItem("No Acclimatising Items", Acclimatiser.slotAcclimatiser) : super.canWork()));
        }

        public ErrorState canProgress() {
            return (ErrorState) (!Acclimatiser.canAcclimatise(this.getUtil().getStack(4), this.getUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser)) ? new ErrorState.InvalidItem("Cannot Acclimatise this individual with these items", 4) : super.canProgress());
        }

        protected boolean inProgress() {
            return this.canWork() == null;
        }

        protected void onTickTask() {
            super.onTickTask();
            if (this.getUtil().getRandom().nextInt(100) == 0) {
                this.attemptAcclimatisation();
            }

        }

        protected void attemptAcclimatisation() {
            List<ItemStack> acclms = new ArrayList();

            for (ItemStack s : this.getUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser)) {
                if (Acclimatiser.canAcclimatise(this.getUtil().getStack(4), s)) {
                    acclms.add(s);
                }
            }

            ItemStack acc = (ItemStack) acclms.get(this.getUtil().getRandom().nextInt(acclms.size()));
            ItemStack acclimed = Acclimatiser.acclimatise(this.getUtil().getStack(4), acc);
            if (acclimed != null) {
                this.getUtil().setStack(4, acclimed);
                boolean removed = false;

                for (int i : Acclimatiser.slotAcclimatiser) {
                    if (!removed && this.getUtil().getStack(i) != null && this.getUtil().getStack(i).isItemEqual(acc)) {
                        this.getUtil().decreaseStack(i, 1);
                        removed = true;
                    }
                }
            }

        }
    }

    public static class PackageAcclimatiser extends GeneticMachine.PackageGeneticBase implements IMachineInformation {
        public PackageAcclimatiser() {
            super("acclimatiser", GeneticsTexture.Acclimatiser, 9857609, true);
        }

        public void createMachine(final Machine machine) {
            new ComponentGeneticGUI(machine, GeneticsGUI.Acclimatiser);
            ComponentInventorySlots inventory = new ComponentInventorySlots(machine);
            inventory.addSlot(4, "process");
            inventory.getSlot(4).setValidator(new SlotValidator.Individual());
            inventory.getSlot(4).setReadOnly();
            inventory.getSlot(4).forbidExtraction();

            for (InventorySlot slot : inventory.addSlotArray(Acclimatiser.slotReserve, "input")) {
                slot.forbidExtraction();
                slot.setValidator(new SlotValidator.Individual());
            }

            for (InventorySlot slot : inventory.addSlotArray(Acclimatiser.slotDone, "output")) {
                slot.setReadOnly();
                slot.setValidator(new SlotValidator.Individual());
            }

            for (InventorySlot slot : inventory.addSlotArray(Acclimatiser.slotAcclimatiser, "acclimatiser")) {
                slot.setValidator(new Acclimatiser.ValidatorAcclimatiserItem());
            }

            ComponentInventoryTransfer transfer = new ComponentInventoryTransfer(machine);
            transfer.addRestock(Acclimatiser.slotReserve, 4, 1);
            transfer.addStorage(4, Acclimatiser.slotDone, new ComponentInventoryTransfer.Condition() {
                public boolean fufilled(ItemStack stack) {
                    return !Acclimatiser.canAcclimatise(stack, machine.getMachineUtil().getNonNullStacks(Acclimatiser.slotAcclimatiser));
                }
            });
            new ComponentPowerReceptor(machine, 5000);
            new Acclimatiser.ComponentAcclimatiserLogic(machine);
        }

        public TileEntity createTileEntity() {
            return new TileEntityMachine(this);
        }

        public void register() {
        }
    }

    private static class ToleranceSystem {
        String uid;
        IChromosomeType chromosomeOrdinal;
        Acclimatiser.ToleranceType type;

        private ToleranceSystem(String uid, IChromosomeType chromosomeOrdinal, Acclimatiser.ToleranceType type) {
            super();
            this.uid = uid;
            this.chromosomeOrdinal = chromosomeOrdinal;
            this.type = type;
        }

        public boolean canAlter(ItemStack stack, ItemStack acclim) {
            IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
            IGenome genome = member.getGenome();
            IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(this.chromosomeOrdinal);
            Tolerance tol = Tolerance.get(tolAllele.getValue());
            float effect = this.type.getEffect(acclim);
            return effect > 0.0F && tol.getBounds()[1] < 5 ? true : effect < 0.0F && tol.getBounds()[0] > -5;
        }

        public ItemStack alter(ItemStack stack, ItemStack acc) {
            Random rand = new Random();
            float effect = this.type.getEffect(acc);
            if (rand.nextFloat() > Math.abs(effect)) {
                return stack;
            } else {
                IIndividual member = AlleleManager.alleleRegistry.getIndividual(stack);
                IGenome genome = member.getGenome();
                IAlleleTolerance tolAllele = (IAlleleTolerance) genome.getActiveAllele(this.chromosomeOrdinal);
                Tolerance tol = Tolerance.get(tolAllele.getValue());
                Tolerance newTol = Acclimatiser.alterTolerance(tol, effect);
                if (rand.nextFloat() > 1.0F / (-((float) newTol.getBounds()[0]) + (float) newTol.getBounds()[1])) {
                    return stack;
                } else {
                    ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(stack);
                    Inoculator.setGene(new Gene(newTol.getAllele(), this.chromosomeOrdinal, root), stack, rand.nextInt(2));
                    return stack;
                }
            }
        }
    }

    public static enum ToleranceType {
        Temperature,
        Humidity,
        PH;

        private ToleranceType() {
        }

        public float getEffect(ItemStack stack) {
            switch (this) {
                case Temperature:
                    return Acclimatiser.getTemperatureEffect(stack);
                case Humidity:
                    return Acclimatiser.getHumidityEffect(stack);
                case PH:
                    if (Gardening.isAcidFertiliser(stack)) {
                        return -0.5F * (float) Gardening.getFertiliserStrength(stack);
                    } else if (Gardening.isAlkalineFertiliser(stack)) {
                        return 0.5F * (float) Gardening.getFertiliserStrength(stack);
                    }
                default:
                    return 0.0F;
            }
        }

        public boolean hasEffect(ItemStack stack) {
            return this.getEffect(stack) != 0.0F;
        }
    }

    public static class ValidatorAcclimatiserItem extends SlotValidator {
        public ValidatorAcclimatiserItem() {
            super((ValidatorIcon) null);
        }

        public boolean isValid(ItemStack stack) {
            for (Acclimatiser.ToleranceType type : Acclimatiser.ToleranceType.values()) {
                if (type.hasEffect(stack)) {
                    return true;
                }
            }

            return false;
        }

        public String getTooltip() {
            return "Acclimatising Items";
        }
    }
}
