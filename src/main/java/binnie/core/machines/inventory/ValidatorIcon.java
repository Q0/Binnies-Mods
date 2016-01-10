package binnie.core.machines.inventory;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.resource.BinnieIcon;
import java.util.ArrayList;
import java.util.List;

public class ValidatorIcon {
   private List iconsInput = new ArrayList();
   private List iconsOutput = new ArrayList();

   public ValidatorIcon(AbstractMod mod, String pathInput, String pathOutput) {
      super();
      this.iconsInput.add(Binnie.Resource.getItemIcon(mod, pathInput));
      this.iconsOutput.add(Binnie.Resource.getItemIcon(mod, pathOutput));
   }

   public BinnieIcon getIcon(boolean input) {
      return input?(BinnieIcon)this.iconsInput.get(0):(BinnieIcon)this.iconsOutput.get(0);
   }
}
