package binnie.extratrees.item;

import binnie.core.item.IItemMisc;
import binnie.extratrees.item.Food;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.Tabs;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemFood extends net.minecraft.item.ItemFood {
   IItemMisc[] items;

   public ItemFood() {
      super(0, 0.0F, false);
      this.setUnlocalizedName("food");
      this.setCreativeTab(Tabs.tabArboriculture);
      this.setHasSubtypes(true);
      this.items = Food.values();
   }

   @SideOnly(Side.CLIENT)
   public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
      for(IItemMisc item : this.items) {
         if(item.isActive()) {
            par3List.add(this.getStack(item, 1));
         }
      }

   }

   private IItemMisc getItem(int damage) {
      return damage >= this.items.length?this.items[0]:this.items[damage];
   }

   public ItemStack getStack(IItemMisc type, int size) {
      return new ItemStack(this, size, type.ordinal());
   }

   @SideOnly(Side.CLIENT)
   public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
      super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
      IItemMisc item = this.getItem(par1ItemStack.getItemDamage());
      if(item != null) {
         item.addInformation(par3List);
      }

   }

   public String getItemStackDisplayName(ItemStack stack) {
      IItemMisc item = this.getItem(stack.getItemDamage());
      return item != null?item.getName(stack):"null";
   }

   public IIcon getIcon(ItemStack stack, int pass) {
      IItemMisc item = this.getItem(stack.getItemDamage());
      return item != null?item.getIcon(stack):null;
   }

   public IIcon getIconFromDamage(int damage) {
      IItemMisc item = this.getItem(damage);
      return item != null?item.getIcon((ItemStack)null):null;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      for(IItemMisc item : this.items) {
         if(item.isActive()) {
            item.registerIcons(register);
         }
      }

   }

   private Food getFood(ItemStack par1ItemStack) {
      return Food.values()[par1ItemStack.getItemDamage()];
   }

   public int func_150905_g(ItemStack p_150905_1_) {
      return this.getFood(p_150905_1_).getHealth();
   }

   public float func_150906_h(ItemStack p_150906_1_) {
      return 3.0F;
   }
}
