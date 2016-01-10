package binnie.extratrees.carpentry;

import binnie.extratrees.api.*;
import net.minecraft.item.ItemStack;

import java.util.*;
import java.util.Map.Entry;

public class CarpentryInterface implements ICarpentryInterface {
    static Map woodMap = new LinkedHashMap();
    static Map designMap = new LinkedHashMap();
    static Map designCategories = new HashMap();

    public CarpentryInterface() {
        super();
    }

    public boolean registerCarpentryWood(int index, IDesignMaterial wood) {
        return wood != null ? woodMap.put(Integer.valueOf(index), wood) == null : false;
    }

    public int getCarpentryWoodIndex(IDesignMaterial wood) {
        for (Integer integer : woodMap.keySet()) {
            if (((IDesignMaterial) woodMap.get(integer)).equals(wood)) {
                return integer.intValue();
            }
        }

        return -1;
    }

    public IDesignMaterial getWoodMaterial(int index) {
        return (IDesignMaterial) woodMap.get(Integer.valueOf(index));
    }

    public boolean registerDesign(int index, IDesign wood) {
        return wood != null ? designMap.put(Integer.valueOf(index), wood) == null : false;
    }

    public int getDesignIndex(IDesign wood) {
        for (Integer integer : designMap.keySet()) {
            if (((IDesign) designMap.get(integer)).equals(wood)) {
                return integer.intValue();
            }
        }

        return -1;
    }

    public IDesign getDesign(int index) {
        return (IDesign) designMap.get(Integer.valueOf(index));
    }

    public ILayout getLayout(IPattern pattern, boolean inverted) {
        return Layout.get(pattern, inverted);
    }

    public boolean registerDesignCategory(IDesignCategory category) {
        return category != null && category.getId() != null ? designCategories.put(category.getId(), category) == null : false;
    }

    public IDesignCategory getDesignCategory(String id) {
        return (IDesignCategory) designCategories.get(id);
    }

    public Collection getAllDesignCategories() {
        List<IDesignCategory> categories = new ArrayList();

        for (IDesignCategory category : designCategories.values()) {
            if (category.getDesigns().size() > 0) {
                categories.add(category);
            }
        }

        return categories;
    }

    public List getSortedDesigns() {
        List<IDesign> designs = new ArrayList();

        for (IDesignCategory category : this.getAllDesignCategories()) {
            designs.addAll(category.getDesigns());
        }

        return designs;
    }

    public IDesignMaterial getWoodMaterial(ItemStack stack) {
        if (stack == null) {
            return null;
        } else {
            for (Entry<Integer, IDesignMaterial> entry : woodMap.entrySet()) {
                ItemStack key = ((IDesignMaterial) entry.getValue()).getStack();
                if (key != null && key.isItemEqual(stack)) {
                    return (IDesignMaterial) entry.getValue();
                }
            }

            return null;
        }
    }
}
