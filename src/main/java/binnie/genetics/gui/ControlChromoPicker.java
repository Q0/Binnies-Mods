package binnie.genetics.gui;

import binnie.Binnie;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventValueChanged;
import binnie.craftgui.events.EventWidget;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import forestry.api.genetics.IChromosomeType;

public class ControlChromoPicker extends Control implements ITooltip {
    Texture Selected = new StandardTexture(160, 18, 16, 16, ExtraBeeTexture.GUIPunnett);
    Texture Texture = new StandardTexture(160, 34, 16, 16, ExtraBeeTexture.GUIPunnett);
    IChromosomeType type;
    ControlChromosome parent;

    public ControlChromoPicker(ControlChromosome parent, float x, float y, IChromosomeType chromo) {
        super(parent, x, y, 16.0F, 16.0F);
        this.type = chromo;
        this.addAttribute(Attribute.MouseOver);
        this.parent = parent;
        this.addSelfEventHandler(new EventWidget.StartMouseOver.Handler() {
            public void onEvent(EventWidget.StartMouseOver event) {
                ControlChromoPicker.this.callEvent(new EventValueChanged(ControlChromoPicker.this.getWidget(), ControlChromoPicker.this.type));
            }
        });
        this.addSelfEventHandler(new EventWidget.EndMouseOver.Handler() {
            public void onEvent(EventWidget.EndMouseOver event) {
                ControlChromoPicker.this.callEvent(new EventValueChanged(ControlChromoPicker.this.getWidget(), (Object) null));
            }
        });
    }

    public void onRenderBackground() {
        super.onRenderBackground();
        boolean selected = this.isMouseOver();
        Texture text = selected ? this.Selected : this.Texture;
        CraftGUI.Render.texture(text, IPoint.ZERO);
    }

    public void getTooltip(Tooltip tooltip) {
        tooltip.add(Binnie.Genetics.getSystem(this.parent.getRoot()).getChromosomeName(this.type));
    }
}
