package binnie.craftgui.core;

import binnie.craftgui.core.geometry.IPoint;

public interface ITopLevelWidget extends IWidget {
    void setMousePosition(int var1, int var2);

    IPoint getAbsoluteMousePosition();

    IWidget getFocusedWidget();

    IWidget getMousedOverWidget();

    IWidget getDraggedWidget();

    boolean isFocused(IWidget var1);

    boolean isMouseOver(IWidget var1);

    boolean isDragged(IWidget var1);

    void updateTopLevel();

    void widgetDeleted(IWidget var1);

    IPoint getDragDistance();
}
