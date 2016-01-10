package binnie.extratrees.block;

import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class StairsRenderer implements ISimpleBlockRenderingHandler {
    public StairsRenderer() {
        super();
    }

    public void renderInventoryBlock(Block par1Block, int metadata, int modelID, RenderBlocks renderer) {
        Tessellator var4 = Tessellator.instance;

        for (int var14 = 0; var14 < 2; ++var14) {
            if (var14 == 0) {
                renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
            }

            if (var14 == 1) {
                renderer.setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
            }

            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            var4.startDrawingQuads();
            var4.setNormal(0.0F, -1.0F, 0.0F);
            renderer.renderFaceYNeg(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(0, metadata));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(1, metadata));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0F, 0.0F, -1.0F);
            renderer.renderFaceXPos(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(2, metadata));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(0.0F, 0.0F, 1.0F);
            renderer.renderFaceXNeg(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(3, metadata));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(-1.0F, 0.0F, 0.0F);
            renderer.renderFaceZNeg(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(4, metadata));
            var4.draw();
            var4.startDrawingQuads();
            var4.setNormal(1.0F, 0.0F, 0.0F);
            renderer.renderFaceZPos(par1Block, 0.0D, 0.0D, 0.0D, par1Block.getIcon(5, metadata));
            var4.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }

    }

    public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4, Block block, int modelId, RenderBlocks renderer) {
        BlockETStairs blockStairs = (BlockETStairs) block;
        blockStairs.func_150147_e(renderer.blockAccess, par2, par3, par4);
        renderer.setRenderBoundsFromBlock(blockStairs);
        renderer.renderStandardBlock(blockStairs, par2, par3, par4);
        boolean var5 = blockStairs.func_150145_f(renderer.blockAccess, par2, par3, par4);
        renderer.setRenderBoundsFromBlock(blockStairs);
        renderer.renderStandardBlock(blockStairs, par2, par3, par4);
        if (var5 && blockStairs.func_150144_g(renderer.blockAccess, par2, par3, par4)) {
            renderer.setRenderBoundsFromBlock(blockStairs);
            renderer.renderStandardBlock(blockStairs, par2, par3, par4);
        }

        return true;
    }

    public boolean shouldRender3DInInventory(int i) {
        return true;
    }

    public int getRenderId() {
        return ExtraTrees.stairsID;
    }
}
