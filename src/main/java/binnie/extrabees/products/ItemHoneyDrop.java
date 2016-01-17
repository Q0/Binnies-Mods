package binnie.extrabees.products;

import binnie.core.BinnieCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemHoneyDrop extends ItemProduct {
    IIcon icon1;
    IIcon icon2;

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public ItemHoneyDrop() {
        super(EnumHoneyDrop.values());
        setCreativeTab(Tabs.tabApiculture);
        setUnlocalizedName("honeyDrop");
    }

    public int getColorFromItemStack(final ItemStack itemStack, final int j) {
        if (j == 0) {
            return EnumHoneyDrop.get(itemStack).colour[0];
        }

        return EnumHoneyDrop.get(itemStack).colour[1];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(final int i, final int j) {
        if (j > 0) {
            return icon1;
        }

        return icon2;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        icon1 = BinnieCore.proxy.getIcon(register, "forestry", "honeyDrop.0");
        icon2 = BinnieCore.proxy.getIcon(register, "forestry", "honeyDrop.1");
    }
}
