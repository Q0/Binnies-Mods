package binnie.craftgui.minecraft;

import binnie.craftgui.window.Panel;

public class MinecraftGUI {
    public MinecraftGUI() {
        super();
    }

    public static enum PanelType implements Panel.IPanelType {
        Black,
        Gray,
        Tinted,
        Coloured,
        Outline,
        TabOutline;

        private PanelType() {
        }
    }
}
