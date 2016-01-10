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

    public FarmLogic(IFarmHousing housing) {
        super();
        this.housing = housing;
    }

    public IFarmLogic setManual(boolean flag) {
        this.isManual = flag;
        return this;
    }

    protected final boolean isAirBlock(Vect position) {
        return this.world.isAirBlock(position.x, position.y, position.z);
    }

    protected final boolean isWaterBlock(Vect position) {
        return this.world.getBlock(position.x, position.y, position.z) == Blocks.water;
    }

    protected final Block getBlock(Vect position) {
        return this.world.getBlock(position.x, position.y, position.z);
    }

    protected final int getBlockMeta(Vect position) {
        return this.world.getBlockMetadata(position.x, position.y, position.z);
    }

    protected final ItemStack getAsItemStack(Vect position) {
        return new ItemStack(this.getBlock(position), 1, this.getBlockMeta(position));
    }

    protected final Vect translateWithOffset(int x, int y, int z, ForgeDirection direction, int step) {
        return new Vect(x + direction.offsetX * step, y + direction.offsetY * step, z + direction.offsetZ * step);
    }

    protected final void setBlock(Vect position, Block block, int meta) {
        this.world.setBlock(position.x, position.y, position.z, block, meta, 2);
    }
}
