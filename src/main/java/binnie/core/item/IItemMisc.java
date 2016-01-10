package binnie.core.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public interface IItemMisc extends IItemEnum {
    IIcon getIcon(ItemStack var1);

    @SideOnly(Side.CLIENT)
    void registerIcons(IIconRegister var1);

    void addInformation(List var1);
}
