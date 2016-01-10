package binnie.extrabees.core;

import binnie.Binnie;
import binnie.core.IInitializable;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeItems;
import forestry.api.core.Tabs;

public class ModuleCore implements IInitializable {
   public ModuleCore() {
      super();
   }

   public void preInit() {
      ExtraBees.itemMisc = Binnie.Item.registerMiscItems(ExtraBeeItems.values(), Tabs.tabApiculture);
   }

   public void init() {
      ExtraBeeItems.init();
   }

   public void postInit() {
      ExtraBeeItems.postInit();
   }
}
