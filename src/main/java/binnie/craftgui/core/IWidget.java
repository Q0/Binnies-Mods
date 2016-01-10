package binnie.craftgui.core;

import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.Event;
import binnie.craftgui.events.EventHandler;

import java.util.List;

public interface IWidget {
    IWidget getParent();

    void deleteChild(IWidget var1);

    void deleteAllChildren();

    ITopLevelWidget getSuperParent();

    boolean isTopLevel();

    IPoint getPosition();

    IPoint pos();

    void setPosition(IPoint var1);

    IPoint getSize();

    IPoint size();

    void setSize(IPoint var1);

    IPoint getOriginalPosition();

    IPoint getAbsolutePosition();

    IPoint getOriginalAbsolutePosition();

    IPoint getOffset();

    IArea getArea();

    IArea area();

    void setOffset(IPoint var1);

    IPoint getMousePosition();

    IPoint getRelativeMousePosition();

    void setColour(int var1);

    int getColour();

    void render();

    void updateClient();

    void enable();

    void disable();

    void show();

    void hide();

    boolean calculateIsMouseOver();

    boolean isEnabled();

    boolean isVisible();

    boolean isFocused();

    boolean isMouseOver();

    boolean isDragged();

    boolean isChildVisible(IWidget var1);

    boolean isChildEnabled(IWidget var1);

    boolean canMouseOver();

    boolean canFocus();

    IWidget addWidget(IWidget var1);

    List getWidgets();

    void callEvent(Event var1);

    void recieveEvent(Event var1);

    void onUpdateClient();

    void delete();

    void onDelete();

    Object getWidget(Class var1);

    IArea getCroppedZone();

    void setCroppedZone(IWidget var1, IArea var2);

    boolean isCroppedWidet();

    IWidget getCropWidget();

    boolean isMouseOverWidget(IPoint var1);

    int getLevel();

    boolean isDescendant(IWidget var1);

    List getAttributes();

    boolean hasAttribute(IWidgetAttribute var1);

    boolean addAttribute(IWidgetAttribute var1);

    void addEventHandler(EventHandler var1);

    void addSelfEventHandler(EventHandler var1);

    boolean contains(IPoint var1);

    float x();

    float y();

    float w();

    float h();

    void onRender(RenderStage var1);
}
