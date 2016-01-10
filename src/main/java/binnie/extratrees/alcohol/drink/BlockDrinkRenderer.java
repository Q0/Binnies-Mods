package binnie.extratrees.alcohol.drink;

import binnie.extratrees.alcohol.ModuleAlcohol;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class BlockDrinkRenderer implements ISimpleBlockRenderingHandler {
   public BlockDrinkRenderer() {
      super();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      GL11.glPushMatrix();
      Tessellator.instance.setColorRGBA(255, 255, 255, 150);
      Tessellator tess = Tessellator.instance;
      renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, Blocks.dirt.getIcon(0, 0));
      float s = 0.0625F;

      for(int h = 0; h < 16; ++h) {
         float d1 = 8.0F * s;
         float d2 = 8.0F * s;
         float h1 = (float)h * s;
         float h2 = (float)h * s;
         tess.addVertexWithUV((double)((float)x + 0.5F - d1), (double)((float)y + h1), (double)((float)z + 0.5F - d1), 0.0D, 0.0D);
         tess.addVertexWithUV((double)((float)x + 0.5F - d1), (double)((float)y + h2), (double)((float)z + 0.5F - d1), 0.0D, 0.0D);
         tess.addVertexWithUV((double)((float)x + 0.5F + d1), (double)((float)y + h2), (double)((float)z + 0.5F - d1), 0.0D, 0.0D);
         tess.addVertexWithUV((double)((float)x + 0.5F + d1), (double)((float)y + h1), (double)((float)z + 0.5F - d1), 0.0D, 0.0D);
      }

      GL11.glPopMatrix();
      return false;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return false;
   }

   public int getRenderId() {
      return ModuleAlcohol.drinkRendererID;
   }
}
