package binnie.core.machines;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Collection;

public interface IMachine extends IOwnable {
    void addComponent(MachineComponent var1);

    MachineUtil getMachineUtil();

    Object getInterface(Class var1);

    void markDirty();

    World getWorld();

    TileEntity getTileEntity();

    Collection getInterfaces(Class var1);

    MachinePackage getPackage();
}
