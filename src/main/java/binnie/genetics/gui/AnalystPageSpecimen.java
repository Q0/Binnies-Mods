package binnie.genetics.gui;

import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import forestry.api.lepidopterology.IButterfly;

public class AnalystPageSpecimen extends ControlAnalystPage {
    public AnalystPageSpecimen(IWidget parent, IArea area, IButterfly ind) {
        super(parent, area);
        this.setColour(3355443);
        int y = 4;
        (new ControlTextCentered(this, (float) y, "§nSpecimen")).setColour(this.getColour());
        y = y + 12;
        float w = (this.w() - 16.0F) * ind.getSize();
        new ControlIndividualDisplay(this, (this.w() - w) / 2.0F, (float) y + (this.w() - w) / 2.0F, w, ind);
    }

    public String getTitle() {
        return "Specimen";
    }
}
