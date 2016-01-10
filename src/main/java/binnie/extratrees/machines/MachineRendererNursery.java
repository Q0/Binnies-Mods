package binnie.extratrees.machines;

import binnie.core.BinnieCore;
import binnie.core.resource.BinnieResource;
import binnie.core.resource.ResourceType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.ModelNursery;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class MachineRendererNursery {
   public static MachineRendererNursery instance = new MachineRendererNursery();
   BinnieResource texture;
   private IModelCustom casinoMachine;
   private ModelNursery model = new ModelNursery();

   public MachineRendererNursery() {
      super();
   }

   public void renderMachine(BinnieResource texture, double x, double y, double z, float var8) {
      this.texture = texture;
      GL11.glPushMatrix();
      GL11.glTranslated(x + 0.5D, y, z + 0.5D);
      GL11.glScaled(0.05D, 0.05D, 0.05D);
      BinnieCore.proxy.bindTexture(new BinnieResource(ExtraTrees.instance, ResourceType.Tile, "test.png"));
      this.casinoMachine.renderAll();
      GL11.glPopMatrix();
   }
}
