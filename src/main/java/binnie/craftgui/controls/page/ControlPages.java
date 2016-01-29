package binnie.craftgui.controls.page;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.controls.core.IControlValues;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventValueChanged;

import java.util.ArrayList;
import java.util.Collection;

public class ControlPages<T>
        extends Control
        implements IControlValues<T>,
        IControlValue<T> {
    T value = null;

    public ControlPages(IWidget parent, float x, float y, float w, float h) {
        super(parent, x, y, w, h);
    }

    @Override
    public boolean isChildVisible(IWidget child) {
        if (child == null) {
            return false;
        }
        return this.value == ((IControlValue) child).getValue();
    }

    @Override
    public void onAddChild(IWidget widget) {
    }

    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public void setValue(T value) {
        if (this.value != value) {
            this.value = value;
            this.callEvent(new EventValueChanged<T>(this, value));
        }
    }

    @Override
    public Collection<T> getValues() {
        ArrayList list = new ArrayList();
        for (IWidget child : this.getWidgets()) {
            list.add(((IControlValue) child).getValue());
        }
        return list;
    }

    @Override
    public void setValues(Collection<T> values) {
    }
}