package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;

public class ControlIndividualDisplay extends ControlItemDisplay implements ITooltip {
    public ControlIndividualDisplay(IWidget parent, float x, float y, IIndividual ind) {
        this(parent, x, y, 16.0F, ind);
    }

    public ControlIndividualDisplay(IWidget parent, float x, float y, float size, IIndividual ind) {
        super(parent, x, y, size);
        BreedingSystem system = Binnie.Genetics.getSystem(ind.getGenome().getSpeciesRoot());
        this.setItemStack(system.getSpeciesRoot().getMemberStack(ind, system.getDefaultType()));
        this.setTooltip();
    }

    public void getTooltip(Tooltip tooltip) {
        ItemStack stack = this.getItemStack();
        if (stack != null) {
            IIndividual ind = AlleleManager.alleleRegistry.getIndividual(stack);
            if (ind != null) {
                tooltip.add(ind.getGenome().getPrimary().getName());
            }
        }
    }
}
