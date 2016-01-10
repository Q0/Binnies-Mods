package binnie.craftgui.botany;

import binnie.botany.api.IFlowerColour;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlTextOption;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.CraftGUIUtil;
import binnie.craftgui.core.geometry.IPoint;

public class ControlColourOption extends ControlTextOption {
    ControlColourDisplay controlBee;
    IPoint boxPosition;

    public ControlColourOption(ControlList controlList, IFlowerColour option, int y) {
        super(controlList, option, option.getName(), y);
        this.setSize(new IPoint(this.getSize().x(), 20.0F));
        this.controlBee = new ControlColourDisplay(this, 2.0F, 2.0F);
        this.controlBee.setValue(option);
        this.addAttribute(Attribute.MouseOver);
        CraftGUIUtil.moveWidget(this.textWidget, new IPoint(22.0F, 0.0F));
        this.textWidget.setSize(this.textWidget.getSize().sub(new IPoint(24.0F, 0.0F)));
        int th = (int) CraftGUI.Render.textHeight(this.textWidget.getValue(), this.textWidget.getSize().x());
        int height = Math.max(20, th + 6);
        this.setSize(new IPoint(this.size().x(), (float) height));
        this.textWidget.setSize(new IPoint(this.textWidget.getSize().x(), (float) height));
        this.boxPosition = new IPoint(2.0F, (float) ((height - 18) / 2));
    }
}
