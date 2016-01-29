package binnie.craftgui.core;

import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.Event;
import binnie.craftgui.events.EventHandler;

import java.util.List;

public interface IWidget {
    IWidget getParent();

    void deleteChild(final IWidget p0);

    void deleteAllChildren();

    ITopLevelWidget getSuperParent();

    boolean isTopLevel();

    IPoint getPosition();

    void setPosition(final IPoint p0);

    IPoint pos();

    IPoint getSize();

    void setSize(final IPoint p0);

    IPoint size();

    IPoint getOriginalPosition();

    IPoint getAbsolutePosition();

    IPoint getOriginalAbsolutePosition();

    IPoint getOffset();

    void setOffset(final IPoint p0);

    IArea getArea();

    IArea area();

    IPoint getMousePosition();

    IPoint getRelativeMousePosition();

    int getColour();

    void setColour(final int p0);

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

    boolean isChildVisible(final IWidget p0);

    boolean isChildEnabled(final IWidget p0);

    boolean canMouseOver();

    boolean canFocus();

    IWidget addWidget(final IWidget p0);

    List<IWidget> getWidgets();

    void callEvent(final Event p0);

    void recieveEvent(final Event p0);

    void onUpdateClient();

    void delete();

    void onDelete();

    <T> T getWidget(final Class<T> p0);

    IArea getCroppedZone();

    void setCroppedZone(final IWidget p0, final IArea p1);

    boolean isCroppedWidet();

    IWidget getCropWidget();

    boolean isMouseOverWidget(final IPoint p0);

    int getLevel();

    boolean isDescendant(final IWidget p0);

    List<IWidgetAttribute> getAttributes();

    boolean hasAttribute(final IWidgetAttribute p0);

    boolean addAttribute(final IWidgetAttribute p0);

    <E extends Event> void addEventHandler(final EventHandler<E> p0);

    <E extends Event> void addSelfEventHandler(final EventHandler<E> p0);

    boolean contains(final IPoint p0);

    float x();

    float y();

    float w();

    float h();

    void onRender(final RenderStage p0);
}
