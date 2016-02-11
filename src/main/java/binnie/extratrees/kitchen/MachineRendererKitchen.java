package binnie.extratrees.kitchen;

import binnie.core.machines.Machine;
import binnie.core.resource.BinnieResource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MachineRendererKitchen {
    public static MachineRendererKitchen instance;
    static int level;

    static {
        MachineRendererKitchen.level = 0;
        MachineRendererKitchen.instance = new MachineRendererKitchen();
    }

    BinnieResource texture;

    public void renderMachine(final Machine machine, final BinnieResource texture, final double x, final double y, final double z, final float var8, final RenderBlocks renderer) {
        if (renderer != null && renderer.blockAccess != null) {
            final Block block = machine.getPackage().getGroup().getBlock();
            final float i = 0.0625f;
            renderer.setRenderBounds((double) (2.0f * i), 0.0, (double) (2.0f * i), (double) (14.0f * i), (double) (14.0f * i), (double) (14.0f * i));
            renderer.renderStandardBlock(block, (int) x, (int) y, (int) z);
            MachineRendererKitchen.level = 1;
            renderer.setRenderBounds(0.0, (double) (14.0f * i), 0.0, 1.0, 1.0, 1.0);
            renderer.renderStandardBlock(block, (int) x, (int) y, (int) z);
            MachineRendererKitchen.level = 0;
            renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            if (RenderManager.instance == null || RenderManager.instance.renderEngine == null) {
                return;
            }
            final IIcon icon = Blocks.dirt.getIcon(0, 0);
            final Tessellator tess = Tessellator.instance;
            boolean wasTessellating = true;
            try {
                tess.draw();
            } catch (Exception e) {
                wasTessellating = false;
            }
            tess.startDrawingQuads();
            tess.draw();
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y + 1.0, z + 0.5);
            RenderManager.instance.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            final float ux = icon.getMinU();
            final float uy = icon.getMinV();
            final float uw = icon.getMaxU();
            final float uh = icon.getMaxV();
            final int[] widths = {1, 1, 1, 1, 1, 1, 1, 1, 2, 4, 5, 6, 6, 6, 5, 4};
            for (int h = 0; h < 15; ++h) {
                final float h2 = h * i;
                final float h3 = h2 + i;
                final float d1 = widths[h] * i;
                final float d2 = widths[h + 1] * i;
                float ang = 0.707f;
                final float half = 8.0f * i;
                if (h < 13) {
                    ang -= 0.05f;
                    for (int angle = 0; angle < 360; angle += 90) {
                    }
                    if (h == 12) {
                    }
                    ang += 0.05f;
                }
                for (int angle = 0; angle < 360; angle += 90) {
                    GL11.glPushMatrix();
                    GL11.glRotatef((float) angle, 0.0f, 1.0f, 0.0f);
                    GL11.glTranslatef(-0.5f, 0.0f, 0.0f);
                    tess.startDrawingQuads();
                    tess.setColorRGBA(255, 255, 255, 150);
                    tess.addVertexWithUV((double) (half - d1 * ang), (double) h2, (double) (-d1 * ang), 0.0, 0.0);
                    tess.addVertexWithUV((double) (half - d2 * ang), (double) h3, (double) (-d2 * ang), 0.0, 0.0);
                    tess.addVertexWithUV((double) (half + d2 * ang), (double) h3, (double) (-d2 * ang), 0.0, 0.0);
                    tess.addVertexWithUV((double) (half + d1 * ang), (double) h2, (double) (-d1 * ang), 0.0, 0.0);
                    tess.draw();
                    tess.startDrawingQuads();
                    tess.addVertexWithUV((double) (half + d1 * ang), (double) h2, (double) (-d1 * ang), 0.0, 0.0);
                    tess.addVertexWithUV((double) (half + d2 * ang), (double) h3, (double) (-d2 * ang), 0.0, 0.0);
                    tess.addVertexWithUV((double) (half - d2 * ang), (double) h3, (double) (-d2 * ang), 0.0, 0.0);
                    tess.addVertexWithUV((double) (half - d1 * ang), (double) h2, (double) (-d1 * ang), 0.0, 0.0);
                    tess.draw();
                    GL11.glPopMatrix();
                }
            }
            GL11.glPopMatrix();
            if (wasTessellating) {
                tess.startDrawingQuads();
            }
        }
    }
}
