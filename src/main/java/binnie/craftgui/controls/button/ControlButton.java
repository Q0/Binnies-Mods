package binnie.craftgui.controls.button;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventButtonClicked;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlButton extends Control {
   private ControlText textWidget;
   private String text;

   public ControlButton(IWidget parent, float x, float y, float width, float height) {
      super(parent, x, y, width, height);
      this.addAttribute(Attribute.MouseOver);
      this.addEventHandler((new EventMouse.Down.Handler() {
         public void onEvent(EventMouse.Down event) {
            ControlButton.this.callEvent(new EventButtonClicked(ControlButton.this.getWidget()));
            ControlButton.this.onMouseClick(event);
         }
      }).setOrigin(EventHandler.Origin.Self, this));
   }

   protected void onMouseClick(EventMouse.Down event) {
   }

   public ControlButton(IWidget parent, float x, float y, float width, float height, String text) {
      this(parent, x, y, width, height);
      this.text = text;
      this.textWidget = new ControlText(this, this.getArea(), text, TextJustification.MiddleCenter);
   }

   public void onUpdateClient() {
      if(this.textWidget != null) {
         this.textWidget.setValue(this.getText());
      }

   }

   public String getText() {
      return this.text;
   }

   public void onRenderBackground() {
      Object texture = CraftGUITexture.ButtonDisabled;
      if(this.isMouseOver()) {
         texture = CraftGUITexture.ButtonHighlighted;
      } else if(this.isEnabled()) {
         texture = CraftGUITexture.Button;
      }

      CraftGUI.Render.texture(texture, this.getArea());
   }
}
