package binnie.core.network.packet;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.io.IOException;

public class MessageTileNBT extends MessageNBT implements IPacketLocation {
    private int posX;
    private int posY;
    private int posZ;

    public MessageTileNBT(final MessageBinnie message) {
        super(message);
    }

    public MessageTileNBT(final int id, final TileEntity tile, final NBTTagCompound nbt) {
        super(id);
        this.posX = tile.xCoord;
        this.posY = tile.yCoord;
        this.posZ = tile.zCoord;
        this.nbt = nbt;
    }

    @Override
    public void writeData(final ByteBuf data) throws IOException {
        data.writeInt(this.posX);
        data.writeInt(this.posY);
        data.writeInt(this.posZ);
        super.writeData(data);
    }

    @Override
    public void readData(final ByteBuf data) throws IOException {
        this.posX = data.readInt();
        this.posY = data.readInt();
        this.posZ = data.readInt();
        super.readData(data);
    }

    @Override
    public TileEntity getTarget(final World world) {
        return world.getTileEntity(this.posX, this.posY, this.posZ);
    }

    @Override
    public int getX() {
        return this.posX;
    }

    @Override
    public int getY() {
        return this.posY;
    }

    @Override
    public int getZ() {
        return this.posZ;
    }

    @Override
    public NBTTagCompound getTagCompound() {
        return this.nbt;
    }

    @Override
    void setTagCompound(final NBTTagCompound nbt) {
        this.nbt = nbt;
    }
}
