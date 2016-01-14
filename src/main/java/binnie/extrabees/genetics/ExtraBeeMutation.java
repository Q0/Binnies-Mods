package binnie.extrabees.genetics;

import binnie.Binnie;
import binnie.core.genetics.ForestryAllele;
import forestry.api.apiculture.*;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.IMutation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtraBeeMutation implements IBeeMutation {
    public static List<IBeeMutation> mutations;
    MutationRequirement req;
    IAlleleBeeSpecies species0;
    IAlleleBeeSpecies species1;
    IAllele[] template;
    int chance;

    public static void doInit() {
        final IAlleleBeeSpecies[] vanilla = new IAlleleBeeSpecies[0];
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.ARID, 10);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.ARID, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ARID, ForestryAllele.BeeSpecies.Common.getAllele(), ExtraBeesSpecies.BARREN, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ARID, (IAlleleBeeSpecies) ExtraBeesSpecies.BARREN, ExtraBeesSpecies.DESOLATE, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BARREN, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.GNAWING, 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.DESOLATE, ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.ROTTEN, 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.DESOLATE, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.BONE, 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.DESOLATE, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.CREEPER, 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BARREN, ForestryAllele.BeeSpecies.Marshy.getAllele(), ExtraBeesSpecies.DECOMPOSING, 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.STONE, 12);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.STONE, ForestryAllele.BeeSpecies.Unweary.getAllele(), ExtraBeesSpecies.GRANITE, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.GRANITE, ForestryAllele.BeeSpecies.Industrious.getAllele(), ExtraBeesSpecies.MINERAL, 6);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.IRON, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.IRON, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Wintry.getAllele(), ExtraBeesSpecies.COPPER, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.COPPER, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Marshy.getAllele(), ExtraBeesSpecies.TIN, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.TIN, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.LEAD, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.LEAD, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Wintry.getAllele(), ExtraBeesSpecies.ZINC, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.ZINC, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.NICKEL, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Marshy.getAllele(), ExtraBeesSpecies.NICKEL, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Cultivated.getAllele(), ExtraBeesSpecies.TITANIUM, 3);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Common.getAllele(), ExtraBeesSpecies.TUNGSTATE, 3);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ZINC, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 2);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.TIN, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 2);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.LEAD, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 2);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.TITANIUM, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.SILVER, 3);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.IRON, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 2);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.COPPER, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 2);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.NICKEL, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 2);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.TUNGSTATE, ForestryAllele.BeeSpecies.Majestic.getAllele(), ExtraBeesSpecies.GOLD, 3);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.GOLD, (IAlleleBeeSpecies) ExtraBeesSpecies.SILVER, ExtraBeesSpecies.PLATINUM, 2);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ForestryAllele.BeeSpecies.Imperial.getAllele(), ExtraBeesSpecies.LAPIS, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.LAPIS, ForestryAllele.BeeSpecies.Forest.getAllele(), ExtraBeesSpecies.EMERALD, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.LAPIS, ForestryAllele.BeeSpecies.Modest.getAllele(), ExtraBeesSpecies.RUBY, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.LAPIS, (IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ExtraBeesSpecies.SAPPHIRE, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.LAPIS, ForestryAllele.BeeSpecies.Cultivated.getAllele(), ExtraBeesSpecies.DIAMOND, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.PREHISTORIC, (IAlleleBeeSpecies) ExtraBeesSpecies.MINERAL, ExtraBeesSpecies.UNSTABLE, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.UNSTABLE, (IAlleleBeeSpecies) ExtraBeesSpecies.IRON, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.UNSTABLE, (IAlleleBeeSpecies) ExtraBeesSpecies.COPPER, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.UNSTABLE, (IAlleleBeeSpecies) ExtraBeesSpecies.TIN, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.UNSTABLE, (IAlleleBeeSpecies) ExtraBeesSpecies.ZINC, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.UNSTABLE, (IAlleleBeeSpecies) ExtraBeesSpecies.NICKEL, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.UNSTABLE, (IAlleleBeeSpecies) ExtraBeesSpecies.LEAD, ExtraBeesSpecies.NUCLEAR, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.NUCLEAR, (IAlleleBeeSpecies) ExtraBeesSpecies.GOLD, ExtraBeesSpecies.RADIOACTIVE, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.NUCLEAR, (IAlleleBeeSpecies) ExtraBeesSpecies.SILVER, ExtraBeesSpecies.RADIOACTIVE, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.NUCLEAR, ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.YELLORIUM, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.NUCLEAR, (IAlleleBeeSpecies) ExtraBeesSpecies.YELLORIUM, ExtraBeesSpecies.CYANITE, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.YELLORIUM, (IAlleleBeeSpecies) ExtraBeesSpecies.CYANITE, ExtraBeesSpecies.BLUTONIUM, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Noble.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.ANCIENT, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ANCIENT, ForestryAllele.BeeSpecies.Secluded.getAllele(), ExtraBeesSpecies.PRIMEVAL, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.PRIMEVAL, (IAlleleBeeSpecies) ExtraBeesSpecies.ANCIENT, ExtraBeesSpecies.PREHISTORIC, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.PREHISTORIC, ForestryAllele.BeeSpecies.Imperial.getAllele(), ExtraBeesSpecies.RELIC, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.PRIMEVAL, (IAlleleBeeSpecies) ExtraBeesSpecies.GROWING, ExtraBeesSpecies.COAL, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.PRIMEVAL, ForestryAllele.BeeSpecies.Rural.getAllele(), ExtraBeesSpecies.COAL, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.PRIMEVAL, ForestryAllele.BeeSpecies.Miry.getAllele(), ExtraBeesSpecies.RESIN, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.PRIMEVAL, (IAlleleBeeSpecies) ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.OIL, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.PRIMEVAL, ForestryAllele.BeeSpecies.Frugal.getAllele(), ExtraBeesSpecies.OIL, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.OIL, ForestryAllele.BeeSpecies.Industrious.getAllele(), ExtraBeesSpecies.DISTILLED, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.DISTILLED, (IAlleleBeeSpecies) ExtraBeesSpecies.OIL, ExtraBeesSpecies.FUEL, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.DISTILLED, (IAlleleBeeSpecies) ExtraBeesSpecies.COAL, ExtraBeesSpecies.CREOSOTE, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.DISTILLED, (IAlleleBeeSpecies) ExtraBeesSpecies.RESIN, ExtraBeesSpecies.LATEX, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.RIVER, 10, new RequirementBiomeType(BiomeDictionary.Type.RIVER));
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.OCEAN, 10, new RequirementBiomeType(BiomeDictionary.Type.OCEAN));
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BLACK, (IAlleleBeeSpecies) ExtraBeesSpecies.OCEAN, ExtraBeesSpecies.INK, 8);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.GROWING, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.GROWING, ForestryAllele.BeeSpecies.Unweary.getAllele(), ExtraBeesSpecies.THRIVING, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.THRIVING, ForestryAllele.BeeSpecies.Industrious.getAllele(), ExtraBeesSpecies.BLOOMING, 8);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Valiant.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.SWEET, 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.SWEET, ForestryAllele.BeeSpecies.Rural.getAllele(), ExtraBeesSpecies.SUGAR, 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.SWEET, (IAlleleBeeSpecies) ExtraBeesSpecies.GROWING, ExtraBeesSpecies.RIPENING, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.SWEET, (IAlleleBeeSpecies) ExtraBeesSpecies.THRIVING, ExtraBeesSpecies.FRUIT, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.ALCOHOL, 10);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ForestryAllele.BeeSpecies.Meadows.getAllele(), ExtraBeesSpecies.FARM, 10);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), (IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ExtraBeesSpecies.MILK, 10);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Farmerly.getAllele(), ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.COFFEE, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Miry.getAllele(), ExtraBeesSpecies.SWAMP, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.SWAMP, ForestryAllele.BeeSpecies.Boggy.getAllele(), ExtraBeesSpecies.BOGGY, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BOGGY, (IAlleleBeeSpecies) ExtraBeesSpecies.SWAMP, ExtraBeesSpecies.FUNGAL, 8);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Boggy.getAllele(), ForestryAllele.BeeSpecies.Miry.getAllele(), ExtraBeesSpecies.FUNGAL, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Modest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, (IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, (IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Modest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, (IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Modest.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Common.getTemplate(), 15);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Common.getAllele(), ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Common.getAllele(), ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Common.getAllele(), ForestryAllele.BeeSpecies.Cultivated.getTemplate(), 12);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Fiendish.getAllele(), ExtraBeesSpecies.TEMPERED, 30, new RequirementBiomeType(BiomeDictionary.Type.NETHER));
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.TEMPERED, ForestryAllele.BeeSpecies.Demonic.getAllele(), ExtraBeesSpecies.VOLCANIC, 20, new RequirementBiomeType(BiomeDictionary.Type.NETHER));
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Cultivated.getAllele(), ForestryAllele.BeeSpecies.Sinister.getTemplate(), 60, new RequirementBiomeType(BiomeDictionary.Type.NETHER));
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BASALT, ForestryAllele.BeeSpecies.Sinister.getAllele(), ForestryAllele.BeeSpecies.Fiendish.getTemplate(), 40, new RequirementBiomeType(BiomeDictionary.Type.NETHER));
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Sinister.getAllele(), ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.MALICIOUS, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MALICIOUS, ForestryAllele.BeeSpecies.Tropical.getAllele(), ExtraBeesSpecies.INFECTIOUS, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MALICIOUS, (IAlleleBeeSpecies) ExtraBeesSpecies.INFECTIOUS, ExtraBeesSpecies.VIRULENT, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Exotic.getAllele(), ExtraBeesSpecies.VISCOUS, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.VISCOUS, ForestryAllele.BeeSpecies.Exotic.getAllele(), ExtraBeesSpecies.GLUTINOUS, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.VISCOUS, (IAlleleBeeSpecies) ExtraBeesSpecies.GLUTINOUS, ExtraBeesSpecies.STICKY, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.MALICIOUS, (IAlleleBeeSpecies) ExtraBeesSpecies.VISCOUS, ExtraBeesSpecies.CORROSIVE, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.CORROSIVE, ForestryAllele.BeeSpecies.Fiendish.getAllele(), ExtraBeesSpecies.CAUSTIC, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.CORROSIVE, (IAlleleBeeSpecies) ExtraBeesSpecies.CAUSTIC, ExtraBeesSpecies.ACIDIC, 4);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Cultivated.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.EXCITED, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.EXCITED, ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.ENERGETIC, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.EXCITED, (IAlleleBeeSpecies) ExtraBeesSpecies.ENERGETIC, ExtraBeesSpecies.ECSTATIC, 8);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Diligent.getAllele(), ExtraBeesSpecies.ARTIC, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.OCEAN, (IAlleleBeeSpecies) ExtraBeesSpecies.ARTIC, ExtraBeesSpecies.FREEZING, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Sinister.getAllele(), ExtraBeesSpecies.SHADOW, 10);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.SHADOW, (IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ExtraBeesSpecies.DARKENED, 8);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.SHADOW, (IAlleleBeeSpecies) ExtraBeesSpecies.DARKENED, ExtraBeesSpecies.ABYSS, 8);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Forest.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.RED, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Meadows.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.YELLOW, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.WATER, ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.BLUE, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Tropical.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.GREEN, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.ROCK, ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.BLACK, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Wintry.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.WHITE, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Marshy.getAllele(), ForestryAllele.BeeSpecies.Valiant.getAllele(), ExtraBeesSpecies.BROWN, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.RED, (IAlleleBeeSpecies) ExtraBeesSpecies.YELLOW, ExtraBeesSpecies.ORANGE, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.GREEN, (IAlleleBeeSpecies) ExtraBeesSpecies.BLUE, ExtraBeesSpecies.CYAN, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.RED, (IAlleleBeeSpecies) ExtraBeesSpecies.BLUE, ExtraBeesSpecies.PURPLE, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BLACK, (IAlleleBeeSpecies) ExtraBeesSpecies.WHITE, ExtraBeesSpecies.GRAY, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.BLUE, (IAlleleBeeSpecies) ExtraBeesSpecies.WHITE, ExtraBeesSpecies.LIGHTBLUE, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.RED, (IAlleleBeeSpecies) ExtraBeesSpecies.WHITE, ExtraBeesSpecies.PINK, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.GREEN, (IAlleleBeeSpecies) ExtraBeesSpecies.WHITE, ExtraBeesSpecies.LIMEGREEN, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.PURPLE, (IAlleleBeeSpecies) ExtraBeesSpecies.PINK, ExtraBeesSpecies.MAGENTA, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.GRAY, (IAlleleBeeSpecies) ExtraBeesSpecies.WHITE, ExtraBeesSpecies.LIGHTGRAY, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.TEMPERED, (IAlleleBeeSpecies) ExtraBeesSpecies.EXCITED, ExtraBeesSpecies.GLOWSTONE, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Austere.getAllele(), (IAlleleBeeSpecies) ExtraBeesSpecies.DESOLATE, ExtraBeesSpecies.HAZARDOUS, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Ended.getAllele(), (IAlleleBeeSpecies) ExtraBeesSpecies.RELIC, ExtraBeesSpecies.JADED, 2, new RequirementPerson("jadedcat"));
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Austere.getAllele(), (IAlleleBeeSpecies) ExtraBeesSpecies.EXCITED, ExtraBeesSpecies.CELEBRATORY, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Secluded.getAllele(), ForestryAllele.BeeSpecies.Ended.getAllele(), ExtraBeesSpecies.UNUSUAL, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.UNUSUAL, ForestryAllele.BeeSpecies.Hermitic.getAllele(), ExtraBeesSpecies.SPATIAL, 5);
        new ExtraBeeMutation((IAlleleBeeSpecies) ExtraBeesSpecies.SPATIAL, ForestryAllele.BeeSpecies.Spectral.getAllele(), ExtraBeesSpecies.QUANTUM, 5);
        new ExtraBeeMutation(ForestryAllele.BeeSpecies.Noble.getAllele(), ForestryAllele.BeeSpecies.Monastic.getAllele(), ExtraBeesSpecies.MYSTICAL, 5);
        for (final IBeeMutation mutation : ExtraBeeMutation.mutations) {
            Binnie.Genetics.getBeeRoot().registerMutation((IMutation) mutation);
        }
    }

    public ExtraBeeMutation(final IAlleleBeeSpecies allele0, final IAlleleBeeSpecies allele1, final ExtraBeesSpecies mutation, final int chance) {
        this(allele0, allele1, mutation.getTemplate(), chance, null);
    }

    public ExtraBeeMutation(final IAlleleBeeSpecies allele0, final IAlleleBeeSpecies allele1, final ExtraBeesSpecies mutation, final int chance, final MutationRequirement req) {
        this(allele0, allele1, mutation.getTemplate(), chance, req);
    }

    public ExtraBeeMutation(final IAlleleBeeSpecies allele0, final IAlleleBeeSpecies allele1, final IAllele[] mutation, final int chance) {
        this(allele0, allele1, mutation, chance, null);
    }

    public ExtraBeeMutation(final IAlleleBeeSpecies allele0, final IAlleleBeeSpecies allele1, final IAllele[] mutation, final int chance, final MutationRequirement req) {
        this.species0 = null;
        this.species1 = null;
        this.template = new IAllele[0];
        this.chance = 80;
        this.chance = chance;
        this.req = req;
        this.species0 = allele0;
        this.species1 = allele1;
        this.template = mutation;
        if (this.species0 != null && this.species1 != null && this.template != null) {
            ExtraBeeMutation.mutations.add((IBeeMutation) this);
        }
    }

    public IAlleleSpecies getAllele0() {
        return (IAlleleSpecies) this.species0;
    }

    public IAlleleSpecies getAllele1() {
        return (IAlleleSpecies) this.species1;
    }

    public IAllele[] getTemplate() {
        return this.template;
    }

    public float getBaseChance() {
        return this.chance;
    }

    public boolean isPartner(final IAllele allele) {
        return allele.getUID().equals(this.species0.getUID()) || allele.getUID().equals(this.species1.getUID());
    }

    public IAllele getPartner(final IAllele allele) {
        return (IAllele) (allele.getUID().equals(this.species0.getUID()) ? this.species1 : this.species0);
    }

    public boolean isSecret() {
        return false;
    }

    public float getChance(final IBeeHousing housing, final IAlleleBeeSpecies allele0, final IAlleleBeeSpecies allele1, final IBeeGenome genome0, final IBeeGenome genome1) {
        return this.getChance(housing, (IAllele) allele0, (IAllele) allele1, (IGenome) genome0, (IGenome) genome1);
    }

    public float getChance(final IBeeHousing housing, final IAllele allele0, final IAllele allele1, final IGenome genome0, final IGenome genome1) {
        if (this.species0 == null || this.species1 == null || allele0 == null || allele1 == null) {
            return 0.0f;
        }
        final World world = housing.getWorld();
        ChunkCoordinates coords = housing.getCoordinates();
        final int x = coords.posX;
        final int y = coords.posX;
        final int z = coords.posX;
        final BiomeGenBase biome = housing.getBiome();
        if (this.req != null && !this.req.fufilled(housing, allele0, allele1, genome0, genome1)) {
            return 0.0f;
        }
        IBeeModifier housingBeeModifier = BeeManager.beeRoot.createBeeHousingModifier(housing);
        IBeeModifier modeBeeModifier = BeeManager.beeRoot.getBeekeepingMode(housing.getWorld()).getBeeModifier();
        final int processedChance = Math.round(this.chance * housingBeeModifier.getMutationModifier((IBeeGenome) genome0, (IBeeGenome) genome1, 1.0f) * modeBeeModifier.getMutationModifier((IBeeGenome) genome0, (IBeeGenome) genome1, 1.0f));
        if (this.species0.getUID().equals(allele0.getUID()) && this.species1.getUID().equals(allele1.getUID())) {
            return processedChance;
        }
        if (this.species1.getUID().equals(allele0.getUID()) && this.species0.getUID().equals(allele1.getUID())) {
            return processedChance;
        }
        return 0.0f;
    }

    public Collection<String> getSpecialConditions() {
        final List<String> conditions = new ArrayList<String>();
        if (this.req != null) {
            for (final String s : this.req.tooltip()) {
                conditions.add(s);
            }
        }
        return conditions;
    }

    public IBeeRoot getRoot() {
        return Binnie.Genetics.getBeeRoot();
    }

    static {
        ExtraBeeMutation.mutations = new ArrayList<IBeeMutation>();
    }

    abstract static class MutationRequirement {
        public abstract String[] tooltip();

        public abstract boolean fufilled(final IBeeHousing p0, final IAllele p1, final IAllele p2, final IGenome p3, final IGenome p4);
    }

    static class RequirementBiomeType extends MutationRequirement {
        BiomeDictionary.Type type;

        public RequirementBiomeType(final BiomeDictionary.Type type) {
            this.type = type;
        }

        @Override
        public String[] tooltip() {
            return new String[]{"Is restricted to " + this.type + "-like biomes."};
        }

        @Override
        public boolean fufilled(final IBeeHousing housing, final IAllele allele0, final IAllele allele1, final IGenome genome0, final IGenome genome1) {
            return BiomeDictionary.isBiomeOfType(housing.getBiome(), this.type);
        }
    }

    static class RequirementPerson extends MutationRequirement {
        String name;

        public RequirementPerson(final String name) {
            this.name = name;
        }

        @Override
        public String[] tooltip() {
            return new String[]{"Can only be bred by " + this.name};
        }

        @Override
        public boolean fufilled(final IBeeHousing housing, final IAllele allele0, final IAllele allele1, final IGenome genome0, final IGenome genome1) {
            return housing.getOwner() != null && housing.getOwner().equals((Object) this.name);
        }
    }
}
