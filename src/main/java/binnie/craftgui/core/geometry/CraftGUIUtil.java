package binnie.craftgui.core.geometry;

import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventValueChanged;

public class CraftGUIUtil {
   public CraftGUIUtil() {
      super();
   }

   public static void alignToWidget(IWidget target, IWidget relativeTo) {
      IPoint startPos = target.getAbsolutePosition();
      IPoint endPos = relativeTo.getAbsolutePosition();
      moveWidget(target, endPos.sub(startPos));
   }

   public static void moveWidget(IWidget target, IPoint movement) {
      target.setPosition(target.getPosition().add(movement));
   }

   public static void horizontalGrid(float px, float py, IWidget... widgets) {
      horizontalGrid(px, py, TextJustification.MiddleCenter, 0.0F, widgets);
   }

   public static void horizontalGrid(float px, float py, TextJustification just, float spacing, IWidget... widgets) {
      float x = 0.0F;
      float h = 0.0F;

      for(IWidget widget : widgets) {
         h = Math.max(h, widget.getSize().y());
      }

      for(IWidget widget : widgets) {
         widget.setPosition(new IPoint(px + x, py + (h - widget.getSize().y()) * just.yOffset));
         x += widget.getSize().x() + spacing;
      }

   }

   public static void verticalGrid(float px, float py, IWidget... widgets) {
      horizontalGrid(px, py, TextJustification.MiddleCenter, 0.0F, widgets);
   }

   public static void verticalGrid(float px, float py, TextJustification just, float spacing, IWidget... widgets) {
      float y = 0.0F;
      float w = 0.0F;

      for(IWidget widget : widgets) {
         w = Math.max(w, widget.getSize().x());
      }

      for(IWidget widget : widgets) {
         widget.setPosition(new IPoint(px + (w - widget.getSize().x()) * just.xOffset, py + y));
         y += widget.getSize().y() + spacing;
      }

   }

   public static void linkWidgets(final IControlValue tab, final IControlValue target) {
      tab.addSelfEventHandler(new EventValueChanged.Handler() {
         public void onEvent(EventValueChanged event) {
            target.setValue(event.getValue());
         }
      });
      target.addSelfEventHandler(new EventValueChanged.Handler() {
         public void onEvent(EventValueChanged event) {
            tab.setValue(event.getValue());
         }
      });
   }
}
