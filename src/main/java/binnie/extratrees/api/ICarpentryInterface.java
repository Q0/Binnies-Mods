package binnie.extratrees.api;

import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.List;

public interface ICarpentryInterface {
    boolean registerCarpentryWood(int var1, IDesignMaterial var2);

    int getCarpentryWoodIndex(IDesignMaterial var1);

    IDesignMaterial getWoodMaterial(int var1);

    boolean registerDesign(int var1, IDesign var2);

    int getDesignIndex(IDesign var1);

    IDesign getDesign(int var1);

    ILayout getLayout(IPattern var1, boolean var2);

    IDesignMaterial getWoodMaterial(ItemStack var1);

    boolean registerDesignCategory(IDesignCategory var1);

    IDesignCategory getDesignCategory(String var1);

    Collection getAllDesignCategories();

    List getSortedDesigns();
}
