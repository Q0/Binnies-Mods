package binnie.botany;

import binnie.botany.Botany;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabBotany extends CreativeTabs {
   public static CreativeTabs instance = new CreativeTabBotany();

   public ItemStack getIconItemStack() {
      return new ItemStack(Blocks.red_flower, 1, 5);
   }

   public CreativeTabBotany() {
      super("Botany");
   }

   public String getTranslatedTabLabel() {
      return this.getTabLabel();
   }

   @SideOnly(Side.CLIENT)
   public String getTabLabel() {
      return Botany.proxy.localise("tab.botany");
   }

   @SideOnly(Side.CLIENT)
   public Item getTabIconItem() {
      return Item.getItemFromBlock(Blocks.yellow_flower);
   }
}
