package binnie.craftgui.resource;

import java.util.HashMap;
import java.util.Map;

public class StyleSheet implements IStyleSheet {
    protected Map textures = new HashMap();

    public StyleSheet() {
        super();
    }

    public Texture getTexture(Object key) {
        return !this.textures.containsKey(key) ? StyleSheetManager.getTexture(key) : (Texture) this.textures.get(key);
    }
}
