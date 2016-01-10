package binnie.extratrees.alcohol.drink;

import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.Glassware;
import binnie.extratrees.alcohol.drink.DrinkManager;
import binnie.extratrees.alcohol.drink.IDrinkLiquid;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class CocktailRenderer implements IItemRenderer {
   public CocktailRenderer() {
      super();
   }

   private void renderCocktail(RenderBlocks renderBlocks, ItemStack item, float p, float q, float r) {
      RenderItem renderItem = new RenderItem();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      Glassware glass = ExtraTrees.drink.getGlassware(item);
      FluidStack fluid = ExtraTrees.drink.getFluid(item);
      IDrinkLiquid drink = fluid == null?null:DrinkManager.getLiquid(fluid.getFluid());
      this.setColor(16777215, 0.8F);
      renderItem.renderIcon(0, 0, glass.glass, 16, 16);
      if(drink != null) {
         this.setColor(drink.getColour(), 1.2F * drink.getTransparency() + 0.3F);
         IIcon icon = glass.contents;
         float amount = (float)fluid.amount / (float)glass.getVolume();
         float level = glass.getContentHeight() * (1.0F - amount);
         float gapAtTop = 1.0F - (glass.getContentBottom() + glass.getContentHeight());
         float x = 0.0F;
         float y = 16.0F * (gapAtTop + level);
         float w = 16.0F;
         float h = 16.0F - y;
         float minV = icon.getInterpolatedV((double)y);
         float maxV = icon.getInterpolatedV(16.0D);
         Tessellator tessellator = Tessellator.instance;
         tessellator.startDrawingQuads();
         tessellator.addVertexWithUV((double)(x + 0.0F), (double)(y + h), (double)renderItem.zLevel, (double)icon.getMinU(), (double)maxV);
         tessellator.addVertexWithUV((double)(x + w), (double)(y + h), (double)renderItem.zLevel, (double)icon.getMaxU(), (double)maxV);
         tessellator.addVertexWithUV((double)(x + w), (double)(y + 0.0F), (double)renderItem.zLevel, (double)icon.getMaxU(), (double)minV);
         tessellator.addVertexWithUV((double)(x + 0.0F), (double)(y + 0.0F), (double)renderItem.zLevel, (double)icon.getMinU(), (double)minV);
         tessellator.draw();
      }

      GL11.glDisable(3042);
   }

   private void setColor(int i1, float alpha) {
      float f = (float)(i1 >> 16 & 255) / 255.0F;
      float f1 = (float)(i1 >> 8 & 255) / 255.0F;
      float f2 = (float)(i1 & 255) / 255.0F;
      GL11.glColor4f(f, f1, f2, alpha > 1.0F?1.0F:alpha);
   }

   private void renderFace(Tessellator tessellator, ForgeDirection dir) {
      switch(dir) {
      case DOWN:
         tessellator.addVertex(0.0D, 0.0D, 0.0D);
         tessellator.addVertex(0.0D, 0.0D, 1.0D);
         tessellator.addVertex(1.0D, 0.0D, 1.0D);
         tessellator.addVertex(1.0D, 0.0D, 0.0D);
      default:
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
      case EQUIPPED_FIRST_PERSON:
         return true;
      default:
         return false;
      }
   }

   public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
      return helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.ENTITY_BOBBING;
   }

   public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
      switch(type) {
      case ENTITY:
      case EQUIPPED:
      case EQUIPPED_FIRST_PERSON:
         if(type == ItemRenderType.ENTITY) {
            GL11.glTranslatef(-0.5F, -0.25F, -0.0F);
         }

         TextureManager texturemanager = BinnieCore.proxy.getMinecraftInstance().getTextureManager();
         Glassware glass = ExtraTrees.drink.getGlassware(item);
         FluidStack fluid = ExtraTrees.drink.getFluid(item);
         IIcon iicon = glass.glass;
         float f = iicon.getMinU();
         float f1 = iicon.getMaxU();
         float f2 = iicon.getMinV();
         float f3 = iicon.getMaxV();
         texturemanager.bindTexture(texturemanager.getResourceLocation(item.getItemSpriteNumber()));
         TextureUtil.func_152777_a(false, false, 1.0F);
         this.setColor(16777215, 0.8F);
         IDrinkLiquid drink = fluid == null?null:DrinkManager.getLiquid(fluid.getFluid());
         this.setColor(16777215, 0.8F);
         ItemRenderer.renderItemIn2D(Tessellator.instance, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
         if(drink != null) {
            this.setColor(drink.getColour(), 1.2F * drink.getTransparency() + 0.3F);
            iicon = glass.contents;
            IIcon icon = glass.contents;
            float amount = (float)fluid.amount / (float)glass.getVolume();
            float level = glass.getContentHeight() * (1.0F - amount);
            float gapAtTop = 1.0F - (glass.getContentBottom() + glass.getContentHeight());
            float x = 0.0F;
            float y = 1.0F * (gapAtTop + level);
            float w = 1.0F;
            float h = 1.0F - y;
            float minV = icon.getInterpolatedV((double)(y * 16.0F));
            float maxV = icon.getInterpolatedV(16.0D);
            float minU = icon.getMinU();
            float maxU = icon.getMaxU();
            Tessellator tessellator = Tessellator.instance;
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0.0D, 1.0D - (double)y, -0.03125D, (double)maxU, (double)minV);
            tessellator.addVertexWithUV(1.0D, 1.0D - (double)y, -0.03125D, (double)minU, (double)minV);
            tessellator.addVertexWithUV(1.0D, 0.0D, -0.03125D, (double)minU, (double)maxV);
            tessellator.addVertexWithUV(0.0D, 0.0D, -0.03125D, (double)maxU, (double)maxV);
            tessellator.draw();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(0.0D, 1.0D - (double)y, -0.03125D, (double)maxU, (double)minV);
            tessellator.addVertexWithUV(0.0D, 0.0D, -0.03125D, (double)maxU, (double)maxV);
            tessellator.addVertexWithUV(1.0D, 0.0D, -0.03125D, (double)minU, (double)maxV);
            tessellator.addVertexWithUV(1.0D, 1.0D - (double)y, -0.03125D, (double)minU, (double)minV);
            tessellator.draw();
         }
         break;
      case INVENTORY:
         this.renderCocktail((RenderBlocks)data[0], item, -0.5F, -0.5F, -0.5F);
      }

   }

   public void renderItemIn2DPercentage(Tessellator tesselator, float maxU, float minV, float minU, float maxV, int width, int height, float depth, float percent) {
      maxV = minV + (maxV - minV) * 1.0F;
      tesselator.startDrawingQuads();
      tesselator.setNormal(0.0F, 0.0F, 1.0F);
      tesselator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)maxU, (double)maxV);
      tesselator.addVertexWithUV(1.0D, 0.0D, 0.0D, (double)minU, (double)maxV);
      tesselator.addVertexWithUV(1.0D, 1.0D, 0.0D, (double)minU, (double)minV);
      tesselator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)maxU, (double)minV);
      tesselator.draw();
      tesselator.startDrawingQuads();
      tesselator.setNormal(0.0F, 0.0F, -1.0F);
      tesselator.addVertexWithUV(0.0D, (double)percent, (double)(0.0F - depth), (double)maxU, (double)minV);
      tesselator.addVertexWithUV(1.0D, (double)percent, (double)(0.0F - depth), (double)minU, (double)minV);
      tesselator.addVertexWithUV(1.0D, 0.0D, (double)(0.0F - depth), (double)minU, (double)maxV);
      tesselator.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - depth), (double)maxU, (double)maxV);
      tesselator.draw();
      float f5 = 0.5F * (maxU - minU) / (float)width;
      float f6 = 0.5F * (maxV - minV) / (float)height;
      tesselator.startDrawingQuads();
      tesselator.setNormal(-1.0F, 0.0F, 0.0F);

      for(int k = 0; k < width; ++k) {
         float f7 = (float)k / (float)width;
         float f8 = maxU + (minU - maxU) * f7 - f5;
         tesselator.addVertexWithUV((double)f7, 0.0D, (double)(0.0F - depth), (double)f8, (double)maxV);
         tesselator.addVertexWithUV((double)f7, 0.0D, 0.0D, (double)f8, (double)maxV);
         tesselator.addVertexWithUV((double)f7, 1.0D, 0.0D, (double)f8, (double)minV);
         tesselator.addVertexWithUV((double)f7, 1.0D, (double)(0.0F - depth), (double)f8, (double)minV);
      }

      tesselator.draw();
      tesselator.startDrawingQuads();
      tesselator.setNormal(1.0F, 0.0F, 0.0F);

      for(int var17 = 0; var17 < width; ++var17) {
         float f7 = (float)var17 / (float)width;
         float f8 = maxU + (minU - maxU) * f7 - f5;
         float f9 = f7 + 1.0F / (float)width;
         tesselator.addVertexWithUV((double)f9, 1.0D, (double)(0.0F - depth), (double)f8, (double)minV);
         tesselator.addVertexWithUV((double)f9, 1.0D, 0.0D, (double)f8, (double)minV);
         tesselator.addVertexWithUV((double)f9, 0.0D, 0.0D, (double)f8, (double)maxV);
         tesselator.addVertexWithUV((double)f9, 0.0D, (double)(0.0F - depth), (double)f8, (double)maxV);
      }

      tesselator.draw();
      tesselator.startDrawingQuads();
      tesselator.setNormal(0.0F, 1.0F, 0.0F);

      for(int var18 = 0; var18 < height; ++var18) {
         float f7 = (float)var18 / (float)height;
         float f8 = maxV + (minV - maxV) * f7 - f6;
         float f9 = f7 + 1.0F / (float)height;
         tesselator.addVertexWithUV(0.0D, (double)f9, 0.0D, (double)maxU, (double)f8);
         tesselator.addVertexWithUV(1.0D, (double)f9, 0.0D, (double)minU, (double)f8);
         tesselator.addVertexWithUV(1.0D, (double)f9, (double)(0.0F - depth), (double)minU, (double)f8);
         tesselator.addVertexWithUV(0.0D, (double)f9, (double)(0.0F - depth), (double)maxU, (double)f8);
      }

      tesselator.draw();
      tesselator.startDrawingQuads();
      tesselator.setNormal(0.0F, -1.0F, 0.0F);

      for(int var19 = 0; var19 < height; ++var19) {
         float f7 = (float)var19 / (float)height;
         float f8 = maxV + (minV - maxV) * f7 - f6;
         tesselator.addVertexWithUV(1.0D, (double)f7, 0.0D, (double)minU, (double)f8);
         tesselator.addVertexWithUV(0.0D, (double)f7, 0.0D, (double)maxU, (double)f8);
         tesselator.addVertexWithUV(0.0D, (double)f7, (double)(0.0F - depth), (double)maxU, (double)f8);
         tesselator.addVertexWithUV(1.0D, (double)f7, (double)(0.0F - depth), (double)minU, (double)f8);
      }

      tesselator.draw();
   }
}
