package binnie.extratrees.genetics;

import binnie.Binnie;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.arboriculture.ITreeMutation;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.*;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ExtraTreeMutation implements ITreeMutation {
    int chance;
    boolean isSecret;
    IAllele allele0;
    IAllele allele1;
    IAllele[] template;
    private float minTemperature;
    private float maxTemperature;
    private float minRainfall;
    private float maxRainfall;
    private float height;

    public static void init() {
        final IAlleleTreeSpecies lemon = (IAlleleTreeSpecies) getVanilla("Lemon");
        new ExtraTreeMutation(getVanilla("Cherry"), (IAllele) lemon, (IAllele) ExtraTreeSpecies.KeyLime, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.KeyLime, (IAllele) lemon, (IAllele) ExtraTreeSpecies.FingerLime, 10);
        new ExtraTreeMutation(getVanilla("Cherry"), (IAllele) lemon, (IAllele) ExtraTreeSpecies.Pomelo, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Pomelo, getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.Manderin, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Pomelo, (IAllele) lemon, (IAllele) ExtraTreeSpecies.Citron, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Manderin, getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.Kumquat, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Pomelo, (IAllele) ExtraTreeSpecies.Manderin, (IAllele) ExtraTreeSpecies.Orange, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Citron, (IAllele) ExtraTreeSpecies.Manderin, (IAllele) ExtraTreeSpecies.BuddhaHand, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Kumquat, (IAllele) ExtraTreeSpecies.Manderin, (IAllele) ExtraTreeSpecies.Tangerine, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Kumquat, (IAllele) ExtraTreeSpecies.Manderin, (IAllele) ExtraTreeSpecies.Satsuma, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Pomelo, (IAllele) ExtraTreeSpecies.Orange, (IAllele) ExtraTreeSpecies.Grapefruit, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Pomelo, (IAllele) ExtraTreeSpecies.KeyLime, (IAllele) ExtraTreeSpecies.Lime, 10);
        new ExtraTreeMutation(getVanilla("Oak"), getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.OrchardApple, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.OrchardApple, getVanilla("Maple"), (IAllele) ExtraTreeSpecies.SweetCrabapple, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.OrchardApple, (IAllele) ExtraTreeSpecies.SweetCrabapple, (IAllele) ExtraTreeSpecies.FloweringCrabapple, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.OrchardApple, getVanilla("Birch"), (IAllele) ExtraTreeSpecies.PrairieCrabapple, 10);
        new ExtraTreeMutation(getVanilla("Plum"), (IAllele) ExtraTreeSpecies.OrchardApple, (IAllele) ExtraTreeSpecies.Blackthorn, 10);
        new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.CherryPlum, 10);
        new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Chestnut"), (IAllele) ExtraTreeSpecies.Peach, 10);
        new ExtraTreeMutation(getVanilla("Plum"), (IAllele) ExtraTreeSpecies.Peach, (IAllele) ExtraTreeSpecies.Nectarine, 10);
        new ExtraTreeMutation(getVanilla("Plum"), (IAllele) ExtraTreeSpecies.Peach, (IAllele) ExtraTreeSpecies.Apricot, 10);
        new ExtraTreeMutation(getVanilla("Plum"), getVanilla("Walnut"), (IAllele) ExtraTreeSpecies.Almond, 10);
        new ExtraTreeMutation(getVanilla("Lime"), getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.WildCherry, 10);
        new ExtraTreeMutation(getVanilla("Willow"), getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.SourCherry, 10);
        new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.BlackCherry, 10);
        new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Jungle"), (IAllele) ExtraTreeSpecies.Banana, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Banana, getVanilla("Kapok"), (IAllele) ExtraTreeSpecies.RedBanana, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Banana, getVanilla("Teak"), (IAllele) ExtraTreeSpecies.Plantain, 10);
        new ExtraTreeMutation(getVanilla("Birch"), getVanilla("Oak"), (IAllele) ExtraTreeSpecies.Beech, 10);
        new ExtraTreeMutation(getVanilla("Birch"), (IAllele) ExtraTreeSpecies.Beech, (IAllele) ExtraTreeSpecies.Alder, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Alder, (IAllele) ExtraTreeSpecies.Beech, (IAllele) ExtraTreeSpecies.Aspen, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Aspen, (IAllele) ExtraTreeSpecies.Alder, (IAllele) ExtraTreeSpecies.Rowan, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Beech, (IAllele) ExtraTreeSpecies.Aspen, (IAllele) ExtraTreeSpecies.Hazel, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Beech, (IAllele) ExtraTreeSpecies.Rowan, (IAllele) ExtraTreeSpecies.Hawthorn, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Alder, (IAllele) ExtraTreeSpecies.Aspen, (IAllele) ExtraTreeSpecies.Elder, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Alder, (IAllele) ExtraTreeSpecies.Rowan, (IAllele) ExtraTreeSpecies.Holly, 10);
        new ExtraTreeMutation(getVanilla("Willow"), (IAllele) ExtraTreeSpecies.Aspen, (IAllele) ExtraTreeSpecies.Sallow, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Beech, getVanilla("Birch"), (IAllele) ExtraTreeSpecies.Pecan, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Beech, getVanilla("Spruce"), (IAllele) ExtraTreeSpecies.CopperBeech, 10);
        new ExtraTreeMutation(getVanilla("Lime"), getVanilla("Spruce"), (IAllele) ExtraTreeSpecies.Ash, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Ash, getVanilla("Birch"), (IAllele) ExtraTreeSpecies.Whitebeam, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Ash, getVanilla("Pine"), (IAllele) ExtraTreeSpecies.Elm, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Ash, getVanilla("Larch"), (IAllele) ExtraTreeSpecies.Hornbeam, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Ash, getVanilla("Maple"), (IAllele) ExtraTreeSpecies.Sycamore, 10);
        new ExtraTreeMutation(getVanilla("Larch"), getVanilla("Spruce"), (IAllele) ExtraTreeSpecies.Yew, 10);
        new ExtraTreeMutation(getVanilla("Larch"), (IAllele) ExtraTreeSpecies.Alder, (IAllele) ExtraTreeSpecies.BalsamFir, 10);
        new ExtraTreeMutation(getVanilla("Pine"), (IAllele) ExtraTreeSpecies.BalsamFir, (IAllele) ExtraTreeSpecies.Fir, 10);
        new ExtraTreeMutation(getVanilla("Pine"), (IAllele) ExtraTreeSpecies.Fir, (IAllele) ExtraTreeSpecies.Hemlock, 10).setHeight(80);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Fir, getVanilla("Larch"), (IAllele) ExtraTreeSpecies.Cedar, 10).setHeight(60);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Fir, getVanilla("Spruce"), (IAllele) ExtraTreeSpecies.DouglasFir, 10).setHeight(60);
        new ExtraTreeMutation(getVanilla("Pine"), getVanilla("Spruce"), (IAllele) ExtraTreeSpecies.Cypress, 10);
        new ExtraTreeMutation(getVanilla("Pine"), getVanilla("Spruce"), (IAllele) ExtraTreeSpecies.LoblollyPine, 10);
        new ExtraTreeMutation(getVanilla("Walnut"), getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.Butternut, 10);
        new ExtraTreeMutation(getVanilla("Walnut"), getVanilla("Oak"), (IAllele) ExtraTreeSpecies.AcornOak, 10);
        new ExtraTreeMutation(getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.Alder, (IAllele) ExtraTreeSpecies.Olive, 10);
        new ExtraTreeMutation(getVanilla("Maple"), getVanilla("Lime"), (IAllele) ExtraTreeSpecies.RedMaple, 10);
        new ExtraTreeMutation(getVanilla("Maple"), getVanilla("Larch"), (IAllele) ExtraTreeSpecies.Sweetgum, 10);
        new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Lime"), (IAllele) ExtraTreeSpecies.Locust, 10);
        new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Teak"), (IAllele) ExtraTreeSpecies.Iroko, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.OrchardApple, getVanilla("Birch"), (IAllele) ExtraTreeSpecies.Pear, 10);
        new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), (IAllele) ExtraTreeSpecies.OldFustic, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.OldFustic, getVanilla("Kapok"), (IAllele) ExtraTreeSpecies.OsangeOsange, 10);
        new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), (IAllele) ExtraTreeSpecies.Brazilwood, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Brazilwood, getVanilla("Kapok"), (IAllele) ExtraTreeSpecies.Purpleheart, 10);
        new ExtraTreeMutation(getVanilla("Ebony"), getVanilla("Teak"), (IAllele) ExtraTreeSpecies.Rosewood, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Rosewood, getVanilla("Kapok"), (IAllele) ExtraTreeSpecies.Logwood, 10);
        new ExtraTreeMutation(getVanilla("Wenge"), getVanilla("Lime"), (IAllele) ExtraTreeSpecies.Gingko, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Beech, getVanilla("Jungle"), (IAllele) ExtraTreeSpecies.Brazilnut, 10);
        new ExtraTreeMutation(getVanilla("Balsa"), getVanilla("Jungle"), (IAllele) ExtraTreeSpecies.RoseGum, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.RoseGum, getVanilla("Mahogony"), (IAllele) ExtraTreeSpecies.SwampGum, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Coffee, getVanilla("Teak"), (IAllele) ExtraTreeSpecies.Clove, 10);
        new ExtraTreeMutation(getVanilla("Cherry"), getVanilla("Jungle"), (IAllele) ExtraTreeSpecies.Coffee, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Holly, (IAllele) ExtraTreeSpecies.Alder, (IAllele) ExtraTreeSpecies.Box, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Hemlock, getVanilla("Jungle"), (IAllele) ExtraTreeSpecies.MonkeyPuzzle, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.RoseGum, getVanilla("Balsa"), (IAllele) ExtraTreeSpecies.RainbowGum, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.RoseGum, (IAllele) ExtraTreeSpecies.Brazilwood, (IAllele) ExtraTreeSpecies.PinkIvory, 10);
        new ExtraTreeMutation(getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.Elder, (IAllele) ExtraTreeSpecies.Raspberry, 10);
        new ExtraTreeMutation(getVanilla("Cherry"), (IAllele) ExtraTreeSpecies.Elder, (IAllele) ExtraTreeSpecies.Redcurrant, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.BlackCherry, (IAllele) ExtraTreeSpecies.Redcurrant, (IAllele) ExtraTreeSpecies.Blackcurrant, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.BlackCherry, (IAllele) ExtraTreeSpecies.Raspberry, (IAllele) ExtraTreeSpecies.Blackberry, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Blackberry, (IAllele) ExtraTreeSpecies.Raspberry, (IAllele) ExtraTreeSpecies.Blueberry, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Blackberry, (IAllele) ExtraTreeSpecies.CherryPlum, (IAllele) ExtraTreeSpecies.Cranberry, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Raspberry, (IAllele) ExtraTreeSpecies.Fir, (IAllele) ExtraTreeSpecies.Juniper, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Raspberry, (IAllele) ExtraTreeSpecies.Lime, (IAllele) ExtraTreeSpecies.Gooseberry, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Raspberry, (IAllele) ExtraTreeSpecies.Orange, (IAllele) ExtraTreeSpecies.GoldenRaspberry, 10);
        new ExtraTreeMutation(getVanilla("Teak"), (IAllele) ExtraTreeSpecies.Rosewood, (IAllele) ExtraTreeSpecies.Cinnamon, 10);
        new ExtraTreeMutation(getVanilla("Balsa"), (IAllele) ExtraTreeSpecies.Brazilnut, (IAllele) ExtraTreeSpecies.Coconut, 10);
        new ExtraTreeMutation(getVanilla("Teak"), getVanilla("Oak"), (IAllele) ExtraTreeSpecies.Cashew, 10);
        new ExtraTreeMutation(getVanilla("Wenge"), getVanilla("Oak"), (IAllele) ExtraTreeSpecies.Avacado, 10);
        new ExtraTreeMutation(getVanilla("Teak"), (IAllele) ExtraTreeSpecies.Clove, (IAllele) ExtraTreeSpecies.Nutmeg, 10);
        new ExtraTreeMutation(getVanilla("Teak"), (IAllele) ExtraTreeSpecies.Clove, (IAllele) ExtraTreeSpecies.Allspice, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Allspice, (IAllele) ExtraTreeSpecies.Clove, (IAllele) ExtraTreeSpecies.StarAnise, 10);
        new ExtraTreeMutation(getVanilla("Jungle"), (IAllele) ExtraTreeSpecies.Orange, (IAllele) ExtraTreeSpecies.Mango, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.StarAnise, (IAllele) ExtraTreeSpecies.Mango, (IAllele) ExtraTreeSpecies.Starfruit, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Hazel, (IAllele) ExtraTreeSpecies.Gingko, (IAllele) ExtraTreeSpecies.Candlenut, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Hazel, (IAllele) ExtraTreeSpecies.Gingko, (IAllele) ExtraTreeSpecies.Chilli, 10);
        new ExtraTreeMutation((IAllele) ExtraTreeSpecies.Hazel, (IAllele) ExtraTreeSpecies.Alder, (IAllele) ExtraTreeSpecies.DwarfHazel, 10);
    }

    public static IAllele getVanilla(final String uid) {
        final IAllele allele = AlleleManager.alleleRegistry.getAllele("forestry.tree" + uid);
        if (allele == null) {
            throw new RuntimeException("No forestry species with id " + uid);
        }
        return allele;
    }

    public ExtraTreeMutation(final IAllele allele0, final IAllele allele1, final IAllele result, final int chance) {
        this(allele0, allele1, Binnie.Genetics.getTreeRoot().getTemplate(result.getUID()), chance);
    }

    public ExtraTreeMutation(final IAllele allele0, final IAllele allele1, final IAllele[] template, final int chance) {
        this.isSecret = false;
        this.minTemperature = 0.0f;
        this.maxTemperature = 2.0f;
        this.minRainfall = 0.0f;
        this.maxRainfall = 2.0f;
        this.height = -1.0f;
        this.allele0 = allele0;
        this.allele1 = allele1;
        this.template = template;
        this.chance = chance;
        Binnie.Genetics.getTreeRoot().registerMutation((IMutation) this);
    }

    public ExtraTreeMutation setIsSecret() {
        this.isSecret = true;
        return this;
    }

    public ExtraTreeMutation setTemperature(final float minTemperature, final float maxTemperature) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        return this;
    }

    public ExtraTreeMutation setRainfall(final float minRainfall, final float maxRainfall) {
        this.minRainfall = minRainfall;
        this.maxRainfall = maxRainfall;
        return this;
    }

    public ExtraTreeMutation setTemperatureRainfall(final float minTemperature, final float maxTemperature, final float minRainfall, final float maxRainfall) {
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minRainfall = minRainfall;
        this.maxRainfall = maxRainfall;
        return this;
    }

    public ExtraTreeMutation setHeight(final int minHeight) {
        this.height = minHeight;
        return this;
    }

    public IAlleleSpecies getAllele0() {
        return (IAlleleSpecies) this.allele0;
    }

    public IAlleleSpecies getAllele1() {
        return (IAlleleSpecies) this.allele1;
    }

    public float getBaseChance() {
        return this.chance;
    }

    public IAllele[] getTemplate() {
        return this.template;
    }

    public boolean isPartner(final IAllele allele) {
        return this.allele0.getUID().equals(allele.getUID()) || this.allele1.getUID().equals(allele.getUID());
    }

    public IAllele getPartner(final IAllele allele) {
        if (this.allele0.getUID().equals(allele.getUID())) {
            return this.allele1;
        }
        return this.allele0;
    }

    public boolean isSecret() {
        return this.isSecret;
    }

    public float getChance(final World world, final int x, final int y, final int z, final IAllele allele0, final IAllele allele1, final IGenome genome0, final IGenome genome1) {
        final int processedChance = this.chance;
        final BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(x, z);
        if (biome.temperature < this.minTemperature || biome.temperature > this.maxTemperature) {
            return 0.0f;
        }
        if (biome.rainfall < this.minRainfall || biome.rainfall > this.maxRainfall) {
            return 0.0f;
        }
        if (this.height > 0.0f && y < this.height) {
            return 0.0f;
        }
        if (this.allele0.getUID().equals(allele0.getUID()) && this.allele1.getUID().equals(allele1.getUID())) {
            return processedChance;
        }
        if (this.allele1.getUID().equals(allele0.getUID()) && this.allele0.getUID().equals(allele1.getUID())) {
            return processedChance;
        }
        return 0.0f;
    }

    public Collection<String> getSpecialConditions() {
        final List<String> conditions = new ArrayList<String>();
        if (this.height > 0.0f) {
            conditions.add("Minimum height from bedrock of " + this.height);
        }
        return conditions;
    }

    public ITreeRoot getRoot() {
        return Binnie.Genetics.getTreeRoot();
    }

    @Override
    public float getChance(World world, int x, int y, int z, IAlleleTreeSpecies allele0, IAlleleTreeSpecies allele1, ITreeGenome genome0, ITreeGenome genome1) {
        return 0;
    }
}
