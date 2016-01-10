package binnie.extratrees.genetics;

import forestry.api.arboriculture.EnumGrowthConditions;
import forestry.api.arboriculture.IAlleleGrowth;
import forestry.api.arboriculture.IGrowthProvider;
import forestry.api.arboriculture.ITreeGenome;
import net.minecraft.world.World;

public class ExtraTreeGrowthProvider implements IAlleleGrowth, IGrowthProvider {
   public ExtraTreeGrowthProvider() {
      super();
   }

   public String getUID() {
      return "extratrees.fruit." + this.toString().toLowerCase();
   }

   public boolean isDominant() {
      return true;
   }

   public IGrowthProvider getProvider() {
      return this;
   }

   public boolean canGrow(ITreeGenome genome, World world, int xPos, int yPos, int zPos, int expectedGirth, int expectedHeight) {
      return false;
   }

   public EnumGrowthConditions getGrowthConditions(ITreeGenome genome, World world, int xPos, int yPos, int zPos) {
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
