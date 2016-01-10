package binnie.botany.genetics;

import binnie.botany.api.IAlleleFlowerEffect;
import binnie.botany.api.IFlowerGenome;
import forestry.api.genetics.IEffectData;
import net.minecraft.world.World;

public class AlleleEffectNone implements IAlleleFlowerEffect {
    public AlleleEffectNone() {
        super();
    }

    public boolean isCombinable() {
        return true;
    }

    public IEffectData validateStorage(IEffectData storedData) {
        return storedData;
    }

    public String getUID() {
        return "binnie.flowerEffectNone";
    }

    public boolean isDominant() {
        return false;
    }

    public String getName() {
        return "None";
    }

    public IEffectData doEffect(IFlowerGenome genome, IEffectData storedData, World world, int x, int y, int z) {
        return storedData;
    }

    public String getUnlocalizedName() {
        return this.getUID();
    }
}
