package binnie.genetics.item;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.liquid.ItemFluidContainer;
import binnie.core.resource.BinnieIcon;
import binnie.extrabees.ExtraBees;
import binnie.extratrees.ExtraTrees;
import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModuleItem implements IInitializable {
    public static BinnieIcon iconNight;
    public static BinnieIcon iconDaytime;
    public static BinnieIcon iconAllDay;
    public static BinnieIcon iconRain;
    public static BinnieIcon iconNoRain;
    public static BinnieIcon iconSky;
    public static BinnieIcon iconNoSky;
    public static BinnieIcon iconFire;
    public static BinnieIcon iconNoFire;
    public static BinnieIcon iconAdd;
    public static BinnieIcon iconArrow;
    public static BinnieIcon iconAdd0;
    public static BinnieIcon iconArrow0;
    public static BinnieIcon iconAdd1;
    public static BinnieIcon iconArrow1;

    public ModuleItem() {
        super();
    }

    public void preInit() {
        Genetics.itemSerum = new ItemSerum();
        Genetics.itemSerumArray = new ItemSerumArray();
        Genetics.itemSequencer = new ItemSequence();
        Genetics.itemGenetics = Binnie.Item.registerMiscItems(GeneticsItems.values(), CreativeTabGenetics.instance);
        Genetics.database = new ItemDatabase();
        Genetics.analyst = new ItemAnalyst();
        Genetics.registry = new ItemRegistry();
        Genetics.masterRegistry = new ItemMasterRegistry();
        Binnie.Liquid.createLiquids(GeneticLiquid.values(), ItemFluidContainer.LiquidGenetics);
    }

    public void init() {
        iconNight = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.night");
        iconDaytime = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.day");
        iconAllDay = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.allday");
        iconRain = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.rain");
        iconNoRain = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.norain");
        iconSky = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.sky");
        iconNoSky = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.nosky");
        iconFire = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.fire");
        iconNoFire = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.nofire");
        iconAdd = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.add");
        iconArrow = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.arrow");
        iconAdd0 = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.add.0");
        iconArrow0 = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.arrow.0");
        iconAdd1 = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.add.1");
        iconArrow1 = Binnie.Resource.getItemIcon(BinnieCore.instance, "gui/analyst.arrow.1");
    }

    public void postInit() {
        GameRegistry.addShapelessRecipe(GeneticsItems.DNADye.get(8), new Object[]{Items.glowstone_dust, new ItemStack(Items.dye, 1, 5)});
        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.LaboratoryCasing.get(1), new Object[]{"iii", "iYi", "iii", Character.valueOf('i'), "ingotIron", Character.valueOf('Y'), Mods.Forestry.item("sturdyMachine")}));
        GameRegistry.addRecipe(new ShapelessOreRecipe(GeneticsItems.DNADye.get(2), new Object[]{"dyePurple", "dyeMagenta", "dyePink"}));
        GameRegistry.addRecipe(new ShapelessOreRecipe(GeneticsItems.FluorescentDye.get(2), new Object[]{"dyeOrange", "dyeYellow", "dustGlowstone"}));
        GameRegistry.addRecipe(new ShapelessOreRecipe(GeneticsItems.GrowthMedium.get(2), new Object[]{new ItemStack(Items.dye, 1, 15), Items.sugar}));
        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.EmptySequencer.get(1), new Object[]{" p ", "iGi", " p ", Character.valueOf('i'), "ingotGold", Character.valueOf('G'), Blocks.glass_pane, Character.valueOf('p'), Items.paper}));
        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.EmptySerum.get(1), new Object[]{" g ", " G ", "GGG", Character.valueOf('g'), "ingotGold", Character.valueOf('G'), Blocks.glass_pane}));
        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.EmptyGenome.get(1), new Object[]{"sss", "sss", "sss", Character.valueOf('s'), GeneticsItems.EmptySerum.get(1)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.Cylinder.get(8), new Object[]{" g ", "g g", "   ", Character.valueOf('g'), Blocks.glass_pane}));
        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCircuit.get(1), new Object[]{"l g", " c ", "g l", Character.valueOf('c'), Mods.Forestry.stack("chipsets", 1, 1), Character.valueOf('l'), new ItemStack(Items.dye, 1, 4), Character.valueOf('g'), "dustGlowstone"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCircuit.get(1), new Object[]{"g l", " c ", "l g", Character.valueOf('c'), Mods.Forestry.stack("chipsets", 1, 1), Character.valueOf('l'), new ItemStack(Items.dye, 1, 4), Character.valueOf('g'), "dustGlowstone"}));
        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCasing.get(1), new Object[]{"ccc", "cdc", "ccc", Character.valueOf('c'), GeneticsItems.IntegratedCircuit.get(1), Character.valueOf('d'), GeneticsItems.LaboratoryCasing.get(1)}));
        GameRegistry.addRecipe(new ShapedOreRecipe(GeneticsItems.IntegratedCPU.get(1), new Object[]{"ccc", "cdc", "ccc", Character.valueOf('c'), GeneticsItems.IntegratedCircuit.get(1), Character.valueOf('d'), Items.diamond}));
        RecipeManagers.carpenterManager.addRecipe(100, Binnie.Liquid.getLiquidStack("water", 2000), (ItemStack) null, new ItemStack(Genetics.database), new Object[]{"X#X", "YEY", "RDR", Character.valueOf('#'), Blocks.glass_pane, Character.valueOf('X'), Items.diamond, Character.valueOf('Y'), Items.diamond, Character.valueOf('R'), Items.redstone, Character.valueOf('D'), Items.ender_eye, Character.valueOf('E'), Blocks.obsidian});
        GameRegistry.addSmelting(Genetics.itemSequencer, GeneticsItems.EmptySequencer.get(1), 0.0F);
        GameRegistry.addSmelting(Genetics.itemSerum, GeneticsItems.EmptySerum.get(1), 0.0F);
        GameRegistry.addSmelting(Genetics.itemSerumArray, GeneticsItems.EmptyGenome.get(1), 0.0F);
        Item[] lyzers = new Item[]{Mods.Forestry.item("beealyzer"), Mods.Forestry.item("treealyzer"), Mods.Forestry.item("flutterlyzer")};

        for (Item a : lyzers) {
            for (Item b : lyzers) {
                for (Item c : lyzers) {
                    if (a != b && a != c && b != c) {
                        GameRegistry.addShapedRecipe(new ItemStack(Genetics.analyst), new Object[]{" b ", "fct", " d ", Character.valueOf('c'), GeneticsItems.IntegratedCircuit.get(1), Character.valueOf('b'), a, Character.valueOf('t'), b, Character.valueOf('f'), c, Character.valueOf('d'), new ItemStack(Items.diamond)});
                    }
                }
            }
        }

        Item[] dbs = new Item[]{ExtraBees.dictionary, ExtraTrees.itemDictionary, ExtraTrees.itemDictionaryLepi, Botany.database};
        if (BinnieCore.isBotanyActive() && BinnieCore.isExtraBeesActive() && BinnieCore.isExtraTreesActive()) {
            for (Item a : dbs) {
                for (Item b : dbs) {
                    for (Item c : dbs) {
                        for (Item d : dbs) {
                            if (a != b && a != c && a != d && b != c && b != d && c != d) {
                                GameRegistry.addShapedRecipe(new ItemStack(Genetics.registry), new Object[]{" b ", "fct", " l ", Character.valueOf('c'), GeneticsItems.IntegratedCircuit.get(1), Character.valueOf('b'), a, Character.valueOf('t'), b, Character.valueOf('f'), c, Character.valueOf('l'), d});
                            }
                        }
                    }
                }
            }
        }

    }
}
