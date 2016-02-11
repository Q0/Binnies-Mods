package binnie.genetics.gui;

import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.IButterfly;

public class AnalystPageSpecimen extends ControlAnalystPage {
    public AnalystPageSpecimen(final IWidget parent, final IArea area, final IButterfly ind) {
        super(parent, area);
        this.setColour(3355443);
        int y = 4;
        new ControlTextCentered(this, y, "§nSpecimen").setColour(this.getColour());
        y += 12;
        final float w = (this.w() - 16.0f) * ind.getSize();
        new ControlIndividualDisplay(this, (this.w() - w) / 2.0f, y + (this.w() - w) / 2.0f, w, ind);
    }

    @Override
    public String getTitle() {
        return "Specimen";
    }
}
