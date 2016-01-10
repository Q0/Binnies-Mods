package binnie.extratrees.api;

import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.ILayout;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public interface IPattern {
   IIcon getPrimaryIcon(IDesignSystem var1);

   IIcon getSecondaryIcon(IDesignSystem var1);

   ILayout getRotation();

   ILayout getHorizontalFlip();

   void registerIcons(IIconRegister var1);
}
