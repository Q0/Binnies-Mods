package binnie.craftgui.genetics.machine;

import binnie.core.AbstractMod;
import binnie.craftgui.minecraft.Window;
import binnie.genetics.Genetics;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowGeneProject extends Window {
    public WindowGeneProject(final EntityPlayer player, final IInventory inventory, final Side side) {
        super(100.0f, 100.0f, player, inventory, side);
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "GeneProjects";
    }

    @Override
    public void initialiseClient() {
        this.setTitle("Gene Projects");
    }
}
