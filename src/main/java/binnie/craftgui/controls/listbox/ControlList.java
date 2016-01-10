package binnie.craftgui.controls.listbox;

import binnie.core.util.IValidator;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventValueChanged;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ControlList extends Control implements IControlValue {
    ControlListBox parent;
    Object value = null;
    Map allOptions = new LinkedHashMap();
    Map optionWidgets = new LinkedHashMap();
    boolean creating = false;
    IValidator validator;

    protected ControlList(ControlListBox parent, float x, float y, float w, float h) {
        super(parent, x, y, w, h);
        this.parent = parent;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        if (value != this.value) {
            this.value = value;
            if (value != null && this.optionWidgets.containsKey(value)) {
                IWidget child = (IWidget) this.optionWidgets.get(value);
                this.parent.ensureVisible(child.y(), child.y() + child.h(), this.h());
            }

            this.getParent().callEvent(new EventValueChanged(this.getParent(), value));
        }
    }

    public void setOptions(Collection options) {
        this.deleteAllChildren();
        this.allOptions.clear();
        int i = 0;

        for (T option : options) {
            IWidget optionWidget = ((ControlListBox) this.getParent()).createOption(option, 0);
            if (optionWidget != null) {
                this.allOptions.put(option, optionWidget);
            }

            ++i;
        }

        this.filterOptions();
    }

    public void filterOptions() {
        int height = 0;
        this.optionWidgets.clear();

        for (Entry<T, IWidget> entry : this.allOptions.entrySet()) {
            if (this.isValidOption((IWidget) entry.getValue())) {
                ((IWidget) entry.getValue()).show();
                this.optionWidgets.put(entry.getKey(), entry.getValue());
                ((IWidget) entry.getValue()).setPosition(new IPoint(0.0F, (float) height));
                height = (int) ((float) height + ((IWidget) entry.getValue()).getSize().y());
            } else {
                ((IWidget) entry.getValue()).hide();
            }
        }

        this.creating = true;
        this.setValue(this.getValue());
        this.setSize(new IPoint(this.getSize().x(), (float) height));
    }

    public Collection getOptions() {
        return this.optionWidgets.keySet();
    }

    public Collection getAllOptions() {
        return this.allOptions.keySet();
    }

    public int getIndexOf(Object value) {
        int index = 0;

        for (T option : this.getOptions()) {
            if (option.equals(value)) {
                return index;
            }

            ++index;
        }

        return -1;
    }

    public int getCurrentIndex() {
        return this.getIndexOf(this.getValue());
    }

    public void setIndex(int currentIndex) {
        int index = 0;

        for (T option : this.getOptions()) {
            if (index == currentIndex) {
                this.setValue(option);
                return;
            }

            ++index;
        }

        this.setValue((Object) null);
    }

    private boolean isValidOption(IWidget widget) {
        return this.validator == null ? true : this.validator.isValid(widget);
    }

    public void setValidator(IValidator validator) {
        if (this.validator != validator) {
            this.validator = validator;
            this.filterOptions();
        }

    }
}
