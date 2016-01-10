package binnie.botany.genetics;

import binnie.botany.Botany;
import binnie.botany.CreativeTabBotany;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemEncyclopedia extends Item {
   boolean reinforced = false;

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      this.itemIcon = Botany.proxy.getIcon(register, "encylopedia" + (this.reinforced?"Iron":""));
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIconFromDamage(int par1) {
      return this.itemIcon;
   }

   public ItemEncyclopedia(boolean reinforced) {
      super();
      this.reinforced = reinforced;
      this.setCreativeTab(CreativeTabBotany.instance);
      this.setUnlocalizedName("encylopedia" + (reinforced?"Iron":""));
      this.setMaxStackSize(1);
      this.setMaxDamage(reinforced?480:120);
   }

   public String getItemStackDisplayName(ItemStack i) {
      return (this.reinforced?"Reinforced ":"") + "Florist\'s Encyclopaedia";
   }
}
