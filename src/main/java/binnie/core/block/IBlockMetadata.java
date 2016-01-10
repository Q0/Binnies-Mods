package binnie.core.block;

import java.util.List;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IBlockMetadata extends ITileEntityProvider {
   int getPlacedMeta(ItemStack var1, World var2, int var3, int var4, int var5, ForgeDirection var6);

   int getDroppedMeta(int var1, int var2);

   String getBlockName(ItemStack var1);

   void getBlockTooltip(ItemStack var1, List var2);

   void dropAsStack(World var1, int var2, int var3, int var4, ItemStack var5);
}
