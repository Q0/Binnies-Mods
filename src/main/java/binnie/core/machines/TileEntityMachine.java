package binnie.core.machines;

import binnie.Binnie;
import binnie.core.machines.Machine;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.base.TileEntityMachineBase;
import binnie.core.machines.component.IInteraction;
import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.PacketPayload;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;

public class TileEntityMachine extends TileEntityMachineBase implements INetworkedEntity {
   private Machine machine;

   public void updateEntity() {
      super.updateEntity();
      if(this.machine != null) {
         this.machine.onUpdate();
      }

   }

   public boolean canUpdate() {
      return super.canUpdate();
   }

   public TileEntityMachine(MachinePackage pack) {
      super();
      this.setMachine(pack);
   }

   public TileEntityMachine() {
      super();
   }

   public void setMachine(MachinePackage pack) {
      if(pack != null) {
         this.machine = new Machine(pack, this);
      }

   }

   public void readFromNBT(NBTTagCompound nbtTagCompound) {
      super.readFromNBT(nbtTagCompound);
      String name = nbtTagCompound.getString("name");
      String group = nbtTagCompound.getString("group");
      MachinePackage pack = Binnie.Machine.getPackage(group, name);
      if(pack == null) {
         this.invalidate();
      } else {
         this.setMachine(pack);
         this.getMachine().readFromNBT(nbtTagCompound);
      }
   }

   public void writeToNBT(NBTTagCompound nbtTagCompound) {
      super.writeToNBT(nbtTagCompound);
      String name = this.machine.getPackage().getUID();
      String group = this.machine.getPackage().getGroup().getUID();
      nbtTagCompound.setString("group", group);
      nbtTagCompound.setString("name", name);
      this.getMachine().writeToNBT(nbtTagCompound);
   }

   public void writeToPacket(PacketPayload payload) {
      this.machine.writeToPacket(payload);
   }

   public void readFromPacket(PacketPayload payload) {
      this.machine.readFromPacket(payload);
   }

   public Machine getMachine() {
      return this.machine;
   }

   public void onBlockDestroy() {
      if(this.getMachine() != null) {
         this.getMachine().onBlockDestroy();
      }

   }

   public final Packet getDescriptionPacket() {
      return this.getMachine() != null?this.getMachine().getDescriptionPacket():null;
   }

   public void invalidate() {
      super.invalidate();

      for(IInteraction.Invalidation c : this.getMachine().getInterfaces(IInteraction.Invalidation.class)) {
         c.onInvalidation();
      }

   }

   public void onChunkUnload() {
      super.onChunkUnload();

      for(IInteraction.ChunkUnload c : this.getMachine().getInterfaces(IInteraction.ChunkUnload.class)) {
         c.onChunkUnload();
      }

   }
}
