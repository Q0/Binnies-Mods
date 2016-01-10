package binnie.craftgui.extratrees.dictionary;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.genetics.TreeBreedingSystem;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.extratrees.dictionary.PageFruit;
import binnie.craftgui.extratrees.dictionary.PagePlanksOverview;
import binnie.craftgui.extratrees.dictionary.PagePlanksTrees;
import binnie.craftgui.extratrees.dictionary.PageSpeciesTreeGenome;
import binnie.craftgui.extratrees.dictionary.PageWood;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.mod.database.ControlItemStackOption;
import binnie.craftgui.mod.database.DatabaseTab;
import binnie.craftgui.mod.database.IDatabaseMode;
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
import net.minecraft.item.ItemStack;

public class WindowArboristDatabase extends WindowAbstractDatabase {
   ControlListBox selectionBoxFruit;
   ControlListBox selectionBoxWood;
   ControlListBox selectionBoxPlanks;

   public WindowArboristDatabase(EntityPlayer player, Side side, boolean nei) {
      super(player, side, nei, Binnie.Genetics.treeBreedingSystem, 120.0F);
   }

   public static Window create(EntityPlayer player, Side side, boolean nei) {
      return new WindowArboristDatabase(player, side, nei);
   }

   protected void addTabs() {
      new PageSpeciesOverview(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.overview", 0));
      new PageSpeciesTreeGenome(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.genome", 0));
      new PageSpeciesClassification(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.classification", 0));
      new PageSpeciesResultant(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.resultant", 0));
      new PageSpeciesMutations(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(ExtraTrees.instance, "species.further", 0));
      new PageBranchOverview(this.getInfoPages(WindowAbstractDatabase.Mode.Branches), new DatabaseTab(ExtraTrees.instance, "branches.overview", 0));
      new PageBranchSpecies(this.getInfoPages(WindowAbstractDatabase.Mode.Branches), new DatabaseTab(ExtraTrees.instance, "branches.species", 0));
      new PageBreeder(this.getInfoPages(WindowAbstractDatabase.Mode.Breeder), this.getUsername(), new DatabaseTab(ExtraTrees.instance, "breeder", 0));
      this.createMode(WindowArboristDatabase.TreeMode.Fruit, new WindowAbstractDatabase.ModeWidgets(WindowArboristDatabase.TreeMode.Fruit, this) {
         public void createListBox(IArea area) {
            this.listBox = new ControlListBox(this.modePage, area.x(), area.y(), area.w(), area.h(), 12.0F) {
               public IWidget createOption(ItemStack value, int y) {
                  return new ControlItemStackOption((ControlList)this.getContent(), value, y);
               }
            };
            this.listBox.setOptions(((TreeBreedingSystem)WindowArboristDatabase.this.getBreedingSystem()).allFruits);
         }
      });
      this.createMode(WindowArboristDatabase.TreeMode.Wood, new WindowAbstractDatabase.ModeWidgets(WindowArboristDatabase.TreeMode.Wood, this) {
         public void createListBox(IArea area) {
            this.listBox = new ControlListBox(this.modePage, area.x(), area.y(), area.w(), area.h(), 12.0F) {
               public IWidget createOption(ItemStack value, int y) {
                  return new ControlItemStackOption((ControlList)this.getContent(), value, y);
               }
            };
            this.listBox.setOptions(((TreeBreedingSystem)WindowArboristDatabase.this.getBreedingSystem()).allWoods);
         }
      });
      this.createMode(WindowArboristDatabase.TreeMode.Planks, new WindowAbstractDatabase.ModeWidgets(WindowArboristDatabase.TreeMode.Planks, this) {
         public void createListBox(IArea area) {
            this.listBox = new ControlListBox(this.modePage, area.x(), area.y(), area.w(), area.h(), 12.0F) {
               public IWidget createOption(ItemStack value, int y) {
                  return new ControlItemStackOption((ControlList)this.getContent(), value, y);
               }
            };
         }
      });
      new PageFruit(this.getInfoPages(WindowArboristDatabase.TreeMode.Fruit), new DatabaseTab(ExtraTrees.instance, "fruit.natural", 0), true);
      new PageFruit(this.getInfoPages(WindowArboristDatabase.TreeMode.Fruit), new DatabaseTab(ExtraTrees.instance, "fruit.potential", 0), false);
      new PageWood(this.getInfoPages(WindowArboristDatabase.TreeMode.Wood), new DatabaseTab(ExtraTrees.instance, "wood.natural", 0));
      new PagePlanksOverview(this.getInfoPages(WindowArboristDatabase.TreeMode.Planks), new DatabaseTab(ExtraTrees.instance, "planks.overview", 0));
      new PagePlanksTrees(this.getInfoPages(WindowArboristDatabase.TreeMode.Planks), new DatabaseTab(ExtraTrees.instance, "planks.natural", 1));
   }

   protected AbstractMod getMod() {
      return ExtraTrees.instance;
   }

   protected String getName() {
      return "TreeDatabase";
   }

   static enum TreeMode implements IDatabaseMode {
      Fruit,
      Wood,
      Planks;

      private TreeMode() {
      }

      public String getName() {
         return ExtraTrees.proxy.localise("gui.database.mode." + this.name().toLowerCase());
      }
   }
}
