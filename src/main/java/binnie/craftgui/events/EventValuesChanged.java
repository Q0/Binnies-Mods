package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.Event;

public class EventValuesChanged extends Event {
   public Object[] values;

   public EventValuesChanged(IWidget origin, Object[] values) {
      super(origin);
      this.values = values;
   }

   public Object[] getValues() {
      return this.values;
   }
}
