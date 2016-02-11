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

    short[] textures;
    IIcon[] icons;

    FruitPod() {
        this.textures = new short[]{BinnieCore.proxy.getUniqueTextureUID(), BinnieCore.proxy.getUniqueTextureUID(), BinnieCore.proxy.getUniqueTextureUID()};
        this.icons = new IIcon[3];
    }

    public short[] getTextures() {
        return this.textures;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(final short texUID) {
        final int index = this.textures[0] - texUID;
        if (index >= 0 && index < 3) {
            return this.icons[index];
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister register) {
        this.icons[0] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".0");
        this.icons[1] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".1");
        this.icons[2] = ExtraTrees.proxy.getIcon(register, "pods/" + this.toString().toLowerCase() + ".2");
    }
}
