package binnie.core.gui;

import binnie.core.network.IOrdinaled;
import binnie.craftgui.minecraft.Window;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IBinnieGUID extends IOrdinaled {
   Window getWindow(EntityPlayer var1, World var2, int var3, int var4, int var5, Side var6);
}
