package binnie.core.network.packet;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

interface IPacketLocation {
    TileEntity getTarget(World var1);

    int getX();

    int getY();

    int getZ();
}
