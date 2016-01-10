package binnie.extrabees.products;

import binnie.core.BinnieCore;
import binnie.extrabees.products.EnumPropolis;
import binnie.extrabees.products.ItemProduct;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;

public class ItemPropolis extends ItemProduct {
   public ItemPropolis() {
      super(EnumPropolis.values());
      this.setCreativeTab(Tabs.tabApiculture);
      this.setUnlocalizedName("propolis");
   }

   public int getColorFromItemStack(ItemStack itemStack, int j) {
      int i = itemStack.getItemDamage();
      return j == 0?EnumPropolis.get(itemStack).colour[0]:EnumPropolis.get(itemStack).colour[1];
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      this.itemIcon = BinnieCore.proxy.getIcon(register, "forestry", "propolis.0");
   }
}
