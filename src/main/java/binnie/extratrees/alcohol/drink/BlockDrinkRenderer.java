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
    public void renderInventoryBlock(final Block block, final int metadata, final int modelId, final RenderBlocks renderer) {
    }

    public boolean renderWorldBlock(final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer) {
        GL11.glPushMatrix();
        Tessellator.instance.setColorRGBA(255, 255, 255, 150);
        final Tessellator tess = Tessellator.instance;
        renderer.renderFaceYPos(block, (double) x, (double) y, (double) z, Blocks.dirt.getIcon(0, 0));
        final float s = 0.0625f;
        for (int h = 0; h < 16; ++h) {
            final float d1 = 8.0f * s;
            final float d2 = 8.0f * s;
            final float h2 = h * s;
            final float h3 = h * s;
            tess.addVertexWithUV((double) (x + 0.5f - d1), (double) (y + h2), (double) (z + 0.5f - d1), 0.0, 0.0);
            tess.addVertexWithUV((double) (x + 0.5f - d1), (double) (y + h3), (double) (z + 0.5f - d1), 0.0, 0.0);
            tess.addVertexWithUV((double) (x + 0.5f + d1), (double) (y + h3), (double) (z + 0.5f - d1), 0.0, 0.0);
            tess.addVertexWithUV((double) (x + 0.5f + d1), (double) (y + h2), (double) (z + 0.5f - d1), 0.0, 0.0);
        }
        GL11.glPopMatrix();
        return false;
    }

    public boolean shouldRender3DInInventory(final int modelId) {
        return false;
    }

    public int getRenderId() {
        return ModuleAlcohol.drinkRendererID;
    }
}
