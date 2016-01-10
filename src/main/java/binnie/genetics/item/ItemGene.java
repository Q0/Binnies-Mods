package binnie.genetics.item;

import binnie.genetics.CreativeTabGenetics;
import binnie.genetics.Genetics;
import binnie.genetics.genetics.IGeneItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public abstract class ItemGene extends Item {
    IIcon[] icons = new IIcon[4];

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass) {
        return this.icons[pass];
    }

    public boolean getShareTag() {
        return true;
    }

    public boolean isRepairable() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.icons[0] = Genetics.proxy.getIcon(register, "machines/serum.glass");
        this.icons[1] = Genetics.proxy.getIcon(register, "machines/serum.cap");
        this.icons[2] = Genetics.proxy.getIcon(register, "machines/serum.edges");
        this.icons[3] = Genetics.proxy.getIcon(register, "machines/serum.dna");
    }

    public int getRenderPasses(int metadata) {
        return 4;
    }

    public ItemGene(String unlocName) {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(16);
        this.setUnlocalizedName(unlocName);
        this.setCreativeTab(CreativeTabGenetics.instance);
    }

    public int getColorFromItemStack(ItemStack itemstack, int j) {
        IGeneItem gene = this.getGeneItem(itemstack);
        return gene.getColour(j);
    }

    public int getCharges(ItemStack stack) {
        return stack == null ? 0 : stack.getItem().getMaxDamage() - stack.getItemDamage();
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer entityPlayer, List list, boolean par4) {
        super.addInformation(itemstack, entityPlayer, list, par4);
        int damage = this.getMaxDamage() - itemstack.getItemDamage();
        if (damage == 0) {
            list.add("Empty");
        } else if (damage == 1) {
            list.add("1 Charge");
        } else {
            list.add(damage + " Charges");
        }

        IGeneItem gene = this.getGeneItem(itemstack);
        gene.getInfo(list);
    }

    public abstract String getItemStackDisplayName(ItemStack var1);

    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List itemList) {
    }

    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public abstract IGeneItem getGeneItem(ItemStack var1);
}
