package binnie.extratrees.api;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public interface IDesignSystem {
    IIcon getPrimaryIcon(IPattern var1);

    IIcon getSecondaryIcon(IPattern var1);

    void registerIcons(IIconRegister var1);

    IDesignMaterial getDefaultMaterial();

    IDesignMaterial getMaterial(int var1);

    int getMaterialIndex(IDesignMaterial var1);

    IDesignMaterial getDefaultMaterial2();

    ItemStack getAdhesive();

    IDesignMaterial getMaterial(ItemStack var1);
}
