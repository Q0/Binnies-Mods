package binnie.genetics.gui;

import binnie.core.genetics.Gene;
import binnie.craftgui.controls.listbox.ControlList;
import binnie.craftgui.controls.listbox.ControlOption;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.TextJustification;

public class ControlGenesisOption extends ControlOption {
   public ControlGenesisOption(ControlList parent, Gene gene, int y) {
      super(parent, gene, y);
   }

   String getAlleleName() {
      return ((Gene)this.getValue()).getAlleleName();
   }

   String getChromosomeName() {
      return ((Gene)this.getValue()).getShortChromosomeName();
   }

   public void onRenderBackground() {
      super.onRenderBackground();
      CraftGUI.Render.text(new IArea(0.0F, 0.0F, 70.0F, 22.0F), TextJustification.MiddleCenter, this.getChromosomeName(), this.getColour());
      CraftGUI.Render.text(new IArea(75.0F, 0.0F, 80.0F, 22.0F), TextJustification.MiddleCenter, this.getAlleleName(), this.getColour());
      CraftGUI.Render.solid(new IArea(70.0F, 2.0F, 1.0F, 16.0F), -16777216 + this.getColour());
   }
}
