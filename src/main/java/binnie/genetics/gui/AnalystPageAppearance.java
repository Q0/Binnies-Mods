package binnie.genetics.gui;

import binnie.botany.api.EnumFlowerStage;
import binnie.botany.api.IFlower;
import binnie.craftgui.botany.ControlColourDisplay;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.control.ControlIconDisplay;
import binnie.genetics.gui.ControlAnalystPage;
import forestry.api.genetics.IAlleleSpecies;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

public class AnalystPageAppearance extends ControlAnalystPage {
   public AnalystPageAppearance(IWidget parent, IArea area, final IFlower ind) {
      super(parent, area);
      this.setColour(3355443);
      int y = 4;
      IAlleleSpecies species = ind.getGenome().getPrimary();
      (new ControlTextCentered(this, (float)y, "§nAppearance")).setColour(this.getColour());
      y = y + 12;
      ControlColourDisplay a = new ControlColourDisplay(this, this.w() / 2.0F - 28.0F, (float)y);
      a.setValue(ind.getGenome().getPrimaryColor());
      a.addTooltip("Primary Petal Colour");
      ControlColourDisplay b = new ControlColourDisplay(this, this.w() / 2.0F - 8.0F, (float)y);
      b.setValue(ind.getGenome().getSecondaryColor());
      b.addTooltip("Secondary Petal Colour");
      ControlColourDisplay c = new ControlColourDisplay(this, this.w() / 2.0F + 12.0F, (float)y);
      c.setValue(ind.getGenome().getStemColor());
      c.addTooltip("Stem Colour");
      y = y + 26;
      final int sections = ind.getGenome().getType().getSections();
      final int w = sections > 1?50:100;
      ControlIconDisplay var10001 = new ControlIconDisplay(this, (this.w() - (float)w) / 2.0F, (float)(y - (sections == 1?0:0)), (IIcon)null) {
         public void onRenderForeground() {
            GL11.glPushMatrix();
            float scale = (float)w / 16.0F;
            float dy = sections > 1?16.0F:0.0F;
            GL11.glScalef(scale, scale, 1.0F);
            CraftGUI.Render.colour(ind.getGenome().getStemColor().getColor(false));
            if(sections > 1) {
               CraftGUI.Render.iconBlock(new IPoint(0.0F, 0.0F), ind.getGenome().getType().getStem(EnumFlowerStage.FLOWER, true, 1));
            }

            CraftGUI.Render.iconBlock(new IPoint(0.0F, dy), ind.getGenome().getType().getStem(EnumFlowerStage.FLOWER, true, 0));
            CraftGUI.Render.colour(ind.getGenome().getPrimaryColor().getColor(false));
            if(sections > 1) {
               CraftGUI.Render.iconBlock(new IPoint(0.0F, 0.0F), ind.getGenome().getType().getPetalIcon(EnumFlowerStage.FLOWER, true, 1));
            }

            CraftGUI.Render.iconBlock(new IPoint(0.0F, dy), ind.getGenome().getType().getPetalIcon(EnumFlowerStage.FLOWER, true, 0));
            CraftGUI.Render.colour(ind.getGenome().getSecondaryColor().getColor(false));
            if(sections > 1) {
               CraftGUI.Render.iconBlock(new IPoint(0.0F, 0.0F), ind.getGenome().getType().getVariantIcon(EnumFlowerStage.FLOWER, true, 1));
            }

            CraftGUI.Render.iconBlock(new IPoint(0.0F, dy), ind.getGenome().getType().getVariantIcon(EnumFlowerStage.FLOWER, true, 0));
            GL11.glPopMatrix();
         }
      };
   }

   public String getTitle() {
      return "Appearance";
   }
}
