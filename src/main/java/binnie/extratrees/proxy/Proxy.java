package binnie.extratrees.proxy;

import binnie.core.proxy.BinnieModProxy;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.proxy.IExtraTreeProxy;

public class Proxy extends BinnieModProxy implements IExtraTreeProxy {
   public Proxy() {
      super(ExtraTrees.instance);
   }
}
