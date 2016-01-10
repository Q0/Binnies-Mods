package binnie.extratrees.proxy;

import binnie.extratrees.genetics.FruitSprite;
import binnie.extratrees.proxy.IExtraTreeProxy;
import binnie.extratrees.proxy.Proxy;
import forestry.api.core.ForestryAPI;

public class ProxyClient extends Proxy implements IExtraTreeProxy {
   public ProxyClient() {
      super();
   }

   public void init() {
      ForestryAPI.textureManager.registerIconProvider(FruitSprite.Average);
   }
}
