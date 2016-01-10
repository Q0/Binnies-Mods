package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.Event;
import binnie.craftgui.events.EventHandler;

public class EventValueChanged extends Event {
   public Object value;

   public EventValueChanged(IWidget origin, Object value) {
      super(origin);
      this.value = value;
   }

   public Object getValue() {
      return this.value;
   }

   public abstract static class Handler extends EventHandler {
      public Handler() {
         super(EventValueChanged.class);
      }
   }
}
