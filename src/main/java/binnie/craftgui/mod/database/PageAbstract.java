package binnie.craftgui.mod.database;

import binnie.craftgui.controls.page.ControlPage;
import binnie.craftgui.core.IWidget;

public abstract class PageAbstract extends ControlPage {
    public PageAbstract(IWidget parent, DatabaseTab tab) {
        super(parent, 0.0F, 0.0F, parent.getSize().x(), parent.getSize().y(), tab);
    }

    public abstract void onValueChanged(Object var1);
}
