package binnie.extratrees.genetics;

import binnie.Binnie;
import binnie.core.resource.BinnieIcon;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.IIconProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum FruitSprite implements IIconProvider {
   Tiny,
   Small,
   Average,
   Large,
   Larger,
   Pear;

   BinnieIcon icon;

   private FruitSprite() {
   }

   public short getIndex() {
      return (short)(this.ordinal() + 4200);
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(short texUID) {
      int index = texUID - 4200;
      return index >= 0 && index < values().length?values()[index].icon.getIcon():null;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      this.icon = Binnie.Resource.getBlockIcon(ExtraTrees.instance, "fruit/" + this.toString().toLowerCase());
   }
}
