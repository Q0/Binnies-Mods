package binnie.genetics.gui;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;

public class ControlTooltip extends Control implements ITooltip {
   public ControlTooltip(IWidget parent, float x, float y, float w, float h) {
      super(parent, x, y, w, h);
   }

   public void getTooltip(Tooltip tooltip) {
   }
}
