package binnie.core;

import binnie.Binnie;
import binnie.core.IInitializable;

public abstract class ManagerBase implements IInitializable {
   public ManagerBase() {
      super();
      Binnie.Managers.add(this);
   }

   public void preInit() {
   }

   public void init() {
   }

   public void postInit() {
   }
}
