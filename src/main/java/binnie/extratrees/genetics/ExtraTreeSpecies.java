package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.genetics.ForestryAllele;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.ILogType;
import binnie.extratrees.gen.WorldGenAlder;
import binnie.extratrees.gen.WorldGenApple;
import binnie.extratrees.gen.WorldGenAsh;
import binnie.extratrees.gen.WorldGenBanana;
import binnie.extratrees.gen.WorldGenBeech;
import binnie.extratrees.gen.WorldGenConifer;
import binnie.extratrees.gen.WorldGenDefault;
import binnie.extratrees.gen.WorldGenEucalyptus;
import binnie.extratrees.gen.WorldGenFir;
import binnie.extratrees.gen.WorldGenHolly;
import binnie.extratrees.gen.WorldGenJungle;
import binnie.extratrees.gen.WorldGenLazy;
import binnie.extratrees.gen.WorldGenMaple;
import binnie.extratrees.gen.WorldGenPalm;
import binnie.extratrees.gen.WorldGenPoplar;
import binnie.extratrees.gen.WorldGenShrub;
import binnie.extratrees.gen.WorldGenSorbus;
import binnie.extratrees.gen.WorldGenTree;
import binnie.extratrees.gen.WorldGenTree2;
import binnie.extratrees.gen.WorldGenTree3;
import binnie.extratrees.gen.WorldGenTropical;
import binnie.extratrees.gen.WorldGenWalnut;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.arboriculture.*;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IClassification;
import forestry.api.genetics.IFruitFamily;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import forestry.api.genetics.IClassification.EnumClassLevel;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;

public enum ExtraTreeSpecies implements IAlleleTreeSpecies, IIconProvider {
    OrchardApple("malus", "domestica", 6588464, 16751859, ILogType.ExtraTreeLog.Apple, ExtraTreeFruitGene.Apple, WorldGenApple.OrchardApple.class),
    SweetCrabapple("malus", "coronaria", 8034643, 16528799, ILogType.ExtraTreeLog.Apple, ExtraTreeFruitGene.Crabapple, WorldGenApple.SweetCrabapple.class),
    FloweringCrabapple("malus", "hopa", 8034643, 16528799, ILogType.ExtraTreeLog.Apple, ExtraTreeFruitGene.Crabapple, WorldGenApple.FloweringCrabapple.class),
    PrairieCrabapple("malus", "ioensis", 8034643, 16528799, ILogType.ExtraTreeLog.Apple, ExtraTreeFruitGene.Crabapple, WorldGenApple.PrairieCrabapple.class),
    Blackthorn("prunus", "spinosa ", 7180062, 16746439, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.Blackthorn, (Class)null),
    CherryPlum("prunus", "cerasifera", 7180062, 16746439, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.CherryPlum, (Class)null),
    Peach("prunus", "persica", 7180062, 16721562, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.Peach, (Class)null),
    Nectarine("prunus", "nectarina", 7180062, 16721562, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.Nectarine, (Class)null),
    Apricot("prunus", "armeniaca", 7180062, 16103640, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.Apricot, (Class)null),
    Almond("prunus", "amygdalus", 7180062, 16090304, ILogType.ForestryLog.PLUM, ExtraTreeFruitGene.Almond, (Class)null),
    WildCherry("prunus", "avium", 7180062, 16247798, ILogType.ExtraTreeLog.Cherry, ExtraTreeFruitGene.WildCherry, (Class)null),
    SourCherry("prunus", "cerasus", 7180062, 16247798, ILogType.ExtraTreeLog.Cherry, ExtraTreeFruitGene.SourCherry, (Class)null),
    BlackCherry("prunus", "serotina", 7180062, 16441848, ILogType.ExtraTreeLog.Cherry, ExtraTreeFruitGene.BlackCherry, (Class)null),
    Orange("citrus", "sinensis", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Orange, (Class)null),
    Manderin("citrus", "reticulata", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Manderin, (Class)null),
    Satsuma("citrus", "unshiu", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Satsuma, (Class)null),
    Tangerine("citrus", "tangerina", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Tangerine, (Class)null),
    Lime("citrus", "latifolia", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Lime, (Class)null),
    KeyLime("citrus", "aurantifolia", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.KeyLime, (Class)null),
    FingerLime("citrus", "australasica", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.FingerLime, (Class)null),
    Pomelo("citrus", "maxima", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Pomelo, (Class)null),
    Grapefruit("citrus", "paradisi", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Grapefruit, (Class)null),
    Kumquat("citrus", "margarita", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Kumquat, (Class)null),
    Citron("citrus", "medica", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.Citron, (Class)null),
    BuddhaHand("citrus", "sarcodactylus", 8957780, 10729552, ILogType.ForestryLog.CITRUS, ExtraTreeFruitGene.BuddhaHand, (Class)null),
    Banana("musa", "sinensis", 10603918, 4515072, ILogType.ExtraTreeLog.Banana, ExtraTreeFruitGene.Banana, WorldGenBanana.class),
    RedBanana("musa", "rubra", 10603918, 4515072, ILogType.ExtraTreeLog.Banana, ExtraTreeFruitGene.RedBanana, WorldGenBanana.class),
    Plantain("musa", "paradisiaca", 10603918, 4515072, ILogType.ExtraTreeLog.Banana, ExtraTreeFruitGene.Plantain, WorldGenBanana.class),
    Butternut("juglans", "cinerea", 8566156, 8576396, ILogType.ExtraTreeLog.Butternut, ExtraTreeFruitGene.Butternut, WorldGenWalnut.Butternut.class),
    Rowan("sorbus", "aucuparia", 10405787, 10414258, ILogType.ExtraTreeLog.Rowan, (IAlleleFruit)null, WorldGenSorbus.Rowan.class),
    Hemlock("tsuga", "heterophylla", 6073458, 6082930, ILogType.ExtraTreeLog.Hemlock, (IAlleleFruit)null, WorldGenConifer.WesternHemlock.class),
    Ash("fraxinus", "excelsior", 4754987, 4777003, ILogType.ExtraTreeLog.Ash, (IAlleleFruit)null, WorldGenAsh.CommonAsh.class),
    Alder("alnus", "glutinosa", 6916659, 6925875, ILogType.ExtraTreeLog.Alder, (IAlleleFruit)null, WorldGenAlder.CommonAlder.class),
    Beech("fagus", "sylvatica", 8626252, 8635980, ILogType.ExtraTreeLog.Beech, ExtraTreeFruitGene.Beechnut, WorldGenBeech.CommonBeech.class),
    CopperBeech("fagus", "purpurea", 8393496, 13720397, ILogType.ExtraTreeLog.Beech, ExtraTreeFruitGene.Beechnut, WorldGenBeech.CopperBeech.class),
    Aspen("populus", "tremula", 9096247, 9101711, ILogType.ForestryLog.POPLAR, (IAlleleFruit)null, WorldGenPoplar.Aspen.class),
    Yew("taxus", "baccata", 9734733, 9743949, ILogType.ExtraTreeLog.Yew, (IAlleleFruit)null, WorldGenConifer.Yew.class),
    Cypress("chamaecyparis", "lawsoniana", 9030055, 9035206, ILogType.ExtraTreeLog.Cypress, (IAlleleFruit)null, WorldGenConifer.Cypress.class),
    DouglasFir("pseudotsuga", "menziesii", 10073474, 10080682, ILogType.ExtraTreeLog.Fir, (IAlleleFruit)null, WorldGenFir.DouglasFir.class),
    Hazel("Corylus", "avellana", 10204498, 10215762, ILogType.ExtraTreeLog.Hazel, ExtraTreeFruitGene.Hazelnut, WorldGenTree3.Hazel.class),
    Sycamore("ficus", "sycomorus", 10528047, 11851100, ILogType.ExtraTreeLog.Fig, ExtraTreeFruitGene.Fig, WorldGenTree3.Sycamore.class),
    Whitebeam("sorbus", "aria", 12242585, 7505471, ILogType.ExtraTreeLog.Whitebeam, (IAlleleFruit)null, WorldGenSorbus.Whitebeam.class),
    Hawthorn("crataegus", "monogyna", 7055434, 10008443, ILogType.ExtraTreeLog.Hawthorn, (IAlleleFruit)null, WorldGenTree3.Hawthorn.class),
    Pecan("carya", "illinoinensis", 8762996, 2906139, ILogType.ExtraTreeLog.Hickory, ExtraTreeFruitGene.Pecan, WorldGenTree3.Pecan.class),
    Elm("ulmus", "procera", 8163400, 8175176, ILogType.ExtraTreeLog.Elm, (IAlleleFruit)null, WorldGenTree3.Elm.class),
    Elder("sambucus", "nigra", 11450483, 14739389, ILogType.ExtraTreeLog.Elder, ExtraTreeFruitGene.Elderberry, WorldGenTree3.Elder.class),
    Holly("ilex", "aquifolium", 2444108, 7246468, ILogType.ExtraTreeLog.Holly, (IAlleleFruit)null, WorldGenHolly.Holly.class),
    Hornbeam("carpinus", "betulus", 9873179, 9887003, ILogType.ExtraTreeLog.Hornbeam, (IAlleleFruit)null, WorldGenTree3.Hornbeam.class),
    Sallow("salix", "caprea", 11449123, 12053541, ILogType.ForestryLog.WILLOW, (IAlleleFruit)null, WorldGenTree3.Sallow.class),
    AcornOak("quercus", "robur", 6714174, 10396209, ILogType.VanillaLog.Oak, ExtraTreeFruitGene.Acorn, WorldGenTree3.AcornOak.class),
    Fir("abies", "alba", 7306272, 7328032, ILogType.ExtraTreeLog.Fir, (IAlleleFruit)null, WorldGenFir.SilverFir.class),
    Cedar("cedrus", "libani", 9806704, 9824368, ILogType.ExtraTreeLog.Cedar, (IAlleleFruit)null, WorldGenConifer.Cedar.class),
    Olive("olea", "europaea", 3950644, 3950644, ILogType.ExtraTreeLog.Olive, ExtraTreeFruitGene.Olive, WorldGenTree2.Olive.class),
    RedMaple("acer", "ubrum", 15216151, 15216151, ILogType.ForestryLog.MAPLE, (IAlleleFruit)null, WorldGenMaple.RedMaple.class),
    BalsamFir("abies", "balsamea", 7643260, 7643260, ILogType.ExtraTreeLog.Fir, (IAlleleFruit)null, WorldGenFir.BalsamFir.class),
    LoblollyPine("pinus", "taeda", 7309895, 7309895, ILogType.ForestryLog.PINE, (IAlleleFruit)null, WorldGenConifer.LoblollyPine.class),
    Sweetgum("liquidambar", "styraciflua", 9144162, 9144162, ILogType.ExtraTreeLog.Sweetgum, (IAlleleFruit)null, WorldGenTree2.Sweetgum.class),
    Locust("robinia", "pseudoacacia", 8942336, 8942336, ILogType.ExtraTreeLog.Locust, (IAlleleFruit)null, WorldGenTree2.Locust.class),
    Pear("pyrus", "communis", 6195238, 6195238, ILogType.ExtraTreeLog.Pear, ExtraTreeFruitGene.Pear, WorldGenTree2.Pear.class),
    OsangeOsange("maclura", "pomifera", 6847056, 6847056, ILogType.ExtraTreeLog.Maclura, ExtraTreeFruitGene.OsangeOsange, WorldGenJungle.OsangeOsange.class),
    OldFustic("maclura", "tinctoria", 6847056, 6847056, ILogType.ExtraTreeLog.Maclura, (IAlleleFruit)null, WorldGenJungle.OldFustic.class),
    Brazilwood("caesalpinia", "echinata", 6321241, 6321241, ILogType.ExtraTreeLog.Brazilwood, (IAlleleFruit)null, WorldGenJungle.Brazilwood.class),
    Logwood("haematoxylum", "campechianum", 8953707, 8953707, ILogType.ExtraTreeLog.Logwood, (IAlleleFruit)null, WorldGenJungle.Logwood.class),
    Rosewood("dalbergia", "latifolia", 8887074, 8887074, ILogType.ExtraTreeLog.Rosewood, (IAlleleFruit)null, WorldGenJungle.Rosewood.class),
    Purpleheart("peltogyne", "spp.", 7835477, 7835477, ILogType.ExtraTreeLog.Purpleheart, (IAlleleFruit)null, WorldGenJungle.Purpleheart.class),
    Iroko("milicia", "excelsa", 11520108, 11520108, ILogType.ExtraTreeLog.Iroko, (IAlleleFruit)null, WorldGenTree2.Iroko.class),
    Gingko("ginkgo", "biloba", 7444049, 7444049, ILogType.ExtraTreeLog.Gingko, ExtraTreeFruitGene.GingkoNut, WorldGenTree2.Gingko.class),
    Brazilnut("bertholletia", "excelsa", 8163195, 8163195, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.BrazilNut, WorldGenJungle.BrazilNut.class),
    RoseGum("eucalyptus", "grandis", 10265176, 10265176, ILogType.ExtraTreeLog.Eucalyptus, (IAlleleFruit)null, WorldGenEucalyptus.RoseGum.class),
    SwampGum("eucalyptus", "grandis", 10667654, 10667654, ILogType.ExtraTreeLog.Eucalyptus2, (IAlleleFruit)null, WorldGenEucalyptus.SwampGum.class),
    Box("boxus", "sempervirens", 7510381, 7510381, ILogType.ExtraTreeLog.Box, (IAlleleFruit)null, WorldGenTree2.Box.class),
    Clove("syzygium", "aromaticum", 8028703, 8028703, ILogType.ExtraTreeLog.Syzgium, ExtraTreeFruitGene.Clove, WorldGenTree2.Clove.class),
    Coffee("coffea", "arabica", 7311461, 7311461, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Coffee, WorldGenJungle.Coffee.class),
    MonkeyPuzzle("araucaria", "araucana", 5726552, 5726552, ILogType.ForestryLog.PINE, (IAlleleFruit)null, WorldGenConifer.MonkeyPuzzle.class),
    RainbowGum("eucalyptus", "deglupta", 12054565, 12054565, ILogType.ExtraTreeLog.Eucalyptus3, (IAlleleFruit)null, WorldGenEucalyptus.RainbowGum.class),
    PinkIvory("berchemia", "zeyheri", 8163673, 8163673, ILogType.ExtraTreeLog.PinkIvory, (IAlleleFruit)null, WorldGenTree.class),
    Blackcurrant("ribes", "nigrum", 10934876, 10934876, (ILogType)null, ExtraTreeFruitGene.Blackcurrant, WorldGenShrub.Shrub.class),
    Redcurrant("ribes", "rubrum", 7646208, 7646208, (ILogType)null, ExtraTreeFruitGene.Redcurrant, WorldGenShrub.Shrub.class),
    Blackberry("rubus", "fruticosus", 9617755, 9617755, (ILogType)null, ExtraTreeFruitGene.Blackberry, WorldGenShrub.Shrub.class),
    Raspberry("rubus", "idaeus", 8632686, 8632686, (ILogType)null, ExtraTreeFruitGene.Raspberry, WorldGenShrub.Shrub.class),
    Blueberry("vaccinium", "corymbosum", 7522128, 7522128, (ILogType)null, ExtraTreeFruitGene.Blueberry, WorldGenShrub.Shrub.class),
    Cranberry("vaccinium", "oxycoccos", 9884025, 9884025, (ILogType)null, ExtraTreeFruitGene.Cranberry, WorldGenShrub.Shrub.class),
    Juniper("juniperus", "communis", 9482569, 9482569, (ILogType)null, ExtraTreeFruitGene.Juniper, WorldGenShrub.Shrub.class),
    Gooseberry("ribes", "grossularia", 7977728, 7977728, (ILogType)null, ExtraTreeFruitGene.Gooseberry, WorldGenShrub.Shrub.class),
    GoldenRaspberry("rubus", "occidentalis", 8632686, 8632686, (ILogType)null, ExtraTreeFruitGene.GoldenRaspberry, WorldGenShrub.Shrub.class),
    Cinnamon("cinnamomum", "cassia", 7573003, 7573003, ILogType.ExtraTreeLog.Cinnamon, (IAlleleFruit)null, WorldGenLazy.Tree.class),
    Coconut("cocous", "nucifera", 6592803, 6592803, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Coconut, WorldGenPalm.Coconut.class),
    Cashew("anacardium", "occidentale", 11254114, 11254114, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Cashew, WorldGenLazy.Tree.class),
    Avacado("persea", "americana", 9872245, 9872245, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Avacado, WorldGenLazy.Tree.class),
    Nutmeg("myristica", "fragrans", 4754764, 4754764, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Nutmeg, WorldGenLazy.Tree.class),
    Allspice("pimenta", "dioica", 8165156, 8165156, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Allspice, WorldGenLazy.Tree.class),
    Chilli("capsicum", "annuum", 2793217, 2793217, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Chilli, WorldGenLazy.Tree.class),
    StarAnise("illicium", "verum", 8373257, 8373257, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.StarAnise, WorldGenLazy.Tree.class),
    Mango("mangifera", "indica", 8893812, 8893812, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Mango, WorldGenTropical.Mango.class),
    Starfruit("averrhoa", "carambola", 7186733, 7186733, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Starfruit, WorldGenLazy.Tree.class),
    Candlenut("aleurites", "moluccana", 9085804, 9085804, ILogType.VanillaLog.Jungle, ExtraTreeFruitGene.Candlenut, WorldGenLazy.Tree.class),
    DwarfHazel("Corylus", "americana", 10204498, 10215762, ILogType.ExtraTreeLog.Hazel, ExtraTreeFruitGene.Hazelnut, WorldGenShrub.Shrub.class);

    private ExtraTreeSpecies.LeafType leafType = ExtraTreeSpecies.LeafType.Normal;
    private ExtraTreeSpecies.SaplingType saplingType = ExtraTreeSpecies.SaplingType.Default;
    ArrayList families = new ArrayList();
    int girth = 1;
    Class gen;
    IAlleleFruit fruit = null;
    IAllele[] template;
    int color;
    String binomial;
    String uid;
    ILogType wood;
    String branchName;
    IClassification branch;

    public static void init() {
        String bookArborist = "Arborist Manual";

        for(ExtraTreeSpecies species : values()) {
            species.preInit();
        }

        OrchardApple.finished();
        SweetCrabapple.finished();
        FloweringCrabapple.finished();
        PrairieCrabapple.finished();
        //TODO: FIX
        /*ExtraTreeSpecies[] pruneSpecies = new ExtraTreeSpecies[]{Blackthorn, CherryPlum, Almond, Apricot, Peach, Nectarine, WildCherry, SourCherry, BlackCherry};

        for(ExtraTreeSpecies species : pruneSpecies) {
            IAlleleTreeSpecies citrus = (IAlleleTreeSpecies)AlleleManager.alleleRegistry.getAllele("forestry.treePlum");
            species.setWorldGen(citrus.getGeneratorClasses()[0]);
            species.saplingType = ExtraTreeSpecies.SaplingType.Fruit;
            species.finished();
        }

        ExtraTreeSpecies[] citrusSpecies = new ExtraTreeSpecies[]{Orange, Manderin, Satsuma, Tangerine, Lime, KeyLime, FingerLime, Pomelo, Grapefruit, Kumquat, Citron, BuddhaHand};

        for(ExtraTreeSpecies species : citrusSpecies) {
            species.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
            species.saplingType = ExtraTreeSpecies.SaplingType.Fruit;
            IAlleleTreeSpecies citrus = (IAlleleTreeSpecies)AlleleManager.alleleRegistry.getAllele("forestry.treeLemon");
            species.setWorldGen(citrus.getGeneratorClasses()[0]);
            species.finished();
        }*/

        Banana.setLeafType(ExtraTreeSpecies.LeafType.Palm);
        RedBanana.setLeafType(ExtraTreeSpecies.LeafType.Palm);
        Plantain.setLeafType(ExtraTreeSpecies.LeafType.Palm);
        Banana.finished();
        RedBanana.finished();
        Plantain.finished();
        Hemlock.setLeafType(ExtraTreeSpecies.LeafType.Conifer);
        Butternut.finished();
        Butternut.setGirth(2);
        Rowan.finished();
        Hemlock.finished();
        Hemlock.setGirth(2);
        Ash.finished();
        Alder.finished();
        Beech.finished();
        CopperBeech.finished();
        Aspen.finished();
        Yew.setLeafType(ExtraTreeSpecies.LeafType.Conifer);
        Cypress.setLeafType(ExtraTreeSpecies.LeafType.Conifer);
        DouglasFir.setLeafType(ExtraTreeSpecies.LeafType.Conifer);
        Yew.finished();
        Cypress.finished();
        Cypress.saplingType = ExtraTreeSpecies.SaplingType.Poplar;
        DouglasFir.finished();
        DouglasFir.setGirth(2);
        Hazel.finished();
        DwarfHazel.finished();
        DwarfHazel.saplingType = ExtraTreeSpecies.SaplingType.Shrub;
        Sycamore.finished();
        Whitebeam.finished();
        Hawthorn.finished();
        Pecan.finished();
        Fir.setLeafType(ExtraTreeSpecies.LeafType.Conifer);
        Cedar.setLeafType(ExtraTreeSpecies.LeafType.Conifer);
        Sallow.setLeafType(ExtraTreeSpecies.LeafType.Willow);
        Elm.finished();
        Elder.finished();
        Holly.finished();
        Hornbeam.finished();
        Sallow.finished();
        AcornOak.finished();
        AcornOak.setGirth(2);
        Fir.finished();
        Cedar.finished();
        Cedar.setGirth(2);
        RedMaple.setLeafType(ExtraTreeSpecies.LeafType.Maple);
        BalsamFir.setLeafType(ExtraTreeSpecies.LeafType.Conifer);
        LoblollyPine.setLeafType(ExtraTreeSpecies.LeafType.Conifer);
        Olive.finished();
        RedMaple.finished();
        BalsamFir.finished();
        LoblollyPine.finished();
        Sweetgum.finished();
        Locust.finished();
        Pear.finished();
        OsangeOsange.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        OldFustic.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Brazilwood.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Logwood.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Rosewood.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Purpleheart.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        OsangeOsange.finished();
        OldFustic.finished();
        Brazilwood.finished();
        Logwood.finished();
        Rosewood.finished();
        Purpleheart.finished();
        Gingko.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Brazilnut.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        RoseGum.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        SwampGum.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Coffee.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        MonkeyPuzzle.setLeafType(ExtraTreeSpecies.LeafType.Conifer);
        RainbowGum.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Iroko.finished();
        Gingko.finished();
        Brazilnut.finished();
        RoseGum.finished();
        SwampGum.finished();
        SwampGum.setGirth(2);
        Box.finished();
        Clove.finished();
        Coffee.finished();
        MonkeyPuzzle.finished();
        MonkeyPuzzle.setGirth(2);
        RainbowGum.finished();
        PinkIvory.finished();
        Juniper.setLeafType(ExtraTreeSpecies.LeafType.Conifer);
        Blackcurrant.saplingType = ExtraTreeSpecies.SaplingType.Shrub;
        Redcurrant.saplingType = ExtraTreeSpecies.SaplingType.Shrub;
        Blackberry.saplingType = ExtraTreeSpecies.SaplingType.Shrub;
        Raspberry.saplingType = ExtraTreeSpecies.SaplingType.Shrub;
        Blueberry.saplingType = ExtraTreeSpecies.SaplingType.Shrub;
        Cranberry.saplingType = ExtraTreeSpecies.SaplingType.Shrub;
        Juniper.saplingType = ExtraTreeSpecies.SaplingType.Shrub;
        Gooseberry.saplingType = ExtraTreeSpecies.SaplingType.Shrub;
        GoldenRaspberry.saplingType = ExtraTreeSpecies.SaplingType.Shrub;

        for(ExtraTreeSpecies species : values()) {
            String scientific = species.branchName.substring(0, 1).toUpperCase() + species.branchName.substring(1).toLowerCase();
            String uid = "trees." + species.branchName.toLowerCase();
            IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
            if(branch == null) {
                branch = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, uid, scientific);
            }

            species.branch = branch;
            species.branch.addMemberSpecies(species);
        }

        Cinnamon.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Coconut.setLeafType(ExtraTreeSpecies.LeafType.Palm);
        Cashew.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Avacado.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Nutmeg.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Allspice.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Chilli.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        StarAnise.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Mango.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        Starfruit.setLeafType(ExtraTreeSpecies.LeafType.Jungle);
        IFruitFamily familyPrune = AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes");
        IFruitFamily familyPome = AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes");
        IFruitFamily familyJungle = AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle");
        IFruitFamily familyNuts = AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts");
        IFruitFamily familyBerry = ExtraTreeFruitFamily.Berry;
        IFruitFamily familyCitrus = ExtraTreeFruitFamily.Citrus;
        OrchardApple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        SweetCrabapple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        FloweringCrabapple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        PrairieCrabapple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Blackthorn.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        CherryPlum.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Peach.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Nectarine.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Apricot.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Almond.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        WildCherry.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        SourCherry.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        BlackCherry.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Orange.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Manderin.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Satsuma.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Tangerine.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Lime.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        KeyLime.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        FingerLime.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Pomelo.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Grapefruit.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Kumquat.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Citron.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        BuddhaHand.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Banana.addFamily(familyNuts).addFamily(familyJungle);
        RedBanana.addFamily(familyNuts).addFamily(familyJungle);
        Plantain.addFamily(familyNuts).addFamily(familyJungle);
        Butternut.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        Rowan.addFamily(familyNuts).addFamily(familyBerry);
        Ash.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        Alder.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts);
        Beech.addFamily(familyPrune).addFamily(familyNuts);
        CopperBeech.addFamily(familyPrune).addFamily(familyNuts);
        Aspen.addFamily(familyPome).addFamily(familyNuts);
        Hazel.addFamily(familyPrune).addFamily(familyNuts);
        Sycamore.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        Whitebeam.addFamily(familyPome).addFamily(familyPrune);
        Hawthorn.addFamily(familyPrune).addFamily(familyNuts);
        Pecan.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        Elm.addFamily(familyPome).addFamily(familyPrune);
        Elder.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus).addFamily(familyBerry);
        Holly.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Hornbeam.addFamily(familyPrune).addFamily(familyNuts);
        Sallow.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        AcornOak.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts);
        Olive.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        RedMaple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        Sweetgum.addFamily(familyNuts);
        Locust.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        Pear.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        OsangeOsange.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        OldFustic.addFamily(familyJungle);
        Brazilwood.addFamily(familyJungle);
        Logwood.addFamily(familyJungle);
        Rosewood.addFamily(familyJungle);
        Purpleheart.addFamily(familyJungle);
        Iroko.addFamily(familyNuts).addFamily(familyJungle);
        Gingko.addFamily(familyNuts).addFamily(familyJungle);
        Brazilnut.addFamily(familyNuts).addFamily(familyJungle);
        RoseGum.addFamily(familyJungle);
        SwampGum.addFamily(familyJungle);
        Box.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        Clove.addFamily(familyNuts).addFamily(familyJungle);
        Coffee.addFamily(familyNuts).addFamily(familyJungle);
        MonkeyPuzzle.addFamily(familyNuts).addFamily(familyJungle);
        RainbowGum.addFamily(familyNuts).addFamily(familyJungle);
        PinkIvory.addFamily(familyNuts).addFamily(familyJungle);
        Blackcurrant.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        Redcurrant.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        Blackberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        Raspberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        Blueberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        Cranberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        Juniper.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        Gooseberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        GoldenRaspberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        Cinnamon.addFamily(familyNuts).addFamily(familyJungle);
        Coconut.addFamily(familyJungle);
        Cashew.addFamily(familyNuts).addFamily(familyJungle);
        Avacado.addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        Nutmeg.addFamily(familyNuts).addFamily(familyJungle);
        Allspice.addFamily(familyNuts).addFamily(familyJungle);
        Chilli.addFamily(familyNuts).addFamily(familyJungle);
        StarAnise.addFamily(familyNuts).addFamily(familyJungle);
        Mango.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        Starfruit.addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        Candlenut.addFamily(familyNuts).addFamily(familyJungle);
        DwarfHazel.addFamily(familyPrune).addFamily(familyNuts);
        OrchardApple.setYield(ForestryAllele.Yield.Higher).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        SweetCrabapple.setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        FloweringCrabapple.setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        PrairieCrabapple.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average);
        Blackthorn.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        CherryPlum.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        Peach.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average);
        Nectarine.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average);
        Apricot.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Average);
        Almond.setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        WildCherry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        SourCherry.setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low);
        BlackCherry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        Orange.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        Manderin.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low);
        Satsuma.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        Tangerine.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Faster);
        Lime.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low);
        KeyLime.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        FingerLime.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        Pomelo.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        Grapefruit.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        Kumquat.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low);
        Citron.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Average);
        BuddhaHand.setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low);
        Banana.setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        RedBanana.setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Lower);
        Plantain.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower);
        Butternut.setHeight(ForestryAllele.TreeHeight.Smaller).setYield(ForestryAllele.Yield.Low);
        Rowan.setHeight(ForestryAllele.TreeHeight.Larger).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        Hemlock.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low).setMaturation(ForestryAllele.Maturation.Slower);
        Ash.setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        Alder.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        Beech.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower);
        CopperBeech.setMaturation(ForestryAllele.Maturation.Slow);
        Aspen.setFertility(ForestryAllele.Saplings.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        Yew.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower);
        Cypress.setHeight(ForestryAllele.TreeHeight.Larger).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        DouglasFir.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setMaturation(ForestryAllele.Maturation.Slower);
        Hazel.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        Sycamore.setFertility(ForestryAllele.Saplings.Lowest).setSappiness(ForestryAllele.Sappiness.Lower);
        Whitebeam.setHeight(ForestryAllele.TreeHeight.Smaller);
        Hawthorn.setHeight(ForestryAllele.TreeHeight.Average);
        Pecan.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        Elm.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Average);
        Elder.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Low);
        Holly.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Low);
        Hornbeam.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        Sallow.setHeight(ForestryAllele.TreeHeight.Large).setFertility(ForestryAllele.Saplings.Low);
        AcornOak.setHeight(ForestryAllele.TreeHeight.Large).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        Fir.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        Cedar.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slower);
        Olive.setYield(ForestryAllele.Yield.Average);
        RedMaple.setFertility(ForestryAllele.Saplings.Average).setSappiness(ForestryAllele.Sappiness.High);
        BalsamFir.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        LoblollyPine.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        Sweetgum.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.High).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Average);
        Locust.setHeight(ForestryAllele.TreeHeight.Smallest);
        Pear.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        OsangeOsange.setYield(ForestryAllele.Yield.Lower);
        OldFustic.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Lower);
        Brazilwood.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Lower);
        Logwood.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        Rosewood.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        Purpleheart.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower);
        Iroko.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low);
        Gingko.setHeight(ForestryAllele.TreeHeight.Large).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low);
        Brazilnut.setHeight(ForestryAllele.TreeHeight.Larger).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low);
        RoseGum.setHeight(ForestryAllele.TreeHeight.Largest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Slowest);
        SwampGum.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setMaturation(ForestryAllele.Maturation.Slower);
        Box.setHeight(ForestryAllele.TreeHeight.Smaller).setMaturation(ForestryAllele.Maturation.Faster);
        Clove.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        Coffee.setHeight(ForestryAllele.TreeHeight.Large).setYield(ForestryAllele.Yield.Average).setMaturation(ForestryAllele.Maturation.Fast);
        MonkeyPuzzle.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low);
        RainbowGum.setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower);
        PinkIvory.setHeight(ForestryAllele.TreeHeight.Smallest);
        Blackcurrant.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
        Redcurrant.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        Blackberry.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
        Raspberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
        Blueberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        Cranberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        Juniper.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        Gooseberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.High).setYield(ForestryAllele.Yield.High).setMaturation(ForestryAllele.Maturation.Faster);
        GoldenRaspberry.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fastest);
        Cinnamon.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        Coconut.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setMaturation(ForestryAllele.Maturation.Fast);
        Cashew.setYield(ForestryAllele.Yield.Low);
        Avacado.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average);
        Nutmeg.setHeight(ForestryAllele.TreeHeight.Smaller).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low);
        Allspice.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High);
        Chilli.setHeight(ForestryAllele.TreeHeight.Smaller).setYield(ForestryAllele.Yield.Higher).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        StarAnise.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.High);
        Mango.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        Starfruit.setYield(ForestryAllele.Yield.Average).setMaturation(ForestryAllele.Maturation.Fast);
        Candlenut.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low);
        DwarfHazel.setFertility(ForestryAllele.Saplings.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
    }

    private ExtraTreeSpecies addFamily(IFruitFamily family) {
        this.families.add(family);
        return this;
    }

    static final ItemStack getEBXLStack(String name) {
        try {
            Class elements = Class.forName("extrabiomes.lib.Element");
            Method getElementMethod = elements.getMethod("valueOf", new Class[]{String.class});
            Method getItemStack = elements.getMethod("get", new Class[0]);
            Object element = getElementMethod.invoke((Object)null, new Object[]{"SAPLING_AUTUMN_YELLOW"});
            return (ItemStack)getItemStack.invoke(element, new Object[0]);
        } catch (Exception var5) {
            throw new RuntimeException(var5);
        }
    }

    private void setWorldGen(Class gen) {
        this.gen = gen;
    }

    private void setGirth(int i) {
        this.template[EnumTreeChromosome.GIRTH.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.i" + i + "d");
    }

    public void preInit() {
        this.template = Binnie.Genetics.getTreeRoot().getDefaultTemplate();
        this.template[0] = this;
        if(this.fruit != null) {
            this.template[EnumTreeChromosome.FRUITS.ordinal()] = this.fruit;
        }

        this.template[EnumTreeChromosome.GIRTH.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.i" + this.girth + "d");
        IClassification clas = AlleleManager.alleleRegistry.getClassification("trees." + this.branch);
        if(clas != null) {
            clas.addMemberSpecies(this);
            this.branch = clas;
        }

    }

    @Override
    public ITreeGenerator getGenerator() {
        return null;
    }

    @Override
    public int getLeafColour(boolean pollinated) {
        return 0;
    }

    @Override
    public IIcon getLeafIcon(boolean pollinated, boolean fancy) {
        return null;
    }

    private ExtraTreeSpecies(String branch, String binomial, int color, int polColor, ILogType wood, IAlleleFruit fruit, Class gen) {
        this.color = color;
        this.uid = this.toString().toLowerCase();
        this.wood = wood;
        this.fruit = fruit;
        this.gen = gen == null?WorldGenTree.class:gen;
        this.branchName = branch;
        this.binomial = binomial;
    }

    public String getName() {
        return ExtraTrees.proxy.localise("species." + this.name().toLowerCase() + ".name");
    }

    public String getDescription() {
        return ExtraTrees.proxy.localiseOrBlank("species." + this.name().toLowerCase() + ".desc");
    }

    public EnumTemperature getTemperature() {
        return EnumTemperature.NORMAL;
    }

    public EnumHumidity getHumidity() {
        return EnumHumidity.NORMAL;
    }

    public boolean hasEffect() {
        return false;
    }

    public boolean isSecret() {
        return false;
    }

    public boolean isCounted() {
        return true;
    }

    public String getBinomial() {
        return this.binomial;
    }

    public String getAuthority() {
        return "Binnie";
    }

    public IClassification getBranch() {
        return this.branch;
    }

    public String getUID() {
        return "extratrees.species." + this.uid;
    }

    public boolean isDominant() {
        return true;
    }

    public EnumPlantType getPlantType() {
        return EnumPlantType.Plains;
    }

    public WorldGenerator getGenerator(ITree tree, World world, int x, int y, int z) {
        if(this.gen != null) {
            try {
                return (WorldGenerator)this.gen.getConstructor(new Class[]{ITree.class}).newInstance(new Object[]{tree});
            } catch (Exception var7) {
                ;
            }
        }

        return new WorldGenDefault(tree);
    }

    public Class<? extends WorldGenerator>[] getGeneratorClasses() {
        return null;
    }

    void setLeafType(ExtraTreeSpecies.LeafType type) {
        this.leafType = type;
        if(this.leafType == ExtraTreeSpecies.LeafType.Conifer) {
            this.saplingType = ExtraTreeSpecies.SaplingType.Conifer;
        }

        if(this.leafType == ExtraTreeSpecies.LeafType.Jungle) {
            this.saplingType = ExtraTreeSpecies.SaplingType.Jungle;
        }

        if(this.leafType == ExtraTreeSpecies.LeafType.Palm) {
            this.saplingType = ExtraTreeSpecies.SaplingType.Palm;
        }

    }

    public IAllele[] getTemplate() {
        return this.template;
    }

    public ArrayList getSuitableFruit() {
        return this.families;
    }

    public ILogType getLog() {
        return this.wood;
    }

    public ExtraTreeSpecies setHeight(ForestryAllele.TreeHeight height) {
        IAllele allele = height.getAllele();
        if(allele != null) {
            this.template[EnumTreeChromosome.HEIGHT.ordinal()] = allele;
        }

        return this;
    }

    public ExtraTreeSpecies setSappiness(ForestryAllele.Sappiness height) {
        IAllele allele = height.getAllele();
        if(allele != null) {
            this.template[EnumTreeChromosome.SAPPINESS.ordinal()] = allele;
        }

        return this;
    }

    public ExtraTreeSpecies setMaturation(ForestryAllele.Maturation height) {
        IAllele allele = height.getAllele();
        if(allele != null) {
            this.template[EnumTreeChromosome.MATURATION.ordinal()] = allele;
        }

        return this;
    }

    public ExtraTreeSpecies setYield(ForestryAllele.Yield height) {
        IAllele allele = height.getAllele();
        if(allele != null) {
            this.template[EnumTreeChromosome.YIELD.ordinal()] = allele;
        }

        return this;
    }

    public ExtraTreeSpecies setFertility(ForestryAllele.Saplings height) {
        IAllele allele = height.getAllele();
        if(allele != null) {
            this.template[EnumTreeChromosome.FERTILITY.ordinal()] = allele;
        }

        return this;
    }

    public void setGrowthConditions(ForestryAllele.Growth growth) {
        IAllele allele = growth.getAllele();
        if(allele != null) {
            this.template[EnumTreeChromosome.GROWTH.ordinal()] = allele;
        }

    }

    public void finished() {
    }

    public int getLeafColour(ITree tree) {
        return this.color;
    }

    public short getLeafIconIndex(ITree tree, boolean fancy) {
        return !fancy?this.leafType.plainUID:(tree.getMate() != null?this.leafType.changedUID:this.leafType.fancyUID);
    }

    public int getIconColour(int renderPass) {
        return renderPass == 0?this.color:10451021;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getGermlingIcon(EnumGermlingType type, int renderPass) {
        return type == EnumGermlingType.POLLEN?Mods.Forestry.item("pollen").getIconFromDamageForRenderPass(0, renderPass):(renderPass == 0?this.saplingType.icon[1]:this.saplingType.icon[0]);
    }

    public IIconProvider getIconProvider() {
        return this;
    }

    public IIcon getIcon(short texUID) {
        return null;
    }

    public void registerIcons(IIconRegister register) {
        for(ExtraTreeSpecies.SaplingType type : ExtraTreeSpecies.SaplingType.values()) {
            type.icon = new IIcon[2];
            type.icon[0] = ExtraTrees.proxy.getIcon(register, "saplings/" + type.toString().toLowerCase() + ".trunk");
            type.icon[1] = ExtraTrees.proxy.getIcon(register, "saplings/" + type.toString().toLowerCase() + ".leaves");
        }

    }

    public ITreeRoot getRoot() {
        return Binnie.Genetics.getTreeRoot();
    }

    public float getResearchSuitability(ItemStack itemstack) {
        if(itemstack == null) {
            return 0.0F;
        } else {
            if(this.template[EnumTreeChromosome.FRUITS.ordinal()] instanceof ExtraTreeFruitGene) {
                ExtraTreeFruitGene fruit = (ExtraTreeFruitGene)this.template[EnumTreeChromosome.FRUITS.ordinal()];

                for(ItemStack stack : fruit.products.keySet()) {
                    if(stack.isItemEqual(itemstack)) {
                        return 1.0F;
                    }
                }
            }

            if(itemstack.getItem() == Mods.Forestry.item("honeyDrop")) {
                return 0.5F;
            } else if(itemstack.getItem() == Mods.Forestry.item("honeydew")) {
                return 0.7F;
            } else if(itemstack.getItem() == Mods.Forestry.item("beeComb")) {
                return 0.4F;
            } else if(AlleleManager.alleleRegistry.isIndividual(itemstack)) {
                return 1.0F;
            } else {
                for(Entry<ItemStack, Float> entry : this.getRoot().getResearchCatalysts().entrySet()) {
                    if(((ItemStack)entry.getKey()).isItemEqual(itemstack)) {
                        return ((Float)entry.getValue()).floatValue();
                    }
                }

                return 0.0F;
            }
        }
    }

    public ItemStack[] getResearchBounty(World world, GameProfile researcher, IIndividual individual, int bountyLevel) {
        ArrayList<ItemStack> bounty = new ArrayList();
        ItemStack research = null;
        if(world.rand.nextFloat() < 10.0F / (float)bountyLevel) {
            Collection<? extends IMutation> combinations = this.getRoot().getCombinations(this);
            if(combinations.size() > 0) {
                IMutation[] candidates = (IMutation[])combinations.toArray(new IMutation[0]);
                research = AlleleManager.alleleRegistry.getMutationNoteStack(researcher, candidates[world.rand.nextInt(candidates.length)]);
            }
        }

        if(research != null) {
            bounty.add(research);
        }

        if(this.template[EnumTreeChromosome.FRUITS.ordinal()] instanceof ExtraTreeFruitGene) {
            ExtraTreeFruitGene fruit = (ExtraTreeFruitGene)this.template[EnumTreeChromosome.FRUITS.ordinal()];

            for(ItemStack stack : fruit.products.keySet()) {
                ItemStack stack2 = stack.copy();
                stack2.stackSize = world.rand.nextInt((int)((float)bountyLevel / 2.0F)) + 1;
                bounty.add(stack2);
            }
        }

        return (ItemStack[])bounty.toArray(new ItemStack[0]);
    }

    @SideOnly(Side.CLIENT)
    public int getGermlingColour(EnumGermlingType type, int renderPass) {
        return type == EnumGermlingType.SAPLING?(renderPass == 0?this.getLeafColour((ITree)null):(this.getLog() == null?16777215:this.getLog().getColour())):this.getLeafColour((ITree)null);
    }

    public int getComplexity() {
        return 1 + this.getGeneticAdvancement(this, new ArrayList());
    }

    private int getGeneticAdvancement(IAllele species, ArrayList exclude) {
        int own = 1;
        int highest = 0;
        exclude.add(species);

        for(IMutation mutation : this.getRoot().getPaths(species, EnumBeeChromosome.SPECIES)) {
            if(!exclude.contains(mutation.getAllele0())) {
                int otherAdvance = this.getGeneticAdvancement(mutation.getAllele0(), exclude);
                if(otherAdvance > highest) {
                    highest = otherAdvance;
                }
            }

            if(!exclude.contains(mutation.getAllele1())) {
                int otherAdvance = this.getGeneticAdvancement(mutation.getAllele1(), exclude);
                if(otherAdvance > highest) {
                    highest = otherAdvance;
                }
            }
        }

        return own + (highest < 0?0:highest);
    }

    public ItemStack[] getLogStacks() {
        return this.wood == null?new ItemStack[0]:new ItemStack[]{this.wood.getItemStack()};
    }

    public String getUnlocalizedName() {
        return "extratrees.species." + this.toString().toLowerCase() + ".name";
    }

    public static enum LeafType {
        Normal((short)10, (short)11, (short)12, "Decidious"),
        Conifer((short)15, (short)16, (short)17, "Conifer"),
        Jungle((short)20, (short)21, (short)22, "Jungle"),
        Willow((short)25, (short)26, (short)27, "Willow"),
        Maple((short)30, (short)31, (short)32, "Maple"),
        Palm((short)35, (short)36, (short)37, "Palm");

        public final short fancyUID;
        public final short plainUID;
        public final short changedUID;
        public final String descript;

        private LeafType(short fancyUID, short plainUID, short changedUID, String descript) {
            this.fancyUID = fancyUID;
            this.plainUID = plainUID;
            this.changedUID = changedUID;
            this.descript = descript;
        }
    }

    public static enum SaplingType {
        Default,
        Jungle,
        Conifer,
        Fruit,
        Poplar,
        Palm,
        Shrub;

        IIcon[] icon;

        private SaplingType() {
        }
    }
}
