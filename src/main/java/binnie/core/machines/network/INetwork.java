package binnie.core.machines.network;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public interface INetwork {
    public interface GuiNBT extends INetwork.RecieveGuiNBT, INetwork.SendGuiNBT {
    }

    public interface RecieveGuiNBT {
        void recieveGuiNBT(Side var1, EntityPlayer var2, String var3, NBTTagCompound var4);
    }

    public interface SendGuiNBT {
        void sendGuiNBT(Map var1);
    }

    public interface TilePacketSync {
        void syncToNBT(NBTTagCompound var1);

        void syncFromNBT(NBTTagCompound var1);
    }
}
