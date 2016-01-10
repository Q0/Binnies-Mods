package binnie.genetics.gui;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import java.text.DecimalFormat;

public abstract class ControlAnalystPage extends Control {
   public ControlAnalystPage(IWidget parent, IArea area) {
      super(parent, area);
      this.hide();
   }

   public void onRenderBackground() {
   }

   public abstract String getTitle();

   protected String getTimeString(float time) {
      float seconds = time / 20.0F;
      float minutes = seconds / 60.0F;
      float hours = minutes / 60.0F;
      DecimalFormat df = new DecimalFormat("#.0");
      return hours > 1.0F?df.format((double)hours) + " hours":(minutes > 1.0F?df.format((double)minutes) + " min.":df.format((double)seconds) + " sec.");
   }
}
