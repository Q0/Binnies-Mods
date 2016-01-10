package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.genetics.gui.ControlAnalystPage;
import forestry.api.apiculture.IAlleleBeeEffect;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IIndividual;
import forestry.api.lepidopterology.EnumButterflyChromosome;
import forestry.api.lepidopterology.IAlleleButterflyEffect;
import forestry.api.lepidopterology.IButterfly;
import forestry.plugins.PluginApiculture;

public class AnalystPageBehaviour extends ControlAnalystPage {
   public AnalystPageBehaviour(IWidget parent, IArea area, IIndividual ind) {
      super(parent, area);
      this.setColour(6684723);
      int y = 4;
      (new ControlTextCentered(this, (float)y, "§nBehaviour")).setColour(this.getColour());
      y = y + 12;
      if(ind instanceof IBee) {
         IBee bee = (IBee)ind;
         y = y + 8;
         int fertility = bee.getGenome().getFlowering();
         (new ControlTextCentered(this, (float)y, "Pollinates nearby\n" + bee.getGenome().getFlowerProvider().getDescription())).setColour(this.getColour());
         y = y + 20;
         (new ControlTextCentered(this, (float)y, "§oEvery " + this.getTimeString((float)PluginApiculture.ticksPerBeeWorkCycle * 100.0F / (float)fertility))).setColour(this.getColour());
         y = y + 22;
         IAlleleBeeEffect effect = bee.getGenome().getEffect();
         int[] t = bee.getGenome().getTerritory();
         if(!effect.getUID().contains("None")) {
            String effectDesc = BinnieCore.proxy.localiseOrBlank("allele." + effect.getUID() + ".desc");
            String loc = effectDesc.equals("")?"Effect: " + effect.getName():effectDesc;
            (new ControlText(this, new IArea(4.0F, (float)y, this.w() - 8.0F, 0.0F), loc, TextJustification.TopCenter)).setColour(this.getColour());
            y = (int)((float)y + CraftGUI.Render.textHeight(loc, this.w() - 8.0F) + 1.0F);
            (new ControlTextCentered(this, (float)y, "§oWithin " + (int)((float)t[0] / 2.0F) + " blocks")).setColour(this.getColour());
            y = y + 22;
         }

         (new ControlTextCentered(this, (float)y, "Territory: §o" + t[0] + "x" + t[1] + "x" + t[2])).setColour(this.getColour());
         y = y + 22;
      }

      if(ind instanceof IButterfly) {
         IButterfly bee = (IButterfly)ind;
         (new ControlTextCentered(this, (float)y, "§oMetabolism: " + Binnie.Genetics.mothBreedingSystem.getAlleleName(EnumButterflyChromosome.METABOLISM, ind.getGenome().getActiveAllele(EnumButterflyChromosome.METABOLISM)))).setColour(this.getColour());
         y = y + 20;
         (new ControlTextCentered(this, (float)y, "Pollinates nearby\n" + bee.getGenome().getFlowerProvider().getDescription())).setColour(this.getColour());
         y = y + 20;
         (new ControlTextCentered(this, (float)y, "§oEvery " + this.getTimeString(1500.0F))).setColour(this.getColour());
         y = y + 22;
         IAlleleButterflyEffect effect = bee.getGenome().getEffect();
         if(!effect.getUID().contains("None")) {
            String effectDesc = BinnieCore.proxy.localiseOrBlank("allele." + effect.getUID() + ".desc");
            String loc = effectDesc.equals("")?"Effect: " + effect.getName():effectDesc;
            (new ControlText(this, new IArea(4.0F, (float)y, this.w() - 8.0F, 0.0F), loc, TextJustification.TopCenter)).setColour(this.getColour());
            y = (int)((float)y + CraftGUI.Render.textHeight(loc, this.w() - 8.0F) + 1.0F);
            y = y + 22;
         }
      }

   }

   public String getTitle() {
      return "Behaviour";
   }
}
