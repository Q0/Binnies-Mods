package binnie.craftgui.resource.minecraft;

import binnie.core.resource.BinnieResource;
import binnie.core.resource.IBinnieTexture;
import binnie.craftgui.core.geometry.IArea;
import binnie.craftgui.core.geometry.IBorder;
import binnie.craftgui.resource.Texture;

public class StandardTexture extends Texture {
    public StandardTexture(int u, int v, int w, int h, IBinnieTexture textureFile) {
        this(u, v, w, h, 0, (BinnieResource) textureFile.getTexture());
    }

    public StandardTexture(int u, int v, int w, int h, int offset, IBinnieTexture textureFile) {
        this(u, v, w, h, offset, textureFile.getTexture());
    }

    public StandardTexture(int u, int v, int w, int h, BinnieResource textureFile) {
        this(u, v, w, h, 0, (BinnieResource) textureFile);
    }

    public StandardTexture(int u, int v, int w, int h, int padding, BinnieResource textureFile) {
        super(new IArea((float) u, (float) v, (float) w, (float) h), IBorder.ZERO, new IBorder((float) padding), textureFile);
    }

    public BinnieResource getTexture() {
        return this.getFilename();
    }
}
