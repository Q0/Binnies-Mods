package binnie.craftgui.controls.listbox;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlOption extends Control implements IControlValue {
   Object value;

   public void onUpdateClient() {
      if(this.getValue() != null) {
         int colour = 10526880;
         if(this.isCurrentSelection()) {
            colour = 16777215;
         }

         this.setColour(colour);
      }
   }

   public ControlOption(ControlList controlList, Object option) {
      this(controlList, option, 16);
   }

   public ControlOption(ControlList controlList, Object option, int height) {
      super(controlList, 0.0F, (float)height, controlList.getSize().x(), 20.0F);
      this.value = option;
      if(this.value != null) {
         this.addAttribute(Attribute.MouseOver);
      }

      this.addSelfEventHandler(new EventMouse.Down.Handler() {
         public void onEvent(EventMouse.Down event) {
            ((IControlValue)ControlOption.this.getParent()).setValue(ControlOption.this.getValue());
         }
      });
   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   public boolean isCurrentSelection() {
      return this.getValue() != null && this.getValue().equals(((IControlValue)this.getParent()).getValue());
   }

   public void onRenderForeground() {
      if(this.isCurrentSelection()) {
         CraftGUI.Render.texture((Object)CraftGUITexture.Outline, (IArea)this.getArea());
      }

   }
}
