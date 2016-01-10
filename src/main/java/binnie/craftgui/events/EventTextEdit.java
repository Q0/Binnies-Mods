package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventValueChanged;

public class EventTextEdit extends EventValueChanged {
   public EventTextEdit(IWidget origin, String text) {
      super(origin, text);
   }

   public abstract static class Handler extends EventHandler {
      public Handler() {
         super(EventTextEdit.class);
      }
   }
}
