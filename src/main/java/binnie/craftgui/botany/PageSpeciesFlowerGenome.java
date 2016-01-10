package binnie.craftgui.botany;

import binnie.Binnie;
import binnie.botany.api.EnumFlowerChromosome;
import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.api.IFlower;
import binnie.botany.api.IFlowerGenome;
import binnie.botany.core.BotanyCore;
import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.scroll.ControlScrollableContent;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.PageSpecies;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import net.minecraft.item.ItemStack;

public class PageSpeciesFlowerGenome extends PageSpecies {
   public PageSpeciesFlowerGenome(IWidget parent, DatabaseTab tab) {
      super(parent, tab);
   }

   public void onValueChanged(IAlleleSpecies species) {
      this.deleteAllChildren();
      IAllele[] template = Binnie.Genetics.getFlowerRoot().getTemplate(species.getUID());
      if(template != null) {
         IFlower tree = Binnie.Genetics.getFlowerRoot().templateAsIndividual(template);
         if(tree != null) {
            IFlowerGenome genome = tree.getGenome();
            IAlleleFlowerSpecies treeSpecies = genome.getPrimary();
            int w = 144;
            int h = 176;
            new ControlText(this, new IArea(0.0F, 4.0F, (float)w, 16.0F), "Genome", TextJustification.MiddleCenter);
            ControlScrollableContent scrollable = new ControlScrollableContent(this, 4.0F, 20.0F, (float)(w - 8), (float)(h - 8 - 16), 12.0F);
            Control contents = new Control(scrollable, 0.0F, 0.0F, (float)(w - 8 - 12), (float)(h - 8 - 16));
            int tw = w - 8 - 12;
            int w1 = 55;
            int w2 = tw - 50;
            int y = 0;
            int th = 14;
            int th2 = 18;
            new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), "Temp. : ", TextJustification.MiddleRight);
            new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), treeSpecies.getTemperature().getName(), TextJustification.MiddleLeft);
            y = y + th;
            new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), "Moist. : ", TextJustification.MiddleRight);
            new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), Binnie.Language.localise(treeSpecies.getMoisture()), TextJustification.MiddleLeft);
            y = y + th;
            new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), "pH. : ", TextJustification.MiddleRight);
            new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), Binnie.Language.localise(treeSpecies.getPH()), TextJustification.MiddleLeft);
            y = y + th;
            new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), "Fertility : ", TextJustification.MiddleRight);
            new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), genome.getFertility() + "x", TextJustification.MiddleLeft);
            y = y + th;
            float lifespan = (float)genome.getLifespan() * 68.27F / genome.getAgeChance() / 24000.0F;
            new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), "Lifespan : ", TextJustification.MiddleRight);
            new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), "" + String.format("%.2f", new Object[]{Float.valueOf(lifespan)}) + " days", TextJustification.MiddleLeft);
            y = y + th;
            new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), "Nectar : ", TextJustification.MiddleRight);
            new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), genome.getActiveAllele(EnumFlowerChromosome.SAPPINESS).getName(), TextJustification.MiddleLeft);
            y = y + th;
            int x = w1;
            int tot = 0;

            for(IIndividual vid : BotanyCore.getFlowerRoot().getIndividualTemplates()) {
               if(vid.getGenome().getPrimary() == treeSpecies) {
                  if(tot > 0 && tot % 3 == 0) {
                     x -= 54;
                     y += 18;
                  }

                  ItemStack stack = BotanyCore.getFlowerRoot().getMemberStack((IFlower)vid, EnumFlowerStage.FLOWER.ordinal());
                  ControlItemDisplay display = new ControlItemDisplay(contents, (float)x, (float)y);
                  display.setItemStack(stack);
                  ++tot;
                  x += 18;
               }
            }

            int numOfLines = 1 + (tot - 1) / 3;
            new ControlText(contents, new IArea(0.0F, (float)(y - (numOfLines - 1) * 18), (float)w1, (float)(4 + 18 * numOfLines)), "Varieties : ", TextJustification.MiddleRight);
            y = y + th;
            contents.setSize(new IPoint(contents.size().x(), (float)y));
            scrollable.setScrollableContent(contents);
         }
      }
   }

   public static String tolerated(boolean t) {
      return t?"Tolerated":"Not Tolerated";
   }
}
