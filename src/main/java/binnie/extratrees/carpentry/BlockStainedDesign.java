package binnie.extratrees.carpentry;

import binnie.Binnie;
import binnie.botany.Botany;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IDesign;
import binnie.extratrees.carpentry.BlockDesign;
import binnie.extratrees.carpentry.DesignBlock;
import binnie.extratrees.carpentry.DesignSystem;
import binnie.extratrees.carpentry.GlassType;
import binnie.extratrees.carpentry.ModuleCarpentry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;

public class BlockStainedDesign extends BlockDesign {
   public BlockStainedDesign() {
      super(DesignSystem.Glass, Material.glass);
      this.setCreativeTab(Tabs.tabArboriculture);
      this.setBlockName("stainedGlass");
   }

   public int quantityDropped(Random p_149745_1_) {
      return 0;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean renderAsNormalBlock() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public int getRenderBlockPass() {
      return 1;
   }

   @SideOnly(Side.CLIENT)
   public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
      p_149646_1_.getBlock(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_]);
      Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
      return block != this && block != Botany.stained?super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_):false;
   }

   public ItemStack getCreativeStack(IDesign design) {
      return ModuleCarpentry.getItemStack(this, GlassType.get(0), GlassType.get(1), design);
   }

   public String getBlockName(DesignBlock design) {
      return Binnie.Language.localise(ExtraTrees.instance, "block.stainedglass.name", new Object[]{design.getDesign().getName()});
   }
}
