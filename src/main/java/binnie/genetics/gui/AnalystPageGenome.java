package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.genetics.gui.ControlAnalystPage;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.ISpeciesRoot;
import org.lwjgl.opengl.GL11;

public class AnalystPageGenome extends ControlAnalystPage {
   boolean active;

   public AnalystPageGenome(IWidget parent, IArea area, boolean active, IIndividual ind) {
      super(parent, area);
      this.active = active;
      this.setColour(26265);
      int y = 4;
      (new ControlTextCentered(this, (float)y, "§n" + this.getTitle())).setColour(this.getColour());
      y = y + 16;
      ISpeciesRoot root = AlleleManager.alleleRegistry.getSpeciesRoot(ind.getClass());
      BreedingSystem system = Binnie.Genetics.getSystem(root);
      Control scaled = new Control(this, 0.0F, (float)y, 0.0F, 0.0F) {
         public void onRenderBackground() {
            GL11.glPushMatrix();
            GL11.glTranslatef(10.0F, -15.0F, 0.0F);
            GL11.glScalef(0.9F, 0.95F, 1.0F);
         }

         public void onRenderForeground() {
            GL11.glPopMatrix();
         }
      };

      for(IChromosomeType chromo : system.getActiveKaryotype()) {
         IAllele allele = active?ind.getGenome().getActiveAllele(chromo):ind.getGenome().getInactiveAllele(chromo);
         String alleleName = system.getAlleleName(chromo, allele);
         float height = CraftGUI.Render.textHeight(alleleName, this.w() / 2.0F - 2.0F);
         (new ControlText(scaled, new IArea(0.0F, (float)y + (height - 9.0F) / 2.0F, this.w() / 2.0F - 2.0F, 0.0F), system.getChromosomeShortName(chromo) + " :", TextJustification.TopRight)).setColour(this.getColour());
         (new ControlText(scaled, new IArea(this.w() / 2.0F + 2.0F, (float)y, this.w() / 2.0F - 2.0F, 0.0F), alleleName, TextJustification.TopLeft)).setColour(this.getColour());
         y = (int)((float)y + 3.0F + height);
      }

      this.setSize(new IPoint(this.w(), (float)(y + 8)));
   }

   public String getTitle() {
      return (this.active?"Active":"Inactive") + " Genome";
   }
}
