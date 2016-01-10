package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;

public class AnalystPageKaryogram extends ControlAnalystPage {
    public AnalystPageKaryogram(IWidget parent, IArea area, IIndividual ind) {
        super(parent, area);
        this.setColour(10040319);
        int y = 4;
        (new ControlTextCentered(this, (float) y, "§nKaryogram")).setColour(this.getColour());
        y = y + 16;
        y = y + 8;
        ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(ind.getClass());
        BreedingSystem system = Binnie.Genetics.getSystem(root);
        int maxBiomePerLine = (int) ((this.w() + 4.0F - 16.0F) / 22.0F);
        float karygramX = (this.w() - (float) (Math.min(maxBiomePerLine, system.getActiveKaryotype().size()) * 18 - 4)) / 2.0F;
        int dx = 0;
        int dy = 0;
        int rem = system.getActiveKaryotype().size();

        for (IChromosomeType type : system.getActiveKaryotype()) {
            new ControlAnalystChromosome(this, karygramX + (float) dx, (float) (y + dy), root, type, ind.getGenome().getActiveAllele(type), ind.getGenome().getInactiveAllele(type));
            dx += 22;
            if (dx >= 22 * maxBiomePerLine - 22) {
                rem -= maxBiomePerLine - 1;
                if (rem < maxBiomePerLine) {
                    karygramX += (float) ((maxBiomePerLine - 1 - rem) * 22) / 2.0F;
                }

                dx = 0;
                dy += 28;
            }
        }

    }

    public String getTitle() {
        return "Karyogram";
    }
}
