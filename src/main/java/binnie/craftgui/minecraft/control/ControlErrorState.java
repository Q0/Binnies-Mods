package binnie.craftgui.minecraft.control;

import binnie.core.machines.power.ErrorState;
import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.*;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.minecraft.CustomSlot;
import binnie.craftgui.minecraft.MinecraftTooltip;
import binnie.craftgui.minecraft.Window;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.entity.player.InventoryPlayer;

import java.util.List;

public class ControlErrorState extends Control implements ITooltip {
    private ErrorState errorState;
    private int type = 0;

    public void onRenderBackground() {
        Object texture = CraftGUITexture.StateWarning;
        if (this.errorState == null) {
            texture = CraftGUITexture.StateNone;
        } else if (this.type == 0) {
            texture = CraftGUITexture.StateError;
        }

        CraftGUI.Render.texture(texture, IPoint.ZERO);
        super.onRenderBackground();
    }

    public ErrorState getError() {
        return Window.get(this).getContainer().getErrorState();
    }

    public final void onUpdateClient() {
        this.errorState = this.getError();
        this.type = Window.get(this).getContainer().getErrorType();
        ((List) ControlSlot.highlighting.get(EnumHighlighting.Error)).clear();
        ((List) ControlSlot.highlighting.get(EnumHighlighting.Warning)).clear();
        ControlLiquidTank.tankError.clear();
        ControlEnergyBar.isError = false;
        if (this.isMouseOver() && this.errorState != null) {
            ControlEnergyBar.isError = this.errorState.isPowerError();
            if (this.errorState.isItemError()) {
                for (int slot : this.errorState.getData()) {
                    int id = -1;

                    for (CustomSlot cslot : Window.get(this).getContainer().getCustomSlots()) {
                        if (!(cslot.inventory instanceof InventoryPlayer) && cslot.getSlotIndex() == slot) {
                            id = cslot.slotNumber;
                        }
                    }

                    if (id >= 0) {
                        if (this.type == 0) {
                            ((List) ControlSlot.highlighting.get(EnumHighlighting.Error)).add(Integer.valueOf(id));
                        } else {
                            ((List) ControlSlot.highlighting.get(EnumHighlighting.Warning)).add(Integer.valueOf(id));
                        }
                    }
                }
            }

            if (this.errorState.isTankError()) {
                for (int slot : this.errorState.getData()) {
                    ControlLiquidTank.tankError.add(Integer.valueOf(slot));
                }
            }

        }
    }

    public ControlErrorState(IWidget parent, float x, float y) {
        super(parent, x, y, 16.0F, 16.0F);
        this.addAttribute(Attribute.MouseOver);
    }

    public void getTooltip(Tooltip tooltipOrig) {
        MinecraftTooltip tooltip = (MinecraftTooltip) tooltipOrig;
        if (this.errorState != null) {
            if (this.type == 0) {
                tooltip.setType(MinecraftTooltip.Type.Error);
            } else {
                tooltip.setType(MinecraftTooltip.Type.Warning);
            }

            tooltip.add(this.errorState.toString());
            if (this.errorState.getTooltip().length() > 0) {
                tooltip.add(this.errorState.getTooltip());
            }
        }

    }

    public ErrorState getErrorState() {
        return this.errorState;
    }
}
