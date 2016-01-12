package binnie.core.texture;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class TextureManager {
    static List<Icon> textures;

    public static void init() {
    }

    static {
        TextureManager.textures = new ArrayList<Icon>();
    }
}
