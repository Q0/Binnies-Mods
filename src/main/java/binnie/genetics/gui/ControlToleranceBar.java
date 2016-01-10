package binnie.genetics.gui;

import binnie.core.genetics.Tolerance;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import forestry.api.core.EnumTemperature;
import forestry.api.genetics.EnumTolerance;
import java.util.EnumSet;

public abstract class ControlToleranceBar extends Control implements ITooltip {
   private Class enumClass;
   EnumSet tolerated;
   EnumSet fullSet;

   public ControlToleranceBar(IWidget parent, float x, float y, float width, float height, Class clss) {
      super(parent, x, y, width, height);
      this.addAttribute(Attribute.MouseOver);
      this.enumClass = clss;
      this.tolerated = EnumSet.noneOf(this.enumClass);
      this.fullSet = EnumSet.allOf(this.enumClass);
      if(this.enumClass == EnumTemperature.class) {
         this.fullSet.remove(EnumTemperature.NONE);
      }

   }

   public void getTooltip(Tooltip list) {
      int types = this.fullSet.size();
      int type = (int)((float)((int)this.getRelativeMousePosition().x()) / (this.getSize().x() / (float)types));

      for(T tol : this.fullSet) {
         if(tol.ordinal() - (this.enumClass == EnumTemperature.class?1:0) == type) {
            list.add((this.tolerated.contains(tol)?"":"§8") + this.getName(tol));
            return;
         }
      }

   }

   protected abstract String getName(Enum var1);

   protected abstract int getColour(Enum var1);

   public void onRenderBackground() {
      CraftGUI.Render.gradientRect(this.getArea(), -1431655766, -1431655766);
      float w = this.getArea().w() / (float)this.fullSet.size();
      int t = 0;

      for(T value : this.fullSet) {
         int col = (this.tolerated.contains(value)?-16777216:855638016) + this.getColour(value);
         IBorder inset = new IBorder(this.tolerated.contains(value)?1.0F:3.0F);
         CraftGUI.Render.gradientRect((new IArea(w * (float)t, 0.0F, w, this.h())).inset(inset), col, col);
         ++t;
      }

   }

   public void setValues(Enum value, EnumTolerance enumTol) {
      this.tolerated.clear();
      Tolerance tol = Tolerance.get(enumTol);

      for(T full : this.fullSet) {
         if(full.ordinal() <= value.ordinal() + tol.getBounds()[1] && full.ordinal() >= value.ordinal() + tol.getBounds()[0]) {
            this.tolerated.add(full);
         }
      }

   }
}
