package binnie.extrabees.core;

import binnie.core.gui.IBinnieGUID;
import binnie.craftgui.minecraft.Window;
import binnie.extrabees.gui.WindowAlvearyFrame;
import binnie.extrabees.gui.WindowAlvearyHatchery;
import binnie.extrabees.gui.WindowAlvearyMutator;
import binnie.extrabees.gui.WindowAlvearyStimulator;
import binnie.extrabees.gui.database.WindowApiaristDatabase;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public enum ExtraBeeGUID implements IBinnieGUID {
    Database,
    DatabaseNEI,
    AlvearyMutator,
    AlvearyFrame,
    AlvearyStimulator,
    PunnettSquare,
    AlvearyHatchery;

    @Override
    public Window getWindow(final EntityPlayer player, final World world, final int x, final int y, final int z, final Side side) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        IInventory object = null;

        if (tileEntity instanceof IInventory) {
            object = (IInventory) tileEntity;
        }

        switch (this) {
            case Database:
            case DatabaseNEI:
                return WindowApiaristDatabase.create(player, side, this != ExtraBeeGUID.Database);

            case AlvearyMutator:
                return WindowAlvearyMutator.create(player, object, side);

            case AlvearyFrame:
                return WindowAlvearyFrame.create(player, object, side);

            case AlvearyStimulator:
                return WindowAlvearyStimulator.create(player, object, side);

            case AlvearyHatchery:
                return WindowAlvearyHatchery.create(player, object, side);
        }

        return null;
    }
}
