package binnie.extratrees.block;

import binnie.core.BinnieCore;
import binnie.extratrees.ExtraTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.IIconProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public enum FruitPod implements IIconProvider {
   Cocoa,
   Banana,
   Coconut,
   Plantain,
   RedBanana,
   Papayimar;

   short[] textures = new short[]{BinnieCore.proxy.getUniqueTextureUID(), BinnieCore.proxy.getUniqueTextureUID(), BinnieCore.proxy.getUniqueTextureUID()};
   IIcon[] icons = new IIcon[3];

   private FruitPod() {
   }

   public short[] getTextures() {
      return this.textures;
   }

   @SideOnly(Side.CLIENT)
   public IIcon getIcon(short texUID) {
      int index = this.textures[0] - texUID;
      return index >= 0 && index < 3?this.icons[index]:null;
   }

   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister register) {
      this.icons[0] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".0");
      this.icons[1] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".1");
      this.icons[2] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".2");
   }
}
