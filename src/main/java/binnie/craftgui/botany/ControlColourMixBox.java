package binnie.craftgui.botany;

import binnie.botany.api.IColourMix;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.IWidget;

public class ControlColourMixBox extends ControlListBox<IColourMix> {
    private int index;
    private Type type;

    public ControlColourMixBox(final IWidget parent, final int x, final int y, final int width, final int height, final Type type) {
        super(parent, x, y, width, height, 12.0f);
        this.type = type;
    }

    @Override
    public IWidget createOption(final IColourMix value, final int y) {
        return new ControlColourMixItem(this.getContent(), value, y);
    }

    enum Type {
        Resultant,
        Further
    }
}
