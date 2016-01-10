package binnie.craftgui.minecraft.control;

import binnie.core.machines.Machine;
import binnie.core.machines.TileEntityMachine;
import binnie.core.machines.power.IPoweredMachine;
import binnie.core.machines.power.IProcess;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.core.geometry.Position;
import binnie.craftgui.minecraft.MinecraftTooltip;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.inventory.IInventory;
import org.lwjgl.opengl.GL11;

public class ControlEnergyBar extends Control implements ITooltip {
    public static boolean isError;
    private Position direction;

    public ControlEnergyBar(IWidget parent, int x, int y, int width, int height, Position direction) {
        super(parent, (float) x, (float) y, (float) width, (float) height);
        this.direction = direction;
        this.addAttribute(Attribute.MouseOver);
    }

    public IPoweredMachine getClientPower() {
        IInventory inventory = Window.get(this).getInventory();
        TileEntityMachine machine = (TileEntityMachine) ((TileEntityMachine) (inventory instanceof TileEntityMachine ? inventory : null));
        if (machine == null) {
            return null;
        } else {
            IPoweredMachine clientPower = (IPoweredMachine) machine.getMachine().getInterface(IPoweredMachine.class);
            return clientPower;
        }
    }

    public float getPercentage() {
        float percentage = 100.0F * this.getStoredEnergy() / this.getMaxEnergy();
        if (percentage > 100.0F) {
            percentage = 100.0F;
        }

        return percentage;
    }

    private float getStoredEnergy() {
        return (float) Window.get(this).getContainer().getPowerInfo().getStoredEnergy();
    }

    private float getMaxEnergy() {
        return (float) Window.get(this).getContainer().getPowerInfo().getMaxEnergy();
    }

    public void getTooltip(Tooltip tooltip) {
        tooltip.add((int) this.getPercentage() + "% charged");
        tooltip.add(this.getStoredEnergy() + "/" + this.getMaxEnergy() + " RF");
    }

    public void getHelpTooltip(Tooltip tooltip) {
        tooltip.add("Energy Bar");
        tooltip.add("Current: " + this.getStoredEnergy() + " RF (" + (int) this.getPercentage() + "%)");
        tooltip.add("Capacity: " + this.getMaxEnergy() + " RF");
        IProcess process = (IProcess) Machine.getInterface(IProcess.class, Window.get(this).getInventory());
        if (process != null) {
            tooltip.add("Usage: " + (int) process.getEnergyPerTick() + " RF");
        }

    }

    public void onRenderBackground() {
        CraftGUI.Render.texture((Object) CraftGUITexture.EnergyBarBack, (IArea) this.getArea());
        float percentage = this.getPercentage() / 100.0F;
        CraftGUI.Render.colour(this.getColourFromPercentage(percentage));
        IArea area = this.getArea();
        switch (this.direction) {
            case Top:
            case Bottom:
                float height = area.size().y() * percentage;
                area.setSize(new IPoint(area.size().x(), height));
                break;
            case Left:
            case Right:
                float width = area.size().x() * percentage;
                area.setSize(new IPoint(width, area.size().y()));
        }

        if (this.isMouseOver() && Window.get(this).getGui().isHelpMode()) {
            int c = -1442840576 + MinecraftTooltip.getOutline(Tooltip.Type.Help);
            CraftGUI.Render.gradientRect(this.getArea().inset(1), c, c);
        } else if (isError) {
            int c = -1442840576 + MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error);
            CraftGUI.Render.gradientRect(this.getArea().inset(1), c, c);
        }

        CraftGUI.Render.texture((Object) CraftGUITexture.EnergyBarGlow, (IArea) area);
        GL11.glColor3d(1.0D, 1.0D, 1.0D);
        CraftGUI.Render.texture((Object) CraftGUITexture.EnergyBarGlass, (IArea) this.getArea());
    }

    public void onRenderForeground() {
        if (this.isMouseOver() && Window.get(this).getGui().isHelpMode()) {
            IArea area = this.getArea();
            CraftGUI.Render.colour(MinecraftTooltip.getOutline(Tooltip.Type.Help));
            CraftGUI.Render.texture((Object) CraftGUITexture.Outline, (IArea) area.outset(1));
        } else if (isError) {
            IArea area = this.getArea();
            CraftGUI.Render.colour(MinecraftTooltip.getOutline(MinecraftTooltip.Type.Error));
            CraftGUI.Render.texture((Object) CraftGUITexture.Outline, (IArea) area.outset(1));
        }

    }

    public int getColourFromPercentage(float percentage) {
        int colour = 16777215;
        if ((double) percentage > 0.5D) {
            int r = (int) ((1.0D - 2.0D * ((double) percentage - 0.5D)) * 255.0D);
            colour = (r << 16) + '\uff00';
        } else {
            int g = (int) (255.0F * 2.0F * percentage);
            colour = 16711680 + (g << 8);
        }

        return colour;
    }
}
