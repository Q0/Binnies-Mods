package binnie.core.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

import java.io.IOException;

public final class MessageBinnie implements IMessage {
    public int id;
    private MessageBase message;
    ByteBuf data;

    public MessageBinnie() {
        super();
    }

    public MessageBinnie(int id, MessageBase base) {
        super();
        this.id = id;
        this.message = base;
    }

    public void toBytes(ByteBuf buf) {
        buf.writeByte(this.id);

        try {
            this.message.writeData(buf);
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public void fromBytes(ByteBuf buf) {
        this.id = buf.readByte();
        this.data = buf;
    }
}
