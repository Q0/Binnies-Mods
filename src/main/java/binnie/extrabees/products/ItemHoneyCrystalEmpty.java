package binnie.extrabees.products;

import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

public class ItemHoneyCrystalEmpty extends ItemHoneyCrystal {
    public ItemHoneyCrystalEmpty() {
        super();
        this.setMaxDamage(0);
        this.setMaxStackSize(64);
        this.setUnlocalizedName("honeyCrystalEmpty");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = ExtraBees.proxy.getIcon(register, "honeyCrystalEmpty");
    }

    public String getItemStackDisplayName(ItemStack i) {
        return ExtraBees.proxy.localise("item.honeycrystal.empty");
    }
}
