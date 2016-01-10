package binnie.craftgui.controls.scroll;

import binnie.craftgui.core.IWidget;

public interface IControlScrollable extends IWidget {
    float getPercentageShown();

    float getPercentageIndex();

    void movePercentage(float var1);

    void setPercentageIndex(float var1);

    float getMovementRange();
}
