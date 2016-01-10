package binnie.extrabees.gui.database;

import binnie.Binnie;
import binnie.core.AbstractMod;
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
import binnie.extrabees.ExtraBees;
import binnie.extrabees.gui.database.PageSpeciesClimate;
import binnie.extrabees.gui.database.PageSpeciesGenome;
import binnie.extrabees.gui.database.PageSpeciesProducts;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

public class WindowApiaristDatabase extends WindowAbstractDatabase {
   protected void addTabs() {
      new PageSpeciesOverview(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraBees.instance, "species.overview", 0));
      new PageSpeciesClassification(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraBees.instance, "species.classification", 0));
      new PageSpeciesGenome(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraBees.instance, "species.genome", 0));
      new PageSpeciesProducts(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraBees.instance, "species.products", 0));
      new PageSpeciesClimate(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraBees.instance, "species.climate", 0));
      new PageSpeciesResultant(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraBees.instance, "species.resultant", 0));
      new PageSpeciesMutations(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraBees.instance, "species.further", 0));
      new PageBranchOverview(this.getInfoPages(WindowAbstractDatabase.Mode.Branches), new DatabaseTab(ExtraBees.instance, "branches.overview", 0));
      new PageBranchSpecies(this.getInfoPages(WindowAbstractDatabase.Mode.Branches), new DatabaseTab(ExtraBees.instance, "branches.species", 0));
      new PageBreeder(this.getInfoPages(WindowAbstractDatabase.Mode.Breeder), this.getUsername(), new DatabaseTab(ExtraBees.instance, "breeder", 0));
   }

   public WindowApiaristDatabase(EntityPlayer player, Side side, boolean nei) {
      super(player, side, nei, Binnie.Genetics.beeBreedingSystem, 110.0F);
   }

   public static Window create(EntityPlayer player, Side side, boolean nei) {
      return new WindowApiaristDatabase(player, side, nei);
   }

   public AbstractMod getMod() {
      return ExtraBees.instance;
   }

   public String getName() {
      return "Database";
   }

   static enum BranchesTab {
      Overview(255),
      Species(16711680);

      public int colour;

      private BranchesTab(int colour) {
         this.colour = colour;
      }
   }

   static enum SpeciesTab {
      Overview(255),
      Genome(16776960),
      Productivity('\uffff'),
      Climate(16711680),
      ResultantMutations(16711935),
      FurtherMutations('\uff00');

      public int colour;

      private SpeciesTab(int colour) {
         this.colour = colour;
      }
   }
}
