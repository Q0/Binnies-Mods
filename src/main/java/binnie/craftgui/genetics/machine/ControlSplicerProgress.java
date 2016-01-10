package binnie.craftgui.genetics.machine;

import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.minecraft.control.ControlProgressBase;

public class ControlSplicerProgress extends ControlProgressBase {
   float strength = 0.0F;

   public ControlSplicerProgress(IWidget parent, float x, float y, float w, float h) {
      super(parent, x, y, w, h);
      this.strength = 0.0F;
   }

   public void onRenderBackground() {
      float progress = this.getProcess().getCurrentProgress() / 100.0F;
      float n = (float)this.getProcess().getProcessTime() / 12.0F;
      float spacing = 10.0F;
      float range = this.w();
      float h = 8.0F;
      float ooy = -((n - 1.0F) * spacing) - range / 2.0F;
      float ddy = (n - 1.0F) * spacing + range;
      float oy = ooy + ddy * progress;

      for(int i = 0; (float)i < n; ++i) {
         int seed = 432523;
         int[] colours = new int[]{10027008, 30464, 255, 10057472};
         int c1 = colours[(int)Math.abs(13.0D * Math.sin((double)i) + 48.0D * Math.cos((double)i) + 25.0D * Math.sin((double)(7 * i))) % 4];
         int c2 = colours[(int)Math.abs(23.0D * Math.sin((double)i) + 28.0D * Math.cos((double)i) + 15.0D * Math.sin((double)(7 * i))) % 4];
         int c3 = colours[(int)Math.abs(43.0D * Math.sin((double)i) + 38.0D * Math.cos((double)i) + 55.0D * Math.sin((double)(7 * i))) % 4];
         int c4 = colours[(int)Math.abs(3.0D * Math.sin((double)i) + 18.0D * Math.cos((double)i) + 35.0D * Math.sin((double)(7 * i))) % 4];
         float y = oy + (float)i * spacing;
         if(y > -range / 2.0F && y < range / 2.0F) {
            float percentView = (float)Math.sqrt((double)(1.0F - Math.abs(2.0F * y / range)));
            float offMovement = (this.h() - 2.0F * h) / 2.0F;
            int alpha = 16777216 * (int)(255.0F * percentView);
            c1 = c1 + alpha;
            c2 = c2 + alpha;
            c3 = c3 + alpha;
            c4 = c4 + alpha;
            CraftGUI.Render.solidAlpha(new IArea(this.w() / 2.0F + y, offMovement * percentView, 4.0F, h / 2.0F), c1);
            CraftGUI.Render.solidAlpha(new IArea(this.w() / 2.0F + y, offMovement * percentView + 4.0F, 4.0F, h / 2.0F), y < 0.0F?c2:c3);
            CraftGUI.Render.solidAlpha(new IArea(this.w() / 2.0F + y, this.h() - offMovement * percentView - 8.0F, 4.0F, h / 2.0F), y < 0.0F?c3:c2);
            CraftGUI.Render.solidAlpha(new IArea(this.w() / 2.0F + y, this.h() - offMovement * percentView - 4.0F, 4.0F, h / 2.0F), c4);
         }
      }

   }
}
