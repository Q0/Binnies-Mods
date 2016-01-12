package binnie.genetics.core;

import binnie.core.gui.IBinnieGUID;
import binnie.craftgui.genetics.machine.*;
import binnie.craftgui.minecraft.Window;
import binnie.genetics.gui.WindowAnalyst;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.lang.reflect.Constructor;

public enum GeneticsGUI implements IBinnieGUID {
    Genepool((Class) WindowGenepool.class),
    Isolator((Class) WindowIsolator.class),
    Sequencer((Class) WindowSequencer.class),
    Replicator((Class) WindowPolymeriser.class),
    Inoculator((Class) WindowInoculator.class),
    GeneBank((Class) WindowGeneBank.class),
    Analyser((Class) WindowAnalyser.class),
    Incubator((Class) WindowIncubator.class),
    Database((Class) WindowGeneBank.class),
    DatabaseNEI((Class) WindowGeneBankNEI.class),
    Acclimatiser((Class) WindowAcclimatiser.class),
    Splicer((Class) WindowSplicer.class),
    Analyst,
    Registry,
    MasterRegistry;

    Class<? extends Window> windowClass;

    private GeneticsGUI(final Class window) {
        this.windowClass = (Class<? extends Window>) window;
    }


    GeneticsGUI() {

    }

    public Window getWindow(final EntityPlayer player, final IInventory object, final Side side) throws Exception {
        switch (this) {
            case Analyst: {
                return new WindowAnalyst(player, null, side, false, false);
            }
            case MasterRegistry: {
                return new WindowAnalyst(player, null, side, true, true);
            }
            case Registry: {
                return new WindowAnalyst(player, null, side, true, false);
            }
            default: {
                final Constructor constr = this.windowClass.getConstructor(EntityPlayer.class, IInventory.class, Side.class);
                return (Window) constr.newInstance(player, object, side);
            }
        }
    }

    @Override
    public Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
        Window window = null;
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        IInventory object = null;
        if (tileEntity instanceof IInventory) {
            object = (IInventory) tileEntity;
        }
        try {
            if (this == GeneticsGUI.Database || this == GeneticsGUI.GeneBank) {
                window = WindowGeneBank.create(player, object, side);
            } else if (this == GeneticsGUI.DatabaseNEI) {
                window = WindowGeneBankNEI.create(player, object, side);
            } else {
                window = this.getWindow(player, object, side);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return window;
    }
}
