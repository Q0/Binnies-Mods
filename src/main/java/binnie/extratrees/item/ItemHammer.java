package binnie.extratrees.item;

import binnie.extratrees.ExtraTrees;
import binnie.extratrees.api.IToolHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemHammer extends Item implements IToolHammer {
   boolean isDurableHammer = false;

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      this.itemIcon = ExtraTrees.proxy.getIcon(register, this.isDurableHammer?"durableHammer":"carpentryHammer");
   }

   public ItemHammer(boolean durable) {
      super();
      this.isDurableHammer = durable;
      this.setCreativeTab(CreativeTabs.tabTools);
      this.setUnlocalizedName(durable?"durableHammer":"hammer");
      this.setMaxStackSize(1);
      this.setMaxDamage(durable?1562:251);
   }

   public String getItemStackDisplayName(ItemStack i) {
      return this.isDurableHammer?"Master Carpentry Hammer":"Carpentry Hammer";
   }

   public boolean isActive(ItemStack item) {
      return true;
   }

   public void onHammerUsed(ItemStack item, EntityPlayer player) {
      item.damageItem(1, player);
   }
}
