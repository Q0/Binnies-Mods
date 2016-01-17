package binnie.craftgui.extratrees.dictionary;

import binnie.Binnie;
import binnie.core.BinnieCore;
import binnie.core.genetics.BreedingSystem;
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
import binnie.extratrees.ExtraTrees;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.IAlleleTreeSpecies;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.core.ForestryAPI;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class PageSpeciesTreeGenome extends PageSpecies {
    public PageSpeciesTreeGenome(IWidget parent, DatabaseTab tab) {
        super(parent, tab);
    }

    public void onValueChanged(IAlleleSpecies species) {
        this.deleteAllChildren();
        IAllele[] template = Binnie.Genetics.getTreeRoot().getTemplate(species.getUID());
        if(template != null) {
            ITree tree = (ITree) Binnie.Genetics.getTreeRoot().templateAsIndividual(template);
            if(tree != null) {
                ITreeGenome genome = tree.getGenome();
                IAlleleTreeSpecies treeSpecies = genome.getPrimary();
                int w = 144;
                int h = 176;
                new ControlText(this, new IArea(0.0F, 4.0F, (float)w, 16.0F), ((DatabaseTab)this.getValue()).toString(), TextJustification.MiddleCenter);
                ControlScrollableContent scrollable = new ControlScrollableContent(this, 4.0F, 20.0F, (float)(w - 8), (float)(h - 8 - 16), 12.0F);
                Control contents = new Control(scrollable, 0.0F, 0.0F, (float)(w - 8 - 12), (float)(h - 8 - 16));
                int tw = w - 8 - 12;
                int w1 = 65;
                int w2 = tw - 50;
                int y = 0;
                int th = 14;
                int th2 = 18;
                BreedingSystem syst = Binnie.Genetics.treeBreedingSystem;
                new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), syst.getChromosomeShortName(EnumTreeChromosome.PLANT) + " : ", TextJustification.MiddleRight);
                new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), treeSpecies.getPlantType().toString(), TextJustification.MiddleLeft);
                y = y + th;
                new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), BinnieCore.proxy.localise("gui.temperature.short") + " : ", TextJustification.MiddleRight);
                new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), treeSpecies.getTemperature().getName(), TextJustification.MiddleLeft);
                y = y + th;
                //TODO:FIX
                IIcon leaf = treeSpecies.getLeafIcon(true, false);
                IIcon fruit = null;
                int fruitColour = 16777215;

                try {
                    fruit = ForestryAPI.textureManager.getIcon(genome.getFruitProvider().getIconIndex(genome, (IBlockAccess)null, 0, 0, 0, 100, false));
                    fruitColour = genome.getFruitProvider().getColour(genome, (IBlockAccess)null, 0, 0, 0, 100);
                } catch (Exception var26) {
                    ;
                }

                if(leaf != null) {
                    new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th2), ExtraTrees.proxy.localise("gui.database.leaves") + " : ", TextJustification.MiddleRight);
                    (new ControlBlockIconDisplay(contents, (float)w1, (float)y, leaf)).setColour(treeSpecies.getIconColour(0));
                    if(fruit != null && !treeSpecies.getUID().equals("forestry.treeOak")) {
                        (new ControlBlockIconDisplay(contents, (float)w1, (float)y, fruit)).setColour(fruitColour);
                    }

                    y += th2;
                }
                //TODO:FIX
                /*ItemStack log = treeSpecies.getLogStacks().length > 0?treeSpecies.getLogStacks()[0]:null;
                if(log != null) {
                    new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th2), ExtraTrees.proxy.localise("gui.database.log") + " : ", TextJustification.MiddleRight);
                    ControlItemDisplay display = new ControlItemDisplay(contents, (float)w1, (float)y);
                    display.setItemStack(log);
                    display.setTooltip();
                    y += th2;
                }*/

                new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), syst.getChromosomeShortName(EnumTreeChromosome.GROWTH) + " : ", TextJustification.MiddleRight);
                new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), genome.getGrowthProvider().getDescription(), TextJustification.MiddleLeft);
                y = y + th;
                new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), syst.getChromosomeShortName(EnumTreeChromosome.HEIGHT) + " : ", TextJustification.MiddleRight);
                new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), genome.getHeight() + "x", TextJustification.MiddleLeft);
                y = y + th;
                new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), syst.getChromosomeShortName(EnumTreeChromosome.FERTILITY) + " : ", TextJustification.MiddleRight);
                new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), genome.getFertility() + "x", TextJustification.MiddleLeft);
                y = y + th;
                List<ItemStack> fruits = new ArrayList();

                for(ItemStack stack : genome.getFruitProvider().getProducts()) {
                    fruits.add(stack);
                }

                if(!fruits.isEmpty()) {
                    new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th2), syst.getChromosomeShortName(EnumTreeChromosome.FRUITS) + " : ", TextJustification.MiddleRight);

                    for(ItemStack fruitw : fruits) {
                        ControlItemDisplay display = new ControlItemDisplay(contents, (float)w1, (float)y);
                        display.setItemStack(fruitw);
                        display.setTooltip();
                        y += th2;
                    }
                }

                new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), syst.getChromosomeShortName(EnumTreeChromosome.YIELD) + " : ", TextJustification.MiddleRight);
                new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), genome.getYield() + "x", TextJustification.MiddleLeft);
                y = y + th;
                new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), syst.getChromosomeShortName(EnumTreeChromosome.SAPPINESS) + " : ", TextJustification.MiddleRight);
                new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), genome.getSappiness() + "x", TextJustification.MiddleLeft);
                y = y + th;
                new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), syst.getChromosomeShortName(EnumTreeChromosome.MATURATION) + " : ", TextJustification.MiddleRight);
                new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), genome.getMaturationTime() + "x", TextJustification.MiddleLeft);
                y = y + th;
                new ControlText(contents, new IArea(0.0F, (float)y, (float)w1, (float)th), syst.getChromosomeShortName(EnumTreeChromosome.GIRTH) + " : ", TextJustification.MiddleRight);
                new ControlText(contents, new IArea((float)w1, (float)y, (float)w2, (float)th), genome.getGirth() + "x" + genome.getGirth(), TextJustification.MiddleLeft);
                y = y + th;
                contents.setSize(new IPoint(contents.size().x(), (float)y));
                scrollable.setScrollableContent(contents);
            }
        }
    }

    public static String tolerated(boolean t) {
        return t?BinnieCore.proxy.localise("gui.tolerated"):BinnieCore.proxy.localise("gui.nottolerated");
    }
}
