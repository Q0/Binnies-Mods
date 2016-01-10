package binnie.botany.ceramic;

import binnie.botany.Botany;
import binnie.botany.genetics.EnumFlowerColor;
import binnie.core.block.TileEntityMetadata;
import binnie.extratrees.api.IDesignMaterial;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

public class CeramicColor implements IDesignMaterial {
    EnumFlowerColor color;
    static Map map = new LinkedHashMap();

    CeramicColor(EnumFlowerColor color) {
        super();
        this.color = color;
    }

    public static CeramicColor get(EnumFlowerColor c) {
        return (CeramicColor) map.get(c);
    }

    public ItemStack getStack() {
        return TileEntityMetadata.getItemStack(Botany.ceramic, this.color.ordinal());
    }

    public String getName() {
        return this.color.getName();
    }

    public int getColour() {
        return this.color.getColor(false);
    }

    static {
        for (EnumFlowerColor c : EnumFlowerColor.values()) {
            map.put(c, new CeramicColor(c));
        }

    }
}
