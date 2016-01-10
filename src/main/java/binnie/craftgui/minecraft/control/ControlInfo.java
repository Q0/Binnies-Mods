package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlInfo extends Control implements ITooltip {
    private String info;

    public ControlInfo(IWidget parent, float x, float y, String info) {
        super(parent, x, y, 16.0F, 16.0F);
        this.addAttribute(Attribute.MouseOver);
        this.info = info;
    }

    public void onRenderBackground() {
        CraftGUI.Render.texture((Object) CraftGUITexture.InfoButton, (IArea) this.getArea());
    }

    public void getTooltip(Tooltip tooltip) {
        tooltip.setType(Tooltip.Type.Information);
        tooltip.add("Info");
        tooltip.add(this.info);
        tooltip.setMaxWidth(200);
    }
}
