package binnie.craftgui.extratrees.kitchen;

import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.window.Panel;

public class ControlDropDownMenu extends Panel {
    public boolean stayOpenOnChildClick = false;

    public ControlDropDownMenu(IWidget parent, float x, float y, float width, float height) {
        super(parent, x, y, width, 2.0F, MinecraftGUI.PanelType.Gray);
        this.addAttribute(Attribute.CanFocus);
    }
}
