package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.api.*;
import binnie.botany.core.BotanyCore;
import binnie.core.genetics.ForestryAllele;
import com.mojang.authlib.GameProfile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.core.IIconProvider;
import forestry.api.genetics.*;
import forestry.api.genetics.IClassification.EnumClassLevel;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public enum FlowerSpecies implements IAlleleFlowerSpecies {
    Dandelion("Dandelion", "taraxacum", "officinale", EnumFlowerType.Dandelion, EnumFlowerColor.Yellow),
    Poppy("Poppy", "papaver", "rhoeas", EnumFlowerType.Poppy, EnumFlowerColor.Red),
    Orchid("Orchid", "vanda", "coerulea", EnumFlowerType.Orchid, EnumFlowerColor.DeepSkyBlue),
    Allium("Allium", "allium", "giganteum", EnumFlowerType.Allium, EnumFlowerColor.MediumPurple),
    Bluet("Bluet", "houstonia", "caerulea", EnumFlowerType.Bluet, EnumFlowerColor.Lavender, EnumFlowerColor.Khaki),
    Tulip("Tulip", "tulipa", "agenensis", EnumFlowerType.Tulip, EnumFlowerColor.Violet),
    Daisy("Daisy", "leucanthemum", "vulgare", EnumFlowerType.Daisy, EnumFlowerColor.White, EnumFlowerColor.Yellow),
    Cornflower("Cornflower", "centaurea", "cyanus", EnumFlowerType.Cornflower, EnumFlowerColor.SkyBlue),
    Pansy("Pansy", "viola", "tricolor", EnumFlowerType.Pansy, EnumFlowerColor.Pink, EnumFlowerColor.Purple),
    Iris("Iris", "iris", "germanica", EnumFlowerType.Iris, EnumFlowerColor.LightGray, EnumFlowerColor.Purple),
    Lavender("Lavender", "Lavandula", "angustifolia", EnumFlowerType.Lavender, EnumFlowerColor.MediumOrchid),
    Viola("Viola", "viola", "odorata", EnumFlowerType.Viola, EnumFlowerColor.MediumPurple, EnumFlowerColor.SlateBlue),
    Daffodil("Daffodil", "narcissus", "pseudonarcissus", EnumFlowerType.Daffodil, EnumFlowerColor.Yellow, EnumFlowerColor.Gold),
    Dahlia("Dahlia", "dahlia", "variabilis", EnumFlowerType.Dahlia, EnumFlowerColor.HotPink, EnumFlowerColor.DeepPink),
    Peony("Peony", "paeonia", "suffruticosa", EnumFlowerType.Peony, EnumFlowerColor.Thistle),
    Rose("Rose", "rosa", "rubiginosa", EnumFlowerType.Rose, EnumFlowerColor.Red),
    Lilac("Lilac", "syringa", "vulgaris", EnumFlowerType.Lilac, EnumFlowerColor.Plum),
    Hydrangea("Hydrangea", "hydrangea", "macrophylla", EnumFlowerType.Hydrangea, EnumFlowerColor.DeepSkyBlue),
    Foxglove("Foxglove", "digitalis", "purpurea", EnumFlowerType.Foxglove, EnumFlowerColor.HotPink),
    Zinnia("Zinnia", "zinnia", "elegans", EnumFlowerType.Zinnia, EnumFlowerColor.MediumVioletRed, EnumFlowerColor.Yellow),
    Chrysanthemum("Chrysanthemum", "chrysanthemum", "ï¿½ grandiflorum", EnumFlowerType.Mums, EnumFlowerColor.Violet),
    Marigold("Marigold", "calendula", "officinalis", EnumFlowerType.Marigold, EnumFlowerColor.Gold, EnumFlowerColor.DarkOrange),
    Geranium("Geranium", "geranium", "maderense", EnumFlowerType.Geranium, EnumFlowerColor.DeepPink),
    Azalea("Azalea", "rhododendrons", "aurigeranum", EnumFlowerType.Azalea, EnumFlowerColor.HotPink),
    Primrose("Primrose", "primula", "vulgaris", EnumFlowerType.Primrose, EnumFlowerColor.Red, EnumFlowerColor.Gold),
    Aster("Aster", "aster", "amellus", EnumFlowerType.Aster, EnumFlowerColor.MediumPurple, EnumFlowerColor.Goldenrod),
    Carnation("Carnation", "dianthus", "caryophyllus", EnumFlowerType.Carnation, EnumFlowerColor.Crimson, EnumFlowerColor.White),
    Lily("Lily", "lilium", "auratum", EnumFlowerType.Lily, EnumFlowerColor.Pink, EnumFlowerColor.Gold),
    Yarrow("Yarrow", "achillea", "millefolium", EnumFlowerType.Yarrow, EnumFlowerColor.Yellow),
    Petunia("Petunia", "petunia", "ï¿½ atkinsiana", EnumFlowerType.Petunia, EnumFlowerColor.MediumVioletRed, EnumFlowerColor.Thistle),
    Agapanthus("Agapanthus", "agapanthus", "praecox", EnumFlowerType.Agapanthus, EnumFlowerColor.DeepSkyBlue),
    Fuchsia("Fuchsia", "fuchsia", "magellanica", EnumFlowerType.Fuchsia, EnumFlowerColor.DeepPink, EnumFlowerColor.MediumOrchid),
    Dianthus("Dianthus", "dianthus", "barbatus", EnumFlowerType.Dianthus, EnumFlowerColor.Crimson, EnumFlowerColor.HotPink),
    Forget("Forget-me-nots", "myosotis", "arvensis", EnumFlowerType.Forget, EnumFlowerColor.LightSteelBlue),
    Anemone("Anemone", "anemone", "coronaria", EnumFlowerType.Anemone, EnumFlowerColor.Red, EnumFlowerColor.MistyRose),
    Aquilegia("Aquilegia", "aquilegia", "vulgaris", EnumFlowerType.Aquilegia, EnumFlowerColor.SlateBlue, EnumFlowerColor.Thistle),
    Edelweiss("Edelweiss", "leontopodium", "alpinum", EnumFlowerType.Edelweiss, EnumFlowerColor.White, EnumFlowerColor.Khaki),
    Scabious("Scabious", "scabiosa", "columbaria", EnumFlowerType.Scabious, EnumFlowerColor.RoyalBlue),
    Coneflower("Coneflower", "echinacea", "purpurea", EnumFlowerType.Coneflower, EnumFlowerColor.Violet, EnumFlowerColor.DarkOrange),
    Gaillardia("Gaillardia", "gaillardia", "aristata", EnumFlowerType.Gaillardia, EnumFlowerColor.DarkOrange, EnumFlowerColor.Yellow),
    Auricula("Auricula", "primula", "auricula", EnumFlowerType.Auricula, EnumFlowerColor.Red, EnumFlowerColor.Yellow),
    Camellia("Camellia", "camellia", "japonica", EnumFlowerType.Camellia, EnumFlowerColor.Crimson),
    Goldenrod("Goldenrod", "solidago", "canadensis", EnumFlowerType.Goldenrod, EnumFlowerColor.Gold),
    Althea("Althea", "althaea", "officinalis", EnumFlowerType.Althea, EnumFlowerColor.Thistle, EnumFlowerColor.MediumOrchid),
    Penstemon("Penstemon", "penstemon", "digitalis", EnumFlowerType.Penstemon, EnumFlowerColor.MediumOrchid, EnumFlowerColor.Thistle),
    Delphinium("Delphinium", "delphinium", "staphisagria", EnumFlowerType.Delphinium, EnumFlowerColor.DarkSlateBlue),
    Hollyhock("Hollyhock", "Alcea", "rosea", EnumFlowerType.Hollyhock, EnumFlowerColor.Black, EnumFlowerColor.Gold);

    EnumFlowerColor stemColor;
    ForestryAllele.Fertility fert;
    ForestryAllele.Lifespan life;
    ForestryAllele.Sappiness sap;
    IFlowerType type;
    String name;
    String binomial;
    String branchName;
    EnumFlowerColor primaryColor;
    EnumFlowerColor secondaryColor;
    EnumTemperature temperature;
    EnumAcidity pH;
    EnumMoisture moisture;
    EnumTolerance tempTolerance;
    EnumTolerance pHTolerance;
    EnumTolerance moistureTolerance;
    List variantTemplates;
    IClassification branch;

    public static void setupVariants() {
        Dandelion.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Lower);
        Poppy.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Shorter, ForestryAllele.Sappiness.Average);
        Orchid.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Low);
        Allium.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
        Bluet.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Lower);
        Tulip.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Average);
        Daisy.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
        Cornflower.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Shorter, ForestryAllele.Sappiness.Low);
        Pansy.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Average);
        Iris.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Average);
        Lavender.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
        Viola.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Average);
        Daffodil.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Elongated, ForestryAllele.Sappiness.Average);
        Aster.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Higher);
        Lilac.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Longer, ForestryAllele.Sappiness.Average);
        Rose.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Longer, ForestryAllele.Sappiness.High);
        Peony.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Average);
        Marigold.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Shorter, ForestryAllele.Sappiness.Average);
        Hydrangea.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Longer, ForestryAllele.Sappiness.High);
        Foxglove.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Low);
        Dahlia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Average);
        Chrysanthemum.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.High);
        Carnation.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.High);
        Zinnia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shorter, ForestryAllele.Sappiness.Average);
        Primrose.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Average);
        Azalea.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Average);
        Geranium.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Low);
        Lily.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Low);
        Yarrow.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
        Petunia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shorter, ForestryAllele.Sappiness.Average);
        Agapanthus.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
        Fuchsia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Average);
        Dianthus.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Short, ForestryAllele.Sappiness.High);
        Forget.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Short, ForestryAllele.Sappiness.Lower);
        Anemone.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Low);
        Aquilegia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Average);
        Edelweiss.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Lowest);
        Scabious.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Shortened, ForestryAllele.Sappiness.Low);
        Coneflower.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Higher);
        Gaillardia.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Higher);
        Auricula.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Elongated, ForestryAllele.Sappiness.High);
        Camellia.setTraits(ForestryAllele.Fertility.Normal, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.High);
        Goldenrod.setTraits(ForestryAllele.Fertility.High, ForestryAllele.Lifespan.Normal, ForestryAllele.Sappiness.Higher);
        Althea.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Elongated, ForestryAllele.Sappiness.High);
        Penstemon.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.Low);
        Delphinium.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Longer, ForestryAllele.Sappiness.Low);
        Hollyhock.setTraits(ForestryAllele.Fertility.Low, ForestryAllele.Lifespan.Long, ForestryAllele.Sappiness.High);
        Dandelion.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Dandelion.setMoisture(EnumMoisture.Normal, EnumTolerance.BOTH_1);
        Poppy.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Poppy.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Poppy.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_2);
        Orchid.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
        Orchid.setMoisture(EnumMoisture.Normal, EnumTolerance.BOTH_1);
        Allium.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
        Allium.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Bluet.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Bluet.setMoisture(EnumMoisture.Damp, EnumTolerance.NONE);
        Tulip.setMoisture(EnumMoisture.Normal, EnumTolerance.BOTH_1);
        Daisy.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Daisy.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Daisy.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_2);
        Cornflower.setMutation(Dandelion, Tulip, 10);
        Cornflower.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Pansy.setMutation(Tulip, Viola, 5);
        Pansy.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
        Pansy.setTemperature(EnumTemperature.WARM, EnumTolerance.DOWN_1);
        Iris.setMutation(Orchid, Viola, 10);
        Iris.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
        Iris.setTemperature(EnumTemperature.WARM, EnumTolerance.DOWN_1);
        Lavender.setMutation(Allium, Viola, 10);
        Lavender.setPH(EnumAcidity.Neutral, EnumTolerance.UP_1);
        Lavender.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Lavender.setTemperature(EnumTemperature.WARM, EnumTolerance.DOWN_1);
        Viola.setMutation(Orchid, Poppy, 15);
        Viola.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
        Viola.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Daffodil.setMutation(Dandelion, Poppy, 10);
        Daffodil.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Aster.setMutation(Daisy, Tulip, 10);
        Aster.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Lilac.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
        Rose.setPH(EnumAcidity.Acid, EnumTolerance.UP_1);
        Peony.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
        Peony.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Marigold.setMutation(Daisy, Dandelion, 10);
        Marigold.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Marigold.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Marigold.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_2);
        Hydrangea.setMutation(Peony, Bluet, 10);
        Hydrangea.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Hydrangea.setMoisture(EnumMoisture.Damp, EnumTolerance.NONE);
        Foxglove.setMutation(Lilac, Zinnia, 5);
        Foxglove.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Foxglove.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Dahlia.setMutation(Daisy, Allium, 15);
        Dahlia.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Dahlia.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Dahlia.setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2);
        Chrysanthemum.setMutation(Geranium, Rose, 10);
        Chrysanthemum.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Chrysanthemum.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Carnation.setMutation(Dianthus, Rose, 5);
        Carnation.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
        Zinnia.setMutation(Dahlia, Marigold, 5);
        Zinnia.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Zinnia.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Zinnia.setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2);
        Primrose.setMutation(Chrysanthemum, Auricula, 5);
        Primrose.setPH(EnumAcidity.Acid, EnumTolerance.UP_1);
        Primrose.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Azalea.setMutation(Orchid, Geranium, 5);
        Azalea.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
        Geranium.setMutation(Tulip, Orchid, 15);
        Geranium.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Geranium.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1);
        Lily.setMutation(Tulip, Chrysanthemum, 5);
        Lily.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Lily.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1);
        Yarrow.setMutation(Dandelion, Orchid, 10);
        Yarrow.setPH(EnumAcidity.Acid, EnumTolerance.UP_1);
        Yarrow.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Petunia.setMutation(Tulip, Dahlia, 5);
        Petunia.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Petunia.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Petunia.setTemperature(EnumTemperature.WARM, EnumTolerance.UP_1);
        Agapanthus.setMutation(Allium, Geranium, 5);
        Agapanthus.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Agapanthus.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Agapanthus.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1);
        Fuchsia.setMutation(Foxglove, Dahlia, 5);
        Fuchsia.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Fuchsia.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Fuchsia.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1);
        Dianthus.setMutation(Tulip, Poppy, 15);
        Dianthus.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
        Dianthus.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Dianthus.setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2);
        Forget.setMutation(Orchid, Bluet, 10);
        Forget.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
        Forget.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Forget.setTemperature(EnumTemperature.NORMAL, EnumTolerance.UP_1);
        Anemone.setMutation(Aquilegia, Rose, 5);
        Anemone.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Anemone.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Aquilegia.setMutation(Iris, Poppy, 5);
        Aquilegia.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Edelweiss.setMutation(Peony, Bluet, 5);
        Edelweiss.setPH(EnumAcidity.Alkaline, EnumTolerance.DOWN_1);
        Edelweiss.setMoisture(EnumMoisture.Normal, EnumTolerance.DOWN_1);
        Edelweiss.setTemperature(EnumTemperature.NORMAL, EnumTolerance.DOWN_1);
        Scabious.setMutation(Allium, Cornflower, 5);
        Scabious.setPH(EnumAcidity.Neutral, EnumTolerance.UP_1);
        Scabious.setTemperature(EnumTemperature.NORMAL, EnumTolerance.DOWN_1);
        Coneflower.setMutation(Tulip, Cornflower, 5);
        Coneflower.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Gaillardia.setMutation(Dandelion, Marigold, 5);
        Gaillardia.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Gaillardia.setMoisture(EnumMoisture.Damp, EnumTolerance.DOWN_1);
        Gaillardia.setTemperature(EnumTemperature.NORMAL, EnumTolerance.BOTH_2);
        Auricula.setMutation(Poppy, Geranium, 10);
        Auricula.setPH(EnumAcidity.Acid, EnumTolerance.UP_1);
        Auricula.setMoisture(EnumMoisture.Normal, EnumTolerance.UP_1);
        Camellia.setMutation(Hydrangea, Rose, 5);
        Camellia.setPH(EnumAcidity.Acid, EnumTolerance.NONE);
        Camellia.setMoisture(EnumMoisture.Damp, EnumTolerance.NONE);
        Camellia.setTemperature(EnumTemperature.WARM, EnumTolerance.UP_1);
        Goldenrod.setMutation(Lilac, Marigold, 10);
        Goldenrod.setPH(EnumAcidity.Neutral, EnumTolerance.BOTH_1);
        Althea.setMutation(Hydrangea, Iris, 5);
        Althea.setPH(EnumAcidity.Neutral, EnumTolerance.DOWN_1);
        Althea.setTemperature(EnumTemperature.WARM, EnumTolerance.BOTH_1);
        Penstemon.setMutation(Peony, Lilac, 5);
        Penstemon.setMoisture(EnumMoisture.Dry, EnumTolerance.UP_1);
        Penstemon.setTemperature(EnumTemperature.WARM, EnumTolerance.UP_1);
        Delphinium.setMutation(Lilac, Bluet, 5);
        Delphinium.setMoisture(EnumMoisture.Damp, EnumTolerance.DOWN_1);
        Delphinium.setTemperature(EnumTemperature.NORMAL, EnumTolerance.DOWN_1);
        Hollyhock.setMutation(Delphinium, Lavender, 5);
        Hollyhock.setPH(EnumAcidity.Neutral, EnumTolerance.UP_1);
        Dandelion.setStemColor(EnumFlowerColor.Green);
        Poppy.setStemColor(EnumFlowerColor.Green);
        Orchid.setStemColor(EnumFlowerColor.Green);
        Allium.setStemColor(EnumFlowerColor.Green);
        Bluet.setStemColor(EnumFlowerColor.OliveDrab);
        Tulip.setStemColor(EnumFlowerColor.OliveDrab);
        Daisy.setStemColor(EnumFlowerColor.OliveDrab);
        Cornflower.setStemColor(EnumFlowerColor.OliveDrab);
        Pansy.setStemColor(EnumFlowerColor.SeaGreen);
        Iris.setStemColor(EnumFlowerColor.SeaGreen);
        Lavender.setStemColor(EnumFlowerColor.Green);
        Viola.setStemColor(EnumFlowerColor.OliveDrab);
        Daffodil.setStemColor(EnumFlowerColor.Green);
        Dahlia.setStemColor(EnumFlowerColor.OliveDrab);
        Peony.setStemColor(EnumFlowerColor.DarkGreen);
        Rose.setStemColor(EnumFlowerColor.Green);
        Lilac.setStemColor(EnumFlowerColor.OliveDrab);
        Hydrangea.setStemColor(EnumFlowerColor.DarkGreen);
        Foxglove.setStemColor(EnumFlowerColor.DarkGreen);
        Zinnia.setStemColor(EnumFlowerColor.MediumSeaGreen);
        Chrysanthemum.setStemColor(EnumFlowerColor.MediumSeaGreen);
        Marigold.setStemColor(EnumFlowerColor.Green);
        Geranium.setStemColor(EnumFlowerColor.MediumSeaGreen);
        Azalea.setStemColor(EnumFlowerColor.Green);
        Primrose.setStemColor(EnumFlowerColor.Green);
        Aster.setStemColor(EnumFlowerColor.Green);
        Carnation.setStemColor(EnumFlowerColor.SeaGreen);
        Lily.setStemColor(EnumFlowerColor.Green);
        Yarrow.setStemColor(EnumFlowerColor.DarkOliveGreen);
        Petunia.setStemColor(EnumFlowerColor.Green);
        Agapanthus.setStemColor(EnumFlowerColor.DarkOliveGreen);
        Fuchsia.setStemColor(EnumFlowerColor.SeaGreen);
        Dianthus.setStemColor(EnumFlowerColor.OliveDrab);
        Forget.setStemColor(EnumFlowerColor.Green);
        Anemone.setStemColor(EnumFlowerColor.DarkOliveGreen);
        Aquilegia.setStemColor(EnumFlowerColor.MediumSeaGreen);
        Edelweiss.setStemColor(EnumFlowerColor.DarkOliveGreen);
        Scabious.setStemColor(EnumFlowerColor.OliveDrab);
        Coneflower.setStemColor(EnumFlowerColor.DarkOliveGreen);
        Gaillardia.setStemColor(EnumFlowerColor.OliveDrab);
        Auricula.setStemColor(EnumFlowerColor.DarkOliveGreen);
        Camellia.setStemColor(EnumFlowerColor.DarkOliveGreen);
        Goldenrod.setStemColor(EnumFlowerColor.MediumSeaGreen);
        Althea.setStemColor(EnumFlowerColor.DarkGreen);
        Penstemon.setStemColor(EnumFlowerColor.OliveDrab);
        Delphinium.setStemColor(EnumFlowerColor.DarkSeaGreen);
        Hollyhock.setStemColor(EnumFlowerColor.Green);
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.yellow_flower, 1, 0), Dandelion.getTemplate());
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 0), Poppy.getTemplate());
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 1), Orchid.getTemplate());
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 2), Allium.getTemplate());
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 3), Bluet.getTemplate());
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 7), Tulip.getTemplate());
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 8), Daisy.getTemplate());
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.double_plant, 1, 1), Lilac.getTemplate());
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.double_plant, 1, 4), Rose.getTemplate());
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.double_plant, 1, 5), Peony.getTemplate());
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 6), Tulip.AddVariant(EnumFlowerColor.White));
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 4), Tulip.AddVariant(EnumFlowerColor.Crimson));
        BotanyCore.getFlowerRoot().addConversion(new ItemStack(Blocks.red_flower, 1, 5), Tulip.AddVariant(EnumFlowerColor.DarkOrange));

        for (FlowerSpecies species : values()) {
            String scientific = species.branchName.substring(0, 1).toUpperCase() + species.branchName.substring(1).toLowerCase();
            String uid = "flowers." + species.branchName.toLowerCase();
            IClassification branch = AlleleManager.alleleRegistry.getClassification("genus." + uid);
            if (branch == null) {
                branch = AlleleManager.alleleRegistry.createAndRegisterClassification(EnumClassLevel.GENUS, uid, scientific);
            }

            species.branch = branch;
            species.branch.addMemberSpecies(species);
        }

    }

    private void setStemColor(EnumFlowerColor green) {
        this.stemColor = green;
    }

    private void setTraits(ForestryAllele.Fertility high, ForestryAllele.Lifespan shortened, ForestryAllele.Sappiness lower) {
        this.fert = high;
        this.life = shortened;
        this.sap = lower;
    }

    private void setMutation(FlowerSpecies dandelion2, FlowerSpecies tulip2, int i) {
        BotanyCore.getFlowerRoot().registerMutation(new FlowerMutation(dandelion2, tulip2, this.getTemplate(), i));
    }

    private FlowerSpecies(String name, String branch, String binomial, IFlowerType type, EnumFlowerColor colour) {
        this(name, branch, binomial, type, colour, colour);
    }

    private FlowerSpecies(String name, String branch, String binomial, IFlowerType type, EnumFlowerColor primaryColor, EnumFlowerColor secondaryColor) {
        this.stemColor = EnumFlowerColor.Green;
        this.temperature = EnumTemperature.NORMAL;
        this.pH = EnumAcidity.Neutral;
        this.moisture = EnumMoisture.Normal;
        this.tempTolerance = EnumTolerance.BOTH_1;
        this.pHTolerance = EnumTolerance.NONE;
        this.moistureTolerance = EnumTolerance.NONE;
        this.variantTemplates = new ArrayList();
        this.name = name;
        this.binomial = binomial;
        this.branchName = branch;
        this.type = type;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }

    private IAllele[] AddVariant(EnumFlowerColor a, EnumFlowerColor b) {
        IAllele[] template = this.getTemplate();
        template[EnumFlowerChromosome.PRIMARY.ordinal()] = a.getAllele();
        template[EnumFlowerChromosome.SECONDARY.ordinal()] = b.getAllele();
        this.variantTemplates.add(template);
        return template;
    }

    private void setTemperature(EnumTemperature temperature, EnumTolerance tolerance) {
        this.temperature = temperature;
        this.tempTolerance = tolerance;
    }

    private void setPH(EnumAcidity temperature, EnumTolerance tolerance) {
        this.pH = temperature;
        this.pHTolerance = tolerance;
    }

    private void setMoisture(EnumMoisture temperature, EnumTolerance tolerance) {
        this.moisture = temperature;
        this.moistureTolerance = tolerance;
    }

    private IAllele[] AddVariant(EnumFlowerColor a) {
        return this.AddVariant(a, a);
    }

    public List getVariants() {
        return this.variantTemplates;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return "";
    }

    public EnumTemperature getTemperature() {
        return this.temperature;
    }

    public EnumHumidity getHumidity() {
        return EnumHumidity.values()[this.getMoisture().ordinal()];
    }

    public EnumAcidity getPH() {
        return this.pH;
    }

    public EnumMoisture getMoisture() {
        return this.moisture;
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

    public void setBranch(IClassification branch) {
        this.branch = branch;
    }

    public IClassification getBranch() {
        return this.branch;
    }

    public String getUID() {
        return "botany.flowers.species." + this.toString().toLowerCase();
    }

    public boolean isDominant() {
        return false;
    }

    public IAllele[] getTemplate() {
        IAllele[] template = FlowerTemplates.getDefaultTemplate();
        template[0] = this;
        template[EnumFlowerChromosome.PRIMARY.ordinal()] = this.primaryColor.getAllele();
        template[EnumFlowerChromosome.SECONDARY.ordinal()] = this.secondaryColor.getAllele();
        template[EnumFlowerChromosome.TEMPERATURE_TOLERANCE.ordinal()] = Binnie.Genetics.getToleranceAllele(this.tempTolerance);
        template[EnumFlowerChromosome.PH_TOLERANCE.ordinal()] = Binnie.Genetics.getToleranceAllele(this.pHTolerance);
        template[EnumFlowerChromosome.HUMIDITY_TOLERANCE.ordinal()] = Binnie.Genetics.getToleranceAllele(this.moistureTolerance);
        template[EnumFlowerChromosome.FERTILITY.ordinal()] = this.fert.getAllele();
        template[EnumFlowerChromosome.LIFESPAN.ordinal()] = this.life.getAllele();
        template[EnumFlowerChromosome.SAPPINESS.ordinal()] = this.sap.getAllele();
        template[EnumFlowerChromosome.STEM.ordinal()] = this.stemColor.getAllele();
        return template;
    }

    @SideOnly(Side.CLIENT)
    public IIconProvider getIconProvider() {
        return null;
    }

    public ISpeciesRoot getRoot() {
        return BotanyCore.getFlowerRoot();
    }

    public int getIconColour(int renderPass) {
        return 0;
    }

    public int getComplexity() {
        return 0;
    }

    public float getResearchSuitability(ItemStack itemstack) {
        return 0.0F;
    }

    public ItemStack[] getResearchBounty(World world, GameProfile researcher, IIndividual individual, int bountyLevel) {
        return null;
    }

    public static void init() {
    }

    public IFlowerType getType() {
        return this.type;
    }

    public String getUnlocalizedName() {
        return this.getUID();
    }
}
