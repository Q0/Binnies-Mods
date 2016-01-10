package binnie.genetics.gui;

import binnie.Binnie;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

public class AnalystPageGrowth extends ControlAnalystPage {
    public AnalystPageGrowth(IWidget parent, IArea area, IIndividual ind) {
        super(parent, area);
        this.setColour(3355443);
        int y = 4;
        IAlleleSpecies species = ind.getGenome().getPrimary();
        (new ControlTextCentered(this, (float) y, "§nGrowth")).setColour(this.getColour());
        y = y + 12;
        if (ind instanceof ITree) {
            ITree tree = (ITree) ind;
            int mat = tree.getGenome().getMaturationTime();
            (new ControlTextCentered(this, (float) y, "Saplings mature in")).setColour(this.getColour());
            y = y + 12;
            (new ControlTextCentered(this, (float) y, "§l" + this.getTimeString(1373.3999F * (float) mat))).setColour(this.getColour());
            y = y + 22;
            (new ControlTextCentered(this, (float) y, "§oHeight: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.HEIGHT, ind.getGenome().getActiveAllele(EnumTreeChromosome.HEIGHT)))).setColour(this.getColour());
            y = y + 12;
            (new ControlTextCentered(this, (float) y, "§oGirth: " + Binnie.Genetics.treeBreedingSystem.getAlleleName(EnumTreeChromosome.GIRTH, ind.getGenome().getActiveAllele(EnumTreeChromosome.GIRTH)))).setColour(this.getColour());
            y = y + 20;
            (new ControlTextCentered(this, (float) y, "Growth Conditions")).setColour(this.getColour());
            y = y + 12;
            (new ControlTextCentered(this, (float) y, "§o" + tree.getGenome().getGrowthProvider().getDescription())).setColour(this.getColour());
            y = y + 12;

            for (String s : tree.getGenome().getGrowthProvider().getInfo()) {
                (new ControlTextCentered(this, (float) y, "§o" + s)).setColour(this.getColour());
                y += 12;
            }
        }

    }

    public String getTitle() {
        return "Growth";
    }
}
