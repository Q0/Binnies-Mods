package binnie.craftgui.extratrees.dictionary;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.craftgui.extratrees.dictionary.PageSpeciesImage;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.PageBranchOverview;
import binnie.craftgui.mod.database.PageBranchSpecies;
import binnie.craftgui.mod.database.PageBreeder;
import binnie.craftgui.mod.database.PageSpeciesClassification;
import binnie.craftgui.mod.database.PageSpeciesMutations;
import binnie.craftgui.mod.database.PageSpeciesOverview;
import binnie.craftgui.mod.database.PageSpeciesResultant;
import binnie.craftgui.mod.database.WindowAbstractDatabase;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

public class WindowLepidopteristDatabase extends WindowAbstractDatabase {
   public WindowLepidopteristDatabase(EntityPlayer player, Side side, boolean nei) {
      super(player, side, nei, Binnie.Genetics.mothBreedingSystem, 160.0F);
   }

   public static Window create(EntityPlayer player, Side side, boolean nei) {
      return new WindowLepidopteristDatabase(player, side, nei);
   }

   protected void addTabs() {
      new PageSpeciesOverview(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.overview", 0));
      new PageSpeciesClassification(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.classification", 0));
      new PageSpeciesImage(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.specimen", 0));
      new PageSpeciesResultant(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.resultant", 0));
      new PageSpeciesMutations(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraTrees.instance, "butterfly.species.further", 0));
      new PageBranchOverview(this.getInfoPages(WindowAbstractDatabase.Mode.Branches), new DatabaseTab(ExtraTrees.instance, "butterfly.branches.overview", 0));
      new PageBranchSpecies(this.getInfoPages(WindowAbstractDatabase.Mode.Branches), new DatabaseTab(ExtraTrees.instance, "butterfly.branches.species", 0));
      new PageBreeder(this.getInfoPages(WindowAbstractDatabase.Mode.Breeder), this.getUsername(), new DatabaseTab(ExtraTrees.instance, "butterfly.breeder", 0));
   }

   protected AbstractMod getMod() {
      return ExtraTrees.instance;
   }

   protected String getName() {
      return "MothDatabase";
   }
}
