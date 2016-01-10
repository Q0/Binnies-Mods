package binnie.craftgui.extratrees.dictionary;

import binnie.core.AbstractMod;
import binnie.core.machines.Machine;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.*;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.machines.Brewery;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowBrewery extends Window {
    public WindowBrewery(EntityPlayer player, IInventory inventory, Side side) {
        super(228.0F, 218.0F, player, inventory, side);
    }

    protected AbstractMod getMod() {
        return ExtraTrees.instance;
    }

    protected String getName() {
        return "Brewery";
    }

    public void initialiseClient() {
        this.setTitle(Machine.getMachine(this.getInventory()).getPackage().getDisplayName());
        (new ControlSlotArray(this, 42, 32, 1, 3)).create(Brewery.slotRecipeGrains);
        (new ControlSlot(this, 16.0F, 41.0F)).assign(Brewery.slotRecipeInput);
        (new ControlSlot(this, 105.0F, 77.0F)).assign(Brewery.slotRecipeYeast);
        (new ControlLiquidTank(this, 76, 32)).setTankID(Brewery.tankInput);
        (new ControlLiquidTank(this, 162, 32)).setTankID(Brewery.tankOutput);
        new ControlEnergyBar(this, 196, 32, 16, 60, Position.Bottom);
        new ControlBreweryProgress(this, 110.0F, 32.0F);
        (new ControlSlotArray(this, (int) (this.getSize().x() / 2.0F - 81.0F), 104, 9, 1)).create(Brewery.slotInventory);
        new ControlPlayerInventory(this);
        new ControlErrorState(this, 133.0F, 79.0F);
    }

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowBrewery(player, inventory, side);
    }
}
