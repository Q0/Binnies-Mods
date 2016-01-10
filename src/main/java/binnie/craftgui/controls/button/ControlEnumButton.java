package binnie.craftgui.controls.button;

import binnie.craftgui.controls.button.ControlButton;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventValueChanged;
import java.util.ArrayList;
import java.util.List;

public class ControlEnumButton extends ControlButton implements IControlValue {
   public static final String eventEnumChanged = "eventEnumButtonChanged";
   private Object currentSelection;
   private List enumConstants = new ArrayList();

   public String getText() {
      return this.currentSelection.toString();
   }

   public void onMouseClick(EventMouse.Down event) {
      int index = this.enumConstants.indexOf(this.currentSelection);
      if(index < this.enumConstants.size() - 1) {
         ++index;
      } else {
         index = 0;
      }

      T newEnum = this.enumConstants.get(index);
      this.setValue(newEnum);
   }

   public void setValue(Object selection) {
      if(this.currentSelection != selection) {
         this.currentSelection = selection;
         this.callEvent(new EventValueChanged(this, this.getValue()));
      }

   }

   public ControlEnumButton(IWidget parent, float x, float y, float width, float height, Object[] values) {
      super(parent, x, y, width, height, "");

      for(T value : values) {
         this.enumConstants.add(value);
      }

      if(values.length > 0) {
         this.currentSelection = values[0];
      }

   }

   public Object getValue() {
      return this.currentSelection;
   }
}
