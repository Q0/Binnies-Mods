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
    private List<String> tooltips;

    public BinnieCircuit(final String uid, final int limit, final ICircuitLayout layout, final ItemStack itemStack) {
        this.tooltips = new ArrayList<String>();
        this.uid = "binnie.circuit." + uid;
        this.limit = limit;
        ChipsetManager.circuitRegistry.registerCircuit(this);

        if (itemStack != null) {
            ChipsetManager.solderManager.addRecipe(layout, itemStack, this);
        }
    }

    public BinnieCircuit(final String uid, final int limit, final ICircuitLayout layout, final Item item, final int itemMeta) {
        this(uid, limit, layout, new ItemStack(item, 1, itemMeta));
    }

    public void addTooltipString(final String string) {
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

    @Override
    public boolean isCircuitable(Object tile) {
        return false;
    }

    @Override
    public void onInsertion(int slot, Object tile) {

    }

    @Override
    public void onLoad(int slot, Object tile) {

    }

    @Override
    public void onRemoval(int slot, Object tile) {

    }

    @Override
    public void onTick(int slot, Object tile) {

    }

    public boolean isCircuitable(final TileEntity tile) {
        return false;
    }

    public void addTooltip(final List<String> list) {
        for (final String string : this.tooltips) {
            list.add(" - " + string);
        }
    }
}
