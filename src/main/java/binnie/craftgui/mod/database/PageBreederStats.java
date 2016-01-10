package binnie.craftgui.mod.database;

import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;

public class PageBreederStats extends Control {
    String player;

    public PageBreederStats(IWidget parent, int w, int h, String player) {
        super(parent, 0.0F, 0.0F, (float) w, (float) h);
        this.player = player;
        new ControlTextCentered(this, 8.0F, "§nStats§r");
        BreedingSystem system = ((WindowAbstractDatabase) this.getSuperParent()).getBreedingSystem();
    }
}
