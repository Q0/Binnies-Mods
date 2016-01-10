package binnie.genetics.gui;

import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.IFlower;
import binnie.core.genetics.Tolerance;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IBee;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import forestry.api.genetics.IAlleleTolerance;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.IButterfly;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import java.util.ArrayList;
import java.util.List;

public class AnalystPageClimate extends ControlAnalystPage {
    public AnalystPageClimate(IWidget parent, IArea area, IIndividual ind) {
        super(parent, area);
        this.setColour(26163);
        EnumTemperature temp = ind.getGenome().getPrimary().getTemperature();
        EnumTolerance tempTol = EnumTolerance.NONE;
        EnumHumidity humid = ind.getGenome().getPrimary().getHumidity();
        EnumTolerance humidTol = EnumTolerance.NONE;
        if (ind instanceof IBee) {
            tempTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumBeeChromosome.TEMPERATURE_TOLERANCE)).getValue();
            humidTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumBeeChromosome.HUMIDITY_TOLERANCE)).getValue();
        }

        if (ind instanceof IFlower) {
            tempTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumFlowerChromosome.TEMPERATURE_TOLERANCE)).getValue();
            humidTol = EnumTolerance.BOTH_5;
        }

        if (ind instanceof IButterfly) {
            tempTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumButterflyChromosome.TEMPERATURE_TOLERANCE)).getValue();
            humidTol = ((IAlleleTolerance) ind.getGenome().getActiveAllele(EnumButterflyChromosome.HUMIDITY_TOLERANCE)).getValue();
        }

        int y = 4;
        (new ControlTextCentered(this, (float) y, "§nClimate")).setColour(this.getColour());
        y = y + 16;
        (new ControlText(this, new IArea(4.0F, (float) y, this.w() - 8.0F, 14.0F), "Temp. Tolerance", TextJustification.MiddleCenter)).setColour(this.getColour());
        y = y + 12;
        this.createTemperatureBar(this, (this.w() - 100.0F) / 2.0F, (float) y, 100.0F, 10.0F, temp, tempTol);
        y = y + 16;
        if (!(ind instanceof IFlower)) {
            (new ControlText(this, new IArea(4.0F, (float) y, this.w() - 8.0F, 14.0F), "Humidity Tolerance", TextJustification.MiddleCenter)).setColour(this.getColour());
            y = y + 12;
            this.createHumidity(this, (this.w() - 100.0F) / 2.0F, (float) y, 100.0F, 10.0F, humid, humidTol);
            y = y + 16;
        }

        (new ControlText(this, new IArea(4.0F, (float) y, this.w() - 8.0F, 14.0F), "Biomes", TextJustification.MiddleCenter)).setColour(this.getColour());
        y = y + 12;
        List<BiomeGenBase> biomes = new ArrayList();

        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome != null && BiomeDictionary.isBiomeRegistered(biome) && biome != BiomeGenBase.frozenOcean && Tolerance.canTolerate(temp, EnumTemperature.getFromBiome(biome), tempTol) && Tolerance.canTolerate(humid, EnumHumidity.getFromValue(biome.rainfall), humidTol)) {
                boolean match = false;

                for (BiomeGenBase eBiome : biomes) {
                    if (biome.biomeName.contains(eBiome.biomeName) && EnumHumidity.getFromValue(eBiome.rainfall) == EnumHumidity.getFromValue(biome.rainfall) && EnumTemperature.getFromBiome(eBiome) == EnumTemperature.getFromBiome(biome)) {
                        match = true;
                    }
                }

                if (!match) {
                    biomes.add(biome);
                }
            }
        }

        int maxBiomePerLine = (int) ((this.w() + 2.0F - 16.0F) / 18.0F);
        float biomeListX = (this.w() - (float) (Math.min(maxBiomePerLine, biomes.size()) * 18 - 2)) / 2.0F;
        int dx = 0;
        int dy = 0;

        for (BiomeGenBase biome : biomes) {
            new ControlBiome(this, biomeListX + (float) dx, (float) (y + dy), 16.0F, 16.0F, biome);
            dx += 18;
            if (dx >= 18 * maxBiomePerLine) {
                dx = 0;
                dy += 18;
            }
        }

        this.setSize(new IPoint(this.w(), (float) (y + dy + 18 + 8)));
    }

    protected void createTemperatureBar(final IWidget parent, final float x, final float y, final float w, final float h, EnumTemperature value, EnumTolerance tol) {
        (new ControlToleranceBar(parent, x, y, w, h, EnumTemperature.class) {
            protected String getName(EnumTemperature value) {
                return value.name;
            }

            protected int getColour(EnumTemperature value) {
                return (new int[]{'\ufffb', 7912447, 5242672, 16776960, 16753152, 16711680})[value.ordinal() - 1];
            }
        }).setValues(value, tol);
    }

    protected void createHumidity(final IWidget parent, final float x, final float y, final float w, final float h, EnumHumidity value, EnumTolerance tol) {
        (new ControlToleranceBar(parent, x, y, w, h, EnumHumidity.class) {
            protected String getName(EnumHumidity value) {
                return value.name;
            }

            protected int getColour(EnumHumidity value) {
                return (new int[]{16770979, 1769216, 3177727})[value.ordinal()];
            }
        }).setValues(value, tol);
    }

    public String getTitle() {
        return "Climate";
    }
}
