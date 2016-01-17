package binnie.extrabees.apiary;

import binnie.extrabees.ExtraBees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeHousing;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

public class ItemHiveFrame extends forestry.apiculture.items.ItemHiveFrame {
    EnumHiveFrame frame;

    public ItemHiveFrame(final EnumHiveFrame frame) {
        super(frame.maxDamage, frame.getGeneticDecay(null, 1));
        this.frame = frame;
        setUnlocalizedName("hiveFrame");
    }

    public ItemStack frameUsed(final IBeeHousing housing, final ItemStack frame, final IBee queen, final int wear) {
        frame.setItemDamage(frame.getItemDamage() + wear);

        if (frame.getItemDamage() >= frame.getMaxDamage()) {
            return null;
        }

        return frame;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        itemIcon = ExtraBees.proxy.getIcon(register, "frame" + frame.toString());
    }

    public String getItemStackDisplayName(final ItemStack par1ItemStack) {
        return frame.getName();
    }
}
