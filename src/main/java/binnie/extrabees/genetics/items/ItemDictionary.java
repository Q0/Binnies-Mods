package binnie.extrabees.genetics.items;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeGUID;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemDictionary extends Item {
    IIcon iconMaster;

    public ItemDictionary() {
        setCreativeTab(Tabs.tabApiculture);
        setUnlocalizedName("dictionary");
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        itemIcon = ExtraBees.proxy.getIcon(register, "apiaristDatabase");
        iconMaster = ExtraBees.proxy.getIcon(register, "masterApiaristDatabase");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int par1) {
        return (par1 == 0) ? itemIcon : iconMaster;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List list, final boolean par4) {
        super.addInformation(itemStack, player, list, par4);
        if (itemStack.getItemDamage() > 0) {
            list.add("Flora-in-a-box");
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        super.getSubItems(par1, par2CreativeTabs, par3List);
        par3List.add(new ItemStack(par1, 1, 1));
    }

    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
        if (itemstack.getItemDamage() == 0) {
            ExtraBees.proxy.openGui(ExtraBeeGUID.Database, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        else {
            ExtraBees.proxy.openGui(ExtraBeeGUID.DatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        }

        return itemstack;
    }

    public String getItemStackDisplayName(final ItemStack i) {
        return (i.getItemDamage() == 0) ? "Apiarist Database" : "Master Apiarist Database";
    }
}
