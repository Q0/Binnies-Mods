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

    public int getColorFromItemStack(ItemStack itemStack, int j) {
        int i = itemStack.getItemDamage();
        return j == 0 ? EnumHoneyDrop.get(itemStack).colour[0] : EnumHoneyDrop.get(itemStack).colour[1];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int i, int j) {
        return j > 0 ? this.icon1 : this.icon2;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.icon1 = BinnieCore.proxy.getIcon(register, "forestry", "honeyDrop.0");
        this.icon2 = BinnieCore.proxy.getIcon(register, "forestry", "honeyDrop.1");
    }
}
