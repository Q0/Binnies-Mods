package binnie.craftgui.controls.core;

import binnie.craftgui.core.IWidget;

public interface IControlValue extends IWidget {
   Object getValue();

   void setValue(Object var1);
}
