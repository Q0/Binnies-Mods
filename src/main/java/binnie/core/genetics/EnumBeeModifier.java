package binnie.core.genetics;

import binnie.Binnie;
import binnie.core.BinnieCore;

public enum EnumBeeModifier {
    Territory,
    Mutation,
    Lifespan,
    Production,
    Flowering,
    GeneticDecay;

    private EnumBeeModifier() {
    }

    public String getName() {
        return Binnie.Language.localise(BinnieCore.instance, "beemodifier." + this.name().toLowerCase());
    }
}
