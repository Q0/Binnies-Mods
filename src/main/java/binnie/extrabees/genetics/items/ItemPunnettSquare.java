package binnie.extrabees.genetics.items;

import binnie.extrabees.ExtraBees;
import binnie.extrabees.core.ExtraBeeGUID;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPunnettSquare extends Item {
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.itemIcon = ExtraBees.proxy.getIcon(register, "");
    }

    public ItemPunnettSquare() {
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setMaxStackSize(1);
    }

    public String getItemStackDisplayName(final ItemStack itemstack) {
        return "Punnett Square";
    }

    public ItemStack onItemRightClick(final ItemStack itemstack, final World world, final EntityPlayer player) {
        ExtraBees.proxy.openGui(ExtraBeeGUID.PunnettSquare, player, (int) player.posX, (int) player.posY, (int) player.posZ);
        return itemstack;
    }
}
