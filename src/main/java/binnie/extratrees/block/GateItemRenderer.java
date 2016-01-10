package binnie.extratrees.block;

import binnie.extratrees.block.WoodManager;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import org.lwjgl.opengl.GL11;

public class GateItemRenderer implements IItemRenderer {
   public GateItemRenderer() {
      super();
   }

   private void renderStairBlock(RenderBlocks renderBlocks, ItemStack item, float f, float g, float h) {
      Tessellator tessellator = Tessellator.instance;
      Block block = ((ItemBlock)item.getItem()).field_150939_a;
      IIcon textureIndex = WoodManager.getPlankType(item.getItemDamage()).getIcon();

      for(int k = 0; k < 3; ++k) {
         float f2 = 0.0625F;
         if(k == 0) {
            renderBlocks.setRenderBounds((double)(0.5F - f2), 0.30000001192092896D, 0.0D, (double)(0.5F + f2), 1.0D, (double)(f2 * 2.0F));
         }

         if(k == 1) {
            renderBlocks.setRenderBounds((double)(0.5F - f2), 0.30000001192092896D, (double)(1.0F - f2 * 2.0F), (double)(0.5F + f2), 1.0D, 1.0D);
         }

         f2 = 0.0625F;
         if(k == 2) {
            renderBlocks.setRenderBounds((double)(0.5F - f2), 0.5D, 0.0D, (double)(0.5F + f2), (double)(1.0F - f2), 1.0D);
         }

         GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
         tessellator.startDrawingQuads();
         tessellator.setNormal(0.0F, -1.0F, 0.0F);
         renderBlocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 0, item.getItemDamage()));
         tessellator.draw();
         tessellator.startDrawingQuads();
         tessellator.setNormal(0.0F, 1.0F, 0.0F);
         renderBlocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 1, item.getItemDamage()));
         tessellator.draw();
         tessellator.startDrawingQuads();
         tessellator.setNormal(0.0F, 0.0F, -1.0F);
         renderBlocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 2, item.getItemDamage()));
         tessellator.draw();
         tessellator.startDrawingQuads();
         tessellator.setNormal(0.0F, 0.0F, 1.0F);
         renderBlocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 3, item.getItemDamage()));
         tessellator.draw();
         tessellator.startDrawingQuads();
         tessellator.setNormal(-1.0F, 0.0F, 0.0F);
         renderBlocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 4, item.getItemDamage()));
         tessellator.draw();
         tessellator.startDrawingQuads();
         tessellator.setNormal(1.0F, 0.0F, 0.0F);
         renderBlocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocks.getBlockIconFromSideAndMetadata(block, 5, item.getItemDamage()));
         tessellator.draw();
         GL11.glTranslatef(0.5F, 0.5F, 0.5F);
      }

   }

   public boolean handleRenderType(ItemStack item, ItemRenderType type) {
      switch(type) {
      case ENTITY:
         return true;
      case EQUIPPED:
         return true;
      case INVENTORY:
         return true;
      default:
         return false;
      }
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return true;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
      switch(type) {
      case ENTITY:
         this.renderStairBlock((RenderBlocks)data[0], item, -0.5F, -0.5F, -0.5F);
         break;
      case EQUIPPED:
         this.renderStairBlock((RenderBlocks)data[0], item, 0.0F, 0.0F, 0.0F);
         break;
      case INVENTORY:
         this.renderStairBlock((RenderBlocks)data[0], item, -0.5F, -0.5F, -0.5F);
      }

   }
}
