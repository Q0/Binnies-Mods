package binnie.core.machines.storage;

import binnie.core.BinnieCore;
import binnie.core.gui.BinnieCoreGUI;
import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

class ComponentBinnieCoreGUI extends MachineComponent implements IInteraction.RightClick {
    private BinnieCoreGUI id;

    public ComponentBinnieCoreGUI(final Machine machine, final BinnieCoreGUI id) {
        super(machine);
        this.id = id;
    }

    @Override
    public void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z) {
        BinnieCore.proxy.openGui(this.id, player, x, y, z);
    }
}
