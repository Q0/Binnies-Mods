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
   static int level = 0;
   public static MachineRendererKitchen instance = new MachineRendererKitchen();
   BinnieResource texture;

   public MachineRendererKitchen() {
      super();
   }

   public void renderMachine(Machine machine, BinnieResource texture, double x, double y, double z, float var8, RenderBlocks renderer) {
      if(renderer != null && renderer.blockAccess != null) {
         Block block = machine.getPackage().getGroup().getBlock();
         float i = 0.0625F;
         renderer.setRenderBounds((double)(2.0F * i), 0.0D, (double)(2.0F * i), (double)(14.0F * i), (double)(14.0F * i), (double)(14.0F * i));
         renderer.renderStandardBlock(block, (int)x, (int)y, (int)z);
         level = 1;
         renderer.setRenderBounds(0.0D, (double)(14.0F * i), 0.0D, 1.0D, 1.0D, 1.0D);
         renderer.renderStandardBlock(block, (int)x, (int)y, (int)z);
         level = 0;
         renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
         if(RenderManager.instance == null || RenderManager.instance.renderEngine == null) {
            return;
         }

         IIcon icon = Blocks.dirt.getIcon(0, 0);
         Tessellator tess = Tessellator.instance;
         boolean wasTessellating = true;

         try {
            tess.draw();
         } catch (Exception var29) {
            wasTessellating = false;
         }

         tess.startDrawingQuads();
         tess.draw();
         GL11.glPushMatrix();
         GL11.glTranslated(x + 0.5D, y + 1.0D, z + 0.5D);
         RenderManager.instance.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
         float ux = icon.getMinU();
         float uy = icon.getMinV();
         float uw = icon.getMaxU();
         float uh = icon.getMaxV();
         int[] widths = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 2, 4, 5, 6, 6, 6, 5, 4};

         for(int h = 0; h < 15; ++h) {
            float h1 = (float)h * i;
            float h2 = h1 + i;
            float d1 = (float)widths[h] * i;
            float d2 = (float)widths[h + 1] * i;
            float ang = 0.707F;
            float half = 8.0F * i;
            if(h < 13) {
               ang = ang - 0.05F;

               for(int angle = 0; angle < 360; angle += 90) {
                  ;
               }

               if(h == 12) {
                  ;
               }

               ang = ang + 0.05F;
            }

            for(int angle = 0; angle < 360; angle += 90) {
               GL11.glPushMatrix();
               GL11.glRotatef((float)angle, 0.0F, 1.0F, 0.0F);
               GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
               tess.startDrawingQuads();
               tess.setColorRGBA(255, 255, 255, 150);
               tess.addVertexWithUV((double)(half - d1 * ang), (double)h1, (double)(-d1 * ang), 0.0D, 0.0D);
               tess.addVertexWithUV((double)(half - d2 * ang), (double)h2, (double)(-d2 * ang), 0.0D, 0.0D);
               tess.addVertexWithUV((double)(half + d2 * ang), (double)h2, (double)(-d2 * ang), 0.0D, 0.0D);
               tess.addVertexWithUV((double)(half + d1 * ang), (double)h1, (double)(-d1 * ang), 0.0D, 0.0D);
               tess.draw();
               tess.startDrawingQuads();
               tess.addVertexWithUV((double)(half + d1 * ang), (double)h1, (double)(-d1 * ang), 0.0D, 0.0D);
               tess.addVertexWithUV((double)(half + d2 * ang), (double)h2, (double)(-d2 * ang), 0.0D, 0.0D);
               tess.addVertexWithUV((double)(half - d2 * ang), (double)h2, (double)(-d2 * ang), 0.0D, 0.0D);
               tess.addVertexWithUV((double)(half - d1 * ang), (double)h1, (double)(-d1 * ang), 0.0D, 0.0D);
               tess.draw();
               GL11.glPopMatrix();
            }
         }

         GL11.glPopMatrix();
         if(wasTessellating) {
            tess.startDrawingQuads();
         }
      }

   }
}
