package binnie.craftgui.minecraft.control;

import binnie.craftgui.controls.core.Control;
import binnie.craftgui.core.IWidget;
import binnie.craftgui.minecraft.InventoryType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ControlSlotArray extends Control implements Iterable {
    private int rows;
    private int columns;
    private List slots = new ArrayList();

    public ControlSlotArray(IWidget parent, int x, int y, int columns, int rows) {
        super(parent, (float) x, (float) y, (float) (columns * 18), (float) (rows * 18));
        this.rows = rows;
        this.columns = columns;

        for (int row = 0; row < rows; ++row) {
            for (int column = 0; column < columns; ++column) {
                this.slots.add(this.createSlot(column * 18, row * 18));
            }
        }

    }

    public ControlSlot createSlot(int x, int y) {
        return new ControlSlot(this, (float) x, (float) y);
    }

    public void setItemStacks(ItemStack[] array) {
        int i = 0;

        for (ItemStack item : array) {
            if (i >= this.slots.size()) {
                return;
            }

            ((ControlSlot) this.slots.get(i)).slot.putStack(item);
            ++i;
        }

    }

    public ControlSlot getControlSlot(int i) {
        return i >= 0 && i < this.slots.size() ? (ControlSlot) this.slots.get(i) : null;
    }

    public ControlSlotArray create(int[] index) {
        return this.create(InventoryType.Machine, index);
    }

    public ControlSlotArray create(InventoryType type, int[] index) {
        int i = 0;

        for (ControlSlot slot : this.slots) {
            slot.assign(type, index[i++]);
        }

        return this;
    }

    public Iterator iterator() {
        return this.slots.iterator();
    }
}
