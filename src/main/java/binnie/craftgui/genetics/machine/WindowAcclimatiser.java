package binnie.craftgui.genetics.machine;

import binnie.core.AbstractMod;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.*;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.machine.Acclimatiser;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowAcclimatiser extends WindowMachine {
    static Texture ProgressBase = new StandardTexture(64, 0, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
    static Texture Progress = new StandardTexture(64, 21, 130, 21, ExtraBeeTexture.GUIProgress.getTexture());
    public static final int[] slotReserve = new int[]{0, 1, 2, 3};
    public static final int slotTarget = 4;
    public static final int[] slotAcclimatiser = new int[]{5, 6, 7};
    public static final int[] slotDone = new int[]{8, 9, 10, 11};

    public static Window create(EntityPlayer player, IInventory inventory, Side side) {
        return new WindowAcclimatiser(player, inventory, side);
    }

    public WindowAcclimatiser(EntityPlayer player, IInventory inventory, Side side) {
        super(280, 198, player, inventory, side);
    }

    public void initialiseClient() {
        this.setTitle("Acclimatiser");
        int x = 16;
        int y = 32;
        (new ControlSlotArray(this, x, y, 2, 2)).create(Acclimatiser.slotReserve);
        x = x + 54;
        (new ControlSlot(this, (float) (x + 18), (float) y)).assign(4);
        (new ControlSlotArray(this, x, y + 18 + 18, 3, 1)).create(Acclimatiser.slotAcclimatiser);
        x = x + 72;
        (new ControlSlotArray(this, x, y, 2, 2)).create(Acclimatiser.slotDone);
        new ControlEnergyBar(this, 21, 115, 16, 60, Position.Bottom);
        new ControlErrorState(this, 181.0F, 83.0F);
        new ControlPlayerInventory(this);
    }

    public String getTitle() {
        return "Acclimatiser";
    }

    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    protected String getName() {
        return "Acclimatiser";
    }
}
