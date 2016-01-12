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
    IIcon[] icons;

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(final int damage, final int pass) {
        return this.icons[pass];
    }

    public boolean getShareTag() {
        return true;
    }

    public boolean isRepairable() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.icons[0] = Genetics.proxy.getIcon(register, "machines/serum.glass");
        this.icons[1] = Genetics.proxy.getIcon(register, "machines/serum.cap");
        this.icons[2] = Genetics.proxy.getIcon(register, "machines/serum.edges");
        this.icons[3] = Genetics.proxy.getIcon(register, "machines/serum.dna");
    }

    public int getRenderPasses(final int metadata) {
        return 4;
    }

    public ItemGene(final String unlocName) {
        this.icons = new IIcon[4];
        this.setMaxStackSize(1);
        this.setMaxDamage(16);
        this.setUnlocalizedName(unlocName);
        this.setCreativeTab(CreativeTabGenetics.instance);
    }

    public int getColorFromItemStack(final ItemStack itemstack, final int j) {
        final IGeneItem gene = this.getGeneItem(itemstack);
        return gene.getColour(j);
    }

    public int getCharges(final ItemStack stack) {
        return (stack == null) ? 0 : (stack.getItem().getMaxDamage() - stack.getItemDamage());
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack itemstack, final EntityPlayer entityPlayer, final List list, final boolean par4) {
        super.addInformation(itemstack, entityPlayer, list, par4);
        final int damage = this.getMaxDamage() - itemstack.getItemDamage();
        if (damage == 0) {
            list.add("Empty");
        } else if (damage == 1) {
            list.add("1 Charge");
        } else {
            list.add(damage + " Charges");
        }
        final IGeneItem gene = this.getGeneItem(itemstack);
        gene.getInfo(list);
    }

    public abstract String getItemStackDisplayName(final ItemStack p0);

    public void getSubItems(final Item par1, final CreativeTabs par2CreativeTabs, final List itemList) {
    }

    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public abstract IGeneItem getGeneItem(final ItemStack p0);
}
