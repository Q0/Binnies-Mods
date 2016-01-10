package binnie.craftgui.controls.scroll;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.events.EventWidget;

public class ControlScrollableContent extends Control implements IControlScrollable {
    protected IWidget controlChild;
    protected float scrollBarSize;
    float percentageIndex = 0.0F;

    public ControlScrollableContent(IWidget parent, float x, float y, float w, float h, float scrollBarSize) {
        super(parent, x, y, w, h);
        if (scrollBarSize != 0.0F) {
            new ControlScroll(this, this.getSize().x() - scrollBarSize, 0.0F, scrollBarSize, this.getSize().y(), this);
        }

        this.addEventHandler(new EventMouse.Wheel.Handler() {
            public void onEvent(EventMouse.Wheel event) {
                if (ControlScrollableContent.this.getRelativeMousePosition().x() > 0.0F && ControlScrollableContent.this.getRelativeMousePosition().y() > 0.0F && ControlScrollableContent.this.getRelativeMousePosition().x() < ControlScrollableContent.this.getSize().x() && ControlScrollableContent.this.getRelativeMousePosition().y() < ControlScrollableContent.this.getSize().y()) {
                    if (ControlScrollableContent.this.getMovementRange() == 0.0F) {
                        return;
                    }

                    float percentageMove = 0.8F / ControlScrollableContent.this.getMovementRange();
                    ControlScrollableContent.this.movePercentage(percentageMove * (float) (-event.getDWheel()));
                }

            }
        });
        this.scrollBarSize = scrollBarSize;
    }

    public void setScrollableContent(IWidget child) {
        this.controlChild = child;
        if (child != null) {
            child.setCroppedZone(this, new IArea(1.0F, 1.0F, this.getSize().x() - 2.0F - this.scrollBarSize, this.getSize().y() - 2.0F));
            child.addSelfEventHandler(new EventWidget.ChangeSize.Handler() {
                public void onEvent(EventWidget.ChangeSize event) {
                    ControlScrollableContent.this.controlChild.setOffset(new IPoint(0.0F, -ControlScrollableContent.this.percentageIndex * ControlScrollableContent.this.getMovementRange()));
                    if (ControlScrollableContent.this.getMovementRange() == 0.0F) {
                        ControlScrollableContent.this.percentageIndex = 0.0F;
                    }

                }
            });
        }
    }

    public IWidget getContent() {
        return this.controlChild;
    }

    public float getPercentageShown() {
        if (this.controlChild != null && this.controlChild.getSize().y() != 0.0F) {
            float shown = this.getSize().y() / this.controlChild.getSize().y();
            return Math.min(shown, 1.0F);
        } else {
            return 1.0F;
        }
    }

    public float getPercentageIndex() {
        return this.percentageIndex;
    }

    public void movePercentage(float percentage) {
        if (this.controlChild != null) {
            this.percentageIndex += percentage;
            if (this.percentageIndex > 1.0F) {
                this.percentageIndex = 1.0F;
            } else if (this.percentageIndex < 0.0F) {
                this.percentageIndex = 0.0F;
            }

            if (this.getMovementRange() == 0.0F) {
                this.percentageIndex = 0.0F;
            }

            this.controlChild.setOffset(new IPoint(0.0F, -this.percentageIndex * this.getMovementRange()));
        }
    }

    public void setPercentageIndex(float index) {
        this.movePercentage(index - this.percentageIndex);
    }

    public float getMovementRange() {
        if (this.controlChild == null) {
            return 0.0F;
        } else {
            float range = this.controlChild.getSize().y() - this.getSize().y();
            return Math.max(range, 0.0F);
        }
    }

    public void onUpdateClient() {
        this.setPercentageIndex(this.getPercentageIndex());
    }

    public void ensureVisible(float minY, float maxY, float totalY) {
        minY = minY / totalY;
        maxY = maxY / totalY;
        float shownPercentage = this.getPercentageShown();
        float percentageIndex = this.getPercentageIndex();
        float minPercent = (1.0F - shownPercentage) * percentageIndex;
        float maxPercent = minPercent + shownPercentage;
        if (maxY > maxPercent) {
            this.setPercentageIndex((maxY - shownPercentage) / (1.0F - shownPercentage));
        }

        if (minY < minPercent) {
            this.setPercentageIndex(minY / (1.0F - shownPercentage));
        }

    }
}
