package binnie.craftgui.botany;

import binnie.botany.api.IColourMix;
import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlOption;
import binnie.craftgui.mod.database.WindowAbstractDatabase;

public class ControlColourMixItem extends ControlOption {
    ControlColourDisplay itemWidget1 = new ControlColourDisplay(this, 4.0F, 4.0F);
    ControlColourDisplay itemWidget2 = new ControlColourDisplay(this, 44.0F, 4.0F);
    ControlColourDisplay itemWidget3 = new ControlColourDisplay(this, 104.0F, 4.0F);
    ControlColourMixSymbol addSymbol = new ControlColourMixSymbol(this, 24, 4, 0);
    ControlColourMixSymbol arrowSymbol = new ControlColourMixSymbol(this, 64, 4, 1);

    public ControlColourMixItem(ControlList controlList, IColourMix option, int y) {
        super(controlList, option, y);
        BreedingSystem system = ((WindowAbstractDatabase) this.getSuperParent()).getBreedingSystem();
        if (this.getValue() != null) {
            this.itemWidget1.setValue(((IColourMix) this.getValue()).getColour1());
            this.itemWidget2.setValue(((IColourMix) this.getValue()).getColour2());
            this.itemWidget3.setValue(((IColourMix) this.getValue()).getResult());
            this.addSymbol.setValue((IColourMix) this.getValue());
            this.arrowSymbol.setValue((IColourMix) this.getValue());
        }

    }
}
