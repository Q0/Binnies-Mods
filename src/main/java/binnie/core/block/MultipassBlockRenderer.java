package binnie.core.block;

import binnie.core.BinnieCore;
import binnie.core.block.IMultipassBlock;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class MultipassBlockRenderer implements ISimpleBlockRenderingHandler {
   public static MultipassBlockRenderer instance;
   private static int layer = 0;

   public MultipassBlockRenderer() {
      super();
      instance = this;
   }

   private void setColour(Tessellator tess, int colour) {
      float var6 = (float)(colour >> 16 & 255) / 255.0F;
      float var7 = (float)(colour >> 8 & 255) / 255.0F;
      float var8 = (float)(colour & 255) / 255.0F;
      GL11.glColor3f(var6, var7, var8);
   }

   public static int getLayer() {
      return layer;
   }

   public void renderInventoryBlock(Block block, int meta, int modelID, RenderBlocks renderer) {
      block.setBlockBoundsForItemRender();
      renderer.setRenderBoundsFromBlock(block);
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

      for(layer = 0; layer < ((IMultipassBlock)block).getNumberOfPasses(); ++layer) {
         this.renderItem(block, renderer, meta);
      }

      layer = 0;
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      boolean r = true;

      for(layer = 0; layer < ((IMultipassBlock)block).getNumberOfPasses(); ++layer) {
         r = renderer.renderStandardBlock(block, x, y, z);
      }

      layer = 0;
      return r;
   }

   public boolean shouldRender3DInInventory(int i) {
      return true;
   }

   public int getRenderId() {
      return BinnieCore.multipassRenderID;
   }

   public void renderItem(Block block, RenderBlocks renderer, int meta) {
      this.setColor(((IMultipassBlock)block).colorMultiplier(meta));
      Tessellator tessellator = Tessellator.instance;
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, -1.0F, 0.0F);
      renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, meta));
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 1.0F, 0.0F);
      renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, meta));
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 0.0F, -1.0F);
      renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, meta));
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 0.0F, 1.0F);
      renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, meta));
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.setNormal(-1.0F, 0.0F, 0.0F);
      renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, meta));
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.setNormal(1.0F, 0.0F, 0.0F);
      renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, meta));
      tessellator.draw();
   }

   public void setColor(int l) {
      float f = (float)(l >> 16 & 255) / 255.0F;
      float f1 = (float)(l >> 8 & 255) / 255.0F;
      float f2 = (float)(l & 255) / 255.0F;
      GL11.glColor3f(f, f1, f2);
   }
}
