package binnie.botany.items;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import binnie.botany.genetics.EnumFlowerColor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemClay extends Item {
   public ItemClay() {
      super();
      this.setUnlocalizedName("clay");
      this.setHasSubtypes(true);
      this.setCreativeTab(CreativeTabBotany.instance);
   }

   @SideOnly(Side.CLIENT)
   public int getColorFromItemStack(ItemStack stack, int p_82790_2_) {
      int damage = stack.getItemDamage();
      return EnumFlowerColor.get(damage).getColor(false);
   }

   public String getItemStackDisplayName(ItemStack stack) {
      return EnumFlowerColor.get(stack.getItemDamage()).getName() + " Clay";
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List list) {
      for(EnumFlowerColor c : EnumFlowerColor.values()) {
         list.add(new ItemStack(this, 1, c.ordinal()));
      }

   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      this.itemIcon = Botany.proxy.getIcon(register, "clay");
   }
}
