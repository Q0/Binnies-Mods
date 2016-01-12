package binnie.genetics.machine;

import binnie.core.machines.Machine;
import binnie.core.machines.MachineComponent;
import binnie.core.machines.component.IInteraction;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ComponentGeneticGUI extends MachineComponent implements IInteraction.RightClick {
    GeneticsGUI id;

    public ComponentGeneticGUI(final Machine machine, final GeneticsGUI id) {
        super(machine);
        this.id = id;
    }

    @Override
    public void onRightClick(final World world, final EntityPlayer player, final int x, final int y, final int z) {
        Genetics.proxy.openGui(this.id, player, x, y, z);
    }
}
