package binnie.core.machines;

import binnie.core.machines.IOwnable;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.MachinePackage;
import binnie.core.machines.MachineUtil;
import java.util.Collection;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
