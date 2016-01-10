package binnie.extratrees.carpentry;

import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.carpentry.DesignSystem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DesignerManager {
   public static DesignerManager instance = new DesignerManager();
   List systems = new ArrayList();

   public DesignerManager() {
      super();
   }

   public void registerDesignSystem(IDesignSystem system) {
      this.systems.add(system);
   }

   public Collection getDesignSystems() {
      return this.systems;
   }

   public void addDesignSystem(DesignSystem system) {
      this.systems.add(system);
   }
}
