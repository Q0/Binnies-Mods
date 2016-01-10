package binnie.extratrees.block;

import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.CarpentryManager;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.block.decor.FenceDescription;
import binnie.extratrees.block.decor.FenceType;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WoodManager {
    public WoodManager() {
        super();
    }

    public static IPlankType getPlankType(int index) {
        IDesignMaterial wood = CarpentryManager.carpentryInterface.getWoodMaterial(index);
        return (IPlankType) (wood instanceof IPlankType ? (IPlankType) wood : PlankType.ExtraTreePlanks.Fir);
    }

    public static int getPlankTypeIndex(IPlankType type) {
        int index = CarpentryManager.carpentryInterface.getCarpentryWoodIndex(type);
        return index < 0 ? 0 : index;
    }

    public static FenceType getFenceType(ItemStack stack) {
        FenceDescription desc = getFenceDescription(stack);
        return desc == null ? null : desc.getFenceType();
    }

    public static FenceDescription getFenceDescription(ItemStack stack) {
        if (stack == null) {
            return null;
        } else if (stack.getItem() == Item.getItemFromBlock(ExtraTrees.blockMultiFence)) {
            int damage = TileEntityMetadata.getItemDamage(stack);
            return getFenceDescription(damage);
        } else {
            for (IPlankType type : getAllPlankTypes()) {
                if (type instanceof IFenceProvider) {
                    ItemStack f = ((IFenceProvider) type).getFence();
                    if (f != null && ItemStack.areItemStacksEqual(stack, f)) {
                        return new FenceDescription(new FenceType(0), type, type);
                    }
                }
            }

            return null;
        }
    }

    public static FenceDescription getFenceDescription(int meta) {
        return new FenceDescription(meta);
    }

    public static FenceType getFenceType(int meta) {
        return getFenceDescription(meta).getFenceType();
    }

    public static ItemStack getGate(IPlankType plank) {
        return plank == PlankType.VanillaPlanks.OAK ? new ItemStack(Blocks.fence_gate) : TileEntityMetadata.getItemStack(ExtraTrees.blockGate, getPlankTypeIndex(plank));
    }

    public static ItemStack getFence(IPlankType plank, FenceType type, int amount) {
        return getFence(plank, plank, type, amount);
    }

    public static ItemStack getFence(IPlankType plank, IPlankType plank2, FenceType type, int amount) {
        if (plank instanceof IFenceProvider && plank == plank2 && type.isPlain()) {
            ItemStack original = ((IFenceProvider) plank).getFence();
            if (original != null) {
                original.stackSize = amount;
                return original;
            }
        }

        int ord = type.ordinal();
        int i = getPlankTypeIndex(plank) + 256 * ord;
        ItemStack stack = TileEntityMetadata.getItemStack(ExtraTrees.blockMultiFence, i + 65536 * getPlankTypeIndex(plank2));
        stack.stackSize = amount;
        return stack;
    }

    public static ItemStack getDoor(IPlankType plank, DoorType type) {
        return TileEntityMetadata.getItemStack(ExtraTrees.blockDoor, type.ordinal() * 256 + getPlankTypeIndex(plank));
    }

    public static List getAllPlankTypes() {
        List<IPlankType> list = new ArrayList();

        for (IPlankType type : PlankType.ExtraTreePlanks.values()) {
            list.add(type);
        }

        for (IPlankType type : PlankType.ForestryPlanks.values()) {
            list.add(type);
        }

        for (IPlankType type : PlankType.ExtraBiomesPlank.values()) {
            if (type.getStack() != null) {
                list.add(type);
            }
        }

        for (IPlankType type : PlankType.VanillaPlanks.values()) {
            list.add(type);
        }

        return list;
    }

    public static IPlankType get(ItemStack species) {
        for (IPlankType type : getAllPlankTypes()) {
            if (type.getStack() != null && type.getStack().isItemEqual(species)) {
                return type;
            }
        }

        return null;
    }
}
