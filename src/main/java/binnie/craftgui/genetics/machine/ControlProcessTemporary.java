package binnie.craftgui.genetics.machine;

import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.control.ControlMachineProgress;
import binnie.craftgui.resource.Texture;

public class ControlProcessTemporary extends ControlMachineProgress {
   public ControlProcessTemporary(IWidget parent, int x, int y, int width, int height) {
      super(parent, x, y, (Texture)null, (Texture)null, (Position)null);
      this.setSize(new IPoint((float)width, (float)height));
   }

   public void onRenderBackground() {
      CraftGUI.Render.solid(this.getArea(), -4868683);
      float w = this.getSize().y() * this.progress / 100.0F;
      CraftGUI.Render.solid(new IArea(this.getArea().x(), this.getArea().y(), w, this.getArea().h()), -65536);
   }
}
