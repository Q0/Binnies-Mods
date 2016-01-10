package binnie.craftgui.controls.core;

import binnie.craftgui.controls.core.IControlValue;
import java.util.Collection;

public interface IControlValues extends IControlValue {
   Collection getValues();

   void setValues(Collection var1);
}
