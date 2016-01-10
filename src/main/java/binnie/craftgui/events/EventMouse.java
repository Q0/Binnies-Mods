package binnie.craftgui.events;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.Event;
import binnie.craftgui.events.EventHandler;

public abstract class EventMouse extends Event {
   public EventMouse(IWidget origin) {
      super(origin);
   }

   public static class Button extends EventMouse {
      int x;
      int y;
      int button;

      public int getX() {
         return this.x;
      }

      public int getY() {
         return this.y;
      }

      public int getButton() {
         return this.button;
      }

      public Button(IWidget currentMousedOverWidget, int x, int y, int button) {
         super(currentMousedOverWidget);
         this.x = x;
         this.y = y;
         this.button = button;
      }
   }

   public static class Down extends EventMouse.Button {
      public Down(IWidget currentMousedOverWidget, int x, int y, int button) {
         super(currentMousedOverWidget, x, y, button);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventMouse.Down.class);
         }
      }
   }

   public static class Drag extends EventMouse.Move {
      public Drag(IWidget draggedWidget, float dx, float dy) {
         super(draggedWidget, dx, dy);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventMouse.Drag.class);
         }
      }
   }

   public static class Move extends EventMouse {
      float dx;
      float dy;

      public float getDx() {
         return this.dx;
      }

      public float getDy() {
         return this.dy;
      }

      public Move(IWidget origin, float dx, float dy) {
         super(origin);
         this.dx = dx;
         this.dy = dy;
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventMouse.Move.class);
         }
      }
   }

   public static class Up extends EventMouse.Button {
      public Up(IWidget currentMousedOverWidget, int x, int y, int button) {
         super(currentMousedOverWidget, x, y, button);
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventMouse.Up.class);
         }
      }
   }

   public static class Wheel extends EventMouse {
      int dWheel = 0;

      public Wheel(IWidget origin, int dWheel) {
         super(origin);
         this.dWheel = dWheel / 28;
      }

      public int getDWheel() {
         return this.dWheel;
      }

      public abstract static class Handler extends EventHandler {
         public Handler() {
            super(EventMouse.Wheel.class);
         }
      }
   }
}
