package binnie.core.network.packet;

import binnie.core.network.INetworkedEntity;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.packet.MessageCoordinates;
import binnie.core.network.packet.PacketPayload;
import io.netty.buffer.ByteBuf;
import java.io.IOException;
import java.util.Iterator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MessageUpdate extends MessageCoordinates {
   public PacketPayload payload;

   public MessageUpdate(MessageBinnie message) {
      super(message);
   }

   public MessageUpdate(int id, INetworkedEntity tile) {
      super(id, ((TileEntity)tile).xCoord, ((TileEntity)tile).yCoord, ((TileEntity)tile).zCoord);
      this.payload = new PacketPayload();
      tile.writeToPacket(this.payload);
   }

   public void writeData(ByteBuf data) throws IOException {
      super.writeData(data);
      if(this.payload == null) {
         data.writeInt(0);
         data.writeInt(0);
         data.writeInt(0);
      } else {
         data.writeInt(this.payload.intPayload.size());
         data.writeInt(this.payload.floatPayload.size());
         data.writeInt(this.payload.stringPayload.size());
         Iterator i$ = this.payload.intPayload.iterator();

         while(i$.hasNext()) {
            int intData = ((Integer)i$.next()).intValue();
            data.writeInt(intData);
         }

         i$ = this.payload.floatPayload.iterator();

         while(i$.hasNext()) {
            float floatData = ((Float)i$.next()).floatValue();
            data.writeFloat(floatData);
         }

         for(String stringData : this.payload.stringPayload) {
            byte[] bytes = stringData.getBytes("UTF-8");
            data.writeShort(bytes.length);
            data.writeBytes(bytes);
         }

      }
   }

   public void readData(ByteBuf data) throws IOException {
      super.readData(data);
      this.payload = new PacketPayload();
      int intLength = data.readInt();
      int floatLength = data.readInt();
      int stringLength = data.readInt();
      this.payload.intPayload.clear();
      this.payload.floatPayload.clear();
      this.payload.stringPayload.clear();

      for(int i = 0; i < intLength; ++i) {
         this.payload.addInteger(data.readInt());
      }

      for(int i = 0; i < floatLength; ++i) {
         this.payload.addFloat(data.readFloat());
      }

      for(int i = 0; i < stringLength; ++i) {
         int length = data.readShort();
         byte[] string = data.readBytes(length).array();
         this.payload.addString(new String(string, "UTF-8"));
      }

   }

   public TileEntity getTarget(World world) {
      return world.getTileEntity(this.posX, this.posY, this.posZ);
   }
}
