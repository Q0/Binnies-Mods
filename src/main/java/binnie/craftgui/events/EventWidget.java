package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.Event;
import binnie.craftgui.events.EventHandler;

public class EventWidget extends Event {
   public EventWidget(IWidget origin) {
      super(origin);
   }

   public static class ChangeColour extends EventWidget {
      public ChangeColour(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.ChangeColour.class);
         }
      }
   }

   public static class ChangeOffset extends EventWidget {
      public ChangeOffset(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.ChangeOffset.class);
         }
      }
   }

   public static class ChangePosition extends EventWidget {
      public ChangePosition(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.ChangePosition.class);
         }
      }
   }

   public static class ChangeSize extends EventWidget {
      public ChangeSize(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.ChangeSize.class);
         }
      }
   }

   public static class Disable extends EventWidget {
      public Disable(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.Disable.class);
         }
      }
   }

   public static class Enable extends EventWidget {
      public Enable(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.Enable.class);
         }
      }
   }

   public static class EndDrag extends EventWidget {
      public EndDrag(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.EndDrag.class);
         }
      }
   }

   public static class EndMouseOver extends EventWidget {
      public EndMouseOver(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.EndMouseOver.class);
         }
      }
   }

   public static class GainFocus extends EventWidget {
      public GainFocus(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.GainFocus.class);
         }
      }
   }

   public static class Hide extends EventWidget {
      public Hide(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.Hide.class);
         }
      }
   }

   public static class LoseFocus extends EventWidget {
      public LoseFocus(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.LoseFocus.class);
         }
      }
   }

   public static class Show extends EventWidget {
      public Show(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.Show.class);
         }
      }
   }

   public static class StartDrag extends EventWidget {
      int button;

      public StartDrag(IWidget origin, int button) {
         super(origin);
         this.button = button;
      }

      public int getButton() {
         return this.button;
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.StartDrag.class);
         }
      }
   }

   public static class StartMouseOver extends EventWidget {
      public StartMouseOver(IWidget origin) {
         super(origin);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventWidget.StartMouseOver.class);
         }
      }
   }
}
