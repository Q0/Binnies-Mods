package binnie.extratrees.genetics;

import forestry.api.arboriculture.EnumGrowthConditions;
import forestry.api.arboriculture.IAlleleGrowth;
import forestry.api.arboriculture.IGrowthProvider;
import forestry.api.arboriculture.ITreeGenome;
import net.minecraft.world.World;

public class ExtraTreeGrowthProvider implements IAlleleGrowth, IGrowthProvider {
    public String getUID() {
        return "extratrees.fruit." + this.toString().toLowerCase();
    }

    public boolean isDominant() {
        return true;
    }

    public IGrowthProvider getProvider() {
        return this;
    }

    public boolean canGrow(final ITreeGenome genome, final World world, final int xPos, final int yPos, final int zPos, final int expectedGirth, final int expectedHeight) {
        return false;
    }

    public EnumGrowthConditions getGrowthConditions(final ITreeGenome genome, final World world, final int xPos, final int yPos, final int zPos) {
        return null;
    }

    public String getDescription() {
        return null;
    }

    public String[] getInfo() {
        return null;
    }

    public String getName() {
        return this.getProvider().getDescription();
    }

    public String getUnlocalizedName() {
        return this.getUID();
    }
}
