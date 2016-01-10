package binnie.botany.genetics;

import binnie.Binnie;
import binnie.botany.api.*;
import binnie.botany.core.BotanyCore;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.*;
import forestry.core.genetics.Genome;
import net.minecraft.nbt.NBTTagCompound;

public class FlowerGenome extends Genome implements IFlowerGenome {
    public FlowerGenome(NBTTagCompound nbttagcompound) {
        super(nbttagcompound);
    }

    public FlowerGenome(IChromosome[] chromosomes) {
        super(chromosomes);
    }

    public IAlleleFlowerSpecies getPrimary() {
        return (IAlleleFlowerSpecies) this.getChromosomes()[EnumFlowerChromosome.SPECIES.ordinal()].getPrimaryAllele();
    }

    public IAlleleFlowerSpecies getSecondary() {
        return (IAlleleFlowerSpecies) this.getChromosomes()[EnumFlowerChromosome.SPECIES.ordinal()].getSecondaryAllele();
    }

    public ISpeciesRoot getSpeciesRoot() {
        return BotanyCore.getFlowerRoot();
    }

    public EnumFlowerColor getPrimaryColor() {
        return ((AlleleColor) this.getActiveAllele(EnumFlowerChromosome.PRIMARY)).getColor();
    }

    public EnumFlowerColor getSecondaryColor() {
        return ((AlleleColor) this.getActiveAllele(EnumFlowerChromosome.SECONDARY)).getColor();
    }

    public EnumFlowerColor getStemColor() {
        return ((AlleleColor) this.getActiveAllele(EnumFlowerChromosome.STEM)).getColor();
    }

    public int getFertility() {
        return ((IAlleleInteger) this.getActiveAllele(EnumFlowerChromosome.FERTILITY)).getValue();
    }

    public int getLifespan() {
        return ((IAlleleInteger) this.getActiveAllele(EnumFlowerChromosome.LIFESPAN)).getValue() / 5;
    }

    public IFlowerType getType() {
        return this.getPrimary().getType();
    }

    public EnumTolerance getToleranceTemperature() {
        return ((IAlleleTolerance) this.getActiveAllele(EnumFlowerChromosome.TEMPERATURE_TOLERANCE)).getValue();
    }

    public EnumTolerance getToleranceMoisture() {
        return ((IAlleleTolerance) this.getActiveAllele(EnumFlowerChromosome.HUMIDITY_TOLERANCE)).getValue();
    }

    public EnumTolerance getTolerancePH() {
        return ((IAlleleTolerance) this.getActiveAllele(EnumFlowerChromosome.PH_TOLERANCE)).getValue();
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
    }

    public float getAgeChance() {
        return 1.0F * (float) Math.sqrt((double) (2.0F / (float) this.getLifespan()));
    }

    public float getSappiness() {
        return ((IAlleleFloat) this.getActiveAllele(EnumFlowerChromosome.SAPPINESS)).getValue();
    }

    public boolean canTolerate(EnumAcidity ePH) {
        int pH = ePH.ordinal();
        int[] pHTol = Binnie.Genetics.getTolerance(this.getTolerancePH());
        int fPH = this.getPrimary().getPH().ordinal();
        return pH >= fPH + pHTol[0] && pH <= fPH + pHTol[1];
    }

    public boolean canTolerate(EnumMoisture eMoisture) {
        int moisture = eMoisture.ordinal();
        int[] moistTol = Binnie.Genetics.getTolerance(this.getToleranceMoisture());
        int fMoisture = this.getPrimary().getMoisture().ordinal();
        return moisture >= fMoisture + moistTol[0] && moisture <= fMoisture + moistTol[1];
    }

    public boolean canTolerate(EnumTemperature eTemp) {
        int temp = eTemp.ordinal();
        int[] tempTol = Binnie.Genetics.getTolerance(this.getToleranceTemperature());
        int fTemp = this.getPrimary().getTemperature().ordinal();
        return temp >= fTemp + tempTol[0] && temp <= fTemp + tempTol[1];
    }
}
