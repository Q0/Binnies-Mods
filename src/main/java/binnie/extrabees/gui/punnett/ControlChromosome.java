package binnie.extrabees.gui.punnett;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.controls.core.IControlValue;
import binnie.craftgui.core.Attribute;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.ITooltip;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.extrabees.gui.punnett.ExtraBeeGUITexture;
import forestry.api.genetics.IChromosomeType;

public class ControlChromosome extends Control implements IControlValue, ITooltip {
   IChromosomeType value;

   protected ControlChromosome(IWidget parent, float x, float y, IChromosomeType type) {
      super(parent, x, y, 16.0F, 16.0F);
      this.setValue(type);
      this.addAttribute(Attribute.MouseOver);
   }

   public IChromosomeType getValue() {
      return this.value;
   }

   public void setValue(IChromosomeType value) {
      this.value = value;
   }

   public void onRenderBackground() {
      CraftGUI.Render.texture((Object)ExtraBeeGUITexture.Chromosome, (IArea)this.getArea());
      CraftGUI.Render.colour(16711680);
      CraftGUI.Render.texture((Object)ExtraBeeGUITexture.Chromosome2, (IArea)this.getArea());
   }

   public void getTooltip(Tooltip tooltip) {
      if(this.value != null) {
         tooltip.add(this.value.getName());
      }

   }
}
