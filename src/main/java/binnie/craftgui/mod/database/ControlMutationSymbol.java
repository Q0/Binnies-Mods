package binnie.craftgui.mod.database;

import binnie.core.genetics.BreedingSystem;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.mod.database.WindowAbstractDatabase;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.CraftGUITextureSheet;
import binnie.craftgui.resource.minecraft.StandardTexture;
import forestry.api.genetics.IAllele;
import forestry.api.genetics.IMutation;

class ControlMutationSymbol extends Control implements ITooltip {
   private static Texture MutationPlus = new StandardTexture(2, 94, 16, 16, CraftGUITextureSheet.Controls2);
   private static Texture MutationArrow = new StandardTexture(20, 94, 32, 16, CraftGUITextureSheet.Controls2);
   private IMutation value = null;
   private boolean discovered;
   private int type;

   public void onRenderBackground() {
      super.onRenderBackground();
      if(this.type == 0) {
         CraftGUI.Render.texture(MutationPlus, IPoint.ZERO);
      } else {
         CraftGUI.Render.texture(MutationArrow, IPoint.ZERO);
      }

   }

   protected ControlMutationSymbol(IWidget parent, int x, int y, int type) {
      super(parent, (float)x, (float)y, (float)(16 + type * 16), 16.0F);
      this.type = type;
      this.addAttribute(Attribute.MouseOver);
   }

   public void setValue(IMutation value) {
      this.value = value;
      boolean isNEI = ((WindowAbstractDatabase)this.getSuperParent()).isNEI();
      BreedingSystem system = ((WindowAbstractDatabase)this.getSuperParent()).getBreedingSystem();
      this.discovered = isNEI?true:system.isMutationDiscovered(value, Window.get(this).getWorld(), Window.get(this).getUsername());
      if(this.discovered) {
         this.setColour(16777215);
      } else {
         this.setColour(7829367);
      }

   }

   public void getTooltip(Tooltip tooltip) {
      if(this.type == 1 && this.discovered) {
         IAllele species1 = this.value.getAllele0();
         IAllele species2 = this.value.getAllele1();
         BreedingSystem system = ((WindowAbstractDatabase)this.getSuperParent()).getBreedingSystem();
         float chance = system.getChance(this.value, Window.get(this).getPlayer(), species1, species2);
         tooltip.add("Current Chance - " + chance + "%");
         if(this.value.getSpecialConditions() != null) {
            for(String string : this.value.getSpecialConditions()) {
               tooltip.add(string);
            }
         }
      }

   }
}
