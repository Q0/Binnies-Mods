package binnie.craftgui.controls.scroll;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.resource.minecraft.CraftGUITexture;

public class ControlScrollBar extends Control {
    protected final IControlScrollable scrollable;

    public ControlScrollBar(ControlScroll parent) {
        this(parent, 0.0F, 0.0F, parent.getSize().x(), parent.getSize().y(), parent.getScrollableWidget());
    }

    public ControlScrollBar(IWidget parent, float x, float y, float w, float h, IControlScrollable scrollable2) {
        super(parent, x, y, w, h);
        this.scrollable = scrollable2;
        this.addAttribute(Attribute.MouseOver);
        this.addSelfEventHandler(new EventMouse.Drag.Handler() {
            public void onEvent(EventMouse.Drag event) {
                ControlScrollBar.this.scrollable.movePercentage(event.getDy() / (ControlScrollBar.this.h() - ControlScrollBar.this.getBarHeight()));
            }
        });
        this.addSelfEventHandler(new EventMouse.Down.Handler() {
            public void onEvent(EventMouse.Down event) {
                float shownPercentage = ControlScrollBar.this.scrollable.getPercentageShown();
                float percentageIndex = ControlScrollBar.this.scrollable.getPercentageIndex();
                float minPercent = (1.0F - shownPercentage) * percentageIndex;
                float maxPercent = minPercent + shownPercentage;
                float clickedPercentage = ControlScrollBar.this.getRelativeMousePosition().y() / (ControlScrollBar.this.h() - 2.0F);
                clickedPercentage = Math.max(Math.min(clickedPercentage, 1.0F), 0.0F);
                if (clickedPercentage > maxPercent) {
                    ControlScrollBar.this.scrollable.setPercentageIndex((clickedPercentage - shownPercentage) / (1.0F - shownPercentage));
                }

                if (clickedPercentage < minPercent) {
                    ControlScrollBar.this.scrollable.setPercentageIndex(clickedPercentage / (1.0F - shownPercentage));
                }

            }
        });
    }

    public void onUpdateClient() {
    }

    public boolean isEnabled() {
        return this.scrollable.getPercentageShown() < 0.99F;
    }

    public float getBarHeight() {
        return this.h() * this.scrollable.getPercentageShown();
    }

    protected IArea getRenderArea() {
        float height = this.getBarHeight();
        if (height < 6.0F) {
            height = 6.0F;
        }

        float yOffset = (float) ((int) this.h() - (int) this.getBarHeight()) * this.scrollable.getPercentageIndex();
        return new IArea(0.0F, yOffset, this.getSize().x(), height);
    }

    public void onRenderBackground() {
        IArea renderArea = this.getRenderArea();
        Object texture = CraftGUITexture.ScrollDisabled;
        if (this.isMouseOver()) {
            texture = CraftGUITexture.ScrollHighlighted;
        } else if (this.isEnabled()) {
            texture = CraftGUITexture.Scroll;
        }

        CraftGUI.Render.texture(texture, renderArea);
    }
}
