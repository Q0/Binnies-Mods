package binnie.craftgui.mod.database;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.mod.database.ControlDatabaseIndividualDisplay;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.EnumDiscoveryState;
import binnie.craftgui.mod.database.PageSpecies;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesOverview extends PageSpecies {
   private ControlText controlName = new ControlTextCentered(this, 8.0F, "");
   private ControlText controlScientific = new ControlTextCentered(this, 32.0F, "");
   private ControlText controlAuthority = new ControlTextCentered(this, 44.0F, "");
   private ControlText controlComplexity = new ControlTextCentered(this, 56.0F, "");
   private ControlText controlDescription = new ControlText(this, new IArea(8.0F, 84.0F, this.getSize().x() - 16.0F, 0.0F), "", TextJustification.MiddleCenter);
   private ControlText controlSignature = new ControlText(this, new IArea(8.0F, 84.0F, this.getSize().x() - 16.0F, 0.0F), "", TextJustification.BottomRight);
   private ControlDatabaseIndividualDisplay controlInd1 = new ControlDatabaseIndividualDisplay(this, 5.0F, 5.0F);
   private ControlDatabaseIndividualDisplay controlInd2 = new ControlDatabaseIndividualDisplay(this, 123.0F, 5.0F);

   public PageSpeciesOverview(IWidget parent, DatabaseTab tab) {
      super(parent, tab);
   }

   public void onValueChanged(IAlleleSpecies species) {
      this.controlInd1.setSpecies(species, EnumDiscoveryState.Show);
      this.controlInd2.setSpecies(species, EnumDiscoveryState.Show);
      String branchBinomial = species.getBranch() != null?species.getBranch().getScientific():"<Unknown>";
      if(species.getBranch() != null) {
         species.getBranch().getName();
      } else {
         String var10000 = "Unknown";
      }

      this.controlName.setValue("§n" + species.getName() + "§r");
      this.controlScientific.setValue("§o" + branchBinomial + " " + species.getBinomial() + "§r");
      this.controlAuthority.setValue("Discovered by §l" + species.getAuthority() + "§r");
      this.controlComplexity.setValue("Complexity: " + species.getComplexity());
      String desc = species.getDescription();
      String descBody = "§o";
      String descSig = "";
      if(desc != null && desc != "") {
         String[] descStrings = desc.split("\\|");
         descBody = descBody + descStrings[0];

         for(int i = 1; i < descStrings.length - 1; ++i) {
            descBody = descBody + " " + descStrings[i];
         }

         if(descStrings.length > 1) {
            descSig = descSig + descStrings[descStrings.length - 1];
         }
      } else {
         descBody = descBody + "No Description Provided.";
      }

      this.controlDescription.setValue(descBody + "§r");
      this.controlSignature.setValue(descSig + "§r");
      float descHeight = CraftGUI.Render.textHeight(this.controlDescription.getValue(), this.controlDescription.getSize().x());
      this.controlSignature.setPosition(new IPoint(this.controlSignature.pos().x(), this.controlDescription.getPosition().y() + descHeight + 10.0F));
   }
}
