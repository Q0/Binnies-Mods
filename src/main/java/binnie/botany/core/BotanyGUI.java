package binnie.botany.core;

import binnie.core.gui.IBinnieGUID;
import binnie.craftgui.botany.WindowBotanistDatabase;
import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public enum BotanyGUI implements IBinnieGUID {
    Database,
    DatabaseNEI;

    private BotanyGUI() {
    }

    public Window getWindow(EntityPlayer player, World world, int x, int y, int z, Side side) {
        Window window = null;
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        IInventory object = null;
        if (tileEntity instanceof IInventory) {
            object = (IInventory) tileEntity;
        }

        switch (this) {
            case Database:
            case DatabaseNEI:
                window = WindowBotanistDatabase.create(player, side, this != Database);
            default:
                return window;
        }
    }
}
