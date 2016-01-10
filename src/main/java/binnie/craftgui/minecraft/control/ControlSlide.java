package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import org.lwjgl.opengl.GL11;

public class ControlSlide extends Control {
   private IArea expanded;
   private IArea shrunk;
   private boolean slideActive = true;
   private Position anchor;
   private String label = null;

   public ControlSlide(IWidget parent, float x, float y, float w, float h, Position anchor2) {
      super(parent, x, y, w, h);
      this.addAttribute(Attribute.MouseOver);
      this.addAttribute(Attribute.BlockTooltip);
      this.expanded = new IArea(this.getPosition(), this.getSize());
      this.anchor = anchor2.opposite();
      float border = this.anchor.x() != 0?this.expanded.w() - 6.0F:this.expanded.h() - 6.0F;
      this.shrunk = this.expanded.inset(new IBorder(this.anchor, border));
      this.slideActive = false;
   }

   public void onRenderBackground() {
      super.onRenderBackground();
      if(this.label != null) {
         float lw = (float)(CraftGUI.Render.textWidth(this.label) + 16);
         float lh = (float)(CraftGUI.Render.textHeight() + 16);
         boolean hor = this.anchor.x() != 0;
         IArea ar = this.isSlideActive()?this.expanded:this.shrunk;
         IArea tabArea = new IArea(hor?-lh / 2.0F:-lw / 2.0F, hor?-lw / 2.0F:-lh / 2.0F, hor?lh:lw, hor?lw:lh);
         IPoint shift = new IPoint(ar.w() * (float)(1 - this.anchor.x()) / 2.0F, ar.h() * (float)(1 - this.anchor.y()) / 2.0F);
         tabArea = tabArea.shift(shift.x() - (-3.0F + lh / 2.0F) * (float)this.anchor.x(), shift.y() - (-3.0F + lh / 2.0F) * (float)this.anchor.y());
         Texture texture = CraftGUI.Render.getTexture(this.isSlideActive()?CraftGUITexture.Tab:CraftGUITexture.TabDisabled).crop(this.anchor.opposite(), 8.0F);
         CraftGUI.Render.texture(texture, tabArea);
         texture = CraftGUI.Render.getTexture(CraftGUITexture.TabOutline).crop(this.anchor.opposite(), 8.0F);
         CraftGUI.Render.texture(texture, tabArea.inset(2));
         IArea labelArea = new IArea(-lw / 2.0F, 0.0F, lw, lh);
         GL11.glPushMatrix();
         GL11.glTranslatef(shift.x() + (float)this.anchor.x() * 2.0F, shift.y() + (float)this.anchor.y() * 2.0F, 0.0F);
         if(this.anchor.x() != 0) {
            GL11.glRotatef(90.0F, 0.0F, 0.0F, (float)this.anchor.x());
         }

         if(this.anchor.y() > 0) {
            GL11.glTranslatef(0.0F, -lh, 0.0F);
         }

         CraftGUI.Render.text(labelArea, TextJustification.MiddleCenter, this.label, 16777215);
         GL11.glPopMatrix();
      }

      CraftGUI.Render.texture((Object)CraftGUITexture.Window, (IArea)this.getArea());
      Object slideTexture = this.anchor == Position.Bottom?CraftGUITexture.SlideDown:(this.anchor == Position.Top?CraftGUITexture.SlideUp:(this.anchor == Position.Left?CraftGUITexture.SlideLeft:CraftGUITexture.SlideRight));
      CraftGUI.Render.texture(slideTexture, new IPoint(((float)this.anchor.x() + 1.0F) * this.w() / 2.0F - 8.0F, ((float)this.anchor.y() + 1.0F) * this.h() / 2.0F - 8.0F));
   }

   public boolean isSlideActive() {
      return this.slideActive;
   }

   public void onUpdateClient() {
      boolean mouseOver = this.isMouseOverWidget(this.getRelativeMousePosition());
      if(mouseOver != this.slideActive) {
         this.setSlide(mouseOver);
      }

   }

   public boolean isMouseOverWidget(IPoint relativeMouse) {
      return this.getArea().outset(this.isSlideActive()?16:8).outset(new IBorder(this.anchor.opposite(), 16.0F)).contains(relativeMouse);
   }

   public boolean isChildVisible(IWidget child) {
      return this.slideActive;
   }

   public void setSlide(boolean b) {
      this.slideActive = b;
      IArea area = this.isSlideActive()?this.expanded:this.shrunk;
      this.setSize(area.size());
      this.setPosition(area.pos());
   }

   public void setLabel(String l) {
      this.label = l;
   }
}
