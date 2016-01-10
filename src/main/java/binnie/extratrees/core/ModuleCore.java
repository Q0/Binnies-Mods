package binnie.extratrees.core;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.block.ILogType;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.carpentry.EnumPattern;
import binnie.extratrees.genetics.ExtraTreeFruitGene;
import binnie.extratrees.genetics.ExtraTreeSpecies;
import forestry.api.arboriculture.IAlleleTreeSpecies;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class ModuleCore implements IInitializable {
    public ModuleCore() {
        super();
    }

    public void preInit() {
    }

    public void init() {
    }

    public void postInit() {
        if (BinnieCore.proxy.isDebug()) {
            try {
                PrintWriter outputSpecies = new PrintWriter(new FileWriter("data/species.html"));
                PrintWriter outputLogs = new PrintWriter(new FileWriter("data/logs.html"));
                PrintWriter outputPlanks = new PrintWriter(new FileWriter("data/planks.html"));
                PrintWriter outputFruit = new PrintWriter(new FileWriter("data/fruit.html"));
                PrintWriter outputDesigns = new PrintWriter(new FileWriter("data/designs.html"));
                Queue<IAlleleTreeSpecies> speciesQueue = new LinkedList();

                for (IAlleleTreeSpecies s : ExtraTreeSpecies.values()) {
                    speciesQueue.add(s);
                }

                Queue<ILogType> logQueue = new LinkedList();

                for (ILogType wood : ILogType.ExtraTreeLog.values()) {
                    logQueue.add(wood);
                }

                Queue<IDesignMaterial> plankQueue = new LinkedList();

                for (IDesignMaterial wood : PlankType.ExtraTreePlanks.values()) {
                    plankQueue.add(wood);
                }

                Queue<ExtraTreeFruitGene> fruitQueue = new LinkedList();

                for (ExtraTreeFruitGene wood : ExtraTreeFruitGene.values()) {
                    fruitQueue.add(wood);
                }

                Queue<IDesign> designQueue = new LinkedList();

                for (IDesign wood : CarpentryManager.carpentryInterface.getSortedDesigns()) {
                    designQueue.add(wood);
                }

                fruitQueue.remove(ExtraTreeFruitGene.Apple);
                outputSpecies.println("<table style=\"width: 100%;\">");

                while (!((Queue) speciesQueue).isEmpty()) {
                    outputSpecies.println("<tr>");

                    for (int i = 0; i < 4; ++i) {
                        IAlleleTreeSpecies species = (IAlleleTreeSpecies) speciesQueue.poll();
                        outputSpecies.println("<td>" + (species == null ? "" : species.getName()) + "</td>");
                    }

                    outputSpecies.println("</tr>");
                }

                outputSpecies.println("</table>");
                outputLogs.println("<table style=\"width: 100%;\">");

                while (!((Queue) logQueue).isEmpty()) {
                    outputLogs.println("<tr>");

                    for (int i = 0; i < 4; ++i) {
                        ILogType.ExtraTreeLog wood = (ILogType.ExtraTreeLog) logQueue.poll();
                        if (wood == null) {
                            outputLogs.println("<td></td>");
                        } else {
                            String img = "<img alt=\"" + wood.getName() + "\" src=\"images/logs/" + wood.toString().toLowerCase() + "Bark.png\">";
                            outputLogs.println("<td>" + img + " " + wood.getName() + "</td>");
                        }
                    }

                    outputLogs.println("</tr>");
                }

                outputLogs.println("</table>");
                outputPlanks.println("<table style=\"width: 100%;\">");

                while (!((Queue) plankQueue).isEmpty()) {
                    outputPlanks.println("<tr>");

                    for (int i = 0; i < 4; ++i) {
                        IDesignMaterial wood = (IDesignMaterial) plankQueue.poll();
                        if (wood == null) {
                            outputPlanks.println("<td></td>");
                        } else {
                            String img = "<img alt=\"" + wood.getName() + "\" src=\"images/planks/" + wood.getName() + ".png\">";
                            outputPlanks.println("<td>" + img + " " + wood.getName() + "</td>");
                        }
                    }

                    outputPlanks.println("</tr>");
                }

                outputPlanks.println("</table>");
                outputFruit.println("<table style=\"width: 100%;\">");

                while (!((Queue) fruitQueue).isEmpty()) {
                    outputFruit.println("<tr>");

                    for (int i = 0; i < 4; ++i) {
                        ExtraTreeFruitGene wood = (ExtraTreeFruitGene) fruitQueue.poll();
                        if (wood == null) {
                            outputFruit.println("<td></td>");
                        } else {
                            String fruit = wood.getNameOfFruit();
                            String img = "<img alt=\"" + wood.getName() + "\" src=\"images/fruits/" + fruit + ".png\">";
                            outputFruit.println("<td>" + img + " " + wood.getName() + "</td>");
                        }
                    }

                    outputFruit.println("</tr>");
                }

                outputFruit.println("</table>");
                outputDesigns.println("<table style=\"width: 100%;\">");

                while (!((Queue) designQueue).isEmpty()) {
                    outputDesigns.println("<tr>");

                    for (int i = 0; i < 4; ++i) {
                        IDesign wood = (IDesign) designQueue.poll();
                        if (wood == null) {
                            outputDesigns.println("<td></td>");
                        } else {
                            String texture = ((EnumPattern) wood.getTopPattern().getPattern()).toString().toLowerCase();
                            String img = "<img alt=\"" + texture + "\" src=\"images/pattern/" + texture + ".png\">";
                            outputDesigns.println("<td>" + img + " " + wood.getName() + "</td>");
                        }
                    }

                    outputDesigns.println("</tr>");
                }

                outputDesigns.println("</table>");
                outputSpecies.close();
                outputLogs.close();
                outputPlanks.close();
                outputFruit.close();
                outputDesigns.close();
            } catch (IOException var16) {
                var16.printStackTrace();
            }
        }

    }
}
