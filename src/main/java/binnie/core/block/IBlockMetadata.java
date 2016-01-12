package binnie.core.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public interface IBlockMetadata extends ITileEntityProvider {
    int getPlacedMeta(final ItemStack p0, final World p1, final int p2, final int p3, final int p4, final ForgeDirection p5);

    int getDroppedMeta(final int p0, final int p1);

    String getBlockName(final ItemStack p0);

    void getBlockTooltip(final ItemStack p0, final List p1);

    void dropAsStack(final World p0, final int p1, final int p2, final int p3, final ItemStack p4);
}
