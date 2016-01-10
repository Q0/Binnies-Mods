package binnie.core.network.packet;

import binnie.core.network.BinnieCorePacketID;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.packet.MessageNBT;
import net.minecraft.nbt.NBTTagCompound;

public class MessageCraftGUI extends MessageNBT {
   public MessageCraftGUI(MessageBinnie message) {
      super(message);
   }

   public MessageCraftGUI(NBTTagCompound action) {
      super(BinnieCorePacketID.CraftGUIAction.ordinal(), action);
   }
}
