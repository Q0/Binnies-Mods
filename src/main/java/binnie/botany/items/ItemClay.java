package binnie.botany.items;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemClay extends Item {
    public ItemClay() {
        this.setUnlocalizedName("clay");
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabBotany.instance);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(final ItemStack stack, final int p_82790_2_) {
        final int damage = stack.getItemDamage();
        return EnumFlowerColor.get(damage).getColor(false);
    }

    public String getItemStackDisplayName(final ItemStack stack) {
        return EnumFlowerColor.get(stack.getItemDamage()).getName() + " Clay";
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List list) {
        for (final EnumFlowerColor c : EnumFlowerColor.values()) {
            list.add(new ItemStack(this, 1, c.ordinal()));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.itemIcon = Botany.proxy.getIcon(register, "clay");
    }
}
