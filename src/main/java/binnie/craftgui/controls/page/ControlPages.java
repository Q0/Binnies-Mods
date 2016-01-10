package binnie.craftgui.controls.page;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.controls.core.IControlValues;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventValueChanged;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ControlPages extends Control implements IControlValues, IControlValue {
    Object value = null;

    public boolean isChildVisible(IWidget child) {
        return child == null ? false : this.value == ((IControlValue) child).getValue();
    }

    public ControlPages(IWidget parent, float x, float y, float w, float h) {
        super(parent, x, y, w, h);
    }

    public void onAddChild(IWidget widget) {
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        if (this.value != value) {
            this.value = value;
            this.callEvent(new EventValueChanged(this, value));
        }

    }

    public Collection getValues() {
        List<T> list = new ArrayList();

        for (IWidget child : this.getWidgets()) {
            list.add(((IControlValue) child).getValue());
        }

        return list;
    }

    public void setValues(Collection values) {
    }
}
