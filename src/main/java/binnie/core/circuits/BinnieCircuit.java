package binnie.core.circuits;

import forestry.api.circuits.ChipsetManager;
import forestry.api.circuits.ICircuit;
import forestry.api.circuits.ICircuitLayout;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

public class BinnieCircuit implements ICircuit {
    private String uid;
    private int limit;
    private List tooltips;

    public BinnieCircuit(String uid, int limit, ICircuitLayout layout, ItemStack itemStack) {
        super();
        this.tooltips = new ArrayList();
        this.uid = "binnie.circuit." + uid;
        this.limit = limit;
        ChipsetManager.circuitRegistry.registerCircuit(this);
        if (itemStack != null) {
            ChipsetManager.solderManager.addRecipe(layout, itemStack, this);
        }

    }

    public BinnieCircuit(String uid, int limit, ICircuitLayout layout, Item item, int itemMeta) {
        this(uid, limit, layout, new ItemStack(item, 1, itemMeta));
    }

    public void addTooltipString(String string) {
        this.tooltips.add(string);
    }

    public String getUID() {
        return this.uid;
    }

    public boolean requiresDiscovery() {
        return false;
    }

    public int getLimit() {
        return this.limit;
    }

    public String getName() {
        return this.uid;
    }

    public boolean isCircuitable(TileEntity tile) {
        return false;
    }

    public void onInsertion(int slot, TileEntity tile) {
    }

    public void onLoad(int slot, TileEntity tile) {
    }

    public void onRemoval(int slot, TileEntity tile) {
    }

    public void onTick(int slot, TileEntity tile) {
    }

    public void addTooltip(List list) {
        for (String string : this.tooltips) {
            list.add(" - " + string);
        }

    }
}
