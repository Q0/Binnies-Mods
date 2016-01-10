package binnie.genetics.gui;

import binnie.Binnie;
import binnie.core.Mods;
import binnie.core.genetics.BreedingSystem;
import binnie.core.genetics.ForestryAllele;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.TextJustification;
import binnie.craftgui.minecraft.EnumColor;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.genetics.ExtraBeesSpecies;
import binnie.genetics.gui.ControlAnalystPage;
import binnie.genetics.item.ModuleItem;
import forestry.api.apiculture.IBee;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IAlleleSpecies;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IMutation;
import java.util.Collection;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class AnalystPageMutations extends ControlAnalystPage {
   public AnalystPageMutations(IWidget parent, IArea area, IIndividual ind, boolean isMaster) {
      super(parent, area);
      this.setColour(3355392);
      int y = 4;
      (new ControlTextCentered(this, (float)y, "§nMutations")).setColour(this.getColour());
      y = y + 18;
      final BreedingSystem system = Binnie.Genetics.getSystem(ind.getGenome().getSpeciesRoot());
      system.getDiscoveredMutations(Window.get(this).getWorld(), Window.get(this).getUsername());
      final IAlleleSpecies speciesCurrent = ind.getGenome().getPrimary();
      Collection<IMutation> resultant = system.getResultantMutations(speciesCurrent);
      Collection<IMutation> further = system.getFurtherMutations(speciesCurrent);
      if(ind instanceof IBee) {
         ItemStack hive = null;
         if(ind.getGenome().getPrimary() == ExtraBeesSpecies.WATER) {
            hive = new ItemStack(ExtraBees.hive, 1, 0);
         }

         if(ind.getGenome().getPrimary() == ExtraBeesSpecies.ROCK) {
            hive = new ItemStack(ExtraBees.hive, 1, 1);
         }

         if(ind.getGenome().getPrimary() == ExtraBeesSpecies.BASALT) {
            hive = new ItemStack(ExtraBees.hive, 1, 2);
         }

         if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Forest.getAllele()) {
            hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 1);
         }

         if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Meadows.getAllele()) {
            hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 2);
         }

         if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Modest.getAllele()) {
            hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 3);
         }

         if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Tropical.getAllele()) {
            hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 4);
         }

         if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Ended.getAllele()) {
            hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 5);
         }

         if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Wintry.getAllele()) {
            hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 6);
         }

         if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Marshy.getAllele()) {
            hive = new ItemStack(Mods.Forestry.block("beehives"), 1, 7);
         }

         if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Steadfast.getAllele()) {
            hive = new ItemStack(Blocks.chest);
         }

         if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Valiant.getAllele()) {
            (new ControlTextCentered(this, (float)y, "Natural Habitat")).setColour(this.getColour());
            y = y + 10;
            (new ControlTextCentered(this, (float)y, "§oFound in any Hive")).setColour(this.getColour());
            y = y + 22;
         } else if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Monastic.getAllele()) {
            (new ControlTextCentered(this, (float)y, "Natural Habitat")).setColour(this.getColour());
            y = y + 10;
            (new ControlTextCentered(this, (float)y, "§oBought from Villagers")).setColour(this.getColour());
            y = y + 22;
         } else if(hive != null) {
            (new ControlTextCentered(this, (float)y, "Natural Habitat")).setColour(this.getColour());
            y = y + 10;
            ControlItemDisplay display = new ControlItemDisplay(this, (this.w() - 16.0F) / 2.0F, (float)y);
            if(ind.getGenome().getPrimary() == ForestryAllele.BeeSpecies.Steadfast.getAllele()) {
               display.addTooltip("Dungeon Chests");
            } else {
               display.setTooltip();
            }

            display.setItemStack(hive);
            y = y + 24;
         }
      }

      float ox = (this.w() - 88.0F - 8.0F) / 2.0F;
      float dx = 0.0F;
      if(!resultant.isEmpty()) {
         if(resultant.size() == 1) {
            ox = (this.w() - 44.0F) / 2.0F;
         }

         (new ControlTextCentered(this, (float)y, "Resultant Mutations")).setColour(this.getColour());
         y = y + 10;

         for(final IMutation mutation : resultant) {
            final float specificChance = this.getSpecificChance(ind, mutation, system);
            if(!isMaster && !this.isKnown(system, mutation)) {
               new AnalystPageMutations.ControlUnknownMutation(this, ox + dx, (float)y, 44.0F, 16.0F);
            } else {
               Control var10001 = new Control(this, ox + dx, (float)y, 44.0F, 16.0F) {
                  public void initialise() {
                     IAlleleSpecies species0 = (IAlleleSpecies)mutation.getAllele0();
                     IAlleleSpecies species1 = (IAlleleSpecies)mutation.getAllele1();
                     String comb = species0.getName() + " + " + species1.getName();
                     this.addTooltip(comb);
                     String chance = AnalystPageMutations.this.getMutationColour(mutation.getBaseChance()).getCode() + (int)mutation.getBaseChance() + "% Chance";
                     if(specificChance != mutation.getBaseChance()) {
                        chance = chance + AnalystPageMutations.this.getMutationColour(specificChance).getCode() + " (" + (int)specificChance + "% currently)";
                     }

                     this.addTooltip(chance);

                     for(String s : mutation.getSpecialConditions()) {
                        this.addTooltip(s);
                     }

                  }

                  public void onRenderBackground() {
                     CraftGUI.Render.item(new IPoint(0.0F, 0.0F), system.getDefaultMember(mutation.getAllele0().getUID()));
                     CraftGUI.Render.item(new IPoint(28.0F, 0.0F), system.getDefaultMember(mutation.getAllele1().getUID()));
                     if(specificChance != mutation.getBaseChance()) {
                        CraftGUI.Render.colour(AnalystPageMutations.this.getMutationColour(mutation.getBaseChance()).getColour());
                        CraftGUI.Render.iconItem(new IPoint(14.0F, 0.0F), ModuleItem.iconAdd0.getIcon());
                        CraftGUI.Render.colour(AnalystPageMutations.this.getMutationColour(specificChance).getColour());
                        CraftGUI.Render.iconItem(new IPoint(14.0F, 0.0F), ModuleItem.iconAdd1.getIcon());
                     } else {
                        CraftGUI.Render.colour(AnalystPageMutations.this.getMutationColour(mutation.getBaseChance()).getColour());
                        CraftGUI.Render.iconItem(new IPoint(14.0F, 0.0F), ModuleItem.iconAdd.getIcon());
                     }

                  }
               };
            }

            dx = 52.0F - dx;
            if(dx == 0.0F || resultant.size() == 1) {
               y += 18;
            }
         }

         if(dx != 0.0F && resultant.size() != 1) {
            y += 18;
         }

         y = y + 10;
      }

      ox = (this.w() - 88.0F - 8.0F) / 2.0F;
      dx = 0.0F;
      if(!further.isEmpty()) {
         if(further.size() == 1) {
            ox = (this.w() - 44.0F) / 2.0F;
         }

         (new ControlTextCentered(this, (float)y, "Further Mutations")).setColour(this.getColour());
         y += 10;

         for(final IMutation mutation : further) {
            final IAllele speciesComb = mutation.getPartner(speciesCurrent);
            final float specificChance = this.getSpecificChance(ind, mutation, system);
            if(!isMaster && !this.isKnown(system, mutation)) {
               new AnalystPageMutations.ControlUnknownMutation(this, ox + dx, (float)y, 44.0F, 16.0F);
            } else {
               Control var30 = new Control(this, ox + dx, (float)y, 44.0F, 16.0F) {
                  public void initialise() {
                     IAlleleSpecies species0 = (IAlleleSpecies)speciesComb;
                     IAlleleSpecies species1 = (IAlleleSpecies)mutation.getTemplate()[0];
                     this.addTooltip(species1.getName());
                     String comb = speciesCurrent.getName() + " + " + species0.getName();
                     this.addTooltip(comb);
                     String chance = AnalystPageMutations.this.getMutationColour(mutation.getBaseChance()).getCode() + (int)mutation.getBaseChance() + "% Chance";
                     if(specificChance != mutation.getBaseChance()) {
                        chance = chance + AnalystPageMutations.this.getMutationColour(specificChance).getCode() + " (" + (int)specificChance + "% currently)";
                     }

                     this.addTooltip(chance);

                     for(String s : mutation.getSpecialConditions()) {
                        this.addTooltip(s);
                     }

                  }

                  public void onRenderBackground() {
                     CraftGUI.Render.item(new IPoint(0.0F, 0.0F), system.getDefaultMember(speciesComb.getUID()));
                     CraftGUI.Render.item(new IPoint(28.0F, 0.0F), system.getDefaultMember(mutation.getTemplate()[0].getUID()));
                     CraftGUI.Render.colour(AnalystPageMutations.this.getMutationColour(mutation.getBaseChance()).getColour());
                     if(specificChance != mutation.getBaseChance()) {
                        CraftGUI.Render.colour(AnalystPageMutations.this.getMutationColour(mutation.getBaseChance()).getColour());
                        CraftGUI.Render.iconItem(new IPoint(14.0F, 0.0F), ModuleItem.iconArrow0.getIcon());
                        CraftGUI.Render.colour(AnalystPageMutations.this.getMutationColour(specificChance).getColour());
                        CraftGUI.Render.iconItem(new IPoint(14.0F, 0.0F), ModuleItem.iconArrow1.getIcon());
                     } else {
                        CraftGUI.Render.colour(AnalystPageMutations.this.getMutationColour(mutation.getBaseChance()).getColour());
                        CraftGUI.Render.iconItem(new IPoint(14.0F, 0.0F), ModuleItem.iconArrow.getIcon());
                     }

                  }
               };
            }

            dx = 52.0F - dx;
            if(dx == 0.0F || further.size() == 1) {
               y += 18;
            }
         }

         if(dx != 0.0F && further.size() != 1) {
            y += 18;
         }
      }

      y = y + 8;
      this.setSize(new IPoint(this.w(), (float)y));
   }

   private boolean isKnown(BreedingSystem system, IMutation mutation) {
      return system.getDiscoveredMutations(this.getWindow().getWorld(), this.getWindow().getPlayer().getGameProfile()).contains(mutation);
   }

   private float getSpecificChance(IIndividual ind, IMutation mutation, BreedingSystem system) {
      return system.getChance(mutation, this.getWindow().getPlayer(), mutation.getAllele0(), mutation.getAllele1());
   }

   public String getTitle() {
      return "Mutations";
   }

   protected EnumColor getMutationColour(float percent) {
      return percent >= 20.0F?EnumColor.DarkGreen:(percent >= 15.0F?EnumColor.Green:(percent >= 10.0F?EnumColor.Yellow:(percent >= 5.0F?EnumColor.Gold:(percent > 0.0F?EnumColor.Red:EnumColor.DarkRed))));
   }

   static class ControlUnknownMutation extends Control {
      public ControlUnknownMutation(IWidget parent, float x, float y, float w, float h) {
         super(parent, x, y, w, h);
         this.addAttribute(Attribute.MouseOver);
         this.addTooltip("Unknown Mutation");
      }

      public void onRenderBackground() {
         CraftGUI.Render.text(this.getArea(), TextJustification.MiddleCenter, "UNKNOWN", 11184810);
      }
   }
}
