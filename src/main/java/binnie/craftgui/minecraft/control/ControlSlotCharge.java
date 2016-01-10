package binnie.craftgui.minecraft.control;

import binnie.core.machines.Machine;
import binnie.core.machines.inventory.IChargedSlots;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlSlotCharge extends Control {
   private int slot;

   float getCharge() {
      IChargedSlots slots = (IChargedSlots)Machine.getInterface(IChargedSlots.class, Window.get(this).getInventory());
      return slots == null?0.0F:slots.getCharge(this.slot);
   }

   public void onRenderBackground() {
      CraftGUI.Render.texture((Object)CraftGUITexture.PanelBlack, (IArea)this.getArea());
      CraftGUI.Render.texturePercentage(CraftGUI.Render.getTexture(CraftGUITexture.SlotCharge), this.getArea().inset(1), Position.Bottom, this.getCharge());
   }

   public ControlSlotCharge(IWidget parent, int x, int y, int slot) {
      super(parent, (float)x, (float)y, 4.0F, 18.0F);
      this.slot = slot;
   }

   public void getHelpTooltip(Tooltip tooltip) {
      tooltip.add("Charge Remaining: " + (int)(this.getCharge() * 100.0F) + "%");
   }
}
