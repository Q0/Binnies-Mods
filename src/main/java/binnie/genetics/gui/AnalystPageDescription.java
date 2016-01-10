package binnie.genetics.gui;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;

public class AnalystPageDescription extends ControlAnalystPage {
    public AnalystPageDescription(IWidget parent, IArea area, IIndividual ind) {
        super(parent, area);
        this.setColour(3355443);
        int y = 4;
        IAlleleSpecies species = ind.getGenome().getPrimary();
        String branchBinomial = species.getBranch() != null ? species.getBranch().getScientific() : "<Unknown>";
        if (species.getBranch() != null) {
            species.getBranch().getName();
        } else {
            String var10000 = "Unknown";
        }

        String desc = species.getDescription();
        String descBody = "§o";
        String descSig = "";
        if (desc != null && desc != "" && !desc.contains("for.description")) {
            String[] descStrings = desc.split("\\|");
            descBody = descBody + descStrings[0];

            for (int i = 1; i < descStrings.length - 1; ++i) {
                descBody = descBody + " " + descStrings[i];
            }

            if (descStrings.length > 1) {
                descSig = descSig + descStrings[descStrings.length - 1];
            }
        } else {
            descBody = descBody + "";
        }

        String authority = species.getAuthority();
        if (authority.contains("Binnie")) {
            authority = "§1§l" + authority;
        }

        if (authority.contains("Sengir")) {
            authority = "§2§l" + authority;
        }

        if (authority.contains("MysteriousAges")) {
            authority = "§5§l" + authority;
        }

        (new ControlTextCentered(this, (float) y, "§nDescription")).setColour(this.getColour());
        y = y + 16;
        (new ControlTextCentered(this, (float) y, species.getName() + "§r")).setColour(this.getColour());
        y = y + 10;
        (new ControlTextCentered(this, (float) y, "§o" + branchBinomial + " " + species.getBinomial() + "§r")).setColour(this.getColour());
        y = y + 20;
        (new ControlTextCentered(this, (float) y, "Discovered by §l" + authority + "§r")).setColour(this.getColour());
        y = (int) ((float) y + 3.0F + CraftGUI.Render.textHeight("Discovered by §l" + authority + "§r", this.w()));
        (new ControlTextCentered(this, (float) y, "Genetic Complexity: " + species.getComplexity())).setColour(this.getColour());
        y = y + 26;
        ControlText descText = new ControlText(this, new IArea(8.0F, (float) y, this.w() - 16.0F, 0.0F), descBody + "§r", TextJustification.TopCenter);
        IWidget signatureText = new ControlText(this, new IArea(8.0F, (float) y, this.w() - 16.0F, 0.0F), descSig + "§r", TextJustification.BottomRight);
        descText.setColour(this.getColour());
        signatureText.setColour(this.getColour());
        float descHeight = CraftGUI.Render.textHeight(descText.getValue(), descText.getSize().x());
        signatureText.setPosition(new IPoint(signatureText.pos().x(), descText.getPosition().y() + descHeight + 10.0F));
        this.setSize(new IPoint(this.w(), 20.0F + signatureText.y()));
    }

    public String getTitle() {
        return "Description";
    }
}
