package binnie.botany.core;

import binnie.botany.api.IFlowerRoot;
import binnie.botany.genetics.FlowerHelper;

public class BotanyCore {
   public static final int CHANCE_INTERPOLLINATION = 20;
   public static IFlowerRoot speciesRoot = new FlowerHelper();

   public BotanyCore() {
      super();
   }

   public static IFlowerRoot getFlowerRoot() {
      return speciesRoot;
   }
}
