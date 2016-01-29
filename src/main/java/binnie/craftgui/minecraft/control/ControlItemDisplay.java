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
    public boolean hastooltip;
    private ItemStack itemStack;
    private boolean rotating;

    public ControlItemDisplay(final IWidget parent, final float x, final float y) {
        this(parent, x, y, 16.0f);
    }

    public ControlItemDisplay(final IWidget parent, final float f, final float y, final ItemStack stack, final boolean tooltip) {
        this(parent, f, y, 16.0f);
        this.setItemStack(stack);
        if (tooltip) {
            this.setTooltip();
        }
    }

    public ControlItemDisplay(final IWidget parent, final float x, final float y, final float size) {
        super(parent, x, y, size, size);
        this.itemStack = null;
        this.hastooltip = false;
        this.rotating = false;
    }

    public void setTooltip() {
        this.hastooltip = true;
        this.addAttribute(Attribute.MouseOver);
    }

    @Override
    public void onRenderBackground() {
        final IPoint relativeToWindow = this.getAbsolutePosition().sub(this.getSuperParent().getPosition());
        if (relativeToWindow.x() > Window.get(this).getSize().x() + 100.0f || relativeToWindow.y() > Window.get(this).getSize().y() + 100.0f) {
            return;
        }
        if (this.itemStack != null) {
            if (this.getSize().x() != 16.0f) {
                GL11.glPushMatrix();
                final float scale = this.getSize().x() / 16.0f;
                GL11.glScalef(scale, scale, 1.0f);
                BinnieCore.proxy.getMinecraftInstance();
                final float phase = Minecraft.getSystemTime() / 20.0f;
                CraftGUI.Render.item(IPoint.ZERO, this.itemStack, this.rotating);
                GL11.glPopMatrix();
            } else {
                CraftGUI.Render.item(IPoint.ZERO, this.itemStack, this.rotating);
            }
        }
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setItemStack(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void getTooltip(final Tooltip tooltip) {
        if (this.hastooltip && this.itemStack != null) {
            tooltip.add(this.itemStack.getTooltip(((Window) this.getSuperParent()).getPlayer(), false));
        }
        super.getTooltip(tooltip);
    }

    public void setRotating() {
        this.rotating = true;
    }
}
