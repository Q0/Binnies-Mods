package binnie.craftgui.controls.scroll;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.scroll.ControlScroll;
import binnie.craftgui.controls.scroll.IControlScrollable;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.Event;
import binnie.craftgui.events.EventHandler;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventWidget;

public class ControlScrollableContent<T extends IWidget>
        extends Control
        implements IControlScrollable {
    protected T controlChild;
    protected float scrollBarSize;
    float percentageIndex = 0.0f;

    public ControlScrollableContent(IWidget parent, float x, float y, float w, float h, float scrollBarSize) {
        super(parent, x, y, w, h);
        if (scrollBarSize != 0.0f) {
            new ControlScroll(this, this.getSize().x() - scrollBarSize, 0.0f, scrollBarSize, this.getSize().y(), this);
        }
        this.addEventHandler(new EventMouse.Wheel.Handler(){

            @Override
            public void onEvent(EventMouse.Wheel event) {
                if (ControlScrollableContent.this.getRelativeMousePosition().x() > 0.0f && ControlScrollableContent.this.getRelativeMousePosition().y() > 0.0f && ControlScrollableContent.this.getRelativeMousePosition().x() < ControlScrollableContent.this.getSize().x() && ControlScrollableContent.this.getRelativeMousePosition().y() < ControlScrollableContent.this.getSize().y()) {
                    if (ControlScrollableContent.this.getMovementRange() == 0.0f) {
                        return;
                    }
                    float percentageMove = 0.8f / ControlScrollableContent.this.getMovementRange();
                    ControlScrollableContent.this.movePercentage(percentageMove * (float)(- event.getDWheel()));
                }
            }
        });
        this.scrollBarSize = scrollBarSize;
    }

    public void setScrollableContent(T child) {
        this.controlChild = child;
        if (child == null) {
            return;
        }
        child.setCroppedZone(this, new IArea(1.0f, 1.0f, this.getSize().x() - 2.0f - this.scrollBarSize, this.getSize().y() - 2.0f));
        child.addSelfEventHandler(new EventWidget.ChangeSize.Handler(){

            @Override
            public void onEvent(EventWidget.ChangeSize event) {
                ControlScrollableContent.this.controlChild.setOffset(new IPoint(0.0f, (- ControlScrollableContent.this.percentageIndex) * ControlScrollableContent.this.getMovementRange()));
                if (ControlScrollableContent.this.getMovementRange() == 0.0f) {
                    ControlScrollableContent.this.percentageIndex = 0.0f;
                }
            }
        });
    }

    public T getContent() {
        return this.controlChild;
    }

    @Override
    public float getPercentageShown() {
        if (this.controlChild == null || this.controlChild.getSize().y() == 0.0f) {
            return 1.0f;
        }
        float shown = this.getSize().y() / this.controlChild.getSize().y();
        return Math.min(shown, 1.0f);
    }

    @Override
    public float getPercentageIndex() {
        return this.percentageIndex;
    }

    @Override
    public void movePercentage(float percentage) {
        if (this.controlChild == null) {
            return;
        }
        this.percentageIndex += percentage;
        if (this.percentageIndex > 1.0f) {
            this.percentageIndex = 1.0f;
        } else if (this.percentageIndex < 0.0f) {
            this.percentageIndex = 0.0f;
        }
        if (this.getMovementRange() == 0.0f) {
            this.percentageIndex = 0.0f;
        }
        this.controlChild.setOffset(new IPoint(0.0f, (- this.percentageIndex) * this.getMovementRange()));
    }

    @Override
    public void setPercentageIndex(float index) {
        this.movePercentage(index - this.percentageIndex);
    }

    @Override
    public float getMovementRange() {
        if (this.controlChild == null) {
            return 0.0f;
        }
        float range = this.controlChild.getSize().y() - this.getSize().y();
        return Math.max(range, 0.0f);
    }

    @Override
    public void onUpdateClient() {
        this.setPercentageIndex(this.getPercentageIndex());
    }

    public void ensureVisible(float minY, float maxY, float totalY) {
        float percentageIndex;
        minY /= totalY;
        float shownPercentage = this.getPercentageShown();
        float minPercent = (1.0f - shownPercentage) * (percentageIndex = this.getPercentageIndex());
        float maxPercent = minPercent + shownPercentage;
        if ((maxY /= totalY) > maxPercent) {
            this.setPercentageIndex((maxY - shownPercentage) / (1.0f - shownPercentage));
        }
        if (minY < minPercent) {
            this.setPercentageIndex(minY / (1.0f - shownPercentage));
        }
    }

}
