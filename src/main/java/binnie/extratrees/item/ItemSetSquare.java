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

public class ItemSetSquare extends Item implements IToolHammer {
   ItemSetSquare.EnumSetSquareMode mode = ItemSetSquare.EnumSetSquareMode.Rotate;

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      this.itemIcon = ExtraTrees.proxy.getIcon(register, "setSquare" + this.mode.ordinal());
   }

   public ItemSetSquare(ItemSetSquare.EnumSetSquareMode mode) {
      super();
      this.mode = mode;
      this.setCreativeTab(CreativeTabs.tabTools);
      this.setUnlocalizedName("setSquare" + mode);
      this.setMaxStackSize(1);
   }

   public String getItemStackDisplayName(ItemStack i) {
      return "Set Square";
   }

   public boolean isActive(ItemStack item) {
      return this.mode == ItemSetSquare.EnumSetSquareMode.Rotate;
   }

   public void onHammerUsed(ItemStack item, EntityPlayer player) {
   }

   public static enum EnumSetSquareMode {
      Rotate,
      Edit,
      Swap;

      private EnumSetSquareMode() {
      }
   }
}
