package binnie.core.network.packet;

import binnie.core.network.packet.MessageBase;
import binnie.core.network.packet.MessageBinnie;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.nbt.NBTTagCompound;

public class MessageNBT extends MessageBase {
   NBTTagCompound nbt;

   public NBTTagCompound getTagCompound() {
      return this.nbt;
   }

   void setTagCompound(NBTTagCompound nbt) {
      this.nbt = nbt;
   }

   public MessageNBT(int id) {
      super(id);
   }

   public MessageNBT(int id, NBTTagCompound nbt) {
      this(id);
      this.setTagCompound(nbt);
   }

   public MessageNBT(MessageBinnie message) {
      super(message);
   }

   public void writeData(ByteBuf data) throws IOException {
      this.writeNBTTagCompound(this.nbt, data);
   }

   public void readData(ByteBuf data) throws IOException {
      this.nbt = this.readNBTTagCompound(data);
   }
}
