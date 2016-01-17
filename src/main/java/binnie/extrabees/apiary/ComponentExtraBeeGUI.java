package binnie.extrabees.apiary;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeGUID;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ComponentExtraBeeGUI extends MachineComponent implements IInteraction.RightClick {
    ExtraBeeGUID id;

    public ComponentExtraBeeGUI(final Machine machine, final ExtraBeeGUID id) {
        super(machine);
        this.id = id;
    }

    @Override
    public void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z) {
        ExtraBees.proxy.openGui(id, player, x, y, z);
    }
}
