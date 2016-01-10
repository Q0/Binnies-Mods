package binnie.craftgui.genetics.machine;

import binnie.Binnie;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.Gene;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.genetics.machine.ControlGene;
import binnie.craftgui.genetics.machine.WindowGeneBank;
import binnie.craftgui.minecraft.Window;
import binnie.genetics.api.IGene;
import binnie.genetics.genetics.GeneTracker;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IChromosomeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ControlGeneScroll extends Control implements IControlValue {
   private String filter = "";
   private BreedingSystem system = null;

   protected ControlGeneScroll(IWidget parent, float x, float y, float w, float h) {
      super(parent, x, y, w, h);
   }

   public void setFilter(String filter) {
      this.filter = filter.toLowerCase();
      this.refresh();
   }

   public void setGenes(BreedingSystem system) {
      this.system = system;
      this.refresh();
   }

   public void refresh() {
      this.deleteAllChildren();
      GeneTracker tracker = GeneTracker.getTracker(Window.get(this).getWorld(), Window.get(this).getUsername());
      Map<IChromosomeType, List<IAllele>> genes = Binnie.Genetics.getChromosomeMap(this.system.getSpeciesRoot());
      int x = 0;
      int y = 0;
      boolean isNEI = ((WindowGeneBank)Window.get(this)).isNei;

      for(Entry<IChromosomeType, List<IAllele>> entry : genes.entrySet()) {
         List<IAllele> discovered = new ArrayList();

         for(IAllele allele : (List)entry.getValue()) {
            Gene gene = new Gene(allele, (IChromosomeType)entry.getKey(), this.system.getSpeciesRoot());
            if((isNEI || tracker.isSequenced(new Gene(allele, (IChromosomeType)entry.getKey(), this.system.getSpeciesRoot()))) && gene.getName().toLowerCase().contains(this.filter)) {
               discovered.add(allele);
            }
         }

         if(discovered.size() != 0) {
            x = 0;
            new ControlText(this, new IPoint((float)x, (float)y), this.system.getChromosomeName((IChromosomeType)entry.getKey()));
            y = y + 12;

            for(IAllele allele : discovered) {
               if((float)(x + 18) > this.getSize().x()) {
                  y += 20;
                  x = 0;
               }

               (new ControlGene(this, (float)x, (float)y)).setValue((IGene)(new Gene(allele, (IChromosomeType)entry.getKey(), this.system.getSpeciesRoot())));
               x += 18;
            }

            y = y + 24;
         }
      }

      this.setSize(new IPoint(this.getSize().x(), (float)y));
   }

   public void setValue(BreedingSystem system) {
      this.setGenes(system);
   }

   public BreedingSystem getValue() {
      return this.system;
   }
}
