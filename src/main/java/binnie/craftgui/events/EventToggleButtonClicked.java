package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.Event;

public class EventToggleButtonClicked extends Event {
   boolean toggled;

   public EventToggleButtonClicked(IWidget origin, boolean toggled) {
      super(origin);
      this.toggled = toggled;
   }

   public boolean isActive() {
      return this.toggled;
   }
}
