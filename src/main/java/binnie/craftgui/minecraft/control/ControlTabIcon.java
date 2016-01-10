package binnie.craftgui.minecraft.control;

import binnie.core.genetics.IItemStackRepresentitive;
import binnie.craftgui.controls.tab.ControlTab;
import binnie.craftgui.controls.tab.ControlTabBar;
import binnie.craftgui.core.geometry.IPoint;
import net.minecraft.item.ItemStack;

public class ControlTabIcon extends ControlTab {
    private ControlItemDisplay item;

    public ControlTabIcon(ControlTabBar parent, float x, float y, float w, float h, Object value) {
        super(parent, x, y, w, h, value);
        this.item = new ControlItemDisplay(this, -8.0F + w / 2.0F, -8.0F + h / 2.0F);
        this.item.hastooltip = false;
    }

    public ItemStack getItemStack() {
        return this.value instanceof IItemStackRepresentitive ? ((IItemStackRepresentitive) this.value).getItemStackRepresentitive() : null;
    }

    public void onUpdateClient() {
        super.onUpdateClient();
        this.item.setItemStack(this.getItemStack());
        float x = (float) ((ControlTabBar) this.getParent()).getDirection().x();
        this.item.setOffset(new IPoint(!this.isCurrentSelection() && !this.isMouseOver() ? -4.0F * x : 0.0F, 0.0F));
    }

    public boolean hasOutline() {
        return false;
    }

    public int getOutlineColour() {
        return 16777215;
    }
}
