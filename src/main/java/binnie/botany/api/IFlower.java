package binnie.botany.api;

import binnie.botany.api.IFlowerGenome;
import forestry.api.genetics.IIndividual;
import net.minecraft.world.World;

public interface IFlower extends IIndividual {
   IFlowerGenome getGenome();

   IFlowerGenome getMate();

   void mate(IFlower var1);

   int getAge();

   void age();

   void setAge(int var1);

   IFlower getOffspring(World var1);

   int getMaxAge();

   boolean isWilted();

   void setWilted(boolean var1);

   boolean hasFlowered();

   void setFlowered(boolean var1);

   void removeMate();
}
