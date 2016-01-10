package binnie.craftgui.minecraft;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public abstract class Dialog extends Control {
    public Dialog(IWidget parent, float w, float h) {
        super(parent, (parent.w() - w) / 2.0F, (parent.h() - h) / 2.0F, w, h);
        this.addAttribute(Attribute.MouseOver);
        this.addAttribute(Attribute.AlwaysOnTop);
        this.addAttribute(Attribute.BlockTooltip);
        this.initialise();
        this.addEventHandler((new EventMouse.Down.Handler() {
            public void onEvent(EventMouse.Down event) {
                if (!Dialog.this.getArea().contains(Dialog.this.getRelativeMousePosition())) {
                    Dialog.this.onClose();
                    Dialog.this.getParent().deleteChild(Dialog.this);
                }

            }
        }).setOrigin(EventHandler.Origin.Any, this));
    }

    public abstract void initialise();

    public abstract void onClose();

    public void onRenderBackground() {
        CraftGUI.Render.gradientRect(this.getArea().outset(400), -1442840576, -1442840576);
        CraftGUI.Render.texture((Object) CraftGUITexture.Window, (IArea) this.getArea());
        CraftGUI.Render.texture((Object) CraftGUITexture.TabOutline, (IArea) this.getArea().inset(4));
    }

    public boolean isMouseOverWidget(IPoint relativeMouse) {
        return true;
    }
}
