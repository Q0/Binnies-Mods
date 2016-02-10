package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.genetics.ForestryAllele;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.ILogType;
import binnie.extratrees.gen.*;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.arboriculture.*;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.EnumPlantType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public enum ExtraTreeSpecies implements IAlleleTreeSpecies, IIconProvider {
    OrchardApple("malus", "domestica", 6588464, 16751859, (ILogType) ILogType.ExtraTreeLog.Apple, (IAlleleFruit) ExtraTreeFruitGene.Apple, (Class<? extends WorldGenerator>) WorldGenApple.OrchardApple.class),
    SweetCrabapple("malus", "coronaria", 8034643, 16528799, (ILogType) ILogType.ExtraTreeLog.Apple, (IAlleleFruit) ExtraTreeFruitGene.Crabapple, (Class<? extends WorldGenerator>) WorldGenApple.SweetCrabapple.class),
    FloweringCrabapple("malus", "hopa", 8034643, 16528799, (ILogType) ILogType.ExtraTreeLog.Apple, (IAlleleFruit) ExtraTreeFruitGene.Crabapple, (Class<? extends WorldGenerator>) WorldGenApple.FloweringCrabapple.class),
    PrairieCrabapple("malus", "ioensis", 8034643, 16528799, (ILogType) ILogType.ExtraTreeLog.Apple, (IAlleleFruit) ExtraTreeFruitGene.Crabapple, (Class<? extends WorldGenerator>) WorldGenApple.PrairieCrabapple.class),
    Blackthorn("prunus", "spinosa ", 7180062, 16746439, (ILogType) ILogType.ForestryLog.PLUM, (IAlleleFruit) ExtraTreeFruitGene.Blackthorn, (Class<? extends WorldGenerator>) null),
    CherryPlum("prunus", "cerasifera", 7180062, 16746439, (ILogType) ILogType.ForestryLog.PLUM, (IAlleleFruit) ExtraTreeFruitGene.CherryPlum, (Class<? extends WorldGenerator>) null),
    Peach("prunus", "persica", 7180062, 16721562, (ILogType) ILogType.ForestryLog.PLUM, (IAlleleFruit) ExtraTreeFruitGene.Peach, (Class<? extends WorldGenerator>) null),
    Nectarine("prunus", "nectarina", 7180062, 16721562, (ILogType) ILogType.ForestryLog.PLUM, (IAlleleFruit) ExtraTreeFruitGene.Nectarine, (Class<? extends WorldGenerator>) null),
    Apricot("prunus", "armeniaca", 7180062, 16103640, (ILogType) ILogType.ForestryLog.PLUM, (IAlleleFruit) ExtraTreeFruitGene.Apricot, (Class<? extends WorldGenerator>) null),
    Almond("prunus", "amygdalus", 7180062, 16090304, (ILogType) ILogType.ForestryLog.PLUM, (IAlleleFruit) ExtraTreeFruitGene.Almond, (Class<? extends WorldGenerator>) null),
    WildCherry("prunus", "avium", 7180062, 16247798, (ILogType) ILogType.ExtraTreeLog.Cherry, (IAlleleFruit) ExtraTreeFruitGene.WildCherry, (Class<? extends WorldGenerator>) null),
    SourCherry("prunus", "cerasus", 7180062, 16247798, (ILogType) ILogType.ExtraTreeLog.Cherry, (IAlleleFruit) ExtraTreeFruitGene.SourCherry, (Class<? extends WorldGenerator>) null),
    BlackCherry("prunus", "serotina", 7180062, 16441848, (ILogType) ILogType.ExtraTreeLog.Cherry, (IAlleleFruit) ExtraTreeFruitGene.BlackCherry, (Class<? extends WorldGenerator>) null),
    Orange("citrus", "sinensis", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.Orange, (Class<? extends WorldGenerator>) null),
    Manderin("citrus", "reticulata", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.Manderin, (Class<? extends WorldGenerator>) null),
    Satsuma("citrus", "unshiu", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.Satsuma, (Class<? extends WorldGenerator>) null),
    Tangerine("citrus", "tangerina", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.Tangerine, (Class<? extends WorldGenerator>) null),
    Lime("citrus", "latifolia", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.Lime, (Class<? extends WorldGenerator>) null),
    KeyLime("citrus", "aurantifolia", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.KeyLime, (Class<? extends WorldGenerator>) null),
    FingerLime("citrus", "australasica", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.FingerLime, (Class<? extends WorldGenerator>) null),
    Pomelo("citrus", "maxima", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.Pomelo, (Class<? extends WorldGenerator>) null),
    Grapefruit("citrus", "paradisi", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.Grapefruit, (Class<? extends WorldGenerator>) null),
    Kumquat("citrus", "margarita", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.Kumquat, (Class<? extends WorldGenerator>) null),
    Citron("citrus", "medica", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.Citron, (Class<? extends WorldGenerator>) null),
    BuddhaHand("citrus", "sarcodactylus", 8957780, 10729552, (ILogType) ILogType.ForestryLog.CITRUS, (IAlleleFruit) ExtraTreeFruitGene.BuddhaHand, (Class<? extends WorldGenerator>) null),
    Banana("musa", "sinensis", 10603918, 4515072, (ILogType) ILogType.ExtraTreeLog.Banana, (IAlleleFruit) ExtraTreeFruitGene.Banana, (Class<? extends WorldGenerator>) WorldGenBanana.class),
    RedBanana("musa", "rubra", 10603918, 4515072, (ILogType) ILogType.ExtraTreeLog.Banana, (IAlleleFruit) ExtraTreeFruitGene.RedBanana, (Class<? extends WorldGenerator>) WorldGenBanana.class),
    Plantain("musa", "paradisiaca", 10603918, 4515072, (ILogType) ILogType.ExtraTreeLog.Banana, (IAlleleFruit) ExtraTreeFruitGene.Plantain, (Class<? extends WorldGenerator>) WorldGenBanana.class),
    Butternut("juglans", "cinerea", 8566156, 8576396, (ILogType) ILogType.ExtraTreeLog.Butternut, (IAlleleFruit) ExtraTreeFruitGene.Butternut, (Class<? extends WorldGenerator>) WorldGenWalnut.Butternut.class),
    Rowan("sorbus", "aucuparia", 10405787, 10414258, (ILogType) ILogType.ExtraTreeLog.Rowan, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenSorbus.Rowan.class),
    Hemlock("tsuga", "heterophylla", 6073458, 6082930, (ILogType) ILogType.ExtraTreeLog.Hemlock, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenConifer.WesternHemlock.class),
    Ash("fraxinus", "excelsior", 4754987, 4777003, (ILogType) ILogType.ExtraTreeLog.Ash, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenAsh.CommonAsh.class),
    Alder("alnus", "glutinosa", 6916659, 6925875, (ILogType) ILogType.ExtraTreeLog.Alder, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenAlder.CommonAlder.class),
    Beech("fagus", "sylvatica", 8626252, 8635980, (ILogType) ILogType.ExtraTreeLog.Beech, (IAlleleFruit) ExtraTreeFruitGene.Beechnut, (Class<? extends WorldGenerator>) WorldGenBeech.CommonBeech.class),
    CopperBeech("fagus", "purpurea", 8393496, 13720397, (ILogType) ILogType.ExtraTreeLog.Beech, (IAlleleFruit) ExtraTreeFruitGene.Beechnut, (Class<? extends WorldGenerator>) WorldGenBeech.CopperBeech.class),
    Aspen("populus", "tremula", 9096247, 9101711, (ILogType) ILogType.ForestryLog.POPLAR, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenPoplar.Aspen.class),
    Yew("taxus", "baccata", 9734733, 9743949, (ILogType) ILogType.ExtraTreeLog.Yew, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenConifer.Yew.class),
    Cypress("chamaecyparis", "lawsoniana", 9030055, 9035206, (ILogType) ILogType.ExtraTreeLog.Cypress, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenConifer.Cypress.class),
    DouglasFir("pseudotsuga", "menziesii", 10073474, 10080682, (ILogType) ILogType.ExtraTreeLog.Fir, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenFir.DouglasFir.class),
    Hazel("Corylus", "avellana", 10204498, 10215762, (ILogType) ILogType.ExtraTreeLog.Hazel, (IAlleleFruit) ExtraTreeFruitGene.Hazelnut, (Class<? extends WorldGenerator>) WorldGenTree3.Hazel.class),
    Sycamore("ficus", "sycomorus", 10528047, 11851100, (ILogType) ILogType.ExtraTreeLog.Fig, (IAlleleFruit) ExtraTreeFruitGene.Fig, (Class<? extends WorldGenerator>) WorldGenTree3.Sycamore.class),
    Whitebeam("sorbus", "aria", 12242585, 7505471, (ILogType) ILogType.ExtraTreeLog.Whitebeam, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenSorbus.Whitebeam.class),
    Hawthorn("crataegus", "monogyna", 7055434, 10008443, (ILogType) ILogType.ExtraTreeLog.Hawthorn, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenTree3.Hawthorn.class),
    Pecan("carya", "illinoinensis", 8762996, 2906139, (ILogType) ILogType.ExtraTreeLog.Hickory, (IAlleleFruit) ExtraTreeFruitGene.Pecan, (Class<? extends WorldGenerator>) WorldGenTree3.Pecan.class),
    Elm("ulmus", "procera", 8163400, 8175176, (ILogType) ILogType.ExtraTreeLog.Elm, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenTree3.Elm.class),
    Elder("sambucus", "nigra", 11450483, 14739389, (ILogType) ILogType.ExtraTreeLog.Elder, (IAlleleFruit) ExtraTreeFruitGene.Elderberry, (Class<? extends WorldGenerator>) WorldGenTree3.Elder.class),
    Holly("ilex", "aquifolium", 2444108, 7246468, (ILogType) ILogType.ExtraTreeLog.Holly, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenHolly.Holly.class),
    Hornbeam("carpinus", "betulus", 9873179, 9887003, (ILogType) ILogType.ExtraTreeLog.Hornbeam, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenTree3.Hornbeam.class),
    Sallow("salix", "caprea", 11449123, 12053541, (ILogType) ILogType.ForestryLog.WILLOW, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenTree3.Sallow.class),
    AcornOak("quercus", "robur", 6714174, 10396209, (ILogType) ILogType.VanillaLog.Oak, (IAlleleFruit) ExtraTreeFruitGene.Acorn, (Class<? extends WorldGenerator>) WorldGenTree3.AcornOak.class),
    Fir("abies", "alba", 7306272, 7328032, (ILogType) ILogType.ExtraTreeLog.Fir, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenFir.SilverFir.class),
    Cedar("cedrus", "libani", 9806704, 9824368, (ILogType) ILogType.ExtraTreeLog.Cedar, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenConifer.Cedar.class),
    Olive("olea", "europaea", 3950644, 3950644, (ILogType) ILogType.ExtraTreeLog.Olive, (IAlleleFruit) ExtraTreeFruitGene.Olive, (Class<? extends WorldGenerator>) WorldGenTree2.Olive.class),
    RedMaple("acer", "ubrum", 15216151, 15216151, (ILogType) ILogType.ForestryLog.MAPLE, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenMaple.RedMaple.class),
    BalsamFir("abies", "balsamea", 7643260, 7643260, (ILogType) ILogType.ExtraTreeLog.Fir, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenFir.BalsamFir.class),
    LoblollyPine("pinus", "taeda", 7309895, 7309895, (ILogType) ILogType.ForestryLog.PINE, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenConifer.LoblollyPine.class),
    Sweetgum("liquidambar", "styraciflua", 9144162, 9144162, (ILogType) ILogType.ExtraTreeLog.Sweetgum, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenTree2.Sweetgum.class),
    Locust("robinia", "pseudoacacia", 8942336, 8942336, (ILogType) ILogType.ExtraTreeLog.Locust, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenTree2.Locust.class),
    Pear("pyrus", "communis", 6195238, 6195238, (ILogType) ILogType.ExtraTreeLog.Pear, (IAlleleFruit) ExtraTreeFruitGene.Pear, (Class<? extends WorldGenerator>) WorldGenTree2.Pear.class),
    OsangeOsange("maclura", "pomifera", 6847056, 6847056, (ILogType) ILogType.ExtraTreeLog.Maclura, (IAlleleFruit) ExtraTreeFruitGene.OsangeOsange, (Class<? extends WorldGenerator>) WorldGenJungle.OsangeOsange.class),
    OldFustic("maclura", "tinctoria", 6847056, 6847056, (ILogType) ILogType.ExtraTreeLog.Maclura, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenJungle.OldFustic.class),
    Brazilwood("caesalpinia", "echinata", 6321241, 6321241, (ILogType) ILogType.ExtraTreeLog.Brazilwood, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenJungle.Brazilwood.class),
    Logwood("haematoxylum", "campechianum", 8953707, 8953707, (ILogType) ILogType.ExtraTreeLog.Logwood, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenJungle.Logwood.class),
    Rosewood("dalbergia", "latifolia", 8887074, 8887074, (ILogType) ILogType.ExtraTreeLog.Rosewood, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenJungle.Rosewood.class),
    Purpleheart("peltogyne", "spp.", 7835477, 7835477, (ILogType) ILogType.ExtraTreeLog.Purpleheart, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenJungle.Purpleheart.class),
    Iroko("milicia", "excelsa", 11520108, 11520108, (ILogType) ILogType.ExtraTreeLog.Iroko, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenTree2.Iroko.class),
    Gingko("ginkgo", "biloba", 7444049, 7444049, (ILogType) ILogType.ExtraTreeLog.Gingko, (IAlleleFruit) ExtraTreeFruitGene.GingkoNut, (Class<? extends WorldGenerator>) WorldGenTree2.Gingko.class),
    Brazilnut("bertholletia", "excelsa", 8163195, 8163195, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.BrazilNut, (Class<? extends WorldGenerator>) WorldGenJungle.BrazilNut.class),
    RoseGum("eucalyptus", "grandis", 10265176, 10265176, (ILogType) ILogType.ExtraTreeLog.Eucalyptus, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenEucalyptus.RoseGum.class),
    SwampGum("eucalyptus", "grandis", 10667654, 10667654, (ILogType) ILogType.ExtraTreeLog.Eucalyptus2, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenEucalyptus.SwampGum.class),
    Box("boxus", "sempervirens", 7510381, 7510381, (ILogType) ILogType.ExtraTreeLog.Box, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenTree2.Box.class),
    Clove("syzygium", "aromaticum", 8028703, 8028703, (ILogType) ILogType.ExtraTreeLog.Syzgium, (IAlleleFruit) ExtraTreeFruitGene.Clove, (Class<? extends WorldGenerator>) WorldGenTree2.Clove.class),
    Coffee("coffea", "arabica", 7311461, 7311461, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.Coffee, (Class<? extends WorldGenerator>) WorldGenJungle.Coffee.class),
    MonkeyPuzzle("araucaria", "araucana", 5726552, 5726552, (ILogType) ILogType.ForestryLog.PINE, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenConifer.MonkeyPuzzle.class),
    RainbowGum("eucalyptus", "deglupta", 12054565, 12054565, (ILogType) ILogType.ExtraTreeLog.Eucalyptus3, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenEucalyptus.RainbowGum.class),
    PinkIvory("berchemia", "zeyheri", 8163673, 8163673, (ILogType) ILogType.ExtraTreeLog.PinkIvory, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenTree.class),
    Blackcurrant("ribes", "nigrum", 10934876, 10934876, (ILogType) null, (IAlleleFruit) ExtraTreeFruitGene.Blackcurrant, (Class<? extends WorldGenerator>) WorldGenShrub.Shrub.class),
    Redcurrant("ribes", "rubrum", 7646208, 7646208, (ILogType) null, (IAlleleFruit) ExtraTreeFruitGene.Redcurrant, (Class<? extends WorldGenerator>) WorldGenShrub.Shrub.class),
    Blackberry("rubus", "fruticosus", 9617755, 9617755, (ILogType) null, (IAlleleFruit) ExtraTreeFruitGene.Blackberry, (Class<? extends WorldGenerator>) WorldGenShrub.Shrub.class),
    Raspberry("rubus", "idaeus", 8632686, 8632686, (ILogType) null, (IAlleleFruit) ExtraTreeFruitGene.Raspberry, (Class<? extends WorldGenerator>) WorldGenShrub.Shrub.class),
    Blueberry("vaccinium", "corymbosum", 7522128, 7522128, (ILogType) null, (IAlleleFruit) ExtraTreeFruitGene.Blueberry, (Class<? extends WorldGenerator>) WorldGenShrub.Shrub.class),
    Cranberry("vaccinium", "oxycoccos", 9884025, 9884025, (ILogType) null, (IAlleleFruit) ExtraTreeFruitGene.Cranberry, (Class<? extends WorldGenerator>) WorldGenShrub.Shrub.class),
    Juniper("juniperus", "communis", 9482569, 9482569, (ILogType) null, (IAlleleFruit) ExtraTreeFruitGene.Juniper, (Class<? extends WorldGenerator>) WorldGenShrub.Shrub.class),
    Gooseberry("ribes", "grossularia", 7977728, 7977728, (ILogType) null, (IAlleleFruit) ExtraTreeFruitGene.Gooseberry, (Class<? extends WorldGenerator>) WorldGenShrub.Shrub.class),
    GoldenRaspberry("rubus", "occidentalis", 8632686, 8632686, (ILogType) null, (IAlleleFruit) ExtraTreeFruitGene.GoldenRaspberry, (Class<? extends WorldGenerator>) WorldGenShrub.Shrub.class),
    Cinnamon("cinnamomum", "cassia", 7573003, 7573003, (ILogType) ILogType.ExtraTreeLog.Cinnamon, (IAlleleFruit) null, (Class<? extends WorldGenerator>) WorldGenLazy.Tree.class),
    Coconut("cocous", "nucifera", 6592803, 6592803, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.Coconut, (Class<? extends WorldGenerator>) WorldGenPalm.Coconut.class),
    Cashew("anacardium", "occidentale", 11254114, 11254114, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.Cashew, (Class<? extends WorldGenerator>) WorldGenLazy.Tree.class),
    Avacado("persea", "americana", 9872245, 9872245, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.Avacado, (Class<? extends WorldGenerator>) WorldGenLazy.Tree.class),
    Nutmeg("myristica", "fragrans", 4754764, 4754764, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.Nutmeg, (Class<? extends WorldGenerator>) WorldGenLazy.Tree.class),
    Allspice("pimenta", "dioica", 8165156, 8165156, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.Allspice, (Class<? extends WorldGenerator>) WorldGenLazy.Tree.class),
    Chilli("capsicum", "annuum", 2793217, 2793217, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.Chilli, (Class<? extends WorldGenerator>) WorldGenLazy.Tree.class),
    StarAnise("illicium", "verum", 8373257, 8373257, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.StarAnise, (Class<? extends WorldGenerator>) WorldGenLazy.Tree.class),
    Mango("mangifera", "indica", 8893812, 8893812, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.Mango, (Class<? extends WorldGenerator>) WorldGenTropical.Mango.class),
    Starfruit("averrhoa", "carambola", 7186733, 7186733, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.Starfruit, (Class<? extends WorldGenerator>) WorldGenLazy.Tree.class),
    Candlenut("aleurites", "moluccana", 9085804, 9085804, (ILogType) ILogType.VanillaLog.Jungle, (IAlleleFruit) ExtraTreeFruitGene.Candlenut, (Class<? extends WorldGenerator>) WorldGenLazy.Tree.class),
    DwarfHazel("Corylus", "americana", 10204498, 10215762, (ILogType) ILogType.ExtraTreeLog.Hazel, (IAlleleFruit) ExtraTreeFruitGene.Hazelnut, (Class<? extends WorldGenerator>) WorldGenShrub.Shrub.class);

    ArrayList<IFruitFamily> families;
    int girth;
    Class<? extends WorldGenerator> gen;
    IAlleleFruit fruit;
    IAllele[] template;
    int color;
    String binomial;
    String uid;
    ILogType wood;
    String branchName;
    IClassification branch;
    private LeafType leafType;
    private SaplingType saplingType;

    private ExtraTreeSpecies(final String branch, final String binomial, final int color, final int polColor, final ILogType wood, final IAlleleFruit fruit, final Class<? extends WorldGenerator> gen) {
        this.leafType = LeafType.Normal;
        this.saplingType = SaplingType.Default;
        this.families = new ArrayList<IFruitFamily>();
        this.girth = 1;
        this.fruit = null;
        this.color = color;
        this.uid = this.toString().toLowerCase();
        this.wood = wood;
        this.fruit = fruit;
        this.gen = ((gen == null) ? WorldGenTree.class : gen);
        this.branchName = branch;
        this.binomial = binomial;
    }

    public static void init() {
        final String bookArborist = "Arborist Manual";
        for (final ExtraTreeSpecies species : values()) {
            species.preInit();
        }
        ExtraTreeSpecies.OrchardApple.finished();
        ExtraTreeSpecies.SweetCrabapple.finished();
        ExtraTreeSpecies.FloweringCrabapple.finished();
        ExtraTreeSpecies.PrairieCrabapple.finished();
        final ExtraTreeSpecies[] arr$2;
        final ExtraTreeSpecies[] pruneSpecies = arr$2 = new ExtraTreeSpecies[]{ExtraTreeSpecies.Blackthorn, ExtraTreeSpecies.CherryPlum, ExtraTreeSpecies.Almond, ExtraTreeSpecies.Apricot, ExtraTreeSpecies.Peach, ExtraTreeSpecies.Nectarine, ExtraTreeSpecies.WildCherry, ExtraTreeSpecies.SourCherry, ExtraTreeSpecies.BlackCherry};
        for (final ExtraTreeSpecies species2 : arr$2) {
            final IAlleleTreeSpecies citrus = (IAlleleTreeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.treePlum");
            species2.setWorldGen(citrus.getGeneratorClasses()[0]);
            species2.saplingType = SaplingType.Fruit;
            species2.finished();
        }
        ExtraTreeSpecies[] arr$3;
        final ExtraTreeSpecies[] citrusSpecies = arr$3 = new ExtraTreeSpecies[]{ExtraTreeSpecies.Orange, ExtraTreeSpecies.Manderin, ExtraTreeSpecies.Satsuma, ExtraTreeSpecies.Tangerine, ExtraTreeSpecies.Lime, ExtraTreeSpecies.KeyLime, ExtraTreeSpecies.FingerLime, ExtraTreeSpecies.Pomelo, ExtraTreeSpecies.Grapefruit, ExtraTreeSpecies.Kumquat, ExtraTreeSpecies.Citron, ExtraTreeSpecies.BuddhaHand};
        for (final ExtraTreeSpecies species3 : arr$3) {
            species3.setLeafType(LeafType.Jungle);
            species3.saplingType = SaplingType.Fruit;
            final IAlleleTreeSpecies citrus2 = (IAlleleTreeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.treeLemon");
            species3.setWorldGen(citrus2.getGeneratorClasses()[0]);
            species3.finished();
        }
        ExtraTreeSpecies.Banana.setLeafType(LeafType.Palm);
        ExtraTreeSpecies.RedBanana.setLeafType(LeafType.Palm);
        ExtraTreeSpecies.Plantain.setLeafType(LeafType.Palm);
        ExtraTreeSpecies.Banana.finished();
        ExtraTreeSpecies.RedBanana.finished();
        ExtraTreeSpecies.Plantain.finished();
        ExtraTreeSpecies.Hemlock.setLeafType(LeafType.Conifer);
        ExtraTreeSpecies.Butternut.finished();
        ExtraTreeSpecies.Butternut.setGirth(2);
        ExtraTreeSpecies.Rowan.finished();
        ExtraTreeSpecies.Hemlock.finished();
        ExtraTreeSpecies.Hemlock.setGirth(2);
        ExtraTreeSpecies.Ash.finished();
        ExtraTreeSpecies.Alder.finished();
        ExtraTreeSpecies.Beech.finished();
        ExtraTreeSpecies.CopperBeech.finished();
        ExtraTreeSpecies.Aspen.finished();
        ExtraTreeSpecies.Yew.setLeafType(LeafType.Conifer);
        ExtraTreeSpecies.Cypress.setLeafType(LeafType.Conifer);
        ExtraTreeSpecies.DouglasFir.setLeafType(LeafType.Conifer);
        ExtraTreeSpecies.Yew.finished();
        ExtraTreeSpecies.Cypress.finished();
        ExtraTreeSpecies.Cypress.saplingType = SaplingType.Poplar;
        ExtraTreeSpecies.DouglasFir.finished();
        ExtraTreeSpecies.DouglasFir.setGirth(2);
        ExtraTreeSpecies.Hazel.finished();
        ExtraTreeSpecies.DwarfHazel.finished();
        ExtraTreeSpecies.DwarfHazel.saplingType = SaplingType.Shrub;
        ExtraTreeSpecies.Sycamore.finished();
        ExtraTreeSpecies.Whitebeam.finished();
        ExtraTreeSpecies.Hawthorn.finished();
        ExtraTreeSpecies.Pecan.finished();
        ExtraTreeSpecies.Fir.setLeafType(LeafType.Conifer);
        ExtraTreeSpecies.Cedar.setLeafType(LeafType.Conifer);
        ExtraTreeSpecies.Sallow.setLeafType(LeafType.Willow);
        ExtraTreeSpecies.Elm.finished();
        ExtraTreeSpecies.Elder.finished();
        ExtraTreeSpecies.Holly.finished();
        ExtraTreeSpecies.Hornbeam.finished();
        ExtraTreeSpecies.Sallow.finished();
        ExtraTreeSpecies.AcornOak.finished();
        ExtraTreeSpecies.AcornOak.setGirth(2);
        ExtraTreeSpecies.Fir.finished();
        ExtraTreeSpecies.Cedar.finished();
        ExtraTreeSpecies.Cedar.setGirth(2);
        ExtraTreeSpecies.RedMaple.setLeafType(LeafType.Maple);
        ExtraTreeSpecies.BalsamFir.setLeafType(LeafType.Conifer);
        ExtraTreeSpecies.LoblollyPine.setLeafType(LeafType.Conifer);
        ExtraTreeSpecies.Olive.finished();
        ExtraTreeSpecies.RedMaple.finished();
        ExtraTreeSpecies.BalsamFir.finished();
        ExtraTreeSpecies.LoblollyPine.finished();
        ExtraTreeSpecies.Sweetgum.finished();
        ExtraTreeSpecies.Locust.finished();
        ExtraTreeSpecies.Pear.finished();
        ExtraTreeSpecies.OsangeOsange.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.OldFustic.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Brazilwood.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Logwood.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Rosewood.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Purpleheart.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.OsangeOsange.finished();
        ExtraTreeSpecies.OldFustic.finished();
        ExtraTreeSpecies.Brazilwood.finished();
        ExtraTreeSpecies.Logwood.finished();
        ExtraTreeSpecies.Rosewood.finished();
        ExtraTreeSpecies.Purpleheart.finished();
        ExtraTreeSpecies.Gingko.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Brazilnut.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.RoseGum.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.SwampGum.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Coffee.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.MonkeyPuzzle.setLeafType(LeafType.Conifer);
        ExtraTreeSpecies.RainbowGum.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Iroko.finished();
        ExtraTreeSpecies.Gingko.finished();
        ExtraTreeSpecies.Brazilnut.finished();
        ExtraTreeSpecies.RoseGum.finished();
        ExtraTreeSpecies.SwampGum.finished();
        ExtraTreeSpecies.SwampGum.setGirth(2);
        ExtraTreeSpecies.Box.finished();
        ExtraTreeSpecies.Clove.finished();
        ExtraTreeSpecies.Coffee.finished();
        ExtraTreeSpecies.MonkeyPuzzle.finished();
        ExtraTreeSpecies.MonkeyPuzzle.setGirth(2);
        ExtraTreeSpecies.RainbowGum.finished();
        ExtraTreeSpecies.PinkIvory.finished();
        ExtraTreeSpecies.Juniper.setLeafType(LeafType.Conifer);
        ExtraTreeSpecies.Blackcurrant.saplingType = SaplingType.Shrub;
        ExtraTreeSpecies.Redcurrant.saplingType = SaplingType.Shrub;
        ExtraTreeSpecies.Blackberry.saplingType = SaplingType.Shrub;
        ExtraTreeSpecies.Raspberry.saplingType = SaplingType.Shrub;
        ExtraTreeSpecies.Blueberry.saplingType = SaplingType.Shrub;
        ExtraTreeSpecies.Cranberry.saplingType = SaplingType.Shrub;
        ExtraTreeSpecies.Juniper.saplingType = SaplingType.Shrub;
        ExtraTreeSpecies.Gooseberry.saplingType = SaplingType.Shrub;
        ExtraTreeSpecies.GoldenRaspberry.saplingType = SaplingType.Shrub;
        arr$3 = values();
        for (final ExtraTreeSpecies species3 : arr$3) {
            final String scientific = species3.branchName.substring(0, 1).toUpperCase() + species3.branchName.substring(1).toLowerCase();
            final String uid = "trees." + species3.branchName.toLowerCase();
            IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
            if (branch == null) {
                branch = AlleleManager.alleleRegistry.createAndRegisterClassification(IClassification.EnumClassLevel.GENUS, uid, scientific);
            }
            (species3.branch = branch).addMemberSpecies((IAlleleSpecies) species3);
        }
        ExtraTreeSpecies.Cinnamon.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Coconut.setLeafType(LeafType.Palm);
        ExtraTreeSpecies.Cashew.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Avacado.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Nutmeg.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Allspice.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Chilli.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.StarAnise.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Mango.setLeafType(LeafType.Jungle);
        ExtraTreeSpecies.Starfruit.setLeafType(LeafType.Jungle);
        final IFruitFamily familyPrune = AlleleManager.alleleRegistry.getFruitFamily("forestry.prunes");
        final IFruitFamily familyPome = AlleleManager.alleleRegistry.getFruitFamily("forestry.pomes");
        final IFruitFamily familyJungle = AlleleManager.alleleRegistry.getFruitFamily("forestry.jungle");
        final IFruitFamily familyNuts = AlleleManager.alleleRegistry.getFruitFamily("forestry.nuts");
        final IFruitFamily familyBerry = (IFruitFamily) ExtraTreeFruitFamily.Berry;
        final IFruitFamily familyCitrus = (IFruitFamily) ExtraTreeFruitFamily.Citrus;
        ExtraTreeSpecies.OrchardApple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.SweetCrabapple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.FloweringCrabapple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.PrairieCrabapple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Blackthorn.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.CherryPlum.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Peach.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Nectarine.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Apricot.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Almond.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.WildCherry.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.SourCherry.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.BlackCherry.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Orange.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Manderin.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Satsuma.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Tangerine.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Lime.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.KeyLime.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.FingerLime.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Pomelo.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Grapefruit.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Kumquat.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Citron.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.BuddhaHand.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Banana.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.RedBanana.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Plantain.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Butternut.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Rowan.addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Ash.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Alder.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Beech.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.CopperBeech.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Aspen.addFamily(familyPome).addFamily(familyNuts);
        ExtraTreeSpecies.Hazel.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Sycamore.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Whitebeam.addFamily(familyPome).addFamily(familyPrune);
        ExtraTreeSpecies.Hawthorn.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Pecan.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Elm.addFamily(familyPome).addFamily(familyPrune);
        ExtraTreeSpecies.Elder.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus).addFamily(familyBerry);
        ExtraTreeSpecies.Holly.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Hornbeam.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Sallow.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.AcornOak.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.Olive.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.RedMaple.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.Sweetgum.addFamily(familyNuts);
        ExtraTreeSpecies.Locust.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Pear.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus);
        ExtraTreeSpecies.OsangeOsange.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        ExtraTreeSpecies.OldFustic.addFamily(familyJungle);
        ExtraTreeSpecies.Brazilwood.addFamily(familyJungle);
        ExtraTreeSpecies.Logwood.addFamily(familyJungle);
        ExtraTreeSpecies.Rosewood.addFamily(familyJungle);
        ExtraTreeSpecies.Purpleheart.addFamily(familyJungle);
        ExtraTreeSpecies.Iroko.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Gingko.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Brazilnut.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.RoseGum.addFamily(familyJungle);
        ExtraTreeSpecies.SwampGum.addFamily(familyJungle);
        ExtraTreeSpecies.Box.addFamily(familyPome).addFamily(familyPrune).addFamily(familyNuts).addFamily(familyCitrus);
        ExtraTreeSpecies.Clove.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Coffee.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.MonkeyPuzzle.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.RainbowGum.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.PinkIvory.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Blackcurrant.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Redcurrant.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Blackberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Raspberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Blueberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Cranberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Juniper.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Gooseberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.GoldenRaspberry.addFamily(familyPrune).addFamily(familyNuts).addFamily(familyBerry);
        ExtraTreeSpecies.Cinnamon.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Coconut.addFamily(familyJungle);
        ExtraTreeSpecies.Cashew.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Avacado.addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        ExtraTreeSpecies.Nutmeg.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Allspice.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Chilli.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.StarAnise.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.Mango.addFamily(familyPome).addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        ExtraTreeSpecies.Starfruit.addFamily(familyPrune).addFamily(familyCitrus).addFamily(familyJungle);
        ExtraTreeSpecies.Candlenut.addFamily(familyNuts).addFamily(familyJungle);
        ExtraTreeSpecies.DwarfHazel.addFamily(familyPrune).addFamily(familyNuts);
        ExtraTreeSpecies.OrchardApple.setYield(ForestryAllele.Yield.Higher).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.SweetCrabapple.setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.FloweringCrabapple.setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.PrairieCrabapple.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Blackthorn.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.CherryPlum.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Peach.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Nectarine.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Apricot.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Almond.setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.WildCherry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.SourCherry.setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.BlackCherry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Orange.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Manderin.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Satsuma.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Tangerine.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Average).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Lime.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.KeyLime.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.FingerLime.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Pomelo.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Grapefruit.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Kumquat.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Citron.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.BuddhaHand.setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Banana.setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.RedBanana.setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Plantain.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower);
        ExtraTreeSpecies.Butternut.setHeight(ForestryAllele.TreeHeight.Smaller).setYield(ForestryAllele.Yield.Low);
        ExtraTreeSpecies.Rowan.setHeight(ForestryAllele.TreeHeight.Larger).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Hemlock.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low).setMaturation(ForestryAllele.Maturation.Slower);
        ExtraTreeSpecies.Ash.setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Alder.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Beech.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower);
        ExtraTreeSpecies.CopperBeech.setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.Aspen.setFertility(ForestryAllele.Saplings.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Yew.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Cypress.setHeight(ForestryAllele.TreeHeight.Larger).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.DouglasFir.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setMaturation(ForestryAllele.Maturation.Slower);
        ExtraTreeSpecies.Hazel.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Sycamore.setFertility(ForestryAllele.Saplings.Lowest).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Whitebeam.setHeight(ForestryAllele.TreeHeight.Smaller);
        ExtraTreeSpecies.Hawthorn.setHeight(ForestryAllele.TreeHeight.Average);
        ExtraTreeSpecies.Pecan.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.Elm.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Elder.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Holly.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Hornbeam.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.Sallow.setHeight(ForestryAllele.TreeHeight.Large).setFertility(ForestryAllele.Saplings.Low);
        ExtraTreeSpecies.AcornOak.setHeight(ForestryAllele.TreeHeight.Large).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Fir.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.Cedar.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slower);
        ExtraTreeSpecies.Olive.setYield(ForestryAllele.Yield.Average);
        ExtraTreeSpecies.RedMaple.setFertility(ForestryAllele.Saplings.Average).setSappiness(ForestryAllele.Sappiness.High);
        ExtraTreeSpecies.BalsamFir.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.LoblollyPine.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Slow);
        ExtraTreeSpecies.Sweetgum.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.High).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Average);
        ExtraTreeSpecies.Locust.setHeight(ForestryAllele.TreeHeight.Smallest);
        ExtraTreeSpecies.Pear.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.OsangeOsange.setYield(ForestryAllele.Yield.Lower);
        ExtraTreeSpecies.OldFustic.setHeight(ForestryAllele.TreeHeight.Smaller).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Brazilwood.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Logwood.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Rosewood.setHeight(ForestryAllele.TreeHeight.Average).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Purpleheart.setHeight(ForestryAllele.TreeHeight.Large).setSappiness(ForestryAllele.Sappiness.Lower);
        ExtraTreeSpecies.Iroko.setHeight(ForestryAllele.TreeHeight.Average).setFertility(ForestryAllele.Saplings.Low);
        ExtraTreeSpecies.Gingko.setHeight(ForestryAllele.TreeHeight.Large).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Brazilnut.setHeight(ForestryAllele.TreeHeight.Larger).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.RoseGum.setHeight(ForestryAllele.TreeHeight.Largest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Slowest);
        ExtraTreeSpecies.SwampGum.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setMaturation(ForestryAllele.Maturation.Slower);
        ExtraTreeSpecies.Box.setHeight(ForestryAllele.TreeHeight.Smaller).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Clove.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Coffee.setHeight(ForestryAllele.TreeHeight.Large).setYield(ForestryAllele.Yield.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.MonkeyPuzzle.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.RainbowGum.setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Lower);
        ExtraTreeSpecies.PinkIvory.setHeight(ForestryAllele.TreeHeight.Smallest);
        ExtraTreeSpecies.Blackcurrant.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Redcurrant.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Blackberry.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Raspberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Blueberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Cranberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Average).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Juniper.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.Gooseberry.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.High).setYield(ForestryAllele.Yield.High).setMaturation(ForestryAllele.Maturation.Faster);
        ExtraTreeSpecies.GoldenRaspberry.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fastest);
        ExtraTreeSpecies.Cinnamon.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Coconut.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Cashew.setYield(ForestryAllele.Yield.Low);
        ExtraTreeSpecies.Avacado.setHeight(ForestryAllele.TreeHeight.Smallest).setYield(ForestryAllele.Yield.Average);
        ExtraTreeSpecies.Nutmeg.setHeight(ForestryAllele.TreeHeight.Smaller).setYield(ForestryAllele.Yield.High).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.Allspice.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.High);
        ExtraTreeSpecies.Chilli.setHeight(ForestryAllele.TreeHeight.Smaller).setYield(ForestryAllele.Yield.Higher).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.StarAnise.setHeight(ForestryAllele.TreeHeight.Average).setYield(ForestryAllele.Yield.High);
        ExtraTreeSpecies.Mango.setHeight(ForestryAllele.TreeHeight.Smaller).setFertility(ForestryAllele.Saplings.Low).setYield(ForestryAllele.Yield.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Starfruit.setYield(ForestryAllele.Yield.Average).setMaturation(ForestryAllele.Maturation.Fast);
        ExtraTreeSpecies.Candlenut.setHeight(ForestryAllele.TreeHeight.Smallest).setFertility(ForestryAllele.Saplings.Lowest).setYield(ForestryAllele.Yield.Low).setSappiness(ForestryAllele.Sappiness.Low);
        ExtraTreeSpecies.DwarfHazel.setFertility(ForestryAllele.Saplings.Average).setSappiness(ForestryAllele.Sappiness.Lower).setMaturation(ForestryAllele.Maturation.Faster);
    }

    static final ItemStack getEBXLStack(final String name) {
        try {
            final Class elements = Class.forName("extrabiomes.lib.Element");
            final Method getElementMethod = elements.getMethod("valueOf", String.class);
            final Method getItemStack = elements.getMethod("get", (Class[]) new Class[0]);
            final Object element = getElementMethod.invoke(null, "SAPLING_AUTUMN_YELLOW");
            return (ItemStack) getItemStack.invoke(element, new Object[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ExtraTreeSpecies addFamily(final IFruitFamily family) {
        this.families.add(family);
        return this;
    }

    private void setWorldGen(final Class<? extends WorldGenerator> gen) {
        this.gen = gen;
    }

    private void setGirth(final int i) {
        this.template[EnumTreeChromosome.GIRTH.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.i" + i + "d");
    }

    public void preInit() {
        (this.template = Binnie.Genetics.getTreeRoot().getDefaultTemplate())[0] = (IAllele) this;
        if (this.fruit != null) {
            this.template[EnumTreeChromosome.FRUITS.ordinal()] = (IAllele) this.fruit;
        }
        this.template[EnumTreeChromosome.GIRTH.ordinal()] = AlleleManager.alleleRegistry.getAllele("forestry.i" + this.girth + "d");
        final IClassification clas = AlleleManager.alleleRegistry.getClassification("trees." + this.branch);
        if (clas != null) {
            clas.addMemberSpecies((IAlleleSpecies) this);
            this.branch = clas;
        }
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

    @Override
    public ITreeGenerator getGenerator() {
        //TODO: UPD TO Forestry4
        return null;
    }

    @Override
    public int getLeafColour(boolean pollinated) {
        //TODO: UPD TO Forestry4
        return 0;
    }

    @Override
    public IIcon getLeafIcon(boolean pollinated, boolean fancy) {
        //TODO: UPD TO Forestry4
        return null;
    }

    public boolean isDominant() {
        return true;
    }

    public EnumPlantType getPlantType() {
        return EnumPlantType.Plains;
    }

    public WorldGenerator getGenerator(final ITree tree, final World world, final int x, final int y, final int z) {
        if (this.gen != null) {
            try {
                return (WorldGenerator) this.gen.getConstructor(ITree.class).newInstance(tree);
            } catch (Exception ex) {
            }
        }
        return new WorldGenDefault(tree);
    }

    public Class<? extends WorldGenerator>[] getGeneratorClasses() {
        return null;
    }

    void setLeafType(final LeafType type) {
        this.leafType = type;
        if (this.leafType == LeafType.Conifer) {
            this.saplingType = SaplingType.Conifer;
        }
        if (this.leafType == LeafType.Jungle) {
            this.saplingType = SaplingType.Jungle;
        }
        if (this.leafType == LeafType.Palm) {
            this.saplingType = SaplingType.Palm;
        }
    }

    public IAllele[] getTemplate() {
        return this.template;
    }

    public ArrayList<IFruitFamily> getSuitableFruit() {
        return this.families;
    }

    public ILogType getLog() {
        return this.wood;
    }

    public ExtraTreeSpecies setHeight(final ForestryAllele.TreeHeight height) {
        final IAllele allele = height.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.HEIGHT.ordinal()] = allele;
        }
        return this;
    }

    public ExtraTreeSpecies setSappiness(final ForestryAllele.Sappiness height) {
        final IAllele allele = height.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.SAPPINESS.ordinal()] = allele;
        }
        return this;
    }

    public ExtraTreeSpecies setMaturation(final ForestryAllele.Maturation height) {
        final IAllele allele = height.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.MATURATION.ordinal()] = allele;
        }
        return this;
    }

    public ExtraTreeSpecies setYield(final ForestryAllele.Yield height) {
        final IAllele allele = height.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.YIELD.ordinal()] = allele;
        }
        return this;
    }

    public ExtraTreeSpecies setFertility(final ForestryAllele.Saplings height) {
        final IAllele allele = height.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.FERTILITY.ordinal()] = allele;
        }
        return this;
    }

    public void setGrowthConditions(final ForestryAllele.Growth growth) {
        final IAllele allele = growth.getAllele();
        if (allele != null) {
            this.template[EnumTreeChromosome.GROWTH.ordinal()] = allele;
        }
    }

    public void finished() {
    }

    public int getLeafColour(final ITree tree) {
        return this.color;
    }

    public short getLeafIconIndex(final ITree tree, final boolean fancy) {
        if (!fancy) {
            return this.leafType.plainUID;
        }
        if (tree.getMate() != null) {
            return this.leafType.changedUID;
        }
        return this.leafType.fancyUID;
    }

    public int getIconColour(final int renderPass) {
        return (renderPass == 0) ? this.color : 10451021;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getGermlingIcon(final EnumGermlingType type, final int renderPass) {
        if (type == EnumGermlingType.POLLEN) {
            return Mods.Forestry.item("pollen").getIconFromDamageForRenderPass(0, renderPass);
        }
        return (renderPass == 0) ? this.saplingType.icon[1] : this.saplingType.icon[0];
    }

    public IIconProvider getIconProvider() {
        return (IIconProvider) this;
    }

    public IIcon getIcon(final short texUID) {
        return null;
    }

    public void registerIcons(final IIconRegister register) {
        for (final SaplingType type : SaplingType.values()) {
            (type.icon = new IIcon[2])[0] = ExtraTrees.proxy.getIcon(register, "saplings/" + type.toString().toLowerCase() + ".trunk");
            type.icon[1] = ExtraTrees.proxy.getIcon(register, "saplings/" + type.toString().toLowerCase() + ".leaves");
        }
    }

    public ITreeRoot getRoot() {
        return Binnie.Genetics.getTreeRoot();
    }

    public float getResearchSuitability(final ItemStack itemstack) {
        if (itemstack == null) {
            return 0.0f;
        }
        if (this.template[EnumTreeChromosome.FRUITS.ordinal()] instanceof ExtraTreeFruitGene) {
            final ExtraTreeFruitGene fruit = (ExtraTreeFruitGene) this.template[EnumTreeChromosome.FRUITS.ordinal()];
            for (final ItemStack stack : fruit.products.keySet()) {
                if (stack.isItemEqual(itemstack)) {
                    return 1.0f;
                }
            }
        }
        if (itemstack.getItem() == Mods.Forestry.item("honeyDrop")) {
            return 0.5f;
        }
        if (itemstack.getItem() == Mods.Forestry.item("honeydew")) {
            return 0.7f;
        }
        if (itemstack.getItem() == Mods.Forestry.item("beeComb")) {
            return 0.4f;
        }
        if (AlleleManager.alleleRegistry.isIndividual(itemstack)) {
            return 1.0f;
        }
        for (final Map.Entry<ItemStack, Float> entry : this.getRoot().getResearchCatalysts().entrySet()) {
            if (entry.getKey().isItemEqual(itemstack)) {
                return entry.getValue();
            }
        }
        return 0.0f;
    }

    public ItemStack[] getResearchBounty(final World world, final GameProfile researcher, final IIndividual individual, final int bountyLevel) {
        final ArrayList<ItemStack> bounty = new ArrayList<ItemStack>();
        ItemStack research = null;
        if (world.rand.nextFloat() < 10.0f / bountyLevel) {
            final Collection<? extends IMutation> combinations = (Collection<? extends IMutation>) this.getRoot().getCombinations((IAllele) this);
            if (combinations.size() > 0) {
                final IMutation[] candidates = combinations.toArray(new IMutation[0]);
                research = AlleleManager.alleleRegistry.getMutationNoteStack(researcher, candidates[world.rand.nextInt(candidates.length)]);
            }
        }
        if (research != null) {
            bounty.add(research);
        }
        if (this.template[EnumTreeChromosome.FRUITS.ordinal()] instanceof ExtraTreeFruitGene) {
            final ExtraTreeFruitGene fruit = (ExtraTreeFruitGene) this.template[EnumTreeChromosome.FRUITS.ordinal()];
            for (final ItemStack stack : fruit.products.keySet()) {
                final ItemStack stack2 = stack.copy();
                stack2.stackSize = world.rand.nextInt((int) (bountyLevel / 2.0f)) + 1;
                bounty.add(stack2);
            }
        }
        return bounty.toArray(new ItemStack[0]);
    }

    @SideOnly(Side.CLIENT)
    public int getGermlingColour(final EnumGermlingType type, final int renderPass) {
        if (type == EnumGermlingType.SAPLING) {
            return (renderPass == 0) ? this.getLeafColour(null) : ((this.getLog() == null) ? 16777215 : this.getLog().getColour());
        }
        return this.getLeafColour(null);
    }

    public int getComplexity() {
        return 1 + this.getGeneticAdvancement((IAllele) this, new ArrayList<IAllele>());
    }

    private int getGeneticAdvancement(final IAllele species, final ArrayList<IAllele> exclude) {
        final int own = 1;
        int highest = 0;
        exclude.add(species);
        for (final IMutation mutation : this.getRoot().getPaths(species, (IChromosomeType) EnumBeeChromosome.SPECIES)) {
            if (!exclude.contains(mutation.getAllele0())) {
                final int otherAdvance = this.getGeneticAdvancement(mutation.getAllele0(), exclude);
                if (otherAdvance > highest) {
                    highest = otherAdvance;
                }
            }
            if (!exclude.contains(mutation.getAllele1())) {
                final int otherAdvance = this.getGeneticAdvancement(mutation.getAllele1(), exclude);
                if (otherAdvance <= highest) {
                    continue;
                }
                highest = otherAdvance;
            }
        }
        return own + ((highest < 0) ? 0 : highest);
    }

    public ItemStack[] getLogStacks() {
        if (this.wood == null) {
            return new ItemStack[0];
        }
        return new ItemStack[]{this.wood.getItemStack()};
    }

    public String getUnlocalizedName() {
        return "extratrees.species." + this.toString().toLowerCase() + ".name";
    }

    public enum LeafType {
        Normal((short) 10, (short) 11, (short) 12, "Decidious"),
        Conifer((short) 15, (short) 16, (short) 17, "Conifer"),
        Jungle((short) 20, (short) 21, (short) 22, "Jungle"),
        Willow((short) 25, (short) 26, (short) 27, "Willow"),
        Maple((short) 30, (short) 31, (short) 32, "Maple"),
        Palm((short) 35, (short) 36, (short) 37, "Palm");

        public final short fancyUID;
        public final short plainUID;
        public final short changedUID;
        public final String descript;

        private LeafType(final short fancyUID, final short plainUID, final short changedUID, final String descript) {
            this.fancyUID = fancyUID;
            this.plainUID = plainUID;
            this.changedUID = changedUID;
            this.descript = descript;
        }
    }

    public enum SaplingType {
        Default,
        Jungle,
        Conifer,
        Fruit,
        Poplar,
        Palm,
        Shrub;

        IIcon[] icon;
    }
}
