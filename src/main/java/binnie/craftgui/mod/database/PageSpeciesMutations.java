package binnie.craftgui.mod.database;

import binnie.craftgui.controls.ControlText;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.mod.database.ControlMutationBox;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.PageSpecies;
import forestry.api.genetics.IAlleleSpecies;

public class PageSpeciesMutations extends PageSpecies {
   private ControlText pageSpeciesFurther_Title = new ControlTextCentered(this, 8.0F, "Further Mutations");
   private ControlMutationBox pageSpeciesFurther_List = new ControlMutationBox(this, 4, 20, 136, 152, ControlMutationBox.Type.Further);

   public PageSpeciesMutations(IWidget parent, DatabaseTab tab) {
      super(parent, tab);
   }

   public void onValueChanged(IAlleleSpecies species) {
      this.pageSpeciesFurther_List.setSpecies(species);
   }
}
