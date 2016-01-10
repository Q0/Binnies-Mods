package binnie.extratrees.carpentry;

import binnie.Binnie;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.block.PlankType;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.DesignBlock;
import binnie.extratrees.carpentry.DesignSystem;
import binnie.extratrees.carpentry.ModuleCarpentry;
import forestry.api.core.Tabs;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockCarpentry extends BlockDesign {
   public BlockCarpentry() {
      super(DesignSystem.Wood, Material.wood);
      this.setCreativeTab(Tabs.tabArboriculture);
      this.setBlockName("carpentry");
      this.setResistance(5.0F);
      this.setHardness(2.0F);
      this.setStepSound(soundTypeWood);
   }

   public ItemStack getCreativeStack(IDesign design) {
      return ModuleCarpentry.getItemStack(this, PlankType.ExtraTreePlanks.Apple, PlankType.VanillaPlanks.BIRCH, design);
   }

   public String getBlockName(DesignBlock design) {
      return Binnie.Language.localise(ExtraTrees.instance, "block.woodentile.name", new Object[]{design.getDesign().getName()});
   }
}
