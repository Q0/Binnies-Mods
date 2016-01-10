package binnie.craftgui.window;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class Panel extends Control {
   Panel.IPanelType type;

   public Panel(IWidget parent, float x, float y, float width, float height, Panel.IPanelType type) {
      super(parent, x, y, width, height);
      this.type = type;
   }

   public Panel(IWidget parent, IArea area, Panel.IPanelType type) {
      this(parent, area.x(), area.y(), area.w(), area.h(), type);
   }

   public Panel.IPanelType getType() {
      return this.type;
   }

   public void onRenderBackground() {
      Panel.IPanelType panelType = this.getType();
      if(panelType instanceof MinecraftGUI.PanelType) {
         switch((MinecraftGUI.PanelType)panelType) {
         case Black:
            CraftGUI.Render.texture((Object)CraftGUITexture.PanelBlack, (IArea)this.getArea());
            break;
         case Gray:
            CraftGUI.Render.texture((Object)CraftGUITexture.PanelGray, (IArea)this.getArea());
            break;
         case Tinted:
            CraftGUI.Render.texture((Object)CraftGUITexture.PanelTinted, (IArea)this.getArea());
            break;
         case Outline:
            CraftGUI.Render.texture((Object)CraftGUITexture.Outline, (IArea)this.getArea());
            break;
         case TabOutline:
            CraftGUI.Render.texture((Object)CraftGUITexture.TabOutline, (IArea)this.getArea());
         case Coloured:
         }
      }

   }

   public interface IPanelType {
   }
}
