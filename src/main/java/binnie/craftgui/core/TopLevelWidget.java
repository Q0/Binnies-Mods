package binnie.craftgui.core;

import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventWidget;
import org.lwjgl.input.Mouse;

import java.util.*;

public abstract class TopLevelWidget extends Widget implements ITopLevelWidget {
    IWidget mousedOverWidget = null;
    IWidget draggedWidget = null;
    IWidget focusedWidget = null;
    protected IPoint mousePosition = new IPoint(0.0F, 0.0F);
    IPoint dragStart = IPoint.ZERO;

    public TopLevelWidget() {
        super((IWidget) null);
        this.addEventHandler(new EventMouse.Down.Handler() {
            public void onEvent(EventMouse.Down event) {
                TopLevelWidget.this.setDraggedWidget(TopLevelWidget.this.mousedOverWidget, event.getButton());
                TopLevelWidget.this.setFocusedWidget(TopLevelWidget.this.mousedOverWidget);
            }
        });
        this.addEventHandler(new EventMouse.Up.Handler() {
            public void onEvent(EventMouse.Up event) {
                TopLevelWidget.this.setDraggedWidget((IWidget) null);
            }
        });
        this.addEventHandler(new EventWidget.StartDrag.Handler() {
            public void onEvent(EventWidget.StartDrag event) {
                TopLevelWidget.this.dragStart = TopLevelWidget.this.getRelativeMousePosition();
            }
        });
    }

    public void setMousedOverWidget(IWidget widget) {
        if (this.mousedOverWidget != widget) {
            if (this.mousedOverWidget != null) {
                this.callEvent(new EventWidget.EndMouseOver(this.mousedOverWidget));
            }

            this.mousedOverWidget = widget;
            if (this.mousedOverWidget != null) {
                this.callEvent(new EventWidget.StartMouseOver(this.mousedOverWidget));
            }

        }
    }

    public void setDraggedWidget(IWidget widget) {
        this.setDraggedWidget(widget, -1);
    }

    public void setDraggedWidget(IWidget widget, int button) {
        if (this.draggedWidget != widget) {
            if (this.draggedWidget != null) {
                this.callEvent(new EventWidget.EndDrag(this.draggedWidget));
            }

            this.draggedWidget = widget;
            if (this.draggedWidget != null) {
                this.callEvent(new EventWidget.StartDrag(this.draggedWidget, button));
            }

        }
    }

    public void setFocusedWidget(IWidget widget) {
        IWidget newWidget = widget;
        if (this.focusedWidget != widget) {
            if (widget != null && !widget.canFocus()) {
                newWidget = null;
            }

            if (this.focusedWidget != null) {
                this.callEvent(new EventWidget.LoseFocus(this.focusedWidget));
            }

            this.focusedWidget = newWidget;
            if (this.focusedWidget != null) {
                this.callEvent(new EventWidget.GainFocus(this.focusedWidget));
            }

        }
    }

    public IWidget getMousedOverWidget() {
        return this.mousedOverWidget;
    }

    public IWidget getDraggedWidget() {
        return this.draggedWidget;
    }

    public IWidget getFocusedWidget() {
        return this.focusedWidget;
    }

    public boolean isMouseOver(IWidget widget) {
        return this.getMousedOverWidget() == widget;
    }

    public boolean isDragged(IWidget widget) {
        return this.getDraggedWidget() == widget;
    }

    public boolean isFocused(IWidget widget) {
        return this.getFocusedWidget() == widget;
    }

    public void updateTopLevel() {
        this.setMousedOverWidget(this.calculateMousedOverWidget());
        if (this.getFocusedWidget() != null && (!this.getFocusedWidget().isVisible() || !this.getFocusedWidget().isEnabled())) {
            this.setFocusedWidget((IWidget) null);
        }

        if (!Mouse.isButtonDown(0) && this.draggedWidget != null) {
            this.setDraggedWidget((IWidget) null);
        }

    }

    private IWidget calculateMousedOverWidget() {
        Deque<IWidget> queue = this.calculateMousedOverWidgets();

        while (!queue.isEmpty()) {
            IWidget widget = (IWidget) queue.removeFirst();
            if (widget.isEnabled() && widget.isVisible() && widget.canMouseOver() && widget.isEnabled() && widget.isVisible() && widget.canMouseOver() && widget.calculateIsMouseOver()) {
                return widget;
            }
        }

        return null;
    }

    public Deque calculateMousedOverWidgets() {
        Deque<IWidget> list = new ArrayDeque();

        for (IWidget widget : this.getQueuedWidgets(this)) {
            if (widget.calculateIsMouseOver()) {
                list.addLast(widget);
            }
        }

        return list;
    }

    private Collection getQueuedWidgets(IWidget widget) {
        List<IWidget> widgets = new ArrayList();
        boolean addChildren = true;
        if (widget.isCroppedWidet()) {
            addChildren = widget.getCroppedZone().contains(widget.getCropWidget().getRelativeMousePosition());
        }

        if (addChildren) {
            ListIterator<IWidget> li = widget.getWidgets().listIterator(widget.getWidgets().size());

            while (li.hasPrevious()) {
                IWidget child = (IWidget) li.previous();
                widgets.addAll(this.getQueuedWidgets(child));
            }
        }

        widgets.add(widget);
        return widgets;
    }

    public void setMousePosition(int x, int y) {
        float dx = (float) x - this.mousePosition.x();
        float dy = (float) y - this.mousePosition.y();
        if (dx != 0.0F || dy != 0.0F) {
            if (this.getDraggedWidget() != null) {
                this.callEvent(new EventMouse.Drag(this.getDraggedWidget(), dx, dy));
            } else {
                this.callEvent(new EventMouse.Move(this, dx, dy));
            }
        }

        if (this.mousePosition.x() != (float) x || this.mousePosition.y() != (float) y) {
            this.mousePosition = new IPoint((float) x, (float) y);
            this.setMousedOverWidget(this.calculateMousedOverWidget());
        }

    }

    public IPoint getAbsoluteMousePosition() {
        return this.mousePosition;
    }

    public void widgetDeleted(IWidget widget) {
        if (this.isMouseOver(widget)) {
            this.setMousedOverWidget((IWidget) null);
        }

        if (this.isDragged(widget)) {
            this.setDraggedWidget((IWidget) null);
        }

        if (this.isFocused(widget)) {
            this.setFocusedWidget((IWidget) null);
        }

    }

    public IPoint getDragDistance() {
        return this.getRelativeMousePosition().sub(this.dragStart);
    }
}
