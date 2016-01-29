package binnie.craftgui.controls.listbox;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventWidget;

public class ControlTextOption<T>
        extends ControlOption<T> {
    protected ControlText textWidget = null;

    public ControlTextOption(ControlList<T> controlList, T option, String optionName, int y) {
        super(controlList, option, y);
        this.textWidget = new ControlText(this, this.getArea(), optionName, TextJustification.MiddleCenter);
        this.addEventHandler(new EventWidget.ChangeColour.Handler() {

            @Override
            public void onEvent(EventWidget.ChangeColour event) {
                ControlTextOption.this.textWidget.setColour(ControlTextOption.this.getColour());
            }
        }.setOrigin(EventHandler.Origin.Self, this));
    }

    public ControlTextOption(ControlList<T> controlList, T option, int y) {
        this(controlList, option, option.toString(), y);
    }

    public String getText() {
        return this.textWidget.getValue();
    }

}