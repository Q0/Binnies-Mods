package binnie.extratrees.core;

import binnie.core.gui.IBinnieGUID;
import binnie.craftgui.extratrees.dictionary.*;
import binnie.craftgui.extratrees.kitchen.WindowBottleRack;
import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public enum ExtraTreesGUID implements IBinnieGUID {
    Database,
    Woodworker,
    Lumbermill,
    DatabaseNEI,
    Incubator,
    MothDatabase,
    MothDatabaseNEI,
    Press,
    Brewery,
    Distillery,
    KitchenBottleRack,
    Infuser,
    SetSquare;

    private ExtraTreesGUID() {
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
                window = WindowArboristDatabase.create(player, side, this != Database);
                break;
            case Woodworker:
                window = WindowWoodworker.create(player, object, side);
                break;
            case Lumbermill:
                window = WindowLumbermill.create(player, object, side);
                break;
            case KitchenBottleRack:
                window = WindowBottleRack.create(player, object, side);
                break;
            case Press:
                window = WindowPress.create(player, object, side);
                break;
            case Brewery:
                window = WindowBrewery.create(player, object, side);
                break;
            case Distillery:
                window = WindowDistillery.create(player, object, side);
                break;
            case MothDatabase:
            case MothDatabaseNEI:
                window = WindowLepidopteristDatabase.create(player, side, this != MothDatabase);
                break;
            case SetSquare:
                window = WindowSetSquare.create(player, world, x, y, z, side);
        }

        return window;
    }
}
