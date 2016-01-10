package binnie.botany.api;

import binnie.botany.api.IFlowerColour;

public interface IColourMix {
   IFlowerColour getColour1();

   IFlowerColour getColour2();

   boolean isMutation(IFlowerColour var1, IFlowerColour var2);

   int getChance();

   IFlowerColour getResult();
}
