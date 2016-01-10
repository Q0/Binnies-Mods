package binnie.craftgui.botany;

import binnie.botany.api.IColourMix;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.craftgui.resource.minecraft.StandardTexture;

public class ControlColourMixSymbol extends Control implements ITooltip {
    static Texture MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.Controls2);
    static Texture MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.Controls2);
    IColourMix value = null;
    int type;

    public void onRenderBackground() {
        super.onRenderBackground();
        if (this.type == 0) {
            CraftGUI.Render.texture(MutationPlus, IPoint.ZERO);
        } else {
            CraftGUI.Render.texture(MutationArrow, IPoint.ZERO);
        }

    }

    protected ControlColourMixSymbol(IWidget parent, int x, int y, int type) {
        super(parent, (float) x, (float) y, (float) (16 + type * 16), 16.0F);
        this.type = type;
        this.addAttribute(Attribute.MouseOver);
    }

    public void setValue(IColourMix value) {
        this.value = value;
        this.setColour(16777215);
    }

    public void getTooltip(Tooltip tooltip) {
        if (this.type == 1) {
            float chance = (float) this.value.getChance();
            tooltip.add("Current Chance - " + chance + "%");
        }

    }
}
