package binnie.extratrees.block.decor;

import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.WoodManager;
import binnie.extratrees.block.decor.BlockFence;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.block.decor.FenceType;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class FenceRenderer implements ISimpleBlockRenderingHandler {
   public static int layer;

   public FenceRenderer() {
      super();
   }

   public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
      Tessellator tess = Tessellator.instance;

      for(int i = 0; i < 5; ++i) {
         float thickness = 0.125F;
         layer = 0;
         if(i == 0) {
            block.setBlockBounds(0.5F - thickness, 0.0F, 0.0F, 0.5F + thickness, 1.0F, thickness * 2.0F);
         }

         if(i == 1) {
            block.setBlockBounds(0.5F - thickness, 0.0F, 1.0F - thickness * 2.0F, 0.5F + thickness, 1.0F, 1.0F);
         }

         float s = 0.0625F;
         FenceType fenceType = block == ExtraTrees.blockMultiFence?WoodManager.getFenceType(metadata):new FenceType(0);
         boolean bottomBar = !fenceType.solid;
         float topBarMaxY = 1.0F - s;
         float topBarMinY = 1.0F - s * 3.0F;
         float bottomBarMaxY = 0.5F - s;
         float bottomBarMinY = 0.5F - s * 3.0F;
         if(fenceType.size == 2) {
            bottomBarMinY -= 4.0F * s;
            bottomBarMaxY -= 4.0F * s;
            topBarMinY -= 4.0F * s;
            topBarMaxY -= 4.0F * s;
         }

         if(fenceType.size == 1) {
            bottomBarMinY -= 4.0F * s;
            bottomBarMaxY -= 4.0F * s;
         }

         if(fenceType.solid) {
            topBarMinY = bottomBarMinY;
         }

         float minX = 0.5F - s;
         float maxX = 0.5F + s;
         float minZ = -s * 2.0F;
         float maxZ = 1.0F + s * 2.0F;
         if(i == 2) {
            block.setBlockBounds(minX, topBarMinY, minZ, maxX, topBarMaxY, maxZ);
            layer = 1;
         }

         if(i == 3) {
            if(!bottomBar) {
               continue;
            }

            block.setBlockBounds(minX, bottomBarMinY, minZ, maxX, bottomBarMaxY, maxZ);
            layer = 1;
         }

         if(i == 4) {
            if(fenceType.embossed) {
               minX = minX - s * 0.9F;
               maxX = maxX + s * 0.9F;
               minZ = minZ - s;
               maxZ = maxZ + s;
               float minY = 0.0F;
               float maxY = 1.0F;
               if(fenceType.size != 1 && !fenceType.solid) {
                  minY = bottomBarMinY + 2.0F * s;
                  maxY = topBarMaxY - 2.0F * s;
               } else if(fenceType.size == 1 && fenceType.solid) {
                  minY = bottomBarMinY + 2.0F * s;
                  maxY = topBarMaxY - 2.0F * s;
               } else {
                  minY = 0.5F - 2.0F * s;
                  maxY = 0.5F + 2.0F * s;
               }

               if(fenceType.solid && fenceType.size == 0) {
                  minY -= s;
                  maxY -= s;
               }

               if(fenceType.solid && fenceType.size == 2) {
                  minY += s;
                  maxY += s;
               }

               block.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
               layer = 0;
            } else {
               if(fenceType.size != 1 || fenceType.solid) {
                  continue;
               }

               block.setBlockBounds(minX, 0.5F - s, minZ, maxX, 0.5F + s, maxZ);
               layer = 1;
            }
         }

         renderer.setRenderBoundsFromBlock(block);
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

      block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      renderer.setRenderBoundsFromBlock(block);
   }

   public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block block, int modelId, RenderBlocks renderer) {
      layer = 0;
      BlockFence blockFence = (BlockFence)block;
      float i = 0.0625F;
      FenceType fenceType = new FenceType(0);
      TileEntity tile = world.getTileEntity(par2, par3, par4);
      if(tile != null && tile instanceof TileEntityMetadata && block instanceof BlockMultiFence) {
         fenceType = WoodManager.getFenceType(((TileEntityMetadata)tile).getTileMetadata());
      }

      boolean rendered = false;
      float postWidth = 0.25F;
      float postHeight = 1.0F;
      float minPostPos = 0.5F - postWidth / 2.0F;
      float maxPostPos = 0.5F + postWidth / 2.0F;
      renderer.setRenderBounds((double)minPostPos, 0.0D, (double)minPostPos, (double)maxPostPos, (double)postHeight, (double)maxPostPos);
      renderer.renderStandardBlock(block, par2, par3, par4);
      rendered = true;
      boolean connectAnyX = false;
      boolean connectAnyZ = false;
      if(blockFence.canConnectFenceTo(renderer.blockAccess, par2 - 1, par3, par4) || blockFence.canConnectFenceTo(renderer.blockAccess, par2 + 1, par3, par4)) {
         connectAnyX = true;
      }

      if(blockFence.canConnectFenceTo(renderer.blockAccess, par2, par3, par4 - 1) || blockFence.canConnectFenceTo(renderer.blockAccess, par2, par3, par4 + 1)) {
         connectAnyZ = true;
      }

      boolean connectNegX = blockFence.canConnectFenceTo(renderer.blockAccess, par2 - 1, par3, par4);
      boolean connectPosX = blockFence.canConnectFenceTo(renderer.blockAccess, par2 + 1, par3, par4);
      boolean connectNegZ = blockFence.canConnectFenceTo(renderer.blockAccess, par2, par3, par4 - 1);
      boolean connectPosZ = blockFence.canConnectFenceTo(renderer.blockAccess, par2, par3, par4 + 1);
      if(!connectAnyX && !connectAnyZ) {
         connectAnyX = true;
      }

      minPostPos = 7.0F * i;
      maxPostPos = 9.0F * i;
      float barMinY = 12.0F * i;
      float barMaxY = 15.0F * i;
      float minX = connectNegX?0.0F:minPostPos;
      float maxX = connectPosX?1.0F:maxPostPos;
      float minZ = connectNegZ?0.0F:minPostPos;
      float maxZ = connectPosZ?1.0F:maxPostPos;
      boolean renderBottom = true;
      if(fenceType.size == 2) {
         barMaxY -= 5.0F * i;
         barMinY -= 5.0F * i;
      }

      if(fenceType.solid) {
         renderBottom = false;
         if(fenceType.size == 0) {
            barMinY = 6.0F * i;
         } else {
            barMinY = i;
         }
      }

      layer = 1;
      if(connectAnyX) {
         renderer.setRenderBounds((double)minX, (double)barMinY, (double)minPostPos, (double)maxX, (double)barMaxY, (double)maxPostPos);
         renderer.renderStandardBlock(blockFence, par2, par3, par4);
         rendered = true;
      }

      if(connectAnyZ) {
         renderer.setRenderBounds((double)minPostPos, (double)barMinY, (double)minZ, (double)maxPostPos, (double)barMaxY, (double)maxZ);
         renderer.renderStandardBlock(blockFence, par2, par3, par4);
         rendered = true;
      }

      if(renderBottom) {
         barMinY -= 6.0F * i;
         barMaxY -= 6.0F * i;
         if(fenceType.size == 1) {
            barMinY += i;
         }

         if(connectAnyX) {
            renderer.setRenderBounds((double)minX, (double)barMinY, (double)minPostPos, (double)maxX, (double)barMaxY, (double)maxPostPos);
            renderer.renderStandardBlock(blockFence, par2, par3, par4);
            rendered = true;
         }

         if(connectAnyZ) {
            renderer.setRenderBounds((double)minPostPos, (double)barMinY, (double)minZ, (double)maxPostPos, (double)barMaxY, (double)maxZ);
            renderer.renderStandardBlock(blockFence, par2, par3, par4);
            rendered = true;
         }
      }

      if(renderBottom && fenceType.size == 1) {
         barMinY -= 6.0F * i;
         barMaxY = barMaxY - 6.0F * i;
         barMaxY = barMaxY + i;
         if(connectAnyX) {
            renderer.setRenderBounds((double)minX, (double)barMinY, (double)minPostPos, (double)maxX, (double)barMaxY, (double)maxPostPos);
            renderer.renderStandardBlock(blockFence, par2, par3, par4);
            rendered = true;
         }

         if(connectAnyZ) {
            renderer.setRenderBounds((double)minPostPos, (double)barMinY, (double)minZ, (double)maxPostPos, (double)barMaxY, (double)maxZ);
            renderer.renderStandardBlock(blockFence, par2, par3, par4);
            rendered = true;
         }
      }

      layer = 0;
      if(fenceType.embossed) {
         minPostPos = (float)((double)minPostPos - ((double)i - 0.25D * (double)i));
         maxPostPos = (float)((double)maxPostPos + ((double)i - 0.25D * (double)i));
         float minY = barMinY + 2.0F * i;
         float maxY = barMaxY - 2.0F * i;
         if(fenceType.size == 1 && !fenceType.solid) {
            minY = 6.0F * i;
            maxY = 10.0F * i;
         } else if(fenceType.size == 0 && fenceType.solid) {
            minY -= 4.0F * i;
            maxY -= 4.0F * i;
         } else if(fenceType.size == 2 && fenceType.solid) {
            minY += 4.0F * i;
            maxY += 4.0F * i;
         }

         if(connectAnyX) {
            renderer.setRenderBounds((double)minX, (double)minY, (double)minPostPos, (double)maxX, (double)maxY, (double)maxPostPos);
            renderer.renderStandardBlock(block, par2, par3, par4);
         }

         if(connectAnyZ) {
            renderer.setRenderBounds((double)minPostPos, (double)minY, (double)minZ, (double)maxPostPos, (double)maxY, (double)maxZ);
            renderer.renderStandardBlock(block, par2, par3, par4);
         }
      }

      blockFence.setBlockBoundsBasedOnState(renderer.blockAccess, par2, par3, par4);
      return rendered;
   }

   public boolean shouldRender3DInInventory(int i) {
      return true;
   }

   public int getRenderId() {
      return ExtraTrees.fenceID;
   }
}
