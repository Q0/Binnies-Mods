package binnie.botany.genetics;

import binnie.botany.api.IFlowerMutation;
import binnie.botany.core.BotanyCore;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IGenome;
import forestry.api.genetics.ISpeciesRoot;

import java.util.ArrayList;
import java.util.Collection;

public class FlowerMutation implements IFlowerMutation {
    IAllele allele0;
    IAllele allele1;
    IAllele[] template;
    int chance;

    FlowerMutation(final IAllele allele0, final IAllele allele1, final IAllele[] template, final int chance) {
        this.allele0 = allele0;
        this.allele1 = allele1;
        this.template = template;
        this.chance = chance;
    }

    public IAllele getAllele0() {
        return this.allele0;
    }

    public IAllele getAllele1() {
        return this.allele1;
    }

    public IAllele[] getTemplate() {
        return this.template;
    }

    public float getBaseChance() {
        return this.chance;
    }

    public boolean isPartner(final IAllele allele) {
        return allele.getUID().equals(this.allele0.getUID()) || allele.getUID().equals(this.allele1.getUID());
    }

    public IAllele getPartner(final IAllele allele) {
        return allele.getUID().equals(this.allele0.getUID()) ? this.allele1 : this.allele0;
    }

    public boolean isSecret() {
        return false;
    }

    @Override
    public float getChance(final IAllele allele0, final IAllele allele1, final IGenome genome0, final IGenome genome1) {
        if (allele0 == null || allele1 == null) {
            return 0.0f;
        }
        if (this.allele0.getUID().equals(allele0.getUID()) && this.allele1.getUID().equals(allele1.getUID())) {
            return this.getBaseChance();
        }
        if (this.allele1.getUID().equals(allele0.getUID()) && this.allele0.getUID().equals(allele1.getUID())) {
            return this.getBaseChance();
        }
        return 0.0f;
    }

    public ISpeciesRoot getRoot() {
        return (ISpeciesRoot) BotanyCore.getFlowerRoot();
    }

    public Collection<String> getSpecialConditions() {
        return new ArrayList<String>();
    }
}
