package binnie.craftgui.extratrees.kitchen;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventWidget;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraftforge.fluids.FluidStack;

public class ControlSlotFluid extends Control implements ITooltip {
    ControlFluidDisplay itemDisplay;
    FluidStack fluidStack;

    public ControlSlotFluid(IWidget parent, int x, int y, FluidStack fluid) {
        this(parent, x, y, 18, fluid);
    }

    public ControlSlotFluid(IWidget parent, int x, int y, int size, FluidStack fluid) {
        super(parent, (float) x, (float) y, (float) size, (float) size);
        this.addAttribute(Attribute.MouseOver);
        this.itemDisplay = new ControlFluidDisplay(this, 1.0F, 1.0F, (float) (size - 2));
        this.fluidStack = fluid;
        this.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
            public void onEvent(EventWidget.ChangeSize event) {
                if (ControlSlotFluid.this.itemDisplay != null) {
                    ControlSlotFluid.this.itemDisplay.setSize(ControlSlotFluid.this.getSize().sub(new IPoint(2.0F, 2.0F)));
                }

            }
        });
    }

    public void onRenderBackground() {
        int size = (int) this.getSize().x();
        CraftGUI.Render.texture((Object) CraftGUITexture.Slot, (IArea) this.getArea());
        if (this.getSuperParent().getMousedOverWidget() == this) {
            CraftGUI.Render.gradientRect(new IArea(new IPoint(1.0F, 1.0F), this.getArea().size().sub(new IPoint(2.0F, 2.0F))), -2130706433, -2130706433);
        }

    }

    public void onUpdateClient() {
        super.onUpdateClient();
        this.itemDisplay.setItemStack(this.getFluidStack());
    }

    public void getTooltip(Tooltip tooltip) {
        FluidStack item = this.getFluidStack();
        if (item != null) {
            tooltip.add(item.getFluid().getLocalizedName());
        }
    }

    public FluidStack getFluidStack() {
        return this.fluidStack;
    }
}
