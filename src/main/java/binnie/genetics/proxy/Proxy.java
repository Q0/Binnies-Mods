package binnie.genetics.proxy;

import binnie.core.proxy.BinnieModProxy;
import binnie.genetics.Genetics;
import binnie.genetics.proxy.IGeneticsProxy;

public class Proxy extends BinnieModProxy implements IGeneticsProxy {
   public Proxy() {
      super(Genetics.instance);
   }
}
