package binnie.genetics.machine;

import binnie.core.BinnieCore;
import binnie.core.machines.Machine;
import binnie.core.machines.component.IRender;
import binnie.core.resource.BinnieResource;
import binnie.genetics.machine.ModelMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MachineRendererGenetics {
   public static MachineRendererGenetics instance = new MachineRendererGenetics();
   public final RenderItem customRenderItem = new RenderItem() {
      public boolean shouldBob() {
         return false;
      }

      public boolean shouldSpreadItems() {
         return false;
      }
   };
   private ModelMachine model = new ModelMachine();

   public MachineRendererGenetics() {
      super();
      this.customRenderItem.setRenderManager(RenderManager.instance);
   }

   public void renderMachine(Machine machine, int colour, BinnieResource texture, double x, double y, double z, float var8) {
      GL11.glPushMatrix();
      int i1 = 0;
      int ix = machine.getTileEntity().xCoord;
      int iy = machine.getTileEntity().yCoord;
      int iz = machine.getTileEntity().zCoord;
      if(machine.getTileEntity() != null) {
         i1 = ix * iy * iz + ix * iy - ix * iz + iy * iz - ix + iy - iz;
      }

      float phase = (float)Math.max(0.0D, Math.sin((double)(System.currentTimeMillis() + (long)i1) * 0.003D));
      GL11.glTranslated(x + 0.5D, y + 1.5D, z + 0.5D);
      GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
      BinnieCore.proxy.bindTexture(texture);
      GL11.glPushMatrix();
      this.model.render((float)x, (float)y, (float)z, 0.0625F, 0.0625F, 0.0625F);
      GL11.glPopMatrix();
      GL11.glPushMatrix();
      BinnieCore.proxy.getMinecraftInstance();
      if(Minecraft.isFancyGraphicsEnabled()) {
         for(IRender.Render render : machine.getInterfaces(IRender.Render.class)) {
            render.renderInWorld(this.customRenderItem, x, y, z);
         }
      }

      GL11.glPopMatrix();
      GL11.glPopMatrix();
   }
}
