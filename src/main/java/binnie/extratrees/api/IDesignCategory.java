package binnie.extratrees.api;

import binnie.extratrees.api.IDesign;
import java.util.List;

public interface IDesignCategory {
   String getName();

   List getDesigns();

   void addDesign(IDesign var1);

   String getId();
}
