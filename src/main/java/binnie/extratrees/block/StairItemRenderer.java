package binnie.extratrees.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class StairItemRenderer implements IItemRenderer {
    public StairItemRenderer() {
        super();
    }

    private void renderStairBlock(RenderBlocks renderBlocks, ItemStack item, float f, float g, float h) {
        Tessellator tessellator = Tessellator.instance;
        Block block = ((ItemBlock) item.getItem()).field_150939_a;
        IIcon textureIndex = PlankType.ExtraTreePlanks.values()[item.getItemDamage()].getIcon();

        for (int i = 0; i < 2; ++i) {
            if (i == 0) {
                renderBlocks.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
            }

            if (i == 1) {
                renderBlocks.setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
            }

            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            renderBlocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, textureIndex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderBlocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, textureIndex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            renderBlocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, textureIndex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderBlocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, textureIndex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            renderBlocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, textureIndex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderBlocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, textureIndex);
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }

    }

    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        switch (type) {
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
        switch (type) {
            case ENTITY:
                this.renderStairBlock((RenderBlocks) data[0], item, -0.5F, -0.5F, -0.5F);
                break;
            case EQUIPPED:
                this.renderStairBlock((RenderBlocks) data[0], item, 0.0F, 0.0F, 0.0F);
                break;
            case INVENTORY:
                this.renderStairBlock((RenderBlocks) data[0], item, -0.5F, -0.5F, -0.5F);
        }

    }
}
