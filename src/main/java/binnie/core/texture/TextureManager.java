package binnie.core.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class TextureManager {
   static List textures = new ArrayList();

   public TextureManager() {
      super();
   }

   public static void init() {
   }
}
