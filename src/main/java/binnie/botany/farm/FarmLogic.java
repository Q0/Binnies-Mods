package binnie.botany.farm;

import forestry.api.farming.IFarmHousing;
import forestry.api.farming.IFarmLogic;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class FarmLogic implements IFarmLogic {
    World world;
    IFarmHousing housing;
    boolean isManual;

    public FarmLogic(final IFarmHousing housing) {
        this.housing = housing;
    }

    public IFarmLogic setManual(final boolean flag) {
        this.isManual = flag;
        return (IFarmLogic) this;
    }

    protected final boolean isAirBlock(final Vect position) {
        return this.world.isAirBlock(position.x, position.y, position.z);
    }

    protected final boolean isWaterBlock(final Vect position) {
        return this.world.getBlock(position.x, position.y, position.z) == Blocks.water;
    }

    protected final Block getBlock(final Vect position) {
        return this.world.getBlock(position.x, position.y, position.z);
    }

    protected final int getBlockMeta(final Vect position) {
        return this.world.getBlockMetadata(position.x, position.y, position.z);
    }

    protected final ItemStack getAsItemStack(final Vect position) {
        return new ItemStack(this.getBlock(position), 1, this.getBlockMeta(position));
    }

    protected final Vect translateWithOffset(final int x, final int y, final int z, final ForgeDirection direction, final int step) {
        return new Vect(x + direction.offsetX * step, y + direction.offsetY * step, z + direction.offsetZ * step);
    }

    protected final void setBlock(final Vect position, final Block block, final int meta) {
        this.world.setBlock(position.x, position.y, position.z, block, meta, 2);
    }
}
