package binnie.craftgui.controls.core;

import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.ITooltipHelp;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.Widget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.Window;
import java.util.ArrayList;
import java.util.List;

public class Control extends Widget implements ITooltipHelp, ITooltip {
   List helpStrings;
   List tooltipStrings;
   public int extraLevel;

   public Control(IWidget parent, float x, float y, float w, float h) {
      super(parent);
      this.helpStrings = new ArrayList();
      this.tooltipStrings = new ArrayList();
      this.extraLevel = 0;
      this.setPosition(new IPoint(x, y));
      this.setSize(new IPoint(w, h));
      this.initialise();
   }

   public Control(IWidget parent, IArea area) {
      this(parent, area.x(), area.y(), area.w(), area.h());
   }

   protected void initialise() {
   }

   public void onUpdateClient() {
   }

   public void addHelp(String string) {
      this.helpStrings.add(string);
   }

   public void addHelp(String[] strings) {
      for(String string : strings) {
         this.addHelp(string);
      }

   }

   public void addTooltip(String string) {
      this.addAttribute(Attribute.MouseOver);
      this.tooltipStrings.add(string);
   }

   public void addTooltip(String[] strings) {
      for(String string : strings) {
         this.addTooltip(string);
      }

   }

   public int getLevel() {
      return this.extraLevel + super.getLevel();
   }

   public void getHelpTooltip(Tooltip tooltip) {
      tooltip.add(this.helpStrings);
   }

   public void getTooltip(Tooltip tooltip) {
      tooltip.add(this.tooltipStrings);
   }

   public Window getWindow() {
      return (Window)this.getSuperParent();
   }
}
