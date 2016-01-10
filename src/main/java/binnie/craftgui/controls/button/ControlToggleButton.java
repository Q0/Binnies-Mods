package binnie.craftgui.controls.button;

import binnie.craftgui.controls.button.ControlButton;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventToggleButtonClicked;

public class ControlToggleButton extends ControlButton {
   boolean toggled;

   public void onMouseClick(EventMouse.Down event) {
      this.callEvent(new EventToggleButtonClicked(this, this.toggled));
   }

   public ControlToggleButton(IWidget parent, int x, int y, int width, int height) {
      super(parent, (float)x, (float)y, (float)width, (float)height);
   }
}
