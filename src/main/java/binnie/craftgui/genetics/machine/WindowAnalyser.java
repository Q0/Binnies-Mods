package binnie.craftgui.genetics.machine;

import binnie.core.AbstractMod;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.GUIIcon;
import binnie.craftgui.minecraft.MinecraftGUI;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.minecraft.control.*;
import binnie.craftgui.resource.Texture;
import binnie.craftgui.resource.minecraft.StandardTexture;
import binnie.craftgui.window.Panel;
import binnie.extrabees.core.ExtraBeeTexture;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsTexture;
import binnie.genetics.machine.Analyser;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public class WindowAnalyser extends WindowMachine {
    static Texture ProgressBase;
    static Texture Progress;

    static {
        WindowAnalyser.ProgressBase = new StandardTexture(0, 218, 142, 17, ExtraBeeTexture.GUIProgress.getTexture());
        WindowAnalyser.Progress = new StandardTexture(0, 201, 142, 17, ExtraBeeTexture.GUIProgress.getTexture());
    }

    public WindowAnalyser(final EntityPlayer player, final IInventory inventory, final Side side) {
        super(220, 210, player, inventory, side);
    }

    public static Window create(final EntityPlayer player, final IInventory inventory, final Side side) {
        return new WindowAnalyser(player, inventory, side);
    }

    @Override
    public void initialiseClient() {
        WindowAnalyser.ProgressBase = new StandardTexture(0, 51, 66, 40, GeneticsTexture.GUIProcess.getTexture());
        WindowAnalyser.Progress = new StandardTexture(66, 51, 66, 40, GeneticsTexture.GUIProcess.getTexture());
        int x = 16;
        final int y = 32;
        new ControlSlotArray(this, x, y, 2, 3).create(Analyser.slotReserve);
        x += 28;
        new ControlSlot(this, x, y + 54 + 8).assign(13);
        new ControlSlotCharge(this, x + 20, y + 54 + 8, 13).setColour(10040319);
        new ControlEnergyBar(this, x + 24 + 16, y + 54 + 8 + 1, 60, 16, Position.Left);
        new ControlErrorState(this, x + 24 + 16 + 60 + 16, y + 54 + 8 + 1);
        x -= 28;
        new ControlIconDisplay(this, x + 36 + 2, y + 18, GUIIcon.ArrowRight.getIcon());
        x += 56;
        new Panel(this, x, y, 76.0f, 50.0f, MinecraftGUI.PanelType.Tinted);
        new ControlProgress(this, x + 5, y + 5, WindowAnalyser.ProgressBase, WindowAnalyser.Progress, Position.Left);
        new ControlSlot(this, x + 38 - 9, y + 25 - 9).assign(6);
        new ControlIconDisplay(this, x + 76 + 2, y + 18, GUIIcon.ArrowRight.getIcon());
        x += 96;
        new ControlSlotArray(this, x, y, 2, 3).create(Analyser.slotFinished);
        x += 52;
        this.setTitle("Analyser");
        new ControlPlayerInventory(this);
    }

    @Override
    public String getTitle() {
        return "Analyser";
    }

    @Override
    protected AbstractMod getMod() {
        return Genetics.instance;
    }

    @Override
    protected String getName() {
        return "Analyser";
    }
}
