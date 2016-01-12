package binnie.extratrees.proxy;

import binnie.extratrees.genetics.FruitSprite;
import forestry.api.core.ForestryAPI;
import forestry.api.core.IIconProvider;

public class ProxyClient extends Proxy implements IExtraTreeProxy {
    @Override
    public void init() {
        ForestryAPI.textureManager.registerIconProvider((IIconProvider) FruitSprite.Average);
    }
}
