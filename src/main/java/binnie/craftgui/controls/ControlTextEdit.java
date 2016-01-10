package binnie.craftgui.controls;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventKey;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventTextEdit;
import binnie.craftgui.events.EventWidget;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.client.gui.GuiTextField;

public class ControlTextEdit extends Control implements IControlValue {
   GuiTextField field = new GuiTextField(this.getWindow().getGui().getFontRenderer(), 0, 0, 10, 10);
   private String cachedValue = "";

   public ControlTextEdit(IWidget parent, float x, float y, float width, float height) {
      super(parent, x, y, width, height);
      this.addAttribute(Attribute.CanFocus);
      this.addAttribute(Attribute.MouseOver);
      this.field.setEnableBackgroundDrawing(false);
      this.addEventHandler((new EventKey.Down.Handler() {
         public void onEvent(EventKey.Down event) {
            ControlTextEdit.this.field.textboxKeyTyped(event.getCharacter(), event.getKey());
            String text = ControlTextEdit.this.getValue();
            if(!text.equals(ControlTextEdit.this.cachedValue)) {
               ControlTextEdit.this.cachedValue = text;
               ControlTextEdit.this.callEvent(new EventTextEdit(ControlTextEdit.this, ControlTextEdit.this.cachedValue));
               ControlTextEdit.this.onTextEdit(ControlTextEdit.this.cachedValue);
            }

         }
      }).setOrigin(EventHandler.Origin.Self, this));
      this.addEventHandler((new EventMouse.Down.Handler() {
         public void onEvent(EventMouse.Down event) {
            ControlTextEdit.this.field.mouseClicked((int)ControlTextEdit.this.getRelativeMousePosition().x(), (int)ControlTextEdit.this.getRelativeMousePosition().y(), event.getButton());
         }
      }).setOrigin(EventHandler.Origin.Self, this));
      this.addEventHandler((new EventWidget.GainFocus.Handler() {
         public void onEvent(EventWidget.GainFocus event) {
            ControlTextEdit.this.field.setFocused(true);
         }
      }).setOrigin(EventHandler.Origin.Self, this));
      this.addEventHandler((new EventWidget.LoseFocus.Handler() {
         public void onEvent(EventWidget.LoseFocus event) {
            ControlTextEdit.this.field.setFocused(false);
         }
      }).setOrigin(EventHandler.Origin.Self, this));
   }

   public String getValue() {
      return this.field.getText() == null?"":this.field.getText();
   }

   public void setValue(String value) {
      if(!this.getValue().equals(value)) {
         this.field.setText(value);
         this.field.setCursorPosition(0);
      }

   }

   public void onUpdateClient() {
   }

   protected void onTextEdit(String value) {
   }

   public void onRenderBackground() {
      CraftGUI.Render.texture((Object)CraftGUITexture.Slot, (IArea)this.getArea());
      this.renderTextField();
   }

   protected void renderTextField() {
      this.field.width = (int)this.w();
      this.field.height = (int)this.h();
      this.field.xPosition = (int)((this.h() - 8.0F) / 2.0F);
      this.field.yPosition = (int)((this.h() - 8.0F) / 2.0F);
      this.field.drawTextBox();
   }
}
