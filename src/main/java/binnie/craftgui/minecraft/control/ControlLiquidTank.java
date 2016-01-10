package binnie.craftgui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.TankSlot;
import binnie.core.machines.power.ITankMachine;
import binnie.core.machines.power.TankInfo;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.MinecraftTooltip;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.opengl.GL11;

public class ControlLiquidTank extends Control implements ITooltip {
   public static List tankError = new ArrayList();
   private int tankID;
   private boolean horizontal;

   public ControlLiquidTank(IWidget parent, int x, int y) {
      this(parent, x, y, false);
   }

   public ControlLiquidTank(IWidget parent, int x, int y, boolean horizontal) {
      super(parent, (float)x, (float)y, horizontal?60.0F:18.0F, horizontal?18.0F:60.0F);
      this.tankID = 0;
      this.horizontal = false;
      this.horizontal = horizontal;
      this.addAttribute(Attribute.MouseOver);
      this.addSelfEventHandler(new EventMouse.Down.Handler() {
         public void onEvent(EventMouse.Down event) {
            if(event.getButton() == 0) {
               NBTTagCompound nbt = new NBTTagCompound();
               nbt.setByte("id", (byte)ControlLiquidTank.this.tankID);
               Window.get(ControlLiquidTank.this.getWidget()).sendClientAction("tank-click", nbt);
            }

         }
      });
   }

   public void setTankID(int tank) {
      this.tankID = tank;
   }

   public TankInfo getTank() {
      return Window.get(this).getContainer().getTankInfo(this.tankID);
   }

   public boolean isTankValid() {
      return !this.getTank().isEmpty();
   }

   public int getTankCapacity() {
      return (int)this.getTank().getCapacity();
   }

   public void onRenderBackground() {
      CraftGUI.Render.texture((Object)(this.horizontal?CraftGUITexture.HorizontalLiquidTank:CraftGUITexture.LiquidTank), (IPoint)IPoint.ZERO);
      if(this.isMouseOver() && Window.get(this).getGui().isHelpMode()) {
         int c = -1442840576 + MinecraftTooltip.getOutline(Tooltip.Type.Help);
         CraftGUI.Render.gradientRect(this.getArea().inset(1), c, c);
      } else if(tankError.contains(Integer.valueOf(this.tankID))) {
         int c = -1442840576 + MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error);
         CraftGUI.Render.gradientRect(this.getArea().inset(1), c, c);
      } else if(this.getSuperParent().getMousedOverWidget() == this) {
         if(Window.get(this).getGui().getDraggedItem() != null) {
            CraftGUI.Render.gradientRect(this.getArea().inset(1), -1426089575, -1426089575);
         } else {
            CraftGUI.Render.gradientRect(this.getArea().inset(1), -2130706433, -2130706433);
         }
      }

      if(this.isTankValid()) {
         Object content = null;
         float height = this.horizontal?16.0F:58.0F;
         int squaled = (int)(height * (this.getTank().getAmount() / this.getTank().getCapacity()));
         int yPos = (int)height + 1;
         Fluid fluid = this.getTank().liquid.getFluid();
         int hex = fluid.getColor(this.getTank().liquid);
         int r = (hex & 16711680) >> 16;
         int g = (hex & '\uff00') >> 8;
         int b = hex & 255;
         GL11.glColor4f((float)r / 255.0F, (float)g / 255.0F, (float)b / 255.0F, 1.0F);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         IPoint pos = this.getAbsolutePosition();
         IPoint offset = new IPoint(0.0F, height - (float)squaled);
         IArea limited = this.getArea().inset(1);
         if(this.horizontal) {
            limited.setSize(new IPoint(limited.w() - 1.0F, limited.h()));
         }

         CraftGUI.Render.limitArea(new IArea(limited.pos().add(pos).add(offset), limited.size().sub(offset)));
         GL11.glEnable(3089);
         BinnieCore.proxy.bindTexture(TextureMap.locationItemsTexture);

         for(int y = 0; (float)y < height; y += 16) {
            for(int x = 0; x < (this.horizontal?58:16); x += 16) {
               IIcon icon = fluid.getIcon();
               CraftGUI.Render.iconBlock(new IPoint((float)(1 + x), (float)(1 + y)), icon);
            }
         }

         GL11.glDisable(3089);
         GL11.glDisable(3042);
      }

   }

   public void onRenderForeground() {
      CraftGUI.Render.texture((Object)(this.horizontal?CraftGUITexture.HorizontalLiquidTankOverlay:CraftGUITexture.LiquidTankOverlay), (IPoint)IPoint.ZERO);
      if(this.isMouseOver() && Window.get(this).getGui().isHelpMode()) {
         IArea area = this.getArea();
         CraftGUI.Render.colour(MinecraftTooltip.getOutline(Tooltip.Type.Help));
         CraftGUI.Render.texture((Object)CraftGUITexture.Outline, (IArea)area.outset(1));
      }

      if(tankError.contains(Integer.valueOf(this.tankID))) {
         IArea area = this.getArea();
         CraftGUI.Render.colour(MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error));
         CraftGUI.Render.texture((Object)CraftGUITexture.Outline, (IArea)area.outset(1));
      }

   }

   public void getHelpTooltip(Tooltip tooltip) {
      if(this.getTankSlot() != null) {
         TankSlot slot = this.getTankSlot();
         tooltip.add(slot.getName());
         tooltip.add("Capacity: " + this.getTankCapacity() + " mB");
         tooltip.add("Insert Side: " + MachineSide.asString(slot.getInputSides()));
         tooltip.add("Extract Side: " + MachineSide.asString(slot.getOutputSides()));
         if(slot.isReadOnly()) {
            tooltip.add("Output Only Tank");
         }

         tooltip.add("Accepts: " + (slot.getValidator() == null?"Any Item":slot.getValidator().getTooltip()));
      }
   }

   public void getTooltip(Tooltip tooltip) {
      if(this.isTankValid()) {
         int percentage = (int)(100.0D * (double)this.getTank().getAmount() / (double)this.getTankCapacity());
         tooltip.add(this.getTank().getName());
         tooltip.add(percentage + "% full");
         tooltip.add((int)this.getTank().getAmount() + " mB");
      } else {
         tooltip.add("Empty");
      }
   }

   private TankSlot getTankSlot() {
      ITankMachine tank = (ITankMachine)Machine.getInterface(ITankMachine.class, Window.get(this).getInventory());
      return tank != null?tank.getTankSlot(this.tankID):null;
   }
}
