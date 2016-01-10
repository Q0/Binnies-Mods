package binnie.core.network;

import binnie.core.network.IPacketID;

public interface IPacketProvider {
   String getChannel();

   IPacketID[] getPacketIDs();
}
