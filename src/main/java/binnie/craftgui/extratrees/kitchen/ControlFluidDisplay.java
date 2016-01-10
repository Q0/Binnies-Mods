package binnie.craftgui.extratrees.kitchen;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.Window;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class ControlFluidDisplay extends Control implements ITooltip {
   FluidStack itemStack;
   public boolean hastooltip;

   public void setTooltip() {
      this.hastooltip = true;
      this.addAttribute(Attribute.MouseOver);
   }

   public ControlFluidDisplay(IWidget parent, float f, float y) {
      this(parent, f, y, 16.0F);
   }

   public ControlFluidDisplay(IWidget parent, float f, float y, FluidStack stack, boolean tooltip) {
      this(parent, f, y, 16.0F);
      this.setItemStack(stack);
      if(tooltip) {
         this.setTooltip();
      }

   }

   public ControlFluidDisplay(IWidget parent, float x, float y, float size) {
      super(parent, x, y, size, size);
      this.itemStack = null;
      this.hastooltip = false;
   }

   public void onRenderForeground() {
      if(this.itemStack != null) {
         IPoint relativeToWindow = this.getAbsolutePosition().sub(this.getSuperParent().getPosition());
         if(relativeToWindow.x() <= Window.get(this).getSize().x() + 100.0F && relativeToWindow.y() <= Window.get(this).getSize().y() + 100.0F) {
            if(this.itemStack != null) {
               Fluid fluid = this.itemStack.getFluid();
               int hex = fluid.getColor(this.itemStack);
               int r = (hex & 16711680) >> 16;
               int g = (hex & '\uff00') >> 8;
               int b = hex & 255;
               IIcon icon = this.itemStack.getFluid().getIcon(this.itemStack);
               GL11.glColor4f((float)r / 255.0F, (float)g / 255.0F, (float)b / 255.0F, 1.0F);
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               if(this.getSize().x() != 16.0F) {
                  GL11.glPushMatrix();
                  float scale = this.getSize().x() / 16.0F;
                  GL11.glScalef(scale, scale, 1.0F);
                  CraftGUI.Render.iconBlock(IPoint.ZERO, this.itemStack.getFluid().getIcon(this.itemStack));
                  GL11.glPopMatrix();
               } else {
                  CraftGUI.Render.iconBlock(IPoint.ZERO, this.itemStack.getFluid().getIcon(this.itemStack));
               }

               GL11.glDisable(3042);
            }

         }
      }
   }

   public void setItemStack(FluidStack itemStack) {
      this.itemStack = itemStack;
   }

   public void getTooltip(Tooltip tooltip) {
      if(this.hastooltip && this.itemStack != null) {
         tooltip.add(this.itemStack.getLocalizedName());
      }

   }
}
