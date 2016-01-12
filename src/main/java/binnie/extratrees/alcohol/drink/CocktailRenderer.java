package binnie.extratrees.alcohol.drink;

import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.alcohol.Glassware;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class CocktailRenderer implements IItemRenderer {
    private void renderCocktail(final RenderBlocks renderBlocks, final ItemStack item, final float p, final float q, final float r) {
        final RenderItem renderItem = new RenderItem();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final Glassware glass = ExtraTrees.drink.getGlassware(item);
        final FluidStack fluid = ExtraTrees.drink.getFluid(item);
        final IDrinkLiquid drink = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
        this.setColor(16777215, 0.8f);
        renderItem.renderIcon(0, 0, glass.glass, 16, 16);
        if (drink != null) {
            this.setColor(drink.getColour(), 1.2f * drink.getTransparency() + 0.3f);
            final IIcon icon = glass.contents;
            final float amount = fluid.amount / glass.getVolume();
            final float level = glass.getContentHeight() * (1.0f - amount);
            final float gapAtTop = 1.0f - (glass.getContentBottom() + glass.getContentHeight());
            final float x = 0.0f;
            final float y = 16.0f * (gapAtTop + level);
            final float w = 16.0f;
            final float h = 16.0f - y;
            final float minV = icon.getInterpolatedV((double) y);
            final float maxV = icon.getInterpolatedV(16.0);
            final Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV((double) (x + 0.0f), (double) (y + h), (double) renderItem.zLevel, (double) icon.getMinU(), (double) maxV);
            tessellator.addVertexWithUV((double) (x + w), (double) (y + h), (double) renderItem.zLevel, (double) icon.getMaxU(), (double) maxV);
            tessellator.addVertexWithUV((double) (x + w), (double) (y + 0.0f), (double) renderItem.zLevel, (double) icon.getMaxU(), (double) minV);
            tessellator.addVertexWithUV((double) (x + 0.0f), (double) (y + 0.0f), (double) renderItem.zLevel, (double) icon.getMinU(), (double) minV);
            tessellator.draw();
        }
        GL11.glDisable(3042);
    }

    private void setColor(final int i1, final float alpha) {
        final float f = (i1 >> 16 & 0xFF) / 255.0f;
        final float f2 = (i1 >> 8 & 0xFF) / 255.0f;
        final float f3 = (i1 & 0xFF) / 255.0f;
        GL11.glColor4f(f, f2, f3, (alpha > 1.0f) ? 1.0f : alpha);
    }

    private void renderFace(final Tessellator tessellator, final ForgeDirection dir) {
        switch (dir) {
            case DOWN: {
                tessellator.addVertex(0.0, 0.0, 0.0);
                tessellator.addVertex(0.0, 0.0, 1.0);
                tessellator.addVertex(1.0, 0.0, 1.0);
                tessellator.addVertex(1.0, 0.0, 0.0);
                break;
            }
        }
    }

    public boolean handleRenderType(final ItemStack item, final IItemRenderer.ItemRenderType type) {
        switch (type) {
            case ENTITY: {
                return true;
            }
            case EQUIPPED: {
                return true;
            }
            case INVENTORY: {
                return true;
            }
            case EQUIPPED_FIRST_PERSON: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public boolean shouldUseRenderHelper(final IItemRenderer.ItemRenderType type, final ItemStack item, final IItemRenderer.ItemRendererHelper helper) {
        return helper == IItemRenderer.ItemRendererHelper.ENTITY_ROTATION || helper == IItemRenderer.ItemRendererHelper.ENTITY_BOBBING;
    }

    public void renderItem(final IItemRenderer.ItemRenderType type, final ItemStack item, final Object... data) {
        switch (type) {
            case ENTITY:
            case EQUIPPED:
            case EQUIPPED_FIRST_PERSON: {
                if (type == IItemRenderer.ItemRenderType.ENTITY) {
                    GL11.glTranslatef(-0.5f, -0.25f, -0.0f);
                }
                final TextureManager texturemanager = BinnieCore.proxy.getMinecraftInstance().getTextureManager();
                final Glassware glass = ExtraTrees.drink.getGlassware(item);
                final FluidStack fluid = ExtraTrees.drink.getFluid(item);
                IIcon iicon = glass.glass;
                final float f = iicon.getMinU();
                final float f2 = iicon.getMaxU();
                final float f3 = iicon.getMinV();
                final float f4 = iicon.getMaxV();
                texturemanager.bindTexture(texturemanager.getResourceLocation(item.getItemSpriteNumber()));
                TextureUtil.func_152777_a(false, false, 1.0f);
                this.setColor(16777215, 0.8f);
                final IDrinkLiquid drink = (fluid == null) ? null : DrinkManager.getLiquid(fluid.getFluid());
                this.setColor(16777215, 0.8f);
                ItemRenderer.renderItemIn2D(Tessellator.instance, f2, f3, f, f4, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625f);
                if (drink != null) {
                    this.setColor(drink.getColour(), 1.2f * drink.getTransparency() + 0.3f);
                    iicon = glass.contents;
                    final IIcon icon = glass.contents;
                    final float amount = fluid.amount / glass.getVolume();
                    final float level = glass.getContentHeight() * (1.0f - amount);
                    final float gapAtTop = 1.0f - (glass.getContentBottom() + glass.getContentHeight());
                    final float x = 0.0f;
                    final float y = 1.0f * (gapAtTop + level);
                    final float w = 1.0f;
                    final float h = 1.0f - y;
                    final float minV = icon.getInterpolatedV((double) (y * 16.0f));
                    final float maxV = icon.getInterpolatedV(16.0);
                    final float minU = icon.getMinU();
                    final float maxU = icon.getMaxU();
                    final Tessellator tessellator = Tessellator.instance;
                    tessellator.setNormal(0.0f, 0.0f, -1.0f);
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(0.0, 1.0 - y, -0.03125, (double) maxU, (double) minV);
                    tessellator.addVertexWithUV(1.0, 1.0 - y, -0.03125, (double) minU, (double) minV);
                    tessellator.addVertexWithUV(1.0, 0.0, -0.03125, (double) minU, (double) maxV);
                    tessellator.addVertexWithUV(0.0, 0.0, -0.03125, (double) maxU, (double) maxV);
                    tessellator.draw();
                    tessellator.setNormal(0.0f, 0.0f, 1.0f);
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(0.0, 1.0 - y, -0.03125, (double) maxU, (double) minV);
                    tessellator.addVertexWithUV(0.0, 0.0, -0.03125, (double) maxU, (double) maxV);
                    tessellator.addVertexWithUV(1.0, 0.0, -0.03125, (double) minU, (double) maxV);
                    tessellator.addVertexWithUV(1.0, 1.0 - y, -0.03125, (double) minU, (double) minV);
                    tessellator.draw();
                    break;
                }
                break;
            }
            case INVENTORY: {
                this.renderCocktail((RenderBlocks) data[0], item, -0.5f, -0.5f, -0.5f);
                break;
            }
        }
    }

    public void renderItemIn2DPercentage(final Tessellator tesselator, final float maxU, final float minV, final float minU, float maxV, final int width, final int height, final float depth, final float percent) {
        maxV = minV + (maxV - minV) * 1.0f;
        tesselator.startDrawingQuads();
        tesselator.setNormal(0.0f, 0.0f, 1.0f);
        tesselator.addVertexWithUV(0.0, 0.0, 0.0, (double) maxU, (double) maxV);
        tesselator.addVertexWithUV(1.0, 0.0, 0.0, (double) minU, (double) maxV);
        tesselator.addVertexWithUV(1.0, 1.0, 0.0, (double) minU, (double) minV);
        tesselator.addVertexWithUV(0.0, 1.0, 0.0, (double) maxU, (double) minV);
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.setNormal(0.0f, 0.0f, -1.0f);
        tesselator.addVertexWithUV(0.0, (double) percent, (double) (0.0f - depth), (double) maxU, (double) minV);
        tesselator.addVertexWithUV(1.0, (double) percent, (double) (0.0f - depth), (double) minU, (double) minV);
        tesselator.addVertexWithUV(1.0, 0.0, (double) (0.0f - depth), (double) minU, (double) maxV);
        tesselator.addVertexWithUV(0.0, 0.0, (double) (0.0f - depth), (double) maxU, (double) maxV);
        tesselator.draw();
        final float f5 = 0.5f * (maxU - minU) / width;
        final float f6 = 0.5f * (maxV - minV) / height;
        tesselator.startDrawingQuads();
        tesselator.setNormal(-1.0f, 0.0f, 0.0f);
        for (int k = 0; k < width; ++k) {
            final float f7 = k / width;
            final float f8 = maxU + (minU - maxU) * f7 - f5;
            tesselator.addVertexWithUV((double) f7, 0.0, (double) (0.0f - depth), (double) f8, (double) maxV);
            tesselator.addVertexWithUV((double) f7, 0.0, 0.0, (double) f8, (double) maxV);
            tesselator.addVertexWithUV((double) f7, 1.0, 0.0, (double) f8, (double) minV);
            tesselator.addVertexWithUV((double) f7, 1.0, (double) (0.0f - depth), (double) f8, (double) minV);
        }
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.setNormal(1.0f, 0.0f, 0.0f);
        for (int k = 0; k < width; ++k) {
            final float f7 = k / width;
            final float f8 = maxU + (minU - maxU) * f7 - f5;
            final float f9 = f7 + 1.0f / width;
            tesselator.addVertexWithUV((double) f9, 1.0, (double) (0.0f - depth), (double) f8, (double) minV);
            tesselator.addVertexWithUV((double) f9, 1.0, 0.0, (double) f8, (double) minV);
            tesselator.addVertexWithUV((double) f9, 0.0, 0.0, (double) f8, (double) maxV);
            tesselator.addVertexWithUV((double) f9, 0.0, (double) (0.0f - depth), (double) f8, (double) maxV);
        }
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.setNormal(0.0f, 1.0f, 0.0f);
        for (int k = 0; k < height; ++k) {
            final float f7 = k / height;
            final float f8 = maxV + (minV - maxV) * f7 - f6;
            final float f9 = f7 + 1.0f / height;
            tesselator.addVertexWithUV(0.0, (double) f9, 0.0, (double) maxU, (double) f8);
            tesselator.addVertexWithUV(1.0, (double) f9, 0.0, (double) minU, (double) f8);
            tesselator.addVertexWithUV(1.0, (double) f9, (double) (0.0f - depth), (double) minU, (double) f8);
            tesselator.addVertexWithUV(0.0, (double) f9, (double) (0.0f - depth), (double) maxU, (double) f8);
        }
        tesselator.draw();
        tesselator.startDrawingQuads();
        tesselator.setNormal(0.0f, -1.0f, 0.0f);
        for (int k = 0; k < height; ++k) {
            final float f7 = k / height;
            final float f8 = maxV + (minV - maxV) * f7 - f6;
            tesselator.addVertexWithUV(1.0, (double) f7, 0.0, (double) minU, (double) f8);
            tesselator.addVertexWithUV(0.0, (double) f7, 0.0, (double) maxU, (double) f8);
            tesselator.addVertexWithUV(0.0, (double) f7, (double) (0.0f - depth), (double) maxU, (double) f8);
            tesselator.addVertexWithUV(1.0, (double) f7, (double) (0.0f - depth), (double) minU, (double) f8);
        }
        tesselator.draw();
    }
}
