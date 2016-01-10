package binnie.craftgui.resource;

import binnie.craftgui.core.CraftGUI;
import binnie.craftgui.resource.IStyleSheet;
import binnie.craftgui.resource.Texture;

public class StyleSheetManager {
   static IStyleSheet defaultSS = new StyleSheetManager.DefaultStyleSheet();

   public StyleSheetManager() {
      super();
   }

   public static Texture getTexture(Object key) {
      return defaultSS.getTexture(key);
   }

   public static IStyleSheet getDefault() {
      return defaultSS;
   }

   private static class DefaultStyleSheet implements IStyleSheet {
      private DefaultStyleSheet() {
         super();
      }

      public Texture getTexture(Object key) {
         return CraftGUI.ResourceManager.getTexture(key.toString());
      }
   }
}
