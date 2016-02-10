package binnie.extrabees.products;

import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemHoneyCrystal extends Item {
    private int maxCharge;
    private int transferLimit;
    private int tier;

    public ItemHoneyCrystal() {
        this.maxCharge = 8000;
        this.transferLimit = 500;
        this.tier = 1;
        this.setMaxDamage(27);
        this.setMaxStackSize(1);
        this.setCreativeTab(Tabs.tabApiculture);
        this.setUnlocalizedName("honeyCrystal");
    }

    public static NBTTagCompound getOrCreateNbtData(final ItemStack itemStack) {
        NBTTagCompound ret = itemStack.getTagCompound();
        if (ret == null) {
            ret = new NBTTagCompound();
            itemStack.setTagCompound(ret);
        }
        return ret;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.itemIcon = ExtraBees.proxy.getIcon(register, "honeyCrystal");
    }

    public String getItemStackDisplayName(final ItemStack i) {
        return ExtraBees.proxy.localise("item.honeycrystal");
    }
}
