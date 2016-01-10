package binnie.core.machines;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineGroup;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;

public abstract class MachinePackage {
   private String uid;
   private boolean active = true;
   boolean powered = false;
   private int metadata = -1;
   private MachineGroup group;

   public String getUID() {
      return this.uid;
   }

   protected MachinePackage(String uid, boolean powered) {
      super();
      this.uid = uid;
      this.powered = powered;
   }

   public abstract void createMachine(Machine var1);

   public abstract TileEntity createTileEntity();

   public abstract void register();

   public final String getDisplayName() {
      return Binnie.Language.localise(this.group.getMod(), "machine." + this.group.getShortUID() + "." + this.getUID());
   }

   public final Integer getMetadata() {
      return Integer.valueOf(this.metadata);
   }

   public void assignMetadata(int meta) {
      this.metadata = meta;
   }

   public MachineGroup getGroup() {
      return this.group;
   }

   public void setGroup(MachineGroup group) {
      this.group = group;
   }

   public abstract void renderMachine(Machine var1, double var2, double var4, double var6, float var8, RenderBlocks var9);

   public boolean isActive() {
      return this.active;
   }

   public void setActive(boolean active) {
      this.active = active;
   }

   public final String getInformation() {
      return Binnie.Language.localise(this.group.getMod(), "machine." + this.group.getShortUID() + "." + this.getUID() + ".info");
   }
}
