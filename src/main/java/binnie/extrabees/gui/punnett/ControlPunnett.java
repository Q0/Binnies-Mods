package binnie.extrabees.gui.punnett;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import java.util.LinkedList;
import java.util.List;

public class ControlPunnett extends Control {
   static int boxWidth = 80;
   static int boxHeight = 28;

   protected ControlPunnett(IWidget parent, float x, float y) {
      super(parent, x, y, (float)(boxWidth * 3), (float)(boxWidth * 3));
   }

   public void onRenderBackground() {
      CraftGUI.Render.solid(new IArea(0.0F, (float)boxHeight, (float)(boxWidth * 3), 1.0F), 11184810);
      CraftGUI.Render.solid(new IArea((float)boxWidth / 2.0F, (float)(boxHeight * 2), (float)boxWidth * 2.5F, 1.0F), 11184810);
      CraftGUI.Render.solid(new IArea((float)boxWidth, 0.0F, 1.0F, (float)(boxHeight * 3)), 11184810);
      CraftGUI.Render.solid(new IArea((float)(boxWidth * 2), (float)boxHeight / 2.0F, 1.0F, (float)boxHeight * 2.5F), 11184810);
   }

   public void setup(IChromosomeType chromosome, IIndividual ind1, IIndividual ind2, ISpeciesRoot root) {
      this.deleteAllChildren();
      if(chromosome != null && ind1 != null && ind2 != null && root != null) {
         IAllele primary1 = ind1.getGenome().getActiveAllele(chromosome);
         IAllele primary2 = ind2.getGenome().getActiveAllele(chromosome);
         IAllele secondary1 = ind1.getGenome().getInactiveAllele(chromosome);
         IAllele secondary2 = ind2.getGenome().getInactiveAllele(chromosome);
         int x = 1;
         int y = 1;

         for(IAllele allele1 : new IAllele[]{primary1, secondary1}) {
            y = 1;

            for(IAllele allele2 : new IAllele[]{primary2, secondary2}) {
               List<IAllele> alleles = new LinkedList();
               if(allele1.isDominant() && !allele2.isDominant()) {
                  alleles.add(allele1);
               } else if(allele2.isDominant() && !allele1.isDominant()) {
                  alleles.add(allele2);
               } else {
                  alleles.add(allele1);
                  if(allele1 != allele2) {
                     alleles.add(allele2);
                  }
               }

               String text = "";

               for(IAllele allele : alleles) {
                  text = text + allele.getName() + ": " + 25.0F / (float)alleles.size() + "%\n";
               }

               (new ControlText(this, new IArea((float)(x * boxWidth), (float)(boxHeight * y), (float)boxWidth, (float)boxHeight), text, TextJustification.TopCenter)).setColour(11184810);
               ++y;
            }

            ++x;
         }

         (new ControlText(this, new IArea((float)boxWidth, 0.0F, (float)boxWidth, (float)boxHeight), "\n" + primary1.getName(), TextJustification.TopCenter)).setColour(11184810);
         (new ControlText(this, new IArea((float)(boxWidth * 2), 0.0F, (float)boxWidth, (float)boxHeight), "\n" + secondary1.getName(), TextJustification.TopCenter)).setColour(11184810);
         (new ControlText(this, new IArea(0.0F, (float)boxHeight, (float)boxWidth, (float)boxHeight), primary2.getName(), TextJustification.TopCenter)).setColour(11184810);
         (new ControlText(this, new IArea(0.0F, (float)(boxHeight * 2), (float)boxWidth, (float)boxHeight), primary2.getName(), TextJustification.TopCenter)).setColour(11184810);
      }
   }
}
