package binnie.craftgui.botany;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.botany.api.IFlowerColour;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.AbstractMod;
import binnie.craftgui.botany.ControlColourOption;
import binnie.craftgui.botany.PageColourMix;
import binnie.craftgui.botany.PageColourMixResultant;
import binnie.craftgui.botany.PageSpeciesFlowerGenome;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlListBox;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.minecraft.Window;
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
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class WindowBotanistDatabase extends WindowAbstractDatabase {
   ControlListBox selectionBoxColors;

   public WindowBotanistDatabase(EntityPlayer player, Side side, boolean nei) {
      super(player, side, nei, Binnie.Genetics.flowerBreedingSystem, 130.0F);
   }

   public static Window create(EntityPlayer player, Side side, boolean nei) {
      return new WindowBotanistDatabase(player, side, nei);
   }

   protected void addTabs() {
      new PageSpeciesOverview(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(Botany.instance, "species.overview", 0));
      new PageSpeciesFlowerGenome(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(Botany.instance, "species.genome", 0));
      new PageSpeciesClassification(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(Botany.instance, "species.classification", 0));
      new PageSpeciesResultant(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(Botany.instance, "species.resultant", 0));
      new PageSpeciesMutations(this.getInfoPages(WindowAbstractDatabase.Mode.Species), new DatabaseTab(Botany.instance, "species.further", 0));
      new PageBranchOverview(this.getInfoPages(WindowAbstractDatabase.Mode.Branches), new DatabaseTab(Botany.instance, "branches.overview", 0));
      new PageBranchSpecies(this.getInfoPages(WindowAbstractDatabase.Mode.Branches), new DatabaseTab(Botany.instance, "branches.species", 0));
      this.createMode(WindowBotanistDatabase.FlowerMode.Colour, new WindowAbstractDatabase.ModeWidgets(WindowBotanistDatabase.FlowerMode.Colour, this) {
         public void createListBox(IArea area) {
            this.listBox = new ControlListBox(this.modePage, area.x(), area.y(), area.w(), area.h(), 12.0F) {
               public IWidget createOption(IFlowerColour value, int y) {
                  return new ControlColourOption((ControlList)this.getContent(), value, y);
               }
            };
            List<IFlowerColour> colors = new ArrayList();

            for(IFlowerColour c : EnumFlowerColor.values()) {
               colors.add(c);
            }

            this.listBox.setOptions(colors);
         }
      });
      new PageColourMixResultant(this.getInfoPages(WindowBotanistDatabase.FlowerMode.Colour), new DatabaseTab(Botany.instance, "colour.resultant", 0));
      new PageColourMix(this.getInfoPages(WindowBotanistDatabase.FlowerMode.Colour), new DatabaseTab(Botany.instance, "colour.further", 0));
      new PageBreeder(this.getInfoPages(WindowAbstractDatabase.Mode.Breeder), this.getUsername(), new DatabaseTab(Botany.instance, "breeder", 0));
   }

   protected AbstractMod getMod() {
      return Botany.instance;
   }

   protected String getName() {
      return "FlowerDatabase";
   }

   static enum FlowerMode implements IDatabaseMode {
      Colour;

      private FlowerMode() {
      }

      public String getName() {
         return Botany.proxy.localise("gui.database.mode." + this.name().toLowerCase());
      }
   }
}
