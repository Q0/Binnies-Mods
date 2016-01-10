package binnie.genetics.core;

import binnie.core.BinnieCore;
import binnie.core.network.IPacketID;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.packet.MessageNBT;
import binnie.genetics.genetics.GeneTracker;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;

public enum GeneticsPacket implements IPacketID {
   GeneTrackerSync;

   private GeneticsPacket() {
   }

   public void onMessage(MessageBinnie message, MessageContext context) {
      if(this == GeneTrackerSync && context.side == Side.CLIENT) {
         MessageNBT packet = new MessageNBT(message);
         EntityPlayer player = BinnieCore.proxy.getPlayer();
         GeneTracker tracker = null;
         tracker = GeneTracker.getTracker(BinnieCore.proxy.getWorld(), player.getGameProfile());
         if(tracker != null) {
            tracker.readFromNBT(packet.getTagCompound());
         }
      }

   }
}
