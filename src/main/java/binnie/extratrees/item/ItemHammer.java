package binnie.extratrees.item;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IToolHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHammer extends Item implements IToolHammer {
    boolean isDurableHammer;

    public ItemHammer(final boolean durable) {
        this.isDurableHammer = false;
        this.isDurableHammer = durable;
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setUnlocalizedName(durable ? "durableHammer" : "hammer");
        this.setMaxStackSize(1);
        this.setMaxDamage(durable ? 1562 : 251);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.itemIcon = ExtraTrees.proxy.getIcon(register, this.isDurableHammer ? "durableHammer" : "carpentryHammer");
    }

    public String getItemStackDisplayName(final ItemStack i) {
        return this.isDurableHammer ? "Master Carpentry Hammer" : "Carpentry Hammer";
    }

    public boolean isActive(final ItemStack item) {
        return true;
    }

    public void onHammerUsed(final ItemStack item, final EntityPlayer player) {
        item.damageItem(1, (EntityLivingBase) player);
    }
}
