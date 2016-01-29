package binnie.extratrees.item;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.core.ExtraTreesGUID;
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
        this.setCreativeTab(Tabs.tabArboriculture);
        this.setUnlocalizedName("database");
        this.setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.itemIcon = ExtraTrees.proxy.getIcon(register, "arboristDatabase");
        this.iconMaster = ExtraTrees.proxy.getIcon(register, "masterArboristDatabase");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(final int par1) {
        return (par1 == 0) ? this.itemIcon : this.iconMaster;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        if (par1ItemStack.getItemDamage() > 0) {
            par3List.add("Sengir-in-a-can");
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List par3List) {
        super.getSubItems(par1, par2CreativeTabs, par3List);
        par3List.add(new ItemStack(par1, 1, 1));
    }

    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
        if (itemstack.getItemDamage() == 0) {
            ExtraTrees.proxy.openGui(ExtraTreesGUID.Database, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        } else {
            ExtraTrees.proxy.openGui(ExtraTreesGUID.DatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return itemstack;
    }

    public String getItemStackDisplayName(final ItemStack i) {
        return (i.getItemDamage() == 0) ? "Arborist Database" : "Master Arborist Database";
    }
}
