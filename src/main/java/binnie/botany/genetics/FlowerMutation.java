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

    FlowerMutation(IAllele allele0, IAllele allele1, IAllele[] template, int chance) {
        super();
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
        return (float) this.chance;
    }

    public boolean isPartner(IAllele allele) {
        return allele.getUID().equals(this.allele0.getUID()) || allele.getUID().equals(this.allele1.getUID());
    }

    public IAllele getPartner(IAllele allele) {
        return allele.getUID().equals(this.allele0.getUID()) ? this.allele1 : this.allele0;
    }

    public boolean isSecret() {
        return false;
    }

    public float getChance(IAllele allele0, IAllele allele1, IGenome genome0, IGenome genome1) {
        return allele0 != null && allele1 != null ? (this.allele0.getUID().equals(allele0.getUID()) && this.allele1.getUID().equals(allele1.getUID()) ? this.getBaseChance() : (this.allele1.getUID().equals(allele0.getUID()) && this.allele0.getUID().equals(allele1.getUID()) ? this.getBaseChance() : 0.0F)) : 0.0F;
    }

    public ISpeciesRoot getRoot() {
        return BotanyCore.getFlowerRoot();
    }

    public Collection getSpecialConditions() {
        return new ArrayList();
    }
}
