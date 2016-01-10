package binnie.core.proxy;

import binnie.core.gui.IBinnieGUID;
import binnie.core.network.packet.MessageBase;
import binnie.core.proxy.IProxyCore;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;

interface IBinnieModProxy extends IProxyCore {
   void openGui(IBinnieGUID var1, EntityPlayer var2, int var3, int var4, int var5);

   void sendToAll(MessageBase var1);

   void sendToPlayer(MessageBase var1, EntityPlayer var2);

   void sendToServer(MessageBase var1);

   IIcon getIcon(IIconRegister var1, String var2);
}
