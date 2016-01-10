package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.Event;

public class EventCycleChanged extends Event {
   public int value;

   public EventCycleChanged(IWidget origin, int value) {
      super(origin);
      this.value = value;
   }

   public int getValue() {
      return this.value;
   }
}
