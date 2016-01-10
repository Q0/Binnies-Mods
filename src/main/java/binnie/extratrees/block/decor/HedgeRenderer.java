package binnie.extratrees.block.decor;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.ModuleBlocks;
import binnie.extratrees.block.decor.BlockHedge;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class HedgeRenderer implements ISimpleBlockRenderingHandler {
   public HedgeRenderer() {
      super();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
      int color = BlockHedge.getColor(metadata);
      int r = color >> 16 & 255;
      int g = color >> 8 & 255;
      int b = color & 255;
      GL11.glColor3f((float)r / 255.0F, (float)g / 255.0F, (float)b / 255.0F);
      renderer.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
      Tessellator tess = Tessellator.instance;
      GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
      tess.startDrawingQuads();
      tess.setNormal(0.0F, -1.0F, 0.0F);
      renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, metadata));
      tess.draw();
      tess.startDrawingQuads();
      tess.setNormal(0.0F, 1.0F, 0.0F);
      renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, metadata));
      tess.draw();
      tess.startDrawingQuads();
      tess.setNormal(0.0F, 0.0F, -1.0F);
      renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, metadata));
      tess.draw();
      tess.startDrawingQuads();
      tess.setNormal(0.0F, 0.0F, 1.0F);
      renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, metadata));
      tess.draw();
      tess.startDrawingQuads();
      tess.setNormal(-1.0F, 0.0F, 0.0F);
      renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, metadata));
      tess.draw();
      tess.startDrawingQuads();
      tess.setNormal(1.0F, 0.0F, 0.0F);
      renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, metadata));
      tess.draw();
      GL11.glTranslatef(0.5F, 0.5F, 0.5F);
   }

   public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
      IIcon icon = block.getIcon(0, world.getBlockMetadata(x, y, z));
      BlockHedge hedge = ExtraTrees.blockHedge;
      boolean connectNegX = hedge.canConnectFenceTo(world, x - 1, y, z);
      boolean connectPosX = hedge.canConnectFenceTo(world, x + 1, y, z);
      boolean connectNegZ = hedge.canConnectFenceTo(world, x, y, z - 1);
      boolean connectPosZ = hedge.canConnectFenceTo(world, x, y, z + 1);
      GL11.glPushMatrix();
      renderer.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
      renderer.renderStandardBlock(block, x, y, z);
      renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
      renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
      if(!connectNegX) {
         renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, icon);
      }

      if(!connectPosX) {
         renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, icon);
      }

      if(!connectNegZ) {
         renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, icon);
      }

      if(!connectPosZ) {
         renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, icon);
      }

      if(connectNegX) {
         renderer.setRenderBounds(0.0D, 0.0D, 0.25D, 0.25D, 1.0D, 0.75D);
         renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, icon);
      }

      if(connectPosX) {
         renderer.setRenderBounds(0.75D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D);
         renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, icon);
      }

      if(connectPosZ) {
         renderer.setRenderBounds(0.25D, 0.0D, 0.75D, 0.75D, 1.0D, 1.0D);
         renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceZPos(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, icon);
      }

      if(connectNegZ) {
         renderer.setRenderBounds(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.25D);
         renderer.renderFaceYPos(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceYNeg(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceZNeg(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceXNeg(block, (double)x, (double)y, (double)z, icon);
         renderer.renderFaceXPos(block, (double)x, (double)y, (double)z, icon);
      }

      GL11.glPopMatrix();
      return false;
   }

   public boolean shouldRender3DInInventory(int modelId) {
      return true;
   }

   public int getRenderId() {
      return ModuleBlocks.hedgeRenderID;
   }
}
