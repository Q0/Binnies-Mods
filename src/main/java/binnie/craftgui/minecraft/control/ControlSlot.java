package binnie.craftgui.minecraft.control;

import binnie.core.machines.inventory.InventorySlot;
import binnie.core.machines.inventory.MachineSide;
import binnie.core.machines.inventory.SlotValidator;
import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.core.Tooltip;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IPoint;
import binnie.craftgui.events.EventMouse;
import binnie.craftgui.minecraft.*;
import binnie.craftgui.resource.minecraft.CraftGUITexture;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ControlSlot extends ControlSlotBase {
    public static Map highlighting = new HashMap();
    public static boolean shiftClickActive = false;
    public Slot slot = null;

    public ControlSlot(IWidget parent, float x, float y) {
        super(parent, x, y);
        this.addSelfEventHandler(new EventMouse.Down.Handler() {
            public void onEvent(EventMouse.Down event) {
                if (ControlSlot.this.slot != null) {
                    PlayerControllerMP var10000 = ((Window) ControlSlot.this.getSuperParent()).getGui().getMinecraft().playerController;
                    int var10001 = ((Window) ControlSlot.this.getSuperParent()).getContainer().windowId;
                    int var10002 = ControlSlot.this.slot.slotNumber;
                    int var10003 = event.getButton();
                    Window.get(ControlSlot.this.getWidget()).getGui();
                    var10000.windowClick(var10001, var10002, var10003, GuiCraftGUI.isShiftKeyDown() ? 1 : 0, ((Window) ControlSlot.this.getSuperParent()).getGui().getMinecraft().thePlayer);
                }

            }
        });
    }

    public ControlSlot(IWidget parent, int x, int y, Slot slot) {
        super(parent, (float) x, (float) y);
        this.slot = slot;
    }

    public void onRenderBackground() {
        CraftGUI.Render.texture((Object) CraftGUITexture.Slot, (IPoint) IPoint.ZERO);
        if (this.slot != null) {
            InventorySlot islot = this.getInventorySlot();
            if (islot != null && islot.getValidator() != null) {
                IIcon icon = islot.getValidator().getIcon(!islot.getInputSides().isEmpty());
                if (icon != null) {
                    CraftGUI.Render.iconItem(new IPoint(1.0F, 1.0F), icon);
                }
            }

            boolean highlighted = false;

            for (Entry<EnumHighlighting, List<Integer>> highlight : highlighting.entrySet()) {
                if ((highlight.getKey() != EnumHighlighting.ShiftClick || shiftClickActive) && !highlighted && ((List) highlight.getValue()).contains(Integer.valueOf(this.slot.slotNumber))) {
                    highlighted = true;
                    int c = -1442840576 + Math.min(((EnumHighlighting) highlight.getKey()).getColour(), 16777215);
                    CraftGUI.Render.gradientRect(new IArea(1.0F, 1.0F, 16.0F, 16.0F), c, c);
                }
            }

            if (!highlighted && this.getSuperParent().getMousedOverWidget() == this) {
                if (Window.get(this).getGui().getDraggedItem() != null && !this.slot.isItemValid(Window.get(this).getGui().getDraggedItem())) {
                    CraftGUI.Render.gradientRect(new IArea(1.0F, 1.0F, 16.0F, 16.0F), -1426089575, -1426089575);
                } else {
                    CraftGUI.Render.gradientRect(new IArea(1.0F, 1.0F, 16.0F, 16.0F), -2130706433, -2130706433);
                }
            }

        }
    }

    public void onRenderOverlay() {
        if (this.slot != null) {
            boolean highlighted = false;

            for (Entry<EnumHighlighting, List<Integer>> highlight : highlighting.entrySet()) {
                if ((highlight.getKey() != EnumHighlighting.ShiftClick || shiftClickActive) && !highlighted && ((List) highlight.getValue()).contains(Integer.valueOf(this.slot.slotNumber))) {
                    highlighted = true;
                    int c = ((EnumHighlighting) highlight.getKey()).getColour();
                    IArea area = this.getArea();
                    if (this.getParent() instanceof ControlSlotArray || this.getParent() instanceof ControlPlayerInventory) {
                        area = this.getParent().getArea();
                        area.setPosition(IPoint.ZERO.sub(this.getPosition()));
                    }

                    CraftGUI.Render.colour(c);
                    CraftGUI.Render.texture((Object) CraftGUITexture.Outline, (IArea) area.outset(1));
                }
            }

        }
    }

    public void onUpdateClient() {
        super.onUpdateClient();
        if (this.slot != null) {
            if (this.isMouseOver() && GuiScreen.isShiftKeyDown()) {
                Window.get(this).getContainer().setMouseOverSlot(this.slot);
                shiftClickActive = true;
            }

            if (Window.get(this).getGui().isHelpMode() && this.isMouseOver()) {
                for (ControlSlot slot2 : this.getControlSlots()) {
                    if (slot2.slot != null) {
                        ((List) highlighting.get(EnumHighlighting.Help)).add(Integer.valueOf(slot2.slot.slotNumber));
                    }
                }
            }

        }
    }

    private List getControlSlots() {
        List<ControlSlot> slots = new ArrayList();
        if (!(this.getParent() instanceof ControlSlotArray) && !(this.getParent() instanceof ControlPlayerInventory)) {
            slots.add(this);
        } else {
            for (IWidget child : this.getParent().getWidgets()) {
                slots.add((ControlSlot) child);
            }
        }

        return slots;
    }

    public ItemStack getItemStack() {
        return this.slot != null ? this.slot.getStack() : null;
    }

    public ControlSlot assign(int index) {
        return this.assign(InventoryType.Machine, index);
    }

    public ControlSlot assign(InventoryType inventory, int index) {
        if (this.slot != null) {
            return this;
        } else {
            this.slot = ((Window) this.getSuperParent()).getContainer().getOrCreateSlot(inventory, index);
            return this;
        }
    }

    public void getHelpTooltip(Tooltip tooltip) {
        if (this.slot != null) {
            InventorySlot slot = this.getInventorySlot();
            if (this.getInventorySlot() != null) {
                tooltip.add(slot.getName());
                tooltip.add("Insert Side: " + MachineSide.asString(slot.getInputSides()));
                tooltip.add("Extract Side: " + MachineSide.asString(slot.getOutputSides()));
                if (slot.isReadOnly()) {
                    tooltip.add("Pickup Only Slot");
                }

                tooltip.add("Accepts: " + (slot.getValidator() == null ? "Any Item" : slot.getValidator().getTooltip()));
            } else if (this.slot.inventory instanceof WindowInventory) {
                SlotValidator s = ((WindowInventory) this.slot.inventory).getValidator(this.slot.getSlotIndex());
                tooltip.add("Accepts: " + (s == null ? "Any Item" : s.getTooltip()));
            } else if (this.slot.inventory instanceof InventoryPlayer) {
                tooltip.add("Player Inventory");
            }

        }
    }

    public InventorySlot getInventorySlot() {
        return this.slot instanceof CustomSlot ? ((CustomSlot) this.slot).getInventorySlot() : null;
    }

    static {
        for (EnumHighlighting h : EnumHighlighting.values()) {
            highlighting.put(h, new ArrayList());
        }

    }
}
