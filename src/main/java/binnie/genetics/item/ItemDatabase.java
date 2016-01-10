package binnie.genetics.item;

import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.core.GeneticsGUI;
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

public class ItemDatabase extends Item {
    IIcon iconMaster;

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = Genetics.proxy.getIcon(register, "geneDatabase");
        this.iconMaster = Genetics.proxy.getIcon(register, "masterGeneDatabase");
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

    public ItemDatabase() {
        super();
        this.setCreativeTab(CreativeTabGenetics.instance);
        this.setUnlocalizedName("database");
        this.setMaxStackSize(1);
    }

    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
        if (itemstack.getItemDamage() == 0) {
            Genetics.proxy.openGui(GeneticsGUI.Database, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        } else {
            Genetics.proxy.openGui(GeneticsGUI.DatabaseNEI, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        }

        return itemstack;
    }

    public String getItemStackDisplayName(ItemStack i) {
        return i.getItemDamage() == 0 ? "Gene Database" : "Master Gene Database";
    }
}
