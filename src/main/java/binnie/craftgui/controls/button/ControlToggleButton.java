package binnie.craftgui.controls.button;

import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventToggleButtonClicked;

public class ControlToggleButton extends ControlButton {
    boolean toggled;

    public ControlToggleButton(final IWidget parent, final int x, final int y, final int width, final int height) {
        super(parent, x, y, width, height);
    }

    public void onMouseClick(final EventMouse.Down event) {
        this.callEvent(new EventToggleButtonClicked(this, this.toggled));
    }
}
