package binnie.craftgui.minecraft.control;

import binnie.core.BinnieCore;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ControlItemDisplay extends Control implements ITooltip {
    private ItemStack itemStack;
    public boolean hastooltip;
    private boolean rotating;

    public void setTooltip() {
        this.hastooltip = true;
        this.addAttribute(Attribute.MouseOver);
    }

    public ControlItemDisplay(IWidget parent, float x, float y) {
        this(parent, x, y, 16.0F);
    }

    public ControlItemDisplay(IWidget parent, float f, float y, ItemStack stack, boolean tooltip) {
        this(parent, f, y, 16.0F);
        this.setItemStack(stack);
        if (tooltip) {
            this.setTooltip();
        }

    }

    public ControlItemDisplay(IWidget parent, float x, float y, float size) {
        super(parent, x, y, size, size);
        this.itemStack = null;
        this.hastooltip = false;
        this.rotating = false;
    }

    public void onRenderBackground() {
        IPoint relativeToWindow = this.getAbsolutePosition().sub(this.getSuperParent().getPosition());
        if (relativeToWindow.x() <= Window.get(this).getSize().x() + 100.0F && relativeToWindow.y() <= Window.get(this).getSize().y() + 100.0F) {
            if (this.itemStack != null) {
                if (this.getSize().x() != 16.0F) {
                    GL11.glPushMatrix();
                    float scale = this.getSize().x() / 16.0F;
                    GL11.glScalef(scale, scale, 1.0F);
                    BinnieCore.proxy.getMinecraftInstance();
                    float phase = (float) Minecraft.getSystemTime() / 20.0F;
                    CraftGUI.Render.item(IPoint.ZERO, this.itemStack, this.rotating);
                    GL11.glPopMatrix();
                } else {
                    CraftGUI.Render.item(IPoint.ZERO, this.itemStack, this.rotating);
                }
            }

        }
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void getTooltip(Tooltip tooltip) {
        if (this.hastooltip && this.itemStack != null) {
            tooltip.add(this.itemStack.getTooltip(((Window) this.getSuperParent()).getPlayer(), false));
        }

        super.getTooltip(tooltip);
    }

    public void setRotating() {
        this.rotating = true;
    }
}
