package binnie.botany.genetics;

import binnie.botany.api.IAlleleFlowerEffect;
import binnie.botany.api.IFlowerGenome;
import forestry.api.genetics.IEffectData;
import net.minecraft.world.World;

public class AlleleEffectNone implements IAlleleFlowerEffect {
    public boolean isCombinable() {
        return true;
    }

    public IEffectData validateStorage(final IEffectData storedData) {
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

    @Override
    public IEffectData doEffect(final IFlowerGenome genome, final IEffectData storedData, final World world, final int x, final int y, final int z) {
        return storedData;
    }

    public String getUnlocalizedName() {
        return this.getUID();
    }
}
