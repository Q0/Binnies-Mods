package binnie.extratrees.block.decor;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.ModuleBlocks;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class HedgeRenderer implements ISimpleBlockRenderingHandler {
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
        final int color = BlockHedge.getColor(metadata);
        final int r = color >> 16 & 0xFF;
        final int g = color >> 8 & 0xFF;
        final int b = color & 0xFF;
        GL11.glColor3f(r / 255.0f, g / 255.0f, b / 255.0f);
        renderer.setRenderBounds(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
        final Tessellator tess = Tessellator.instance;
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        tess.startDrawingQuads();
        tess.setNormal(0.0f, -1.0f, 0.0f);
        renderer.renderFaceYNeg(block, 0.0, 0.0, 0.0, block.getIcon(0, metadata));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(0.0f, 1.0f, 0.0f);
        renderer.renderFaceYPos(block, 0.0, 0.0, 0.0, block.getIcon(1, metadata));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(0.0f, 0.0f, -1.0f);
        renderer.renderFaceXPos(block, 0.0, 0.0, 0.0, block.getIcon(2, metadata));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(0.0f, 0.0f, 1.0f);
        renderer.renderFaceXNeg(block, 0.0, 0.0, 0.0, block.getIcon(3, metadata));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(-1.0f, 0.0f, 0.0f);
        renderer.renderFaceZNeg(block, 0.0, 0.0, 0.0, block.getIcon(4, metadata));
        tess.draw();
        tess.startDrawingQuads();
        tess.setNormal(1.0f, 0.0f, 0.0f);
        renderer.renderFaceZPos(block, 0.0, 0.0, 0.0, block.getIcon(5, metadata));
        tess.draw();
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
    }

    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
        final IIcon icon = block.getIcon(0, world.getBlockMetadata(x, y, z));
        final BlockHedge hedge = ExtraTrees.blockHedge;
        final boolean connectNegX = hedge.canConnectFenceTo(world, x - 1, y, z);
        final boolean connectPosX = hedge.canConnectFenceTo(world, x + 1, y, z);
        final boolean connectNegZ = hedge.canConnectFenceTo(world, x, y, z - 1);
        final boolean connectPosZ = hedge.canConnectFenceTo(world, x, y, z + 1);
        GL11.glPushMatrix();
        renderer.setRenderBounds(0.25, 0.0, 0.25, 0.75, 1.0, 0.75);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.renderFaceYPos(block, (double) x, (double) y, (double) z, icon);
        renderer.renderFaceYNeg(block, (double) x, (double) y, (double) z, icon);
        if (!connectNegX) {
            renderer.renderFaceXNeg(block, (double) x, (double) y, (double) z, icon);
        }
        if (!connectPosX) {
            renderer.renderFaceXPos(block, (double) x, (double) y, (double) z, icon);
        }
        if (!connectNegZ) {
            renderer.renderFaceZNeg(block, (double) x, (double) y, (double) z, icon);
        }
        if (!connectPosZ) {
            renderer.renderFaceZPos(block, (double) x, (double) y, (double) z, icon);
        }
        if (connectNegX) {
            renderer.setRenderBounds(0.0, 0.0, 0.25, 0.25, 1.0, 0.75);
            renderer.renderFaceYPos(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceYNeg(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceXNeg(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceZNeg(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceZPos(block, (double) x, (double) y, (double) z, icon);
        }
        if (connectPosX) {
            renderer.setRenderBounds(0.75, 0.0, 0.25, 1.0, 1.0, 0.75);
            renderer.renderFaceYPos(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceYNeg(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceXPos(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceZNeg(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceZPos(block, (double) x, (double) y, (double) z, icon);
        }
        if (connectPosZ) {
            renderer.setRenderBounds(0.25, 0.0, 0.75, 0.75, 1.0, 1.0);
            renderer.renderFaceYPos(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceYNeg(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceZPos(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceXNeg(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceXPos(block, (double) x, (double) y, (double) z, icon);
        }
        if (connectNegZ) {
            renderer.setRenderBounds(0.25, 0.0, 0.0, 0.75, 1.0, 0.25);
            renderer.renderFaceYPos(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceYNeg(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceZNeg(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceXNeg(block, (double) x, (double) y, (double) z, icon);
            renderer.renderFaceXPos(block, (double) x, (double) y, (double) z, icon);
        }
        GL11.glPopMatrix();
        return false;
    }

    public boolean shouldRender3DInInventory(final int modelId) {
        return true;
    }

    public int getRenderId() {
        return ModuleBlocks.hedgeRenderID;
    }
}
