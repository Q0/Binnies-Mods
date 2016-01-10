package binnie.craftgui.genetics.machine;

import binnie.core.AbstractMod;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.GUIIcon;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.*;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.Incubator;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowIncubator extends WindowMachine {
    static Texture ProgressBase = new StandardTexture(0, 91, 38, 32, GeneticsTexture.GUIProcess);
    static Texture Progress = new StandardTexture(38, 91, 38, 32, GeneticsTexture.GUIProcess);

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowIncubator(player, inventory, side);
    }

    public WindowIncubator(EntityPlayer player, IInventory inventory, Side side) {
        super(228, 196, player, inventory, side);
    }

    public void initialiseClient() {
        this.setTitle("Incubator");
        int x = 16;
        int y = 32;
        (new ControlLiquidTank(this, x, y)).setTankID(0);
        x = x + 26;
        (new ControlSlotArray(this, x, y + 3, 1, 3)).create(Incubator.slotQueue);
        x = x + 20;
        new ControlIconDisplay(this, (float) x, (float) (y + 3 + 10), GUIIcon.ArrowRight.getIcon());
        x = x + 18;
        new ControlMachineProgress(this, x, y + 6, ProgressBase, Progress, Position.Left);
        (new ControlSlot(this, (float) (x + 11), (float) (y + 3 + 10))).assign(3);
        x = x + 40;
        new ControlIconDisplay(this, (float) x, (float) (y + 3 + 10), GUIIcon.ArrowRight.getIcon());
        x = x + 18;
        (new ControlSlotArray(this, x, y + 3, 1, 3)).create(Incubator.slotOutput);
        x = x + 26;
        (new ControlLiquidTank(this, x, y)).setTankID(1);
        x = x + 34;
        new ControlEnergyBar(this, x, y + 3, 16, 54, Position.Bottom);
        new ControlErrorState(this, 91.0F, 82.0F);
        new ControlPlayerInventory(this);
    }

    public String getTitle() {
        return "Incubator";
    }

    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    protected String getName() {
        return "Incubator";
    }
}
