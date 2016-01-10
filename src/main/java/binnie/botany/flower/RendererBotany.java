package binnie.botany.flower;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RendererBotany implements ISimpleBlockRenderingHandler {
    public static int renderID;
    public static int pass;

    public RendererBotany() {
        super();
    }

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        double d1 = (double) x;
        double d2 = (double) y;
        double d0 = (double) z;
        long i1 = (long) (x * 3129871) ^ (long) z * 116129781L;
        i1 = i1 * i1 * 42317861L + i1 * 11L;
        d1 = d1 + ((double) ((float) (i1 >> 16 & 15L) / 15.0F) - 0.5D) * 0.3D;
        d0 = d0 + ((double) ((float) (i1 >> 24 & 15L) / 15.0F) - 0.5D) * 0.3D;
        int rot = (int) (i1 % 4L);

        for (int i = 0; i < 3; ++i) {
            pass = i;
            int l = block.colorMultiplier(renderer.blockAccess, x, y, z);
            float f = (float) (l >> 16 & 255) / 255.0F;
            float f1 = (float) (l >> 8 & 255) / 255.0F;
            float f2 = (float) (l & 255) / 255.0F;
            if (EntityRenderer.anaglyphEnable) {
                float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
                float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
                float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
                f = f3;
                f1 = f4;
                f2 = f5;
            }

            tessellator.setColorOpaque_F(f, f1, f2);
            IIcon iicon = block.getIcon(world, x, y, z, 0);
            renderer.drawCrossedSquares(iicon, d1, d2, d0, 1.0F);
        }

        return false;
    }

    public boolean shouldRender3DInInventory(int i) {
        return false;
    }

    public int getRenderId() {
        return renderID;
    }
}
