package binnie.extratrees.block;

import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class BranchBlockRenderer implements ISimpleBlockRenderingHandler {
   public BranchBlockRenderer() {
      super();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
   }

   public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block par1Block, int modelId, RenderBlocks renderer) {
      renderer.renderStandardBlock(par1Block, par2, par3, par4);
      return true;
   }

   public boolean shouldRender3DInInventory(int i) {
      return false;
   }

   public int getRenderId() {
      return ExtraTrees.branchRenderId;
   }
}
