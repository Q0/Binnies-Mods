package binnie.core.network;

import binnie.core.AbstractMod;
import binnie.core.network.packet.MessageBinnie;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public abstract class BinniePacketHandler implements IMessageHandler {
    private IPacketProvider provider;

    public BinniePacketHandler(AbstractMod mod) {
        super();
        this.setProvider(mod);
    }

    public void setProvider(IPacketProvider provider) {
        this.provider = provider;
    }

    public IMessage onMessage(MessageBinnie message, MessageContext ctx) {
        try {
            int packetId = message.id;

            for (IPacketID id : this.provider.getPacketIDs()) {
                if (id.ordinal() == packetId) {
                    id.onMessage(message, ctx);
                    return null;
                }
            }

            return null;
        } catch (Exception var8) {
            throw new RuntimeException(var8);
        }
    }
}
