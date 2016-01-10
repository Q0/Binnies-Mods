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
    private int maxCharge = 8000;
    private int transferLimit = 500;
    private int tier = 1;

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = ExtraBees.proxy.getIcon(register, "honeyCrystal");
    }

    public ItemHoneyCrystal() {
        super();
        this.setMaxDamage(27);
        this.setMaxStackSize(1);
        this.setCreativeTab(Tabs.tabApiculture);
        this.setUnlocalizedName("honeyCrystal");
    }

    public String getItemStackDisplayName(ItemStack i) {
        return ExtraBees.proxy.localise("item.honeycrystal");
    }

    public static NBTTagCompound getOrCreateNbtData(ItemStack itemStack) {
        NBTTagCompound ret = itemStack.getTagCompound();
        if (ret == null) {
            ret = new NBTTagCompound();
            itemStack.setTagCompound(ret);
        }

        return ret;
    }
}
