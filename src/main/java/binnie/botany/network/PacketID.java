package binnie.botany.network;

import binnie.botany.api.IAlleleFlowerSpecies;
import binnie.botany.flower.TileEntityFlower;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.network.MessageFlowerUpdate;
import binnie.core.BinnieCore;
import binnie.core.network.IPacketID;
import binnie.core.network.packet.MessageBinnie;
import binnie.core.network.packet.MessageNBT;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import forestry.api.genetics.AlleleManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public enum PacketID implements IPacketID {
   Encylopedia,
   FlowerUpdate;

   private PacketID() {
   }

   public void onMessage(MessageBinnie message, MessageContext context) {
      if(this == Encylopedia && context.side == Side.CLIENT) {
         MessageNBT packet = new MessageNBT(message);
         NBTTagCompound data = packet.getTagCompound();
         EntityPlayer player = BinnieCore.proxy.getPlayer();
         String info = "";
         if(data.hasNoTags()) {
            info = info + "Flower has not been discovered by you. Breed this flower yourself to discover.";
         } else {
            IAlleleFlowerSpecies primary = (IAlleleFlowerSpecies)AlleleManager.alleleRegistry.getAllele(data.getString("Species"));
            IAlleleFlowerSpecies secondary = (IAlleleFlowerSpecies)AlleleManager.alleleRegistry.getAllele(data.getString("Species2"));
            float age = data.getFloat("Age");
            EnumFlowerColor color1 = EnumFlowerColor.get(data.getShort("Colour"));
            EnumFlowerColor color2 = EnumFlowerColor.get(data.getShort("Colour2"));
            if(primary == null || secondary == null) {
               return;
            }

            info = info + "A";
            if(age == 0.0F) {
               info = info + "";
            } else if(age < 0.25F) {
               info = info + " Young";
            } else if(age < 0.75F) {
               info = info + " Mature";
            } else {
               info = info + " Old";
            }

            if(color1 == color2) {
               info = info + " " + color1.getName();
            } else {
               info = info + " " + color1.getName() + " & " + color2.getName();
            }

            if(primary == secondary) {
               info = info + " " + primary.getName();
            } else {
               info = info + " " + primary.getName() + "-" + secondary.getName() + " Hybrid";
            }

            if(age == 0.0F) {
               info = info + " Germling";
            }

            if(data.getBoolean("Wilting")) {
               info = info + ". Shame it is Wilting!";
            }
         }

         IChatComponent chat = new ChatComponentText(info);
         player.addChatMessage(chat);
      } else if(this == FlowerUpdate) {
         MessageFlowerUpdate packet = new MessageFlowerUpdate(message);
         TileEntity tile = packet.getTileEntity(BinnieCore.proxy.getWorld());
         if(tile instanceof TileEntityFlower) {
            ((TileEntityFlower)tile).setRender(packet.render);
         }
      }

   }
}
