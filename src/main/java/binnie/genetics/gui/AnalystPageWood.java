package binnie.genetics.gui;

import binnie.core.util.UniqueItemStackSet;
import binnie.craftgui.controls.ControlTextCentered;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.control.ControlIconDisplay;
import binnie.craftgui.minecraft.control.ControlItemDisplay;
import binnie.genetics.gui.AnalystPageProduce;
import binnie.genetics.item.ModuleItem;
import forestry.api.arboriculture.EnumTreeChromosome;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeGenome;
import forestry.api.genetics.IAlleleBoolean;
import java.util.Collection;
import net.minecraft.item.ItemStack;

public class AnalystPageWood extends AnalystPageProduce {
   public AnalystPageWood(IWidget parent, IArea area, ITree ind) {
      super(parent, area);
      this.setColour(6697728);
      ITreeGenome genome = ind.getGenome();
      int y = 4;
      (new ControlTextCentered(this, (float)y, "§nWood")).setColour(this.getColour());
      y = y + 12;
      if(((IAlleleBoolean)ind.getGenome().getActiveAllele(EnumTreeChromosome.FIREPROOF)).getValue()) {
         (new ControlIconDisplay(this, (this.w() - 16.0F) / 2.0F, (float)y, ModuleItem.iconNoFire.getIcon())).addTooltip("Fireproof");
      } else {
         (new ControlIconDisplay(this, (this.w() - 16.0F) / 2.0F, (float)y, ModuleItem.iconFire.getIcon())).addTooltip("Flammable");
      }

      y = y + 30;
      Collection<ItemStack> products = new UniqueItemStackSet();

      for(ItemStack stack : ind.getGenome().getPrimary().getLogStacks()) {
         products.add(stack);
      }

      if(products.size() > 0) {
         (new ControlTextCentered(this, (float)y, "Logs")).setColour(this.getColour());
         y = y + 10;
         int w = products.size() * 18 - 2;
         int i = 0;

         for(ItemStack stack : products) {
            ControlItemDisplay d = new ControlItemDisplay(this, (this.w() - (float)w) / 2.0F + (float)(18 * i), (float)y);
            d.setTooltip();
            d.setItemStack(stack);
         }

         y = y + 26;
      }

      Collection<ItemStack> allProducts = new UniqueItemStackSet();

      for(ItemStack stack : products) {
         allProducts.add(stack);
      }

      Collection<ItemStack> refinedProducts = new UniqueItemStackSet();
      refinedProducts.addAll(this.getAllProductsAndFluids(allProducts));
      if(refinedProducts.size() > 0) {
         y = this.getRefined("Refined Products", y, refinedProducts);
         y = y + 8;
      }

      if(products.size() == 0) {
         (new ControlTextCentered(this, (float)y, "This tree has no \nfruits or nuts")).setColour(this.getColour());
         y += 28;
      }

      this.setSize(new IPoint(this.w(), (float)(y + 8)));
   }

   public String getTitle() {
      return "Wood";
   }
}
