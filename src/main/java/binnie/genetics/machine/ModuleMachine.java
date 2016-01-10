package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.machines.MachineGroup;
import binnie.core.machines.inventory.ValidatorIcon;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.item.GeneticsItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModuleMachine implements IInitializable {
    static ValidatorIcon IconSequencer;
    static ValidatorIcon IconSerum;
    static ValidatorIcon IconEnzyme;
    public static ValidatorIcon IconDye;
    static ValidatorIcon IconBacteria;
    static ValidatorIcon IconNugget;

    public ModuleMachine() {
        super();
    }

    public void preInit() {
        Genetics.packageGenetic = new MachineGroup(Genetics.instance, "machine", "machine", GeneticMachine.values());
        Genetics.packageGenetic.setCreativeTab(CreativeTabGenetics.instance);
        Genetics.packageLabMachine = new MachineGroup(Genetics.instance, "labMachine", "labMachine", LaboratoryMachine.values());
        Genetics.packageLabMachine.setCreativeTab(CreativeTabGenetics.instance);
        Genetics.packageAdvGenetic = new MachineGroup(Genetics.instance, "advMachine", "advMachine", AdvGeneticMachine.values());
        Genetics.packageAdvGenetic.setCreativeTab(CreativeTabGenetics.instance);
    }

    public void init() {
        IconSequencer = new ValidatorIcon(Genetics.instance, "validator/sequencer.0", "validator/sequencer.1");
        IconSerum = new ValidatorIcon(Genetics.instance, "validator/serum.0", "validator/serum.1");
        IconEnzyme = new ValidatorIcon(Genetics.instance, "validator/enzyme.0", "validator/enzyme.1");
        IconDye = new ValidatorIcon(Genetics.instance, "validator/dye.0", "validator/dye.1");
        IconNugget = new ValidatorIcon(Genetics.instance, "validator/nugget.0", "validator/nugget.1");
        IconBacteria = new ValidatorIcon(Genetics.instance, "validator/bacteria.0", "validator/bacteria.1");
        Incubator.addRecipes();
    }

    public void postInit() {
        Acclimatiser.setupRecipes();
        Object[] standardCircuit = new Object[]{Mods.Forestry.stack("chipsets", 1, 1)};
        Object[] advCircuit = new Object[]{GeneticsItems.IntegratedCircuit.get(1)};
        String ironGear = OreDictionary.getOres("gearIron").isEmpty() ? "gearIron" : "ingotIron";
        String goldGear = OreDictionary.getOres("gearGold").isEmpty() ? "gearIron" : "ingotIron";
        String diamondGear = OreDictionary.getOres("gearDiamond").isEmpty() ? "gearIron" : "ingotIron";

        for (Object circuit : standardCircuit) {
            GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Incubator.get(1), new Object[]{"gFg", "cCc", "aPa", Character.valueOf('C'), GeneticsItems.LaboratoryCasing.get(1), Character.valueOf('F'), Blocks.furnace, Character.valueOf('c'), circuit, Character.valueOf('g'), Blocks.glass_pane, Character.valueOf('P'), "gearBronze", Character.valueOf('a'), ironGear}));
            Item alyzer = null;
            if (BinnieCore.isApicultureActive()) {
                alyzer = Mods.Forestry.item("beealyzer");
            } else if (BinnieCore.isArboricultureActive()) {
                alyzer = Mods.Forestry.item("treealyzer");
            } else if (BinnieCore.isArboricultureActive()) {
                alyzer = Mods.Forestry.item("flutterlyzer");
            }

            if (alyzer != null) {
                GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Analyser.get(1), new Object[]{"gBg", "cCc", "aPa", Character.valueOf('C'), GeneticsItems.LaboratoryCasing.get(1), Character.valueOf('B'), alyzer, Character.valueOf('c'), circuit, Character.valueOf('g'), Blocks.glass_pane, Character.valueOf('P'), "gearBronze", Character.valueOf('a'), GeneticsItems.DNADye.get(1)}));
            }

            GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Genepool.get(1), new Object[]{"gBg", "cCc", "aPa", Character.valueOf('C'), GeneticsItems.LaboratoryCasing.get(1), Character.valueOf('B'), "gearBronze", Character.valueOf('c'), circuit, Character.valueOf('g'), Blocks.glass_pane, Character.valueOf('P'), "gearBronze", Character.valueOf('a'), Blocks.glass}));
            GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.Acclimatiser.get(1), new Object[]{"gBg", "cCc", "aPa", Character.valueOf('C'), GeneticsItems.LaboratoryCasing.get(1), Character.valueOf('B'), Items.lava_bucket, Character.valueOf('c'), circuit, Character.valueOf('g'), Blocks.glass_pane, Character.valueOf('P'), "gearBronze", Character.valueOf('a'), Items.water_bucket}));
        }

        for (Object circuit : advCircuit) {
            GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Isolator.get(1), new Object[]{"gBg", "cCc", "aPa", Character.valueOf('C'), GeneticsItems.LaboratoryCasing.get(1), Character.valueOf('B'), goldGear, Character.valueOf('c'), circuit, Character.valueOf('g'), Items.gold_nugget, Character.valueOf('P'), "gearBronze", Character.valueOf('a'), GeneticsItems.Enzyme.get(1)}));
            GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Polymeriser.get(1), new Object[]{"gBg", "cCc", "gPg", Character.valueOf('C'), GeneticsItems.LaboratoryCasing.get(1), Character.valueOf('B'), ironGear, Character.valueOf('c'), circuit, Character.valueOf('g'), Items.gold_nugget, Character.valueOf('P'), "gearBronze"}));
            GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Sequencer.get(1), new Object[]{"gBg", "cCc", "aPa", Character.valueOf('C'), GeneticsItems.LaboratoryCasing.get(1), Character.valueOf('B'), "gearBronze", Character.valueOf('c'), circuit, Character.valueOf('g'), Items.gold_nugget, Character.valueOf('P'), "gearBronze", Character.valueOf('a'), GeneticsItems.FluorescentDye.get(1)}));
            GameRegistry.addRecipe(new ShapedOreRecipe(GeneticMachine.Inoculator.get(1), new Object[]{"gBg", "cCc", "aPa", Character.valueOf('C'), GeneticsItems.LaboratoryCasing.get(1), Character.valueOf('B'), diamondGear, Character.valueOf('c'), circuit, Character.valueOf('g'), Items.gold_nugget, Character.valueOf('P'), "gearBronze", Character.valueOf('a'), Items.emerald}));
        }

        GameRegistry.addRecipe(new ShapedOreRecipe(AdvGeneticMachine.Splicer.get(1), new Object[]{"gBg", "cCc", "aPa", Character.valueOf('C'), GeneticsItems.IntegratedCasing.get(1), Character.valueOf('B'), diamondGear, Character.valueOf('c'), GeneticsItems.IntegratedCPU.get(1), Character.valueOf('g'), Items.gold_nugget, Character.valueOf('P'), "gearBronze", Character.valueOf('a'), Items.blaze_rod}));
        GameRegistry.addRecipe(new ShapedOreRecipe(LaboratoryMachine.LabMachine.get(1), new Object[]{"igi", "gCg", "igi", Character.valueOf('C'), GeneticsItems.LaboratoryCasing.get(1), Character.valueOf('i'), "ingotIron", Character.valueOf('g'), Blocks.glass_pane}));
    }
}
