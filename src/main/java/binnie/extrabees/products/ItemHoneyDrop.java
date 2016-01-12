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
        this.setCreativeTab(Tabs.tabApiculture);
        this.setUnlocalizedName("honeyDrop");
    }

    public int getColorFromItemStack(final ItemStack itemStack, final int j) {
        final int i = itemStack.getItemDamage();
        if (j == 0) {
            return EnumHoneyDrop.get(itemStack).colour[0];
        }
        return EnumHoneyDrop.get(itemStack).colour[1];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(final int i, final int j) {
        if (j > 0) {
            return this.icon1;
        }
        return this.icon2;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.icon1 = BinnieCore.proxy.getIcon(register, "forestry", "honeyDrop.0");
        this.icon2 = BinnieCore.proxy.getIcon(register, "forestry", "honeyDrop.1");
    }
}
