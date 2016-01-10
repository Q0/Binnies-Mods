package binnie.craftgui.controls.page;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;

public class ControlPage extends Control implements IControlValue {
   Object value;

   public ControlPage(IWidget parent, Object value) {
      this(parent, 0.0F, 0.0F, parent.w(), parent.h(), value);
   }

   public ControlPage(IWidget parent, float x, float y, float w, float h, Object value) {
      super(parent, x, y, w, h);
      this.setValue(value);
      if(parent instanceof IControlValue && ((IControlValue)parent).getValue() == null) {
         ((IControlValue)parent).setValue(value);
      }

   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object value) {
      this.value = value;
   }
}
