package binnie.craftgui.controls;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlCheckbox extends Control implements IControlValue {
    boolean value;
    String text;

    public ControlCheckbox(IWidget parent, float x, float y, boolean bool) {
        this(parent, x, y, 0.0F, "", bool);
    }

    public ControlCheckbox(IWidget parent, float x, float y, float w, String text, boolean bool) {
        super(parent, x, y, w > 16.0F ? w : 16.0F, 16.0F);
        this.text = text;
        this.value = bool;
        if (w > 16.0F) {
            (new ControlText(this, new IArea(16.0F, 1.0F, w - 16.0F, 16.0F), text, TextJustification.MiddleCenter)).setColour(4473924);
        }

        this.addAttribute(Attribute.MouseOver);
        this.addEventHandler((new EventMouse.Down.Handler() {
            public void onEvent(EventMouse.Down event) {
                ControlCheckbox.this.toggleValue();
            }
        }).setOrigin(EventHandler.Origin.Self, this));
    }

    protected void onValueChanged(boolean value) {
    }

    public Boolean getValue() {
        return Boolean.valueOf(this.value);
    }

    public void setValue(Boolean value) {
        this.value = value.booleanValue();
        this.onValueChanged(value.booleanValue());
        this.callEvent(new EventValueChanged(this, value));
    }

    public void toggleValue() {
        this.setValue(Boolean.valueOf(!this.getValue().booleanValue()));
    }

    public void onRenderBackground() {
        Object texture = this.getValue().booleanValue() ? CraftGUITexture.CheckboxChecked : CraftGUITexture.Checkbox;
        if (this.isMouseOver()) {
            texture = this.getValue().booleanValue() ? CraftGUITexture.CheckboxCheckedHighlighted : CraftGUITexture.CheckboxHighlighted;
        }

        CraftGUI.Render.texture(texture, IPoint.ZERO);
    }
}
