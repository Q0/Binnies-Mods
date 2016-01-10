package binnie.core.machines.storage;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.minecraft.EnumColor;

class ControlColourSelector extends Control implements ITooltip, IControlValue {
   private EnumColor value;

   public ControlColourSelector(IWidget parent, float x, float y, float w, float h, EnumColor value) {
      super(parent, x, y, w, h);
      this.setValue(value);
      this.addAttribute(Attribute.MouseOver);
   }

   public void getTooltip(Tooltip tooltip) {
      tooltip.add(this.value.toString());
   }

   public EnumColor getValue() {
      return this.value;
   }

   public void setValue(EnumColor value) {
      this.value = value;
      this.setColour(this.getValue().getColour());
   }

   public void onRenderBackground() {
      super.onRenderBackground();
      CraftGUI.Render.gradientRect(this.getArea(), -16777216 + this.value.getColour(), -16777216 + this.value.getColour());
   }
}
