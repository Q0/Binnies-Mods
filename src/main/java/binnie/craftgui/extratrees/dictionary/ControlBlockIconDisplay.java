package binnie.craftgui.extratrees.dictionary;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import net.minecraft.util.IIcon;

public class ControlBlockIconDisplay extends Control {
    IIcon icon;

    public ControlBlockIconDisplay(final IWidget parent, final float x, final float y, final IIcon icon) {
        super(parent, x, y, 18.0f, 18.0f);
        this.icon = icon;
    }

    @Override
    public void onRenderBackground() {
        CraftGUI.Render.iconBlock(IPoint.ZERO, this.icon);
    }
}
