package binnie.craftgui.controls.listbox;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlOption;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventWidget;

public class ControlTextOption extends ControlOption {
   protected ControlText textWidget;

   public ControlTextOption(ControlList controlList, Object option, String optionName, int y) {
      super(controlList, option, y);
      this.textWidget = null;
      this.textWidget = new ControlText(this, this.getArea(), optionName, TextJustification.MiddleCenter);
      this.addEventHandler((new EventWidget.ChangeColour.Handler() {
         public void onEvent(EventWidget.ChangeColour event) {
            ControlTextOption.this.textWidget.setColour(ControlTextOption.this.getColour());
         }
      }).setOrigin(EventHandler.Origin.Self, this));
   }

   public ControlTextOption(ControlList controlList, Object option, int y) {
      this(controlList, option, option.toString(), y);
   }

   public String getText() {
      return this.textWidget.getValue();
   }
}
