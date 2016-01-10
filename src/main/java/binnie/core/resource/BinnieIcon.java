package binnie.core.resource;

import binnie.Binnie;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BinnieIcon extends BinnieResource {
    private int textureSheet = 0;
    private IIcon icon = null;

    public BinnieIcon(AbstractMod mod, ResourceType type, String path) {
        super(mod, type, path);
        this.textureSheet = type == ResourceType.Block ? 0 : 1;
        Binnie.Resource.registerIcon(this);
    }

    public IIcon getIcon() {
        return this.icon;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IIconRegister register) {
        this.registerIcon(register);
        return this.icon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcon(IIconRegister register) {
        this.icon = BinnieCore.proxy.getIcon(register, this.mod, this.path);
    }

    public int getTextureSheet() {
        return this.textureSheet;
    }
}
