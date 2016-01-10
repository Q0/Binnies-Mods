package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.botany.items.BotanyItems;
import binnie.core.AbstractMod;
import binnie.core.BinnieCore;
import binnie.extratrees.api.IDesignMaterial;
import binnie.extratrees.api.IDesignSystem;
import binnie.extratrees.api.IPattern;
import binnie.extratrees.carpentry.DesignerManager;
import binnie.extratrees.carpentry.EnumPattern;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.HashMap;
import java.util.Map;

public class CeramicDesignSystem implements IDesignSystem {
    public static CeramicDesignSystem instance = new CeramicDesignSystem();
    Map primary = new HashMap();
    Map secondary = new HashMap();

    CeramicDesignSystem() {
        super();
        DesignerManager.instance.registerDesignSystem(this);
    }

    public IDesignMaterial getDefaultMaterial() {
        return CeramicColor.get(EnumFlowerColor.White);
    }

    public IDesignMaterial getDefaultMaterial2() {
        return CeramicColor.get(EnumFlowerColor.Black);
    }

    public IDesignMaterial getMaterial(int id) {
        return CeramicColor.get(EnumFlowerColor.get(id));
    }

    public int getMaterialIndex(IDesignMaterial id) {
        return ((CeramicColor) id).color.ordinal();
    }

    public String getTexturePath() {
        return "ceramic";
    }

    public IIcon getPrimaryIcon(IPattern pattern) {
        return pattern instanceof EnumPattern ? (IIcon) this.primary.get(Integer.valueOf(((EnumPattern) pattern).ordinal())) : null;
    }

    public IIcon getSecondaryIcon(IPattern pattern) {
        return pattern instanceof EnumPattern ? (IIcon) this.secondary.get(Integer.valueOf(((EnumPattern) pattern).ordinal())) : null;
    }

    public void registerIcons(IIconRegister register) {
        for (EnumPattern pattern : EnumPattern.values()) {
            this.primary.put(Integer.valueOf(pattern.ordinal()), BinnieCore.proxy.getIcon(register, this.getMod().getModID(), this.getTexturePath() + "/" + pattern.toString().toLowerCase() + ".0"));
            this.secondary.put(Integer.valueOf(pattern.ordinal()), BinnieCore.proxy.getIcon(register, this.getMod().getModID(), this.getTexturePath() + "/" + pattern.toString().toLowerCase() + ".1"));
        }

    }

    public AbstractMod getMod() {
        return Botany.instance;
    }

    public ItemStack getAdhesive() {
        return BotanyItems.Mortar.get(1);
    }

    public IDesignMaterial getMaterial(ItemStack itemStack) {
        return itemStack.getItem() == Item.getItemFromBlock(Botany.ceramic) ? this.getMaterial(itemStack.getItemDamage()) : null;
    }
}
