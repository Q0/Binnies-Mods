package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventWidget;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.item.ItemStack;

public abstract class ControlSlotBase extends Control implements ITooltip {
   private ControlItemDisplay itemDisplay;

   public ControlSlotBase(IWidget parent, float x, float y) {
      this(parent, x, y, 18);
   }

   public ControlSlotBase(IWidget parent, float x, float y, int size) {
      super(parent, x, y, (float)size, (float)size);
      this.addAttribute(Attribute.MouseOver);
      this.itemDisplay = new ControlItemDisplay(this, 1.0F, 1.0F, (float)(size - 2));
      this.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
         public void onEvent(EventWidget.ChangeSize event) {
            if(ControlSlotBase.this.itemDisplay != null) {
               ControlSlotBase.this.itemDisplay.setSize(ControlSlotBase.this.getSize().sub(new IPoint(2.0F, 2.0F)));
            }

         }
      });
   }

   protected void setRotating() {
      this.itemDisplay.setRotating();
   }

   public void onRenderBackground() {
      int size = (int)this.getSize().x();
      CraftGUI.Render.texture((Object)CraftGUITexture.Slot, (IArea)this.getArea());
      if(this.getSuperParent().getMousedOverWidget() == this) {
         CraftGUI.Render.gradientRect(new IArea(new IPoint(1.0F, 1.0F), this.getArea().size().sub(new IPoint(2.0F, 2.0F))), -2130706433, -2130706433);
      }

   }

   public void onUpdateClient() {
      super.onUpdateClient();
      this.itemDisplay.setItemStack(this.getItemStack());
   }

   public void getTooltip(Tooltip tooltip) {
      ItemStack item = this.getItemStack();
      if(item != null) {
         tooltip.add(item.getTooltip(((Window)this.getSuperParent()).getPlayer(), false));
      }
   }

   public abstract ItemStack getItemStack();
}
