package binnie.craftgui.controls.listbox;

import binnie.core.util.IValidator;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.events.EventKey;

import java.util.Collection;

public class ControlListBox extends ControlScrollableContent implements IControlValue {
    public ControlListBox(IWidget parent, float x, float y, float w, float h, float scrollBarSize) {
        super(parent, x, y, w, h, scrollBarSize);
    }

    public void initialise() {
        this.setScrollableContent(new ControlList(this, 1.0F, 1.0F, this.w() - 2.0F - this.scrollBarSize, this.h() - 2.0F));
        this.addEventHandler(new EventKey.Down.Handler() {
            public void onEvent(EventKey.Down event) {
                if (ControlListBox.this.calculateIsMouseOver()) {
                    int currentIndex = ((ControlList) ControlListBox.this.getContent()).getCurrentIndex();
                    if (event.getKey() == 208) {
                        ++currentIndex;
                        if (currentIndex >= ((ControlList) ControlListBox.this.getContent()).getOptions().size()) {
                            currentIndex = 0;
                        }
                    } else if (event.getKey() == 200) {
                        --currentIndex;
                        if (currentIndex < 0) {
                            currentIndex = ((ControlList) ControlListBox.this.getContent()).getOptions().size() - 1;
                        }
                    }

                    ((ControlList) ControlListBox.this.getContent()).setIndex(currentIndex);
                }

            }
        });
    }

    public final Object getValue() {
        return ((ControlList) this.getContent()).getValue();
    }

    public final void setValue(Object value) {
        ((ControlList) this.getContent()).setValue(value);
    }

    public void setOptions(Collection options) {
        ((ControlList) this.getContent()).setOptions(options);
    }

    public IWidget createOption(Object value, int y) {
        return new ControlOption((ControlList) this.getContent(), value, y);
    }

    public void setValidator(IValidator validator) {
        ((ControlList) this.getContent()).setValidator(validator);
    }
}
