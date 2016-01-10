package binnie.botany.api;

import net.minecraft.util.IIcon;

public interface IFlowerType {
    IIcon getStem(EnumFlowerStage var1, boolean var2, int var3);

    IIcon getPetalIcon(EnumFlowerStage var1, boolean var2, int var3);

    IIcon getVariantIcon(EnumFlowerStage var1, boolean var2, int var3);

    int getID();

    int getSections();

    int ordinal();
}
