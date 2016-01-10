package binnie.core.network;

import binnie.core.network.packet.PacketPayload;

public interface INetworkedEntity {
    void writeToPacket(PacketPayload var1);

    void readFromPacket(PacketPayload var1);
}
