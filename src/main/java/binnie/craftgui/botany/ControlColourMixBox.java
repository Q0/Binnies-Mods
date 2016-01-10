package binnie.craftgui.botany;

import binnie.botany.api.IColourMix;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.core.IWidget;

public class ControlColourMixBox extends ControlListBox {
    private int index;
    private ControlColourMixBox.Type type;

    public IWidget createOption(IColourMix value, int y) {
        return new ControlColourMixItem((ControlList) this.getContent(), value, y);
    }

    public ControlColourMixBox(IWidget parent, int x, int y, int width, int height, ControlColourMixBox.Type type) {
        super(parent, (float) x, (float) y, (float) width, (float) height, 12.0F);
        this.type = type;
    }

    static enum Type {
        Resultant,
        Further;

        private Type() {
        }
    }
}
