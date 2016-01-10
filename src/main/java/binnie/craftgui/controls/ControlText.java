package binnie.craftgui.controls;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;

public class ControlText extends Control implements IControlValue {
    private String text;
    private TextJustification align;

    public ControlText(IWidget parent, IPoint pos, String text) {
        this(parent, new IArea(pos, new IPoint(500.0F, 0.0F)), text, TextJustification.TopLeft);
    }

    public ControlText(IWidget parent, String text, TextJustification align) {
        this(parent, parent.getArea(), text, align);
    }

    public ControlText(IWidget parent, IArea area, String text, TextJustification align) {
        super(parent, area.pos().x(), area.pos().y(), area.size().x(), area.size().y());
        this.setValue(text);
        this.align = align;
    }

    public void onRenderBackground() {
        CraftGUI.Render.text(this.getArea(), this.align, this.text, this.getColour());
    }

    public void setValue(String text) {
        this.text = text;
    }

    public String getValue() {
        return this.text;
    }
}
