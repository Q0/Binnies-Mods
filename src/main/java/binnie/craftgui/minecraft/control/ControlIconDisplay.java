package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import net.minecraft.util.IIcon;

public class ControlIconDisplay extends Control {
    private IIcon icon;

    public ControlIconDisplay(final IWidget parent, final float x, final float y, final IIcon icon) {
        super(parent, x, y, 16.0f, 16.0f);
        this.icon = null;
        this.icon = icon;
    }

    @Override
    public void onRenderForeground() {
        CraftGUI.Render.iconItem(IPoint.ZERO, this.icon);
    }
}
