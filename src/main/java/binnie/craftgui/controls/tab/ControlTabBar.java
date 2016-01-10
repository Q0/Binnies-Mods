package binnie.craftgui.controls.tab;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventValueChanged;

import java.util.Collection;

public class ControlTabBar extends Control implements IControlValue {
    Object value;
    Position position;

    public ControlTabBar(IWidget parent, float x, float y, float width, float height, Position position) {
        super(parent, x, y, width, height);
        this.position = position;
        this.addEventHandler((new EventValueChanged.Handler() {
            public void onEvent(EventValueChanged event) {
                ControlTabBar.this.setValue(event.getValue());
            }
        }).setOrigin(EventHandler.Origin.DirectChild, this));
    }

    public void setValues(Collection values) {
        int i = 0;

        while (i < this.getWidgets().size()) {
            this.deleteChild((IWidget) this.getWidgets().get(0));
        }

        float length = (float) values.size();
        int tabDimension = (int) (this.getSize().y() / length);
        if (this.position == Position.Top || this.position == Position.Bottom) {
            tabDimension = (int) (this.getSize().x() / length);
        }

        int i = 0;

        for (T value : values) {
            if (this.position != Position.Top && this.position != Position.Bottom) {
                this.createTab(0.0F, (float) (i * tabDimension), this.getSize().x(), (float) tabDimension, value);
            } else {
                this.createTab((float) (i * tabDimension), 0.0F, (float) tabDimension, this.getSize().y(), value);
            }

            ++i;
        }

        if (this.value == null && !values.isEmpty()) {
            this.setValue(values.iterator().next());
        }

    }

    public ControlTab createTab(float x, float y, float w, float h, Object value) {
        return new ControlTab(this, x, y, w, h, value);
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        boolean change = this.value != value;
        this.value = value;
        if (change) {
            this.callEvent(new EventValueChanged(this, value));
        }

    }

    public Position getDirection() {
        return this.position;
    }
}
