package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.core.BotanyGUI;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = Botany.proxy.getIcon(register, "botanistDatabase");
        this.iconMaster = Botany.proxy.getIcon(register, "masterBotanistDatabase");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1) {
        return par1 == 0 ? this.itemIcon : this.iconMaster;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        super.getSubItems(par1, par2CreativeTabs, par3List);
        par3List.add(new ItemStack(par1, 1, 1));
    }

    public ItemDictionary() {
        super();
        this.setCreativeTab(CreativeTabBotany.instance);
        this.setUnlocalizedName("database");
        this.setMaxStackSize(1);
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        if (itemstack.getItemDamage() == 0) {
            Botany.proxy.openGui(BotanyGUI.Database, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        } else {
            Botany.proxy.openGui(BotanyGUI.DatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        }

        return itemstack;
    }

    public String getItemStackDisplayName(ItemStack i) {
        return i.getItemDamage() == 0 ? "Botanist Database" : "Master Botanist Database";
    }
}
