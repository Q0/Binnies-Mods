package binnie.core.proxy;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.core.gui.IBinnieGUID;
import binnie.core.network.packet.MessageBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.IIcon;

public class BinnieModProxy implements IBinnieModProxy {
    private AbstractMod mod;

    public BinnieModProxy(final AbstractMod mod) {
        this.mod = mod;
    }

    @Override
    public void openGui(final IBinnieGUID ID, final EntityPlayer player, final int x, final int y, final int z) {
        BinnieCore.proxy.openGui(mod, ID.ordinal(), player, x, y, z);
    }

    @Override
    public void sendToAll(final MessageBase packet) {
        mod.getNetworkWrapper().sendToAll(packet.GetMessage());
    }

    @Override
    public void sendToPlayer(final MessageBase packet, final EntityPlayer entityplayer) {
        if (entityplayer instanceof EntityPlayerMP) {
            mod.getNetworkWrapper().sendTo(packet.GetMessage(), (EntityPlayerMP) entityplayer);
        }
    }

    @Override
    public void sendToServer(final MessageBase packet) {
        mod.getNetworkWrapper().sendToServer(packet.GetMessage());
    }

    @Override
    public IIcon getIcon(final IIconRegister register, final String string) {
        return BinnieCore.proxy.getIcon(register, mod.getModID(), string);
    }

    @Override
    public void preInit() {
    }

    @Override
    public void init() {
    }

    @Override
    public void postInit() {
    }

    public String localise(final String string) {
        return Binnie.Language.localise(mod, string);
    }

    public String localiseOrBlank(final String string) {
        return Binnie.Language.localiseOrBlank(mod, string);
    }
}
