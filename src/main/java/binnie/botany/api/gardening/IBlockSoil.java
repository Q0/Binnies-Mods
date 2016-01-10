package binnie.botany.api.gardening;

import binnie.botany.api.EnumAcidity;
import binnie.botany.api.EnumMoisture;
import binnie.botany.api.EnumSoilType;
import net.minecraft.world.World;

public interface IBlockSoil {
   EnumAcidity getPH(World var1, int var2, int var3, int var4);

   EnumMoisture getMoisture(World var1, int var2, int var3, int var4);

   EnumSoilType getType(World var1, int var2, int var3, int var4);

   boolean fertilise(World var1, int var2, int var3, int var4, EnumSoilType var5);

   boolean degrade(World var1, int var2, int var3, int var4, EnumSoilType var5);

   boolean setPH(World var1, int var2, int var3, int var4, EnumAcidity var5);

   boolean setMoisture(World var1, int var2, int var3, int var4, EnumMoisture var5);

   boolean resistsWeeds(World var1, int var2, int var3, int var4);
}
